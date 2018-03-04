package netaq.com.zayedsons.network.model;



import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Sabih Ahmed on 4/03/2018.
 */

public interface ClickATellService {

//    @POST(".")
//    Call<ResponseClickATellAccount> sendOTP(@Body ClickATellMessageObject messageObject);

    @POST(".")
    Observable<ResponseClickATellAccount> sendOTP(@Body ClickATellMessageObject messageObject);

}
