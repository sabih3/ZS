package netaq.com.zayedsons.views;

import netaq.com.zayedsons.network.model.responses.ResponseRegister;

/**
 * Created by sabih on 22-Mar-18.
 */

public interface UpdateProfileView extends BaseView{

    void onProfilePhotoUploaded(String fileURL);
    void onProfileUpdatedSuccessfully(ResponseRegister responseRegister);
    void showPhotoUploadProgress();

    void hidePhotoUploadProgress();
}
