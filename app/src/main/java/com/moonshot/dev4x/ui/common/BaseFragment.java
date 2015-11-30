package com.moonshot.dev4x.ui.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentController;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by adrian on 27/11/15.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            initFromBundle(arguments);
        }
        if(savedInstanceState != null) {
            initFromBundle(savedInstanceState);
        }
    }

    protected void initFromBundle(Bundle arguments) {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layoutToInflate(), container, false);
        ButterKnife.bind(this, view);
        return bindDataToView(view);
    }

    protected abstract View bindDataToView(View view);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected abstract int layoutToInflate();
}
