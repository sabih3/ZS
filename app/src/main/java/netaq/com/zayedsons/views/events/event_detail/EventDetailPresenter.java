package netaq.com.zayedsons.views.events.event_detail;

import android.content.Context;

import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestEventGallery;
import netaq.com.zayedsons.network.model.requests.RequestEventJoin;
import netaq.com.zayedsons.network.model.responses.BaseResponse;
import netaq.com.zayedsons.network.model.responses.ResponseEventGallery;
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
//        requestEventJoin.setDeviceID(UserManager.getDeviceID());
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

    public void getEventGallery(String eventID){
        CompositeDisposable mCompositeDisposable = new CompositeDisposable();

        RequestEventGallery eventGalleryRequest = new RequestEventGallery();
        eventGalleryRequest.setToken(UserManager.getUser().getAuthToken());
//        eventGalleryRequest.setDeviceID(UserManager.getDeviceID());
        eventGalleryRequest.setRefID(eventID);
        eventGalleryRequest.setUsr(UserManager.getUser().getAccountInfo().getUserID());

        Observable<ResponseEventGallery> eventGallery = RestClient.getAdapter().
                                                        getEventGallery(eventGalleryRequest);



        mCompositeDisposable.add(eventGallery
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleGalleryResponse,this::handleGalleryError));


    }


    private void handleGalleryResponse(ResponseEventGallery responseEventGallery) {
        if(responseEventGallery.isSuccess()){

            if(responseEventGallery.getAlbums() == null){
                viewListener.onEmptyGallery();
            }else{
                List<ResponseEventGallery.Albums.Gallery> galleryList =
                        responseEventGallery.getAlbums().get(0).getGallery();

                viewListener.onGalleryFetched(galleryList);

            }


        }else{

            String resolvedError = NetworkErrorResolver.
                                  resolveError(mContext, responseEventGallery);

            viewListener.onError(resolvedError);
        }

    }

    private void handleGalleryError(Throwable t) {

    }


}
