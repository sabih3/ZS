package netaq.com.zayedsons.registration;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import netaq.com.zayedsons.events.finished.FinishedEvents;
import netaq.com.zayedsons.utils.CustomPager;

public class RegistrationActivity extends AppCompatActivity {

    private FragmentAdapter adapter;

    @BindView(R.id.pager)CustomPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EventBus.getDefault().register(this);
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

    }

    //Fired from ScreenBioInfo.NextButtonListener
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNextClickFromBioScreen(OnNextFromBioScreen nextEvent){
            viewPager.setCurrentItem(1,true);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackFromEducationalScreen(OnBackFromEducationInfo backEvent){
        viewPager.setCurrentItem(0,true);
    }

    private class NextButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            viewPager.setCurrentItem(1,true);
            Fragment bioInfoFragment = (ScreenBioInfo)adapter.getFragmentAt(0);
            Fragment educationalInfoFragment = (ScreenEducationalInfo)adapter.getFragmentAt(1);
        }
    }
}
