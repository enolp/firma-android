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

import java.util.Dictionary;
import java.util.Hashtable;

import es.gob.afirma.R;

// Request Error 6XXXXX problemas peticiones: Errores derivados de que no se proporcionen datos en la petición, sean erróneos o incompatibles entre sí
public final class RequestErrors {

	// 6000XX: Error general
	public static final String NOT_SUPPORTED_OPERATION = "NOT_SUPPORTED_OPERATION";
	public static final String REQUEST_PARAM_NOT_VALID = "REQUEST_PARAM_NOT_VALID";
	public static final String ENCODING_CERT = "ENCODING_CERT";
	public static final String INVOCATION_WITHOUT_URL = "INVOCATION_WITHOUT_URL";

    public static final Dictionary<String, ErrorCategory> GENERAL = new Hashtable<>();

	// 6001XX: Peticion de firma
	public static final String NO_DATA_NO_ID = "NO_DATA_NO_ID";
	public static final String FORMAT_NOT_VALID = "FORMAT_NOT_VALID";
	public static final String CANT_DECODE_DATA = "CANT_DECODE_DATA";
	public static final String PARAM_NOT_COMPATIBLE_POLICY = "PARAM_NOT_COMPATIBLE_POLICY";

    public static final Dictionary<String, ErrorCategory> SIGN_REQUEST = new Hashtable<>();

	// 6004XX: Petición de lotes JSON
	public static final String NO_DATA_NO_ID_BATCH = "NO_DATA_NO_ID_BATCH";
	public static final String URL_DOWNLOAD_NOT_FOUND_BATCH = "URL_DOWNLOAD_NOT_FOUND_BATCH";
	public static final String FILE_ID_NOT_FOUND_BATCH = "FILE_ID_NOT_FOUND_BATCH";
	public static final String BATCHPRESIGNERURL_NOT_FOUND_BATCH = "BATCHPRESIGNERURL_NOT_FOUND_BATCH";
	public static final String BATCHPOSTSIGNERURL_NOT_FOUND_BATCH = "BATCHPOSTSIGNERURL_NOT_FOUND_BATCH";
	public static final String ALGORITHM_NOT_FOUND_BATCH = "ALGORITHM_NOT_FOUND_BATCH";
	public static final String JSON_NOT_FORMED_CORRECTLY = "JSON_NOT_FORMED_CORRECTLY";

    public static final Dictionary<String, ErrorCategory> JSON_REQUEST = new Hashtable<>();

	public static void update(Context context) {

		ErrorCategory e600002 = new ErrorCategory(600002, context.getString(R.string.error_bad_params), "La operación no esta soportada");
		ErrorCategory e600006 = new ErrorCategory(600006, context.getString(R.string.error_bad_params), "Los parametros de la peticion no eran validos");
        ErrorCategory e600007 = new ErrorCategory(600007, context.getString(R.string.error_bad_params), "Error en la codificacion del certificado");
        ErrorCategory e600008 = new ErrorCategory(600008, context.getString(R.string.error_bad_params), "Se ha invocado sin URL a la actividad de firma por protocolo");

		GENERAL.put(NOT_SUPPORTED_OPERATION, e600002);
		GENERAL.put(REQUEST_PARAM_NOT_VALID, e600006);
		GENERAL.put(ENCODING_CERT, e600007);
		GENERAL.put(INVOCATION_WITHOUT_URL, e600008);

        ErrorCategory e600100 = new ErrorCategory(600100, context.getString(R.string.error_bad_params), "No se ha recibido los datos en la petición ni el id del fichero a descargar para la operación de firma");
		ErrorCategory e600105 = new ErrorCategory(600105, context.getString(R.string.error_format_not_supported), "El formato de firma no es válido para la operación de firma");
        ErrorCategory e600106 = new ErrorCategory(600106, context.getString(R.string.error_bad_params), "No se ha recibido el algoritmo de firma  para la operación de firmao");
		ErrorCategory e600108 = new ErrorCategory(600108, context.getString(R.string.error_bad_params), "Los datos de la operación no se han podido decodificar para la operación de firma");
        ErrorCategory e600109 = new ErrorCategory(600109, context.getString(R.string.error_signing_config), "Los parametros configurados son incompatibles con la politica de firma");

		SIGN_REQUEST.put(NO_DATA_NO_ID, e600100);
		SIGN_REQUEST.put(FORMAT_NOT_VALID, e600105);
		SIGN_REQUEST.put(NOT_SUPPORTED_OPERATION, e600106);
		SIGN_REQUEST.put(CANT_DECODE_DATA, e600108);
		SIGN_REQUEST.put(PARAM_NOT_COMPATIBLE_POLICY, e600109);

        ErrorCategory e600400 = new ErrorCategory(600400, context.getString(R.string.error_bad_params), "No se ha recibido los datos en la petición ni el id del fichero a descargar para la operación de firma de lotes");
        ErrorCategory e600401 = new ErrorCategory(600401, context.getString(R.string.error_bad_params), "Es necesario descargar la información del servidor intermedio, ha llegado el id de fichero pero no ha llegado la url del servidor de descarga para la operación de firma de lotes");
		ErrorCategory e600403 = new ErrorCategory(600403, context.getString(R.string.error_bad_params), "No se ha recibido el id del fichero a guardar para la operación de firma de lotes");
		ErrorCategory e600405 = new ErrorCategory(600405, context.getString(R.string.error_bad_params), "No se ha recibido el batchpresignerurl en la firma batch");
        ErrorCategory e600406 = new ErrorCategory(600406, context.getString(R.string.error_bad_params), "No se ha recibido el batchpostsignerurl en la firma batch");
		ErrorCategory e600407 = new ErrorCategory(600407, context.getString(R.string.error_bad_params), "El data de la operacion de firma batch no es un JSON valido");
		ErrorCategory e600408 = new ErrorCategory(600408, context.getString(R.string.error_bad_params), "No se ha recibido el algoritmo de firma en la firma batch");

		JSON_REQUEST.put(NO_DATA_NO_ID_BATCH, e600400);
		JSON_REQUEST.put(URL_DOWNLOAD_NOT_FOUND_BATCH, e600401);
		JSON_REQUEST.put(FILE_ID_NOT_FOUND_BATCH, e600403);
		JSON_REQUEST.put(BATCHPRESIGNERURL_NOT_FOUND_BATCH, e600405);
		JSON_REQUEST.put(BATCHPOSTSIGNERURL_NOT_FOUND_BATCH, e600406);
		JSON_REQUEST.put(JSON_NOT_FORMED_CORRECTLY, e600407);
		JSON_REQUEST.put(ALGORITHM_NOT_FOUND_BATCH, e600408);

	}

}
