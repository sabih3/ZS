package netaq.com.zayedsons.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import netaq.com.zayedsons.R;

/**
 * Created by sabih on 11-Feb-18.
 */

public class FragmentAdapter extends FragmentPagerAdapter {


    private final ArrayList<FragmentContainer> mDataset;
    private Context context;
    public FragmentAdapter(Context context,FragmentManager fragmentManager, ArrayList<FragmentContainer>
                                                            fragmentContainerList) {
        super(fragmentManager);
        this.context = context;
        mDataset = fragmentContainerList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataset.get(position).getFragmentTitle();
    }


    @Override
    public Fragment getItem(int position) {
        return mDataset.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab_view_events,null);
        ImageView tabImage = view.findViewById(R.id.tab_icon);
        TextView tabTitle = view.findViewById(R.id.tab_title);

        tabTitle.setText(mDataset.get(position).getFragmentTitle());
        tabImage.setImageResource(mDataset.get(position).getFragmentIcon());
        return view;
    }
}
