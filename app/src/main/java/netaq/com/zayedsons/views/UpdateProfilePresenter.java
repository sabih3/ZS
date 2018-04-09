package netaq.com.zayedsons.views;

import android.content.Context;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.network.EndPoints;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestRegisterProfile;
import netaq.com.zayedsons.network.model.requests.UploadFile;
import netaq.com.zayedsons.network.model.responses.ResponseFileUpload;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;

/**
 * Created by sabih on 22-Mar-18.
 */

public class UpdateProfilePresenter {

    private final UpdateProfileView updateViewListener;
    private final Context mContext;
    private CompositeDisposable mCompositeDisposable;

    public UpdateProfilePresenter(Context context, UpdateProfileView updateProfileView) {
        this.mContext = context;
        this.updateViewListener  = updateProfileView;
    }

    public void uploadProfilePhoto(String userID,String encodedString) {
        mCompositeDisposable = new CompositeDisposable();
        updateViewListener.showPhotoUploadProgress();

        UploadFile uploadFile = new UploadFile();
        uploadFile.setForID(Constants.FOR_ID_FILE_UPLOAD);
        uploadFile.setFileBase64(encodedString);
        uploadFile.setRefID(userID);


        mCompositeDisposable.add(RestClient.getAdapter().uploadPhoto(uploadFile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::responsePhotoUpload,this::errorPhotoUpload));
    }

    private void errorPhotoUpload(Throwable t) {
        updateViewListener.hidePhotoUploadProgress();
        if(t instanceof UnknownHostException){
            updateViewListener.onNetworkUnAvailable();
        }else{
            updateViewListener.onError(NetworkErrorResolver.getAllPurposeError(mContext));
        }
    }

    private void responsePhotoUpload(ResponseFileUpload responseFileUpload) {

        if(responseFileUpload.isSuccess()){
            String fileURL = responseFileUpload.getFileURL();
            String substring = fileURL.substring(2);
            fileURL = EndPoints.BASE_URL + substring;

            updateViewListener.onProfilePhotoUploaded(fileURL);
        } else{
            updateViewListener.onError(NetworkErrorResolver.resolveError(mContext,responseFileUpload));
        }


    }

    public void updateProfile(RequestRegisterProfile updateRequest) {
        mCompositeDisposable = new CompositeDisposable();

        updateViewListener.showProgress();

        mCompositeDisposable.add(RestClient.getAdapter().updateProfile(updateRequest)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::updateProfileResponse,this::updateProfileError));


    }

    private void updateProfileError(Throwable t) {

        updateViewListener.hideProgress();

        if(t instanceof UnknownHostException){
            updateViewListener.onNetworkUnAvailable();
        }
    }

    private void updateProfileResponse(ResponseRegister responseRegister) {
        updateViewListener.hideProgress();

        if(responseRegister.isSuccess()){

            updateViewListener.onProfileUpdatedSuccessfully(responseRegister);
        }else{

            String resolvedError = NetworkErrorResolver.resolveError(mContext, responseRegister);
            updateViewListener.onError(resolvedError);
        }
    }
}
