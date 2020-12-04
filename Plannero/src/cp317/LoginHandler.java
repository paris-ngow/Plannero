package cp317;

import java.io.IOException;
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
			response.sendRedirect("failedLogin.jsp");
		} else {
			//get user submitted information
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			//check if user already in database
			try {
				//create query
				String sql = "SELECT UserID FROM Users WHERE Email=? AND Password=?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				//set variables in query
				stmt.setString(1, email);
				stmt.setString(2, password);
				
				//execute query
				ResultSet rs = stmt.executeQuery();
				
				//move resultset pointer to the first record found with next()
				//check if the user exists
				if (rs.next()) {
					//initialize session 
					HttpSession sessionUser = request.getSession();
					sessionUser.setAttribute("userID", rs.getInt("UserID"));
					
					conn.close();
					response.sendRedirect("/Plannero/TaskHandler");
				} else {
					conn.close();
					response.sendRedirect("failedLogin.jsp");
				} 
				
			} catch (SQLException e){
				e.printStackTrace();
				response.sendRedirect("failedLogin.jsp");
			}	
		
		}
	}

}