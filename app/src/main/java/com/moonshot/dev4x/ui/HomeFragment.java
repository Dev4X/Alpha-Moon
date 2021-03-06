package com.moonshot.dev4x.ui;

import java.util.List;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.helpers.DatabaseHelper;
import com.moonshot.dev4x.models.*;
import com.moonshot.dev4x.eventhandlers.IconClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.util.Log;

public class HomeFragment extends Fragment {
	DatabaseHelper dbHelper;
	List<SkillSets> skillSetsList;
	LinearLayout homeInnerContentContainer;
	LinearLayout homeInnerContentContainer1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			//Inflate home layout to replace content holder.
			View rootView = inflater.inflate(R.layout.home, null);
			homeInnerContentContainer = (LinearLayout) rootView
					.findViewById(R.id.homeInnerContentContainerRow1);
			homeInnerContentContainer1 = (LinearLayout) rootView
				.findViewById(R.id.homeInnerContentContainerRow2);
			//Creating database helper object to get data.
			dbHelper = new DatabaseHelper(getActivity());
			
			//Populates all the nodes for display.
			getNodesToDisplay();
			buildLayout();
			return rootView;
	}
	
	public void getNodesToDisplay(){
		skillSetsList = dbHelper.getAllSkillSets();
	}
	
	public void buildLayout(){
		for(int i=0;i<skillSetsList.size();i++){
			Log.v("node","node_count");
			ImageView nodeImage = createImageView(i);
			if(i>2){
				homeInnerContentContainer1.addView(nodeImage);
			}else {
				homeInnerContentContainer.addView(nodeImage);
			}
		}
	}
	
	public ImageView createImageView(int imageCount){
		ImageView nodeImage = new ImageView(getActivity());
		/////Setting up layout params
		LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity=Gravity.CENTER;
		layoutParams.rightMargin = 20;
		layoutParams.leftMargin = 20;
		nodeImage.setLayoutParams(layoutParams);
		nodeImage.setId(skillSetsList.get(imageCount).getId());
		////////////
		
		////Setting up image drawable to show icon
		nodeImage.setScaleType(ScaleType.CENTER_INSIDE);
		int imageResource = getResources().getIdentifier(skillSetsList.get(imageCount).getIcon(), "drawable", getActivity().getPackageName());
		Drawable res = getResources().getDrawable(imageResource);
		nodeImage.setImageDrawable(res);
		////////////////////////
		
		///////Adding event handler////
		nodeImage.setOnClickListener(new IconClickListener(getActivity()));
		//////////
		return nodeImage;
	}
}
