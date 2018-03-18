package netaq.com.zayedsons.views.events.event_detail;

import android.content.Context;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestEventJoin;
import netaq.com.zayedsons.network.model.responses.BaseResponse;
import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by sabih on 12-Feb-18.
 */

public class EventDetailPresenter {

    private Context mContext;
    private EventDetailView viewListener;

    public EventDetailPresenter(Context context , EventDetailView viewListener) {
        this.mContext = context;
        this.viewListener = viewListener;
    }

    public void sendEventJoinRequest(String eventID){
        CompositeDisposable mCompositeDisposable = new CompositeDisposable();

        RequestEventJoin requestEventJoin = new RequestEventJoin();
        requestEventJoin.setDevID("123");
        requestEventJoin.setId(eventID);
        requestEventJoin.setUsr(UserManager.getUser().getAccountInfo().getUserID());
        requestEventJoin.setToken(UserManager.getUser().getAuthToken());


        RestClient.getAdapter().requestJoinEvent(requestEventJoin);

        mCompositeDisposable.add(RestClient.getAdapter().requestJoinEvent(requestEventJoin)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse,this::handleError));


    }

    private void handleResponse(BaseResponse baseResponse) {
        if(baseResponse.isSuccess()){
            viewListener.onJoinRequestSubmitted();
        } else{
            NetworkErrorResolver.resolveError(mContext,baseResponse);
        }
    }

    private void handleError(Throwable t) {
        if(t instanceof UnknownHostException){
            viewListener.onNetworkUnAvailable();
        }else{
            viewListener.onError(NetworkErrorResolver.getAllPurposeError(mContext));
        }

    }


}
