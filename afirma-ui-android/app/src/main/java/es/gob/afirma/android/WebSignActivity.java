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

import static es.gob.afirma.signers.pades.common.PdfExtraParams.HEADLESS;
import static es.gob.afirma.signers.pades.common.PdfExtraParams.VISIBLE_SIGNATURE;
import static es.gob.afirma.signers.pades.common.PdfExtraParams.VISIBLE_SIGNATURE_VALUE_OPTIONAL;
import static es.gob.afirma.signers.pades.common.PdfExtraParams.VISIBLE_SIGNATURE_VALUE_WANT;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.security.KeyChainException;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Properties;

import es.gob.afirma.R;
import es.gob.afirma.android.crypto.AndroidHttpManager;
import es.gob.afirma.android.crypto.CipherDataManager;
import es.gob.afirma.android.crypto.SelectKeyAndroid41BugException;
import es.gob.afirma.android.crypto.SignResult;
import es.gob.afirma.android.errors.CommunicationErrors;
import es.gob.afirma.android.errors.ErrorCategory;
import es.gob.afirma.android.errors.ErrorManager;
import es.gob.afirma.android.errors.FunctionalErrors;
import es.gob.afirma.android.errors.InternalSoftwareErrors;
import es.gob.afirma.android.errors.RequestErrors;
import es.gob.afirma.android.exceptions.IncompatibleFormatException;
import es.gob.afirma.android.gui.AppConfig;
import es.gob.afirma.android.gui.CustomDialog;
import es.gob.afirma.android.gui.DownloadFileTask;
import es.gob.afirma.android.gui.DownloadFileTask.DownloadDataListener;
import es.gob.afirma.android.gui.SendDataTask;
import es.gob.afirma.android.gui.SendDataTask.SendDataListener;
import es.gob.afirma.android.util.FileUtil;
import es.gob.afirma.core.AOException;
import es.gob.afirma.core.AOUnsupportedSignFormatException;
import es.gob.afirma.core.misc.http.UrlHttpManagerFactory;
import es.gob.afirma.core.misc.protocol.ParameterException;
import es.gob.afirma.core.misc.protocol.ProtocolInvocationUriParser;
import es.gob.afirma.core.misc.protocol.UrlParametersToSign;
import es.gob.afirma.core.signers.AOSignConstants;
import es.gob.afirma.core.signers.ExtraParamsProcessor;
import es.gob.afirma.signers.cades.CAdESExtraParams;
import es.gob.afirma.signers.pades.common.PdfExtraParams;

/** Actividad dedicada a la firma de los datos recibidos en la entrada mediante un certificado
 * del almac&eacute;n central seleccionado por el usuario. */
