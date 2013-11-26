package edu.uncc.mad.homework5.constants;

public interface FlickrRequest {

	int XML_RESPONSE = 1;
	int JSON_RESPONSE = 2;
	
	int MAX_ENTRIES_PER_PAGE = 100;
	
	String HOST = "api.flickr.com/services/rest";
	String PATH = "/services/rest/";
	String METHOD = "flickr.photos.search";
	String TAGS = "uncc";
	String API_KEY = "833349b229a365e2578856bb9e70c9f6";
	String EXTRAS = "views,url_m";
	String JSON_FORMAT = "json";
	String PER_PAGE = "100";
	
	String METHOD_ID = "method";
	String API_KEY_ID = "api_key";
	String TAGS_ID = "tags";
	String FORMAT_ID = "format";
	String PER_PAGE_ID = "per_page";
	
}
