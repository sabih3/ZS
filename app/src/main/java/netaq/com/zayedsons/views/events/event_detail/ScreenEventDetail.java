package netaq.com.zayedsons.views.events.event_detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.entities.ZColor;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.adapters.StripeGalleryAdapter;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.eventbus.ReloadAllEvents;
import netaq.com.zayedsons.model.Event;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.network.model.responses.ResponseEventGallery;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.utils.Utils;

public class ScreenEventDetail extends AppCompatActivity implements
        OnMapReadyCallback, EventDetailView, StripeGalleryAdapter.OnGalleryItemClickListener {

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
    @BindView(R.id.layout_gallery)LinearLayout galleryLayout;
    @BindView(R.id.stripe_gallery_list)RecyclerView stripeGalleryView;

    @BindView(R.id.label_description)TextView labelDescription;
    @BindView(R.id.expandable_layout)ExpandableLayout expandableLayout;
    @BindView(R.id.expandable_layout_gallery)ExpandableLayout expandableGalleryLayout;
    @BindView(R.id.label_gallery)TextView labelGallery;

    private Event event;
    private EventDetailPresenter presenter;
    private StripeGalleryAdapter galleryAdapter;
    private ArrayList<String> stringURLsList;


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

        labelDescription.setOnClickListener(new DiscriptionClickListener());

    }


    private void initViews() {
        ButterKnife.bind(this);
        setToolbar();

        if(!event.isArchived()){
            handleEventButtons();
        }

        setEventDescription();
        btnShowQR.setOnClickListener(new showQRListener());

        tvEventTitle.setText(event.getTitle());
        tvStartDate.setText(Utils.getDate(event.getStartDate(), Constants.DATE_FORMAT_UI)+ " "+ Utils.getTime(event.getStartDate()));
        tvEndDate.setText(Utils.getDate(event.getEndDate(),Constants.DATE_FORMAT_UI)+ " "+ Utils.getTime(event.getEndDate()));

        btnJoinEvent.setOnClickListener(new JoinEventListener());
        pullToRefresh.setOnRefreshListener(new SwipeRefreshListener());

        labelGallery.setOnClickListener(new GalleryLayoutClickListener());
        if(event.isArchived()){
            fetchEventGallery();
        }


    }

    private void fetchEventGallery() {
        galleryLayout.setVisibility(View.VISIBLE);
        presenter.getEventGallery(event.getID());
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

                if(event.getEventDays().get(0).isAttended()){
                    btnJoinEvent.setVisibility(View.VISIBLE);
                    btnJoinEvent.setText("You have already attended this event");
                    btnJoinEvent.setEnabled(false);
                }else{
                    btnJoinEvent.setVisibility(View.GONE);
                    btnShowQR.setVisibility(View.VISIBLE);
                }

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

    @Override
    public void onEmptyGallery() {
        if(event.isArchived()){
            //Handle empty Gallery if event is Archived
            galleryLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onGalleryFetched(List<ResponseEventGallery.Albums.Gallery> galleryList) {
        if(event.isArchived()){
            //Handle Gallery If event is Archived
            stringURLsList = new ArrayList<>();

            for(ResponseEventGallery.Albums.Gallery eachGalleryItem: galleryList){

                String resolvedURL = Utils.getResolvedURL(eachGalleryItem.getFileURL());
                stringURLsList.add(resolvedURL);

            }
            galleryAdapter = new StripeGalleryAdapter(this,galleryList);
            stripeGalleryView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,
                                              false));
            stripeGalleryView.setAdapter(galleryAdapter);

            galleryAdapter.setGalleryClickListener(this);
        }

    }

    private void submitJoinRequest(){
        presenter.sendEventJoinRequest(event.getID());
    }

    @Override
    public void onGalleryItemClicked(int position) {
        ZGallery.with(this, stringURLsList/*your string arraylist of image urls*/)
                .setToolbarTitleColor(ZColor.BLACK) // toolbar title color
                .setGalleryBackgroundColor(ZColor.BLACK) // activity background color
                .setToolbarColorResId(R.color.colorPrimary) // toolbar color
                .setTitle("Event Gallery")// toolbar title
                .setSelectedImgPosition(position)
                .show();
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

    private class DiscriptionClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            expandableLayout.toggle();
        }
    }

    private class GalleryLayoutClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            expandableGalleryLayout.toggle();
        }
    }
}
