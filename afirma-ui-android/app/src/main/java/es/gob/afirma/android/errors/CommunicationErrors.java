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

// Communication Error 4XXXXX problemas comunicaciones: Errores de comunicación con sistemas externos.
public final class CommunicationErrors {

	// 400XXX: Error general
	public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";

	private static final ErrorCategory e400000 = new ErrorCategory(400000, "Error desconocido al enviar el error obtenido al servidor", "Error desconocido al enviar el error obtenido al servidor");

	public static final Dictionary<String, ErrorCategory> GENERAL = new Hashtable<>();
	static {
		GENERAL.put(UNKNOWN_ERROR, e400000);
	}

	// 4011XX: Servidor intermedio (descarga)
	public static final String DOWNLOAD_SIGN = "DOWNLOAD_SIGN";

	private static final ErrorCategory e401100 = new ErrorCategory(401100, "Error en la descarga de la firma", "Error en la descarga de la firma");

	public static final Dictionary<String, ErrorCategory> DOWNLOAD_SERVER = new Hashtable<>();
	static {
		DOWNLOAD_SERVER.put(DOWNLOAD_SIGN, e401100);
	}

	// 4012XX: Servidor intermedio (subida)
	private static final ErrorCategory e401200 = new ErrorCategory(401200, "Error en la descarga de la firma", "Error en la descarga de la firma");

	public static final Dictionary<String, ErrorCategory> UPLOAD_SERVER = new Hashtable<>();
	static {
		UPLOAD_SERVER.put(DOWNLOAD_SIGN, e401200);
	}

	// 4013XX: Servidor trifasico prefirma
	static final String CONNECT_PRESIGN = "CONNECT_PRESIGN";

	private static final ErrorCategory e401300 = new ErrorCategory(401300, "Error de conexion con el servidor trifasico al hacer la prefirma", "Error de conexion con el servidor trifasico al hacer la prefirma");

	public static final Dictionary<String, ErrorCategory> TRIPHASE_SERVER_PRESIGN = new Hashtable<>();
	static {
		TRIPHASE_SERVER_PRESIGN.put(CONNECT_PRESIGN, e401300);
	}

	// 4014XX: Servidor trifasico postfirma
	static final String CONNECT_POSTSIGN = "CONNECT_POSTSIGN";

	private static final ErrorCategory e401400 = new ErrorCategory(401400, "Error de conexion con el servidor trifasico al hacer la posfirma", "Error de conexion con el servidor trifasico al hacer la posfirma");

	public static final Dictionary<String, ErrorCategory> TRIPHASE_SERVER_POSTSIGN = new Hashtable<>();
	static {
		TRIPHASE_SERVER_POSTSIGN.put(CONNECT_POSTSIGN, e401400);
	}

	// 4015XX: Servidor trifasico prefirma lote
	static final String CONNECT_PRESIGN_BATCH = "CONNECT_PRESIGN_BATCH";

	private static final ErrorCategory e401500 = new ErrorCategory(401500, "Error de conexion con el servidor trifasico al hacer la prefirma batch", "Error de conexion con el servidor trifasico al hacer la prefirma batch");

	public static final Dictionary<String, ErrorCategory> TRIPHASE_SERVER_PRESIGN_BATCH = new Hashtable<>();
	static {
		TRIPHASE_SERVER_PRESIGN_BATCH.put(CONNECT_PRESIGN_BATCH, e401500);
	}

	// 4016XX: Servidor trifasico posfirma lote
	static final String CONNECT_POSTSIGN_BATCH = "CONNECT_POSTSIGN_BATCH";

	private static final ErrorCategory e401600 = new ErrorCategory(401600, "Error de conexion con el servidor trifasico al hacer la posfirma batch", "Error de conexion con el servidor trifasico al hacer la posfirma batch");

	public static final Dictionary<String, ErrorCategory> TRIPHASE_SERVER_POSTSIGN_BATCH = new Hashtable<>();
	static {
		TRIPHASE_SERVER_POSTSIGN_BATCH.put(CONNECT_POSTSIGN_BATCH, e401600);
	}

}
