package edu.uncc.mad.huduku.providers;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import edu.uncc.mad.huduku.YelpApi2;

public class Yelp {
	
	private static String consumerKey = "UNlfee1QCFT1zZBplqIW-A";
	private static String consumerSecret = "IIle3ly9Dap0Ebda29Iskew_jgk";
	private static String token = "qh8lqUfehC99F0l3a7aKK0w2L3wyP5hY";
	private static String tokenSecret = "angIT4ZVxWbtIcTWRMcKWuVQVtQ";

	private OAuthService service;
	private Token accessToken;

	/**
	 * Setup the Yelp API OAuth credentials.
	 * 
	 * OAuth credentials are available from the developer site, under Manage
	 * API access (version 2 API).
	 * 
	 * @param consumerKey Consumer key
	 * @param consumerSecret Consumer secret
	 * @param token Token
	 * @param tokenSecret Token secret
	 */
	public Yelp() {
		this.service = new ServiceBuilder().provider(YelpApi2.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
		this.accessToken = new Token(token, tokenSecret);
	}

	/**
	 * Search with term and location.
	 * 
	 * @param term Search term
	 * @param latitude Latitude
	 * @param longitude Longitude
	 * @return JSON string response
	 */
	public String search(String term, double latitude, double longitude) {
		
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
		request.addQuerystringParameter("term", term);
		request.addQuerystringParameter("radius_filter", "3000");
		request.addQuerystringParameter("ll", latitude + "," + longitude);
		request.addQuerystringParameter("sort", "1");
		request.addQuerystringParameter("limit", "10");
		
		this.service.signRequest(this.accessToken, request);
		
		Response response = request.send();
		return response.getBody();
	}
	
	public String searchBusiness(String businessId){
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/business/" + businessId);
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();
		return response.getBody();
	}
}