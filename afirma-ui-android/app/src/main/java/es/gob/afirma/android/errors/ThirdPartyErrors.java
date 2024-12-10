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

/// ThirdParty Software Error 3XXXXX problemas software de terceros: Errores devueltos por bibliotecas o sistemas externos de los que desconocemos los detalles del error, ya que depende de ellos
public final class ThirdPartyErrors {

	// 3001XX: JMulticard
	public static final String UNKNOWN_OR_NOT_SUPPORTED_CARD = "UNKNOWN_OR_NOT_SUPPORTED_CARD";
	public static final String CORRUPTED_CARD = "CORRUPTED_CARD";
	public static final String CANT_CONNECT_CARD = "CANT_CONNECT_CARD";
	public static final String NOT_INITIALIZED_CARD = "NOT_INITIALIZED_CARD";
	public static final String UNEXPECTED = "UNEXPECTED";
	public static final String INCORRECT_PIN = "INCORRECT_PIN";
	public static final String BLOCKED_CARD = "BLOCKED_CARD";
	public static final String PIN_VALIDATION = "PIN_VALIDATION";
	public static final String CAN_VALIDATION = "CAN_VALIDATION";
	public static final String GENERIC_DURING_COMMUNICATION = "GENERIC_DURING_COMMUNICATION";
	public static final String NOT_HAVE_CERTIFICATE = "NOT_HAVE_CERTIFICATE";

	private static final ErrorCategory e300100 = new ErrorCategory(300100, "La tarjeta identificada en el lector es desconocida o no está soportada", "La tarjeta identificada en el lector es desconocida o no está soportada");
	private static final ErrorCategory e300101 = new ErrorCategory(300101, "La tarjeta está corrompida, posiblemente se autodestruyó", "La tarjeta está corrompida, posiblemente se autodestruyó");
	private static final ErrorCategory e300102 = new ErrorCategory(300102, "No se ha podido conectar con la tarjeta", "No se ha podido conectar con la tarjeta");
	private static final ErrorCategory e300103 = new ErrorCategory(300103, "La conexión con la tarjeta no está inicializada", "La conexión con la tarjeta no está inicializada");
	private static final ErrorCategory e300104 = new ErrorCategory(300104, "Ocurrió un error inesperado durante la operación", "Ocurrió un error inesperado durante la operación");
	private static final ErrorCategory e300105 = new ErrorCategory(300105, "MainApplication.getContext().getString(R.string.error_msc_pin)", "MainApplication.getContext().getString(R.string.incorrect_pin)");
	private static final ErrorCategory e300106 = new ErrorCategory(300106, "Tarjeta bloqueada", "Tarjeta bloqueada");
	private static final ErrorCategory e300107 = new ErrorCategory(300107, "Error durante la validación del PIN", "Error durante la validación del PIN");
	private static final ErrorCategory e300108 = new ErrorCategory(300108, "Error durante la validación del CAN", "Error durante la validación del CAN");
	private static final ErrorCategory e300109 = new ErrorCategory(300109, "Error genérico durante la comunicación con el DNIe", "Error genérico durante la comunicación con el DNIe");
	private static final ErrorCategory e300110 = new ErrorCategory(300110, "No se dispone de certificado de firma digital", "No se dispone de certificado de firma digital");

	public static final Dictionary<String, ErrorCategory> JMULTICARD = new Hashtable<>();
	static {
		JMULTICARD.put(UNKNOWN_OR_NOT_SUPPORTED_CARD, e300100);
		JMULTICARD.put(CORRUPTED_CARD, e300101);
		JMULTICARD.put(CANT_CONNECT_CARD, e300102);
		JMULTICARD.put(NOT_INITIALIZED_CARD, e300103);
		JMULTICARD.put(UNEXPECTED, e300104);
		JMULTICARD.put(INCORRECT_PIN, e300105);
		JMULTICARD.put(BLOCKED_CARD, e300106);
		JMULTICARD.put(PIN_VALIDATION, e300107);
		JMULTICARD.put(CAN_VALIDATION, e300108);
		JMULTICARD.put(GENERIC_DURING_COMMUNICATION, e300109);
		JMULTICARD.put(NOT_HAVE_CERTIFICATE, e300110);
	}

