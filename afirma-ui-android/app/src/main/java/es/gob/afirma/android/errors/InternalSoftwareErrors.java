/* Copyright (C) 2011 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * Date: 11/01/11
 * You may contact the copyright holder at: soporte.afirma5@mpt.es
 */

package es.gob.afirma.android.errors;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Dictionary;
import java.util.Hashtable;

// Internal Software Error 2XXXXX - Problemas software internos: Errores en el funcionamiento interno de la aplicacion.
public final class InternalSoftwareErrors extends AppCompatActivity {

	static Context context;

	// 2000XXL: Errores general
	public static final String SOFTWARE_GENERAL = "SOFTWARE_GENERAL";
	public static final String CANT_SAVE_SIGN_RECORD = "CANT_SAVE_SIGN_RECORD";
	public static final String ERROR_SIGNING = "ERROR_SIGNING";

	private static final ErrorCategory e200000 = new ErrorCategory(200000, "Error general de software", "Error general de software");
	private static final ErrorCategory e200001 = new ErrorCategory(200001, "La firma se ha realizado correctamente pero no se ha podido guardar en el histórico de firmas", "La firma se ha realizado correctamente pero no se ha podido guardar en el histórico de firmas");
	private static final ErrorCategory e200002 = new ErrorCategory(200002, "MainApplication.getContext().getString(R.string.error_signing)", "Error durante la firma");

	public static final Dictionary<String, ErrorCategory> GENERAL = new Hashtable<>();
	static {
		GENERAL.put(SOFTWARE_GENERAL, e200000);
		GENERAL.put(CANT_SAVE_SIGN_RECORD, e200001);
		GENERAL.put(ERROR_SIGNING, e200002);
	}

	// 2001XX: Operación de firma
	public static final String ENCRYPTING_SIGN = "ENCRYPTING_SIGN";
	public static final String ENCRYPTING_CERT = "ENCRYPTING_CERT";
	public static final String LOADING_LOCAL_FILE = "LOADING_LOCAL_FILE";
	public static final String SIGNING_FILE_WITH_CERT = "SIGNING_FILE_WITH_CERT";
	public static final String SIGNING_FILE_WITH_DNIE = "SIGNING_FILE_WITH_DNIE";
	public static final String OUT_OF_MEMORY = "OUT_OF_MEMORY";

	private static final ErrorCategory e200100 = new ErrorCategory(200100, "Error al cifrar la firma para enviarla al servidor intermedio", "Error al cifrar la firma para enviarla al servidor intermedio");
	private static final ErrorCategory e200101 = new ErrorCategory(200101, "Error al cifrar el certificado para enviarlo al servidor intermedio", "Error al cifrar el certificado para enviarlo al servidor intermedio");
	private static final ErrorCategory e200102 = new ErrorCategory(200102, "MainApplication.getContext().getString(R.string.error_loading_selected_file)", "Error al cargar el fichero local para realizar la firma");
	private static final ErrorCategory e200103 = new ErrorCategory(200103, "Error realizando la firma del fichero con certificado", "Error realizando la firma del fichero con certificado");
	private static final ErrorCategory e200104 = new ErrorCategory(200104, "Error realizando la firma del fichero con DNIe", "Error realizando la firma del fichero con DNIe");
	private static final ErrorCategory e200105 = new ErrorCategory(200105, "getString(R.string.file_read_out_of_memory)", "Error de memoria al cargar el fichero");

	public static final Dictionary<String, ErrorCategory> OPERATION_SIGN = new Hashtable<>();
	static {
		OPERATION_SIGN.put(ENCRYPTING_SIGN, e200100);
		OPERATION_SIGN.put(ENCRYPTING_CERT, e200101);
		OPERATION_SIGN.put(LOADING_LOCAL_FILE, e200102);
		OPERATION_SIGN.put(SIGNING_FILE_WITH_CERT, e200103);
		OPERATION_SIGN.put(SIGNING_FILE_WITH_DNIE, e200104);
		OPERATION_SIGN.put(OUT_OF_MEMORY, e200105);
	}

	// 2002XX: Operacion de seleccion de certificados
	static final String CYPHERING_CERT = "CYPHERING_CERT";
	private static final ErrorCategory e200200 = new ErrorCategory(200200, "Error al cifrar el certificado para enviarlo al servidor intermedio", "Error al cifrar el certificado para enviarlo al servidor intermedio");

