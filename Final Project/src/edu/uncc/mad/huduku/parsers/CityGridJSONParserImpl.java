package edu.uncc.mad.huduku.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uncc.mad.huduku.core.Deal;
import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.core.Review;

public class CityGridJSONParserImpl implements Parser {

	@Override
	public List<Restaurant> getRestaurantsFrom(String JSON) {
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			JSONArray restaurantsJSONArray = new JSONObject(JSON).getJSONObject("results").getJSONArray("locations");
			
			if(restaurantsJSONArray == null)
				return restaurants;
			
			for(int i = 0 ; i < restaurantsJSONArray.length(); i ++){
				
				Restaurant restaurant = new Restaurant();
				JSONObject restaurantJSONObject = restaurantsJSONArray.getJSONObject(i);
			
				double rating = 0.0;
				try {
					rating = Double.parseDouble(restaurantJSONObject.getString("rating")); 
				} catch (NumberFormatException nfe){
					rating = 0.0;
				}
				
				restaurant.setName(restaurantJSONObject.getString("name"));
				restaurant.setId(restaurantJSONObject.getString("id"));
				restaurant.setRating(rating);

				//TODO setup restaurant deals and reviews. 
				
				restaurants.add(restaurant);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return restaurants;
	}

	@Override
	public List<Review> getReviewsFrom(JSONObject restaurant) throws JSONException {
		return null;
	}

	@Override
	public List<Deal> getDealsFrom(JSONObject restaurant) throws JSONException {
		return null;
	}

}
