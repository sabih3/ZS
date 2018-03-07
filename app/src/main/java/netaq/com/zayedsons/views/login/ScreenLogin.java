package netaq.com.zayedsons.views.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.utils.OTPHelper;

public class ScreenLogin extends AppCompatActivity {

    @BindView(R.id.label_register)TextView labelRegister;
    @BindView(R.id.sign_in_btn)TextView labelLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_sign_in);

        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        labelRegister.setOnClickListener(new RegisterLabelListener());
        labelLogin.setOnClickListener(new LoginLabelListener());
    }

    private class RegisterLabelListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class LoginLabelListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            NavigationController.showMainActivity(ScreenLogin.this);
        }
    }
}
