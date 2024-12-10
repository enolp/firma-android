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


import java.util.Dictionary;
import java.util.Hashtable;

// Request Error 6XXXXX problemas peticiones: Errores derivados de que no se proporcionen datos en la petición, sean erróneos o incompatibles entre sí
public final class RequestErrors {

	// 6000XX: Error general
	public static final String REQUEST_FORMAT = "REQUEST_FORMAT";
	public static final String NO_OPERATION_CODE = "NO_OPERATION_CODE";
	public static final String NOT_SUPPORTED_OPERATION = "NOT_SUPPORTED_OPERATION";
	public static final String NO_ENCRYPTION_KEY = "NO_ENCRYPTION_KEY";
	public static final String REQUEST_PARAM_NOT_VALID = "REQUEST_PARAM_NOT_VALID";
	public static final String INVOCATION_WITHOUT_URL = "INVOCATION_WITHOUT_URL";

	private static final ErrorCategory e600000 = new ErrorCategory(600000, "El formato de la petición es erroneo", "El formato de la petición es erroneo");
	private static final ErrorCategory e600001 = new ErrorCategory(600001, "No ha llegado código de operación en la petición", "No ha llegado código de operación en la petición");
	private static final ErrorCategory e600002 = new ErrorCategory(600002, "La operación no esta soportada", "La operación no esta soportada");
	private static final ErrorCategory e600005 = new ErrorCategory(600005, "Es necesario descargar la información del servidor intermedio y no ha llegado la clave de cifrado", "Es necesario descargar la información del servidor intermedio y no ha llegado la clave de cifrado");
	private static final ErrorCategory e600006 = new ErrorCategory(600006, "MainApplication.getContext().getString(R.string.error_bad_params)", "Los parametros de la peticion no eran validos");
	private static final ErrorCategory e600008 = new ErrorCategory(600008, "Se ha invocado sin URL a la actividad de firma por protocolo", "Se ha invocado sin URL a la actividad de firma por protocolo");

	public static final Dictionary<String, ErrorCategory> GENERAL = new Hashtable<>();
	static {
		GENERAL.put(REQUEST_FORMAT, e600000);
		GENERAL.put(NO_OPERATION_CODE, e600001);
		GENERAL.put(NOT_SUPPORTED_OPERATION, e600002);
		GENERAL.put(NO_ENCRYPTION_KEY, e600005);
		GENERAL.put(REQUEST_PARAM_NOT_VALID, e600006);
		GENERAL.put(INVOCATION_WITHOUT_URL, e600008);
	}

	// 6001XX: Peticion de firma
	public static final String NO_DATA_NO_ID = "NO_DATA_NO_ID";
	public static final String URL_SERVER_NOT_FOUND = "URL_SERVER_NOT_FOUND";
	public static final String URL_SERVLET_TO_SAVE_NOT_FOUND = "URL_SERVLET_TO_SAVE_NOT_FOUND";
	public static final String FILE_ID_NOT_FOUND = "FILE_ID_NOT_FOUND";
	public static final String FORMAT_NOT_FOUND = "FORMAT_NOT_FOUND";
	public static final String ALGORITHM_NOT_FOUND = "ALGORITHM_NOT_FOUND";
	public static final String ALGORITHM_NOT_VALID = "ALGORITHM_NOT_VALID";
	public static final String CANT_DECODE_DATA = "CANT_DECODE_DATA	";

	private static final ErrorCategory e600100 = new ErrorCategory(600100, "No se ha recibido los datos en la petición ni el id del fichero a descargar para la operación de firma", "No se ha recibido los datos en la petición ni el id del fichero a descargar para la operación de firma");
	private static final ErrorCategory e600101 = new ErrorCategory(600101, "Es necesario descargar la información del servidor intermedio, ha llegado el id de fichero pero no ha llegado la url del servidor de descarga para la operación de firma", "Es necesario descargar la información del servidor intermedio, ha llegado el id de fichero pero no ha llegado la url del servidor de descarga para la operación de firma");
	private static final ErrorCategory e600102 = new ErrorCategory(600102, "No se ha recibido la url del servlet para guardar la firma en la operación de firma", "No se ha recibido la url del servlet para guardar la firma en la operación de firma");
	private static final ErrorCategory e600103 = new ErrorCategory(600103, "No se ha recibido el id del fichero a guardar para la operación de firma", "No se ha recibido el id del fichero a guardar para la operación de firma");
	private static final ErrorCategory e600104 = new ErrorCategory(600104, "No se ha recibido el formato de firma para la operación de firma", "No se ha recibido el formato de firma para la operación de firma");
	private static final ErrorCategory e600105 = new ErrorCategory(600105, "El formato de firma no es válido para la operación de firma", "El formato de firma no es válido para la operación de firma");
	private static final ErrorCategory e600106 = new ErrorCategory(600106, "No se ha recibido el algoritmo de firma  para la operación de firma", "No se ha recibido el algoritmo de firma  para la operación de firmao");
	private static final ErrorCategory e600107 = new ErrorCategory(600107, "El algoritmo de firma no es válido para la operación de firma", "El algoritmo de firma no es válido para la operación de firma");
	private static final ErrorCategory e600108 = new ErrorCategory(600108, "Los datos de la operación no se han podido decodificar para la operación de firma", "Los datos de la operación no se han podido decodificar para la operación de firma");

