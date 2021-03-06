package netaq.com.zayedsons.network;

import java.util.Map;

import io.reactivex.Observable;
import netaq.com.zayedsons.model.Lookup;
import netaq.com.zayedsons.network.model.BaseModel;
import netaq.com.zayedsons.network.model.ClickATellMessageObject;
import netaq.com.zayedsons.network.model.requests.RequestAccountExistence;
import netaq.com.zayedsons.network.model.requests.RequestAttendance;
import netaq.com.zayedsons.network.model.requests.RequestAttendeeInfo;
import netaq.com.zayedsons.network.model.requests.RequestEventGallery;
import netaq.com.zayedsons.network.model.requests.RequestEventJoin;
import netaq.com.zayedsons.network.model.requests.RequestEventList;
import netaq.com.zayedsons.network.model.requests.RequestLookup;
import netaq.com.zayedsons.network.model.requests.RequestQR;
import netaq.com.zayedsons.network.model.requests.RequestRegisterProfile;
import netaq.com.zayedsons.network.model.requests.SMSRequest;
import netaq.com.zayedsons.network.model.requests.UploadFile;
import netaq.com.zayedsons.network.model.responses.BaseResponse;
import netaq.com.zayedsons.network.model.responses.ResponseAttendance;
import netaq.com.zayedsons.network.model.responses.ResponseAttendeeInfo;
import netaq.com.zayedsons.network.model.responses.ResponseClickATellAccount;
import netaq.com.zayedsons.network.model.responses.ResponseEventGallery;
import netaq.com.zayedsons.network.model.responses.ResponseEventList;
import netaq.com.zayedsons.network.model.responses.ResponseEventQR;
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

    @POST(EndPoints.EVENT_JOIN)
    Observable<BaseResponse> requestJoinEvent(@Body RequestEventJoin requestEventJoin);

    @POST(EndPoints.EVENT_QR)
    Observable<ResponseEventQR> requestEventQR(@Body RequestQR requestQR);

    @POST(EndPoints.LOOKUP_SPONSOR)
    Call<Lookup> getSponsors();

    @POST(EndPoints.USER_UPDATE_PROFILE)
    Observable<ResponseRegister> updateProfile(@Body RequestRegisterProfile updateRequest);

    @POST(EndPoints.EVENT_GALLERY)
    Observable<ResponseEventGallery> getEventGallery(@Body RequestEventGallery eventGalleryRequest);

    @POST(EndPoints.ATTENDEE_INFO)
    Observable<ResponseAttendeeInfo> getAttendeeInfo(@Body RequestAttendeeInfo attendeeInfo);


    @POST(EndPoints.ATTENDANCE)
    Observable<ResponseAttendance> markAttendance(@Body RequestAttendance attendance);

    @POST(EndPoints.ACCOUNT_LOGOUT)
    Observable<BaseResponse> userLogOut(@Body BaseModel base);
}
