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

public final class NFCErrors {

	// 102XXX: Error tarjetas NFC
	public static final String RESET_NFC = "RESET_NFC";
	public static final String COMMAND_NFC = "COMMAND_NFC";
	public static final String FUNCTION_NOT_SUPPORTED = "FUNCTION_NOT_SUPPORTED";
	public static final String SECURITY_RESTRICTION = "SECURITY_RESTRICTION";
	public static final String INVALID_PARAM = "INVALID_PARAM";
	public static final String INVALID_PARAM_LONG = "INVALID_PARAM_LONG";
	public static final String OUT_LIMIT_PARAM = "OUT_LIMIT_PARAM";
	public static final String DISABLED_RADIO = "DISABLED_RADIO";
	public static final String LOST_CONNECTION_TAG = "LOST_CONNECTION_TAG";
	public static final String TRY_LIMIT = "TRY_LIMIT";
	public static final String INVALID_RESPONSE_TAG = "INVALID_RESPONSE_TAG";
	public static final String INVALID_TRANSMISSION_SESSION = "INVALID_TRANSMISSION_SESSION";
	public static final String NOT_CONNECTED_TAG = "NOT_CONNECTED_TAG";
	public static final String PACKET_TOO_LONG = "PACKET_TOO_LONG";
	public static final String SESSION_CANCELLED_MANUALLY = "SESSION_CANCELLED_MANUALLY";
	public static final String SESSION_EXPIRED_INACTIVITY = "SESSION_EXPIRED_INACTIVITY";
	public static final String UNEXPECTED_CLOSED_SESSION = "UNEXPECTED_CLOSED_SESSION";
	public static final String BUSY_SYSTEM = "BUSY_SYSTEM";
	public static final String CLOSED_SESSION_AFTER_READ_FIRST_TAG = "CLOSED_SESSION_AFTER_READ_FIRST_TAG";
	public static final String INVALID_CONFIG_COMMANDS_SENT = "INVALID_CONFIG_COMMANDS_SENT";
	public static final String NFC_TAG_NOT_WRITABLE = "NFC_TAG_NOT_WRITABLE";
	public static final String FAILED_UPDATING_TAG = "FAILED_UPDATING_TAG";
	public static final String INSUFFICIENT_SPACE_TAG = "INSUFFICIENT_SPACE_TAG";
	public static final String NDEF_LONG_ZERO = "NDEF_LONG_ZERO";
	public static final String UNKNOWN_ERROR_CONNECTING = "UNKNOWN_ERROR_CONNECTING";

