package cp317;

import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EventHandler
 */
@WebServlet("/EventHandler")
public class EventHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Event> events = retrieveEvents(request, response);
		request.setAttribute("events", events);

		RequestDispatcher rd = request.getRequestDispatcher("course.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getParameter("add") != null) {
				addEvent(request, response);
			}if (request.getParameter("delete") != null) {
				removeEvent(request,response);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			response.sendRedirect("/Plannero/EventHandler");
		}
	}
	
	protected void addEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {
			response.sendRedirect("course.jsp");
		} else {
			// get user submitted information
			String eventName = request.getParameter("eName");
			int userID = (int) request.getSession().getAttribute("userID");
			String courseID = (String) request.getSession().getAttribute("courseID");
			String eventType = "C";

			try {
				// create query
				String sql = "INSERT INTO Events (UserID, EventName, EventType, CourseID) VALUES (?,?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setString(2, eventName);
				stmt.setString(3, eventType);
				stmt.setString(4, courseID);

				// execute query
				stmt.executeUpdate();
				conn.close();

				System.out.println("here1");

				response.sendRedirect("/Plannero/CoursePageHandler");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("here2");
				response.sendRedirect("course.jsp");
			}
		}
	}

	protected void removeEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {
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

				System.out.println("here3");
				response.sendRedirect("/Plannero/CoursePageHandler");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("here4");
				response.sendRedirect("course.jsp");
			}
		}
	}

	protected ArrayList<Event> retrieveEvents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();
		// create arraylist to store events to return
		ArrayList<Event> events = new ArrayList<>();

		if (conn == null) {
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
					Event event = new Event(rs.getInt("EventID"), rs.getString("EventName"), rs.getString("EventType"),
							rs.getString("CourseID"));

					events.add(event);
				}

				conn.close();
				System.out.println("here5");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("here6");
				return null;
			}

		}

		// return arraylist
		return events;
	}

}
