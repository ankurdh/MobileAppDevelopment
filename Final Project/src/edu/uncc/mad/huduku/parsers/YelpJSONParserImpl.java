package edu.uncc.mad.huduku.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import edu.uncc.mad.huduku.core.Deal;
import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.core.Review;

public class YelpJSONParserImpl implements Parser {

	/**
	 * Processes a "business" yelp api response. The other methods parse "search" api responses.
	 */
	@Override
	public List<Review> getReviewsFrom(JSONObject restaurant) throws JSONException {
		JSONArray reviewsJSONArray = restaurant.getJSONArray("reviews");
		
		if(reviewsJSONArray == null || reviewsJSONArray.length() == 0)
			return null;
		
		List<Review> reviews = new ArrayList<Review>();
		
		for(int i = 0 ; i < reviewsJSONArray.length(); i++){
			Review review = new Review();
			
			JSONObject reviewJSONObject = reviewsJSONArray.getJSONObject(i);
			JSONObject reviewUser = reviewJSONObject.getJSONObject("user");
			
			review.setReviewRating(reviewJSONObject.getDouble("rating"));
			review.setReviewText(reviewJSONObject.getString("excerpt"));
			review.setTimePosted(reviewJSONObject.getLong("time_created"));
			review.setUserImageUrl(reviewUser.getString("image_url"));
			review.setUserName(reviewUser.getString("name"));
			
			reviews.add(review);
		}
		
		return reviews;
	}

	@Override
	public List<Restaurant> getRestaurantsFrom(String yelpSearchAPIResponseJSON) {
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			JSONArray restaurantsArray = new JSONObject(yelpSearchAPIResponseJSON).getJSONArray("businesses");
			
			for(int i = 0 ; i < restaurantsArray.length(); i ++){
				JSONObject restaurantJSONObject = restaurantsArray.getJSONObject(i);
				
				Restaurant restaurant = new Restaurant();
				
				List<Deal> deals = getDealsFrom(restaurantJSONObject);
				
				restaurant.setId(restaurantJSONObject.getString("id"));
				restaurant.setDeals(deals);
				restaurant.setName(restaurantJSONObject.getString("name"));
				restaurant.setRating(restaurantJSONObject.getDouble("rating"));
				
				restaurants.add(restaurant);				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return restaurants;
	}

	@Override
	public List<Deal> getDealsFrom(JSONObject restaurant) {

		List<Deal> deals = new ArrayList<Deal>();
		try {
			JSONArray dealsJSONArray = restaurant.getJSONArray("deals");
			
			if(dealsJSONArray == null || dealsJSONArray.length() == 0)
				return null;
			
			for(int i = 0 ; i < dealsJSONArray.length(); i++){
				JSONObject dealJSONObject = dealsJSONArray.getJSONObject(i);
				JSONArray dealOptions = dealJSONObject.getJSONArray("options");
				
				Deal deal = new Deal();
				
				deal.setTitle(dealJSONObject.getString("title"));
				deal.setRestrictions(dealJSONObject.getString("important_restrictions"));
				deal.setDealDetails(dealJSONObject.getString("what_you_get"));
				
				deal.setBuyUrl(dealOptions.getJSONObject(0).getString("purchase_url"));
				deal.setNewPrice(dealOptions.getJSONObject(0).getString("formatted_price"));
				deal.setOriginalPrice(dealOptions.getJSONObject(0).getString("formatted_original_price"));
				deal.setRemainingCount(dealOptions.getJSONObject(0).getInt("remaining_count"));
				
				deals.add(deal);
				
			}
			
		} catch (JSONException e){
			//e.printStackTrace();
			try {
				if(restaurant.has("name"))
					Log.d("ankur", "No deals for restaurant: " + restaurant.getString("name"));
			} catch (JSONException e1) {
				//nothing much we can do. But the exception will not occur because of the "has" 
				//method. 
			}
		}
		
		return deals;
		
	}
}
