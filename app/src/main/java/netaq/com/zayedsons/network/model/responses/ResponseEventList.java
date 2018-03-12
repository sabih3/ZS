package netaq.com.zayedsons.network.model.responses;

import java.util.List;

import netaq.com.zayedsons.model.Event;

/**
 * Created by sabih on 12-Mar-18.
 */

public class ResponseEventList extends BaseResponse{

    private List<Event> Upcoming;
    private List<Event> MyEvents;
    private List<Event> Archive;

    public List<Event> getUpcoming() {
        return Upcoming;
    }

    public List<Event> getMyEvents() {
        return MyEvents;
    }

    public List<Event> getArchive() {
        return Archive;
    }
}
