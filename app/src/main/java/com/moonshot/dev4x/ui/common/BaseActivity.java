package com.moonshot.dev4x.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by adrian on 27/11/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayoutId());
        ButterKnife.bind(this);
        if(savedInstanceState != null) {
            initiateFromBundle(savedInstanceState);
        }
        if(getIntent() != null && getIntent().getExtras() != null) {
            initiateFromBundle(getIntent().getExtras());
        }
    }

    protected abstract int getContentLayoutId();

    protected void initiateFromBundle(Bundle bundle) {

    }

    public void addFragment(@IdRes final int fragmentLayoutId, final BaseFragment fragment, String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentLayoutId, fragment, tag);
        fragmentTransaction.commit();
    }
}
