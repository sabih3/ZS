package netaq.com.zayedsons.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sabih on 14-Feb-18.
 */

public class RestClient {
    private static Services servicesInterface;
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient;

    private RestClient(){

    }

    static {
        setUpRestClient();
    }


    private static void setUpRestClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient= new OkHttpClient.Builder();
        httpClient.readTimeout(120, TimeUnit.SECONDS);

        httpClient.addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(EndPoints.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        servicesInterface = retrofit.create(Services.class);
    }


    public static Services getAdapter(){


        return servicesInterface;
    }

    public static Retrofit getRetrofit() {


        return retrofit;
    }


}
