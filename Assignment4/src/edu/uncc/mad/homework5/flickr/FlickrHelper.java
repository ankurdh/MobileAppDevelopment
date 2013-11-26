package edu.uncc.mad.homework5.flickr;

public interface FlickrHelper {
	
	String JSON_URI = "http://api.flickr.com/services/rest/?method=flickr.photos.search&per_page=100&extras=views,url_m&nojsoncallback=1&format=json&tags=uncc&api_key=833349b229a365e2578856bb9e70c9f6";
	String XML_URI = "http://api.flickr.com/services/rest/?method=flickr.photos.search&per_page=100&extras=views,url_m&nojsoncallback=1&tags=uncc&api_key=833349b229a365e2578856bb9e70c9f6";
	
}