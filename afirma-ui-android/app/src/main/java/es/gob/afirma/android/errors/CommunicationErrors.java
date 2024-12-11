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

// Communication Error 4XXXXX problemas comunicaciones: Errores de comunicación con sistemas externos.
public final class CommunicationErrors {

	// 400XXX: Error general
	public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
	public static final String COMMUNICATION_WITH_SERVICE = "COMMUNICATION_WITH_SERVICE";

    public static final Dictionary<String, ErrorCategory> GENERAL = new Hashtable<>();

	// 4011XX: Servidor intermedio (descarga)
	public static final String DOWNLOAD_SIGN = "DOWNLOAD_SIGN";
	public static final String DOWNLOAD_CONFIG_CERT = "DOWNLOAD_CONFIG_CERT";

    public static final Dictionary<String, ErrorCategory> DOWNLOAD_SERVER = new Hashtable<>();

	public static final String UPLOAD_DATA = "UPLOAD_DATA";
    public static final Dictionary<String, ErrorCategory> UPLOAD_SERVER = new Hashtable<>();


	public static void update(Context context) {

        ErrorCategory e400000 = new ErrorCategory(400000, context.getString(R.string.error_sending_data), "Error desconocido al enviar el error obtenido al servidor");
		ErrorCategory e400001 = new ErrorCategory(400001, context.getString(R.string.error_sending_data), "Error durante la descarga de la configuracion para la seleccion de certificado");

		GENERAL.put(UNKNOWN_ERROR, e400000);
		GENERAL.put(COMMUNICATION_WITH_SERVICE, e400001);

        ErrorCategory e401100 = new ErrorCategory(401100, context.getString(R.string.error_sending_data), "Error en la descarga de la firma");
		ErrorCategory e401101 = new ErrorCategory(401101, context.getString(R.string.error_loading_cert), "Error durante la descarga de la configuracion para la seleccion de certificado");

		DOWNLOAD_SERVER.put(DOWNLOAD_SIGN, e401100);
		DOWNLOAD_SERVER.put(DOWNLOAD_CONFIG_CERT, e401101);

        // 4012XX: Servidor intermedio (subida)
        ErrorCategory e401200 = new ErrorCategory(401200, context.getString(R.string.error_sending_data), "Error en el envio de datos");
		UPLOAD_SERVER.put(UPLOAD_DATA, e401200);

	}

}
