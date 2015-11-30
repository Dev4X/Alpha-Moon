package com.moonshot.dev4x.ui.leraningmap;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.ui.common.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class LearningMapActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addFragment(R.id.learning_map_fragment_framelayout, LearningMapFragment.newInstance(), null);
	}

	@Override
	protected Intent createIntent(Context context) {
		return new Intent(context, LearningMapActivity.class);
	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_learning_map;
	}
}