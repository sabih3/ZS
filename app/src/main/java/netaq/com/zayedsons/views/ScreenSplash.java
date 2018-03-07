package netaq.com.zayedsons.views;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;

import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.utils.UserManager;

public class ScreenSplash extends Activity {


    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_splash);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(UserManager.getUser()==null || UserManager.getUser().getAccountInfo()==null ){
                    NavigationController.showLoginScreen(ScreenSplash.this);
                }else{
                    NavigationController.showMainActivity(ScreenSplash.this);
                }



                ScreenSplash.this.finish();
            }
        },3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
