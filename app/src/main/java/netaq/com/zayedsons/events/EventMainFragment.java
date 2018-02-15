package netaq.com.zayedsons.events;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.adapters.FragmentAdapter;
import netaq.com.zayedsons.adapters.FragmentContainer;
import netaq.com.zayedsons.events.finished.FinishedEvents;
import netaq.com.zayedsons.events.my_events.MyEvents;
import netaq.com.zayedsons.events.upcoming.UpComingEvents;

/** EventList tabs controller
 *
 */
public class EventMainFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.events_tabs) TabLayout tabLayout;
    @BindView(R.id.events_pager)ViewPager viewPager;

    public EventMainFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_main, container, false);

        unbinder = ButterKnife.bind(this, view);

        setPager();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        fragmentList.add(finishedEvents);

        FragmentAdapter adapter = new FragmentAdapter(getContext(),getActivity().getSupportFragmentManager(),
                                  fragmentList);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        for(int i=0;i<adapter.getCount();i++){
           tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));
           //tabLayout.getTabAt(i).setIcon(R.drawable.ic_tab_upcoming);
       }

    }
}
