package com.shohrab.shohrabmytaxi.CarInListView;

import com.shohrab.shohrabmytaxi.Car2GoApi.ApiResponse;
import com.shohrab.shohrabmytaxi.Car2GoApi.ApiService;

import java.util.List;

/**
 * Created by shohrab on 5/24/2016.
 * This contract is implemented in Presenter and View class
 */
public interface MainActivityContract {

    public interface View{

        public void loadCars(List<ApiResponse.PlaceMarks> carList);
        public void showPB(boolean isDisplay);

    }

    public interface Action{

        public void listViewClick(int position);
        public void callAPIService();
    }
}
