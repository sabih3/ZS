package netaq.com.zayedsons.views;

/**
 * Created by sabih on 01-Mar-18.
 */

public interface BaseView {

    void onNetworkUnAvailable();
    void onError(String resolvedError);
    void showProgress();
    void hideProgress();
}
