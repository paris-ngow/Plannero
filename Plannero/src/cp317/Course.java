package cp317;

public class Course {
	private String courseID;
	private String name;
	private int sqlID;
	
	//constructor for tasks
	public Course(String courseID, String name, int sqlID) {
		this.courseID = courseID;
		this.name = name;
		this.sqlID = sqlID;
	}
	
	public String getCourseID() {
		return courseID;
	}
	
	public String getCourseName() {
		return name;
	}
	
	public int getSqlID() {
		return sqlID;
	}
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public void setEventName(String name) {
		this.name = name;
	}
}
