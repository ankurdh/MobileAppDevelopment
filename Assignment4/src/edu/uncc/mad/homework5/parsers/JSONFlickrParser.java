package edu.uncc.mad.homework5.parsers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uncc.mad.homework5.flickr.Photo;

public class JSONFlickrParser implements FlickrResponseParser {

	JSONObject jsonObject = null;
	
	@Override
	public ArrayList<Photo> parseFlickrResponseString(String flickrResponse) {
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		try {
			jsonObject = new JSONObject(flickrResponse).getJSONObject("photos");
			
			JSONArray jsonArray = jsonObject.getJSONArray("photo");
			
			for(int i = 0 ; i < jsonArray.length(); i ++){
				JSONObject photoElement = (JSONObject)jsonArray.get(i);
				
				String photoTitle = photoElement.getString("title");
				long photoId = photoElement.getLong("id");
				String photoURI = photoElement.getString("url_m");
				int views = photoElement.getInt("views");
				
				photos.add(new Photo(photoId, photoTitle, photoURI, views));
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return photos;
	}

}
