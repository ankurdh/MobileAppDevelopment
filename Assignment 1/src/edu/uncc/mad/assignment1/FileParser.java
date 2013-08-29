package edu.uncc.mad.assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileParser {
	
	private static Scanner fileScanner = null;
	
	public static Map<String, Integer> readPacketsFromFile(String fileName) throws FileNotFoundException {
		Map<String, Integer> packetCounterMap = new HashMap<String, Integer>();
		
		try {
			fileScanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw e;
		}
		
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
		}
		
		return packetCounterMap;
	}

}
