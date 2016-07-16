package com.shohrab.shohrabmytaxi.Car2GoApi;

import android.app.Application;

import com.shohrab.shohrabmytaxi.R;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by shohrab on 5/24/2016.
 * This Module class provides singleton objects of different classes which are used
 * throughout the application
 */
@Module
public class ApiModule {

    private Application application;

    public ApiModule(Application application) {
        this.application = application;
    }

    public ApiModule(){}

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        return client;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Application application, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(application.getString(R.string.api_base_url))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
