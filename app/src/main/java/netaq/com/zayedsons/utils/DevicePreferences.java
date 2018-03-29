package netaq.com.zayedsons.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.google.gson.Gson;

import netaq.com.zayedsons.network.model.responses.ResponseRegister;


/**
 * Created by Sabih Ahmed on 05/03/2018.
 */

public class DevicePreferences {

    private static final String KEY_ARABIC_LOCALE = "key_locale";
    private static final String KEY_CACHED_OTP = "key_otp";
    private static final String KEY_USER_INFO = "key_user_info";
    private static final String KEY_FCM_TOKEN = "key_fcm_token";


    private static DevicePreferences instance;
    private static SharedPreferences prefs;

    private Context mContext;


    private DevicePreferences() {

    }

    public static DevicePreferences getInstance(){

        if(instance == null ){
            instance = new DevicePreferences();
        }
        return instance;

    }

    public void init(Context context){

        this.mContext = context;

        prefs = mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }


    /**Sets Preferred locale
     * pass setToArabic as true when setting preferred locale
     * to arabic, false otherwise
     * @param setToArabic
     */
    public static void setArabicLocale(boolean setToArabic){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_ARABIC_LOCALE,setToArabic);
        editor.commit();
    }

    /**Gives Preferred App locale
     * true in case of arabic,
     * false otherwise
     * @return
     */
    public static boolean isLocaleSetToArabic(){
        boolean localeSetToArabic;

        localeSetToArabic = prefs.getBoolean(KEY_ARABIC_LOCALE,false);

        return localeSetToArabic;
    }


    public void setOTP(int OTP) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_CACHED_OTP,OTP);
        editor.commit();
    }



    public int getOTP(){
        int cachedOTP = 0;
        cachedOTP = prefs.getInt(KEY_CACHED_OTP,0);
        return cachedOTP;
    }

    public void setUser(ResponseRegister userInfo){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(userInfo);
        editor.putString(KEY_USER_INFO,userJson);

        editor.commit();
    }

    public ResponseRegister getUser(){
        ResponseRegister userInfo = null;

        String userJson = prefs.getString(KEY_USER_INFO, "");

        Gson gson = new Gson();
        userInfo = gson.fromJson(userJson,ResponseRegister.class);

        return userInfo;

    }

    public void setFCMToken(String FCMToken) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_FCM_TOKEN,FCMToken);
        editor.commit();

    }

    public String getFCMToken() {
        String fcmToken = "";

        fcmToken = prefs.getString(KEY_FCM_TOKEN,fcmToken);
        return fcmToken;
    }

    public String getAndroidID(){
        String androidID = Settings.Secure.getString(mContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return androidID;
    }
}
