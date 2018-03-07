package netaq.com.zayedsons.views.registration;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.adapters.FragmentAdapter;
import netaq.com.zayedsons.adapters.FragmentContainer;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.eventbus.OnBackFromEducationInfo;
import netaq.com.zayedsons.eventbus.OnNextFromBioScreen;
import netaq.com.zayedsons.eventbus.RegisterButtonEvent;
import netaq.com.zayedsons.utils.CustomPager;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.utils.UserManager;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView{

    private FragmentAdapter adapter;
    private ScreenBioInfo bioInfoFragment;
    private ScreenEducationalInfo educationalInfoFragment;
    private RegistrationPresenter registerPresenter;

    @BindView(R.id.registration_parent_coordinator)CoordinatorLayout coordinatorLayout;
    @BindView(R.id.pager)CustomPager viewPager;

    @BindString(R.string.snackbar_no_network) String noNetworkMessage;
    @BindString(R.string.action_label_retry)String labelRetry;
    @BindString(R.string.label_something_went_wrong) String defaultErrorMessage;

    @BindView(R.id.progress) ProgressBar progress;

    private int OTP;
    private String recipientNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EventBus.getDefault().register(this);

        registerPresenter = new RegistrationPresenter(this,this);

        OTP = getIntent().getIntExtra(NavigationController.KEY_OTP,-1);
        recipientNumber = getIntent().getStringExtra(NavigationController.KEY_RECIPIENT);
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        setUpPager();
    }

    private void setUpPager() {

        Fragment screenBio = new ScreenBioInfo();
        Fragment screenEducationalInfo = new ScreenEducationalInfo();

        ArrayList<FragmentContainer> fragmentList = new ArrayList<>();

        FragmentContainer forBioScreen = new FragmentContainer(screenBio, "", -1);
        FragmentContainer forEducationalScreen = new FragmentContainer(screenEducationalInfo, "", -1);

        fragmentList.add(forBioScreen);
        fragmentList.add(forEducationalScreen);

        adapter = new FragmentAdapter(this,getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.disableScroll(true);

        bioInfoFragment = (ScreenBioInfo)adapter.getItem(0);


        educationalInfoFragment = (ScreenEducationalInfo)adapter.getItem(1);

    }

    //Fired from ScreenBioInfo.NextButtonListener
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNextClickFromBioScreen(OnNextFromBioScreen nextEvent){
            viewPager.setCurrentItem(1,true);
    }

    //Fired from Screen EducationInfo
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackFromEducationalScreen(OnBackFromEducationInfo backEvent){
        viewPager.setCurrentItem(0,true);
    }

    //Fired from Screen EducationInfo
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterClick(RegisterButtonEvent registerEvent){
        getValuesAndRegister();



    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void OnRegistrationSuccess() {

        NavigationController.showMainActivity(this);
    }

    @Override
    public void onRegistrationError() {
        //TODO: Handle Registration Error On UI
    }

    @Override
    public void onRecordExists(){
        //TODO: Handle Record Exists on UI
    }

    @Override
    public void onNetworkUnAvailable() {
        UIUtils.showSnackBar(coordinatorLayout, noNetworkMessage, labelRetry, new UIUtils.SnackBarActionListener() {
            @Override
            public void onSnackBarAction() {
                getValuesAndRegister();
            }
        });
    }



    @Override
    public void onError() {
        UIUtils.showSnackBar(coordinatorLayout,defaultErrorMessage);
    }

    private void getValuesAndRegister() {
        HashMap<String, Object> bioInfoData = bioInfoFragment.getBioInfoData();
        HashMap<String, String> educationalData = educationalInfoFragment.getEducationalData();

        bioInfoData.put("number",recipientNumber);
        bioInfoData.put("otp",OTP);

        registerPresenter.requestRegisterProfile(bioInfoData,educationalData);
    }
}
