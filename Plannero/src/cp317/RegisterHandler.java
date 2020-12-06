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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//to output errors to users in jsp
		PrintWriter out = response.getWriter();
		//initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Database connection not established.');");
			out.println("location='register.jsp';");
			out.println("</script>");
		} else {
			//get user submitted information
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			//check if user already in database
			int alreadyRegistered = userExists(conn, email);
			if (alreadyRegistered == -1) {
				try {
					//create query
					String sql = "INSERT INTO Users (Email, Password) " +
							"VALUES (?,?)";
					PreparedStatement stmt = conn.prepareStatement(sql);
					
					//set variables in query
					stmt.setString(1, email);
					stmt.setString(2, password);
					
					//execute query
					stmt.executeUpdate();
					conn.close();
					
					response.sendRedirect("successfulRegister.jsp");
				} catch (SQLException e){
					e.printStackTrace();
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Error writing to database.');");
					out.println("location='register.jsp';");
					out.println("</script>");
				}
			} else {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Email already registered.');");
				out.println("location='register.jsp';");
				out.println("</script>");
			}
		}
	}
	
	/**
	 * Check database to see if user is already registered. Returns an integer > 0 if so, -1 if not.
	 * 
	 * @param conn, Connection
	 * @param email, String
	 * 
	 * @return
	 */
	private int userExists(Connection conn, String email) {
		try {
			String sql ="SELECT UserID FROM Users WHERE email=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			
			if (!rs.next()) {
				return -1;
			} else {
				return rs.getInt("UserID");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

}
