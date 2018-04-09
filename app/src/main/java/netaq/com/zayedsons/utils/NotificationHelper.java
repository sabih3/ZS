package netaq.com.zayedsons.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.Settings;

import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import netaq.com.zayedsons.R;
import netaq.com.zayedsons.views.MainActivity;

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


        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        mBuilder.setContentTitle(title)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_notif)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_notif_large))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setStyle((new Notification.BigTextStyle().bigText(desc)))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Issues the notification
        //int intID = Integer.parseInt(notificationID);

        int intID = OTPGenerator.generateRandomNumber();

        mNotifyManager.notify(intID, mBuilder.build());
    }
}
