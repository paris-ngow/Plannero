package cp317;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Servlet implementation class SyllabusHandler
 * 
 * @author Paris Ngow
 * @version 1.0
 * @since 2020-12-05
 */
@WebServlet("/SyllabusHandler")
@MultipartConfig(maxFileSize = 10485760) // upload file's size up to 10MB
public class SyllabusHandler extends HttpServlet {
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SyllabusHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Implements get method for JSP request.
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
		// call function to display syllabus on new page
		viewSyllabus(request, response);
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
		// call function to upload syllabus to database
		uploadSyllabus(request, response);
	}

	/**
	 * Displays syllabus for the user on another page
	 * 
	 * @param request, HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void viewSyllabus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get current session course
		Course course = (Course) request.getSession().getAttribute("course");

		try {
			if (course.getSyllabus() != null) { // syllabus uploaded
				// retrieve syllabus file from course instance
				Blob file = course.getSyllabus();
				// format file to bytes to be read by output stream
				byte[] fileData = file.getBytes(1, (int) file.length());

				// send file to output stream
				OutputStream output = response.getOutputStream();
				output.write(fileData);
				output.flush();

			} else { // syllabus not uploaded
				printViewSyllabusError(response);
			}
		} catch (SQLException e) { // sql query error
			// redirect the user to the course page
			response.sendRedirect("/Plannero/CoursePageHandler");
		}
	}

	/**
	 * Print error for view syllabus function.
	 * 
	 * @param response, HttpServletResponse
	 * 
	 * @throws IOException
	 */
	private void printViewSyllabusError(HttpServletResponse response) throws IOException {
		// to print error to user
		PrintWriter out = response.getWriter();

		// outputs alert window to user
		out.println("<script type=\"text/javascript\">");
		out.println("alert('No syllabus file found. Please upload a file.');");
		out.println("location='course.jsp';");
		out.println("close();");
		out.println("</script>");
	}

	/**
	 * Uploads the syllabus to the current course.
	 * 
	 * @param request,  HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void uploadSyllabus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize connection to the database
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) { // no database connection established
			// redirect user back to course page with no changes
			response.sendRedirect("/Plannero/CoursePageHandler");
		} else {
			// get session user
			int userID = (int) request.getSession().getAttribute("userID");
			// get current session course
			Course course = (Course) request.getSession().getAttribute("course");

			// store input stream of file to be uploaded
			InputStream inputStream = null;

			// obtain upload file part of multipart request from from
			Part filePart = request.getPart("syllabus");
			if (filePart != null) {
				inputStream = filePart.getInputStream();
			}

			try {
				// create query
				String sql = "UPDATE Courses SET Syllabus=? WHERE UserID=? AND ID=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				if (inputStream != null) {
					stmt.setBlob(1, inputStream);
				}
				stmt.setInt(2, userID);
				stmt.setInt(3, course.getSqlID());

				// execute query
				stmt.executeUpdate();

				// move resultset pointer to the first record found with next()
				// check if the user exists
				conn.close();

				// retrieve the binary file from the database & set to the course instance
				getSyllabus(request, response);

				// redirect user to view the syllabus
				response.sendRedirect("/Plannero/SyllabusHandler");

			} catch (SQLException e) { // sql error
				// redirect user to the course page with no changes
				response.sendRedirect("/Plannero/CoursePageHandler");
			}

		}
	}

	/**
	 * Retrieves syllabus from the database and displays it to teh user in another
	 * tab.
	 * 
	 * @param request,  HttpServletRequest
	 * @param response, HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getSyllabus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// initialize database connection
		DBManager db = new DBManager();
		Connection conn = db.getConnection();

		if (conn == null) { // no database connection established
			// redirect user to course page with no changes
			response.sendRedirect("/Plannero/CoursePageHandler");
		} else {
			// get session user
			int userID = (int) request.getSession().getAttribute("userID");
			// get current session course
			Course course = (Course) request.getSession().getAttribute("course");

			try {
				// create query
				String sql = "SELECT * FROM Courses WHERE UserID=? AND ID=?";
				PreparedStatement stmt = conn.prepareStatement(sql);

				// set variables in query
				stmt.setInt(1, userID);
				stmt.setInt(2, course.getSqlID());

				// execute query
				ResultSet rs = stmt.executeQuery();

				// move resultset pointer to the first record found with next()
				// check if the query returned anything
				if (rs.next()) { // found syllabus
					// retrieve blob of syllabus from database
					Blob file = rs.getBlob("syllabus");
					// set course instance syllabus to the blob
					course.setSyllabus(file);
					// close database connection
					conn.close();

				} else { // syllabus not found
					conn.close();
					// redirect user to the course page
					response.sendRedirect("/Plannero/CoursePageHandler");
				}

			} catch (SQLException e) { // sql query error
				// redirect user to the course page
				response.sendRedirect("/Plannero/CoursePageHandler");
			}
		}
	}
}