	public static final Dictionary<String, ErrorCategory> SIGN_REQUEST = new Hashtable<>();
	static {
		SIGN_REQUEST.put(NO_DATA_NO_ID, e600100);
		SIGN_REQUEST.put(URL_SERVER_NOT_FOUND, e600101);
		SIGN_REQUEST.put(URL_SERVLET_TO_SAVE_NOT_FOUND, e600102);
		SIGN_REQUEST.put(FILE_ID_NOT_FOUND, e600103);
		SIGN_REQUEST.put(FORMAT_NOT_FOUND, e600104);
		SIGN_REQUEST.put(ALGORITHM_NOT_FOUND, e600105);
		SIGN_REQUEST.put(NOT_SUPPORTED_OPERATION, e600106);
		SIGN_REQUEST.put(ALGORITHM_NOT_VALID, e600107);
		SIGN_REQUEST.put(CANT_DECODE_DATA, e600108);
	}

	// 6002XX: Petición de selección de certificados
	static final String URL_SERVLET_NOT_FOUND_SEND_CERT = "URL_SERVLET_NOT_FOUND_SEND_CERT";
	static final String FILE_ID_NOT_FOUND_SEND_CERT = "FILE_ID_NOT_FOUND_SEND_CERT";

	private static final ErrorCategory e600200 = new ErrorCategory(600200, "No se ha recibido la url del servlet para guardar el certificado en la operación de enviar certificado", "No se ha recibido la url del servlet para guardar el certificado en la operación de enviar certificado");
	private static final ErrorCategory e600201 = new ErrorCategory(600201, "No se ha recibido el id del fichero a guardar para la operación de enviar certificado", "No se ha recibido el id del fichero a guardar para la operación de enviar certificado");

	public static final Dictionary<String, ErrorCategory> SELECT_CERT_REQUEST = new Hashtable<>();
	static {
		SELECT_CERT_REQUEST.put(URL_SERVLET_NOT_FOUND_SEND_CERT, e600200);
		SELECT_CERT_REQUEST.put(FILE_ID_NOT_FOUND_SEND_CERT, e600201);
	}

	// 6003XX: Petición de guardado de datos
	static final String NO_DATA_NO_ID_SAVE_DATA = "NO_DATA_NO_ID_SAVE_DATA";
	static final String URL_NOT_FOUND_SAVE_DATA = "URL_NOT_FOUND_SAVE_DATA";

	private static final ErrorCategory e600300 = new ErrorCategory(600300, "No se ha recibido los datos en la petición ni el id del fichero a descargar para la operación de guardado", "No se ha recibido los datos en la petición ni el id del fichero a descargar para la operación de guardado");
	private static final ErrorCategory e600301 = new ErrorCategory(600301, "Es necesario descargar la información del servidor intermedio, ha llegado el id de fichero pero no ha llegado la url del servidor de descarga para la operación de guardado", "Es necesario descargar la información del servidor intermedio, ha llegado el id de fichero pero no ha llegado la url del servidor de descarga para la operación de guardado");

	public static final Dictionary<String, ErrorCategory> SAVE_DATA_REQUEST = new Hashtable<>();
	static {
		SAVE_DATA_REQUEST.put(NO_DATA_NO_ID_SAVE_DATA, e600300);
		SAVE_DATA_REQUEST.put(URL_NOT_FOUND_SAVE_DATA, e600301);
	}

