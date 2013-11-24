/**
* @Author: Ankur Huralikoppi
*/

package edu.uncc.mad.huduku;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Menu;

import com.example.citygridbasics.R;

import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.observer.LocationChangeObserver;
import edu.uncc.mad.huduku.parsers.CityGridJSONParserImpl;
import edu.uncc.mad.huduku.parsers.GooglePlacesJSONParserImpl;
import edu.uncc.mad.huduku.parsers.YelpJSONParserImpl;
import edu.uncc.mad.huduku.pinning.SharedPreferenceManager;
import edu.uncc.mad.huduku.providers.CityGrid;
import edu.uncc.mad.huduku.providers.GooglePlacesProvider;
import edu.uncc.mad.huduku.providers.YelpBusinessProvider;
import edu.uncc.mad.huduku.providers.YelpSearchProvider;

public class MainActivity extends Activity implements LocationChangeObserver {

	private Handler handler;
	private ExecutorService es;

	private ProgressDialog dialog;
//	private LocationObservable locationProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * Initialize the Pinned places handler module
		 */
		SharedPreferenceManager.createInstance(getApplication(), getString(R.string.PINNED_PLACES_FILE_NAME));

		/**
		 * USAGE:
		 * SharedPreferenceManager manager = SharedPreferenceManager.getSharedPreferenceManager();
		 */

		es = Executors.newFixedThreadPool(1);

		dialog = new ProgressDialog(MainActivity.this);
		dialog.setCancelable(false);
		dialog.setMessage("Getting data from internet");

		handler = new Handler(new Callback(){

			@Override
			synchronized public boolean handleMessage(Message msg) {
				dialog.dismiss();

//				ArrayList<Restaurant> restaurants = msg.getData().getParcelableArrayList("RESTAURANTS");
				//((TextView)findViewById(R.id.restaurantsCount)).setText(restaurants.get(2).getDeals().get(0).getBuyUrl());

				return false;
			}
		});

		onLocationChanged(35.306993, -80.723481);
//		onLocationChanged(35.231006,-80.839666);
//		onLocationChanged(35.22569,-80.838336);

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
//		es.execute(new YelpProvider(handler, latitude, longitude));
		es.execute(new CityGridProvider(handler, latitude, longitude));
//		es.execute(new GoogleProvider(handler, latitude, longitude));
	}
}

class CityGridProvider implements Runnable {

	private double lat;
	private double lon;
	private Handler handler;

	public CityGridProvider(Handler handler, double lat, double lon ){
		this.lat = lat;
		this.lon = lon;
		this.handler = handler;
	}

	@Override
	public void run(){
		CityGrid cg = new CityGrid();
		String restaurantsJSONString = cg.placesSearch(lat, lon);
		CityGridJSONParserImpl parser = new CityGridJSONParserImpl();
		List<Restaurant> restaurants = parser.getRestaurantsFrom(restaurantsJSONString);

		for(Restaurant r: restaurants){
			try {
				r.setReviews(parser.getReviewsFrom(new JSONObject(cg.reviewsSearch(r.getId()))));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		Message msg = new Message();
		Bundle bundle = new Bundle();

		bundle.putParcelableArrayList("RESTAURANTS", (ArrayList<Restaurant>) restaurants);
		msg.setData(bundle);

		handler.sendMessage(msg);
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
		String searchJSONResponse = new YelpSearchProvider(latitude, longitude).getSearchResults();

		List<Restaurant> restaurants = new YelpJSONParserImpl().getRestaurantsFrom(searchJSONResponse);
		YelpBusinessProvider yelpBusinessProvider;

		for(Restaurant r: restaurants){
			yelpBusinessProvider = new YelpBusinessProvider(r);
			r = yelpBusinessProvider.getRestaurantReviews();
			Log.d("ankur", "YELP - Name: " + r.getName());
		}

		Message msg = new Message();
		Bundle bundle = new Bundle();

		bundle.putParcelableArrayList("RESTAURANTS", (ArrayList<Restaurant>) restaurants);
		msg.setData(bundle);

		handler.sendMessage(msg);

	}
}


class GoogleProvider implements Runnable {

	private Handler handler;
	private double latitude;
	private double longitude;

	public GoogleProvider(Handler handler, double latitude, double longitude){
		this.handler = handler;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public void run() {
		GooglePlacesProvider googlePlacesProvider = new GooglePlacesProvider();
		GooglePlacesJSONParserImpl googleJSONParser = new GooglePlacesJSONParserImpl();

		String searchJSONResponse = googlePlacesProvider.getGoogleSearchRestaurants(latitude, longitude);

		List<Restaurant> restaurants = googleJSONParser.getRestaurantsFrom(searchJSONResponse);

		for(Restaurant r: restaurants){
			Log.d("ankur", "Google Restaurant: " + r.getName());
			try {
				r.setReviews(googleJSONParser.getReviewsFrom(new JSONObject(googlePlacesProvider.getGoogleRestaurantData(r.getId()))));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		Message msg = new Message();
		Bundle bundle = new Bundle();

		bundle.putParcelableArrayList("RESTAURANTS", (ArrayList<Restaurant>) restaurants);
		msg.setData(bundle);

		handler.sendMessage(msg);

	}
}