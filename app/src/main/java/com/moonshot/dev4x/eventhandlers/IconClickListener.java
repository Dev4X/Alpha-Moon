package com.moonshot.dev4x.eventhandlers;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.helpers.DatabaseHelper;
import com.moonshot.dev4x.models.Content;
import com.moonshot.dev4x.models.ContentConsumptions;
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
import java.util.List;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.*;
import android.widget.Toast;
import android.util.Log;
import com.moonshot.dev4x.helpers.SharedPreferencesHelper;

public class IconClickListener implements OnClickListener{
	private Context context;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ImageView icon = (ImageView)v;
		int skillId = icon.getId();
		DatabaseHelper dbHelper = new DatabaseHelper(this.context);

		//First check what was the last content consumed for the skill set. if there is no content consumed previously,
		//Then start with first content
		//Get last consumed node id of skillset from database;
		ContentConsumptions consumption = dbHelper.getLastContentConsumptionDetailsForSkillSet(skillId);
		Content con = null;
		if(consumption == null){
			//No last consumption found, just go with first content
			con = dbHelper.getFirstContentForTheSkillSet(skillId);
			Log.v("content_consumption","content_consumption is null");
		}else{
			//Get next content in sequence
			Log.v("content_consumption","content_consumption is not null");
			con = dbHelper.getNextContentForTheSkillSet(skillId,consumption.getNodeId());
			if(con == null){
				con = dbHelper.getFirstContentForTheSkillSet(skillId);
			}
		}

		if(con!=null){
			Log.v("content","content is not null");
			Log.v("content","content is "+con.getContentType());
			Log.v("content", "content is " + con.getContentSubType());
			if(con.getContentType().equals("internal") && con.getContentSubType().equals("video")){
				Log.v("content","start playing video");
				Intent videoIntent = new Intent((FragmentActivity)context, VideoViewerActivity.class);
				videoIntent.putExtra("content", con.getContent());
				videoIntent.putExtra("content_id", con.getId());
				videoIntent.putExtra("skill_id", skillId);
				videoIntent.putExtra("node_id", con.getNodeId());
				((FragmentActivity)context).startActivity(videoIntent);
			}else if(con.getContentType().equals("external") && con.getContentSubType().equals("app")){
				//first check if external application is installed on phone.
				SharedPreferencesHelper spHelper = new SharedPreferencesHelper(this.context);
				PackageManager pm = ((FragmentActivity)context).getPackageManager();
				ApplicationInfo info = null;
				try {
					info = pm.getApplicationInfo(con.getContent(), 0);
				}catch (NameNotFoundException e) {
					info = null;
				}

				if(info == null){
					//Applicaiton not found, nothing to do but show Toast to user.
					Toast toast = Toast.makeText(context, "Content Not Found", Toast.LENGTH_LONG);
					dbHelper.createVideoConsumptionSessionEvent(con.getId(),con.getNodeId(),skillId, "not_found");
					toast.show();
				}else{
					//Launch the application
					Intent launchIntent = pm.getLaunchIntentForPackage(con.getContent());
					((FragmentActivity)context).startActivity(launchIntent);
					//Increasing view count of application.
					dbHelper.increaseViewCountOfSkill(con.getId());
					//Recording start event in database for application
					dbHelper.createVideoConsumptionSessionEvent(con.getId(),con.getNodeId(),skillId, "start");
					//Saving value in shared preference so we can track it again when app starts.
					spHelper.savePreferences("content_id",String.valueOf(con.getId()));
					spHelper.savePreferences("skill_id",String.valueOf(skillId));
					spHelper.savePreferences("node_id",String.valueOf(con.getNodeId()));
				}
			}
		}else{
			Toast toast = Toast.makeText(context, "Content Not Found", Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
	public IconClickListener(Context context){
	    this.context=context;
	}
}