public final class WebSignActivity extends SignFragmentActivity implements DownloadDataListener,
                                                                           SendDataListener {
	private static final char CERT_SIGNATURE_SEPARATOR = '|';

	private static final String ES_GOB_AFIRMA = "es.gob.afirma"; //$NON-NLS-1$

	/** Juego de carateres UTF-8. */
	private static final String DEFAULT_URL_ENCODING = "UTF-8"; //$NON-NLS-1$

	private final static String EXTRA_RESOURCE_TITLE = "es.gob.afirma.android.title"; //$NON-NLS-1$
	private final static String EXTRA_RESOURCE_EXT = "es.gob.afirma.android.exts"; //$NON-NLS-1$
	private final static String EXTRA_RESOURCE_EXCLUDE_DIRS = "es.gob.afirma.android.excludedDirs"; //$NON-NLS-1$

	private final static String INTENT_ENTRY_ACTION = "es.gob.afirma.android.SIGN_SERVICE"; //$NON-NLS-1$
	
	/** C&oacute;digo de petici&oacute;n usado para invocar a la actividad que selecciona el fichero para firmar. */
	private static final int SELECT_FILE_REQUEST_CODE = 102;

	/** C&oacute;digo de solicitud de firma visible. */
	private final static int REQUEST_VISIBLE_SIGN_PARAMS = 105;

	/** Error cargando PDF para firma visible. */
	final static int ERROR_REQUEST_VISIBLE_SIGN = 106;

	private boolean fileChooserOpenned;

	private UrlParametersToSign parameters;

	private DownloadFileTask downloadFileTask = null;

	private CustomDialog messageDialog;

	private String fileName;

	private String filePath;

	private boolean isRequiredVisibleSignature;

	private boolean dataSelectedByUser;

	CustomDialog getMessageDialog() {
		return this.messageDialog;
	}

	private ProgressDialog progressDialog = null;
	void setProgressDialog(final ProgressDialog pd) {
		this.progressDialog = pd;
	}

    private static final int REQUEST_WRITE_STORAGE = 112;

	static {
		// Instalamos el gestor de descargas que deseamos utilizar en las invocaciones por
		// protocolo a la aplicacion
		UrlHttpManagerFactory.install(new AndroidHttpManager());
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
			this.parameters = ProtocolInvocationUriParser.getParametersToSign(getIntent().getDataString(), true);
		}
		catch (final ParameterException e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
			Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText() + e, e); //$NON-NLS-1$
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

		try {
			processSignRequest();
		}
		catch (final Throwable e) {
			ErrorCategory errorSigning = InternalSoftwareErrors.GENERAL.get(InternalSoftwareErrors.ERROR_SIGNING);
			Logger.e(ES_GOB_AFIRMA, errorSigning.getCode() + " - " + errorSigning.getAdminText(), e); //$NON-NLS-1$
			onSigningError(KeyStoreOperation.SIGN, errorSigning.getCode() + " - " + errorSigning.getUserText(), e);
		}
	}

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Logger.i("es.gob.afirma", "Concedido permiso de escritura en memoria");
				try {
					processSignRequest();
				}
				catch (final Throwable e) {
					ErrorCategory errorSigning = InternalSoftwareErrors.GENERAL.get(InternalSoftwareErrors.ERROR_SIGNING);
					Logger.e(ES_GOB_AFIRMA, errorSigning.getCode() + " - " + errorSigning.getAdminText() + e, e); //$NON-NLS-1$
					onSigningError(KeyStoreOperation.SIGN, errorSigning.getCode() + " - " + errorSigning.getUserText(), e);
				}
			}
			else {
				onSigningError(KeyStoreOperation.SIGN, "No se concedieron permisos de acceso a disco", null);
			}
        }
    }

	/** Inicia el proceso de firma con los parametros previamente configurados. */
	private void processSignRequest() throws ExtraParamsProcessor.IncompatiblePolicyException {

		// Si no tenemos datos ni un fichero de descargar, cargaremos un fichero del dispositivo
		if (this.parameters.getData() == null && this.parameters.getFileId() == null) {

			this.dataSelectedByUser = true;

			Logger.i(ES_GOB_AFIRMA, "Se va a cargar un fichero local para la firma"); //$NON-NLS-1$
			// Comprobamos que no este ya abierta la pantalla de seleccion, ya que puede ser un caso
			// de cancelacion de la seleccion de fichero, en cuyo caso no deseamos que se vuelva a abrir
			if (!this.fileChooserOpenned) {
				openSelectFileActivity();
			}
			else {
				this.fileChooserOpenned = false;
			}
		}

		// Si no se han indicado datos y si el identificador de un fichero remoto, lo recuperamos para firmarlos
		else if (this.parameters.getData() == null && this.parameters.getFileId() != null) {

			this.dataSelectedByUser = false;

			Logger.i(ES_GOB_AFIRMA, "Se van a descargar los datos desde servidor con el identificador: " + this.parameters.getFileId()); //$NON-NLS-1$
			if (this.parameters.getRetrieveServletUrl() != null) {
				this.downloadFileTask = new DownloadFileTask(
						this.parameters.getFileId(),
						this.parameters.getRetrieveServletUrl(),
						this
				);
				this.downloadFileTask.execute();
			} else {
				ErrorCategory errorCat = RequestErrors.SIGN_REQUEST.get(RequestErrors.NO_DATA_NO_ID);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText());
				launchError(ErrorManager.ERROR_BAD_PARAMETERS, errorCat.getUserText(), true, errorCat);
			}
		}

		// Si tenemos los datos, cargamos un certificado para firmarlos
		else {
			Logger.i(ES_GOB_AFIRMA, "Se inicia la firma de los datos obtenidos por parametro"); //$NON-NLS-1$
			showProgressDialog(getString(R.string.dialog_msg_signning));

			// Expandimos los parametros extra si se utiliza uno de los formatos de firma
			// monofasicos compatibles. Si no, sera el servidor trifasico el que debera expandirlos
			if (AOSignConstants.SIGN_FORMAT_CADES.equals(this.parameters.getSignatureFormat()) ||
					AOSignConstants.SIGN_FORMAT_PADES.equals(this.parameters.getSignatureFormat())) {
				this.parameters.expandExtraParams();
			}

			// Si se indica el formato PAdES y no se trata de un PDF se lanzara un error
			if ((AOSignConstants.SIGN_FORMAT_PADES.equals(this.parameters.getSignatureFormat())
				|| AOSignConstants.SIGN_FORMAT_PADES_TRI.equals(this.parameters.getSignatureFormat())
				|| AOSignConstants.SIGN_FORMAT_PDF.equals(this.parameters.getSignatureFormat())
				|| AOSignConstants.SIGN_FORMAT_PDF_TRI.equals(this.parameters.getSignatureFormat()))
				&& !FileUtil.isPdfFile(this.parameters.getData())) {
					ErrorCategory errorCat;
					if (this.dataSelectedByUser) {
						errorCat = InternalSoftwareErrors.OPERATION_SIGN.get(InternalSoftwareErrors.USER_SELECT_NOT_PDF);
					} else {
						errorCat = InternalSoftwareErrors.OPERATION_SIGN.get(InternalSoftwareErrors.PADES_NO_PDF_SIGN);
					}
					onSigningError(KeyStoreOperation.SIGN, "Formato de firma incompatible con tipo de archivo", new IncompatibleFormatException(errorCat));
			}

			// Comprobamos si se debe mostrar el dialogo para seleccionar el area de firma visible o no
			boolean showPdfSignVisiblePreview = checkSignVisiblePreviewExtraParams();

			if (showPdfSignVisiblePreview) {
				// Previsualizacion de PDF
				Intent intent = new Intent(this, PdfSelectPreviewActivity.class);
				intent.putExtra("filePath", this.filePath);
				if (isRequiredVisibleSignature) {
					intent.putExtra("isRequiredVisibleSignature", true);
				}
				startActivityForResult(intent, REQUEST_VISIBLE_SIGN_PARAMS);
			} else {
				sign(
						this.parameters.getOperation(),
						this.parameters.getData(),
						this.parameters.getSignatureFormat(),
						this.parameters.getSignatureAlgorithm(),
						false,
						this.parameters.getExtraParams());
			}
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	/** Identifica las extensiones de los ficheros que se pueden firmar con un formato de firma.
	 * @param signatureFormat Formato de firma.
	 * @return Extensiones. */
	private static String identifyExts(final String signatureFormat) {

		if (AOSignConstants.SIGN_FORMAT_PADES.equals(signatureFormat) ||
			AOSignConstants.SIGN_FORMAT_PADES_TRI.equals(signatureFormat)) {
				return ".pdf"; //$NON-NLS-1$
		}
		return null;
	}

	/** Env&iacute;a los datos indicado a un servlet. En caso de error, cierra la aplicaci&oacute;n.
	 * @param data Datos que se desean enviar. */
	private void sendData(final String data, final boolean critical) {

		Logger.i(ES_GOB_AFIRMA, "Se almacena el resultado en el servidor con el Id: " + this.parameters.getId()); //$NON-NLS-1$

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
		launchError(errorId, null, critical, errorCat);
	}

	/** Muestra un mensaje de error y lo env&iacute;a al servidor para que la p&aacute;gina Web
	 * tenga constancia de &eacute;l.
	 * @param errorId Identificador del error.
	 * @param errorMsg Mensaje de error.
	 * @param critical <code>true</code> si debe mostrarse el error al usuario, <code>false</code>
	 *                    en caso contrario.
	 * @param errorCat Categor&iacute;a del error
	 */
	private void launchError(final String errorId, final String errorMsg, final boolean critical, final ErrorCategory errorCat) {

		try {
			// Devolvemos el error diractamente o a traves del servidor intermedio segun si se nos
			// llamo desde una App o no
			if (INTENT_ENTRY_ACTION.equals(getIntent().getAction())){
				Logger.i(ES_GOB_AFIRMA, "Devolvemos el error a la app solicitante"); //$NON-NLS-1$
				sendErrorByIntent(errorId, ErrorManager.genError(errorId, errorCat.getCode(), errorMsg));
			}
			else {
				sendData(URLEncoder.encode(ErrorManager.genError(errorId, errorCat.getCode(), errorMsg), DEFAULT_URL_ENCODING), critical);
			}
		}
		catch (final UnsupportedEncodingException e) {
			ErrorCategory err = RequestErrors.SIGN_REQUEST.get(RequestErrors.CANT_DECODE_DATA);
			// No puede darse, el soporte de UTF-8 es obligatorio
			Logger.e(ES_GOB_AFIRMA,
					"AA" + err.getCode() + " - " + err.getAdminText() + DEFAULT_URL_ENCODING, e //$NON-NLS-1$
			);
		}
		catch (final Throwable e) {
			ErrorCategory err = CommunicationErrors.GENERAL.get(CommunicationErrors.UNKNOWN_ERROR);
			Logger.e(ES_GOB_AFIRMA,
					"AA" + err.getCode() + " - " + err.getAdminText() + e, e //$NON-NLS-1$
			);
		}
	}

	/** Muestra un mensaje de advertencia al usuario.
	 * @param errorCat Error que se desea mostrar. */
	private void showErrorMessage(final ErrorCategory errorCat) {
		showErrorMessage(null, errorCat);
	}

	/** Muestra un mensaje de advertencia al usuario.
	 * @param title T&iacute;tulo de di&aacute;logo error.
	 * @param errorCat Error que se desea mostrar. */
	private void showErrorMessage(final String title, final ErrorCategory errorCat) {

		dismissProgressDialog();

		String dlgTitle;
		if (title != null) {
			dlgTitle = title;
		} else {
			dlgTitle = getString(R.string.error_ocurred);
		}

		String message = "AA" + errorCat.getCode() + " - " + errorCat.getUserText();

		if (this.messageDialog == null) {
			this.messageDialog = new CustomDialog(this, R.drawable.warn_icon, dlgTitle, message,
					getString(R.string.ok));
		}

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					WebSignActivity.this.getMessageDialog().show(); //$NON-NLS-1$;
				}
				catch (final Exception e) {
					// Si falla el mostrar el error (posiblemente por no disponer de un contexto grafico para mostrarlo)
					// se mostrara en un
					Toast.makeText(WebSignActivity.this, message, Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	/** Muestra un mensaje de advertencia al usuario.
	 * @param message Mensaje que se desea mostrar. */
	private void showErrorMessageOnToast(final String message) {

		dismissProgressDialog();
		dismissMessageDialog();

		runOnUiThread(
			new Runnable() {
				@Override
				public void run() {
					Toast.makeText(WebSignActivity.this, message, Toast.LENGTH_LONG).show();
				}
			}
		);
	}

	@Override
	protected void onSigningError(final KeyStoreOperation op, final String msg, final Throwable t) {

		if (op == KeyStoreOperation.LOAD_KEYSTORE) {
			ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.LOAD_KEYSTORE);
			Log.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText() + msg, t);
			launchError(ErrorManager.ERROR_ESTABLISHING_KEYSTORE, true, errorCat);
			return;
		}
		if (op == KeyStoreOperation.SELECT_CERTIFICATE) {

			if (t instanceof SelectKeyAndroid41BugException) {
				ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.ALIAS_NOT_VALID);
				Log.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), t);
				launchError(ErrorManager.ERROR_PKE_ANDROID_4_1, true, errorCat);
			}
			else if (t instanceof KeyChainException) {
				ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.LOAD_KEY);
				Log.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), t);
				launchError(ErrorManager.ERROR_PKE, true, errorCat);
			}
			else if (t instanceof PendingIntent.CanceledException) {
				ErrorCategory errorCat = FunctionalErrors.GENERAL.get(FunctionalErrors.CANCELED_BY_USER);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), t); //$NON-NLS-1$
				launchError(ErrorManager.ERROR_CANCELLED_OPERATION, false, errorCat);
			}
			else {
				ErrorCategory errorCat = InternalSoftwareErrors.LOAD_CERTS.get(InternalSoftwareErrors.UNEXPECTED_RECOVERING_KEY);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText() + msg, t); //$NON-NLS-1$
				launchError(ErrorManager.ERROR_PKE, true, errorCat);
			}
			return;
		}
		if (op == KeyStoreOperation.SIGN) {
			if (t instanceof AOUnsupportedSignFormatException) {
				ErrorCategory errorCat = RequestErrors.SIGN_REQUEST.get(RequestErrors.FORMAT_NOT_VALID);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText() + t); //$NON-NLS-1$
				showErrorMessage(errorCat);
				launchError(ErrorManager.ERROR_NOT_SUPPORTED_FORMAT, true, errorCat);
			}
			else if (t instanceof ExtraParamsProcessor.IncompatiblePolicyException) {
				ErrorCategory errorCat = RequestErrors.SIGN_REQUEST.get(RequestErrors.PARAM_NOT_COMPATIBLE_POLICY);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText() + t); //$NON-NLS-1$
				showErrorMessage(errorCat);
				launchError(ErrorManager.ERROR_BAD_PARAMETERS, true, errorCat);
			}
			else if (t instanceof IncompatibleFormatException) {
				ErrorCategory errorCat = ((IncompatibleFormatException) t).getErrorCat();
				Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText() + t); //$NON-NLS-1$
				showErrorMessage(errorCat);
				launchError(ErrorManager.ERROR_INVALID_DATA, true, errorCat);
			}
			else if (t instanceof PendingIntent.CanceledException) {
				ErrorCategory errorCat = FunctionalErrors.GENERAL.get(FunctionalErrors.CANCELED_BY_USER);
				launchError(ErrorManager.ERROR_CANCELLED_OPERATION, false, errorCat);
			}
			else if (t instanceof AOException) {
				ErrorCategory errorSigning = InternalSoftwareErrors.GENERAL.get(InternalSoftwareErrors.ERROR_SIGNING);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorSigning.getCode() + " - " + errorSigning.getAdminText(), t); //$NON-NLS-1$
				launchError(ErrorManager.ERROR_SIGNING, t.getMessage(), true, errorSigning);
			}
			else {
				ErrorCategory errorSigning = InternalSoftwareErrors.GENERAL.get(InternalSoftwareErrors.ERROR_SIGNING);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorSigning.getCode() + " - " + errorSigning.getAdminText() + msg, t); //$NON-NLS-1$
				launchError(ErrorManager.ERROR_SIGNING, true, errorSigning);
			}
			return;
		}
		ErrorCategory errorSigning = InternalSoftwareErrors.GENERAL.get(InternalSoftwareErrors.ERROR_SIGNING);
		Logger.e(ES_GOB_AFIRMA, "AA" + errorSigning.getCode() + " - " + errorSigning.getAdminText() + msg, t); //$NON-NLS-1$
		launchError(ErrorManager.ERROR_SIGNING, true, errorSigning);
	}

	private void showProgressDialog(final String message) {
		runOnUiThread(
			new Runnable() {
				@Override
				public void run() {
					try {
						setProgressDialog(ProgressDialog.show(WebSignActivity.this, "", message, true)); //$NON-NLS-1$
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

        Logger.i(ES_GOB_AFIRMA, "Se ha descargado correctamente la configuracion de firma almacenada en servidor"); //$NON-NLS-1$
        Logger.i(ES_GOB_AFIRMA, "Cantidad de datos descargada: " + (data == null ? -1 : data.length)); //$NON-NLS-1$

        // Si hemos tenido que descargar los datos desde el servidor, los desciframos y llamamos
        // al dialogo de seleccion de certificados para la firma
        final byte[] decipheredData;
        try {
            decipheredData = CipherDataManager.decipherData(data, this.parameters.getDesKey());
        }
        catch (final IOException e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
            Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Los datos proporcionados no est&aacute;n correctamente codificados en base 64", e); //$NON-NLS-1$
            showErrorMessage(errorCat);
            return;
        }
        catch (final GeneralSecurityException e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
            Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Error al descifrar los datos recuperados del servidor para la firma", e); //$NON-NLS-1$
            showErrorMessage(errorCat);
            return;
        }
        catch (final IllegalArgumentException e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
            Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Los datos recuperados no son un base64 valido", e); //$NON-NLS-1$
            showErrorMessage(errorCat);
            return;
        }
        catch (final Throwable e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
            Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Error desconocido durante el descifrado de los datos", e); //$NON-NLS-1$
            showErrorMessage(errorCat);
            return;
        }

        Logger.i(ES_GOB_AFIRMA, "Se han descifrado los datos y se inicia su analisis:\n" + new String(decipheredData)); //$NON-NLS-1$

        try {
            this.parameters = ProtocolInvocationUriParser.getParametersToSign(decipheredData, true);
        }
        catch (final ParameterException e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
            Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Error en los parametros XML de configuracion de firma: " + e, e); //$NON-NLS-1$
            showErrorMessage(errorCat);
            return;
        }
        catch (final Throwable e) {
			ErrorCategory errorCat = RequestErrors.GENERAL.get(RequestErrors.REQUEST_PARAM_NOT_VALID);
            Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - Error desconocido al analizar los datos descargados desde el servidor", e); //$NON-NLS-1$
            showErrorMessage(errorCat);
            return;
        }

        // Comprobamos que en los datos descargados de servidor esten los datos a firmar y,
        // en caso contrario, se permite al usuario cargarlos de fichero. Si ya tenemos los
        // datos, los firmamos directamente
        if (this.parameters.getData() == null) {
            Logger.i(ES_GOB_AFIRMA, "Se va a cargar un fichero local para la firma"); //$NON-NLS-1$
            // Comprobamos que no este ya abierta la pantalla de seleccion, ya que puede ser un caso
            // de cancelacion de la seleccion de fichero, en cuyo caso no deseamos que se vuelva a abrir
            if (!this.fileChooserOpenned) {
                openSelectFileActivity();
            }
            else {
                this.fileChooserOpenned = false;
            }
        }
        else {
            Logger.i(ES_GOB_AFIRMA, "Se inicia la firma de los datos descargados desde el servidor"); //$NON-NLS-1$
            showProgressDialog(getString(R.string.dialog_msg_signning));
            try {
                sign(
					this.parameters.getOperation(),
					this.parameters.getData(),
					this.parameters.getSignatureFormat(),
					this.parameters.getSignatureAlgorithm(),
					false,
					this.parameters.getExtraParams()
				);
            }
            catch (final Exception e) {
				ErrorCategory errorSigning = InternalSoftwareErrors.GENERAL.get(InternalSoftwareErrors.ERROR_SIGNING);
                Logger.e(ES_GOB_AFIRMA, "AA" + errorSigning.getCode() + " - " + errorSigning.getAdminText(), e); //$NON-NLS-1$
                showErrorMessage(errorSigning);
            }
        }
	}

	@Override
	public synchronized void onDownloadingDataError(final String msg, final Throwable t) {
		ErrorCategory errorCat = CommunicationErrors.DOWNLOAD_SERVER.get(CommunicationErrors.DOWNLOAD_SIGN);
		Logger.e(ES_GOB_AFIRMA,"AA" + errorCat.getCode() + " Error durante la descarga de la configuracion de firma guardada en servidor:" + msg + (t != null ? ": " + t : ""), t); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		showErrorMessage(errorCat);
	}

	@Override
	public void onSigningSuccess(final SignResult signature) {

		Logger.i(ES_GOB_AFIRMA, "Firma generada correctamente. Se cifra el resultado.");

		// Ciframos si nos dieron clave privada, si no subimos los datos sin cifrar
		String data;
		if (this.parameters.getDesKey() != null && this.parameters.getDesKey().length > 0) {
			try {
				data = CipherDataManager.cipherData(
						signature.getSignature(),
						this.parameters.getDesKey()
				);
			} catch (final GeneralSecurityException e) {
				ErrorCategory errorCat = InternalSoftwareErrors.OPERATION_SIGN.get(InternalSoftwareErrors.CYPHERING_SIGN);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), e);
				launchError(ErrorManager.ERROR_CIPHERING, true, errorCat);
				return;
			} catch (final Throwable e) {
				ErrorCategory errorCat = InternalSoftwareErrors.OPERATION_SIGN.get(InternalSoftwareErrors.CYPHERING_SIGN);
				Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), e); //$NON-NLS-1$
				launchError(ErrorManager.ERROR_CIPHERING, true, errorCat);
				return;
			}
		} else {
			data = Base64.encodeToString(signature.getSignature(), Base64.URL_SAFE);
		}

		byte[] encodedCert = null;
		String signingCert;
		try {
			encodedCert = signature.getSigningCertificate().getEncoded();
			if (this.parameters.getDesKey() != null && this.parameters.getDesKey().length > 0) {
				signingCert = CipherDataManager.cipherData(
						encodedCert,
						this.parameters.getDesKey()
				);
			}
			else {
				signingCert = Base64.encodeToString(encodedCert, Base64.URL_SAFE);
			}
		}
		catch (final GeneralSecurityException e) {
			ErrorCategory errorCat = InternalSoftwareErrors.OPERATION_SIGN.get(InternalSoftwareErrors.CYPHERING_CERT);
			Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), e);
			signingCert = null;
		}

		// Responderemos con ls tupls CERTIFICADO|FIRMA
		String responseText = signingCert + CERT_SIGNATURE_SEPARATOR + data;

		String appName = this.parameters.getAppName();

		String signType = SIGN_TYPE_WEB;

		// Devolvemos el error diractamente o a traves del servidor intermedio segun si se nos
		// llamo desde una App o no
		if (getIntent().getAction() != null && getIntent().getAction().equals(INTENT_ENTRY_ACTION)){
			Logger.i(ES_GOB_AFIRMA, "Devolvemos datos a la app solicitante"); //$NON-NLS-1$
			// Registramos los datos sobre la firma realizada
			if (appName == null) {
				appName = getCallingPackage();
			}
			signType = SIGN_TYPE_APP;
			sendDataByIntent(encodedCert, signature.getSignature());
		}
		else {

			try {
				Logger.i(ES_GOB_AFIRMA, "Enviando firma..."); //$NON-NLS-1$
				sendData(responseText, true);
			}
			catch (final Throwable e) {
				ErrorCategory errorCat = CommunicationErrors.UPLOAD_SERVER.get(CommunicationErrors.UPLOAD_DATA);
				Logger.e(ES_GOB_AFIRMA,
						"AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), e //$NON-NLS-1$
				);
				onSendingDataError(e, true);
			}
		}

		String originalFileName = null;
		if (this.fileName != null) {
			File originalFile = new File(this.fileName);
			originalFileName = originalFile.getName();
		}

		// Registramos los datos de la firma realizada
		saveSignRecord(signType, originalFileName, appName);
	}

	private void sendDataByIntent (byte[] cert, byte[] signature) {
		Intent result = new Intent();
		if (cert != null) {
			result.putExtra("cert", cert);
		}
		result.putExtra("signature", signature);
		Activity activity = getParent() != null ? getParent() : this;
		activity.setResult(RESULT_OK, result);
		finish();
	}

	private void sendErrorByIntent (String errorId, String errorMsg) {
		Intent result = new Intent();
		result.putExtra("errorId", errorId);
		if (errorMsg != null){
			result.putExtra("errorMsg", errorMsg);
		}
		Activity activity = getParent() != null ? getParent() : this;
		activity.setResult(RESULT_CANCELED, result);
		finish();
	}

	@Override
	public void onSendingDataSuccess(final byte[] result, final boolean critical) {
		Logger.i(ES_GOB_AFIRMA, "Resultado del deposito de la firma: " + (result == null ? null : new String(result))); //$NON-NLS-1$
		dismissProgressDialog();
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
		if (this.messageDialog != null && this.messageDialog.isShowing()) {
					this.messageDialog.hide();
					this.messageDialog = null;
		}
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

		if (requestCode == SELECT_FILE_REQUEST_CODE) {
			// Si el usuario cancelo la seleccion del fichero a firmar
			if (resultCode == RESULT_CANCELED) {
				ErrorCategory errorCat = FunctionalErrors.GENERAL.get(FunctionalErrors.CANCELED_BY_USER);
				launchError(ErrorManager.ERROR_CANCELLED_OPERATION, false, errorCat);
				return;
			}
			else if (resultCode == RESULT_OK) {

				byte[] fileContent;
				File dataFile;
				try {
					if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
						final Uri dataUri = data.getData();
						this.fileName = getFilename(dataUri);
						dataFile = FileUtil.from(this, dataUri);
						fileContent = readDataFromUri(dataUri);
						if (dataFile.exists()) {
							this.filePath = dataFile.getAbsolutePath();
						}
					} else {
						this.fileName = data.getStringExtra(FileChooserActivity.RESULT_DATA_STRING_FILENAME);
						dataFile = new File(this.fileName);
						this.filePath = dataFile.getAbsolutePath();
						fileContent = readDataFromFile(dataFile);
					}
				}
				catch (final OutOfMemoryError e) {
					ErrorCategory errorCat = InternalSoftwareErrors.OPERATION_SIGN.get(InternalSoftwareErrors.OUT_OF_MEMORY);
					showErrorMessage(errorCat);
					Logger.e(ES_GOB_AFIRMA, "AA" + errorCat.getCode() + " - " + errorCat.getAdminText(), e);  //$NON-NLS-1$
					return;
				}
				catch (final IOException e) {
					Logger.e(ES_GOB_AFIRMA, "Error al cargar el fichero, se dara al usuario la posibilidad de reintentar", e); //$NON-NLS-1$
					String msg = this.fileName != null
							? getString(R.string.error_loading_selected_file) + this.fileName
							: getString(R.string.error_loading_selected_undefined_file);
					showErrorMessageOnToast(msg);
					openSelectFileActivity();
					return;
				}
				catch (final Throwable e) {
					Logger.e(ES_GOB_AFIRMA, "Error desconocido al cargar el fichero " + this.fileName, e); //$NON-NLS-1$
					String msg = this.fileName != null
							? getString(R.string.error_loading_selected_file) + this.fileName
							: getString(R.string.error_loading_selected_undefined_file);
					showErrorMessageOnToast(msg);
					return;
				}

				this.parameters.setData(fileContent);

				try {
					processSignRequest();
				}
				catch (final Throwable e) {
					ErrorCategory errorSigning = InternalSoftwareErrors.GENERAL.get(InternalSoftwareErrors.ERROR_SIGNING);
					Logger.e(ES_GOB_AFIRMA, "AA" + errorSigning.getCode() + " - " + errorSigning.getAdminText(), e); //$NON-NLS-1$
					showErrorMessageOnToast(errorSigning.getUserText());
					return;
				}
			}
		}
		else if (requestCode == REQUEST_VISIBLE_SIGN_PARAMS) {

			if (resultCode == RESULT_OK) {

				Properties extraParams = new Properties();
				extraParams.setProperty(CAdESExtraParams.MODE, "implicit");

				if(data.hasExtra(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_LOWER_LEFTX)
						&& data.hasExtra(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_LOWER_LEFTY)
						&& data.hasExtra(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_UPPER_RIGHTX)
						&& data.hasExtra(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_UPPER_RIGHTY)) {
					// Ofuscacion de los datos del certificado de firma
					final boolean obfuscate = AppConfig.isPadesObfuscateCertInfo(this);
					extraParams.setProperty(PdfExtraParams.OBFUSCATE_CERT_DATA, Boolean.toString(obfuscate));

					extraParams.setProperty(PdfExtraParams.SIGNATURE_PAGES, data.getStringExtra(PdfExtraParams.SIGNATURE_PAGES));
					extraParams.setProperty(PdfExtraParams.LAYER2_TEXT, getString(R.string.pdf_visible_sign_template));

					extraParams.setProperty(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_LOWER_LEFTX, data.getStringExtra(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_LOWER_LEFTX));
					extraParams.setProperty(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_LOWER_LEFTY, data.getStringExtra(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_LOWER_LEFTY));
					extraParams.setProperty(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_UPPER_RIGHTX, data.getStringExtra(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_UPPER_RIGHTX));
					extraParams.setProperty(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_UPPER_RIGHTY, data.getStringExtra(PdfExtraParams.SIGNATURE_POSITION_ON_PAGE_UPPER_RIGHTY));
				} else if (isRequiredVisibleSignature) {
					ErrorCategory errorCat = FunctionalErrors.GENERAL.get(FunctionalErrors.CANCELED_BY_USER);
					launchError(ErrorManager.ERROR_CANCELLED_OPERATION, false, errorCat);
					return;
				}

				sign(
						this.parameters.getOperation(),
						this.parameters.getData(),
						this.parameters.getSignatureFormat(),
						this.parameters.getSignatureAlgorithm(),
						false,
						this.parameters.getExtraParams());
			}
			else if (resultCode == RESULT_CANCELED) {
				ErrorCategory errorCat = FunctionalErrors.GENERAL.get(FunctionalErrors.CANCELED_BY_USER);
				launchError(ErrorManager.ERROR_CANCELLED_OPERATION, false, errorCat);
				return;
			} else if (resultCode == ERROR_REQUEST_VISIBLE_SIGN) {
				ErrorCategory errorCat = InternalSoftwareErrors.OPERATION_SIGN.get(InternalSoftwareErrors.LOADING_LOCAL_FILE);
				showErrorMessage(errorCat);
				launchError(ErrorManager.ERROR_INVALID_DATA, true, errorCat);
				return;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void openSelectFileActivity() {

		// Si no hay permisos de acceso al almacenamiento, se piden
		boolean hasWritePermission;
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			hasWritePermission = true;
		}
		else {
			hasWritePermission = ContextCompat.checkSelfPermission(
					this,
					Manifest.permission.WRITE_EXTERNAL_STORAGE
			) == PackageManager.PERMISSION_GRANTED;
		}
		if (!hasWritePermission) {
			Logger.i(ES_GOB_AFIRMA, "No se tiene permiso de escritura en memoria y lo pedimos");
			ActivityCompat.requestPermissions(
					this,
					new String[]{
							Manifest.permission.WRITE_EXTERNAL_STORAGE
					},
					REQUEST_WRITE_STORAGE
			);
		}
		// Si hay permisos, se habre el dialogo de carga de ficheros
		else {
			Intent intent;
			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
				intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("*/*"); //$NON-NLS-1$
			} else {
				intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setClass(this, FileChooserActivity.class);
				intent.putExtra(EXTRA_RESOURCE_TITLE, getString(R.string.title_activity_choose_sign_file));
				intent.putExtra(EXTRA_RESOURCE_EXCLUDE_DIRS, FileSystemConstants.COMMON_EXCLUDED_DIRS);
				final String exts = identifyExts(this.parameters.getSignatureFormat());
				if (exts != null) {
					intent.putExtra(EXTRA_RESOURCE_EXT, exts);
				}
			}
			this.fileChooserOpenned = true;
			startActivityForResult(intent, SELECT_FILE_REQUEST_CODE);
		}
	}

	private byte[] readDataFromFile(File dataFile) throws IOException {
		int n;
		final byte[] buffer = new byte[1024];
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (final InputStream is = new FileInputStream(dataFile)) {
			while ((n = is.read(buffer)) > 0) {
				baos.write(buffer, 0, n);
			}
		}
		return baos.toByteArray();
	}

	private String getFilename(Uri uri) {
		String result = null;
		if (uri.getScheme().equals("content")) {
			try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
				if (cursor != null && cursor.moveToFirst()) {
					int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
					result = idx > -1 ? cursor.getString(idx) : null;
				}
			}
		}
		if (result == null) {
			result = uri.getPath();
			int cut = result.lastIndexOf('/');
			if (cut != -1) {
				result = result.substring(cut + 1);
			}
		}
		return result;
	}

	private byte[] readDataFromUri(Uri uri) throws IOException {
		int n;
		final byte[] buffer = new byte[1024];
		final ByteArrayOutputStream baos;
		try (InputStream is = getContentResolver().openInputStream(uri)) {
			baos = new ByteArrayOutputStream();
			while ((n = is.read(buffer)) > 0) {
				baos.write(buffer, 0, n);
			}
		}
		return baos.toByteArray();
	}

	/** Comprueba si en los extraParams se indica que si debe mostrar la pantalla para realizar la firma visible.
	 * @return  Devuelve true en caso de que se deba mostrar o false en caso contrario. */
	private boolean checkSignVisiblePreviewExtraParams() {
		Properties extraParams = parameters.getExtraParams();
		String visibleSignature = null;
		if (extraParams.containsKey(VISIBLE_SIGNATURE)) {
			visibleSignature = (String) extraParams.get(VISIBLE_SIGNATURE);
			if (VISIBLE_SIGNATURE_VALUE_WANT.equalsIgnoreCase(visibleSignature)) {
				isRequiredVisibleSignature = true;
			}
		}
		boolean headless = extraParams.containsKey(HEADLESS) ? Boolean.parseBoolean( (String) extraParams.get(HEADLESS))
				: false;

		// Comprobamos si se debe mostrar el dialogo para seleccionar el area de firma visible o no
		if ((AOSignConstants.SIGN_FORMAT_PADES.equals(this.parameters.getSignatureFormat())
				|| AOSignConstants.SIGN_FORMAT_PADES_TRI.equals(this.parameters.getSignatureFormat())
				|| AOSignConstants.SIGN_FORMAT_PDF.equals(this.parameters.getSignatureFormat())
				|| AOSignConstants.SIGN_FORMAT_PDF_TRI.equals(this.parameters.getSignatureFormat()))
				&& (VISIBLE_SIGNATURE_VALUE_WANT.equalsIgnoreCase(visibleSignature) || VISIBLE_SIGNATURE_VALUE_OPTIONAL.equalsIgnoreCase(visibleSignature))
				&& !headless) {
			return true;
		}

		return false;
	}

	/** Accion para el cierre de la actividad. */
	private final class CloseActivityDialogAction implements DialogInterface.OnClickListener {

		CloseActivityDialogAction() {
			// Constructor vacio para evitar el sintetico
		}

		@Override
		public void onClick(final DialogInterface dialog, final int which) {
			closeActivity();
		}
	}

	void closeActivity() {
		Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("CLOSE_ACTIVITY", true);
		startActivity(intent);
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
			Logger.d(ES_GOB_AFIRMA, "WebSignActivity onDestroy: Cancelamos la descarga"); //$NON-NLS-1$
			try {
				this.downloadFileTask.cancel(true);
			}
			catch(final Exception e) {
				Logger.e(ES_GOB_AFIRMA, "No se ha podido cancelar el procedimiento de descarga de los datos", e); //$NON-NLS-1$
			}
		}
		super.onDestroy();
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(LocaleHelper.onAttach(base));
	}

}