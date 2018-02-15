package netaq.com.zayedsons.adapters;

import android.support.v4.app.Fragment;

import netaq.com.zayedsons.R;

/**
 * Created by Sabih on 11-Feb-18.
 */

public class FragmentContainer {
    private Fragment mFragment;
    private String fragmentTitle;
    private int tabIconID;

    public FragmentContainer(Fragment mFragment, String fragmentTitle, int tabIconID) {
        this.mFragment = mFragment;
        this.fragmentTitle = fragmentTitle;
        this.tabIconID = tabIconID;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public String getFragmentTitle() {
        return fragmentTitle;
    }

    public int getFragmentIcon() {

        return this.tabIconID;
    }
}
