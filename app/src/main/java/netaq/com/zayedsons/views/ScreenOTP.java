package netaq.com.zayedsons.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;
import netaq.com.zayedsons.utils.OTPHelper;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.utils.UserManager;

public class ScreenOTP extends AppCompatActivity {

    @BindView(R.id.tv_resend)TextView labelResend;
    @BindView(R.id.btn_confirm_otp)Button btnConfirm;
    @BindView(R.id.field_otp)EditText fieldOTP;
    @BindView(R.id.label_otp_message) TextView labelOTPMessage;
    @BindString(R.string.label_otp_msg) String messageOTP;

    private boolean userExistence;
    private String recipientNumber;
    private ResponseRegister userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_otp_confirm);

        ButterKnife.bind(this);

        userExistence = getIntent().
                        getBooleanExtra(NavigationController.KEY_USER_EXISTENCE,false);
        recipientNumber = getIntent().getStringExtra(NavigationController.KEY_RECIPIENT);

        userInfo = (ResponseRegister) getIntent().getSerializableExtra(NavigationController.KEY_USER_INFO);

        initViews();
    }

    private void initViews() {
        labelOTPMessage.setText(messageOTP +" "+ recipientNumber);
        btnConfirm.setOnClickListener(new ConfirmBtnListener());
    }

    private class ConfirmBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String otpString = fieldOTP.getText().toString().trim();

            if(!otpString.isEmpty()){
                Integer otpInteger = Integer.valueOf(otpString);

                boolean isOTPValid = OTPHelper.isOTPValid(otpInteger);

                if(isOTPValid){

                    if(userExistence){
                        UserManager.setUser(userInfo);
                        NavigationController.showMainActivity(ScreenOTP.this);
                    }else{
                        NavigationController.showRegistrationScreen(ScreenOTP.this,recipientNumber,
                                OTPHelper.getCachedOTP());
                    }
                }else{

                    UIUtils.showMessageDialog(ScreenOTP.this, "The Code you entered is not valid",
                            "Enter Again", "Dismiss", new UIUtils.DialogButtonListener() {
                                @Override
                                public void onPositiveButtonClicked() {

                                }

                                @Override
                                public void onNegativeButtonClicked() {

                                }
                            });
                }
            }else{
                fieldOTP.setError("Must not be empty");
            }

        }
    }
}
