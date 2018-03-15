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

        InstanceID instanceID = InstanceID.getInstance(this);

        try {
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                           GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
