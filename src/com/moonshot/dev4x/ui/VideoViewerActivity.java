package com.moonshot.dev4x.ui;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.eventhandlers.VideoCompleteListener;
import com.moonshot.dev4x.helpers.DatabaseHelper;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;
public class VideoViewerActivity extends Activity {
	VideoView videoView;
	int videoPosition;
	long videoSessionId;
	public int nodeId;
	DatabaseHelper db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_viewer);
		String content = getIntent().getExtras().getString("content");
		this.nodeId = getIntent().getExtras().getInt("id");
		
		//setting start event for video in database
		db = new DatabaseHelper(this);
		db.createVideoConsumptionSessionEvent(nodeId, "start");
		
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
	    	db.createVideoConsumptionSessionEvent(nodeId, "stop");
	    }else{
	    	Log.v("VideoStatus","VideoStatus Paused");
	    	db.createVideoConsumptionSessionEvent(nodeId, "pause");
	    }
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    //Database action to video resume
	    videoView.seekTo(this.videoPosition);
	    db.createVideoConsumptionSessionEvent(nodeId, "resume");
	    videoView.start();
	}
}
