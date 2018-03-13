package netaq.com.zayedsons.core;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import netaq.com.zayedsons.R;
import netaq.com.zayedsons.model.Event;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;
import netaq.com.zayedsons.views.MainActivity;
import netaq.com.zayedsons.views.QRScanner;
import netaq.com.zayedsons.views.ScreenOTP;
import netaq.com.zayedsons.views.events.EventMainFragment;
import netaq.com.zayedsons.views.events.ScreenEventQR;
import netaq.com.zayedsons.views.events.event_detail.ScreenEventDetail;
import netaq.com.zayedsons.views.login.LoginWithMobile;
import netaq.com.zayedsons.views.registration.RegistrationActivity;

/**
 * Created by sabih on 08-Feb-18.
 */

public class NavigationController {
    public static final String KEY_USER_EXISTENCE = "key_user_existence";
    public static final String KEY_RECIPIENT = "key_recipient";
    public static final String KEY_OTP = "key_otp";
    public static final String KEY_USER_INFO = "key_user_info";
    public static final String KEY_EVENT_OBJ = "key_event";

    public static void showMainEventsScreen(Context context,
                                            FragmentManager fragmentManager) {
        EventMainFragment eventMainFragment = new EventMainFragment();


        fragmentManager.beginTransaction().replace(R.id.main_content, eventMainFragment).commit();
    }

    public static void showMainActivity(Context context) {
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    public static void showEventDetailScreen(Context context, Event event) {
        Intent intent = new Intent(context,ScreenEventDetail.class);
        intent.putExtra(KEY_EVENT_OBJ,event);
        context.startActivity(intent);

//        ScreenEventDetail eventMainFragment = new ScreenEventDetail();
//
//
//        fragmentManager.beginTransaction().replace(R.id.main_content, eventMainFragment).commit();
    }

    public static void showLoginScreen(Context context){
        Intent intent = new Intent(context,LoginWithMobile.class);
        context.startActivity(intent);
    }
    public static void showRegistrationScreen(Context context, String userMobileNum,int cachedOTP){
        Intent intent = new Intent(context,RegistrationActivity.class);
        intent.putExtra(KEY_RECIPIENT,userMobileNum);
        intent.putExtra(KEY_OTP,cachedOTP);
        context.startActivity(intent);
    }

    public static void showQRScreen(Context context) {
        Intent intent = new Intent(context,ScreenEventQR.class);
        context.startActivity(intent);
    }

    public static Intent getGalleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent.createChooser(intent, "Choose your picture");

        return intent;
    }

    public static void showScannerScreen(Context context) {
        Intent intent = new Intent(context,QRScanner.class);
        context.startActivity(intent);
    }

    public static void showOTPConfirmScreen(Context context, ResponseRegister userInfo, String recipient, boolean userExists){
        Intent intent = new Intent(context,ScreenOTP.class);
        intent.putExtra(KEY_USER_INFO,userInfo);
        intent.putExtra(KEY_RECIPIENT,recipient);
        intent.putExtra(KEY_USER_EXISTENCE,userExists);
        context.startActivity(intent);
    }
}
