package netaq.com.zayedsons.utils;

/**
 * Created by sabih on 05-Mar-18.
 */

public class OTPHelper {

    public static int getNewOTP(){

        int OTP = OTPGenerator.generateRandomNumber();

        DevicePreferences.getInstance().setOTP(OTP);

        return OTP;
    }

    public static int getCachedOTP(){

        return DevicePreferences.getInstance().getOTP();
    }

    public static boolean isOTPValid(Integer userProvidedOTP) {

        return getCachedOTP()==userProvidedOTP ;
    }
}
