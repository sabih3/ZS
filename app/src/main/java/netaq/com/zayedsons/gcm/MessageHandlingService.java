package netaq.com.zayedsons.gcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import netaq.com.zayedsons.utils.NotificationHelper;

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

        String messageId = remoteMessage.getMessageId();
        String message = remoteMessage.getData().get("body");
        NotificationHelper.showNotification(this,messageId,"Zayed Sons",message);

    }
}

