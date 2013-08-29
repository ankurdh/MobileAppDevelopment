/**
 * 
 * Assignment# 1
 * FileName: PartThree.java
 * Authors: Ankur Huralikoppi, Vishwas Subramanian
 *
 */

package edu.uncc.mad.assignment1.partthree;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.uncc.mad.assignment1.FileParser;

public class PartThree {
	public static void main(String[] args) {
		// Include implementation for Part 3, and create all the required classes.
		Map<String, List<String>> continentCountriesMap = null;
		
		try {
			continentCountriesMap = FileParser.getContinentCountriesListFromFile("countries-info.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		StringBuffer sb = new StringBuffer();		
		
		for(String continent : continentCountriesMap.keySet()){
			List<String> countriesList = continentCountriesMap.get(continent);
			
			Collections.sort(countriesList);
			
			System.out.print(continent + ":\n    ");
			for(String country : countriesList)
				sb.append(country + ",");
			sb.setCharAt(sb.lastIndexOf(","), '.');
			
			System.out.println(sb.toString());
			
			sb.delete(0, sb.length());
			
		}
	}

}
