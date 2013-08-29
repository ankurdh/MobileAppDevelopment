package edu.uncc.mad.assignment1.parttwo;

import java.util.Comparator;

public class User implements Comparator<User>{
	private String firstName, middleInitial, lastName;
	private int age;
	private String city, state;
	
	public User(String line){
		parseUser(line);
	}
	
	private void parseUser(String line){
		//should parse the user by splitting the line string (comma separated)
		String [] userDetails = line.split(",");
		
		firstName = userDetails[0];
		middleInitial = userDetails[1];
		lastName = userDetails[2];
		age = Integer.parseInt(userDetails[3]);
		city = userDetails[4];
		state = userDetails[5];
		
	}

	@Override
	public int compare(User user1, User user2) {

		if(user1.age > user2.age)
			return -1;
		else if(areUsersSame(user1, user2))
			return 0;
		else return 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User){
			User u = (User)obj;
			return areUsersSame(this, u);
		} else return false;
	}
	
	@Override
	public String toString(){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(firstName).append(" ").append(middleInitial).append(". ").append(lastName);
		sb.append("\nAge: ").append(age).append("\n").append(city).append(",").append(state).append("\n");
		
		return sb.toString();
	}
	
	@Override
	public int hashCode(){
		return (age ^ firstName.hashCode() ^ middleInitial.hashCode() ^ lastName.hashCode() ^ city.hashCode() ^ state.hashCode());
	}
	
	public boolean equals(User obj) {
		if(obj instanceof User){
			User u = (User)obj;
			return areUsersSame(this, u);
		} else return false;
	}

	private boolean areUsersSame(User u1, User u2) {

		if( u1.age == u2.age && 
			u1.firstName.equals(u2.firstName) &&
			u1.middleInitial.equals(u2.middleInitial) && 
			u1.lastName.equals(u2.lastName) && 
			u1.city.equals(u2.city) && 
			u1.state.equals(u2.state))
				return true;
		
		return false;
	}
}
