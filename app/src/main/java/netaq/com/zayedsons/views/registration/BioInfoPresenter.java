package netaq.com.zayedsons.views.registration;

import android.content.Context;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.network.EndPoints;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.UploadFile;
import netaq.com.zayedsons.network.model.responses.ResponseFileUpload;

/**
 * Created by sabih on 01-Mar-18.
 */

public class BioInfoPresenter {


    private Context mContext;
    private BioInfoView bioInfoView;

    private CompositeDisposable mCompositeDisposable;
    //private UUID userID = UUID.randomUUID();;

    public BioInfoPresenter(Context context, BioInfoView bioInfoView) {
        this.mContext = context;
        this.bioInfoView = bioInfoView;
    }



    public void uploadProfilePhoto(String userID,String encodedString) {
        mCompositeDisposable = new CompositeDisposable();
        bioInfoView.showPhotoUploadProgress();

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
        bioInfoView.hidePhotoUploadProgress();
        if(t instanceof UnknownHostException){
            bioInfoView.onNetworkUnAvailable();
        }else{
            bioInfoView.onError(NetworkErrorResolver.getAllPurposeError(mContext));
        }
    }

    private void responsePhotoUpload(ResponseFileUpload responseFileUpload) {

        if(responseFileUpload.isSuccess()){
            String fileURL = responseFileUpload.getFileURL();
            String substring = fileURL.substring(2);
            fileURL = EndPoints.BASE_URL + substring;

            bioInfoView.onPhotoUploaded(fileURL);
        } else{
            bioInfoView.onError(NetworkErrorResolver.resolveError(mContext,responseFileUpload));
        }


    }
}
