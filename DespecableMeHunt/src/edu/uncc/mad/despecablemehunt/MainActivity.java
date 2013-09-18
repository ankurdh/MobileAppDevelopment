package edu.uncc.mad.despecablemehunt;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView [] images = new ImageView[2];
		
		images[0] = (ImageView) findViewById(R.id.image00);
		images[0].setImageResource(R.drawable.icon2);
		
		images[1] = (ImageView) findViewById(R.id.image01);
		images[1].setImageResource(R.drawable.icon1);
		
		images[0].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Clicked on image with id: " + v.getId(), Toast.LENGTH_LONG).show();				
			}
		});
		
		images[1].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("Ankur", "Clicked on image with id: " + v.getId());
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
