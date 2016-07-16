package com.shohrab.shohrabmytaxi.CarInListView;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shohrab.shohrabmytaxi.BaseFragment;
import com.shohrab.shohrabmytaxi.Car2GoApi.ApiResponse;
import com.shohrab.shohrabmytaxi.Car2GoApi.ApiService;
import com.shohrab.shohrabmytaxi.MyApplication;
import com.shohrab.shohrabmytaxi.ParcelableModel.CarParcelable;
import com.shohrab.shohrabmytaxi.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by shohrab on 5/24/2016.
 * ButterKnife is used for vew injection and listview click listener
 * Dagger is used for DI
 */
public class MainFragment extends BaseFragment implements MainActivityContract.View {

    @Bind(R.id.fragment_main_lvCars)
    ListView mLvCars;

    @Bind(R.id.fragment_main_PB)
    ProgressBar mPB;

    @Inject
    MainActivityPresenter mMainActivityPresenter;

    @Inject
    Application mApplication;

    private MainActivityAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
        setRetainInstance(true);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * DI graph is created in this method so satisfy all DI for this class
     */

    @Override
    protected void setupFragmentComponent() {

        MyApplication.get(getActivity())
                .getmAppComponent()
                .inject(new MainActivityModule(this))
                .inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        mMainActivityPresenter.callAPIService();

        return rootView;
    }

    @Override
    public void loadCars(List<ApiResponse.PlaceMarks> carList) {

        mAdapter = new MainActivityAdapter(getActivity(), carList);
        mLvCars.setAdapter(mAdapter);
        //setListAdapter(mAdapter);


    }

    @Override
    public void showPB(boolean isDisplay) {
        mPB.setVisibility(isDisplay ? View.VISIBLE : View.GONE);
    }


    @OnItemClick(R.id.fragment_main_lvCars)
    public void listItemClick(int position) {

        mMainActivityPresenter.listViewClick(position);
    }
}
