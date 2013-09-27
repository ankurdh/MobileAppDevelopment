/**
 * Authors: Ankur Huralikoppi, Vishwas Subramanian
 * Homework 4
 */ 

package edu.uncc.mad.homework4;

import edu.uncc.mad.homework4.constants.PhotoActivityMode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	public final static String PHOTO_ACTIVITY_MODE = "PHOTO_ACTIVITY_MODE";
	public final static String SLIDE_SHOW_MODE = "SLIDE_SHOW_MODE";
	
	private Button photosButton;
	private Button slideShowButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		photosButton = (Button) findViewById(R.id.photosButton);
		slideShowButton = (Button) findViewById(R.id.slideShowButton);
		
		photosButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
				intent.putExtra(PHOTO_ACTIVITY_MODE, PhotoActivityMode.PHOTO_MODE);		
				
				startActivity(intent);
			}
		});
		
		slideShowButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO invoke the Photo Activity in the Slide Show mode. 
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
}
