package netaq.com.zayedsons.network;

import android.content.Context;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.model.ClickATellMessageObject;
import netaq.com.zayedsons.network.model.ResponseClickATellAccount;
import netaq.com.zayedsons.network.model.ResponseSMS;
import netaq.com.zayedsons.network.model.SMSRequest;
import netaq.com.zayedsons.views.BaseView;
import netaq.com.zayedsons.views.login.LoginWithMobile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sabih on 5/16/2017.
 */

public class LoginWithMobilePresenter {

    private Context mContext;
    private LoginMobileView mListener;
    private CompositeDisposable mCompositeDisposable;

    public LoginWithMobilePresenter(Context context, LoginMobileView viewListener) {
        this.mContext = context;
        this.mListener = viewListener;
        mCompositeDisposable = new CompositeDisposable();
    }

    public static HashMap<String, String> getClickATellHeaders(){

        HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("Authorization","LAL0g4lpR5WJS6Avb0buZw==");
        headerMap.put("Content-Type","application/json");
        headerMap.put("Accept","application/json");
        //headerMap.put("X-Version","1");

        return headerMap;

    }

    public void sendOTP(String recipient, int OTP){
        int otp = OTP;

        String[] to = new String[1];

        to[0]= recipient;

       SMSRequest request = new SMSRequest("Welcome to Zayed Sons. Your OTP is :"+otp, to);

       ClickATellMessageObject messageObj = new ClickATellMessageObject("Welcome to Zayed Sons. Your OTP is :"+otp,
                               new String[]{recipient});


       Observable<ResponseClickATellAccount> OTPResponse = ClickATellClient.getAdapter()
                                                           .sendOTP(messageObj)
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribeOn(Schedulers.io());


//        mCompositeDisposable.add(ClickATellClient.getAdapter()
//                            .sendOTP(messageObj)
//                             .observeOn(AndroidSchedulers.mainThread())
//                             .subscribeOn(Schedulers.io())
//                             .subscribe(this::handleResponse, this::handleError));



        mListener.showProgress();

    }

    private  void handleError(Throwable throwable) {
        mListener.hideProgress();
        mListener.onError();
    }
    private  void handleResponse(ResponseClickATellAccount response) {
        mListener.hideProgress();
        mListener.onSmsSent();
    }

    public interface LoginMobileView extends BaseView{

        void onSmsSent();
        void onSmsSentFailure();

    }
}
