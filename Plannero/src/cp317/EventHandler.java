package cp317;

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
	 * Implement get method for JSP requests.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 * @param request,  HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// check if user session is running
		if (request.getSession().getAttribute("userID") != null) {
			// get user events
			ArrayList<Task> tasks = retrieveTasks(request, response);
			request.setAttribute("tasks", tasks);

			// get user tasks
			ArrayList<Event> events = retrieveEvents(request, response);
			request.setAttribute("events", events);

			// forward request to the main page to update page with data
			RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
			rd.forward(request, response);
		} else { // no user session
			response.sendRedirect("login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 * @param request,  HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// check if user wants to add a task
			if (request.getParameter("add") != null) {
				addTask(request, response);

				// check if user wants to delete a task
			} else if (request.getParameter("delete") != null) {
				// check if task list is not empty
				if (!request.getParameter("task").isEmpty()) {
					removeTask(request, response);
				}
			}
		} catch (NullPointerException e) { // invalid request
			// redirect user back to main page with no changes
			response.sendRedirect("/Plannero/EventHandler");
		}

	}

	/**
	 * Add task to the database.
	 * 
	 * @param request,  HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void addTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {
			// redirect user to main page
			response.sendRedirect("main.jsp");
		} else {
			// get user submitted information
			String name = request.getParameter("tName");
			int userID = (int) request.getSession().getAttribute("userID");
			String eventType = "T";

			try {
				// create query
				String sql = "INSERT INTO Events (UserID, EventName, EventType) VALUES (?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setString(2, name);
				stmt.setString(3, eventType);

				// execute query
				stmt.executeUpdate();
				conn.close();

				// update main page
				response.sendRedirect("/Plannero/EventHandler");
			} catch (SQLException e) { // sql query error
				// redirect user to main page
				response.sendRedirect("main.jsp");
			}
		}
	}

	/**
	 * Remove task from database
	 * 
	 * @param request,  HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void removeTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {
			// redirect user back to the main page
			response.sendRedirect("main.jsp");
		} else {
			// get user submitted information
			int userID = (int) request.getSession().getAttribute("userID");
			int taskID = Integer.parseInt(request.getParameter("task"));

			try {
				// create query
				String sql = "DELETE FROM Events WHERE UserID=? AND EventID=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setInt(2, taskID);

				// execute query
				stmt.executeUpdate();
				conn.close();

				// update main page
				response.sendRedirect("/Plannero/EventHandler");
			} catch (SQLException e) { // sql query error
				// redirect user back to the main page
				response.sendRedirect("main.jsp");
			}
		}
	}

	/**
	 * Returns arraylist of user tasks.
	 * 
	 * @param request,  HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @return ArrayList<Task>
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected ArrayList<Task> retrieveTasks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();
		// create arraylist to store events to return
		ArrayList<Task> tasks = new ArrayList<>();

		if (conn == null) {
			return null;
		} else {
			// get session user
			int userID = (int) request.getSession().getAttribute("userID");
			String eventType = "T";

			try {
				// create query
				String sql = "SELECT * FROM Events WHERE UserId=? AND EventType=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setString(2, eventType);

				// execute query
				ResultSet rs = stmt.executeQuery();

				// collect all results, add to arraylist
				while (rs.next()) {
					// create task instance, initialize with data from database
					Task task = new Task(rs.getInt("EventID"), rs.getString("EventName"), rs.getString("EventType"));

					// add task to arraylist
					tasks.add(task);
				}

				conn.close();

			} catch (SQLException e) { // sql query error
				// return null (no tasks)
				return null;
			}

		}

		// return arraylist
		return tasks;
	}

	/**
	 * Returns arraylist of user events.
	 * 
	 * @param request,  HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @return
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

		if (conn == null) {
			return null;
		} else {
			// get session user
			int userID = (int) request.getSession().getAttribute("userID");
			String eventType = "C";

			try {
				// create query
				String sql = "SELECT * FROM Events WHERE UserId=? AND EventType=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setString(2, eventType);

				// execute query
				ResultSet rs = stmt.executeQuery();

				// collect all results, add to arraylist
				while (rs.next()) {
					// create event instance, initialize with data from database
					Event event = new Event(rs.getInt("EventID"), rs.getString("EventName"), rs.getString("EventType"),
							rs.getString("CourseID"));

					// check if date entered, add to instance
					if (rs.getDate("EventDate") != null) {
						event.setEventDate(rs.getDate("EventDate"));
					}

					// check if description entered, add to instance
					if (rs.getString("EventDescription") != null) {
						event.setEventDescription(rs.getString("EventDescription"));
					}

					// add event to arraylist
					events.add(event);
				}

				conn.close();
			} catch (SQLException e) { // sql query error
				// return null (no events)
				return null;
			}

		}

		// return arraylist
		return events;
	}

}
