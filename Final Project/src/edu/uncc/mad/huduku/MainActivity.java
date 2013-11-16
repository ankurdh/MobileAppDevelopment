package edu.uncc.mad.huduku;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Menu;
import android.widget.TextView;

import com.example.citygridbasics.R;

import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.observer.LocationChangeObserver;
import edu.uncc.mad.huduku.observer.LocationObservable;
import edu.uncc.mad.huduku.parsers.YelpJSONParserImpl;
import edu.uncc.mad.huduku.providers.YelpBusinessProvider;
import edu.uncc.mad.huduku.providers.YelpSearchProvider;

public class MainActivity extends Activity implements LocationChangeObserver {
	
	private Handler handler;
	private ExecutorService es;
	ProgressDialog dialog;
	LocationObservable locationProvider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		es = Executors.newFixedThreadPool(1);
		
		dialog = new ProgressDialog(MainActivity.this);
		dialog.setCancelable(false);
		dialog.setMessage("Getting data from internet");

		handler = new Handler(new Callback(){

			@Override
			public boolean handleMessage(Message msg) {
				dialog.dismiss();
				
				ArrayList<Restaurant> restaurants = msg.getData().getParcelableArrayList("YELP_RESTAURANTS");
				((TextView)findViewById(R.id.restaurantsCount)).setText(restaurants.get(2).getDeals().get(0).getBuyUrl());
				
				return false;
			}
		});
		
		onLocationChanged(35.306993, -80.723481);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(double latitude, double longitude) {
		dialog.show();
		es.execute(new YelpProvider(handler, latitude, longitude));
	}
}

class YelpProvider implements Runnable {
	
	private Handler handler;	
	private double latitude;
	private double longitude;
	
	public YelpProvider(Handler handler, double latitude, double longitude){
		this.handler = handler;
		this.latitude = latitude;
		this.longitude = longitude;
		
	}

	@Override
	public void run() {
//		String searchJSONResponse = new YelpSearchProvider(35.306993, -80.723481).getSearchResults();
		String searchJSONResponse = new YelpSearchProvider(latitude, longitude).getSearchResults();
		
		List<Restaurant> restaurants = new YelpJSONParserImpl().getRestaurantsFrom(searchJSONResponse);
		YelpBusinessProvider yelpBusinessProvider; 
		
		for(Restaurant r: restaurants){
			yelpBusinessProvider = new YelpBusinessProvider(r);
			r = yelpBusinessProvider.getRestaurantReviews();
		}
		
		Message msg = new Message();
		Bundle bundle = new Bundle();
		
		bundle.putParcelableArrayList("YELP_RESTAURANTS", (ArrayList<Restaurant>) restaurants);
		msg.setData(bundle);
		
		handler.sendMessage(msg);
		
	}
}

