package netaq.com.zayedsons.views;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.network.LoginWithMobilePresenter;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;
import netaq.com.zayedsons.utils.OTPHelper;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.utils.UserManager;

public class ScreenOTP extends AppCompatActivity{

    @BindView(R.id.tv_resend)TextView labelResend;
    @BindView(R.id.btn_confirm_otp)Button btnConfirm;
    @BindView(R.id.field_otp)EditText fieldOTP;
    @BindView(R.id.label_otp_message) TextView labelOTPMessage;
    @BindString(R.string.label_otp_msg) String messageOTP;

    private boolean userExistence;
    private String recipientNumber;
    private ResponseRegister userInfo;
    private CountDownTimer countToResend;

    private static final int timeToResend = 30000;
    private static final int timeToResendInterval = 1000;

    private LoginWithMobilePresenter presenter;
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

        // to disable sending the request for 30 secs in order to give the server enough time to handle it
        applyCounter();
    }

    private void initViews() {
        labelOTPMessage.setText(messageOTP +" "+ recipientNumber);
        btnConfirm.setOnClickListener(new ConfirmBtnListener());
        addResendListener();
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

                    ScreenOTP.this.finish();

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

    /**
     * to make sure the resend button will send the pin request and in no less that 30 sec as a time-interval between each request
     */
    private void addResendListener() {
        labelResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to disable sending the request for 30 secs
                applyCounter();
                btnConfirm.setEnabled(true);
                // sending the OTP Again to the device

                Toast.makeText(ScreenOTP.this, getResources().getString(R.string.resend_pin), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countToResend.cancel();
    }

    /**
     * To disable sending the request for 30 secs in order to preventing server overload,
     * Plus, creating a visional counter to be displayed for the user.
     */
    private void applyCounter() {
        // Disabling the Resend Button for 30 secs
        labelResend.setClickable(false);
        // to display seconds in Bold
        labelResend.setText(toBoldText(Integer.toString(30) + " " + getResources().getString(R.string.sec), " " + getResources().getString(R.string.counter)));
        // the counter that handles the 30 secs delaying
        countToResend = new CountDownTimer(timeToResend, timeToResendInterval) {
            @Override
            public void onTick(long timeToFinish) {
                // displaying the text of the remaining time on the counter TextView
                labelResend.setText(toBoldText(Integer.toString((int) (timeToFinish / 1000)) + " " + getResources().getString(R.string.sec), " " + getResources().getString(R.string.counter)));
            }

            @Override
            public void onFinish() {
                // when timer finish
                reEnableSending();
            }
        }.start();
    }

    /**
     * The method that is used to re-enable sending the pin code after the 30 sec time-interval passes
     */
    private void reEnableSending() {
        // in case this method called from outside the counter
        try {
            countToResend.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // hide the counter timer
        labelResend.setText(getResources().getString(R.string.resend_pin));
        // re-enable the button
        labelResend.setEnabled(true);
        labelResend.setClickable(true);
        btnConfirm.setEnabled(true);
    }

    /**
     * To convert regular String to Bold String
     *
     * @param textToBeBold the text to be converted to Bold
     * @return the resulted Bold text
     */
    private Spanned toBoldText(String textToBeBold, String textToRemainRegular) {
        String sourceString = "<b>" + textToBeBold + "</b>" + textToRemainRegular;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(sourceString, 0);
        else
            return Html.fromHtml(sourceString);
    }

}