	// 3002XX: Servidor intermedio (descarga)
	static final String HTTP_DOWNLOAD = "HTTP_DOWNLOAD";
	static final String INVALID_DOWNLOAD_RESPONSE = "INVALID_DOWNLOAD_RESPONSE";
	static final String INVALID_XML = "INVALID_XML";
	static final String DECIPHER_RESPONSE = "DECIPHER_RESPONSE";
	static final String ERROR_RESPONSE_DOWNLOADING = "ERROR_RESPONSE_DOWNLOADING";

	private static final ErrorCategory e300200 = new ErrorCategory(300200, "Error HTTP al descargar la información del servidor intermedio", "Error HTTP al descargar la información del servidor intermedio");
	private static final ErrorCategory e300201 = new ErrorCategory(300201, "La respuesta del servidor intermedio al descargar la información no es una respuesta válida", "La respuesta del servidor intermedio al descargar la información no es una respuesta válida");
	private static final ErrorCategory e300202 = new ErrorCategory(300202, "La respuesta del servidor intermedio al descargar la información no es un XML válido", "La respuesta del servidor intermedio al descargar la información no es un XML válido");
	private static final ErrorCategory e300203 = new ErrorCategory(300203, "No se ha podido descifrar la respuesta del servidor intermedio", " No se ha podido descifrar la respuesta del servidor intermedio");
	private static final ErrorCategory e300204 = new ErrorCategory(300204, "El servidor intermedio ha devuelto una respuesta de error al intentar descargar la información de la operación", "El servidor intermedio ha devuelto una respuesta de error al intentar descargar la información de la operación");

	public static final Dictionary<String, ErrorCategory> DOWNLOAD_SERVER = new Hashtable<>();
	static {
		DOWNLOAD_SERVER.put(HTTP_DOWNLOAD, e300200);
		DOWNLOAD_SERVER.put(INVALID_DOWNLOAD_RESPONSE, e300201);
		DOWNLOAD_SERVER.put(INVALID_XML, e300202);
		DOWNLOAD_SERVER.put(DECIPHER_RESPONSE, e300203);
		DOWNLOAD_SERVER.put(ERROR_RESPONSE_DOWNLOADING, e300204);
	}

	// 3003XX: Servidor intermedio (subida)
	static final String HTTP_UPLOAD = "HTTP_UPLOAD";
	static final String INVALID_UPLOAD_RESPONSE = "INVALID_UPLOAD_RESPONSE";

	private static final ErrorCategory e300300 = new ErrorCategory(300300, "Error HTTP al subir la información del servidor intermedio", " Error HTTP al subir la información del servidor intermedio");
	private static final ErrorCategory e300301 = new ErrorCategory(300301, "La respuesta del servidor intermedio al subir la información no es una respuesta válida", "La respuesta del servidor intermedio al subir la información no es una respuesta válida");

	public static final Dictionary<String, ErrorCategory> UPLOAD_SERVER = new Hashtable<>();
	static {
		UPLOAD_SERVER.put(HTTP_UPLOAD, e300300);
		UPLOAD_SERVER.put(INVALID_UPLOAD_RESPONSE, e300301);
	}

	// 3004XX: Servidor trifasico
	public static final String HTTP_PRESIGN = "HTTP_PRESIGN";
	public static final String INVALID_RESPONSE_PRESIGN = "INVALID_RESPONSE_PRESIGN";
	public static final String INVALID_RESPONSE_PRESIGN_NO_SIGNS = "INVALID_RESPONSE_PRESIGN_NO_SIGNS";
	public static final String INVALID_RESPONSE_PRESIGN_NO_JSON = "INVALID_RESPONSE_PRESIGN_NO_JSON";
	public static final String HTTP_POSTSIGN = "HTTP_POSTSIGN";
	public static final String INVALID_RESPONSE_POSTSIGN = "INVALID_RESPONSE_POSTSIGN";
	public static final String INVALID_RESPONSE_POSTSIGN_NO_JSON = "INVALID_RESPONSE_POSTSIGN_NO_JSON";
	public static final String RESPONSE_OK_INCORRECT_FORMAT = "RESPONSE_OK_INCORRECT_FORMAT";
	public static final String RESPONSE_OK_CANT_PROCESS = "HTTP_PRESIGN";

