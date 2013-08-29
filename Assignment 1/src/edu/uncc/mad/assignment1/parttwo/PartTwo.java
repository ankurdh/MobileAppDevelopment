/**
 * 
 * Assignment# 1
 * FileName: PartTwo.java
 * Authors: Ankur Huralikoppi, Vishwas Subramanian
 *
 */

package edu.uncc.mad.assignment1.parttwo;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.uncc.mad.assignment1.FileParser;

public class PartTwo {
	public static void main(String[] args) {
		// Include implementation for Part 2, and create all the required classes.
		Set<User> originalUserSet = null, requiredUsersSet = new HashSet<User>();
		
		try {
			originalUserSet = FileParser.createUserSetFromFile("userList1.txt");
			requiredUsersSet = FileParser.createUserSetFromFile("userList1New.txt");
			
			requiredUsersSet.removeAll(originalUserSet);
			
			System.out.printf("Users present in new list but not first: %d\n", requiredUsersSet.size());
			System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			SortedSet<User> sortedSet = new TreeSet<User>(new SetSorter());
			sortedSet.addAll(requiredUsersSet);
			
			int counter = 0;
			for(User u: sortedSet)
				System.out.printf("%2d. %s\n", ++counter, u);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class SetSorter implements Comparator<User> {

	@Override
	public int compare(User u1, User u2) {
		if (u1.getAge() > u2.getAge()) 
            return -1;
        else if(u1.getAge() <= u2.getAge()) 
        	return 1;
        else 
            return 0;
	}
}
