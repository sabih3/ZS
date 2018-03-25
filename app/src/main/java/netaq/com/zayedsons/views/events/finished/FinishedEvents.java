package netaq.com.zayedsons.views.events.finished;


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
import netaq.com.zayedsons.eventbus.FinishedEventData;
import netaq.com.zayedsons.model.Event;

public class FinishedEvents extends Fragment implements UpComingEventsAdapter.EventClickListener{


    @BindView(R.id.listing_finished_events)RecyclerView listViewFinished;
    @BindView(R.id.empty_view)LinearLayout emptyView;

    private View view;
    private Unbinder unbinder;

    public FinishedEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_finished_events, container, false);

        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishedEventsFetched(FinishedEventData finishedEventData){

        List<Event> finishedEventList = finishedEventData.getFinishedEventList();

        if(finishedEventList.isEmpty()){
            //show Empty Events View
            showEmptyView();
        }else{
            //show list
            setEventList(finishedEventList);
        }

    }

    private void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        listViewFinished.setVisibility(View.GONE);


    }

    private void setEventList(List<Event> eventList){
        emptyView.setVisibility(View.GONE);
        listViewFinished.setVisibility(View.VISIBLE);
        UpComingEventsAdapter upComingAdapter = new UpComingEventsAdapter(eventList,getContext());
        upComingAdapter.setEventClickListener(this);
        listViewFinished.setLayoutManager(new LinearLayoutManager(getContext()));
        listViewFinished.setAdapter(upComingAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onEventClick(Event event) {
        event.setArchived(true);
        event.setArchived(true);
        NavigationController.showEventDetailScreen(getContext(),event);
    }
}
