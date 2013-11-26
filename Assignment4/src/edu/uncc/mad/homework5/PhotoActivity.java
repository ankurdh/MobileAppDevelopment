/**
 * Authors: Ankur Huralikoppi, Vishwas Subramanian
 * Homework 4
 */ 

package edu.uncc.mad.homework5;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.uncc.mad.homework4.R;
import edu.uncc.mad.homework5.constants.PhotoActivityMode;
import edu.uncc.mad.homework5.disklru.DiskLruCacheProvider;
import edu.uncc.mad.homework5.flickr.Photo;

public class PhotoActivity extends Activity {
	
	private static final String PHOTO_IMAGE = "PHOTO_IMAGE";
	private static final int THREAD_COUNT = 1;
	
	private ProgressDialog progressDialog;
	private ArrayList<Photo> urlList;
	private int photoIdToShow;
	private ImageView photoImageView;
	
	private TextView viewsTextView;
	private TextView titleTextView;
	
	private PhotoFetcher photoFetcher;
	private Handler handler;
	private ExecutorService photoFetcherPool;
	
	private DiskLruCacheProvider diskLruCache;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		this.setTitle(R.string.title_activity_photo);
		
		viewsTextView = (TextView)findViewById(R.id.viewsTextView);
		titleTextView = (TextView)findViewById(R.id.titleTextView);
		
		//initialize the threadpool
		photoFetcherPool = Executors.newFixedThreadPool(THREAD_COUNT);
		
		//initialize the Handler.
		handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				//get and display the image.
				Bitmap image = (Bitmap)msg.getData().get(PHOTO_IMAGE);
				photoImageView.setImageBitmap(image);
				
				String title = urlList.get(photoIdToShow).getTitle();
				int views = urlList.get(photoIdToShow).getViews();
				
				titleTextView.setText(title);
				viewsTextView.setText("Views: " + views);
				
				//dismiss the progress dialog
				progressDialog.dismiss();
				
				return false;
			}
		});
		
		//initialize the DiskLruProvider.
		diskLruCache = new DiskLruCacheProvider(getApplicationContext());
		
		//initialize the image View
		photoImageView = (ImageView)findViewById(R.id.newPhotoImageViewer);
		
		//initialize the progress dialog.
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading Image..");
		
		//always start showing from the first photo
		photoIdToShow = 0; 
		
		//populate the URL list.
		urlList = getIntent().getParcelableArrayListExtra(MainActivity.PHOTOS_PARCEL);
		
		if(getIntent().getExtras().getInt(MainActivity.PHOTO_ACTIVITY_MODE) == PhotoActivityMode.PHOTO_MODE) {
		
			// now we have the array of urls. Begin with loading the first
			// image.
			fetchPhoto(photoIdToShow);

			photoImageView.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View photoShower, MotionEvent touchDetail) {
					float x = touchDetail.getX();

					// check if the location is in the first/last 20% of the
					// image.
					if (x < 0.2 * photoImageView.getWidth())
						photoIdToShow = ((photoIdToShow + urlList.size() - 1) % urlList.size());
					else if (x > 0.8 * photoImageView.getWidth())
						photoIdToShow = ((photoIdToShow + 1) % urlList.size());
					else
						return false;

					fetchPhoto(photoIdToShow);
					return false;
				}
			});
		} else if (getIntent().getExtras().getInt(MainActivity.PHOTO_ACTIVITY_MODE) == PhotoActivityMode.SLIDE_SHOW_MODE){
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo, menu);
		return true;
	}
	
	private void fetchPhoto(int photoId){
		
		//Create a new photofetcher with the required data for the image. 
		photoFetcher = new PhotoFetcher(String.valueOf(photoId), urlList.get(photoIdToShow).getImageURL());
		
		//ask the thread pool to get the photo for us. 
		photoFetcherPool.execute(photoFetcher);
		
		//show the dialog till we have our photo.
		progressDialog.show();
		
	}
	
	class PhotoFetcher implements Runnable {

		private String photoId;
		private String url;
		
		public PhotoFetcher(String photoId, String url){
			this.photoId = photoId;
			this.url = url;
		}
		
		@Override
		public void run() {
			
			//check if the disk cache has the image already.
			if(diskLruCache.containsKey(photoId)){
				
				Bundle bundle = new Bundle();
				bundle.putParcelable(PhotoActivity.PHOTO_IMAGE, diskLruCache.getBitmap(photoId));
				
				Message msg = new Message();
				msg.setData(bundle);
				
				handler.sendMessage(msg);
				return;
			}
			
			//Ok, the disk cache doesn't has the image. 
			InputStream in = null;
			try {
				//fetch the image.
				in = new java.net.URL(url).openStream();
				
				//decode into a bitmap
				Bitmap image = BitmapFactory.decodeStream(in);
				
				//dump in the disk cache
				diskLruCache.put(photoId, image);
				
				//return the image. 
				Bundle bundle = new Bundle(); 
				
				//bitmap is already parcelable. Just dump it in the bundle to send it.
				bundle.putParcelable(PhotoActivity.PHOTO_IMAGE, image);
				
				Message msg = new Message();
				msg.setData(bundle);
				
				handler.sendMessage(msg);
			} catch (MalformedURLException e) {
				Toast.makeText(getApplicationContext(), "Failed to get image", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "Failed to get image", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			} finally {
				//ensure the input stream is closed.
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		//shutdown the thread pool.
		photoFetcherPool.shutdown();
		
		//clear the disk cache.
		diskLruCache.clearCache();
		
	}
}
