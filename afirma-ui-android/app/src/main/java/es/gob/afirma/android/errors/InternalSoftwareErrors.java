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

import androidx.appcompat.app.AppCompatActivity;

import java.util.Dictionary;
import java.util.Hashtable;

import es.gob.afirma.R;

// Internal Software Error 2XXXXX - Problemas software internos: Errores en el funcionamiento interno de la aplicacion.
public final class InternalSoftwareErrors extends AppCompatActivity {

	// 2000XXL: Errores general
	public static final String SOFTWARE_GENERAL = "SOFTWARE_GENERAL";
	public static final String CANT_SAVE_SIGN_RECORD = "CANT_SAVE_SIGN_RECORD";
	public static final String ERROR_SIGNING = "ERROR_SIGNING";

    public static final Dictionary<String, ErrorCategory> GENERAL = new Hashtable<>();

	// 2001XX: Operación de firma
	public static final String CYPHERING_SIGN = "CYPHERING_SIGN";
	public static final String CYPHERING_CERT = "ENCRYPTING_CERT";
	public static final String LOADING_LOCAL_FILE = "LOADING_LOCAL_FILE";
	public static final String OUT_OF_MEMORY = "OUT_OF_MEMORY";

    public static final Dictionary<String, ErrorCategory> OPERATION_SIGN = new Hashtable<>();

	// 2002XX: Operacion de seleccion de certificados
	public static final String CYPHERING_CERT_OP = "CYPHERING_CERT";

    public static final Dictionary<String, ErrorCategory> CERTIFICATE_SELECTION = new Hashtable<>();

	// 2003XX: Operacion de guardado de datos
	public static final String SAVING_DATA_DISK = "SAVING_DATA_DISK";
	public static final String NO_DEVICE_STORE = "NO_DEVICE_STORE";
	public static final String CANT_OBTAIN_DATA_FLOW = "CANT_OBTAIN_DATA_FLOW";
	public static final String NO_STORAGE_PERMISSIONS = "NO_STORAGE_PERMISSIONS";
	public static final String SAVING_DATA_OP = "SAVING_DATA_OP";

    public static final Dictionary<String, ErrorCategory> SAVING_DATA = new Hashtable<>();

	// 2004XX: Operacion de lotes JSON
	public static final String CYPHERING_SIGN_INFO_TO_SEND = "CYPHERING_SIGN_INFO_TO_SEND";
	public static final String CYPHERING_CERT_TO_SEND = "CYPHERING_CERT_TO_SEND";

    public static final Dictionary<String, ErrorCategory> BATCH_JSON = new Hashtable<>();

	// 2007XX: Carga de ficheros
	public static final String DOMAIN_FORMAT_INCORRECT = "DOMAIN_FORMAT_INCORRECT";
	public static final Dictionary<String, ErrorCategory> APP_CONFIGURATION = new Hashtable<>();

	// 2011XX: Carga/listado de certificados
	public static final String LOAD_CERT_TO_IMPORT = "LOAD_CERT_TO_IMPORT";
	public static final String LOAD_KEYSTORE = "LOAD_KEYSTORE";
	public static final String ALIAS_NOT_VALID = "ALIAS_NOT_VALID";
	public static final String LOAD_KEY = "LOAD_KEY";
	public static final String USER_NOT_SELECT_CERT = "USER_NOT_SELECT_CERT";
	public static final String UNEXPECED_RECOVERING_KEY = "UNEXPECED_RECOVERING_KEY";
	public static final String LOAD_CERT = "LOAD_CERT";

    public static final Dictionary<String, ErrorCategory> LOAD_CERTS = new Hashtable<>();

