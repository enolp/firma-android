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

// Functional Error 5XXXXX problemas funcionales: Errores relacionados con la operativa de la funcionalidad. Por ejemplo, que se haya pedido firmar en PAdES un fichero no PDF
public final class FunctionalErrors {

	// 500XXX: Error general
	public static final String CANCELED_BY_USER = "CANCELED_BY_USER";

	private static final ErrorCategory e500001 = new ErrorCategory(500001, "Operación cancelada por el usuario", "Operación cancelada por el usuario");

	public static final Dictionary<String, ErrorCategory> GENERAL = new Hashtable<>();
	static {
		GENERAL.put(CANCELED_BY_USER, e500001);
	}

	// 501XXX: Operacion de firma
	public static final String NO_CERTIFICATES = "NO_CERTIFICATES";

	private static final ErrorCategory e501001 = new ErrorCategory(501001, "Error en la operación, no hay certificados", "Error en la operación, no hay certificados");

	public static final Dictionary<String, ErrorCategory> SIGN_OPERATION = new Hashtable<>();
	static {
		SIGN_OPERATION.put(NO_CERTIFICATES, e501001);
	}

}
