package netaq.com.zayedsons.eventbus;

import netaq.com.zayedsons.model.Lookup;

/**
 * Created by sabih on 20-Mar-18.
 */

public class SponsorSelectEvent {
    private final Lookup.Lookups item;

    public SponsorSelectEvent(Lookup.Lookups item) {
        this.item = item;
    }

    public Lookup.Lookups getItem() {
        return item;
    }
}
