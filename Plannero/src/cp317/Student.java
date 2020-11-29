package cp317;

public class Student {
	String fName;
	String lName;
	String email;
	String password;
	int userID;
	
	public Student(String fName, String lName, String email, String password) {
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
}
