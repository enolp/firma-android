package es.gob.afirma.android;

import android.app.Application;
import android.content.Context;

import es.gob.afirma.android.gui.AppConfig;

public class MainApplication extends Application {

    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        String lang = AppConfig.getLocaleConfig(this);
        context = this;
        LocaleHelper.setLocale(this, lang);
        attachBaseContext(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    public Context getAppContext() {
        return this.getApplicationContext();
    }

}
