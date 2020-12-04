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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Course> courses = retrieveCourses(request, response);
		request.setAttribute("courses", courses);

		RequestDispatcher rd = request.getRequestDispatcher("calendar.jsp");
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
				addCourse(request, response);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			response.sendRedirect("/Plannero/CourseHandler");
		}
	}

	protected void addCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {
			response.sendRedirect("calendar.jsp");
		} else {
			// get user submitted information
			int userID = (int) request.getSession().getAttribute("userID");
			String courseName = request.getParameter("cName");
			String courseID = request.getParameter("cID");

			boolean alreadyEntered = courseExists(conn, userID, courseName, courseID);
			if (!alreadyEntered) {
				try {
					// create query
					String sql = "INSERT INTO Courses (UserID, CourseID, CourseName) VALUES (?,?,?)";
					PreparedStatement stmt = conn.prepareStatement(sql);

					// set variables in query
					stmt.setInt(1, userID);
					stmt.setString(2, courseID);
					stmt.setString(3, courseName);

					// execute query
					stmt.executeUpdate();
					conn.close();

					response.sendRedirect("/Plannero/CourseHandler");
				} catch (SQLException e) {
					e.printStackTrace();
					response.sendRedirect("calendar.jsp");
				}
			} else {
				response.sendRedirect("/Plannero/CourseHandler");
			}
		}
	}

	private boolean courseExists(Connection conn, int userID, String name, String courseID) {
		try {
			String sql = "SELECT * FROM Courses WHERE UserID=? AND CourseName=? AND courseID=?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, userID);
			stmt.setString(2, name);
			stmt.setString(3, courseID);
			ResultSet rs = stmt.executeQuery();

			if (!rs.next()) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	protected void removeCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {
			response.sendRedirect("calendar.jsp");
		} else {
			// get user submitted information
			int userID = (int) request.getSession().getAttribute("userID");
			String courseID = request.getParameter("courseID");

			try {
				// create query
				String sql = "DELETE FROM Courses WHERE UserID=? AND CourseID=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setString(2, courseID);

				// execute query
				stmt.executeUpdate();
				conn.close();

				response.sendRedirect("/Plannero/CourseHandler");
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendRedirect("calendar.jsp");
			}
		}
	}

	protected ArrayList<Course> retrieveCourses(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();
		// create arraylist to store events to return
		ArrayList<Course> courses = new ArrayList<>();

		if (conn == null) {
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
					Course course = new Course(rs.getString("CourseID"), rs.getString("CourseName"), rs.getInt("ID"));

					courses.add(course);
				}

				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		}

		// return arraylist
		return courses;
	}

}