	public static final Dictionary<String, ErrorCategory> CERTIFICATE_SELECTION = new Hashtable<>();
	static {
		CERTIFICATE_SELECTION.put(CYPHERING_CERT, e200200);
	}

	// 2003XX: Operacion de guardado de datos
	public static final String DATA_NOT_BASE64 = "DATA_NOT_BASE64";
	public static final String SAVING_DATA_DISK = "SAVING_DATA_DISK";
	public static final String NO_DEVICE_STORE = "NO_DEVICE_STORE";
	public static final String CANT_OBTAIN_DATA_FLOW = "NO_DEVICE_STORE";

	private static final ErrorCategory e200300 = new ErrorCategory(200300, "Los datos recibidos para el guardado de datos no estan en Base64", "Los datos recibidos para el guardado de datos no estan en Base64");
	private static final ErrorCategory e200301 = new ErrorCategory(200301, "MainApplication.getContext().getString(R.string.error_saving_signature)", "No se ha podido guardar el fichero en disco");
	private static final ErrorCategory e200302 = new ErrorCategory(200302, "MainApplication.getContext().getString(R.string.error_no_device_to_store)", "No se ha encontrado donde guardar la firma generada");
	private static final ErrorCategory e200303 = new ErrorCategory(200303, "MainApplication.getContext().getString(R.string.error_saving_signature)", "No se pudo obtener el flujo para el guardado de los datos");


	public static final Dictionary<String, ErrorCategory> SAVING_DATA = new Hashtable<>();
	static {
		SAVING_DATA.put(DATA_NOT_BASE64, e200300);
		SAVING_DATA.put(SAVING_DATA_DISK, e200301);
		SAVING_DATA.put(NO_DEVICE_STORE, e200302);
		SAVING_DATA.put(CANT_OBTAIN_DATA_FLOW, e200303);
	}

	// 2004XX: Operacion de lotes JSON
	static final String CYPHERING_SIGN_INFO_TO_SEND = "CYPHERING_SIGN_INFO_TO_SEND";
	static final String CYPHERING_CERT_TO_SEND = "CYPHERING_CERT_TO_SEND";
	static final String SIGN_WITH_CERT = "SIGN_WITH_CERT";
	static final String SIGN_WITH_DNIE = "SIGN_WITH_DNIE";
	static final String CONVERT_TO_BASE64_POSTSIGN_WITH_SIGN_ERROR = "CONVERT_TO_BASE64_POSTSIGN_WITH_SIGN_ERROR";
	static final String CONVERT_TO_BASE64_POSTSIGN = "CONVERT_TO_BASE64_POSTSIGN";
	static final String CONVERT_TO_BASE64_POSTSIGN_ALL_SIGN_ERROR = "CONVERT_TO_BASE64_POSTSIGN_ALL_SIGN_ERROR";
	static final String SERIALIZE_JSON_RESPONSE_POSTSIGN = "SERIALIZE_JSON_RESPONSE_POSTSIGN";
	private static final ErrorCategory e200400 = new ErrorCategory(200400, "Error al cifrar la información de las firmas del lote para enviarla al servidor intermedio", "Error al cifrar la información de las firmas del lote para enviarla al servidor intermedio");
	private static final ErrorCategory e200401 = new ErrorCategory(200401, "Error al cifrar el certificado para enviarlo al sevidor intermedio", "Error al cifrar el certificado para enviarlo al sevidor intermedio");
	private static final ErrorCategory e200404 = new ErrorCategory(200404, "Error realizando la firma batch con certificado", "Error realizando la firma batch con certificado");
	private static final ErrorCategory e200405 = new ErrorCategory(200405, "Error realizando la firma batch con DNIe", "Error realizando la firma batch con DNIe");
	private static final ErrorCategory e200406 = new ErrorCategory(200406, "Error al convertir a base64 los datos para enviarlos a la posfirma (con alguna firma erronea)", "Error al convertir a base64 los datos para enviarlos a la posfirma (con alguna firma erronea)");
	private static final ErrorCategory e200407 = new ErrorCategory(200407, "Error al convertir a base64 las los datos de la firma para enviarlos a la posfirma", "Error al convertir a base64 las los datos de la firma para enviarlos a la posfirma");
	private static final ErrorCategory e200408 = new ErrorCategory(200408, "Error al convertir a base64 los datos para enviarlos a la posfirma (todos las firmas erronéas)", "Error al convertir a base64 los datos para enviarlos a la posfirma (todos las firmas erronéas)");
	private static final ErrorCategory e200409 = new ErrorCategory(200409, "Error al serializar la respuesta JSON de la posfirma para poder enviarla al servidor intermedio", "Error al serializar la respuesta JSON de la posfirma para poder enviarla al servidor intermedio");

