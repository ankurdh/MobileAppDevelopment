/**
 * Authors: Ankur Huralikoppi, Vishwas Subramanian
 * Homework 4
 */ 

package edu.uncc.mad.homework4;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;
import edu.uncc.mad.homework4.constants.PhotoActivityMode;
import edu.uncc.mad.homework4.disklru.DiskLruCacheProvider;

public class PhotoActivity extends Activity {
	
	private ProgressDialog progressDialog;
	private String [] urlList;
	private int photoIdToShow;
	private ImageView photoImageView;
	
	private PhotoFetcher photoFetcher;
	
	private DiskLruCacheProvider diskLruCache;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		this.setTitle(R.string.title_activity_photo);
		
		//initialize the DiskLruProvider.
		diskLruCache = new DiskLruCacheProvider(getApplicationContext());
		
		//initialize the image View
		photoImageView = (ImageView)findViewById(R.id.photoImageViewer);
		
		//initialize the progress dialog.
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading Image..");
		
		//always start showing from the first photo
		photoIdToShow = 0; 
		
		//populate the URL list.
		urlList = getResources().getStringArray(R.array.photo_urls);
		
		//now we have the array of urls. Begin with loading the first image.
		fetchPhoto(photoIdToShow);
		
		photoImageView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View photoShower, MotionEvent touchDetail) {
				float x = touchDetail.getX();
				
				//check if the location is in the first/last 20% of the image. 
				if( x < 0.2 * photoImageView.getWidth())
					photoIdToShow = ((photoIdToShow + urlList.length - 1 ) % urlList.length);
				else if (x > 0.8 * photoImageView.getWidth())
					photoIdToShow = ((photoIdToShow + 1) % urlList.length);
				else
					return false;
				
				fetchPhoto(photoIdToShow);
				return false;
			}
		});
		
		if(getIntent().getExtras().getInt(MainActivity.PHOTO_ACTIVITY_MODE) == PhotoActivityMode.PHOTO_MODE)
			Toast.makeText(getApplicationContext(), "Started Photo Activity in Photo Mode", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo, menu);
		return true;
	}
	
	private void fetchPhoto(int photoId){
		
		photoFetcher = new PhotoFetcher();
		photoFetcher.execute(new String[] {urlList[photoIdToShow], String.valueOf(photoId)});
		progressDialog.show();
		
	}
	
	class PhotoFetcher extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... url) {
			
			//check if the disk cache has the image already.
			if(diskLruCache.containsKey(url[1])){
				return diskLruCache.getBitmap(url[1]);
			}
			
			//Ok, the disk cache doesn't has the image. 
			InputStream in = null;
			try {
				//fetch the image.
				in = new java.net.URL(url[0]).openStream();
				
				//decode into a bitmap
				Bitmap image = BitmapFactory.decodeStream(in);
				
				//dump in the disk cache
				diskLruCache.put(url[1], image);
				
				//return the image. 
				return image;
			} catch (MalformedURLException e) {
				Toast.makeText(getApplicationContext(), "Failed to get image", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "Failed to get image", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
	        return null;
		}

		/**
		 * The below function will execute in the Photo Activity thread.
		 */
		@Override
		protected void onPostExecute(Bitmap photo) {
			super.onPostExecute(photo);
			progressDialog.dismiss();
			photoImageView.setImageBitmap(photo);			
		}
	}
}
