package com.moonshot.dev4x.ui.leraningmap;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.databinding.FragmentLearningMapBinding;
import com.moonshot.dev4x.ui.common.BaseFragment;
import com.moonshot.dev4x.ui.leraningmap.viewmodel.LearningMapFragmentViewModel;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

public class LearningMapFragment extends BaseFragment {

	public static LearningMapFragment newInstance() {

		Bundle args = new Bundle();
		LearningMapFragment fragment = new LearningMapFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected void initFromBundle(Bundle arguments) {

	}

	@Override
	protected int layoutToInflate() {
		return R.layout.fragment_learning_map;
	}

	@Override
	protected View bindDataToView(View view) {
		FragmentLearningMapBinding binding = DataBindingUtil.bind(view);
		binding.setVm(new LearningMapFragmentViewModel());
		return view;
	}
}
