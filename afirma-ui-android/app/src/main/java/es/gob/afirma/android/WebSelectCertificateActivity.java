/* Copyright (C) 2011 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * Date: 11/01/11
 * You may contact the copyright holder at: soporte.afirma5@mpt.es
 */

package es.gob.afirma.android;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.security.KeyChainException;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;

import es.gob.afirma.R;
import es.gob.afirma.android.crypto.AndroidHttpManager;
import es.gob.afirma.android.crypto.CipherDataManager;
import es.gob.afirma.android.crypto.KeyStoreManagerListener;
import es.gob.afirma.android.crypto.MobileKeyStoreManager;
import es.gob.afirma.android.crypto.SelectKeyAndroid41BugException;
import es.gob.afirma.android.errors.CommunicationErrors;
import es.gob.afirma.android.errors.ErrorCategory;
import es.gob.afirma.android.errors.ErrorManager;
import es.gob.afirma.android.errors.FunctionalErrors;
import es.gob.afirma.android.errors.InternalSoftwareErrors;
import es.gob.afirma.android.errors.RequestErrors;
import es.gob.afirma.android.gui.DownloadFileTask;
import es.gob.afirma.android.gui.MessageDialog;
import es.gob.afirma.android.gui.SendDataTask;
import es.gob.afirma.android.gui.SendDataTask.SendDataListener;
import es.gob.afirma.core.AOCancelledOperationException;
import es.gob.afirma.core.misc.Base64;
import es.gob.afirma.core.misc.http.UrlHttpManagerFactory;
import es.gob.afirma.core.misc.protocol.ParameterException;
import es.gob.afirma.core.misc.protocol.ProtocolInvocationUriParser;
import es.gob.afirma.core.misc.protocol.UrlParametersToSelectCert;

/** Actividad dedicada a la firma de los datos recibidos en la entrada mediante un certificado
 * del almac&eacute;n central seleccionado por el usuario. */
