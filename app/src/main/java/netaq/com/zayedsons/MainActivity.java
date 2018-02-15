package netaq.com.zayedsons;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.core.NavigationController;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)Toolbar mainToolbar;
    @BindView(R.id.main_content)FrameLayout mainContent;
    @BindString(R.string.screen_label_home) String screenLabel;
    @BindDrawable(R.drawable.ic_arrow_back_black_24px) Drawable backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        NavigationController.showMainEventsScreen(MainActivity.this,
                                                  getSupportFragmentManager());


//        ActionBar supportActionBar = getSupportActionBar();
//        supportActionBar.setTitle("EventList");
//        supportActionBar.setDisplayHomeAsUpEnabled(true);
        mainToolbar.setTitle(screenLabel);
        mainToolbar.setNavigationIcon(backArrow);
        setSupportActionBar(mainToolbar);



    }

}
