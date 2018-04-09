package netaq.com.zayedsons.views;

import android.content.Context;

import java.net.UnknownHostException;
import java.util.List;

import netaq.com.zayedsons.model.Lookup;
import netaq.com.zayedsons.model.Lookups;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.network.NetworkErrorResolver;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestLookup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sabih on 20-Mar-18.
 */

public class SearchableListPresenter {

    private final LookupListView viewListener;
    private final Context mContext;

    public SearchableListPresenter(Context context, LookupListView viewListener) {
        this.mContext = context;
        this.viewListener = viewListener;
    }

    public void getDataFromNetwork(int caller) {
        RequestLookup requestLookup = new RequestLookup();
        requestLookup.setForID(Constants.FOR_ID_LOOKUP_COUNTRY);

        Call<Lookup> requestLookUp = null;

        if(caller==Constants.CALLER_CITY){
            requestLookUp = RestClient.getAdapter().getCities(requestLookup);
        }else if (caller==Constants.CALLER_SPONSOR){
            requestLookUp = RestClient.getAdapter().getSponsors();
        }


        viewListener.showProgress();

        requestLookUp.enqueue(new Callback<Lookup>() {
            @Override
            public void onResponse(Call<Lookup> call, Response<Lookup> response) {
                viewListener.hideProgress();

                if(response.isSuccessful()){
                    List<Lookups> lookups = response.body().getLookups();
                    viewListener.onLookupDataFetched(lookups);

                }else{
                    String resolvedError = NetworkErrorResolver.resolveError(mContext, response.body());
                    viewListener.onError(resolvedError);
                }
            }

            @Override
            public void onFailure(Call<Lookup> call, Throwable t) {
                viewListener.hideProgress();
                if(t instanceof UnknownHostException){
                    viewListener.onNetworkUnAvailable();
                }

            }
        });
    }
}

