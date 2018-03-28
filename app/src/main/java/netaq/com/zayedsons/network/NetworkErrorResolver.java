package netaq.com.zayedsons.network;

import android.content.Context;

import netaq.com.zayedsons.R;
import netaq.com.zayedsons.network.model.responses.BaseResponse;

/**
 * Created by sabih on 18-Mar-18.
 */

public class NetworkErrorResolver {

    public static String resolveError(Context mContext, BaseResponse response) {

        String resolvedMessage = mContext.getString(R.string.label_something_went_wrong);
        String statusText = response.getStatusText()+" \n ";
        String supportContactMessage = "Please contact support for assistance";

        int statusCode = response.getStatusCode();
        String stringStatusCode = "1001";

        try {
            stringStatusCode = String.valueOf(statusCode);

        }catch (Exception exc){

        }

        resolvedMessage = statusText + "Error code : "+stringStatusCode +". \n"+
                          supportContactMessage;


        return resolvedMessage;
    }


    public static String getAllPurposeError(Context mContext) {
        String generalPurposeError = mContext.getString(R.string.label_something_went_wrong);

        return generalPurposeError;
    }
}
