package es.gob.afirma.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

import es.gob.afirma.android.errors.CommunicationErrors;
import es.gob.afirma.android.errors.FunctionalErrors;
import es.gob.afirma.android.errors.InternalSoftwareErrors;
import es.gob.afirma.android.errors.NFCErrors;
import es.gob.afirma.android.errors.RequestErrors;
import es.gob.afirma.android.errors.ThirdPartyErrors;
import es.gob.afirma.android.gui.AppConfig;

public class LocaleHelper {

    public static Context onAttach(Context context) {
        String lang = getPersistedData(context);
        return setLocale(context, lang);
    }

    public static Context setLocale(Context context, String language) {
        persist(language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);
    }

    public static String getPersistedData(Context context) {
        return AppConfig.getLocaleConfig(context);
    }

    private static void persist(String language) {
        AppConfig.setLocaleConfig(language);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        Context resContext = context.createConfigurationContext(configuration);

        updateLanguageErrors(resContext);

        return resContext;
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        updateLanguageErrors(context);

        return context;
    }

    private static void updateLanguageErrors(Context context) {
        NFCErrors.update(context);
        InternalSoftwareErrors.update(context);
        ThirdPartyErrors.update(context);
        CommunicationErrors.update(context);
        FunctionalErrors.update(context);
        RequestErrors.update(context);
    }
}
