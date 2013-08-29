public class User {
	private String firstName, middleInitial, lastName;
	private int age;
	private String city, state;
	
	public User(String line){
		parseUser(line);
	}
	
	private void parseUser(String line){
		//should parse the user by splitting the line string (comma separated)
	}
}
