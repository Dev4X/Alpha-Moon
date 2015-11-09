package com.moonshot.dev4x.eventhandlers;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

public class VideoErrorListener implements MediaPlayer.OnErrorListener{
	private Context context;
	
	public VideoErrorListener(Context context){
	    this.context=context;
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		//Database operations to record errors.
		//finish video activity so user can go back to previous screen
		((Activity)this.context).finish();
		return false;
	}
}