	private static final ErrorCategory e300400 = new ErrorCategory(300400, "Error HTTP al realizar la operación de prefirma", "Error HTTP al realizar la operación de prefirma");
	private static final ErrorCategory e300401 = new ErrorCategory(300401, "La respuesta del servidor trifasico al hacer la prefirma no es válida", "La respuesta del servidor trifasico al hacer la prefirma no es válida");
	private static final ErrorCategory e300402 = new ErrorCategory(300402, "La respuesta del servidor trifasico al hacer la prefirma no es válida (No llega ni las firmas correctas, ni erroneas)", "La respuesta del servidor trifasico al hacer la prefirma no es válida (No llega ni las firmas correctas, ni erroneas)");
	private static final ErrorCategory e300403 = new ErrorCategory(300403, "La respuesta del servidor trifasico al hacer la prefirma no es un JSON", "La respuesta del servidor trifasico al hacer la prefirma no es un JSON");
	private static final ErrorCategory e300404 = new ErrorCategory(300404, "Error HTTP al realizar la operación de posfirma", "Error HTTP al realizar la operación de posfirma");
	private static final ErrorCategory e300405 = new ErrorCategory(300405, "La respuesta del servidor trifasico al hacer la posfirma no es válida", "La respuesta del servidor trifasico al hacer la posfirma no es válida");
	private static final ErrorCategory e300406 = new ErrorCategory(300406, "La respuesta del servidor trifasico al hacer la posfirma no es un JSON", "La respuesta del servidor trifasico al hacer la posfirma no es un JSON");
	private static final ErrorCategory e300407 = new ErrorCategory(300407, "El servidor trifasico devolvio una resuesta con texto OK pero no llega el formato correcto para obtener la información a enviar al servidor intermedio", "El servidor trifasico devolvio una resuesta con texto OK pero no llega el formato correcto para obtener la información a enviar al servidor intermedio");
	private static final ErrorCategory e300408 = new ErrorCategory(300408, "El servidor trifasico devolvio una resuesta correcta que no sabemos procesar. No llega ni OK ni ERR-", "El servidor trifasico devolvio una resuesta correcta que no sabemos procesar. No llega ni OK ni ERR-");

	static final String ERR_1_PRESIGN = "ERR_1_PRESIGN";
	static final String ERR_2_PRESIGN = "ERR_2_PRESIGN";
	static final String ERR_3_PRESIGN = "ERR_3_PRESIGN";
	static final String ERR_4_PRESIGN = "ERR_4_PRESIGN";
	static final String ERR_5_PRESIGN = "ERR_5_PRESIGN";
	static final String ERR_6_PRESIGN = "ERR_6_PRESIGN";
	static final String ERR_7_PRESIGN = "ERR_7_PRESIGN";
	static final String ERR_8_PRESIGN = "ERR_8_PRESIGN";
	static final String ERR_9_PRESIGN = "ERR_9_PRESIGN";
	static final String ERR_10_PRESIGN = "ERR_10_PRESIGN";
	static final String ERR_11_PRESIGN = "ERR_11_PRESIGN";
	static final String ERR_12_PRESIGN = "ERR_12_PRESIGN";
	static final String ERR_13_PRESIGN = "ERR_13_PRESIGN";
	static final String ERR_14_PRESIGN = "ERR_14_PRESIGN";
	static final String ERR_15_PRESIGN = "ERR_15_PRESIGN";
	static final String ERR_16_PRESIGN = "ERR_16_PRESIGN";
	static final String ERR_17_PRESIGN = "ERR_17_PRESIGN";
	static final String ERR_18_PRESIGN = "ERR_18_PRESIGN";
	static final String ERR_19_PRESIGN = "ERR_19_PRESIGN";
	static final String ERR_20_PRESIGN = "ERR_20_PRESIGN";
	static final String ERR_21_PRESIGN = "ERR_21_PRESIGN";

