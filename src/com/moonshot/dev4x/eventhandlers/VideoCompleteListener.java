package com.moonshot.dev4x.eventhandlers;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

public class VideoCompleteListener implements MediaPlayer.OnCompletionListener{
	private Context context;
	@Override
	public void onCompletion(MediaPlayer mp) {
		//finish video activity so user can go back to previous screen
		//Do the database activity like increasing count and records.
		((Activity)this.context).finish();
	}
	
	public VideoCompleteListener(Context context){
	    this.context=context;
	}
}
