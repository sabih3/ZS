package netaq.com.zayedsons.network;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import netaq.com.zayedsons.model.Cities;
import netaq.com.zayedsons.network.model.ResponseFileUpload;
import netaq.com.zayedsons.network.model.ResponseGUID;
import netaq.com.zayedsons.network.model.UploadFile;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

import static netaq.com.zayedsons.network.EndPoints.GUID;

/**
 * Created by sabih on 14-Feb-18.
 */

public interface Services {

    @GET(EndPoints.CITIES)
    Call<Cities> getCities();

    @POST(GUID)
    Observable<ResponseGUID> getGUID();

    @POST(EndPoints.FILE_UPLOAD)
    Observable<ResponseFileUpload> uploadPhoto(@Body UploadFile uploadFile);
}
