package netaq.com.zayedsons.views.events.event_detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.eventbus.ReloadAllEvents;
import netaq.com.zayedsons.model.Event;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.utils.Utils;

public class ScreenEventDetail extends AppCompatActivity implements OnMapReadyCallback, EventDetailView {

    private final int DEFAULT_MAP_ZOOM = 15;
    @BindView(R.id.event_detail_coordinator)CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindString(R.string.screen_label_event_detail)String screenTitle;
    @BindDrawable(R.drawable.ic_arrow_back_black_24px)Drawable backArrow;

    @BindView(R.id.btn_join_event)Button btnJoinEvent;
    @BindView(R.id.btn_qr)Button btnShowQR;

    @BindView(R.id.field_event_title)TextView tvEventTitle;
    @BindView(R.id.field_start_date)TextView tvStartDate;
    @BindView(R.id.field_end_date)TextView tvEndDate;
    @BindView(R.id.field_description)TextView tvEventDesc;
    @BindView(R.id.event_detail_swipe) SwipeRefreshLayout pullToRefresh;

    @BindString(R.string.snackbar_no_network) String noNetworkMessage;
    @BindString(R.string.action_label_retry)String labelRetry;
    @BindString(R.string.msg_join_requested)String msgJoinRequested;
    @BindString(R.string.action_label_stay_here)String actionStayHere;
    @BindString(R.string.action_go_back)String actionGoBack;
    private Event event;
    private EventDetailPresenter presenter ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_screen_event_detail);
        presenter = new EventDetailPresenter(this,this);

        event = (Event)getIntent().getSerializableExtra(NavigationController.KEY_EVENT_OBJ);

        initViews();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);

    }


    private void initViews() {
        ButterKnife.bind(this);
        setToolbar();
        handleEventButtons();
        setEventDescription();
        btnShowQR.setOnClickListener(new showQRListener());

        tvEventTitle.setText(event.getTitle());
        tvStartDate.setText(Utils.getDate(event.getStartDate())+ " "+ Utils.getTime(event.getStartDate()));
        tvEndDate.setText(Utils.getDate(event.getEndDate())+ " "+ Utils.getTime(event.getEndDate()));



        btnJoinEvent.setOnClickListener(new JoinEventListener());
        pullToRefresh.setOnRefreshListener(new SwipeRefreshListener());
    }

    private void setEventDescription() {
        Spanned htmlSpanned = Html.fromHtml(event.getDetails());

        tvEventDesc.setText(htmlSpanned);
    }

    private void handleEventButtons() {
        boolean registered = event.isRegistered();
        boolean registrationApproved = event.isRegApproved();

        if(!registered){
            btnJoinEvent.setVisibility(View.VISIBLE);
        }else{
            if(!registrationApproved){
                btnJoinEvent.setVisibility(View.VISIBLE);
                btnJoinEvent.setEnabled(false);
                btnJoinEvent.setText("Waiting for approval");
            }else{
                btnJoinEvent.setVisibility(View.GONE);
                btnShowQR.setVisibility(View.VISIBLE);
            }
        }

    }

    private void setToolbar() {
        toolbar.setTitle(screenTitle);
        toolbar.setNavigationIcon(backArrow);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenEventDetail.this.finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        int zoom = DEFAULT_MAP_ZOOM;

        if(event.getMap_Zoom() >0){
            zoom = event.getMap_Zoom();
        }

        LatLng coordinate = new LatLng(event.getMap_Lat(),event.getMap_Lng());

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate,zoom);

        googleMap.animateCamera(location);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(event.getMap_Lat() , event.getMap_Lng()))
                .title(event.getVenue()));

    }

    @Override
    public void onNetworkUnAvailable() {
        UIUtils.showSnackBar(coordinatorLayout, noNetworkMessage, labelRetry, new UIUtils.SnackBarActionListener() {
            @Override
            public void onSnackBarAction() {
                submitJoinRequest();
            }
        });
    }

    @Override
    public void onError(String resolvedError) {
        UIUtils.showSnackBar(coordinatorLayout,resolvedError);
    }

    @Override
    public void showProgress() {
        pullToRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        pullToRefresh.setRefreshing(false);
    }

    @Override
    public void onJoinRequestSubmitted() {
        btnJoinEvent.setEnabled(false);
        btnJoinEvent.setText("Waiting for approval");

        UIUtils.showMessageDialog(this, msgJoinRequested, actionStayHere,
                actionGoBack, new UIUtils.DialogButtonListener() {
            @Override
            public void onPositiveButtonClicked() {
                //Stay here
            }

            @Override
            public void onNegativeButtonClicked() {
                //Fire an event to EventMainFragment to reload
                ScreenEventDetail.this.finish();
                EventBus.getDefault().post(new ReloadAllEvents());
            }
        });
    }

    private void submitJoinRequest(){
        presenter.sendEventJoinRequest(event.getID());
    }
    private class showQRListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            NavigationController.showQRScreen(ScreenEventDetail.this,event);
        }
    }

    private class JoinEventListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            submitJoinRequest();
        }
    }

    private class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            pullToRefresh.setRefreshing(false);
        }
    }
}
