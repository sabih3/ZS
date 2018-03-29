package netaq.com.zayedsons.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * Created by sabih on 25-Mar-18.
 */

public class NotificationHelper {

    public static void showNotification(Context context, String notificationID,
                                        String title, String desc){
        Notification.Builder mBuilder;
        mBuilder = new Notification.Builder(context);
        NotificationManager mNotifyManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        mBuilder.setContentTitle(title)
                .setContentText(desc)
                .setSmallIcon(android.R.drawable.stat_notify_error)
                .setStyle((new Notification.BigTextStyle().bigText(desc)));

        // Issues the notification
        //int intID = Integer.parseInt(notificationID);

        int intID = OTPGenerator.generateRandomNumber();

        mNotifyManager.notify(intID, mBuilder.build());
    }
}
