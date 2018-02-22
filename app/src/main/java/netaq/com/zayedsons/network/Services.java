package netaq.com.zayedsons.network;

import netaq.com.zayedsons.model.Cities;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sabih on 14-Feb-18.
 */

public interface Services {

    @GET(EndPoints.CITIES)
    Call<Cities> getCities();
}
