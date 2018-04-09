package netaq.com.zayedsons.views;

import android.content.Context;

import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.BaseModel;
import netaq.com.zayedsons.network.model.responses.BaseResponse;
import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by sabih on 09-Apr-18.
 */

public class MainActivityPresenter {

    private Context mContext;
    private MainActivityView mainActivityView;

    public MainActivityPresenter(Context mContext, MainActivityView mainActivityView) {
        this.mContext = mContext;
        this.mainActivityView = mainActivityView;
    }


    public void requestLogOut() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        BaseModel baseModel = new BaseModel();
        baseModel.setUsr(UserManager.getUser().getAccountInfo().getUserID());
        baseModel.setToken(UserManager.getUser().getAuthToken());


        Observable<BaseResponse> logoutRequest = RestClient.getAdapter().userLogOut(baseModel);

        compositeDisposable.add(logoutRequest
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::onLogOutSuccess,this::onLogOutError));

        mainActivityView.showProgress();
    }

    private void onLogOutSuccess(BaseResponse baseResponse) {
        mainActivityView.hideProgress();

        if(baseResponse.isSuccess()){
            mainActivityView.onLogOutSuccess();
        }else{
            String statusText = baseResponse.getStatusText();
            mainActivityView.onError(statusText);

        }
    }

    private void onLogOutError(Throwable t) {

        mainActivityView.hideProgress();

        if(t instanceof UnknownHostException){
            mainActivityView.onNetworkUnAvailable();
        }else{
            mainActivityView.onError(NetworkErrorResolver.getAllPurposeError(mContext));
        }
    }
}
