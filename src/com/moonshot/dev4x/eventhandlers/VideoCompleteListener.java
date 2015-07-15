package com.moonshot.dev4x.eventhandlers;

import com.moonshot.dev4x.helpers.DatabaseHelper;
import com.moonshot.dev4x.ui.VideoViewerActivity;
import android.content.Context;
import android.media.MediaPlayer;

public class VideoCompleteListener implements MediaPlayer.OnCompletionListener{
	private Context context;
	@Override
	public void onCompletion(MediaPlayer mp) {
		//finish video activity so user can go back to previous screen
		//Do the database activity like increasing count and records.
		DatabaseHelper db =  new DatabaseHelper(context);
		db.increaseViewCountofContent(((VideoViewerActivity)this.context).nodeId);
		db.createVideoConsumptionSessionEvent(((VideoViewerActivity)this.context).nodeId, "complete");//Adding complete event
		((VideoViewerActivity)this.context).finish();
	}
	
	public VideoCompleteListener(Context context){
	    this.context=context;
	}
}
