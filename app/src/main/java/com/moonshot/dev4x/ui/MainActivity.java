package com.moonshot.dev4x.ui;

import com.moonshot.dev4x.R;

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
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Creating Home Fragment and Replace it's layout to content holder
		Fragment homeFragment = new HomeFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.contentHolder, homeFragment).commit();
		
		//Checking if user is coming back to this activity after completing video then start the assesment
		if(getIntent().hasExtra("assesment")){
			FragmentManager fragmentManager = getSupportFragmentManager();
			Fragment assessmentFragment = new AssesmentFragment();
			Bundle bundle = new Bundle();
			bundle.putString("nodeId", getIntent().getExtras().getString("nodeId"));
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
	}
}