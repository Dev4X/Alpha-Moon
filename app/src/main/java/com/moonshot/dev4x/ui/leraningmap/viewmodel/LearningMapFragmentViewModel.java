package com.moonshot.dev4x.ui.leraningmap.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.TextView;

import com.moonshot.dev4x.BR;
import com.moonshot.dev4x.R;
import com.moonshot.dev4x.ui.H5PViewerActivity;
import com.moonshot.dev4x.ui.VideoViewerActivity;

/**
 * Created by adrian on 27/11/15.
 */
public class LearningMapFragmentViewModel extends BaseObservable {

    private final Context context;

    public LearningMapFragmentViewModel(Context context) {
        this.context = context;
    }

    public View.OnClickListener onClickNode1() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(H5PViewerActivity.createIntent(context));
            }
        };
    }

    public View.OnClickListener onClickNode2() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(VideoViewerActivity.createIntent(context));
            }
        };
    }
}
