package netaq.com.zayedsons.eventbus;

import netaq.com.zayedsons.model.Lookup;

/**
 * Created by sabih on 20-Mar-18.
 */

public class CitySelectEvent {

    private Lookup.Lookups item;

    public CitySelectEvent(Lookup.Lookups item) {

        this.item = item;
    }

    public Lookup.Lookups getItem() {
        return item;
    }
}


