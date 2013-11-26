/**
 * Authors: Ankur Huralikoppi, Vishwas Subramanian
 * Homework 4
 */ 

package edu.uncc.mad.homework5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import edu.uncc.mad.homework4.R;
import edu.uncc.mad.homework5.constants.Logging;
import edu.uncc.mad.homework5.constants.PhotoActivityMode;
import edu.uncc.mad.homework5.flickr.Photo;
import edu.uncc.mad.homework5.flickr.URLHelper;
import edu.uncc.mad.homework5.parsers.FlickrResponseParser;
import edu.uncc.mad.homework5.parsers.JSONFlickrParser;
import edu.uncc.mad.homework5.parsers.XMLFlickrParser;

public class MainActivity extends Activity {

	public final static String PHOTO_ACTIVITY_MODE = "PHOTO_ACTIVITY_MODE";
	public final static String SLIDE_SHOW_MODE = "SLIDE_SHOW_MODE";
	public final static String PHOTOS_PARCEL = "PHOTOS_PARCEL";
	protected static final String PHOTOS_RESPONSE = "PHOTOS_RESPONSE";
	
	private boolean START_ACTIVITY_AS_PHOTO_MODE;
	private boolean START_ACTIVITY_AS_SLIDE_SHOW_MODE;
	
	private Button photosButton;
	private Button slideShowButton;
	
	private RadioGroup dataTypeRadioGroup;
	
	private ExecutorService flickerPhotoFetcherPool;
	private FlickrResponseParser flickrResponseParser;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		START_ACTIVITY_AS_PHOTO_MODE = false;
		START_ACTIVITY_AS_SLIDE_SHOW_MODE = false;
		
		final Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
		
		flickerPhotoFetcherPool = Executors.newFixedThreadPool(1);
		
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Retrieving Images Info..");
		
		dataTypeRadioGroup = (RadioGroup)findViewById(R.id.formatRadioGroup);
		
		photosButton = (Button) findViewById(R.id.photosButton);
		slideShowButton = (Button) findViewById(R.id.slideShowButton);
		
		handler = new Handler(new Callback(){

			@Override
			public boolean handleMessage(Message msg) {
				
				String photosResponseString = msg.getData().getString(PHOTOS_RESPONSE);
				
				ArrayList<Photo> photos = flickrResponseParser.parseFlickrResponseString(photosResponseString);
				
				Collections.sort(photos);
				
				progressDialog.dismiss();
				
				if(START_ACTIVITY_AS_PHOTO_MODE)
					intent.putExtra(PHOTO_ACTIVITY_MODE, PhotoActivityMode.PHOTO_MODE);
				else if(START_ACTIVITY_AS_SLIDE_SHOW_MODE)
					intent.putExtra(PHOTO_ACTIVITY_MODE, PhotoActivityMode.SLIDE_SHOW_MODE);
					
				intent.putParcelableArrayListExtra(PHOTOS_PARCEL, photos);
				
				startActivity(intent);
				
				return false;
			}
			
		});
		
		photosButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				boolean asJSON = false;
				
				if(dataTypeRadioGroup.getCheckedRadioButtonId() == R.id.xmlRadio)
					flickrResponseParser = new XMLFlickrParser();
				else {
					flickrResponseParser = new JSONFlickrParser();
					asJSON = true;
				}
				
				START_ACTIVITY_AS_PHOTO_MODE = true;
				START_ACTIVITY_AS_SLIDE_SHOW_MODE = false;
				
				flickerPhotoFetcherPool.execute(new FlickrResponseFetcher(asJSON));
				
				progressDialog.show();
				
			}
		});
		
		slideShowButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
								
			}
		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	class FlickrResponseFetcher implements Runnable {

		private boolean asJSON;
		
		public FlickrResponseFetcher(boolean asJSON){
			this.asJSON = asJSON;
		}

		@Override
		public void run() {
			
			String uri = asJSON ? URLHelper.getURLForJSON() : URLHelper.getURLForXML();
			
			HttpGet httpGet = new HttpGet(uri);
			StringBuffer sb = null;
			HttpResponse response = null;
			try {
				response = new DefaultHttpClient().execute(httpGet);
				
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					BufferedReader responseReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					
					sb = new StringBuffer();
					String line = "";
					
					try {
						while( (line = responseReader.readLine()) != null)
							sb.append(line).append("\n");
					} finally {
						responseReader.close();
					}
				} else {
					Log.d(Logging.LOG_TAG, "ERROR HTTP RESPONSE: " + response.getStatusLine().getStatusCode());
				}
				
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Bundle bundle = new Bundle();
			bundle.putString(MainActivity.PHOTOS_RESPONSE, sb.toString());
			
			Message msg = new Message();
			msg.setData(bundle);
			
			handler.sendMessage(msg);
			
			//sb.to string is the one to return
		}
	}	
}
