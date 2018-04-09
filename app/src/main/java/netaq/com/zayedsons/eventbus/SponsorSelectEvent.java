package netaq.com.zayedsons.eventbus;

import netaq.com.zayedsons.model.Lookups;

/**
 * Created by sabih on 20-Mar-18.
 */

public class SponsorSelectEvent {
    private final Lookups item;

    public SponsorSelectEvent(Lookups item) {
        this.item = item;
    }

    public Lookups getItem() {
        return item;
    }
}
