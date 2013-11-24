package edu.uncc.mad.huduku.pinning;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

	private SharedPreferences pinnedPlacesFile;
	private static SharedPreferenceManager pinnedFileManager;
	private static int pinnedPlacesCount = 0;
	
	private static boolean isSingletonInitialized = false;
	
	private SharedPreferenceManager(Context context, String fileName){
		pinnedPlacesFile = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		
		@SuppressWarnings("unchecked")
		Map<String, Set<String>> pinnedPlaces = (Map<String, Set<String>>) pinnedPlacesFile.getAll();
		
		if(pinnedPlaces == null)
			pinnedPlacesCount = 0;
		else
			pinnedPlacesCount = pinnedPlaces.size();
		
	}

	public static void createInstance(Context context, String fileName){
		pinnedFileManager = new SharedPreferenceManager(context, fileName);
		isSingletonInitialized = true;
	}
	
	public static SharedPreferenceManager getSharedPreferenceManager(){
		if(!isSingletonInitialized)
			return null;
		
		return pinnedFileManager;
	}
		
	public void saveLocation(String name, double lat, double lon){
		
		if(pinnedPlacesCount == 5)
			return;
		
		Set<String> latLonSet = new HashSet<String>();
		latLonSet.add("lat:" + lat);
		latLonSet.add("lon:" + lon);
		
		SharedPreferences.Editor pinnedPlacesFileEditor = pinnedPlacesFile.edit();
		pinnedPlacesFileEditor.putStringSet(name, latLonSet);
		pinnedPlacesFileEditor.commit();
		
		++pinnedPlacesCount;
		
	}
	
	public double [] getLocation(String name){
		double [] latLon = new double[2];
		Set<String> latLonSet = new HashSet<String>();
		
		latLonSet = pinnedPlacesFile.getStringSet(name, latLonSet);
		for(String locVal : latLonSet)
			if(locVal.contains("lat"))
				latLon[0] = Double.parseDouble(locVal.split(":")[1]);
			else 
				latLon[1] = Double.parseDouble(locVal.split(":")[1]);
		
		return latLon;
	}
	
	public void deletePinnedPlace(String name){
		
		SharedPreferences.Editor pinnedPlacesFileEditor = pinnedPlacesFile.edit();
		pinnedPlacesFileEditor.remove(name);
		pinnedPlacesFileEditor.commit();
		
		--pinnedPlacesCount;
	}
	
}
