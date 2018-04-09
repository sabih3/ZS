package netaq.com.zayedsons.views;

import java.util.List;

import netaq.com.zayedsons.model.Lookups;

/**
 * Created by sabih on 20-Mar-18.
 */

public interface LookupListView extends BaseView {
    void onLookupDataFetched(List<Lookups> lookups);
}
