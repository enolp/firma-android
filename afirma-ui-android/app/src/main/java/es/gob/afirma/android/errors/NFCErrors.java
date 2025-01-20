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

public final class NFCErrors {

	// 102XXX: Error tarjetas NFC
	public static final String RESET_NFC = "RESET_NFC";

	public static final Dictionary<String, ErrorCategory> NFC_CARDS = new Hashtable<>();

	public static void update(Context context) {
		ErrorCategory e102000 = new ErrorCategory(102000, context.getString(R.string.not_completed_request), "Error al resetear la tarjeta NFC");
		NFC_CARDS.put(RESET_NFC, e102000);
	}

}
