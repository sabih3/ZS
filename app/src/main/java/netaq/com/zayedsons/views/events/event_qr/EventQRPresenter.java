package netaq.com.zayedsons.views.events.event_qr;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.EndPoints;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestQR;
import netaq.com.zayedsons.network.model.responses.ResponseEventQR;
import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by sabih on 14-Mar-18.
 */

public class EventQRPresenter {

    private EventQRView viewListener;

    public EventQRPresenter(EventQRView viewListener) {

        this.viewListener = viewListener;
    }


    public void requestQR(String registrationID, String day) {
        CompositeDisposable mCompositeDisposable = new CompositeDisposable();

        viewListener.showProgress();

        RequestQR requestQR = new RequestQR();

        requestQR.setToken(UserManager.getUser().getAuthToken());
        requestQR.setDevID("123");
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

        if(responseEventQR.getStatusCode()==1){

            String returnedURL = responseEventQR.getFileURL();
            String substring = returnedURL.substring(2);

            returnedURL = EndPoints.BASE_URL + substring;

            viewListener.onQRReceived(returnedURL);
        }else{

            //Extract Error
            viewListener.onError();
        }


    }

    private void handleError(Throwable throwable) {
        viewListener.hideProgress();
    }
}
