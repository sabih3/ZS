package netaq.com.zayedsons.views.events.event_qr;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.model.Event;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.utils.Utils;

public class ScreenEventQR extends AppCompatActivity implements EventQRView{

    @BindView(R.id.coordinator)CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)@Nullable Toolbar toolbar;
    @BindView(R.id.field_event_title)TextView tvEventTitle;
    @BindView(R.id.field_start_date)TextView tvStartDate;
    @BindView(R.id.field_end_date)TextView tvEndDate;

    @BindString(R.string.screen_label_event_QR)String screenTitle;
    @BindDrawable(R.drawable.ic_arrow_back_white)Drawable backButton;
    @BindView(R.id.qr_swipe_refresh)SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress)ProgressBar progress;
    @BindView(R.id.iv_qr)ImageView qrImageView;

    @BindString(R.string.snackbar_no_network) String noNetworkMessage;
    @BindString(R.string.action_label_retry)String labelRetry;

    private Event event;
    private EventQRPresenter qrPresenter = new EventQRPresenter(this,this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_qr);

        event = (Event) getIntent().getSerializableExtra(NavigationController.KEY_EVENT_OBJ);
        initViews();

    }

    private void initViews() {
        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshListener());
        setToolbar();
        setEventData();
        getEventQR();
    }

    private void getEventQR() {
        qrPresenter.requestQR(event.getRegID(),event.getEventDays().get(0).getDay());
    }

    private void setToolbar() {
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        toolbar.setTitle(screenTitle);
        toolbar.setNavigationIcon(backButton);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenEventQR.this.finish();
            }
        });
    }

    private void setEventData() {
        tvEventTitle.setText(event.getTitle());
        tvStartDate.setText(Utils.getDate(event.getStartDate()
                +" " +Utils.getTime(event.getStartDate())));

        tvEndDate.setText(Utils.getDate(event.getEndDate()
                +" " +Utils.getTime(event.getEndDate())));
    }

    @Override
    public void onNetworkUnAvailable() {
        swipeRefreshLayout.setRefreshing(false);
        progress.setVisibility(View.GONE);
        UIUtils.showSnackBar(coordinatorLayout, noNetworkMessage, labelRetry, new UIUtils.SnackBarActionListener() {
            @Override
            public void onSnackBarAction() {
                getEventQR();
            }
        });
    }

    @Override
    public void onError(String resolvedError) {
        swipeRefreshLayout.setRefreshing(false);
        progress.setVisibility(View.GONE);
        UIUtils.showSnackBar(coordinatorLayout, resolvedError);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onQRReceived(String returnedURL) {

        try {
            Picasso.with(this).load(returnedURL).networkPolicy(NetworkPolicy.NO_CACHE).into(qrImageView, new Callback() {
                @Override
                public void onSuccess() {
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    progress.setVisibility(View.GONE);
                }
            });
        }catch (Exception exc){
            progress.setVisibility(View.GONE);
        }

    }

    private class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            getEventQR();
        }
    }
}
