package netaq.com.zayedsons.views.events;

import netaq.com.zayedsons.network.model.responses.ResponseEventList;
import netaq.com.zayedsons.views.BaseView;

/**
 * Created by sabih on 12-Mar-18.
 */

public interface EventMainView extends BaseView{
    void onEventsFetched(ResponseEventList responseEventList);
}
