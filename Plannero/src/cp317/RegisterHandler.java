package cp317;

import cp317.DBManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterHandler
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) {
			response.sendRedirect("failedRegister.jsp");
		} else {
			//get user submitted information
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			int alreadyRegistered = userExists(conn, email, password);
			if (alreadyRegistered == -1) {
				//check if user already in database
				try {
					//create query
					String sql = "INSERT INTO Users (FName, LName, Email, Password) " +
							"SELECT * FROM (SELECT ?,?,?,?) AS tmp WHERE NOT EXISTS " +
							"(SELECT UserID FROM Users WHERE Email=? AND Password=?)";
					PreparedStatement stmt = conn.prepareStatement(sql);
					
					//set variables in query
					stmt.setString(1, fname);
					stmt.setString(2, lname);
					stmt.setString(3, email);
					stmt.setString(4, password);
					stmt.setString(5, email);
					stmt.setString(6, password);
					
					//execute query
					stmt.executeUpdate();
					conn.close();
					
					response.sendRedirect("successfulRegister.jsp");
				} catch (SQLException e){
					e.printStackTrace();
					response.sendRedirect("failedRegister.jsp");
				}
			} else {
				response.sendRedirect("ExistingUserLogin.jsp");
			}
		}
	}
	
	private int userExists(Connection conn, String email, String password) {
		try {
			String sql ="SELECT UserID FROM Users WHERE email=? AND password=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, email);
			stmt.setString(2, password);
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
