package netaq.com.zayedsons.views.events.my_events;


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
import netaq.com.zayedsons.eventbus.MyEventsData;
import netaq.com.zayedsons.model.Event;

/**
 * Created by Sabih Ahmed on 11th Feb 2018
 *
 */
public class MyEvents extends Fragment implements UpComingEventsAdapter.EventClickListener {


    private Unbinder unbinder;
    private View view;

    @BindView(R.id.listing_my_events)RecyclerView myEventsListView;
    @BindView(R.id.empty_view) LinearLayout emptyView;
    public MyEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_events, container, false);

        unbinder = ButterKnife.bind(this,view);

        EventBus.getDefault().register(this);

        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyEventsFetched(MyEventsData myEventsData){

        List<Event> myEventsList = myEventsData.getMyEventsList();

        if(myEventsList.isEmpty()){
            //show Empty Events View
            showEmptyView();
        }else{
            //show list
            setEventList(myEventsList);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void showEmptyView() {
        myEventsListView.setVisibility(View.GONE);


    }

    private void setEventList(List<Event> eventList){
        emptyView.setVisibility(View.GONE);
        myEventsListView.setVisibility(View.VISIBLE);
        UpComingEventsAdapter upComingAdapter = new UpComingEventsAdapter(eventList,getContext());
        upComingAdapter.setEventClickListener(this);
        myEventsListView.setLayoutManager(new LinearLayoutManager(getContext()));
        myEventsListView.setAdapter(upComingAdapter);
    }

    @Override
    public void onEventClick(Event event) {
        NavigationController.showEventDetailScreen(getContext(), event);
    }
}
