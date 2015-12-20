package com.moonshot.dev4x.ui;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.ui.common.BaseActivity;
import com.moonshot.dev4x.ui.leraningmap.LearningMapActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;


public class VideoViewerActivity extends BaseActivity {

	VideoView videoView;
	int videoPosition;
	long videoSessionId;
	public int nodeId;
	public int contentId;
	public int skillId;
	boolean isActivityStarted = false;
	public boolean isVideoCompleted = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		//Setting up full screen mode for kisok mode
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		//////////////
//		String content = getIntent().getExtras().getString("content");
//		this.nodeId = getIntent().getExtras().getInt("node_id");
//		this.contentId = getIntent().getExtras().getInt("content_id");
//		this.skillId = getIntent().getExtras().getInt("skill_id");
		Log.v("Video_Id","Video_Id"+this.nodeId);
		//setting start event for video in database
		this.isActivityStarted = true;
		//assign video path and start playing
		this.videoView = (VideoView)findViewById(R.id.videoViewComponent);
		String path = "android.resource://" + getPackageName() + "/" + "raw/"+"slug";
		videoView.setVideoURI(Uri.parse(path));
		videoView.start();
	}

	public static Intent createIntent(Context context) {
		return new Intent(context, VideoViewerActivity.class);
	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_video;
	}

	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    //pause video play. This will be called when user press fragment_map button or back button
	    //Database action to video pause
	    this.videoPosition = videoView.getCurrentPosition(); 
	    videoView.pause();
	    
	    if(this.isFinishing()){
	    	Log.v("VideoStatus","VideoStatus stopped");
	    	//User press back button of android phone, hence stop event
	    	if(this.isVideoCompleted == false){
	    	}else{
	    		//starting main activity and pass param to start assessment
	    		Intent mainIntent = new Intent(this, LearningMapActivity.class);
	    		mainIntent.putExtra("assesment", true);
	    		mainIntent.putExtra("contentId", this.contentId);
				mainIntent.putExtra("nodeId", this.nodeId);
				mainIntent.putExtra("skillId", this.skillId);
				startActivity(mainIntent);
	    	}
	    }else{
	    	Log.v("VideoStatus","VideoStatus Paused");
	    }
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    //Database action to video resume
	    videoView.seekTo(this.videoPosition);
	    if(!this.isActivityStarted){
	    }else{
	    	this.isActivityStarted = false;
	    }
	    videoView.start();
	}
}