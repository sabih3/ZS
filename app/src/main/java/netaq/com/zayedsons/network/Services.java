package netaq.com.zayedsons.network;

import java.util.Map;

import io.reactivex.Observable;
import netaq.com.zayedsons.model.Lookup;
import netaq.com.zayedsons.network.model.ClickATellMessageObject;
import netaq.com.zayedsons.network.model.requests.RequestAccountExistence;
import netaq.com.zayedsons.network.model.requests.RequestEventList;
import netaq.com.zayedsons.network.model.requests.RequestLookup;
import netaq.com.zayedsons.network.model.requests.RequestRegisterProfile;
import netaq.com.zayedsons.network.model.requests.SMSRequest;
import netaq.com.zayedsons.network.model.requests.UploadFile;
import netaq.com.zayedsons.network.model.responses.ResponseClickATellAccount;
import netaq.com.zayedsons.network.model.responses.ResponseEventList;
import netaq.com.zayedsons.network.model.responses.ResponseFileUpload;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;
import netaq.com.zayedsons.network.model.responses.ResponseSMS;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by sabih on 14-Feb-18.
 */

public interface Services {

    @POST(EndPoints.LOOKUP_CITIES)
    Call<Lookup> getCities(@Body RequestLookup requestLookup);


    @POST(EndPoints.FILE_UPLOAD)
    Observable<ResponseFileUpload> uploadPhoto(@Body UploadFile uploadFile);

    @POST(EndPoints.API_SMS)
    Observable<ResponseSMS> sendOTP(@HeaderMap Map<String,String> headers,
                                    @Body SMSRequest smsRequest);

    @POST(EndPoints.ACCOUNT_EXISTENCE)
    Observable<ResponseRegister> checkExistence(@Body RequestAccountExistence existence);

    @POST(".")
    Observable<ResponseClickATellAccount> sendOTP(@Body ClickATellMessageObject messageObject);

    @POST(EndPoints.USER_REGISTER)
    Observable<ResponseRegister> registerProfile(@Body RequestRegisterProfile registerProfile);

    @POST(EndPoints.EVENT_LIST)
    Observable<ResponseEventList> getAllEvents(@Body RequestEventList eventsRequest);
}
