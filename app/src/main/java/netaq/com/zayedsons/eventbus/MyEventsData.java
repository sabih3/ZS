package netaq.com.zayedsons.eventbus;

import java.util.List;

import netaq.com.zayedsons.model.Event;

/**
 * Created by sabih on 12-Mar-18.
 */

public class MyEventsData {
    private List<Event> myEventsList;

    public MyEventsData(List<Event> myEventsList) {
        this.myEventsList = myEventsList;
    }

    public List<Event> getMyEventsList() {
        return myEventsList;
    }
}
