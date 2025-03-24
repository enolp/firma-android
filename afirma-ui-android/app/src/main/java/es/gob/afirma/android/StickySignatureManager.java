package es.gob.afirma.android;

import android.content.Context;

import java.security.KeyStore.PrivateKeyEntry;

import es.gob.afirma.android.gui.AppConfig;

public class StickySignatureManager {

    /** Clave privada fijada para reutilizarse en operaciones sucesivas. */
    private static PrivateKeyEntry stickyKeyEntry = null;

    /** Tarea con hilo para eliminar el certificado en cach&eacute;. */
    private static DeleteCertCacheService deleteCertCacheService = new DeleteCertCacheService();

    /**
     * Recupera la entrada con la clave y certificado prefijados para las
     * operaciones con certificados.
     *
     * @return Entrada con el certificado y la clave prefijados.
     */
    public static PrivateKeyEntry getStickyKeyEntry() {
        return stickyKeyEntry;
    }

    /**
     * Establece una clave y certificado prefijados para las operaciones con
     * certificados.
     *
     * @param pStickyKeyEntry Entrada con el certificado y la clave prefijados.
     * @param context Contexto.
     */
    public static void setStickyKeyEntry(final PrivateKeyEntry pStickyKeyEntry, final Context context) {
        stickyKeyEntry = pStickyKeyEntry;
        if (pStickyKeyEntry != null) {
            deleteCertCacheService.stop();
            deleteCertCacheService.start(AppConfig.getStickySignatureTimeout(context), context);
        }
    }

}
