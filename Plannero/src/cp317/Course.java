package cp317;

import java.sql.Blob;

/**
 * Class for courses
 * @author Paris Ngow
 * @version 1.0
 * @since 2020-12-05
 *
 */
public class Course {
	private String courseID;
	private String name;
	private String location;
	private String startTime;
	private String endTime;
	private String daysOfWeek;
	private Blob syllabus;
	private int sqlID;
	
	/**
	 * Basic constructor for course 
	 *
	 * @param courseID
	 * @param name
	 * @param sqlID
	 */
	public Course(String courseID, String name, int sqlID) {
		this.courseID = courseID;
		this.name = name;
		this.sqlID = sqlID;
	}
	
	/**
	 * Main constructor for course
	 * 
	 * @param courseID
	 * @param name
	 * @param location
	 * @param startTime
	 * @param endTime
	 * @param daysOfWeek
	 * @param sqlID
	 */
	public Course(String courseID, String name, String location, String startTime, String endTime, String daysOfWeek, int sqlID) {
		this.courseID = courseID;
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.daysOfWeek = daysOfWeek;
		this.sqlID = sqlID;
		this.syllabus = null;
	}
	
	/**
	 * Getter for course ID.
	 * @return String
	 */
	public String getCourseID() {
		return courseID;
	}
	
	/**
	 * Getter for course name
	 * @return String
	 */
	public String getCourseName() {
		return name;
	}
	
	/**
	 * Getter for course location
	 * @return String
	 */
	public String getCourseLocation() {
		return location;
	}
	
	/**
	 * Getter for course start time
	 * @return String
	 */
	public String getStartTime() {
		return startTime;
	}
	
	/**
	 * Getter for course end time
	 * @return String
	 */
	public String getEndTime() {
		return endTime;
	}
	
	/**
	 * Getter for days of the week course takes place
	 * @return String
	 */
	public String getDays() {
		return daysOfWeek;
	}
	
	/**
	 * Getter for syllabus file
	 * @return String
	 */
	public Blob getSyllabus() {
		return syllabus;
	}
	
	/**
	 * Getter for SQL primary key ID for query statements
	 * @return int
	 */
	public int getSqlID() {
		return sqlID;
	}
	
	/**
	 * Setter for course ID
	 * @param courseID, String
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	/**
	 * Setter for course name
	 * @param name, String
	 */
	public void setCourseName(String name) {
		this.name = name;
	}

	/**
	 * Setter for course location
	 * @param location, String
	 */
	public void setCourseLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Setter for course start time
	 * @param startTime, String
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * Setter for course end time
	 * @param endTime, String
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * Setter for course days of the week
	 * @param daysOfWeek, String
	 */
	public void setDays(String daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}
	
	/**
	 * Setter for syllabus file
	 * @param syllabus, Blob
	 */
	public void setSyllabus(Blob syllabus) {
		this.syllabus = syllabus;
	}
}
