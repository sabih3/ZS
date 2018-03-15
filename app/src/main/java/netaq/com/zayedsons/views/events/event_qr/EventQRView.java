package netaq.com.zayedsons.views.events.event_qr;

import netaq.com.zayedsons.views.BaseView;

/**
 * Created by sabih on 14-Mar-18.
 */

public interface EventQRView extends BaseView{

    void onQRReceived(String returnedURL);

}
