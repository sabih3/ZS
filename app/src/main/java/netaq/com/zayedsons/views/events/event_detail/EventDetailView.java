package netaq.com.zayedsons.views.events.event_detail;

import java.util.List;

import netaq.com.zayedsons.network.model.responses.ResponseEventGallery;
import netaq.com.zayedsons.views.BaseView;

/**
 * Created by sabih on 12-Feb-18.
 */

public interface EventDetailView extends BaseView{

    void onJoinRequestSubmitted();

    void onEmptyGallery();

    void onGalleryFetched(List<ResponseEventGallery.Albums.Gallery> galleryList);
}
