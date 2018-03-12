package netaq.com.zayedsons.views.events.my_events;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import netaq.com.zayedsons.R;

/**
 * Created by Sabih Ahmed on 11th Feb 2018
 *
 */
public class MyEvents extends Fragment {

    private Unbinder unbinder;
    private View view;

    public MyEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_events, container, false);

        unbinder = ButterKnife.bind(this,view);

        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyEventsFetched(MyEvents myEventsData){


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
