package netaq.com.zayedsons.views.registration;

import android.content.Context;

import java.net.UnknownHostException;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.model.AccountInfo;
import netaq.com.zayedsons.model.Profile;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestRegisterProfile;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;
import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by sabih on 18-Feb-18.
 */

public class RegistrationPresenter {

    private Context mContext;
    private RegistrationView viewListener;
    private CompositeDisposable mCompositeDisposable;

    public RegistrationPresenter(Context context, RegistrationView registrationView) {
        this.mContext = context;
        this.viewListener = registrationView;
    }

    public void requestRegisterProfile(HashMap<String, Object> bioInfoData,
                                       HashMap<String, String> educationalData) {
        mCompositeDisposable = new CompositeDisposable();
        AccountInfo accountInfo = new AccountInfo();

        accountInfo.setUserID((String)bioInfoData.get("userID"));
        accountInfo.setUserName((String)bioInfoData.get("number"));
        accountInfo.setPassword(String.valueOf(bioInfoData.get("otp")));
        accountInfo.setGender((Integer)bioInfoData.get("gender"));
        accountInfo.setEmail((String)bioInfoData.get("email"));
        accountInfo.setUserTypeID(Constants.USER_TYPE_NORMAL);



        Profile profile = new Profile();
        profile.setName1((String)bioInfoData.get("name1"));
        profile.setName2((String)bioInfoData.get("name2"));
        profile.setName3((String)bioInfoData.get("name3"));
        profile.setName4((String)bioInfoData.get("name4"));
        profile.setProfilePic((String)bioInfoData.get("profilePic"));
        profile.setdOB((String)bioInfoData.get("dob"));


        String major = educationalData.get("major");
        String university = educationalData.get("university");
        String emiratesID = educationalData.get("emiratesID");
        String sponsor = educationalData.get("sponsor");
        String city = educationalData.get("city");

        profile.setCourseMajor(major);
        profile.setUniversity(university);
        profile.seteIDNo(emiratesID);
        profile.setSponsorID("6d2b7f54-c466-40da-915b-eb73ae7519cb");
        profile.setCityID(city);


        RequestRegisterProfile registerRequest = new RequestRegisterProfile();
        registerRequest.setAccountInfo(accountInfo);
        registerRequest.setProfile(profile);

        registerRequest.setDeviceID(UserManager.getDeviceID());

        mCompositeDisposable.add(RestClient.getAdapter().registerProfile(registerRequest)
                             .observeOn(AndroidSchedulers.mainThread())
                             .subscribeOn(Schedulers.io())
                             .subscribe(this::handleResponse,this::handleError));


    }

    private void handleResponse(ResponseRegister responseRegister) {
        viewListener.hideProgress();


        if(responseRegister.isSuccess()){
            UserManager.setUser(responseRegister);
            viewListener.OnRegistrationSuccess();

        }else{
            String resolvedError = NetworkErrorResolver.resolveError(mContext, responseRegister);
            viewListener.onError(resolvedError);
        }
    }


    private void handleError(Throwable t) {
        viewListener.hideProgress();
        if(t instanceof UnknownHostException){
            viewListener.onNetworkUnAvailable();
        }else{
            viewListener.onError(NetworkErrorResolver.getAllPurposeError(mContext));
        }

    }


}
