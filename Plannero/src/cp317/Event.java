package cp317;

import java.util.Date;

/**
 * Class for events
 * 
 * @author Paris Ngow
 * @version 1.0
 * @since 2020-12-05
 *
 */
public class Event implements Comparable<Event> {
	private int eventID;
	private String name;
	private String description;
	private Date date;
	private String type;
	private String courseID;

	/**
	 * Constructor for tasks
	 * 
	 * @param eventID, int
	 * @param name,    String
	 * @param type,    String
	 */
	public Event(int eventID, String name, String type) {
		this.eventID = eventID;
		this.name = name;
		this.type = type;
	}

	/**
	 * Constructor for events
	 * 
	 * @param eventID,  int
	 * @param name,     String
	 * @param type,     String
	 * @param courseID, String
	 */
	public Event(int eventID, String name, String type, String courseID) {
		this.eventID = eventID;
		this.name = name;
		this.type = type;
		this.courseID = courseID;
	}

	/**
	 * Getter for event ID. Returns int.
	 * 
	 * @return eventID
	 */
	public int getEventID() {
		return eventID;
	}

	/**
	 * Getter for event name. Returns String.
	 * 
	 * @return name
	 */
	public String getEventName() {
		return name;
	}

	/**
	 * Getter for event description. Returns String.
	 * 
	 * @return description
	 */
	public String getEventDescription() {
		return description;
	}

	/**
	 * Getter for event date. Returns Date.
	 * 
	 * @return date
	 */
	public Date getEventDate() {
		return date;
	}

	/**
	 * Getter for event type. Returns String.
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Getter for course id. Returns String.
	 * 
	 * @return courseID
	 */
	public String getCourseID() {
		return courseID;
	}

	/**
	 * Setter for event id.
	 * 
	 * @param eventID, int
	 */
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	/**
	 * Setter for event name.
	 * 
	 * @param name, String
	 */
	public void setEventName(String name) {
		this.name = name;
	}

	/**
	 * Setter for event description.
	 * 
	 * @param description, String
	 */
	public void setEventDescription(String description) {
		this.description = description;
	}

	/**
	 * Setter for event date.
	 * 
	 * @param date, Date
	 */
	public void setEventDate(Date date) {
		this.date = date;
	}

	/**
	 * Setter for event type. Either "T" or "C.
	 * 
	 * @param type, String
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Setter for course id.
	 * 
	 * @param courseID, String
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	/**
	 * Overrides compareTo function of the Comparable interface.
	 * 
	 * @param e, Event
	 * @return int
	 */
	@Override
	public int compareTo(Event e) {
		return this.date.compareTo(e.getEventDate());
	}

}