	private static final ErrorCategory e300409 = new ErrorCategory(300409, "El servidor trifasico devolvio una respuesta de error ERR-1 al realizar la prefirma. No se ha indicado la operacion a realizar", " El servidor trifasico devolvio una respuesta de error ERR-1 al realizar la prefirma. No se ha indicado la operacion a realizar");
	private static final ErrorCategory e300410 = new ErrorCategory(300410, "El servidor trifasico devolvio una respuesta de error ERR-2 al realizar la prefirma: No se ha indicado el identificador del documento", "El servidor trifasico devolvio una respuesta de error ERR-2 al realizar la prefirma: No se ha indicado el identificador del documento");
	private static final ErrorCategory e300411 = new ErrorCategory(300411, "El servidor trifasico devolvio una respuesta de error ERR-3 al realizar la prefirma: No se ha indicado el algoritmo de firma", "El servidor trifasico devolvio una respuesta de error ERR-3 al realizar la prefirma: No se ha indicado el algoritmo de firma");
	private static final ErrorCategory e300412 = new ErrorCategory(300412, "El servidor trifasico devolvio una respuesta de error ERR-4 al realizar la prefirma: No se ha indicado el formato de firma", "El servidor trifasico devolvio una respuesta de error ERR-4 al realizar la prefirma: No se ha indicado el formato de firma");
	private static final ErrorCategory e300413 = new ErrorCategory(300413, "El servidor trifasico devolvio una respuesta de error ERR-5 al realizar la prefirma: No se ha indicado el certificado de usuario", "El servidor trifasico devolvio una respuesta de error ERR-5 al realizar la prefirma: No se ha indicado el certificado de usuario");
	private static final ErrorCategory e300414 = new ErrorCategory(300414, "El servidor trifasico devolvio una respuesta de error ERR-6 al realizar la prefirma: El formato de los parametros adicionales suministrados es erroneo", "El servidor trifasico devolvio una respuesta de error ERR-6 al realizar la prefirma: El formato de los parametros adicionales suministrados es erroneo");
	private static final ErrorCategory e300415 = new ErrorCategory(300415, "El servidor trifasico devolvio una respuesta de error ERR-7 al realizar la prefirma: El certificado de usuario no esta en formato X.509", "El servidor trifasico devolvio una respuesta de error ERR-7 al realizar la prefirma: El certificado de usuario no esta en formato X.509");
	private static final ErrorCategory e300416 = new ErrorCategory(300416, "El servidor trifasico devolvio una respuesta de error ERR-8 al realizar la prefirma: Formato de firma no soportado", "El servidor trifasico devolvio una respuesta de error ERR-8 al realizar la prefirma: Formato de firma no soportado");
	private static final ErrorCategory e300417 = new ErrorCategory(300417, "El servidor trifasico devolvio una respuesta de error ERR-9 al realizar la prefirma: Error realizando la prefirma", "El servidor trifasico devolvio una respuesta de error ERR-9 al realizar la prefirma: Error realizando la prefirma");
	private static final ErrorCategory e300418 = new ErrorCategory(300418, "El servidor trifasico devolvio una respuesta de error ERR-10 al realizar la prefirma: Error al almacenar el documento", "El servidor trifasico devolvio una respuesta de error ERR-10 al realizar la prefirma: Error al almacenar el documento");
	private static final ErrorCategory e300419 = new ErrorCategory(300419, "El servidor trifasico devolvio una respuesta de error ERR-11 al realizar la prefirma: Operacion desconocida", "El servidor trifasico devolvio una respuesta de error ERR-11 al realizar la prefirma: Operacion desconocida");
	private static final ErrorCategory e300420 = new ErrorCategory(300420, "El servidor trifasico devolvio una respuesta de error ERR-12 al realizar la prefirma: Error realizando la postfirma", "El servidor trifasico devolvio una respuesta de error ERR-12 al realizar la prefirma: Error realizando la postfirma");
	private static final ErrorCategory e300421 = new ErrorCategory(300421, "El servidor trifasico devolvio una respuesta de error ERR-13 al realizar la prefirma: No se indicado una sub-operacion valida a realizar (firma, cofirma,...)", "El servidor trifasico devolvio una respuesta de error ERR-13 al realizar la prefirma: No se indicado una sub-operacion valida a realizar (firma, cofirma,...)");
	private static final ErrorCategory e300422 = new ErrorCategory(300422, "El servidor trifasico devolvio una respuesta de error ERR-14 al realizar la prefirma: Error al recuperar el documento", "El servidor trifasico devolvio una respuesta de error ERR-14 al realizar la prefirma: Error al recuperar el documento");
	private static final ErrorCategory e300423 = new ErrorCategory(300423, "El servidor trifasico devolvio una respuesta de error ERR-15 al realizar la prefirma: El formato de los datos de sesion suministrados es erroneo", "El servidor trifasico devolvio una respuesta de error ERR-15 al realizar la prefirma: El formato de los datos de sesion suministrados es erroneo");
	private static final ErrorCategory e300424 = new ErrorCategory(300424, "El servidor trifasico devolvio una respuesta de error ERR-16 al realizar la prefirma: Error al generar el codigo de verificacion de las firmas", "El servidor trifasico devolvio una respuesta de error ERR-16 al realizar la prefirma: Error al generar el codigo de verificacion de las firmas");
	private static final ErrorCategory e300425 = new ErrorCategory(300425, "El servidor trifasico devolvio una respuesta de error ERR-17 al realizar la prefirma: Error al comprobar el codigo de verificacion de las firmas", "El servidor trifasico devolvio una respuesta de error ERR-17 al realizar la prefirma: Error al comprobar el codigo de verificacion de las firmas");
	private static final ErrorCategory e300426 = new ErrorCategory(300426, "El servidor trifasico devolvio una respuesta de error ERR-18 al realizar la prefirma: Error de integridad en la firma", "El servidor trifasico devolvio una respuesta de error ERR-18 al realizar la prefirma: Error de integridad en la firma");
	private static final ErrorCategory e300427 = new ErrorCategory(300427, "El servidor trifasico devolvio una respuesta de error ERR-19 al realizar la prefirma: El formato de los datos de operacion suministrados es erroneo", "El servidor trifasico devolvio una respuesta de error ERR-19 al realizar la prefirma: El formato de los datos de operacion suministrados es erroneo");
	private static final ErrorCategory e300428 = new ErrorCategory(300428, "El servidor trifasico devolvio una respuesta de error ERR-20 al realizar la prefirma: Algoritmo de firma no soportado", "El servidor trifasico devolvio una respuesta de error ERR-20 al realizar la prefirma: Algoritmo de firma no soportado");
	private static final ErrorCategory e300429 = new ErrorCategory(300429, "El servidor trifasico devolvio una respuesta de error ERR-21 al realizar la prefirma: Se requiere intervencion del usuario", "El servidor trifasico devolvio una respuesta de error ERR-21 al realizar la prefirma: Se requiere intervencion del usuario");

