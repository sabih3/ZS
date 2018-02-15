package netaq.com.zayedsons;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import netaq.com.zayedsons.core.NavigationController;

public class ScreenSplash extends Activity {


    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_splash);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                NavigationController.showMainActivity(ScreenSplash.this);
                ScreenSplash.this.finish();
            }
        },3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
