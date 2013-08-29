package edu.uncc.mad.assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import edu.uncc.mad.assignment1.parttwo.User;

public class FileParser {
	
	private static Scanner fileScanner = null;
	
	private static void setupFileScannerForFile(String fileName) throws FileNotFoundException{
		try {
			fileScanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw e;
		}
	}
	
	public static Map<String, Integer> readPacketsFromFile(String fileName) throws FileNotFoundException {
		Map<String, Integer> packetCounterMap = new HashMap<String, Integer>();
		
		setupFileScannerForFile(fileName);
		
		String line;
		Integer count = 0;
		
		try {
			while((line = fileScanner.nextLine()) != null){
				String [] splitLine = line.split(",");
				String protoServicePair = splitLine[1]+ "-" + splitLine[2];
			
				count = packetCounterMap.get(protoServicePair);
				
				packetCounterMap.put(protoServicePair, count == null? 1 : count+1);
						
			}
		} catch (NoSuchElementException e) {
			//don't do anything, could be EOF
		} finally {
			fileScanner.close();
		}
		
		return packetCounterMap;
	}

	public static Set<User> createUserSetFromFile(String fileName) throws FileNotFoundException {
		
		Set<User> userSet = new HashSet<User>();
		
		setupFileScannerForFile(fileName);
		
		String line;
		
		try {
			while((line = fileScanner.nextLine()) != null){
				userSet.add(new User(line));		
			}
		} catch (NoSuchElementException e) {
			//don't do anything, could be EOF
		} finally {
			fileScanner.close();
		}
		
		return userSet;
	}

	public static List<User> createUserListFromFile(String fileName) throws FileNotFoundException {
		List<User> userList = new ArrayList<User>();
		
		setupFileScannerForFile(fileName);
		
		String line;
		
		try {
			while((line = fileScanner.nextLine()) != null){
				userList.add(new User(line));		
			}
		} catch (NoSuchElementException e) {
			//don't do anything, could be EOF
		} finally {
			fileScanner.close();
		}
		
		return userList;
	}
	
	public static Map<String, List<String>> getContinentCountriesListFromFile(String fileName) throws FileNotFoundException{
		Map<String, List<String>> continentCountriesList = new HashMap<String, List<String>>();
		
		setupFileScannerForFile(fileName);
		
		String line;
		
		try {
			while((line = fileScanner.nextLine()) != null){
				String [] splitLine = line.split(",");
				
				List<String> currentContinentList = continentCountriesList.get(splitLine[0]);
				
				if(currentContinentList == null)
					currentContinentList = new ArrayList<String>();
				
				currentContinentList.add(splitLine[1]);
				
				continentCountriesList.put(splitLine[0], currentContinentList);
			}
		} catch (NoSuchElementException e) {
			//don't do anything, could be EOF
		} finally {
			fileScanner.close();
		}
		
		return continentCountriesList;
	}
}