	static final String ERR_1_POSTSIGN = "ERR_1_POSTSIGN";
	static final String ERR_2_POSTSIGN = "ERR_2_POSTSIGN";
	static final String ERR_3_POSTSIGN = "ERR_3_POSTSIGN";
	static final String ERR_4_POSTSIGN = "ERR_4_POSTSIGN";
	static final String ERR_5_POSTSIGN = "ERR_5_POSTSIGN";
	static final String ERR_6_POSTSIGN = "ERR_6_POSTSIGN";
	static final String ERR_7_POSTSIGN = "ERR_7_POSTSIGN";
	static final String ERR_8_POSTSIGN = "ERR_8_POSTSIGN";
	static final String ERR_9_POSTSIGN = "ERR_9_POSTSIGN";
	static final String ERR_10_POSTSIGN = "ERR_10_POSTSIGN";
	static final String ERR_11_POSTSIGN = "ERR_11_POSTSIGN";
	static final String ERR_12_POSTSIGN = "ERR_12_POSTSIGN";
	static final String ERR_13_POSTSIGN = "ERR_13_POSTSIGN";
	static final String ERR_14_POSTSIGN = "ERR_14_POSTSIGN";
	static final String ERR_15_POSTSIGN = "ERR_15_POSTSIGN";
	static final String ERR_16_POSTSIGN = "ERR_16_POSTSIGN";
	static final String ERR_17_POSTSIGN = "ERR_17_POSTSIGN";
	static final String ERR_18_POSTSIGN = "ERR_18_POSTSIGN";
	static final String ERR_19_POSTSIGN = "ERR_19_POSTSIGN";
	static final String ERR_20_POSTSIGN = "ERR_20_POSTSIGN";
	static final String ERR_21_POSTSIGN = "ERR_11_POSTSIGN";

