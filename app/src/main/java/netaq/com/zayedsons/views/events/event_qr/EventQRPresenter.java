package netaq.com.zayedsons.views.events.event_qr;

import android.content.Context;
import android.net.Network;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.EndPoints;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestQR;
import netaq.com.zayedsons.network.model.responses.ResponseEventQR;
import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by sabih on 14-Mar-18.
 */

public class EventQRPresenter {

    private Context mContext;
    private EventQRView viewListener;

    public EventQRPresenter(Context context, EventQRView viewListener) {
        this.mContext = context;
        this.viewListener = viewListener;
    }


    public void requestQR(String registrationID, String day) {
        CompositeDisposable mCompositeDisposable = new CompositeDisposable();

        viewListener.showProgress();

        RequestQR requestQR = new RequestQR();

        requestQR.setToken(UserManager.getUser().getAuthToken());
//        requestQR.setDeviceID("123");
        requestQR.setRegistrationID(registrationID);
        requestQR.setDay(day);
        requestQR.setUsr(UserManager.getUser().getAccountInfo().getUserID());



        mCompositeDisposable.add(RestClient.getAdapter()
                            .requestEventQR(requestQR)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse,this::handleError));

    }

    private void handleResponse(ResponseEventQR responseEventQR) {
        viewListener.hideProgress();

        if(responseEventQR.isSuccess()){
            String returnedURL = responseEventQR.getFileURL();
            String substring = returnedURL.substring(2);

            returnedURL = EndPoints.BASE_URL + substring;

            viewListener.onQRReceived(returnedURL);
        }
        else{

            //Extract Error
            viewListener.onError(NetworkErrorResolver.resolveError(mContext,responseEventQR));
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
