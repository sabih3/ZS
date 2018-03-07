package netaq.com.zayedsons.views.registration;

import netaq.com.zayedsons.views.BaseView;

/**
 * Created by sabih on 18-Feb-18.
 */

public interface RegistrationView extends BaseView{

    void OnRegistrationSuccess();
    void onRegistrationError();

    void onRecordExists();
}
