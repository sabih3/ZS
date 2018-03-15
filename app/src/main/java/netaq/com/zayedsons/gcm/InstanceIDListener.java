package netaq.com.zayedsons.gcm;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

import java.io.IOException;

import netaq.com.zayedsons.R;

/**
 * Created by sabih on 12-Mar-18.
 */

public class InstanceIDListener extends InstanceIDListenerService{

    public InstanceIDListener() {
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Log.d("Token","OnTokenRefresh");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
