package netaq.com.zayedsons.views.login;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hbb20.CountryCodePicker;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.network.LoginWithMobilePresenter;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;
import netaq.com.zayedsons.utils.OTPHelper;
import netaq.com.zayedsons.utils.UIUtils;

public class LoginWithMobile extends AppCompatActivity implements
             CountryCodePicker.OnCountryChangeListener,LoginWithMobilePresenter.LoginMobileView{

    @BindView(R.id.parent_coordinator) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.field_phone)EditText fieldPhone;
    @BindView(R.id.code_picker)CountryCodePicker codePicker;
    @BindView(R.id.btn_proceed)ImageView sendButton;
    @BindView(R.id.progress)ProgressBar progress;

    private LoginWithMobilePresenter loginPresenter;
    private String countryCode ="";
    @BindString(R.string.snackbar_no_network) String noNetworkMessage;
    @BindString(R.string.action_label_retry)String labelRetry;
    @BindString(R.string.label_something_went_wrong) String defaultErrorMessage;
    @BindString(R.string.label_SMS_couldnot_sent)String smsCouldnotSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mobile);
        ButterKnife.bind(this);


        codePicker.setOnCountryChangeListener(this);
        sendButton.setOnClickListener(new SendButtonListener());

        loginPresenter = new LoginWithMobilePresenter(this,
                                                                               this);
    }

    @Override
    public void onCountrySelected() {
        countryCode = codePicker.getSelectedCountryCodeWithPlus();
    }

    @Override
    public void onSmsSent(ResponseRegister userInfo, String recipient, boolean userExists) {
        NavigationController.showOTPConfirmScreen(this,userInfo,recipient,userExists);
        this.finish();
    }

    @Override
    public void onSmsSentFailure() {
        UIUtils.showSnackBar(coordinatorLayout,smsCouldnotSent);
    }

    @Override
    public void onNetworkUnAvailable() {
        UIUtils.showSnackBar(coordinatorLayout, noNetworkMessage, labelRetry, new UIUtils.SnackBarActionListener() {
            @Override
            public void onSnackBarAction() {
                getValuesAndSendOTP();
            }
        });
    }

    @Override
    public void onError() {
        UIUtils.showSnackBar(coordinatorLayout,defaultErrorMessage);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }


    private class SendButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            String phone = fieldPhone.getText().toString();
//            countryCode = codePicker.getSelectedCountryCodeWithPlus();
//            String phoneWithCode = countryCode + phone;
//
//            int OTP = OTPHelper.getNewOTP();
//
//            loginPresenter.sendOTP(phoneWithCode,OTP);

            getValuesAndSendOTP();

        }
    }

    private void getValuesAndSendOTP(){
        String phone = fieldPhone.getText().toString();

        if(!phone.isEmpty()){
            //If number entered by user is started with zero
            countryCode = codePicker.getSelectedCountryCodeWithPlus();

            if(phone.startsWith("0")) {
                phone = phone.substring(1);
            }

            String phoneWithCode = countryCode + phone;

            UIUtils.showMessageDialog(this,
                    "Is this Number " + phoneWithCode + " correct ? ",
                    "Confirm", "Edit",
                    new UIUtils.DialogButtonListener() {
                @Override
                public void onPositiveButtonClicked() {
                    int OTP = OTPHelper.getNewOTP();
                    loginPresenter.sendOTP(phoneWithCode,OTP);
                }

                @Override
                public void onNegativeButtonClicked() {

                }
            });


        }else{
            fieldPhone.setError("Please provide a valid mobile number");
        }

    }
}
