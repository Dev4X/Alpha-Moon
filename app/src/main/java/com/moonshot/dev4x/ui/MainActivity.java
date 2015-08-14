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

public class MainActivity extends FragmentActivity{
	DatabaseHelper db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			bundle.putString("nodeId", String.valueOf(getIntent().getExtras().getInt("nodeId")));
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
		String nodeId = spHelper.getPreferenceValue("nodeId");

		if(nodeId != null){
			Log.v("Node","There is a node id");
			//Node id found, that means user is coming back from external app to our app.
			//Record end event in database
			db = new DatabaseHelper(this);
			db.createVideoConsumptionSessionEvent(Integer.parseInt(nodeId),"complete");

			//Remove node id from shared preference
			spHelper.removePreferenceValue("nodeId");
		}else{
			Log.v("Node","There is no node id");
		}
	}
}