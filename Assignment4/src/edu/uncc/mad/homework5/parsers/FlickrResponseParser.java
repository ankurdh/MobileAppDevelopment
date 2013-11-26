package edu.uncc.mad.homework5.parsers;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.mad.homework5.flickr.Photo;

public interface FlickrResponseParser {

	ArrayList<Photo> parseFlickrResponseString(String flickrResponse);
	
}
