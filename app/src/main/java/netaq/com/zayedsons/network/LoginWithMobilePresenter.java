package netaq.com.zayedsons.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.net.UnknownHostException;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.model.ClickATellMessageObject;
import netaq.com.zayedsons.network.model.UserVerification;
import netaq.com.zayedsons.network.model.requests.RequestAccountExistence;
import netaq.com.zayedsons.network.model.requests.SMSRequest;
import netaq.com.zayedsons.network.model.responses.ResponseClickATellAccount;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;
import netaq.com.zayedsons.utils.UserManager;
import netaq.com.zayedsons.views.BaseView;

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

    public static HashMap<String, String> getClickATellHeadersForPlatformAccount(){

        HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("Authorization","LAL0g4lpR5WJS6Avb0buZw==");
        headerMap.put("Content-Type","application/json");
        headerMap.put("Accept","application/json");
        //headerMap.put("X-Version","1");

        return headerMap;

    }

    public void sendOTP(final String recipient, int OTP) {
        int otp = OTP;

        String[] to = new String[1];

        to[0] = recipient;

        SMSRequest request = new SMSRequest("Welcome to Zayed Sons. Your OTP is :" + otp, to);

       ClickATellMessageObject messageObj = new ClickATellMessageObject(
                                            "Welcome to Zayed Sons. Your OTP is :"+" "+otp,
                                             new String[]{recipient});


        RequestAccountExistence accountExistence = new RequestAccountExistence();
        accountExistence.setId(recipient);
//        accountExistence.setDeviceID(UserManager.getDeviceID());



        Observable<ResponseClickATellAccount> OTPResponse = ClickATellClient.getAdapter()
                .sendOTP(messageObj)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

        Observable<ResponseRegister> existenceResponse = RestClient.getAdapter()
                .checkExistence(accountExistence)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());


        Observable<UserVerification> merged = Observable.zip(existenceResponse,OTPResponse,
                new BiFunction<ResponseRegister,ResponseClickATellAccount, UserVerification>() {
                    @Override
                    public UserVerification apply(ResponseRegister responseExistence,
                                                  ResponseClickATellAccount responseClickATellAccount)
                                                  throws Exception {

                        UserVerification verification = new UserVerification();
                        verification.responseExistence = responseExistence;
                        verification.responseSMS = responseClickATellAccount;

                        mListener.hideProgress();
                        boolean userExists = false;

                        if(responseClickATellAccount.data.getMessage()!=null){
                            if(responseClickATellAccount.data.getMessage().get(0).isAccepted()){

                                if(responseExistence.isSuccess()){
                                    if(responseExistence.getStatusCode()==1){
                                        userExists = true;
                                    }

                                    else if(responseExistence.getStatusCode()==8){
                                        userExists = false;
                                    }
                                }


                                mListener.onSmsSent(responseExistence,recipient, userExists);
                            }
                        }else{
                            mListener.onSmsSentFailure();
                        }

                        return verification;
                    }
                });

        merged.subscribe(new Observer<UserVerification>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserVerification userVerification) {

            }

            @Override
            public void onError(Throwable e) {
                mListener.hideProgress();

                if(e instanceof UnknownHostException ){
                    mListener.onNetworkUnAvailable();
                }else{
                    if(e instanceof HttpException && ((HttpException) e).code()==400 ){
                        mListener.onSmsSentFailure();

                    }
                    else{
                        mListener.onError(NetworkErrorResolver.getAllPurposeError(mContext));
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });


        mListener.showProgress();

    }


    public interface LoginMobileView extends BaseView{

        void onSmsSent(ResponseRegister responseExistence, String recipient, boolean userExists);
        void onSmsSentFailure();

    }
}