	public static final Dictionary<String, ErrorCategory> BATCH_JSON = new Hashtable<>();
	static {
		BATCH_JSON.put(CYPHERING_SIGN_INFO_TO_SEND, e200400);
		BATCH_JSON.put(CYPHERING_CERT_TO_SEND, e200401);
		BATCH_JSON.put(SIGN_WITH_CERT, e200404);
		BATCH_JSON.put(SIGN_WITH_DNIE, e200405);
		BATCH_JSON.put(CONVERT_TO_BASE64_POSTSIGN_WITH_SIGN_ERROR, e200406);
		BATCH_JSON.put(CONVERT_TO_BASE64_POSTSIGN, e200407);
		BATCH_JSON.put(CONVERT_TO_BASE64_POSTSIGN_ALL_SIGN_ERROR, e200408);
		BATCH_JSON.put(SERIALIZE_JSON_RESPONSE_POSTSIGN, e200409);
	}

	// 2010XX: Carga de ficheros
	static final String LOAD_FILE_TO_SIGN = "LOAD_FILE_TO_SIGN";
	private static final ErrorCategory e201000 = new ErrorCategory(201000, "Error en la carga de fichero local para firmar", "Error en la carga de fichero local para firmar");

	public static final Dictionary<String, ErrorCategory> LOAD_FILES = new Hashtable<>();
	static {
		LOAD_FILES.put(LOAD_FILE_TO_SIGN, e201000);
	}

	// 2011XX: Carga/listado de certificados
	public static final String LOAD_CERT_TO_IMPORT = "LOAD_CERT_TO_IMPORT";
	public static final String LOAD_KEYSTORE = "LOAD_KEYSTORE";
	public static final String ALIAS_NOT_VALID = "ALIAS_NOT_VALID";
	public static final String LOAD_KEY = "LOAD_KEY";
	public static final String USER_NOT_SELECT_CERT = "USER_NOT_SELECT_CERT";
	public static final String UNEXPECED_RECOVERING_KEY = "UNEXPECED_RECOVERING_KEY";

	private static final ErrorCategory e201100 = new ErrorCategory(201100, "Error en la carga de certificado para importar", "Error en la carga de certificado para importar");
	private static final ErrorCategory e201101 = new ErrorCategory(201101, "Error al cargar el almacen de certificados", "Error al cargar el almacen de certificados");
	private static final ErrorCategory e201102 = new ErrorCategory(201102, "Error al cargar el certificado, posiblemente relacionado por usar un alias de certificado no valido", "Error al cargar el certificado, posiblemente relacionado por usar un alias de certificado no valido");
	private static final ErrorCategory e201103 = new ErrorCategory(201103, "Error al cargar la clave del certificado", "Error al cargar la clave del certificado");
	private static final ErrorCategory e201104 = new ErrorCategory(201104, "El usuario no selecciono un certificado", "El usuario no selecciono un certificado");
	private static final ErrorCategory e201105 = new ErrorCategory(201105, "Error inesperado al recuperar la clave del certificado de firma", "Error inesperado al recuperar la clave del certificado de firma");

	public static final Dictionary<String, ErrorCategory> LOAD_CERTS = new Hashtable<>();
	static {
		LOAD_CERTS.put(LOAD_CERT_TO_IMPORT, e201100);
		LOAD_CERTS.put(LOAD_KEYSTORE, e201101);
		LOAD_CERTS.put(ALIAS_NOT_VALID, e201102);
		LOAD_CERTS.put(LOAD_KEY, e201103);
		LOAD_CERTS.put(USER_NOT_SELECT_CERT, e201104);
		LOAD_CERTS.put(UNEXPECED_RECOVERING_KEY, e201105);
	}
}
