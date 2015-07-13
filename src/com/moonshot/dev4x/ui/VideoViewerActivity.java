package com.moonshot.dev4x.ui;

import com.moonshot.dev4x.R;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
public class VideoViewerActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_viewer);
		String content = getIntent().getExtras().getString("content");
		VideoView videoView = (VideoView)findViewById(R.id.videoViewComponent);
		String path = "android.resource://" + getPackageName() + "/" + "raw/"+content;
		videoView.setVideoURI(Uri.parse(path));
		videoView.start();
	}
}
