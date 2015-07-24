package com.moonshot.dev4x.eventhandlers;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.moonshot.dev4x.ui.AssesmentFragment;

public class LetterIconOnClickListener implements OnClickListener{
	private Context context;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ImageView icon = (ImageView)v;
		String letter = icon.getTag().toString();
		FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
		AssesmentFragment fragment = (AssesmentFragment)fragmentManager.findFragmentByTag("ASSESSMENT_FRAGMENT");
		fragment.checkAnswer(letter, icon);
	}

	public LetterIconOnClickListener(Context context){
	    this.context=context;
	}

}
