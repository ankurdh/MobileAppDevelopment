/**
 * 
 * Assignment# 1
 * FileName: User.java
 * Authors: Ankur Huralikoppi, Vishwas Subramanian
 *
 */

package edu.uncc.mad.assignment1.parttwo;

public class User {
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
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
		sb.append(" " + age).append(" " + city).append(",").append(state);
		
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
