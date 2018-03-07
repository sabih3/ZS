package netaq.com.zayedsons.core;

import android.app.Application;

import netaq.com.zayedsons.utils.DevicePreferences;

/**
 * Created by sabih on 05-Mar-18.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DevicePreferences.getInstance().init(this);
    }
}
