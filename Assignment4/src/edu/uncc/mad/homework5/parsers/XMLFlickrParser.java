package edu.uncc.mad.homework5.parsers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import edu.uncc.mad.homework5.flickr.Photo;

public class XMLFlickrParser implements FlickrResponseParser {

	@Override
	public ArrayList<Photo> parseFlickrResponseString(String flickrResponse) {
		
		XmlPullParser xmlParser = null;
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		try {
			xmlParser = XmlPullParserFactory.newInstance().newPullParser();
			xmlParser.setInput(new StringReader(flickrResponse));
			
			int evtType = xmlParser.getEventType();
			
			while(evtType != XmlPullParser.END_DOCUMENT){
				switch(evtType){
				
				case XmlPullParser.START_TAG:
					
					if(xmlParser.getName().equalsIgnoreCase("photo")){
						
						Long id = Long.parseLong(xmlParser.getAttributeValue(null, "id"));
						int views = Integer.parseInt(xmlParser.getAttributeValue(null, "views"));
						String title = xmlParser.getAttributeValue(null, "title");
						String uri = xmlParser.getAttributeValue(null, "url_m");
						
						photos.add(new Photo(id, title, uri, views));
					}
					
					break;
				}
				
				evtType = xmlParser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return photos;
	}

}