	private static final ErrorCategory e300430 = new ErrorCategory(300430, "El servidor trifasico devolvio una respuesta de error ERR-1 al realizar la postfirma. No se ha indicado la operacion a realizar", "El servidor trifasico devolvio una respuesta de error ERR-1 al realizar la postfirma. No se ha indicado la operacion a realizar");
	private static final ErrorCategory e300431 = new ErrorCategory(300431, "El servidor trifasico devolvio una respuesta de error ERR-2 al realizar la postfirma: No se ha indicado el identificador del documento", "El servidor trifasico devolvio una respuesta de error ERR-2 al realizar la postfirma: No se ha indicado el identificador del documento");
	private static final ErrorCategory e300432 = new ErrorCategory(300432, "El servidor trifasico devolvio una respuesta de error ERR-3 al realizar la postfirma: No se ha indicado el algoritmo de firma", "El servidor trifasico devolvio una respuesta de error ERR-3 al realizar la postfirma: No se ha indicado el algoritmo de firma");
	private static final ErrorCategory e300433 = new ErrorCategory(300433, "El servidor trifasico devolvio una respuesta de error ERR-4 al realizar la postfirma: No se ha indicado el formato de firma", "El servidor trifasico devolvio una respuesta de error ERR-4 al realizar la postfirma: No se ha indicado el formato de firma");
	private static final ErrorCategory e300434 = new ErrorCategory(300434, "El servidor trifasico devolvio una respuesta de error ERR-5 al realizar la postfirma: No se ha indicado el certificado de usuario", "El servidor trifasico devolvio una respuesta de error ERR-5 al realizar la postfirma: No se ha indicado el certificado de usuario");
	private static final ErrorCategory e300435 = new ErrorCategory(300435, "El servidor trifasico devolvio una respuesta de error ERR-6 al realizar la postfirma: El formato de los parametros adicionales suministrados es erroneo", "El servidor trifasico devolvio una respuesta de error ERR-6 al realizar la postfirma: El formato de los parametros adicionales suministrados es erroneo");
	private static final ErrorCategory e300436 = new ErrorCategory(300436, "El servidor trifasico devolvio una respuesta de error ERR-7 al realizar la postfirma: El certificado de usuario no esta en formato X.509", "El servidor trifasico devolvio una respuesta de error ERR-7 al realizar la postfirma: El certificado de usuario no esta en formato X.509");
	private static final ErrorCategory e300437 = new ErrorCategory(300437, "El servidor trifasico devolvio una respuesta de error ERR-8 al realizar la postfirma: Formato de firma no soportado", "El servidor trifasico devolvio una respuesta de error ERR-8 al realizar la postfirma: Formato de firma no soportado");
	private static final ErrorCategory e300438 = new ErrorCategory(300438, "El servidor trifasico devolvio una respuesta de error ERR-9 al realizar la postfirma: Error realizando la prefirma", "El servidor trifasico devolvio una respuesta de error ERR-9 al realizar la postfirma: Error realizando la prefirma");
	private static final ErrorCategory e300439 = new ErrorCategory(300439, "El servidor trifasico devolvio una respuesta de error ERR-10 al realizar la postfirma: Error al almacenar el documento", "El servidor trifasico devolvio una respuesta de error ERR-10 al realizar la postfirma: Error al almacenar el documento");
	private static final ErrorCategory e300440 = new ErrorCategory(300440, "El servidor trifasico devolvio una respuesta de error ERR-11 al realizar la postfirma: Operacion desconocida", "El servidor trifasico devolvio una respuesta de error ERR-11 al realizar la postfirma: Operacion desconocida");
	private static final ErrorCategory e300441 = new ErrorCategory(300441, "El servidor trifasico devolvio una respuesta de error ERR-12 al realizar la postfirma: Error realizando la postfirma", "El servidor trifasico devolvio una respuesta de error ERR-12 al realizar la postfirma: Error realizando la postfirma");
	private static final ErrorCategory e300442 = new ErrorCategory(300442, "El servidor trifasico devolvio una respuesta de error ERR-13 al realizar la postfirma: No se indicado una sub-operacion valida a realizar (firma, cofirma,...)", "El servidor trifasico devolvio una respuesta de error ERR-13 al realizar la postfirma: No se indicado una sub-operacion valida a realizar (firma, cofirma,...)");
	private static final ErrorCategory e300443 = new ErrorCategory(300443, "El servidor trifasico devolvio una respuesta de error ERR-14 al realizar la postfirma: Error al recuperar el documento", "El servidor trifasico devolvio una respuesta de error ERR-14 al realizar la postfirma: Error al recuperar el documento");
	private static final ErrorCategory e300444 = new ErrorCategory(300444, "El servidor trifasico devolvio una respuesta de error ERR-15 al realizar la postfirma: El formato de los datos de sesion suministrados es erroneo", " El servidor trifasico devolvio una respuesta de error ERR-15 al realizar la postfirma: El formato de los datos de sesion suministrados es erroneo");
	private static final ErrorCategory e300445 = new ErrorCategory(300445, "El servidor trifasico devolvio una respuesta de error ERR-16 al realizar la postfirma: Error al generar el codigo de verificacion de las firmas", "El servidor trifasico devolvio una resuesta con texto OK pero no llega el formato correcto para obtener la información a enviar al servidor intermedio");
	private static final ErrorCategory e300446 = new ErrorCategory(300446, "El servidor trifasico devolvio una respuesta de error ERR-17 al realizar la postfirma: Error al comprobar el codigo de verificacion de las firmas", "El servidor trifasico devolvio una respuesta de error ERR-17 al realizar la postfirma: Error al comprobar el codigo de verificacion de las firmas");
	private static final ErrorCategory e300447 = new ErrorCategory(300447, "El servidor trifasico devolvio una respuesta de error ERR-18 al realizar la postfirma: Error de integridad en la firma", "El servidor trifasico devolvio una respuesta de error ERR-18 al realizar la postfirma: Error de integridad en la firma");
	private static final ErrorCategory e300448 = new ErrorCategory(300448, "El servidor trifasico devolvio una respuesta de error ERR-19 al realizar la postfirma: El formato de los datos de operacion suministrados es erroneo", "El servidor trifasico devolvio una respuesta de error ERR-19 al realizar la postfirma: El formato de los datos de operacion suministrados es erroneo");
	private static final ErrorCategory e300449 = new ErrorCategory(300449, "El servidor trifasico devolvio una respuesta de error ERR-20 al realizar la postfirma: Algoritmo de firma no soportado", "El servidor trifasico devolvio una respuesta de error ERR-20 al realizar la postfirma: Algoritmo de firma no soportado");
	private static final ErrorCategory e300450 = new ErrorCategory(300450, "El servidor trifasico devolvio una respuesta de error ERR-21 al realizar la postfirma: Se requiere intervencion del usuario", "El servidor trifasico devolvio una respuesta de error ERR-21 al realizar la postfirma: Se requiere intervencion del usuario");

