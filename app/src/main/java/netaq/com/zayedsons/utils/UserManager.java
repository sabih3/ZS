package netaq.com.zayedsons.utils;

import netaq.com.zayedsons.network.model.responses.ResponseRegister;

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
        //DevicePreferences.getInstance().getFCMToken()
        return "123";
    }

    public static void setDeviceID(String deviceID) {
        DevicePreferences.getInstance().setFCMToken(deviceID);

    }
}
