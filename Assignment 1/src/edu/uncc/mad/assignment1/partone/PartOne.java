/**
 * 
 * Assignment# 1
 * FileName: PartOne.java
 * Authors: Ankur Huralikoppi, Vishwas Subramanian
 *
 */

package edu.uncc.mad.assignment1.partone;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import edu.uncc.mad.assignment1.FileParser;

public class PartOne {
	
	public static void main(String[] args) {
		// Include implementation for Part 1, and create all the required classes.
		Map<String, Integer> protoServiceMap = null;
		try {
			protoServiceMap = FileParser.readPacketsFromFile("packets.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		MapSorter mapSorter = new MapSorter();
		mapSorter.setMapToSort(protoServiceMap);
		
		TreeMap<String, Integer> sortedProtoServiceMap = new TreeMap<String, Integer>(mapSorter);
		sortedProtoServiceMap.putAll(protoServiceMap);
		
		//Now the map must be sorted. Print the top 10
		int counter = 0;
		for(String key : sortedProtoServiceMap.keySet()){
			if(++counter > 10)
				break;
			System.out.printf("%2d. %s: %d\n", counter, key, sortedProtoServiceMap.get(key));
		}
	}
}

class MapSorter implements Comparator<String> {

	private Map<String, Integer> mapToSort;
	
	public MapSorter() {
		
	}
	
	public void setMapToSort(Map<String, Integer> mapToSort){
		this.mapToSort = mapToSort;
	}
	
	@Override
	public int compare(String str1, String str2) {
		
		if (mapToSort.get(str1) > mapToSort.get(str2)) {
            return -1;
        } else if(mapToSort.get(str1) == mapToSort.get(str2)) {
        	return 0;
        } else {
            return 1;
        }
	}
}