package com.shohrab.shohrabmytaxi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by shohrab on 5/24/2016.
 * This abstract class is used to set up component for each Fragment
 */
public abstract  class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupFragmentComponent();
    }

    protected abstract void setupFragmentComponent();
}
