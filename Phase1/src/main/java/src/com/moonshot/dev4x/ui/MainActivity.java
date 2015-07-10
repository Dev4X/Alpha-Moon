package com.moonshot.dev4x.ui;

import com.moonshot.dev4x.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Creating Home Fragment and Replace it's layout to content holder
		Fragment homeFragment = new HomeFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.contentHolder, homeFragment).commit();
		
	}
}