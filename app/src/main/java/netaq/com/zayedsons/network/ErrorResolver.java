package netaq.com.zayedsons.network;

import android.content.Context;

/**
 * Created by sabih on 12-Mar-18.
 */

public class ErrorResolver {
    public static void getResolvedError(Context context, int statusCode) {
        switch (statusCode){

            case 2:
                //Error
            break;

            case 3://Authentication Failed
            case 4://Invalid Token
            case 5://Access Denied

            break;

            case 6://Parameter Missing
            case 7: //Invalid Data

            break;


            case 8:
                //Not found
            break;

            case 9:
                //Record Exists
            break;


            case 10:
                //Record is Blocked
            break;

            case 11:
                //Expired
            break;

            case 12:
                //Invalid call
            break;

            case 13:
                //Invalid Email Address
            break;

            case 14:
                //Invalid Mobile No.
            break;
        }
    }
}
