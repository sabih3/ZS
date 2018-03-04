package netaq.com.zayedsons.views.registration;

import android.content.Context;

/**
 * Created by sabih on 18-Feb-18.
 */

public class RegistrationPresenter {

    private Context mContext;
    private RegistrationView viewListener;


    public RegistrationPresenter(Context context, RegistrationView registrationView) {
        this.mContext = context;
        this.viewListener = registrationView;
    }
}