	public static void update(Context context) {

        ErrorCategory e200000 = new ErrorCategory(200000, context.getString(R.string.not_completed_request), "Error general de software");
        ErrorCategory e200001 = new ErrorCategory(200001, context.getString(R.string.not_completed_request), "La firma se ha realizado correctamente pero no se ha podido guardar en el histórico de firmas");
        ErrorCategory e200002 = new ErrorCategory(200002, context.getString(R.string.not_completed_request), "Error durante la firma");

		GENERAL.put(SOFTWARE_GENERAL, e200000);
		GENERAL.put(CANT_SAVE_SIGN_RECORD, e200001);
		GENERAL.put(ERROR_SIGNING, e200002);

        ErrorCategory e200100 = new ErrorCategory(200100, context.getString(R.string.not_completed_request), "Error al cifrar la firma para enviarla al servidor intermedio");
        ErrorCategory e200101 = new ErrorCategory(200101, context.getString(R.string.not_completed_request), "Error al cifrar el certificado para enviarlo al servidor intermedio");
        ErrorCategory e200102 = new ErrorCategory(200102, context.getString(R.string.not_completed_request), "Error al cargar el fichero local para realizar la firma");
        ErrorCategory e200105 = new ErrorCategory(200105, context.getString(R.string.file_read_out_of_memory), "Error de memoria al cargar el fichero");

		OPERATION_SIGN.put(CYPHERING_SIGN, e200100);
		OPERATION_SIGN.put(CYPHERING_CERT, e200101);
		OPERATION_SIGN.put(LOADING_LOCAL_FILE, e200102);
		OPERATION_SIGN.put(OUT_OF_MEMORY, e200105);

        ErrorCategory e200200 = new ErrorCategory(200200, context.getString(R.string.not_completed_request), "Error al cifrar el certificado para enviarlo al servidor intermedio");
		CERTIFICATE_SELECTION.put(CYPHERING_CERT_OP, e200200);

		ErrorCategory e200301 = new ErrorCategory(200301, context.getString(R.string.not_completed_request), "No se ha podido guardar el fichero en disco");
        ErrorCategory e200302 = new ErrorCategory(200302, context.getString(R.string.not_completed_request), "No se ha encontrado donde guardar la firma generada");
        ErrorCategory e200303 = new ErrorCategory(200303, context.getString(R.string.error_saving_signature), "No se pudo obtener el flujo para el guardado de los datos");
		ErrorCategory e200304 = new ErrorCategory(200304, context.getString(R.string.error_no_storage_permissions), "No dispone de permisos de escritura");
		ErrorCategory e200305 = new ErrorCategory(200305, context.getString(R.string.error_saving_data), "Error al guardar datos");

		SAVING_DATA.put(SAVING_DATA_DISK, e200301);
		SAVING_DATA.put(NO_DEVICE_STORE, e200302);
		SAVING_DATA.put(CANT_OBTAIN_DATA_FLOW, e200303);
		SAVING_DATA.put(NO_STORAGE_PERMISSIONS, e200304);
		SAVING_DATA.put(SAVING_DATA_OP, e200305);

        ErrorCategory e200400 = new ErrorCategory(200400, context.getString(R.string.not_completed_request), "Error al cifrar la información de las firmas del lote para enviarla al servidor intermedio");
        ErrorCategory e200401 = new ErrorCategory(200401, context.getString(R.string.not_completed_request), "Error al cifrar el certificado para enviarlo al sevidor intermedio");

		BATCH_JSON.put(CYPHERING_SIGN_INFO_TO_SEND, e200400);
		BATCH_JSON.put(CYPHERING_CERT_TO_SEND, e200401);

        ErrorCategory e200700 = new ErrorCategory(200700, context.getString(R.string.error_format_trusted_domains), "Se han encontrado entradas no validas en el listado de dominios");
		APP_CONFIGURATION.put(DOMAIN_FORMAT_INCORRECT, e200700);

        ErrorCategory e201100 = new ErrorCategory(201100, context.getString(R.string.cant_add_cert_message), "Error en la carga de certificado para importar");
        ErrorCategory e201101 = new ErrorCategory(201101, context.getString(R.string.error_loading_keystore), "Error al cargar el almacen de certificados");
        ErrorCategory e201102 = new ErrorCategory(201102, context.getString(R.string.error_loading_cert), "Error al cargar el certificado, posiblemente relacionado por usar un alias de certificado no valido");
        ErrorCategory e201103 = new ErrorCategory(201103, context.getString(R.string.error_loading_cert), "Error al cargar la clave del certificado");
        ErrorCategory e201104 = new ErrorCategory(201104, context.getString(R.string.not_selected_cert), "El usuario no selecciono un certificado");
        ErrorCategory e201105 = new ErrorCategory(201105, context.getString(R.string.error_loading_cert), "Error inesperado al recuperar la clave del certificado de firma");
		ErrorCategory e201106 = new ErrorCategory(201106, context.getString(R.string.error_loading_cert), "Error al cargar certificado");

		LOAD_CERTS.put(LOAD_CERT_TO_IMPORT, e201100);
		LOAD_CERTS.put(LOAD_KEYSTORE, e201101);
		LOAD_CERTS.put(ALIAS_NOT_VALID, e201102);
		LOAD_CERTS.put(LOAD_KEY, e201103);
		LOAD_CERTS.put(USER_NOT_SELECT_CERT, e201104);
		LOAD_CERTS.put(UNEXPECED_RECOVERING_KEY, e201105);
		LOAD_CERTS.put(LOAD_CERT, e201106);
	}

}
