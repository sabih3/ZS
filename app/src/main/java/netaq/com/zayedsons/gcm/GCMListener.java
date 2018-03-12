package netaq.com.zayedsons.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by sabih on 12-Mar-18.
 */

public class GCMListener extends GcmListenerService{
    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        super.onMessageReceived(s, bundle);
    }
}
