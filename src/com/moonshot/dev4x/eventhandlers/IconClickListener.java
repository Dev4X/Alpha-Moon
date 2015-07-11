package com.moonshot.dev4x.eventhandlers;

import com.moonshot.dev4x.helpers.DatabaseHelper;
import com.moonshot.dev4x.models.Node;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class IconClickListener implements OnClickListener{
	private Context context;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ImageView icon = (ImageView)v;
		int nodeId = icon.getId();
		DatabaseHelper dbHelper = new DatabaseHelper(this.context);
		Node node = dbHelper.getNode(nodeId);
		
		if(node != null){
			//Get media content and play it.
			MediaPlayer mediaPlayer = MediaPlayer.create(context, context.getResources().getIdentifier("@raw/"+node.getContent(),null, context.getPackageName()));
			mediaPlayer.start(); 
		}
	}
	
	public IconClickListener(Context context){
	    this.context=context;
	}

}
