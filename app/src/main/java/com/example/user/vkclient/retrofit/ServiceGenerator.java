package com.example.user.vkclient.retrofit;


import com.example.user.vkclient.App;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static String BASE_URL = "https://api.vk.com/";

    public static int REMOVE_INTERCEPTOR_FROM_REQUEST = 0;
    private static int flag = 1;




    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request;
                    HttpUrl.Builder url;
                    if(ServiceGenerator.flag == 1) {
                        request = chain.request();
                        url = request.url().newBuilder().addQueryParameter("access_token", App.getSharedPrefHelper().getAccessToken()).addQueryParameter("v", App.getAPI_VERSION());
                        return chain.proceed(request.newBuilder().url(String.valueOf(url)).build());
                    }
                    else {
                        ServiceGenerator.flag = 1;
                        request = chain.request();
                        url = request.url().newBuilder().addQueryParameter("v", App.getAPI_VERSION());
                        return chain.proceed(request.newBuilder().url(String.valueOf(url)).build());
                    }
                }
            })
            .build();




    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static void setFlag(int flag){
        if(flag == REMOVE_INTERCEPTOR_FROM_REQUEST){
            ServiceGenerator.flag = flag;
        }
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