	public static final Dictionary<String, ErrorCategory> TRIPHASE_SERVER = new Hashtable<>();
	static {
		TRIPHASE_SERVER.put(HTTP_PRESIGN, e300400);
		TRIPHASE_SERVER.put(INVALID_RESPONSE_PRESIGN, e300401);
		TRIPHASE_SERVER.put(INVALID_RESPONSE_PRESIGN_NO_SIGNS, e300402);
		TRIPHASE_SERVER.put(INVALID_RESPONSE_PRESIGN_NO_JSON, e300403);
		TRIPHASE_SERVER.put(HTTP_POSTSIGN, e300404);
		TRIPHASE_SERVER.put(INVALID_RESPONSE_POSTSIGN, e300405);
		TRIPHASE_SERVER.put(INVALID_RESPONSE_POSTSIGN_NO_JSON, e300406);
		TRIPHASE_SERVER.put(RESPONSE_OK_INCORRECT_FORMAT, e300407);
		TRIPHASE_SERVER.put(RESPONSE_OK_CANT_PROCESS, e300408);

		TRIPHASE_SERVER.put(ERR_1_PRESIGN, e300409);
		TRIPHASE_SERVER.put(ERR_2_PRESIGN, e300410);
		TRIPHASE_SERVER.put(ERR_3_PRESIGN, e300411);
		TRIPHASE_SERVER.put(ERR_4_PRESIGN, e300412);
		TRIPHASE_SERVER.put(ERR_5_PRESIGN, e300413);
		TRIPHASE_SERVER.put(ERR_6_PRESIGN, e300414);
		TRIPHASE_SERVER.put(ERR_7_PRESIGN, e300415);
		TRIPHASE_SERVER.put(ERR_8_PRESIGN, e300416);
		TRIPHASE_SERVER.put(ERR_9_PRESIGN, e300417);
		TRIPHASE_SERVER.put(ERR_10_PRESIGN, e300418);
		TRIPHASE_SERVER.put(ERR_11_PRESIGN, e300419);
		TRIPHASE_SERVER.put(ERR_12_PRESIGN, e300420);
		TRIPHASE_SERVER.put(ERR_13_PRESIGN, e300421);
		TRIPHASE_SERVER.put(ERR_14_PRESIGN, e300422);
		TRIPHASE_SERVER.put(ERR_15_PRESIGN, e300423);
		TRIPHASE_SERVER.put(ERR_16_PRESIGN, e300424);
		TRIPHASE_SERVER.put(ERR_17_PRESIGN, e300425);
		TRIPHASE_SERVER.put(ERR_18_PRESIGN, e300426);
		TRIPHASE_SERVER.put(ERR_19_PRESIGN, e300427);
		TRIPHASE_SERVER.put(ERR_20_PRESIGN, e300428);
		TRIPHASE_SERVER.put(ERR_21_PRESIGN, e300429);

		TRIPHASE_SERVER.put(ERR_1_POSTSIGN, e300430);
		TRIPHASE_SERVER.put(ERR_2_POSTSIGN, e300431);
		TRIPHASE_SERVER.put(ERR_3_POSTSIGN, e300432);
		TRIPHASE_SERVER.put(ERR_4_POSTSIGN, e300433);
		TRIPHASE_SERVER.put(ERR_5_POSTSIGN, e300434);
		TRIPHASE_SERVER.put(ERR_6_POSTSIGN, e300435);
		TRIPHASE_SERVER.put(ERR_7_POSTSIGN, e300436);
		TRIPHASE_SERVER.put(ERR_8_POSTSIGN, e300437);
		TRIPHASE_SERVER.put(ERR_9_POSTSIGN, e300438);
		TRIPHASE_SERVER.put(ERR_10_POSTSIGN, e300439);
		TRIPHASE_SERVER.put(ERR_11_POSTSIGN, e300440);
		TRIPHASE_SERVER.put(ERR_12_POSTSIGN, e300441);
		TRIPHASE_SERVER.put(ERR_13_POSTSIGN, e300442);
		TRIPHASE_SERVER.put(ERR_14_POSTSIGN, e300443);
		TRIPHASE_SERVER.put(ERR_15_POSTSIGN, e300444);
		TRIPHASE_SERVER.put(ERR_16_POSTSIGN, e300445);
		TRIPHASE_SERVER.put(ERR_17_POSTSIGN, e300446);
		TRIPHASE_SERVER.put(ERR_18_POSTSIGN, e300447);
		TRIPHASE_SERVER.put(ERR_19_POSTSIGN, e300448);
		TRIPHASE_SERVER.put(ERR_20_POSTSIGN, e300449);
		TRIPHASE_SERVER.put(ERR_21_POSTSIGN, e300450);
	}

}
