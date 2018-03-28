package netaq.com.zayedsons.views;

import android.content.Context;

import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestAttendeeInfo;
import netaq.com.zayedsons.network.model.responses.ResponseAttendeeInfo;
import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by sabih on 28-Mar-18.
 */

public class AttendancePresenter {

    private Context mContext;
    private AttendanceView attendanceView;

    public AttendancePresenter(Context context, AttendanceView attendanceView) {
        this.mContext = context;
        this.attendanceView = attendanceView;

    }

    public void requestAttendanceInfo(String eventRegistrationID){

        attendanceView.showProgress();

        CompositeDisposable compositeDisposable = new CompositeDisposable();

        RequestAttendeeInfo attendeeInfo = new RequestAttendeeInfo();
        attendeeInfo.setEventID(eventRegistrationID);
        attendeeInfo.setToken(UserManager.getUser().getAuthToken());
        attendeeInfo.setUserID(UserManager.getUser().getAccountInfo().getUserID());
        attendeeInfo.setDeviceID(UserManager.getDeviceID());

        Observable<ResponseAttendeeInfo> attendeeInfoRequest =
                                         RestClient.getAdapter().getAttendeeInfo(attendeeInfo);


        compositeDisposable.add(attendeeInfoRequest
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(this::onAttendanceInfoResponse,this::onAttendanceInfoError));

    }



    private void onAttendanceInfoResponse(ResponseAttendeeInfo responseAttendeeInfo) {
        attendanceView.hideProgress();

        if(responseAttendeeInfo.isSuccess()){

            attendanceView.onAttendanceInfoReceived(responseAttendeeInfo);

        }else{
            String resolvedError = NetworkErrorResolver.resolveError(mContext, responseAttendeeInfo);

            attendanceView.onError(resolvedError);
        }
    }


    private void onAttendanceInfoError(Throwable t) {
        attendanceView.hideProgress();

        if(t instanceof UnknownHostException){
            attendanceView.onNetworkUnAvailable();
        }
    }



}
