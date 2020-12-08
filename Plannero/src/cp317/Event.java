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
public class Event extends Task implements Comparable<Event> {
	private String description; // description of event
	private Date date; // date of event
	private String courseID; // unique course ID

	/**
	 * Constructor for events.
	 * 
	 * @param eventID,  int
	 * @param name,     String
	 * @param type,     String
	 * @param courseID, String
	 */
	public Event(int eventID, String name, String type, String courseID) {
		super(eventID, name, type);
		// set course id
		this.courseID = courseID;
	}

	/**
	 * Getter for event ID. Returns int.
	 * 
	 * @return eventID
	 */
	public int getEventID() {
		return super.getID();
	}

	/**
	 * Getter for event name. Returns String.
	 * 
	 * @return name
	 */
	public String getEventName() {
		return super.getName();
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
		return super.getType();
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
		super.setID(eventID);
	}

	/**
	 * Setter for event name.
	 * 
	 * @param name, String
	 */
	public void setEventName(String name) {
		super.setName(name);
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
		super.setType(type);
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
	 * Overrides compareTo function of the Comparable interface. Used to order
	 * events by soonest date.
	 * 
	 * @param e, Event
	 * @return int
	 */
	@Override
	public int compareTo(Event e) {
		return this.date.compareTo(e.getEventDate());
	}

}
