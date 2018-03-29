package netaq.com.zayedsons.utils;

import android.provider.Settings;

import netaq.com.zayedsons.network.model.responses.ResponseRegister;

import static android.provider.Settings.*;
import static android.provider.Settings.Secure.*;

/**
 * Created by sabih on 06-Mar-18.
 */

public class UserManager {

    public static void setUser(ResponseRegister userInfo){
        DevicePreferences.getInstance().setUser(userInfo);
    }

    public static ResponseRegister getUser(){
        return DevicePreferences.getInstance().getUser();
    }


    public static void clearUserData() {
        ResponseRegister userInfo = new ResponseRegister();
        DevicePreferences.getInstance().setUser(userInfo);
        DevicePreferences.getInstance().setOTP(0);
    }

    public static String getDeviceID() {

        return DevicePreferences.getInstance().getAndroidID();
    }


    public static void setPushToken(String fcmToken) {
        DevicePreferences.getInstance().setFCMToken(fcmToken);
    }

    public static String getPushToken(){
       return DevicePreferences.getInstance().getFCMToken();
    }


}
