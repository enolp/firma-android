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

/// ThirdParty Software Error 3XXXXX problemas software de terceros: Errores devueltos por bibliotecas o sistemas externos de los que desconocemos los detalles del error, ya que depende de ellos
public final class ThirdPartyErrors {

	// 3001XX: JMulticard
	public static final String UNKNOWN_OR_NOT_SUPPORTED_CARD = "UNKNOWN_OR_NOT_SUPPORTED_CARD";
	public static final String CANT_CONNECT_CARD = "CANT_CONNECT_CARD";
	public static final String ERROR_INITIALIZING_CARD = "ERROR_INITIALIZING_CARD";
	public static final String UNEXPECTED = "UNEXPECTED";
	public static final String INCORRECT_PIN = "INCORRECT_PIN";
	public static final String BLOCKED_CARD = "BLOCKED_CARD";
	public static final String CAN_VALIDATION = "CAN_VALIDATION";
	public static final String NOT_HAVE_CERTIFICATE = "NOT_HAVE_CERTIFICATE";

	public static final Dictionary<String, ErrorCategory> JMULTICARD = new Hashtable<>();

	// 3004XX: Servidor trifasico
	public static final String HTTP_PRESIGN = "HTTP_PRESIGN";
	public static final String HTTP_POSTSIGN = "HTTP_POSTSIGN";

	public static final Dictionary<String, ErrorCategory> TRIPHASE_SERVER = new Hashtable<>();

	public static void update(Context context) {
		ErrorCategory e300100 = new ErrorCategory(300100, context.getString(R.string.unsupported_card), "La tarjeta identificada en el lector es desconocida o no esta soportada");
		ErrorCategory e300102 = new ErrorCategory(300102, context.getString(R.string.error_reading_dnie), "No se ha podido conectar con la tarjeta");
		ErrorCategory e300103 = new ErrorCategory(300103, context.getString(R.string.error_reading_dnie), "La conexion con la tarjeta no esta inicializada");
		ErrorCategory e300104 = new ErrorCategory(300104, context.getString(R.string.error_reading_dnie), "Ocurrió un error inesperado durante la operacion");
		ErrorCategory e300105 = new ErrorCategory(300105, context.getString(R.string.error_msc_pin), context.getString(R.string.incorrect_pin));
		ErrorCategory e300106 = new ErrorCategory(300106, context.getString(R.string.error_dni_blocked_dlg), "Tarjeta bloqueada");
		ErrorCategory e300108 = new ErrorCategory(300108, context.getString(R.string.nfc_card_initializing_error), "Error durante la validacion del CAN");
		ErrorCategory e300110 = new ErrorCategory(300110, context.getString(R.string.error_reading_dnie), "No se dispone de certificado de firma digital");

		JMULTICARD.put(UNKNOWN_OR_NOT_SUPPORTED_CARD, e300100);
		JMULTICARD.put(CANT_CONNECT_CARD, e300102);
		JMULTICARD.put(ERROR_INITIALIZING_CARD, e300103);
		JMULTICARD.put(UNEXPECTED, e300104);
		JMULTICARD.put(INCORRECT_PIN, e300105);
		JMULTICARD.put(BLOCKED_CARD, e300106);
		JMULTICARD.put(CAN_VALIDATION, e300108);
		JMULTICARD.put(NOT_HAVE_CERTIFICATE, e300110);

		ErrorCategory e300400 = new ErrorCategory(300400, context.getString(R.string.error_signing), "Error HTTP al realizar la operación de prefirma");
		ErrorCategory e300404 = new ErrorCategory(300404, context.getString(R.string.error_signing), "Error HTTP al realizar la operación de posfirma");

		TRIPHASE_SERVER.put(HTTP_PRESIGN, e300400);
		TRIPHASE_SERVER.put(HTTP_POSTSIGN, e300404);

	}

}
