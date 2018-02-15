package netaq.com.zayedsons.core;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import netaq.com.zayedsons.MainActivity;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.ScreenSplash;
import netaq.com.zayedsons.events.EventMainFragment;
import netaq.com.zayedsons.events.ScreenEventQR;
import netaq.com.zayedsons.events.event_detail.ScreenEventDetail;

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

    public static void showQRScreen(Context context) {
        Intent intent = new Intent(context,ScreenEventQR.class);
        context.startActivity(intent);
    }
}
