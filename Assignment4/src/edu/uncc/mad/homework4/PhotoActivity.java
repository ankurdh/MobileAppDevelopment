package edu.uncc.mad.homework4;

import edu.uncc.mad.homework4.constants.PhotoActivityMode;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.widget.Toast;

public class PhotoActivity extends Activity {

	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		this.setTitle(R.string.title_activity_photo);
		
		if(getIntent().getExtras().getInt(MainActivity.PHOTO_ACTIVITY_MODE) == PhotoActivityMode.PHOTO_MODE)
			Toast.makeText(getApplicationContext(), "Started Photo Activity in Photo Mode", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo, menu);
		return true;
	}

}
