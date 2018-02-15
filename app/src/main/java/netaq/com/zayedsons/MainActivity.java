package netaq.com.zayedsons;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.adapters.NavDrawerAdapter;
import netaq.com.zayedsons.core.NavigationController;

public class MainActivity extends AppCompatActivity {

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
        drawerList.setAdapter(new NavDrawerAdapter(this,null));
    }
}
