package cp317;

import java.util.Date;

public class Event implements Comparable<Event>{
	private int eventID;
	private String name;
	private Date date;
	private String type;
	private String courseID;
	
	//constructor for tasks
	public Event(int eventID, String name, String type) {
		this.eventID = eventID;
		this.name = name;
		this.type = type;
	}
	
	//constructor for course events
		public Event(int eventID, String name, String type, String courseID) {
			this.eventID = eventID;
			this.name = name;
			this.type = type;
			this.courseID = courseID;
		}
	
	public int getEventID() {
		return eventID;
	}
	
	public String getEventName() {
		return name;
	}
	
	public Date getEventDate() {
		return date;
	}
	
	public String getType() {
		return type;
	}
	
	public String getCourseID() {
		return courseID;
	}
	
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	
	public void setEventName(String name) {
		this.name = name;
	}
	
	public void setEventDate(Date date) {
		this.date = date;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	@Override
	public int compareTo(Event e) {
		return this.date.compareTo(e.getEventDate());
	}
	
}
