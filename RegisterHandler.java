package cp317;

import cp317.DBManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterHandler
 * 
 * @author Paris Ngow
 * @version 1.0
 * @since 2020-12-05
 */
@WebServlet("/RegisterHandler")
public class RegisterHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Implements post method for JSP request.
	 * 
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
		// to output errors to users in jsp
		PrintWriter out = response.getWriter();
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) { // no database connection established
			// outputs alert window to user
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Database connection not established.');");
			out.println("location='register.jsp';");
			out.println("</script>");
		} else {
			// get user submitted information
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			// check if user already in database
			int alreadyRegistered = userExists(conn, email);
			if (alreadyRegistered == -1) { // user not registered
				try {
					// create query
					String sql = "INSERT INTO Users (Email, Password) " + "VALUES (?,?)";
					PreparedStatement stmt = conn.prepareStatement(sql);

					// set variables in query
					stmt.setString(1, email);
					stmt.setString(2, password);

					// execute query
					stmt.executeUpdate();
					conn.close();

					response.sendRedirect("successfulRegister.jsp");
				} catch (SQLException e) { // sql query error
					// outputs alert window to user
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Error writing to database.');");
					out.println("location='register.jsp';");
					out.println("</script>");
				}
			} else { // user already registered
				// outputs alert window to user
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Email already registered.');");
				out.println("location='register.jsp';");
				out.println("</script>");
			}
		}
	}

	/**
	 * Check database to see if user is already registered. Returns an integer > 0
	 * if so, -1 if not.
	 * 
	 * @param conn,  Connection
	 * @param email, String
	 * 
	 * @return int
	 */
	private int userExists(Connection conn, String email) {
		try {
			// create query
			String sql = "SELECT UserID FROM Users WHERE email=?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			// set variables in query
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();

			// check if query returned any results
			if (!rs.next()) {
				// no matching entry found
				return -1;
			} else {
				// return matching user id from database
				return rs.getInt("UserID");
			}

		} catch (SQLException e) { // sql query error
			return -1;
		}
	}

}
