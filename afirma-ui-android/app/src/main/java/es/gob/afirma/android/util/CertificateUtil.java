package es.gob.afirma.android.util;

import java.security.cert.X509Certificate;
import java.util.Date;

public class CertificateUtil {

    /** Tiempo de antelaci&oacute;n desde el que se empezar&aacute; a advertir que hay certificados pr&oacute;ximos a caducar. */
    private static final long EXPIRITY_WARNING_LEVEL = 1000*60*60*24*7;

    /**
     * Indica si el certificado est&aacute; pr&oacute;ximo a caducar.
     * @param cert Certificado a evaluar.
     * @return true en caso de que expire en menos de una semana, false en caso contrario.
     */
    public static boolean checkExpiredSoon(final X509Certificate cert) {
        boolean expiredSoon = false;
        final long notAfter = cert.getNotAfter().getTime();
        final long currentDate = new Date().getTime();
        if (notAfter - currentDate < EXPIRITY_WARNING_LEVEL) {
            expiredSoon = true;
        }
        return expiredSoon;
    }

}
