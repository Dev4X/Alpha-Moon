package com.moonshot.dev4x.ui.leraningmap.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.TextView;

import com.moonshot.dev4x.BR;
import com.moonshot.dev4x.R;

/**
 * Created by adrian on 27/11/15.
 */
public class LearningMapFragmentViewModel extends BaseObservable {

    String test;

    public LearningMapFragmentViewModel() {
        test = "peter";
    }

    @Bindable
    public String getTest() {
        return test;
    }

    public View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)v).setTextColor(v.getContext().getResources().getColor(R.color.green));
                test = "hans";
                notifyPropertyChanged(BR.test);
            }
        };
    }
}
