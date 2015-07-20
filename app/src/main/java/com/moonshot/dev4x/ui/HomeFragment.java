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

public class HomeFragment extends Fragment {
	DatabaseHelper dbHelper;
	List<Node> nodeList;
	LinearLayout homeContainer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			//Inflate home layout to replace content holder.
			View rootView = inflater.inflate(R.layout.home, null);
			homeContainer = (LinearLayout) rootView
					.findViewById(R.id.homeContainer);
			//Creating database helper object to get data.
			dbHelper = new DatabaseHelper(getActivity());
			
			//Populates all the nodes for display.
			getNodesToDisplay();
			buildLayout();
			return rootView;
	}
	
	public void getNodesToDisplay(){
		nodeList = dbHelper.getAllNodes();
	}
	
	public void buildLayout(){
		for(int i=0;i<nodeList.size();i++){
			ImageView nodeImage = createImageView(i);
			homeContainer.addView(nodeImage);
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
		nodeImage.setId(nodeList.get(imageCount).getId());
		////////////
		
		////Setting up image drawable to show icon
		nodeImage.setScaleType(ScaleType.CENTER_INSIDE);
		int imageResource = getResources().getIdentifier(nodeList.get(imageCount).getIcon(), "drawable", getActivity().getPackageName());
		Drawable res = getResources().getDrawable(imageResource);
		nodeImage.setImageDrawable(res);
		////////////////////////
		
		///////Adding event handler////
		nodeImage.setOnClickListener(new IconClickListener(getActivity()));
		//////////
		return nodeImage;
	}
}
