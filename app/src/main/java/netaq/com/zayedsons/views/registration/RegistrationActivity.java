package netaq.com.zayedsons.views.registration;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.adapters.FragmentAdapter;
import netaq.com.zayedsons.adapters.FragmentContainer;
import netaq.com.zayedsons.eventbus.OnBackFromEducationInfo;
import netaq.com.zayedsons.eventbus.OnNextFromBioScreen;
import netaq.com.zayedsons.eventbus.RegisterButtonEvent;
import netaq.com.zayedsons.utils.CustomPager;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView{

    private FragmentAdapter adapter;
    private ScreenBioInfo bioInfoFragment;
    private ScreenEducationalInfo educationalInfoFragment;
    private RegistrationPresenter registerPresenter;

    @BindView(R.id.pager)CustomPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EventBus.getDefault().register(this);

        registerPresenter = new RegistrationPresenter(this,this);

        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        setUpPager();
        //nextBtn.setOnClickListener(new NextButtonListener());
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

        bioInfoFragment = (ScreenBioInfo)adapter.getFragmentAt(0);


        educationalInfoFragment = (ScreenEducationalInfo)adapter.getFragmentAt(1);

    }

    //Fired from ScreenBioInfo.NextButtonListener
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNextClickFromBioScreen(OnNextFromBioScreen nextEvent){
            viewPager.setCurrentItem(1,true);
    }

    //Fired from EducationInfo
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackFromEducationalScreen(OnBackFromEducationInfo backEvent){
        viewPager.setCurrentItem(0,true);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterClick(RegisterButtonEvent registerEvent){

        bioInfoFragment.getBioInfoData();
        educationalInfoFragment.getEducationalData();

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void OnRegistrationSuccess() {

    }

    @Override
    public void onError() {

    }

//    private class NextButtonListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            viewPager.setCurrentItem(1,true);
//
//        }
//    }
}
