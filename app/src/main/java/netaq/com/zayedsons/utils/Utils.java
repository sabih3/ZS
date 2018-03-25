package netaq.com.zayedsons.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import netaq.com.zayedsons.network.EndPoints;


/**
 * Created by Sabih on 19/design_login_mobile/2018.
 */

public class Utils {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    //The following code can be used to get the real path from the URI
    /* Get the real path from the URI */
    public static String getPathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    public static void setLocale(Activity activity, Locale locale) {

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());

    }


    public static String getPathBasedOnSDK(Context context, Uri uri) {

        String realPathFromURI = "";

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            realPathFromURI = ImageUtils.getRealPathFromURI_API11to18(context, uri);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            realPathFromURI = ImageUtils.getRealPathFromURI_API19(context, uri);
        }

        return realPathFromURI;

    }

    public final static boolean isValidEmail(String target) {
        Pattern pattern = Pattern.compile(Regex.emailRegex, Pattern.CASE_INSENSITIVE);
        if (target == null) {
            return false;
        } else {
            return pattern.matcher(target).find();
        }
    }

    public final static boolean isValidPhone(String target) {
        Pattern pattern = Pattern.compile(Regex.phoneRegex);
        if (target == null) {
            return false;
        } else {
            return pattern.matcher(target).find();
        }
    }

    public static void getDeviceID(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(),
                           Settings.Secure.ANDROID_ID);
    }

    public static String getTime(String displayDateTime) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat requiredFormat = new SimpleDateFormat("hh:mm a");
        String time = "";
        try {
            Date date = inputDateFormat.parse(displayDateTime);
            time = requiredFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return time;
    }

    public static String getDate(String displayDateTime){
        SimpleDateFormat inputDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat requiredDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String formattedDate = "";

        try {
            Date date = inputDateTimeFormat.parse(displayDateTime);
            formattedDate = requiredDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public static String getResolvedURL(String inputURL) {
        String resolvedURL = "";


        String substring = inputURL.substring(2);
        resolvedURL = EndPoints.BASE_URL + substring;
        return resolvedURL;
    }
}
