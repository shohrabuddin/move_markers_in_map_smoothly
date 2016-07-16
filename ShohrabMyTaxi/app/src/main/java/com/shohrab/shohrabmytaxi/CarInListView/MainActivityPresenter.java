package com.shohrab.shohrabmytaxi.CarInListView;

import android.app.Application;
import android.content.Intent;

import com.shohrab.shohrabmytaxi.Car2GoApi.ApiResponse;
import com.shohrab.shohrabmytaxi.Car2GoApi.ApiService;
import com.shohrab.shohrabmytaxi.CarInMapView.MapsActivity;
import com.shohrab.shohrabmytaxi.ParcelableModel.CarParcelable;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shohrab on 5/24/2016.
 * All Actions of MainActivity are maintained in this class
 */
public class MainActivityPresenter implements MainActivityContract.Action{

    private MainActivityContract.View mView;
    private ApiService mApiServices;
    private Application mApplication;
    public static ArrayList<ApiResponse.PlaceMarks> sCarList;


    public MainActivityPresenter(MainActivityContract.View  fragmentView, ApiService apiServices, Application application) {

        mView = fragmentView;
        mApiServices = apiServices;
        mApplication = application;
    }

    @Override
    public void listViewClick(int position) {

        ArrayList<CarParcelable> carParcelableList = new ArrayList<CarParcelable>();

        for(ApiResponse.PlaceMarks car : sCarList ){
            carParcelableList.add(new CarParcelable(car.getCoordiatesList().get(0),
                    car.getCoordiatesList().get(1), car.getCarName(), car.getAddress()));
        }

        Intent intent = new Intent(mApplication, MapsActivity.class);
        intent.putParcelableArrayListExtra("carList", carParcelableList);
        intent.putExtra("selectedCar", position);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        mApplication.startActivity(intent);

    }

    @Override
    public void callAPIService() {

        if (sCarList != null) {
            mView.loadCars(sCarList);
        } else {

            mView.showPB(true);

            mApiServices.getCarList()
                    .subscribeOn(Schedulers.newThread())
                    .cache()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.showPB(false);
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(ApiResponse apiResponses) {
                            mView.showPB(false);
                            mView.loadCars(apiResponses.getPlaceMarksList());
                            sCarList = apiResponses.getPlaceMarksList();
                        }
                    });

        }
    }
}
