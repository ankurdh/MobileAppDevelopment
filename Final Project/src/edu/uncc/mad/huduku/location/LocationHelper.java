package edu.uncc.mad.huduku.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import edu.uncc.mad.huduku.observer.LocationChangeObserver;
import edu.uncc.mad.huduku.observer.LocationObservable;

public class LocationHelper implements LocationObservable {

	// private static final
	private LocationManager locationManager;
	private LocationChangeObserver observer;
	
	public LocationHelper(LocationManager loacManager){
		this.locationManager = loacManager;
	}
	
	public double [] getCurrentLocation(int maxInterval){
		Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
		
		double [] location = new double[2];
		location[0] = lastLocation.getLatitude();
		location[1] = lastLocation.getLongitude();
		
		return location;
	}
	
	private LocationListener listener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO nothing right now. 
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO nothing right now. 
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO nothing right now.
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			notifyObserver(location.getLatitude(), location.getLongitude());
			Log.d("ankur", "New Location: " + location.getLatitude() + "," + location.getLongitude());
		}
	};

	@Override
	public void registerLocationObserver(LocationChangeObserver observer) {
		this.observer = observer;
	}

	@Override
	public void notifyObserver(double latitude, double longitude) {
		if(observer == null)
			return;
		
		observer.onLocationChanged(latitude, longitude);
	}
}
