package netaq.com.zayedsons.views.events;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;

public class ScreenEventQR extends AppCompatActivity {

    @BindView(R.id.toolbar)@Nullable Toolbar toolbar;
    @BindString(R.string.screen_label_event_QR)String screenTitle;
    @BindDrawable(R.drawable.ic_arrow_back_white)Drawable backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_qr);



        initViews();

    }

    private void initViews() {
        ButterKnife.bind(this);
        //setToolbar();
    }

    private void setToolbar() {
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        toolbar.setTitle(screenTitle);
        toolbar.setNavigationIcon(backButton);
        setSupportActionBar(toolbar);
    }
}