public final class WebSelectCertificateActivity extends LoadKeyStoreFragmentActivity
                                                implements KeyStoreManagerListener, DownloadFileTask.DownloadDataListener,
                                                            SendDataListener,
                                                            MobileKeyStoreManager.CertificateSelectionListener {

	private static final String ES_GOB_AFIRMA = "es.gob.afirma"; //$NON-NLS-1$

	/** Juego de carateres UTF-8. */
	private static final String DEFAULT_URL_ENCODING = "UTF-8"; //$NON-NLS-1$

	private UrlParametersToSelectCert parameters;

    private DownloadFileTask downloadFileTask = null;

	private MessageDialog messageDialog;
	MessageDialog getMessageDialog() {
		return this.messageDialog;
	}

	private ProgressDialog progressDialog = null;
	void setProgressDialog(final ProgressDialog pd) {
		this.progressDialog = pd;
	}

	static {
		// Instalamos el gestor de descargas que deseamos utilizar en las invocaciones por
		// protocolo a la aplicacion
		UrlHttpManagerFactory.install(new AndroidHttpManager());
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ksmListener = this;

		if (getIntent() == null || getIntent().getData() == null) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
			Logger.w(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText());  //$NON-NLS-1$
			closeActivity();
			return;
		}

		// Si cargamos la actividad desde el carrusel de aplicaciones, redirigimos a la
		// pantalla principal
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)
				== Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setClass(this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return;
		}

		// Si no estamos creando ahora la pantalla (por se una rotacion)
		if (savedInstanceState != null){
			Logger.i(ES_GOB_AFIRMA, "Se esta relanzando la actividad. Se omite volver a iniciar el proceso de firma");
			return;
		}

        Logger.d(ES_GOB_AFIRMA, "URI de invocacion: " + getIntent().getDataString()); //$NON-NLS-1$

        if (getIntent().getDataString() == null) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.INVOCATION_WITHOUT_URL);
            Logger.w(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText()); //$NON-NLS-1$
            closeActivity();
            return;
        }

		try {
			this.parameters = ProtocolInvocationUriParser.getParametersToSelectCert(getIntent().getDataString(), true);
		}
		catch (final ParameterException e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
			Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), e); //$NON-NLS-1$
			showErrorMessage(errorCat);
			launchError(ErrorManager.ERROR_BAD_PARAMETERS, true, errorCat);
			return;
		}
		catch (final Throwable e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
			Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText() + e, e); //$NON-NLS-1$
			showErrorMessage(errorCat);
			launchError(ErrorManager.ERROR_BAD_PARAMETERS, true, errorCat);
			return;
		}

		processSelectionRequest();
	}

	/** Inicia el proceso de selecci&oacute;n de certificado con los parametros previamente configurados. */
	private void processSelectionRequest() {

	    // Si se nos pasa un identificador de fichero, entonces no se nos ha podido pasar toda la
        // configuracion a traves de la URL y habra que descargarla
        if (this.parameters.getFileId() != null) {
            Logger.i(ES_GOB_AFIRMA, "Se van a descargar la configuracion desde el servidor con el identificador: " + this.parameters.getFileId()); //$NON-NLS-1$
            this.downloadFileTask = new DownloadFileTask(
                    this.parameters.getFileId(),
                    this.parameters.getRetrieveServletUrl(),
                    this
            );
            this.downloadFileTask.execute();
        }

        // Si tenemos la configuracion completa, cargamos un certificado
        else {

            Logger.i(ES_GOB_AFIRMA, "Se inicia la seleccion de certificado"); //$NON-NLS-1$
            showProgressDialog(getString(R.string.dialog_msg_loading_keystore));
            loadKeyStore(this);
        }
	}

    @Override
    public synchronized void certificateSelected(final MobileKeyStoreManager.SelectCertificateEvent kse) {

        byte[] certificate;
        try {
            Certificate[] certChain = kse.getCertChain();
            if (certChain == null || certChain.length == 0) {
                throw new NullPointerException("No se obtuvo el certificado del almacen");
            }
            certificate = certChain[0].getEncoded();
        }
        catch (final KeyChainException e) {
            if ("4.1.1".equals(Build.VERSION.RELEASE) || "4.1.0".equals(Build.VERSION.RELEASE) || "4.1".equals(Build.VERSION.RELEASE)) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Logger.e(ES_GOB_AFIRMA, "Error al extraer el certificado en Android " + Build.VERSION.RELEASE + ": " + e); //$NON-NLS-1$ //$NON-NLS-2$
                onKeyStoreError(KeyStoreOperation.SELECT_CERTIFICATE, getString(R.string.error_android_4_1), new SelectKeyAndroid41BugException(e));
            }
            else {
				ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.LOAD_KEYSTORE);
                Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText() + e); //$NON-NLS-1$
                onKeyStoreError(KeyStoreOperation.SELECT_CERTIFICATE, "No se pudo extraer el certificado del almacen", e);
            }
            return;
        }
        catch (final AOCancelledOperationException e) {
			ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.USER_NOT_SELECT_CERT);
            Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText() + e); //$NON-NLS-1$

			// Si hay algun almacen alternativo, peromitimos seleccionar de nuevo. Si nom se lanza
			// el error
			if (NfcHelper.isNfcPreferredConnection(this)) {
				loadKeyStore(this);
			} else {
				onKeyStoreError(KeyStoreOperation.SELECT_CERTIFICATE, errorCat.getCode() + " - " + errorCat.getAdminText(), new PendingIntent.CanceledException(e));
			}
			return;
        }
        // Cuando se instala el certificado desde el dialogo de seleccion, Android da a elegir certificado
        // en 2 ocasiones y en la segunda se produce un "java.lang.AssertionError". Se ignorara este error.
        catch (final AssertionError e) {
            Logger.e(ES_GOB_AFIRMA, "Posible error al insertar un nuevo certificado en el almacen. No se hara nada", e); //$NON-NLS-1$
            return;
        }
        catch (final Throwable e) {
			ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.LOAD_KEYSTORE);
            Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), e); //$NON-NLS-1$
            onKeyStoreError(KeyStoreOperation.SELECT_CERTIFICATE, errorCat.getCode() + " - " + errorCat.getUserText(), e); //$NON-NLS-1$
            return;
        }

        onSelectCertificateChainSuccess(certificate);
    }

    @Override
    public synchronized void onLoadingKeyStoreSuccess(final MobileKeyStoreManager msm) {

        // Si el usuario cancelo la insercion de PIN o cualquier otro dialogo del almacen
        if(msm == null){
			ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.USER_NOT_SELECT_CERT);
            onKeyStoreError(KeyStoreOperation.LOAD_KEYSTORE, errorCat.getCode() + " - " + errorCat.getUserText(), new PendingIntent.CanceledException("Se cancela la seleccion del almacen"));
            return;
        }
        msm.getCertificateChainAsynchronously(this);
    }

	@Override
	public void onKeyStoreError(KeyStoreOperation op, String msg, Throwable t) {
		if (op == KeyStoreOperation.LOAD_KEYSTORE) {
			ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.LOAD_KEYSTORE);
			launchError(ErrorManager.ERROR_ESTABLISHING_KEYSTORE,true, errorCat);
			return;
		}
		else if (op == KeyStoreOperation.SELECT_CERTIFICATE) {

			if (t instanceof SelectKeyAndroid41BugException) {
				ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.LOAD_KEY);
				launchError(ErrorManager.ERROR_PKE_ANDROID_4_1, true, errorCat);
			}
			else if (t instanceof KeyChainException) {
				ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.LOAD_KEY);
				launchError(ErrorManager.ERROR_PKE, true, errorCat);
			}
			else if (t instanceof PendingIntent.CanceledException) {
				ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.USER_NOT_SELECT_CERT);
				Logger.e(ES_GOB_AFIRMA, errorCat.getCode() + " - " + errorCat.getAdminText(), t); //$NON-NLS-1$
				launchError(ErrorManager.ERROR_CANCELLED_OPERATION, false, errorCat);
			}
			else {
				ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.LOAD_CERT);
				Logger.e(ES_GOB_AFIRMA, errorCat.getCode() + " - " + errorCat.getAdminText(), t); //$NON-NLS-1$
				launchError(ErrorManager.ERROR_PKE, true, errorCat);
			}
			return;
		}
		ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.LOAD_CERT);
		Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), t); //$NON-NLS-1$
		launchError(ErrorManager.ERROR_SELECTING_CERTIFICATE, true, errorCat);
	}


	@Override
	public void onStart() {
		super.onStart();
	}

	/** Env&iacute;a los datos indicado a un servlet. En caso de error, cierra la aplicaci&oacute;n.
	 * @param data Datos que se desean enviar. */
	private void sendData(final String data, final boolean critical) {
		new SendDataTask(
			this.parameters.getId(),
			this.parameters.getStorageServletUrl(),
			data,
			this,
			critical
		).execute();
	}

	/** Muestra un mensaje de error y lo env&iacute;a al servidor para que la p&aacute;gina Web
	 * tenga constancia de &eacute;l.
	 * @param errorId Identificador del error.
	 * @param critical <code>true</code> si debe mostrarse el error al usuario, <code>false</code>
	 *                    en caso contrario.
	 * @param errorCat Categor&iacute;a del error
	 */
	private void launchError(final String errorId, final boolean critical, final ErrorCategory errorCat) {
		try {
			sendData(URLEncoder.encode(ErrorManager.genError(errorId, errorCat.getCode(), null), DEFAULT_URL_ENCODING), critical);
		}
		catch (final UnsupportedEncodingException e) {
			// No puede darse, el soporte de UTF-8 es obligatorio
			Logger.e(ES_GOB_AFIRMA,
				"No se ha podido enviar la respuesta al servidor por error en la codificacion " + DEFAULT_URL_ENCODING, e //$NON-NLS-1$
			);
		}
		catch (final Throwable e) {
			Logger.e(ES_GOB_AFIRMA,
				"Error desconocido al enviar el error obtenido al servidor: " + e, e //$NON-NLS-1$
			);
		}
	}

	/** Muestra un mensaje de advertencia al usuario.
	 * @param errorCat Error que se desea mostrar. */
	private void showErrorMessage(final ErrorCategory errorCat) {

		dismissProgressDialog();

		String message = "AA" + errorCat.getCode() + " - " + errorCat.getUserText();

		if (this.messageDialog == null) {
			this.messageDialog = MessageDialog.newInstance(message);
			this.messageDialog.setListener(new CloseActivityDialogAction());
			this.messageDialog.setDialogBuilder(this);
		}

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					WebSelectCertificateActivity.this.getMessageDialog().show(getSupportFragmentManager(), "ErrorDialog"); //$NON-NLS-1$;
				}
				catch (final Exception e) {
					// Si falla el mostrar el error (posiblemente por no disponer de un contexto grafico para mostrarlo)
					// se mostrara en un Toast
					Toast.makeText(WebSelectCertificateActivity.this, message, Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	private void showProgressDialog(final String message) {
		runOnUiThread(
			new Runnable() {
				@Override
				public void run() {
					try {
						setProgressDialog(ProgressDialog.show(WebSelectCertificateActivity.this, "", message, true)); //$NON-NLS-1$
					}
					catch (final Throwable e) {
						Logger.e(ES_GOB_AFIRMA, "No se ha podido mostrar el dialogo de progreso", e); //$NON-NLS-1$
					}
				}
			}
		);
	}

    @Override
    public synchronized void onDownloadingDataSuccess(final byte[] data) {
        Logger.i(ES_GOB_AFIRMA, "Se ha descargado correctamente la configuracion para la seleccion de un certificado"); //$NON-NLS-1$

        // Si hemos tenido que descargar los datos desde el servidor, los desciframos y llamamos
        // al dialogo de seleccion de certificados para la firma
        byte[] decipheredData;
        try {
            decipheredData = CipherDataManager.decipherData(data, this.parameters.getDesKey());
        } catch (final IOException e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
			Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Los datos proporcionados no est&aacute;n correctamente codificados en base 64", e); //$NON-NLS-1$
			showErrorMessage(errorCat);
            return;
        } catch (final GeneralSecurityException e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
			Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Error al descifrar los datos recuperados del servidor para la firma", e); //$NON-NLS-1$
			showErrorMessage(errorCat);
            return;
        } catch (final IllegalArgumentException e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
			Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Los datos recuperados no son un base64 valido", e); //$NON-NLS-1$
			showErrorMessage(errorCat);
            return;
        } catch (final Throwable e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
			Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Error desconocido durante el descifrado de los datos", e); //$NON-NLS-1$
			showErrorMessage(errorCat);
            return;
        }

        Logger.i(ES_GOB_AFIRMA, "Se han descifrado los datos y se inicia su analisis:\n" + new String(decipheredData)); //$NON-NLS-1$

        try {
            this.parameters = ProtocolInvocationUriParser.getParametersToSelectCert(decipheredData, true);
        } catch (final ParameterException e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
			Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Error en los parametros XML de configuracion de firma: " + e, e); //$NON-NLS-1$
			showErrorMessage(errorCat);
            return;
        } catch (final Throwable e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
			Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Error desconocido al analizar los datos descargados desde el servidor", e); //$NON-NLS-1$
			showErrorMessage(errorCat);
            return;
        }

        // Iniciamos la seleccion de certificado
        processSelectionRequest();
    }

    @Override
    public synchronized void onDownloadingDataError(final String msg, final Throwable t) {
		ErrorCategory errorCat = CommunicationErrors.DOWNLOAD_SERVER.get(CommunicationErrors.DOWNLOAD_CONFIG_CERT);
        Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText() + msg, t); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        showErrorMessage(errorCat);
    }

	public void onSelectCertificateChainSuccess(final byte[] certificate) {
		Logger.i(ES_GOB_AFIRMA, "Certificado recuperado correctamente. Se cifra el resultado.");

		// Ciframos si nos dieron clave privada, si no subimos los datos sin cifrar
		final String data;
		if (this.parameters.getDesKey() != null) {
			try {
				data = CipherDataManager.cipherData(certificate, this.parameters.getDesKey());
			}
			catch (final GeneralSecurityException e) {
				ErrorCategory errorCat = InternalSoftwareErrors.CERTIFICATE_SELECTION.get(InternalSoftwareErrors.CYPHERING_CERT);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), e);
				launchError(ErrorManager.ERROR_CIPHERING, true, errorCat);
				return;
			}
			catch (final Throwable e) {
				ErrorCategory errorCat = InternalSoftwareErrors.CERTIFICATE_SELECTION.get(InternalSoftwareErrors.CYPHERING_CERT);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), e); //$NON-NLS-1$
				launchError(ErrorManager.ERROR_CIPHERING, true, errorCat);
				return;
			}
		}
		else {
			data = Base64.encode(certificate, true);
		}

        Logger.i(ES_GOB_AFIRMA, "Certificado cifrado. Se envia al servidor."); //$NON-NLS-1$
		try {
			sendData(data, true);
			Logger.i(ES_GOB_AFIRMA, "Certificado enviado."); //$NON-NLS-1$
		}
		catch (Throwable e) {
			onSendingDataError(e, true);
		}
	}

	@Override
	public void onSendingDataSuccess(final byte[] result, final boolean critical) {
		Logger.i(ES_GOB_AFIRMA, "Resultado del deposito de la firma: " + (result == null ? null : new String(result))); //$NON-NLS-1$
		closeActivity();
	}

	@Override
	public void onSendingDataError(final Throwable error, final boolean critical) {

		Logger.e(ES_GOB_AFIRMA, "Se ejecuta la funcion de error en el envio de datos", error); //$NON-NLS-1$

		if (critical) {
			dismissProgressDialog();
			ErrorCategory errorCat = CommunicationErrors.UPLOAD_SERVER.get(CommunicationErrors.UPLOAD_DATA);
			showErrorMessage(errorCat);
			return;
		}
		closeActivity();
	}

	/** Comprueba si esta abierto el di&aacute;logo de espera y lo cierra en dicho caso. */
	private void dismissProgressDialog() {
		if (this.progressDialog != null) {
			this.progressDialog.dismiss();
		}
	}

	/** Comprueba si esta abierto el di&aacute;logo de mensajes y lo cierra en dicho caso. */
	private void dismissMessageDialog() {
		if (this.messageDialog != null && this.messageDialog.isVisible()) {
					this.messageDialog.dismiss();
		}
	}

	/** Accion para el cierre de la actividad. */
	private class CloseActivityDialogAction implements DialogInterface.OnClickListener {

		CloseActivityDialogAction() {
			// Constructor vacio para evitar el sintetico
		}

		@Override
		public void onClick(final DialogInterface dialog, final int which) {
			closeActivity();
		}
	}

	void closeActivity() {
		// Cerramos a la fuerza para, en siguientes ejecuciones, no se vuelvan a cargar los mismos datos
		finishAffinity();
	}

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_HOME) {
			ErrorCategory errorCat = FunctionalErrors.GENERAL.get(FunctionalErrors.CANCELED_BY_USER);
			launchError(ErrorManager.ERROR_CANCELLED_OPERATION, false, errorCat);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		ErrorCategory errorCat = FunctionalErrors.GENERAL.get(FunctionalErrors.CANCELED_BY_USER);
		launchError(ErrorManager.ERROR_CANCELLED_OPERATION, false, errorCat);
		super.onBackPressed();
	}

	@Override
	protected void onStop() {
		dismissProgressDialog();
		dismissMessageDialog();
		super.onStop();
	}

    @Override
    protected void onDestroy() {
        if (this.downloadFileTask != null) {
            Logger.d(ES_GOB_AFIRMA, "WebSelectCertificateActivity onDestroy: Cancelamos la descarga"); //$NON-NLS-1$
            try {
                this.downloadFileTask.cancel(true);
            }
            catch(final Exception e) {
                Logger.e(ES_GOB_AFIRMA, "No se ha podido cancelar el procedimiento de descarga de la configuracion", e); //$NON-NLS-1$
            }
        }
        super.onDestroy();
    }
}