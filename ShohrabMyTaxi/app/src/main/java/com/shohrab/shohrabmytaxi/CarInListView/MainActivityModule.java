package com.shohrab.shohrabmytaxi.CarInListView;

import android.app.Application;

import com.shohrab.shohrabmytaxi.ActivityScope;
import com.shohrab.shohrabmytaxi.Car2GoApi.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shohrab on 5/24/2016.
 */
@Module
public class MainActivityModule {

    private MainFragment mMainFragment;

    public MainActivityModule(MainFragment mainFragment) {
        this.mMainFragment = mainFragment;
    }

    @Provides
    @ActivityScope
    MainFragment provideGitHubRepoListActivity(){
        return mMainFragment;
    }

    @Provides
    @ActivityScope
    MainActivityPresenter provideMainActivityPresenter(Application application, ApiService apiServices,
                                                           MainFragment mainActivity){

        return new MainActivityPresenter(mainActivity,apiServices,application);

    }
}
