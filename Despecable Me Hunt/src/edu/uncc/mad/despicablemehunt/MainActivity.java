/**
 * Homework #3
 * @author Ankur Huralikoppi, Vishwas Subramanian
 * 
 * NOTE: Please enable sounds on the emulator while testing the application.
 * 
 */

package edu.uncc.mad.despicablemehunt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import edu.uncc.mad.despicablemehunt.images.ImageHelper;
import edu.uncc.mad.despicablemehunt.sound.SoundManager;
import edu.uncc.mad.despicablemehunt.utils.TimeHelper;
 

public class MainActivity extends Activity implements OnClickListener{

	//DUMMY to keep a track of the result activity return;
	private int RESULT_ACTIVITY_RETURNED;
	
	private ImageHelper imageHelper;
	private int currentFocusImageId;
	private int totalProperImageClicks;
	private int currentIteration;
	private SoundManager soundManager;
	private TimeHelper timeHelper;
	
	//private double startTime;
	//private double stopTime;
	private double elapsedTime = 0.0d;
	
	final static String RESULT_TIME = "TIME";
	final static String SOUND_MANAGER = "SOUND_MANAGER";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		soundManager = SoundManager.getSoundManagerInstance(getApplicationContext());
		timeHelper = new TimeHelper();
		
		totalProperImageClicks = 0;
		currentIteration = 0;
		
		imageHelper = new ImageHelper();
		
		soundManager.playSound(SoundManager.MINION_LAUGH);
		soundManager.playSound(SoundManager.MINION_ELO);
		
		setupImageGrid();
		
		triggerNextRound(true);
		
		//put the button handlers here.
		findViewById(R.id.exitButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
		
		findViewById(R.id.restartButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cleanupForNextRound();
				triggerNextRound(true);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void triggerNextRound(boolean forceTimerRestart){
		
		timeHelper.startTimer(forceTimerRestart);
		
		currentFocusImageId = imageHelper.getFocusImageIdAtIteration(currentIteration++);
		((ImageView)findViewById(R.id.focusImage)).setImageResource(currentFocusImageId);
	}
	
	private void setupImageGrid(){
		
		setImage(R.id.image33, 0);
		setImage(R.id.image32, 2);
		setImage(R.id.image31, 1);
		setImage(R.id.image30, 3);
		
		setImage(R.id.image23, 4);
		setImage(R.id.image22, 5);
		setImage(R.id.image21, 7);
		setImage(R.id.image20, 6);
		
		setImage(R.id.image13, 8);
		setImage(R.id.image12, 9);
		setImage(R.id.image11, 10);
		setImage(R.id.image10, 11);
		
		setImage(R.id.image03, 12);
		setImage(R.id.image02, 13);
		setImage(R.id.image01, 14);
		setImage(R.id.image00, 15);
	}
	
	private void setImage(int imageId, int offset){
		ImageView image = (ImageView)findViewById(imageId);
		image.setImageResource(imageHelper.getGridImageId(offset));
		image.setTag(imageHelper.getGridImageId(offset));
		image.setClickable(true);
		image.setOnClickListener(this);
		image.setAlpha(1.0f);
	}

	@Override
	public void onClick(View v) {
		
		ImageView clickedImage = (ImageView)v;
		int clickedImageId = (Integer)clickedImage.getTag();
		
		if(clickedImage.getAlpha() < 1.0f){
			soundManager.playSound(SoundManager.MINION_WRONG_TOUCH);
			return;
		}
		
		if(clickedImageId == currentFocusImageId){
			clickedImage.setAlpha(0.4f);
			totalProperImageClicks++;
			
			//check if the game is over:
			if(totalProperImageClicks == 16){
				
				try {
					elapsedTime = timeHelper.stopTimer();
					//Trigger the intent for the next screen here.
					Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
					intent.putExtra(RESULT_TIME, elapsedTime);
					startActivityForResult(intent, RESULT_ACTIVITY_RETURNED);
					
					return;
					
				} catch (Exception e) {
					//cannot call without starting the timer.
					e.printStackTrace();
				}
			}
			
			if(totalProperImageClicks % 4 == 0){
				soundManager.playHappySound();
				triggerNextRound(false);
			}
		}
		else {
			soundManager.playSound(SoundManager.MINION_WRONG_TOUCH);
		}
	}
	
	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent d){
		super.onActivityResult(reqCode, resCode, d);
		
		if(reqCode == RESULT_ACTIVITY_RETURNED){
			
			cleanupForNextRound();
			triggerNextRound(true);
		}
	}
	
	private void cleanupForNextRound(){
		totalProperImageClicks = 0;
		currentIteration = 0;
		
		if(imageHelper == null)
			imageHelper = new ImageHelper();
		else 
			imageHelper.shuffleArrays();
		
		setupImageGrid();
	}
	
}