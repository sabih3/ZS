package netaq.com.zayedsons.events.upcoming;

/**
 * Created by sabih on 08-Feb-18.
 */

public class UpComingPresenter {

    private final UpComingView view;

    public UpComingPresenter(UpComingView viewListener) {
        this.view = viewListener;
    }

    public void fetchUpComingEventsList() {
        view.onUpComingEventsFetched();
    }
}
