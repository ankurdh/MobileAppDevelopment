/**
 * 
 * Assignment# 1
 * FileName: PartTwo.java
 * Authors: Ankur Huralikoppi, Vishwas Subramanian
 *
 */

package edu.uncc.mad.assignment1.parttwo;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.uncc.mad.assignment1.FileParser;

public class PartTwo {
	public static void main(String[] args) {
		// Include implementation for Part 2, and create all the required classes.
		Set<User> originalUserSet = null, requiredUsersSet = new HashSet<User>(); 
		List<User> newUserList = null;
		
		try {
			originalUserSet = FileParser.createUserSetFromFile("userList1.txt");
			newUserList = FileParser.createUserListFromFile("userList1New.txt");
			
			System.out.printf("Orig set size: %d, New set size: %d\n", originalUserSet.size(), newUserList.size());
			
			for(User u : newUserList)
				if(!originalUserSet.add(u))
					requiredUsersSet.add(u);
			
			System.out.printf("Orig set size: %d, New set size: %d\n", originalUserSet.size(), requiredUsersSet.size());
//			System.out.println("Common Users: ");
//			for(Object u : requiredUsersSet.toArray()){
//				User user = (User)u;
//				System.out.println(user);
//			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
}
