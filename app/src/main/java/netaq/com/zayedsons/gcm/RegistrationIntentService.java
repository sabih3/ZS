package netaq.com.zayedsons.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import netaq.com.zayedsons.R;

/**
 * Created by sabih on 12-Mar-18.
 */

public class RegistrationIntentService extends IntentService {

    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        String stringExtra = intent.getStringExtra(InstanceIDListener.KEY_TOKEN);
    }


}
