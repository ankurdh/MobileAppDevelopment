/**
 * 
 * Assignment# 1
 * FileName: PartOne.java
 * Authors: Ankur Huralikoppi, Vishwas Subramanian
 *
 */

package edu.uncc.mad.assignment1.partone;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PartOne {
	
	public static void main(String[] args) {
		// Include implementation for Part 1, and create all the required classes.

		Scanner fileReader = null;
		
		try {
			fileReader = new Scanner(new File("packets.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = "";
		while((line = fileReader.nextLine()) != null)
			System.out.println(line);		
	}	
}