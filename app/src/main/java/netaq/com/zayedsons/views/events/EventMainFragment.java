package netaq.com.zayedsons.views.events;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.adapters.FragmentAdapter;
import netaq.com.zayedsons.adapters.FragmentContainer;
import netaq.com.zayedsons.eventbus.FinishedEventData;
import netaq.com.zayedsons.eventbus.MyEventsData;
import netaq.com.zayedsons.eventbus.UpcomingEventsData;
import netaq.com.zayedsons.model.Event;
import netaq.com.zayedsons.network.model.responses.ResponseEventList;
import netaq.com.zayedsons.utils.Utils;
import netaq.com.zayedsons.views.events.finished.FinishedEvents;
import netaq.com.zayedsons.views.events.my_events.MyEvents;
import netaq.com.zayedsons.views.events.upcoming.UpComingEvents;

/** EventList tabs controller
 *
 */
public class EventMainFragment extends Fragment implements EventMainView{

    private Unbinder unbinder;

    @BindView(R.id.events_tabs) TabLayout tabLayout;
    @BindView(R.id.events_pager)ViewPager viewPager;
    @BindView(R.id.events_swipe_refresh)SwipeRefreshLayout swipeRefreshLayout;

    private EventMainPresenter mainEventPresenter;

    public EventMainFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_main, container, false);

        unbinder = ButterKnife.bind(this, view);
        mainEventPresenter = new EventMainPresenter(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshListener());
        setPager();

        getAllEventsData();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onEventsFetched(ResponseEventList responseEventList) {

        List<Event> upcomingEventList = responseEventList.getUpcoming();
        List<Event> myEventsList = responseEventList.getMyEvents();
        List<Event> archiveEventList = responseEventList.getArchive();

        EventBus.getDefault().post(new UpcomingEventsData(upcomingEventList));
        EventBus.getDefault().post(new MyEventsData(myEventsList));
        EventBus.getDefault().post(new FinishedEventData(archiveEventList));


    }

    @Override
    public void onNetworkUnAvailable() {
        //TODO: Handle on NEtwork Failure
    }

    @Override
    public void onError() {
        //TODO: Handle Exceptions on Events Main Fragment
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void getAllEventsData() {
        mainEventPresenter.getEvents(getContext());


    }

    private void setPager() {
        ArrayList<FragmentContainer> fragmentList = new ArrayList<>();

        Fragment upComingFragment = new UpComingEvents();
        Fragment myEventFragment = new MyEvents();
        Fragment finishedFragment = new FinishedEvents();


        FragmentContainer upComing = new FragmentContainer(upComingFragment,
                                    getString(R.string.tab_title_event_upcoming),
                                    R.drawable.ic_tab_upcoming);

        FragmentContainer myEvents = new FragmentContainer(myEventFragment,
                                    getString(R.string.tab_title_event_my_events),
                                    R.drawable.ic_tab_my_events);

        FragmentContainer finishedEvents = new FragmentContainer(finishedFragment,
                                          getString(R.string.tab_title_event_finished),
                                          R.drawable.ic_tab_finished_events);

        fragmentList.add(upComing);
        fragmentList.add(myEvents);
        //fragmentList.add(finishedEvents);

        FragmentAdapter adapter = new FragmentAdapter(getContext(),getActivity().getSupportFragmentManager(),
                                                     fragmentList);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);
        for(int i=0;i<adapter.getCount();i++){
           tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));
           //tabLayout.getTabAt(i).setIcon(R.drawable.ic_tab_upcoming);
       }

    }


    private class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            getAllEventsData();
        }
    }
}
