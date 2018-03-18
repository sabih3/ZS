package netaq.com.zayedsons.gcm;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import netaq.com.zayedsons.R;

/**
 * Created by sabih on 12-Mar-18.
 */

public class InstanceIDListener extends FirebaseInstanceIdService {

    private static final String TAG = "InstanceIDListener";
    public static final String KEY_TOKEN = "key_token";

    public InstanceIDListener() {
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        Intent intent = new Intent(this, RegistrationIntentService.class);
        intent.putExtra(KEY_TOKEN,refreshedToken);
        startService(intent);
    }
}
