package com.moonshot.dev4x.ui;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.helpers.DatabaseHelper;
import com.moonshot.dev4x.helpers.SharedPreferencesHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends FragmentActivity{
	DatabaseHelper db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Setting up full screen mode for kisok mode
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//////////////
		setContentView(R.layout.activity_main);
		
		//Creating Home Fragment and Replace it's layout to content holder
		Fragment homeFragment = new HomeFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.contentHolder, homeFragment).commit();
		
		//Checking if user is coming back to this activity after completing video then start the assessment
		if(getIntent().hasExtra("assesment")){
			FragmentManager fragmentManager = getSupportFragmentManager();
			Fragment assessmentFragment = new AssesmentFragment();
			Bundle bundle = new Bundle();
			bundle.putString("contentId", String.valueOf(getIntent().getExtras().getInt("contentId")));
			bundle.putString("nodeId", String.valueOf(getIntent().getExtras().getInt("nodeId")));
			bundle.putString("skillId", String.valueOf(getIntent().getExtras().getInt("skillId")));
			assessmentFragment.setArguments(bundle);
		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		    fragmentTransaction.replace(R.id.contentHolder, assessmentFragment,"ASSESSMENT_FRAGMENT");
		    fragmentTransaction.addToBackStack(null);
		    fragmentTransaction.commit(); 
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//Check if there is a value of nodeId in shared preference
		SharedPreferencesHelper spHelper = new SharedPreferencesHelper(this);
		String nodeId = spHelper.getPreferenceValue("node_id");
		String contentId = spHelper.getPreferenceValue("content_id");
		String skillId = spHelper.getPreferenceValue("skill_id");

		if(nodeId != null){
			Log.v("Node","There is a node id");
			//Node id found, that means user is coming back from external app to our app.
			//Record end event in database
			db = new DatabaseHelper(this);
			db.createVideoConsumptionSessionEvent(Integer.parseInt(contentId),Integer.parseInt(nodeId),Integer.parseInt(skillId),"complete");

			FragmentManager fragmentManager = getSupportFragmentManager();
			Fragment assessmentFragment = new AssesmentFragment();
			Bundle bundle = new Bundle();
			bundle.putString("contentId",contentId);
			bundle.putString("nodeId", nodeId);
			bundle.putString("nodeId", nodeId);
			bundle.putString("nodeId", nodeId);
			bundle.putString("skillId", skillId);
			assessmentFragment.setArguments(bundle);
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.contentHolder, assessmentFragment,"ASSESSMENT_FRAGMENT");
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();



			//Remove node id from shared preference
			spHelper.removePreferenceValue("content_id");
			spHelper.removePreferenceValue("node_id");
			spHelper.removePreferenceValue("skill_id");
		}else{
			Log.v("Node","There is no node id");
		}
	}
}