package edu.uncc.mad.huduku.providers;

public class YelpSearchProvider {

	final private String KEY = "restaurants";
	private double latitude;
	private double longitude;
	
	public YelpSearchProvider(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getSearchResults() {
		String searchResponse = new Yelp().search(KEY, latitude, longitude);
		return searchResponse;
	}
}