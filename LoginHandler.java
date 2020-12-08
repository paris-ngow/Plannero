package cp317;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginHandler
 * 
 * @author Paris Ngow
 * @version 1.0
 * @since 2020-12-05
 */
@WebServlet("/LoginHandler")
public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Implement post method for JSP request.
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
			out.println("location='login.jsp';");
			out.println("</script>");
		} else {
			// get user submitted information
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			// check if user already in database
			try {
				// create query
				String sql = "SELECT UserID FROM Users WHERE Email=? AND Password=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setString(1, email);
				stmt.setString(2, password);

				// execute query
				ResultSet rs = stmt.executeQuery();

				// move resultset pointer to the first record found with next()
				// check if the user exists
				if (rs.next()) {
					// initialize session
					HttpSession sessionUser = request.getSession();
					sessionUser.setAttribute("userID", rs.getInt("UserID"));

					conn.close();
					// admit user to main page of app
					response.sendRedirect("/Plannero/EventHandler");
				} else { // user doesn't exist
					// outputs alert window to user
					out.println("<script type=\"text/javascript\">");
					out.println("alert('User not registered. Please register before logging in.');");
					out.println("location='login.jsp';");
					out.println("</script>");
				}

			} catch (SQLException e) { // error in sql query
				// outputs alert window to user
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Error writing to database.');");
				out.println("location='login.jsp';");
				out.println("</script>");
			}

		}
	}

}
