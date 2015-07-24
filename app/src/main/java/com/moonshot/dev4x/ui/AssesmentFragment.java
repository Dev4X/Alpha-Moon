package com.moonshot.dev4x.ui;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.eventhandlers.LetterIconOnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import java.util.Timer;
import java.util.TimerTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.media.MediaPlayer;


public class AssesmentFragment extends Fragment {
	HashMap<String, String> letters=new HashMap<String, String>();//used to hold letters and there icons mapping
	List<String> lettersKeys = new ArrayList<String>();//used to hold letters
	List<String> availableLetterKeys = new ArrayList<String>();//used in assessment to hold lettters that are pending for assessment
	LinearLayout assesmentContainer;
	int totalAssessmentTries = 10;
	int currentAssessmentCount = 0;
	String currentLetterOfAssessment = "";
	int currentTryOfAssessment = 0;
	TextView numberOfTriesLeft;
	Timer nextAssessmentTimer;
	int totalCorrectAnswers = 0;
	long assessmentStartTime;
	long assessmentEndTime;
	int totalInCorrectAnswers=0;
	Timer repeatPromptTimer;
	MediaPlayer mediaPlayer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			//Inflate home layout to replace content holder.
			View rootView = inflater.inflate(R.layout.assesment, null);
			assesmentContainer = (LinearLayout) rootView
				.findViewById(R.id.assesmentContainer);
			numberOfTriesLeft = (TextView) rootView
					.findViewById(R.id.numberOfTriesLeft);
			//Adding letters to collections, later it will be random three letters selection from alphabet.
			letters.put("A", "ablue");
			letters.put("B", "bblue");
			letters.put("C", "cblue");

			//Adding letters to collections, later it will be random three letters selection from alphabet.
			lettersKeys.add("A");
			lettersKeys.add("B");
			lettersKeys.add("C");

			availableLetterKeys.add("A");
			availableLetterKeys.add("B");
			availableLetterKeys.add("C");
			assessmentStartTime = System.currentTimeMillis();
			startAssessment();
			return rootView;
	}

	//function to start assessment
	public void startAssessment(){
		currentAssessmentCount++;//increase count
		currentTryOfAssessment++;//increase tries count
		numberOfTriesLeft.setText("Tries " + currentAssessmentCount + "/" + totalAssessmentTries);//show on view
		randomizeList();
		setRandomLetterForAssessment();
		repeatPrompt();
	}

	//function to randomize letters and show on ui
	public void randomizeList(){

		//shuffling collections to generate random order.
		Collections.shuffle(lettersKeys);

		//Addind letters icons to layout in random order
		assesmentContainer.removeAllViews();
		for(int i=0;i<lettersKeys.size();i++){
			String letter = lettersKeys.get(i);
			String icon = letters.get(letter);

			//creating image view to add in container.
			ImageView letterImage = new ImageView(getActivity());
			LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.gravity= Gravity.CENTER;
			layoutParams.rightMargin = 20;
			layoutParams.leftMargin = 20;
			letterImage.setLayoutParams(layoutParams);

			////Setting up image drawable to show icon
			letterImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			int imageResource = getResources().getIdentifier(icon, "drawable", getActivity().getPackageName());
			Drawable res = getResources().getDrawable(imageResource);
			letterImage.setImageDrawable(res);
			letterImage.setTag(letter);
			////////////////////////
			letterImage.setOnClickListener(new LetterIconOnClickListener(getActivity()));
			assesmentContainer.addView(letterImage);
		}
	}

	//selecting random letter for current assessment
	public void setRandomLetterForAssessment(){
		//Setting random letter for current assessment;
		int min = 0;
		int max = (availableLetterKeys.size() - 1);
		Random r = new Random();
		int number = r.nextInt(max - min + 1) + min;
		currentLetterOfAssessment = availableLetterKeys.get(number);
		//Remove used letter for next assessment.
		availableLetterKeys.clear();
		for(int i=0;i<lettersKeys.size();i++){
			if(!lettersKeys.get(i).equals(currentLetterOfAssessment)) {
				availableLetterKeys.add(lettersKeys.get(i));
			}
		}
	}

	//play letter touch audio prompt
	public void playAudioPrompt(){
		int audioResources = getResources().getIdentifier(currentLetterOfAssessment.toLowerCase() + "touchsay", "raw", getActivity().getPackageName());
		mediaPlayer = MediaPlayer.create(getActivity(), audioResources);
		mediaPlayer.start();
	}

	//function to check user answer
	public void checkAnswer(String letterTouched, ImageView icon){
		if(letterTouched.equals(currentLetterOfAssessment)){
			correctAnswer(letterTouched, icon);
		}else{
			wrongAnswer(letterTouched, icon);
		}
	}

	//function called when answer is correct
	public void correctAnswer(String letterTouched, ImageView icon){
		//change to green icon and play cheerful sound and restart assessment
		int imageResource = getResources().getIdentifier(letterTouched.toLowerCase()+"green", "drawable", getActivity().getPackageName());
		Drawable res = getResources().getDrawable(imageResource);
		icon.setImageDrawable(res);
		totalCorrectAnswers++;
		playSuccessPrompt();
		stopRepeatPrompt();

		//check if user has identified all the three letters
		if((currentTryOfAssessment == totalAssessmentTries) || totalCorrectAnswers == 3){
			//Game is over.
			nextAssessmentTimer = new Timer();
			TimerTask delayedTask = new TimerTask() {
				@Override
				public void run() {
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							numberOfTriesLeft.setText("Game Over");
							assesmentContainer.removeAllViews();
							assessmentEndTime = System.currentTimeMillis();
						}
					});
				}
			};
			nextAssessmentTimer.schedule(delayedTask, 3000);
		}else{
			startAssessmentAfterDelay();
		}
	}

	//function called when answer is wrong
	public void wrongAnswer(String letterTouched, ImageView icon){
		//change to grey icon, remove icon listener and play wrong sound
		int imageResource = getResources().getIdentifier(letterTouched.toLowerCase()+"greyed", "drawable", getActivity().getPackageName());
		Drawable res = getResources().getDrawable(imageResource);
		icon.setImageDrawable(res);
		icon.setOnClickListener(null);
		currentAssessmentCount++;
		numberOfTriesLeft.setText("Tries " + currentAssessmentCount + "/" + totalAssessmentTries);
		totalInCorrectAnswers++;
	}

	//function to add some delay in starting next assessment so user can hear cheerful sound
	public void startAssessmentAfterDelay(){
		nextAssessmentTimer = new Timer();
		TimerTask delayedTask = new TimerTask() {
			@Override
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						nextAssessmentTimer = null;
						startAssessment();
					}
				});
			}
		};
		nextAssessmentTimer.schedule(delayedTask, 6000);
	}

	//function to play cheerful sound on correct anser
	public void playSuccessPrompt(){
		int audioResources = getResources().getIdentifier("cheering", "raw", getActivity().getPackageName());
		MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), audioResources);
		mediaPlayer.start();
	}

	//function to repeat prompt letter touch
	public void repeatPrompt(){
		repeatPromptTimer = new Timer();
		repeatPromptTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				playAudioPrompt();
			}
		}, 0, 6000);//3 seocnds to play prompt + 3 seconds delay so total 6 seconds
	}

	//stop repeating prompt on correct answer.
	public void stopRepeatPrompt(){
		repeatPromptTimer.cancel();
		if(mediaPlayer.isPlaying()){
			mediaPlayer.stop();
		}
	}
}
