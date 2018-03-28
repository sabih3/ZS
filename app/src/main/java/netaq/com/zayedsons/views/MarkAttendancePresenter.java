package netaq.com.zayedsons.views;

import android.content.Context;

import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestAttendance;
import netaq.com.zayedsons.network.model.responses.ResponseAttendance;
import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by sabih on 28-Mar-18.
 */

public class MarkAttendancePresenter {

    private final Context mContext;
    private MarkAttendanceView markAttendanceView;

    public MarkAttendancePresenter(Context context, MarkAttendanceView markAttendanceView) {
        this.mContext = context;
        this.markAttendanceView = markAttendanceView;
    }

    public void requestMarkAttendance(String eventID, String eventDay, String eventTime) {

        markAttendanceView.showProgress();

        CompositeDisposable compositeDisposable = new CompositeDisposable();

        RequestAttendance attendanceRequest = new RequestAttendance();
        attendanceRequest.setToken(UserManager.getUser().getAuthToken());
        attendanceRequest.setUsr(UserManager.getUser().getAccountInfo().getUserID());
        attendanceRequest.setDeviceID(UserManager.getDeviceID());
        attendanceRequest.setRegistrationID(eventID);
        attendanceRequest.setEd(eventDay);
        attendanceRequest.setEx(eventTime);

        Observable<ResponseAttendance> requestAttendance =
                                       RestClient.getAdapter().markAttendance(attendanceRequest);


        compositeDisposable.add(requestAttendance.
                                observeOn(AndroidSchedulers.mainThread()).
                                subscribeOn(Schedulers.io())
                                .subscribe(this::handleAttendanceResponse,this::handleError));

    }


    private void handleAttendanceResponse(ResponseAttendance responseAttendance) {
        markAttendanceView.hideProgress();

        if(responseAttendance.isSuccess()){

            markAttendanceView.onAttendanceMarked();
        }else{

            String resolvedError = NetworkErrorResolver.resolveError(mContext, responseAttendance);

            markAttendanceView.onError(resolvedError);
     }

    }

    private void handleError(Throwable t) {
        markAttendanceView.hideProgress();

        if(t instanceof UnknownHostException){
            markAttendanceView.onNetworkUnAvailable();
        }
    }
}
