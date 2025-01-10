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

// Functional Error 5XXXXX problemas funcionales: Errores relacionados con la operativa de la funcionalidad. Por ejemplo, que se haya pedido firmar en PAdES un fichero no PDF
public final class FunctionalErrors {

	// 500XXX: Error general
	public static final String CANCELED_BY_USER = "CANCELED_BY_USER";
	public static final Dictionary<String, ErrorCategory> GENERAL = new Hashtable<>();

	// 501XXX: Operacion de firma
	public static final String NO_CERTIFICATES = "NO_CERTIFICATES";
	public static final Dictionary<String, ErrorCategory> SIGN_OPERATION = new Hashtable<>();

	public static void update(Context context) {

		ErrorCategory e500001 = new ErrorCategory(500001, context.getString(R.string.operation_cancelled), "Operacion cancelada por el usuario");
		GENERAL.put(CANCELED_BY_USER, e500001);

		ErrorCategory e501001 = new ErrorCategory(501001, context.getString(R.string.error_title_keystore_empty), "Error en la operacion, no hay certificados");
		SIGN_OPERATION.put(NO_CERTIFICATES, e501001);

	}

}
