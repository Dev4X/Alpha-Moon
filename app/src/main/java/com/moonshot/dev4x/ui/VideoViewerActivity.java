package com.moonshot.dev4x.ui;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.eventhandlers.VideoCompleteListener;
import com.moonshot.dev4x.helpers.DatabaseHelper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;
public class VideoViewerActivity extends Activity {
	VideoView videoView;
	int videoPosition;
	long videoSessionId;
	public int nodeId;
	public int contentId;
	public int skillId;
	DatabaseHelper db;
	boolean isActivityStarted = false;
	public boolean isVideoCompleted = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Setting up full screen mode for kisok mode
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//////////////
		setContentView(R.layout.video_viewer);
		String content = getIntent().getExtras().getString("content");
		this.nodeId = getIntent().getExtras().getInt("node_id");
		this.contentId = getIntent().getExtras().getInt("content_id");
		this.skillId = getIntent().getExtras().getInt("skill_id");
		Log.v("Video_Id","Video_Id"+this.nodeId);
		//setting start event for video in database
		db = new DatabaseHelper(this);
		db.createVideoConsumptionSessionEvent(this.contentId, this.nodeId, this.skillId, "start");
		this.isActivityStarted = true;
		//assign video path and start playing
		this.videoView = (VideoView)findViewById(R.id.videoViewComponent);
		String path = "android.resource://" + getPackageName() + "/" + "raw/"+content;
		videoView.setVideoURI(Uri.parse(path));
		videoView.start();
		videoView.setOnCompletionListener(new VideoCompleteListener(this));
	}
	
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    //pause video play. This will be called when user press home button or back button
	    //Database action to video pause
	    this.videoPosition = videoView.getCurrentPosition(); 
	    videoView.pause();
	    
	    if(this.isFinishing()){
	    	Log.v("VideoStatus","VideoStatus stopped");
	    	//User press back button of android phone, hence stop event
	    	if(this.isVideoCompleted == false){
	    		db.createVideoConsumptionSessionEvent(this.contentId, this.nodeId, this.skillId, "stop");
	    	}else{
	    		//starting main activity and pass param to start assessment
	    		Intent mainIntent = new Intent(this, MainActivity.class);
	    		mainIntent.putExtra("assesment", true);
	    		mainIntent.putExtra("contentId", this.contentId);
				mainIntent.putExtra("nodeId", this.nodeId);
				mainIntent.putExtra("skillId", this.skillId);
				startActivity(mainIntent);
	    	}
	    }else{
	    	Log.v("VideoStatus","VideoStatus Paused");
	    	db.createVideoConsumptionSessionEvent(this.contentId, this.nodeId, this.skillId, "pause");
	    }
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    //Database action to video resume
	    videoView.seekTo(this.videoPosition);
	    if(!this.isActivityStarted){
	    	db.createVideoConsumptionSessionEvent(this.contentId, this.nodeId, this.skillId, "resume");
	    }else{
	    	this.isActivityStarted = false;
	    }
	    videoView.start();
	}
}