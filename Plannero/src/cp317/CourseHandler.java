package cp317;

import cp317.Course;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CourseHandler
 * 
 * @author Paris Ngow
 * @version 1.0
 * @since 2020-12-05
 */
@WebServlet("/CourseHandler")
public class CourseHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CourseHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Implements get method for JSP requests.
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
		//retrieve courses for the user
		ArrayList<Course> courses = retrieveCourses(request, response);
		request.setAttribute("courses", courses);

		//forward request to jsp to display information
		RequestDispatcher rd = request.getRequestDispatcher("courseList.jsp");
		rd.forward(request, response);
	}

	/**
	 * Implements post method for JSP requests.
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
			//attempt to get data inputted by user to add course
			if (request.getParameter("add") != null) {
				addCourse(request, response);
			}
		} catch (NullPointerException e) {	//no valid response submitted
			//send redirect back to page with no changes
			response.sendRedirect("/Plannero/CourseHandler");
		}
	}

	/**
	 * Adds a course to the database.
	 * 
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void addCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {	// no database connection established
			response.sendRedirect("courseList.jsp");
		} else {
			// get user submitted information
			int userID = (int) request.getSession().getAttribute("userID");		//user of app
			String courseName = request.getParameter("cName");					//course name
			String courseID = request.getParameter("cID");						//course id
			String location = request.getParameter("cLocation");				//location of course
			String startTime = request.getParameter("cStart");					//start time
			String endTime = request.getParameter("cEnd");						// end time
			String daysOfWeek = convertDaysToString(request.getParameterValues("cDays"));	//days of the week course takes place in a formatted string

			//check if course already exists for the user in the database
			boolean alreadyEntered = courseExists(conn, userID, courseName, courseID);
			
			//course does not exist
			if (!alreadyEntered) {
				try {
					// create query
					String sql = "INSERT INTO Courses (UserID, CourseID, CourseName, CourseLocation, CourseStart, CourseEnd, CourseDays) VALUES (?,?,?,?,?,?,?)";
					PreparedStatement stmt = conn.prepareStatement(sql);

					// set variables in query
					stmt.setInt(1, userID);
					stmt.setString(2, courseID);
					stmt.setString(3, courseName);
					stmt.setString(4, location);
					stmt.setString(5, startTime);
					stmt.setString(6, endTime);
					stmt.setString(7, daysOfWeek);

					// execute query
					stmt.executeUpdate();
					conn.close();

					// redirect the user to the course list page
					response.sendRedirect("/Plannero/CourseHandler");
				} catch (SQLException e) {	//error in sql query
					e.printStackTrace();
					response.sendRedirect("courseList.jsp");
				}
			} else {	// course exists
				response.sendRedirect("/Plannero/CourseHandler");
			}
		}
	}

	/**
	 * Returns formatted string of days in the week to store in database.
	 * 
	 * @param daysOfWeek	String[]
	 * 
	 * @return formattedString
	 */
	private String convertDaysToString(String[] daysOfWeek) {
		//initialize formatted string
		String formattedString = "";

		// iterate through the days submitted by the user
		for (int i = 0; i < daysOfWeek.length; i++) {
			// check which day & add to the formatted string 
			switch (daysOfWeek[i]) {
			case "Monday":
				formattedString += "M";
				break;
			case "Tuesday":
				formattedString += "T";
				break;
			case "Wednesday":
				formattedString += "W";
				break;
			case "Thursday":
				formattedString += "R";
				break;
			case "Friday":
				formattedString += "F";
				break;
			default:
				break;
			}
			
			//if the current day is not the last that the user submitted, add a space to the formatted string
			if (i != daysOfWeek.length - 1)
				formattedString += " ";
		}
		
		return formattedString;
	}

	/**
	 * Check if course already exists in the database. Returns true or false.
	 * 
	 * @param conn, Connection
	 * @param userID, int
	 * @param name, String
	 * @param courseID, String
	 * 
	 * @return boolean
	 */
	private boolean courseExists(Connection conn, int userID, String name, String courseID) {
		try {
			// create query
			String sql = "SELECT * FROM Courses WHERE UserID=? AND CourseName=? AND courseID=?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			// set variables in query
			stmt.setInt(1, userID);
			stmt.setString(2, name);
			stmt.setString(3, courseID);
			
			// execute query
			ResultSet rs = stmt.executeQuery();

			//check if query found a record
			if (!rs.next()) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) { // error in sql
			// TODO Auto-generated catch block
			return false;
		}
	}

	/**
	 * Returns a list of courses the user previously submitted.
	 * 
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @return events
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected ArrayList<Course> retrieveCourses(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();
		// create arraylist to store events to return
		ArrayList<Course> courses = new ArrayList<>();

		if (conn == null) {	// no database connection established
			return null;
		} else {
			// get session user
			int userID = (int) request.getSession().getAttribute("userID");

			try {
				// create query
				String sql = "SELECT * FROM Courses WHERE UserID=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);

				// execute query
				ResultSet rs = stmt.executeQuery();

				// collect all results, add to arraylist
				while (rs.next()) {
					//create new instance of event
					Course course = new Course(rs.getString("CourseID"), rs.getString("CourseName"), rs.getString("CourseLocation"), rs.getString("CourseStart"), rs.getString("CourseEnd"), rs.getString("CourseDays"), rs.getInt("ID"));

					//add course to arraylist
					courses.add(course);
				}

				conn.close();

			} catch (SQLException e) { // error in sql query
				//don't return an arraylist
				return null;
			}

		}

		// return arraylist
		return courses;
	}

}
