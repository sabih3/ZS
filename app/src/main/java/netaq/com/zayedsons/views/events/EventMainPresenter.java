package netaq.com.zayedsons.views.events;

import android.content.Context;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.ErrorResolver;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestEventList;
import netaq.com.zayedsons.network.model.responses.ResponseEventList;
import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by sabih on 12-Mar-18.
 */

public class EventMainPresenter {

    private EventMainView viewListener;
    private Context context;

    public EventMainPresenter(EventMainView mainEventsView) {
        this.viewListener = mainEventsView;

    }

    public void getEvents(Context context) {

        viewListener.showProgress();

        this.context = context;

        CompositeDisposable compositeDisposable = new CompositeDisposable();

        RequestEventList eventsRequest = new RequestEventList();
//        eventsRequest.setDeviceID("123");
        eventsRequest.setToken(UserManager.getUser().getAuthToken());
        eventsRequest.setUsr(UserManager.getUser().getAccountInfo().getUserID());

        compositeDisposable.add(RestClient.getAdapter()
                            .getAllEvents(eventsRequest)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse,this::handleError));

    }


    private void handleResponse(ResponseEventList responseEventList) {
        viewListener.hideProgress();
        if(responseEventList.isSuccess()){

            viewListener.onEventsFetched(responseEventList);

        }else{
            String resolvedError = NetworkErrorResolver.resolveError(context, responseEventList);

            viewListener.onError(resolvedError);
        }

    }

    private void handleError(Throwable t) {
        viewListener.hideProgress();
        if(t instanceof UnknownHostException){
            viewListener.onNetworkUnAvailable();
        }else{
            viewListener.onError(NetworkErrorResolver.getAllPurposeError(context));
        }
    }
}
