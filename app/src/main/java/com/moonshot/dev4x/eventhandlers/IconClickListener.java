package com.moonshot.dev4x.eventhandlers;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.helpers.DatabaseHelper;
import com.moonshot.dev4x.models.Node;
import com.moonshot.dev4x.ui.HomeFragment;
import com.moonshot.dev4x.ui.VideoViewerActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class IconClickListener implements OnClickListener{
	private Context context;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ImageView icon = (ImageView)v;
		int nodeId = icon.getId();
		DatabaseHelper dbHelper = new DatabaseHelper(this.context);
		Node node = dbHelper.getNode(nodeId);
		
		if(node != null){
			//Creating Video Viewer Fragment.
			Intent videoIntent = new Intent((FragmentActivity)context, VideoViewerActivity.class);
			videoIntent.putExtra("content", node.getContent());
			videoIntent.putExtra("id", node.getId());
			((FragmentActivity)context).startActivity(videoIntent);
			((FragmentActivity)context).finish();
		}
	}
	
	public IconClickListener(Context context){
	    this.context=context;
	}

}
