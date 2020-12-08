package cp317;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CoursePageHandler
 * 
 * @author Paris Ngow
 * @version 1.0
 * @since 2020-12-05
 */
@WebServlet("/CoursePageHandler")
public class CoursePageHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CoursePageHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Implements the get method for JSP requests.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//check if program request has valid parameters to complete it
		if (request.getSession().getAttribute("course") != null || request.getParameter("course") != null) {
			//retrieve course to display
			Course course = getCourse(request, response);
			request.setAttribute("course", course);

			//retrieve events for the course to display
			ArrayList<Event> events = retrieveEvents(request, response);
			request.setAttribute("events", events);

			// forward request to jsp page
			RequestDispatcher rd = request.getRequestDispatcher("course.jsp");
			rd.forward(request, response);
		} else {	//no valid params
			//redirect to course list page
			response.sendRedirect("/Plannero/CourseHandler");
		}
	}

	/**
	 * Implements the post method for JSP requests.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//check if program request wants to add event
			if (request.getParameter("add") != null) {
				addEvent(request, response);
				
			//check if program request wants to delete event
			} else if (request.getParameter("deleteEvent") != null) {
				//check if event list is not empty
				if (!request.getParameter("task").isEmpty()) {
					removeEvent(request, response);
				}
				
				//check if program request wants to delete course
			} else if (request.getParameter("deleteCourse") != null) {
				removeCourse(request, response);
			}
		} catch (NullPointerException e) { // could not find valid request
			//redirect user back to specific course change with no changes
			response.sendRedirect("/Plannero/CoursePageHandler");
		}
	}

	/**
	 * Add event to database for the course.
	 * 
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void addEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {		// no database connection established
			response.sendRedirect("course.jsp");
		} else {
			// get user submitted information
			String eventName = request.getParameter("eName");	// name
			String eventDescription = request.getParameter("eDescription");	// description
			java.sql.Date date = Date.valueOf(request.getParameter("date"));	// date
			int userID = (int) request.getSession().getAttribute("userID");		// user id
			String courseID = ((Course) request.getSession().getAttribute("course")).getCourseID();	//course id
			String eventType = "C";		// event type

			try {
				// create query
				String sql = "INSERT INTO Events (UserID, EventName, EventDescription, EventDate, EventType, CourseID) VALUES (?,?,?,?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setString(2, eventName);
				stmt.setString(3, eventDescription);
				stmt.setDate(4, date);
				stmt.setString(5, eventType);
				stmt.setString(6, courseID);

				// execute query
				stmt.executeUpdate();
				conn.close();
				
				//redirect user to the specific course page update event
				response.sendRedirect("/Plannero/CoursePageHandler");
			} catch (IllegalArgumentException e) {	//invalid argument
				response.sendRedirect("course.jsp");
			} catch (SQLException e) {	//sql query error
				e.printStackTrace();
				response.sendRedirect("course.jsp");
			}
		}
	}

	/**
	 * Remove an event associated with the course from the database.
	 * 
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void removeEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {	// no database connection established
			response.sendRedirect("course.jsp");
		} else {
			// get user submitted information
			int userID = (int) request.getSession().getAttribute("userID");
			int eventID = Integer.parseInt(request.getParameter("event"));

			try {
				// create query
				String sql = "DELETE FROM Events WHERE UserID=? AND EventID=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setInt(2, eventID);

				// execute query
				stmt.executeUpdate();
				conn.close();

				//redirect user to the specific course page update event
				response.sendRedirect("/Plannero/CoursePageHandler");
			} catch (SQLException e) {	//sql query error
				//send user back to course page with no changes
				response.sendRedirect("course.jsp");
			}
		}
	}

	/**
	 * Retrieves a list of events associated with the specific course from the database.
	 * 
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @return ArrayList<Event>
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected ArrayList<Event> retrieveEvents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();
		// create arraylist to store events to return
		ArrayList<Event> events = new ArrayList<>();

		if (conn == null) { // no database connection established
			return null;
		} else {
			// get session user
			int userID = (int) request.getSession().getAttribute("userID");
			String courseID = ((Course) request.getSession().getAttribute("course")).getCourseID();
			String eventType = "C";

			try {
				// create query
				String sql = "SELECT * FROM Events WHERE UserId=? AND EventType=? AND CourseID=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setString(2, eventType);
				stmt.setString(3, courseID);

				// execute query
				ResultSet rs = stmt.executeQuery();

				// collect all results, add to arraylist
				while (rs.next()) {
					//create new instance of event
					Event event = new Event(rs.getInt("EventID"), rs.getString("EventName"), rs.getString("EventType"),
							rs.getString("CourseID"));

					// add event date to event instance if submitted by user
					if (rs.getDate("EventDate") != null) {
						event.setEventDate(rs.getDate("EventDate"));
					}
					
					// add event description to event instance if submitted by user
					if (rs.getString("EventDescription") != null) {
						event.setEventDescription(rs.getString("EventDescription"));
					}

					// add event to arraylist
					events.add(event);
				}

				conn.close();
			} catch (SQLException e) {	//sql query error
				//don't return an arraylist
				return null;
			}

		}

		// return arraylist
		return events;
	}

	/**
	 * Remove the chosen course from the database.
	 * 
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void removeCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {	// no database connection established
			response.sendRedirect("course.jsp");
		} else {
			// get user submitted information
			int userID = (int) request.getSession().getAttribute("userID");

			try {
				Course course = (Course) request.getSession().getAttribute("course");

				// create query
				String sql = "DELETE FROM Courses WHERE UserID=? AND ID=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setInt(2, course.getSqlID());

				// execute query
				stmt.executeUpdate();

				// remove events for the course
				removeEventsForCourse(conn, userID, course.getCourseID());
				conn.close();

				//clear course from system memory
				request.getSession().removeAttribute("course");
				response.sendRedirect("/Plannero/CourseHandler");
			} catch (SQLException e) {	//sql query error
				//send user back to course page with no changes
				response.sendRedirect("course.jsp");
			}
		}
	}

	/**
	 * Removes all the events associated with the deleted course.
	 * 
	 * @param conn, Connection
	 * @param userID, int
	 * @param courseID, String
	 */
	private void removeEventsForCourse(Connection conn, int userID, String courseID) {
		try {
			// create query
			String sql = "DELETE FROM Events WHERE UserID=? AND CourseID=? AND EventType=?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			// set variables in query
			stmt.setInt(1, userID);
			stmt.setString(2, courseID);
			stmt.setString(3, "C");

			// execute query
			stmt.executeUpdate();
		} catch (SQLException e) { // sql query error
			
		}
	}

	/**
	 * Retrieve the course the user selected to view from the database.
	 * 
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @return Course
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected Course getCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();
		// create arraylist to store events to return
		Course course = null;

		if (conn == null) { //no database connection established
			return null;
		} else {
			// get session user
			int userID = (int) request.getSession().getAttribute("userID");

			try {
				try { // handle request from course list page
					int courseID = Integer.parseInt(request.getParameter("course"));

					// create query
					String sql = "SELECT * FROM Courses WHERE UserID=? AND ID=?";
					PreparedStatement stmt = conn.prepareStatement(sql);

					// set variables in query
					stmt.setInt(1, userID);
					stmt.setInt(2, courseID);

					// execute query
					ResultSet rs = stmt.executeQuery();

					// get course
					while (rs.next()) {
						// initialize course information
						course = new Course(rs.getString("CourseID"), rs.getString("CourseName"), rs.getString("CourseLocation"), rs.getString("CourseStart"), rs.getString("CourseEnd"), rs.getString("CourseDays"), rs.getInt("ID"));
						
						//set syllabus to course instance if it exists
						if (rs.getBlob("Syllabus") != null) {
							course.setSyllabus(rs.getBlob("Syllabus"));
						}
						
						// initialize course session attribute to manage page information
						request.getSession().setAttribute("course", course);
					}

					conn.close();
				} catch (NumberFormatException e) { // handle request from updating events
					course = (Course) request.getSession().getAttribute("course");

					// create query
					String sql = "SELECT * FROM Courses WHERE UserID=? AND ID=?";
					PreparedStatement stmt = conn.prepareStatement(sql);

					// set variables in query
					stmt.setInt(1, userID);
					stmt.setInt(2, course.getSqlID());

					// execute query
					ResultSet rs = stmt.executeQuery();

					// get course
					while (rs.next()) {
						course = new Course(rs.getString("CourseID"), rs.getString("CourseName"), rs.getString("CourseLocation"), rs.getString("CourseStart"), rs.getString("CourseEnd"), rs.getString("CourseDays"), rs.getInt("ID"));
						
						//set syllabus to course instance if it exists
						if (rs.getBlob("Syllabus") != null) {
							course.setSyllabus(rs.getBlob("Syllabus"));
						}
						
						// initialize course session attribute to manage page information
						request.getSession().setAttribute("course", course);
					}

					conn.close();
				}
			} catch (SQLException e) {	//sql query error
				//don't return a course
				return null;
			}

			// return course
			return course;
		}
	}

}
