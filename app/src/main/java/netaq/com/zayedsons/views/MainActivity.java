package netaq.com.zayedsons.views;

import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.adapters.NavDrawerAdapter;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.utils.AppConfig;

public class MainActivity extends AppCompatActivity implements NavDrawerAdapter.onItemClickListener{

    @BindView(R.id.toolbar)@Nullable Toolbar mainToolbar;
    @BindView(R.id.main_content)@Nullable FrameLayout mainContent;
    @BindString(R.string.screen_label_home) @Nullable String screenLabel;
    //@BindDrawable(R.drawable.ic_arrow_back)@Nullable Drawable backArrow;
    @BindView(R.id.drawer_layout)DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)NavigationView navigationView;
    @BindView(R.id.nav_drawer_list)RecyclerView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        NavigationController.showMainEventsScreen(MainActivity.this,
                getSupportFragmentManager());

    }

    private void initViews() {
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

        setDrawerList();
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
        switch (position){

            case 0:
                drawerLayout.closeDrawers();
            break;

            case 1:
                drawerLayout.closeDrawers();
            break;

            case 2:
                drawerLayout.closeDrawers();
                NavigationController.showScannerScreen(MainActivity.this);
            break;
        }
    }
}
