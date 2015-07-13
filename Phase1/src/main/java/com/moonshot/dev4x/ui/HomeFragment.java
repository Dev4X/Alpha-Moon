package com.moonshot.dev4x.ui;

import java.util.List;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.helpers.DatabaseHelper;
import com.moonshot.dev4x.models.*;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
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
			ImageView nodeImage = new ImageView(getActivity());
			LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.gravity=Gravity.CENTER;
			nodeImage.setLayoutParams(layoutParams);
			nodeImage.setScaleType(ScaleType.CENTER_INSIDE);
			String uri = "@drawable/"+nodeList.get(i).getIcon();
			int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
			Drawable res = getResources().getDrawable(imageResource);
			nodeImage.setImageDrawable(res);
			homeContainer.addView(nodeImage);
		}
	}
}
