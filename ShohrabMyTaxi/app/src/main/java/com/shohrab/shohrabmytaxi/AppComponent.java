package com.shohrab.shohrabmytaxi;

import com.shohrab.shohrabmytaxi.Car2GoApi.ApiModule;
import com.shohrab.shohrabmytaxi.CarInListView.MainActivityComponent;
import com.shohrab.shohrabmytaxi.CarInListView.MainActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by shohrab on 5/24/2016.T
 * This Dagger component class creates a link between Module and Injection
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    MainActivityComponent inject (MainActivityModule module);

}
