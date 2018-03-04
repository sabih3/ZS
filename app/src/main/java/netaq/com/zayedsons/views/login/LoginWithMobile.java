package netaq.com.zayedsons.views.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.network.LoginWithMobilePresenter;
import netaq.com.zayedsons.utils.OTPGenerator;

public class LoginWithMobile extends AppCompatActivity implements
             CountryCodePicker.OnCountryChangeListener,LoginWithMobilePresenter.LoginMobileView{

    @BindView(R.id.field_phone)EditText fieldPhone;
    @BindView(R.id.code_picker)CountryCodePicker codePicker;
    @BindView(R.id.btn_proceed)ImageView sendButton;
    @BindView(R.id.progress)ProgressBar progress;
    private LoginWithMobilePresenter loginPresenter;
    private String countryCode ="";

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
    public void onSmsSent() {

    }

    @Override
    public void onSmsSentFailure() {

    }

    @Override
    public void onError() {

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
            String phone = fieldPhone.getText().toString();
            countryCode = codePicker.getSelectedCountryCodeWithPlus();
            String phoneWithCode = countryCode + phone;

            int OTP = OTPGenerator.generateRandomNumber();

            loginPresenter.sendOTP(phoneWithCode,OTP);

        }
    }
}
