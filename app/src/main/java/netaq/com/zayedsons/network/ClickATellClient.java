package netaq.com.zayedsons.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sabih on 04-Mar-18.
 */

public class ClickATellClient {

    private static ClickATellService clickATellService;

    private ClickATellClient() {

    }

    static {
        setUpTheClient();
    }

    private static void setUpTheClient() {


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder().
                        addHeader("Authorization", "bearer 3fU6crReAYWXmhd0ymHeELI_7tCEDggS97glcdOUGnX_BqiVODiLLJGEHrYFkCM_ekCQaOCpdt")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .addHeader("X-Version", "1");

                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EndPoints.ACCOUNT_CLICK_A_TELL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        clickATellService = retrofit.create(ClickATellService.class);
    }

    public static ClickATellService getAdapter() {

        return clickATellService;
    }
}
