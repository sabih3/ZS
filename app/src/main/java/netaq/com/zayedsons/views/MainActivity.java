package netaq.com.zayedsons.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import netaq.com.zayedsons.BuildConfig;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.adapters.NavDrawerAdapter;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.utils.AppConfig;
import netaq.com.zayedsons.utils.DevicePreferences;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.utils.UserManager;

public class MainActivity extends AppCompatActivity implements NavDrawerAdapter.onItemClickListener,
             MainActivityView{

    private static final String TAG = "Token";
    @BindView(R.id.toolbar)@Nullable Toolbar mainToolbar;
    @BindView(R.id.main_content)@Nullable FrameLayout mainContent;
    @BindString(R.string.screen_label_home) @Nullable String screenLabel;
    //@BindDrawable(R.drawable.ic_arrow_back)@Nullable Drawable backArrow;
    @BindView(R.id.drawer_layout)DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)NavigationView navigationView;
    @BindView(R.id.nav_drawer_list)RecyclerView drawerList;
    private MainActivityPresenter mainActivityPresenter;
    private Handler handler;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        NavigationController.showMainEventsScreen(MainActivity.this,
                getSupportFragmentManager());

        if(BuildConfig.DEBUG){
            Log.d(TAG, "Refreshed token: " + UserManager.getPushToken());
        }

    }

    private void initViews() {
        mainActivityPresenter = new MainActivityPresenter(this,this);
        ButterKnife.bind(this);
        setToolbar();
        setDrawer();
    }

    private void setToolbar() {
        mainToolbar.setTitle(screenLabel);
        //mainToolbar.setNavigationIcon(backArrow);
        setSupportActionBar(mainToolbar);
    }
    private void setDrawer() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                mainToolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        setDrawerHeader();

        setDrawerList();


    }

    private void setDrawerHeader() {
        //View headerView = navigationView.getHeaderView(0);

        CircleImageView drawerProfilePhoto = findViewById(R.id.drawer_profile_image);
        TextView personName = findViewById(R.id.drawer_tv_person_name);

        try {
            Picasso.with(this).load(UserManager.getUser().getProfile().getProfilePic()).into(drawerProfilePhoto);
        }catch (Exception exc){

        }

        personName.setText(UserManager.getUser().getAccountInfo().getFullName());
    }

    private void setDrawerList() {
        drawerList.setLayoutManager(new LinearLayoutManager(this));
        List<String> drawerItems = AppConfig.getDrawerItems();
        NavDrawerAdapter navDrawerAdapter = new NavDrawerAdapter(this, drawerItems);
        drawerList.setAdapter(navDrawerAdapter);
        navDrawerAdapter.setItemClickListener(this);
    }

    //NavDrawerAdapter.OnItemClick
    @Override
    public void onListItemClick(int position) {
        handler = new Handler();
        switch (position){


            //Profile
            case 0:
                drawerLayout.closeDrawers();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showProfileActivity();
                    }
                },300);

            break;

            //Events
            case 1:
                drawerLayout.closeDrawers();
            break;

            //Conditional
            //in case of attendee, it show log out dialog
            //in case of organizer user i.e. user = power
            //it will open QR Scanner screen
            case 2:
                drawerLayout.closeDrawers();

                if(UserManager.getUser().getAccountInfo().getUserTypeID()
                                                         .equals(Constants.USER_TYPE_POWER)){

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NavigationController.showScannerScreen(MainActivity.this);
                        }
                    },300);

                }else{
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handleLogout();
                        }
                    },300);
                }



            break;

            case 3:
                drawerLayout.closeDrawers();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleLogout();
                    }
                },300);

            break;
        }
    }

    /**
     * To display the User Profile page
     */
    private void showProfileActivity() {
        Intent displayProfile = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(displayProfile);
    }

    private void handleLogout() {
        UIUtils.showMessageDialog(MainActivity.this,
                "Are you sure of logging out?", "Yes", "No",
                new UIUtils.DialogButtonListener() {
                    @Override
                    public void onPositiveButtonClicked() {
                        mainActivityPresenter.requestLogOut();
                    }

                    @Override
                    public void onNegativeButtonClicked() {

                    }
                });
    }

    @Override
    public void onNetworkUnAvailable() {
       UIUtils.showMessageDialog(this, getResources().getString(R.string.snackbar_no_network),
               getResources().getString(R.string.action_label_retry),
               getResources().getString(R.string.action_label_cancel), new UIUtils.DialogButtonListener() {
                   @Override
                   public void onPositiveButtonClicked() {
                       mainActivityPresenter.requestLogOut();
                   }

                   @Override
                   public void onNegativeButtonClicked() {

                   }
               });
    }

    @Override
    public void onError(String resolvedError) {
        UIUtils.showMessageDialog(this, resolvedError, "OK", "", new UIUtils.DialogButtonListener() {
            @Override
            public void onPositiveButtonClicked() {

            }

            @Override
            public void onNegativeButtonClicked() {

            }
        });
    }

    @Override
    public void showProgress() {

        progressDialog = new ProgressDialog(this);

        UIUtils.showProgressDialog(this,"Logging out",progressDialog);
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }


    @Override
    public void onLogOutSuccess() {
        UserManager.clearUserData();
        NavigationController.showLoginScreen(MainActivity.this);
        MainActivity.this.finish();
    }
}
