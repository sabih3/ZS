package netaq.com.zayedsons.gcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by sabih on 17-Mar-18.
 */

public class MessageHandlingService extends FirebaseMessagingService {
    public MessageHandlingService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}

