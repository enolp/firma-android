package es.gob.afirma.android.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

public class Utils {

    private static Boolean isTabletDevice = null;

    public static boolean isTablet(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        boolean xlarge = (screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE;
        boolean large = (screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE;
        return xlarge || large;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    public static void setPortraitSmartphone(Activity activity) {

        if (isTabletDevice == null) {
            isTabletDevice = isTablet(activity);
        }

        if (isTabletDevice) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
