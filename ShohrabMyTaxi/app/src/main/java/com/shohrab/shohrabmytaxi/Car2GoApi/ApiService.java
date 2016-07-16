package com.shohrab.shohrabmytaxi.Car2GoApi;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by shohrab on 5/24/2016.
 * This interface provides an Observable API response
 */
public interface ApiService {
    @GET("/car2go/vehicles.json")
    Observable<ApiResponse> getCarList();
}
