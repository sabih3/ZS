package netaq.com.zayedsons.views.events.upcoming;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.adapters.UpComingEventsAdapter;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.eventbus.UpcomingEventsData;
import netaq.com.zayedsons.model.Event;

/**
 *  Created by Sabih Ahmed on 11th Feb 2018
 */
public class UpComingEvents extends Fragment implements UpComingView,
                                             UpComingEventsAdapter.EventClickListener{

    private Unbinder unbinder;
    private View view;
    private UpComingPresenter upComingPresenter;
    @BindView(R.id.listing_upcoming_events)RecyclerView upcomingEventList;
    @BindView(R.id.empty_view)LinearLayout emptyView;
    public UpComingEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_up_coming_events, container, false);

        unbinder = ButterKnife.bind(this,view);
        upComingPresenter = new UpComingPresenter(this);

        EventBus.getDefault().register(this);
        //upComingPresenter.fetchUpComingEventsList();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpComingDataFetched(UpcomingEventsData upComingEventsData){
        List<Event> upcomingEventList = upComingEventsData.getUpcomingEventList();

        if(upcomingEventList.isEmpty()){
            showEmptyView();
        }else{
            //show list view
            setEventList(upcomingEventList);
        }
    }

    @Override
    public void onUpComingEventsFetched() {
        //List<Event> events = new ArrayList<>();


    }

    //UpComingEventsAdapter.EventClickListener
    @Override
    public void onEventClick(Event event) {
        NavigationController.showEventDetailScreen(getContext(),event);
    }

    private void showEmptyView() {
        upcomingEventList.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);

    }
    private void setEventList(List<Event> eventList){
        emptyView.setVisibility(View.GONE);
        upcomingEventList.setVisibility(View.VISIBLE);
        UpComingEventsAdapter upComingAdapter = new UpComingEventsAdapter(eventList,getContext());
        upComingAdapter.setEventClickListener(this);
        upcomingEventList.setLayoutManager(new LinearLayoutManager(getContext()));
        upcomingEventList.setAdapter(upComingAdapter);
    }
}