	private static final ErrorCategory e102000 = new ErrorCategory(102000, "Error al resetear la tarjeta NFC", "Error al resetear la tarjeta NFC");
	private static final ErrorCategory e102001 = new ErrorCategory(102001, "Error al ejecutar comando en la tarjeta NFC", "Error al ejecutar comando en la tarjeta NFC");
	private static final ErrorCategory e102002 = new ErrorCategory(102002, " Función no soportada por el hardware del dispositivo", " Función no soportada por el hardware del dispositivo");
	private static final ErrorCategory e102003 = new ErrorCategory(102003, "Restricción de seguridad", "Restricción de seguridad");
	private static final ErrorCategory e102004 = new ErrorCategory(102004, "Parámetro inválido en la conexion NFC", "Parámetro inválido en la conexion NFC");
	private static final ErrorCategory e102005 = new ErrorCategory(102005, "Longitud de parámetro no válida en la conexión NFC", "Longitud de parámetro no válida en la conexión NFC");
	private static final ErrorCategory e102006 = new ErrorCategory(102006, "Parámetro fuera de los límites aceptables", "Parámetro fuera de los límites aceptables");
	private static final ErrorCategory e102007 = new ErrorCategory(102007, "La radio NFC está deshabilitada", "La radio NFC está deshabilitada");
	private static final ErrorCategory e102008 = new ErrorCategory(102008, "La conexión con la etiqueta NFC se perdió", "La conexión con la etiqueta NFC se perdió");
	private static final ErrorCategory e102009 = new ErrorCategory(102009, "Se alcanzó el límite de intentos para transmitir datos", "Se alcanzó el límite de intentos para transmitir datos");
	private static final ErrorCategory e102010 = new ErrorCategory(102010, "Respuesta inválida desde la etiqueta NFC", "Respuesta inválida desde la etiqueta NFC");
	private static final ErrorCategory e102011 = new ErrorCategory(102011, "Transmisión en sesión invalidada", "Transmisión en sesión invalidada");
	private static final ErrorCategory e102012 = new ErrorCategory(102012, "La etiqueta NFC no está conectada", "La etiqueta NFC no está conectada");
	private static final ErrorCategory e102013 = new ErrorCategory(102013, "Paquete demasiado largo para la etiqueta NFC", "Paquete demasiado largo para la etiqueta NFC");
	private static final ErrorCategory e102014 = new ErrorCategory(102014, " El usuario canceló manualmente la sesión", " El usuario canceló manualmente la sesión");
	private static final ErrorCategory e102015 = new ErrorCategory(102015, "La sesión NFC expiró debido a inactividad", "La sesión NFC expiró debido a inactividad");private static final ErrorCategory e102016 = new ErrorCategory(102016, "La sesión NFC se cerró inesperadamente", "La sesión NFC se cerró inesperadamente");private static final ErrorCategory e102017 = new ErrorCategory(102017, "El sistema está ocupado", "El sistema está ocupado");
	private static final ErrorCategory e102018 = new ErrorCategory(102018, "Sesión cerrada tras leer la primera etiqueta (según configuración)", "Sesión cerrada tras leer la primera etiqueta (según configuración)");
	private static final ErrorCategory e102019 = new ErrorCategory(102019, "Configuración inválida en los comandos enviados a la etiqueta", "Configuración inválida en los comandos enviados a la etiqueta");
	private static final ErrorCategory e102020 = new ErrorCategory(102020, "La etiqueta NFC no es escribible", "La etiqueta NFC no es escribible");
	private static final ErrorCategory e102021 = new ErrorCategory(102021, "Fallo al intentar actualizar la etiqueta", "Fallo al intentar actualizar la etiqueta");
	private static final ErrorCategory e102022 = new ErrorCategory(102022, "Espacio insuficiente en la etiqueta NFC", "Espacio insuficiente en la etiqueta NFC");
	private static final ErrorCategory e102023 = new ErrorCategory(102023, "El mensaje NDEF tiene longitud cero", "El mensaje NDEF tiene longitud cero");
	private static final ErrorCategory e102024 = new ErrorCategory(102024, "Error desconocido al conectar con la tarjeta NFC", "Error desconocido al conectar con la tarjeta NFC");

	public static final Dictionary<String, ErrorCategory> NFC_CARDS = new Hashtable<>();
	static {
		NFC_CARDS.put(RESET_NFC, e102000);
		NFC_CARDS.put(COMMAND_NFC, e102001);
		NFC_CARDS.put(FUNCTION_NOT_SUPPORTED, e102002);
		NFC_CARDS.put(SECURITY_RESTRICTION, e102003);
		NFC_CARDS.put(INVALID_PARAM, e102004);
		NFC_CARDS.put(INVALID_PARAM_LONG, e102005);
		NFC_CARDS.put(OUT_LIMIT_PARAM, e102006);
		NFC_CARDS.put(DISABLED_RADIO, e102007);
		NFC_CARDS.put(LOST_CONNECTION_TAG, e102008);
		NFC_CARDS.put(TRY_LIMIT, e102009);
		NFC_CARDS.put(INVALID_RESPONSE_TAG, e102010);
		NFC_CARDS.put(INVALID_TRANSMISSION_SESSION, e102011);
		NFC_CARDS.put(NOT_CONNECTED_TAG, e102012);
		NFC_CARDS.put(PACKET_TOO_LONG, e102013);
		NFC_CARDS.put(SESSION_CANCELLED_MANUALLY, e102014);
		NFC_CARDS.put(SESSION_EXPIRED_INACTIVITY, e102015);
		NFC_CARDS.put(UNEXPECTED_CLOSED_SESSION, e102016);
		NFC_CARDS.put(BUSY_SYSTEM, e102017);
		NFC_CARDS.put(CLOSED_SESSION_AFTER_READ_FIRST_TAG, e102018);
		NFC_CARDS.put(INVALID_CONFIG_COMMANDS_SENT, e102019);
		NFC_CARDS.put(NFC_TAG_NOT_WRITABLE, e102020);
		NFC_CARDS.put(FAILED_UPDATING_TAG, e102021);
		NFC_CARDS.put(INSUFFICIENT_SPACE_TAG, e102022);
		NFC_CARDS.put(NDEF_LONG_ZERO, e102023);
		NFC_CARDS.put(UNKNOWN_ERROR_CONNECTING, e102024);
	}

}
