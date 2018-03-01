package netaq.com.zayedsons.views.events.upcoming;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.adapters.UpComingEventsAdapter;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.model.EventList;

/**
 *  Created by Sabih Ahmed on 11th Feb 2018
 */
public class UpComingEvents extends Fragment implements UpComingView,
                                             UpComingEventsAdapter.EventClickListener{

    private Unbinder unbinder;
    private View view;
    private UpComingPresenter upComingPresenter;
    @BindView(R.id.listing_upcoming_events)RecyclerView upcomingEventList;
    public UpComingEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_up_coming_events, container, false);

        unbinder = ButterKnife.bind(this,view);
        upComingPresenter = new UpComingPresenter(this);
        upComingPresenter.fetchUpComingEventsList();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onUpComingEventsFetched() {
        List<EventList> events = new ArrayList<>();
        UpComingEventsAdapter upComingAdapter = new UpComingEventsAdapter(null,getContext());
        upComingAdapter.setEventClickListener(this);
        upcomingEventList.setLayoutManager(new LinearLayoutManager(getContext()));
        upcomingEventList.setAdapter(upComingAdapter);

    }

    //UpComingEventsAdapter.EventClickListener
    @Override
    public void onEventClick() {
        NavigationController.showEventDetailScreen(getContext());
    }
}
