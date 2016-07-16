package com.shohrab.shohrabmytaxi.Car2GoApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shohrab on 5/24/2016.
 * This class converts the json API response to POJO
 */

public class ApiResponse {

    @SerializedName("placemarks")
    ArrayList<PlaceMarks> placeMarksList;

    public ArrayList<PlaceMarks> getPlaceMarksList() {
        return placeMarksList;
    }

    public void setPlaceMarksList(ArrayList<PlaceMarks> placeMarksList) {
        this.placeMarksList = placeMarksList;
    }


    public class PlaceMarks {

        @SerializedName("name")
        String carName;
        @SerializedName("engineType")
        String engineType;
        @SerializedName("address")
        String address;
        @SerializedName("coordinates")
        List<Double> coordiatesList;


        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getEngineType() {
            return engineType;
        }

        public void setEngineType(String engineType) {
            this.engineType = engineType;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<Double> getCoordiatesList() {
            return coordiatesList;
        }

        public void setCoordiatesList(List<Double> coordiatesList) {
            this.coordiatesList = coordiatesList;

        }
    }
}
