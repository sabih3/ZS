package netaq.com.zayedsons.views.registration;

import netaq.com.zayedsons.views.BaseView;

/**
 * Created by sabih on 01-Mar-18.
 */

public interface BioInfoView extends BaseView {


    void onPhotoUploaded(String fileURL);

    void showPhotoUploadProgress();

    void hidePhotoUploadProgress();
}