	// 6004XX: Petición de lotes JSON
	public static final String NO_DATA_NO_ID_BATCH = "NO_DATA_NO_ID_BATCH";
	public static final String URL_DOWNLOAD_NOT_FOUND_BATCH = "URL_DOWNLOAD_NOT_FOUND_BATCH";
	public static final String URL_SERVLET_NOT_FOUND_BATCH = "URL_SERVLET_NOT_FOUND_BATCH";
	public static final String FILE_ID_NOT_FOUND_BATCH = "FILE_ID_NOT_FOUND_BATCH";
	public static final String DAT_NOT_FOUND_BATCH = "DAT_NOT_FOUND_BATCH";
	public static final String BATCHPRESIGNERURL_NOT_FOUND_BATCH = "BATCHPRESIGNERURL_NOT_FOUND_BATCH";
	public static final String BATCHPOSTSIGNERURL_NOT_FOUND_BATCH = "BATCHPOSTSIGNERURL_NOT_FOUND_BATCH";
	public static final String DATA_NOT_VALID_JSON = "DATA_NOT_VALID_JSON";
	public static final String ALGORITHM_NOT_FOUND_BATCH = "ALGORITHM_NOT_FOUND_BATCH";
	public static final String JSON_NOT_FORMED_CORRECTLY = "JSON_NOT_FORMED_CORRECTLY";

	private static final ErrorCategory e600400 = new ErrorCategory(600400, "No se ha recibido los datos en la petición ni el id del fichero a descargar para la operación de firma de lotes", "No se ha recibido los datos en la petición ni el id del fichero a descargar para la operación de firma de lotes");
	private static final ErrorCategory e600401 = new ErrorCategory(600401, "Es necesario descargar la información del servidor intermedio, ha llegado el id de fichero pero no ha llegado la url del servidor de descarga para la operación de firma de lotes", "Es necesario descargar la información del servidor intermedio, ha llegado el id de fichero pero no ha llegado la url del servidor de descarga para la operación de firma de lotes");
	private static final ErrorCategory e600402 = new ErrorCategory(600402, "No se ha recibido la url del servlet para guardar la firma en la operación de firma de lotes", "No se ha recibido la url del servlet para guardar la firma en la operación de firma de lotes");
	private static final ErrorCategory e600403 = new ErrorCategory(600403, "No se ha recibido el id del fichero a guardar para la operación de firma de lotes", "No se ha recibido el id del fichero a guardar para la operación de firma de lotes");
	private static final ErrorCategory e600404 = new ErrorCategory(600404, "No se ha recibido el dat para en la firma batch", "No se ha recibido el dat para en la firma batch");
	private static final ErrorCategory e600405 = new ErrorCategory(600405, "No se ha recibido el batchpresignerurl en la firma batch", "No se ha recibido el batchpresignerurl en la firma batch");
	private static final ErrorCategory e600406 = new ErrorCategory(600406, "No se ha recibido el batchpostsignerurl en la firma batch", "No se ha recibido el batchpostsignerurl en la firma batch");
	private static final ErrorCategory e600407 = new ErrorCategory(600407, "El data de la operación de firma batch no es un JSON válido", "El data de la operación de firma batch no es un JSON válido");
	private static final ErrorCategory e600408 = new ErrorCategory(600408, "No se ha recibido el algoritmo de firma en la firma batch", "No se ha recibido el algoritmo de firma en la firma batch");
	private static final ErrorCategory e600409 = new ErrorCategory(600409, "El JSON de definicion de lote de firmas no esta formado correctamente", "El JSON de definicion de lote de firmas no esta formado correctamente");

	public static final Dictionary<String, ErrorCategory> JSON_REQUEST = new Hashtable<>();
	static {
		JSON_REQUEST.put(NO_DATA_NO_ID_BATCH, e600400);
		JSON_REQUEST.put(URL_DOWNLOAD_NOT_FOUND_BATCH, e600401);
		JSON_REQUEST.put(URL_SERVLET_NOT_FOUND_BATCH, e600402);
		JSON_REQUEST.put(FILE_ID_NOT_FOUND_BATCH, e600403);
		JSON_REQUEST.put(DAT_NOT_FOUND_BATCH, e600404);
		JSON_REQUEST.put(BATCHPRESIGNERURL_NOT_FOUND_BATCH, e600405);
		JSON_REQUEST.put(BATCHPOSTSIGNERURL_NOT_FOUND_BATCH, e600406);
		JSON_REQUEST.put(DATA_NOT_VALID_JSON, e600407);
		JSON_REQUEST.put(ALGORITHM_NOT_FOUND_BATCH, e600408);
		JSON_REQUEST.put(JSON_NOT_FORMED_CORRECTLY, e600409);
	}

}
