package com.moonshot.dev4x.eventhandlers;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.helpers.DatabaseHelper;
import com.moonshot.dev4x.models.Node;
import com.moonshot.dev4x.ui.HomeFragment;
import com.moonshot.dev4x.ui.VideoViewerActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.*;
import android.widget.Toast;

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
			if(node.getContentType().equals("internal") && node.getContentSubType().equals("video")){
				Intent videoIntent = new Intent((FragmentActivity)context, VideoViewerActivity.class);
				videoIntent.putExtra("content", node.getContent());
				videoIntent.putExtra("id", node.getId());
				((FragmentActivity)context).startActivity(videoIntent);
			}else if(node.getContentType().equals("external") && node.getContentSubType().equals("app")){
				//first check if external application is installed on phone.
				PackageManager pm = ((FragmentActivity)context).getPackageManager();
				ApplicationInfo info = null;
				try {
					info = pm.getApplicationInfo(node.getContent(), 0);
				}catch (NameNotFoundException e) {
					info = null;
				}

				if(info == null){
					//Applicaiton not found, nothing to do but show Toast to user.
					Toast toast = Toast.makeText(context, "Application "+node.getName() + " Not Found", Toast.LENGTH_LONG);
					toast.show();
				}else{
					//Launch the application
					Intent launchIntent = pm.getLaunchIntentForPackage(node.getContent());
					((FragmentActivity)context).startActivity(launchIntent);
				}
			}
		}
	}
	
	public IconClickListener(Context context){
	    this.context=context;
	}

}
