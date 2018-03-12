package netaq.com.zayedsons.eventbus;

import java.util.List;

import netaq.com.zayedsons.model.Event;

/**
 * Created by sabih on 12-Mar-18.
 */

public class UpcomingEventsData {

    private List<Event> upcomingEventList;

    public UpcomingEventsData(List<Event> upcomingEvent) {
        this.upcomingEventList = upcomingEvent;
    }

    public List<Event> getUpcomingEventList() {
        return upcomingEventList;
    }
}
