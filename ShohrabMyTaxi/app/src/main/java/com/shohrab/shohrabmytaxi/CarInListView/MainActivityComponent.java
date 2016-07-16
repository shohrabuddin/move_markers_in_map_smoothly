package com.shohrab.shohrabmytaxi.CarInListView;

import com.shohrab.shohrabmytaxi.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by shohrab on 5/24/2016.
 */
@ActivityScope
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent {

    MainFragment inject (MainFragment mainActivity);
}
