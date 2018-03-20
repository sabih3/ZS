package netaq.com.zayedsons.network;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import netaq.com.zayedsons.BuildConfig;
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
        httpClient= new OkHttpClient.Builder();
        httpClient.readTimeout(180, TimeUnit.SECONDS);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if(BuildConfig.DEBUG){

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(loggingInterceptor);

        }

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
