package com.moonshot.dev4x.ui;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.eventhandlers.VideoCompleteListener;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;
public class VideoViewerActivity extends Activity {
	VideoView videoView;
	int videoPosition;
	public int nodeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_viewer);
		String content = getIntent().getExtras().getString("content");
		this.nodeId = getIntent().getExtras().getInt("id");
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
	    Log.v("Video","Video Paused");
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	  //Database action to video resume
	    videoView.seekTo(this.videoPosition);
	    videoView.start(); //Or use resume() if it doesn't work. I'm not sure
	}
}
