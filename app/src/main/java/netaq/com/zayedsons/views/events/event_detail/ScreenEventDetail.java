package netaq.com.zayedsons.views.events.event_detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;

public class ScreenEventDetail extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindString(R.string.screen_label_event_detail)String screenTitle;
    @BindDrawable(R.drawable.ic_arrow_back_black_24px)Drawable backArrow;

    @BindView(R.id.btn_join_event)Button btnJoinEvent;
    @BindView(R.id.btn_qr)Button btnShowQR;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_event_detail);

        initViews();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);

    }

    private void initViews() {
        ButterKnife.bind(this);
        setToolbar();
        btnShowQR.setOnClickListener(new showQRListener());
    }

    private void setToolbar() {
        toolbar.setTitle(screenTitle);
        toolbar.setNavigationIcon(backArrow);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(24.4982192 , 54.3706088))
//                .title("Netaq E Solutions"));



    }

    private class showQRListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            NavigationController.showQRScreen(ScreenEventDetail.this);
        }
    }
}
