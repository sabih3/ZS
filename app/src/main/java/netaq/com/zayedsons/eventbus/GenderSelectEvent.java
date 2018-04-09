package netaq.com.zayedsons.eventbus;

import netaq.com.zayedsons.model.Lookups;

/**
 * Created by sabih on 09-Apr-18.
 */

public class GenderSelectEvent {
    private final Lookups item;

    public GenderSelectEvent(Lookups item) {
        this.item = item;
    }

    public Lookups getItem() {
        return item;
    }
}
