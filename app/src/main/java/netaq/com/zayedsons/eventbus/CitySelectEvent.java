package netaq.com.zayedsons.eventbus;

import netaq.com.zayedsons.model.Lookups;

/**
 * Created by sabih on 20-Mar-18.
 */

public class CitySelectEvent {

    private Lookups item;

    public CitySelectEvent(Lookups item) {

        this.item = item;
    }

    public Lookups getItem() {
        return item;
    }
}


