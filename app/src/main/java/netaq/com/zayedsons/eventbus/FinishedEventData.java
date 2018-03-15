package netaq.com.zayedsons.eventbus;

import java.util.List;

import netaq.com.zayedsons.model.Event;

/**
 * Created by sabih on 12-Mar-18.
 */

public class FinishedEventData {
    private List<Event> finishedEventList;

    public FinishedEventData(List<Event> archiveEventList) {
        this.finishedEventList = archiveEventList;
    }

    public List<Event> getFinishedEventList() {
        return finishedEventList;
    }
}
