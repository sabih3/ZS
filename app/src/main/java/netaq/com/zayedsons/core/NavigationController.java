package netaq.com.zayedsons.core;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import netaq.com.zayedsons.views.MainActivity;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.views.QRScanner;
import netaq.com.zayedsons.views.events.EventMainFragment;
import netaq.com.zayedsons.views.events.ScreenEventQR;
import netaq.com.zayedsons.views.events.event_detail.ScreenEventDetail;
import netaq.com.zayedsons.views.login.ScreenLogin;
import netaq.com.zayedsons.views.registration.RegistrationActivity;

/**
 * Created by sabih on 08-Feb-18.
 */

public class NavigationController {
    public static void showMainEventsScreen(Context context,
                                            FragmentManager fragmentManager) {
        EventMainFragment eventMainFragment = new EventMainFragment();


        fragmentManager.beginTransaction().replace(R.id.main_content, eventMainFragment).commit();
    }

    public static void showMainActivity(Context context) {
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    public static void showEventDetailScreen(Context context) {
        Intent intent = new Intent(context,ScreenEventDetail.class);
        context.startActivity(intent);

//        ScreenEventDetail eventMainFragment = new ScreenEventDetail();
//
//
//        fragmentManager.beginTransaction().replace(R.id.main_content, eventMainFragment).commit();
    }

    public static void showLoginScreen(Context context){
        Intent intent = new Intent(context,ScreenLogin.class);
        context.startActivity(intent);
    }
    public static void showRegistrationScreen(Context context){
        Intent intent = new Intent(context,RegistrationActivity.class);
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
}
