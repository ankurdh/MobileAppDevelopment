package edu.uncc.mad.despicablemehunt;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import edu.uncc.mad.despicablemehunt.sound.SoundManager;

public class ResultActivity extends Activity {

	private final static double TIME_LIMIT = 20.0d;
	private SoundManager soundManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		soundManager = SoundManager.getSoundManagerInstance(getApplicationContext());
		
		TextView resultHeading = (TextView) findViewById(R.id.textResultHeading);
		TextView resultMessage = (TextView) findViewById(R.id.textResultTime);
		ImageView resultImageView = (ImageView) findViewById(R.id.imageResult);
		Button buttonBack = (Button) findViewById(R.id.buttonBack);
		
		if(getIntent().getExtras() != null) {
			
			double resultTime = getIntent().getExtras().getDouble(MainActivity.RESULT_TIME);
			if(resultTime < TIME_LIMIT) {
				//print congratulations
				resultHeading.setText("Congratulations!!!");
				resultMessage.setText("It took you " + resultTime + "s to finish the game.");
				resultImageView.setImageResource(R.drawable.win);
				
				soundManager.playYMCA();
			}
			else {
				//print what took you so long
				resultHeading.setText("What took you so long ?!");
				resultMessage.setText("It took you " + resultTime + "s to finish the game.");
				resultImageView.setImageResource(R.drawable.loose);
				soundManager.playSound(SoundManager.MINION_WRONG_TOUCH);
			}
		}
		
		buttonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//finishes the activity and returns to main activity 
				soundManager.stopAnyPlayingSound();
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

}
