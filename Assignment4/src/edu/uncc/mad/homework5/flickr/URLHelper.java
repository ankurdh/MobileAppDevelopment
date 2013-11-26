package edu.uncc.mad.homework5.flickr;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;

public class URLHelper {
	
	private static String url;
	private static List<NameValuePair> parameters;
	
	static {
		parameters = new ArrayList<NameValuePair>();
		
		parameters.add(0, new URLNameValuePair("method", "flickr.photos.search"));
		parameters.add(1, new URLNameValuePair("per_page", "100"));
		parameters.add(2, new URLNameValuePair("extras", "views,url_m"));
		parameters.add(3, new URLNameValuePair("nojsoncallback", "1"));
		parameters.add(4, new URLNameValuePair("tags", "uncc"));
		parameters.add(5, new URLNameValuePair("api_key", "833349b229a365e2578856bb9e70c9f6"));
		
	}
	
	public static String getURLForJSON(){

		parameters.add(6, new URLNameValuePair("format", "json"));
		
		return getURL();
		
	}
	
	private static String getURL() {
		try {

			url = URIUtils.createURI("http", "api.flickr.com", -1, "/services/rest", URLEncodedUtils.format(parameters, "UTF-8"), null).toString();
			
			return url;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	public static String getURLForXML(){
		
		if(parameters.size() > 6)
			parameters.remove(6);
	
		return getURL();
		
	}
}

class URLNameValuePair implements org.apache.http.NameValuePair {

	private String name;
	private String value;
	
	public URLNameValuePair(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}
	
}
