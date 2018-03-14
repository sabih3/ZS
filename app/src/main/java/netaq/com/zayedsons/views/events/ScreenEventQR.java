package netaq.com.zayedsons.views.events;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.model.Event;

public class ScreenEventQR extends AppCompatActivity {

    @BindView(R.id.toolbar)@Nullable Toolbar toolbar;

    @BindView(R.id.field_event_title)TextView tvEventTitle;
    @BindView(R.id.field_start_date)TextView tvStartDate;
    @BindView(R.id.field_end_date)TextView tvEndDate;

    @BindString(R.string.screen_label_event_QR)String screenTitle;
    @BindDrawable(R.drawable.ic_arrow_back_white)Drawable backButton;

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_qr);

        event = (Event) getIntent().getSerializableExtra(NavigationController.KEY_EVENT_OBJ);
        initViews();

    }

    private void initViews() {
        ButterKnife.bind(this);
        setToolbar();
        setEventData();
    }

    private void setToolbar() {
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        toolbar.setTitle(screenTitle);
        toolbar.setNavigationIcon(backButton);
        setSupportActionBar(toolbar);
    }

    private void setEventData() {
        tvEventTitle.setText(event.getTitle());
    }
}
