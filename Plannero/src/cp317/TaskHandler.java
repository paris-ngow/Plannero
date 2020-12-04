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
 * Servlet implementation class TaskHandler
 */
@WebServlet("/TaskHandler")
public class TaskHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<Event> tasks = retrieveTasks(request, response);
		request.setAttribute("tasks", tasks);
		
		ArrayList<Event> events = retrieveEvents(request,response);
		request.setAttribute("events", events);

		RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
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
				addTask(request, response);
			} else if (request.getParameter("delete") != null) {
				if (!request.getParameter("task").isEmpty()) {
				removeTask(request,response);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			response.sendRedirect("/Plannero/TaskHandler");
		}

	}

	protected void addTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {
			response.sendRedirect("main.jsp");
		} else {
			// get user submitted information
			String name = request.getParameter("eName");
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

				response.sendRedirect("/Plannero/TaskHandler");
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendRedirect("main.jsp");
			}
		}
	}

	protected void removeTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {
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

				response.sendRedirect("/Plannero/TaskHandler");
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendRedirect("main.jsp");
			}
		}
	}

	protected ArrayList<Event> retrieveTasks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();
		// create arraylist to store events to return
		ArrayList<Event> tasks = new ArrayList<>();

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
					Event task = new Event(rs.getInt("EventID"), rs.getString("EventName"), rs.getString("EventType"));

					tasks.add(task);
				}

				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		}

		// return arraylist
		return tasks;
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
					Event event = new Event(rs.getInt("EventID"), rs.getString("EventName"), rs.getString("EventType"),
							rs.getString("CourseID"));
					
					if (rs.getDate("EventDate") != null) {
						event.setEventDate(rs.getDate("EventDate"));
					}

					events.add(event);
				}

				conn.close();
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
