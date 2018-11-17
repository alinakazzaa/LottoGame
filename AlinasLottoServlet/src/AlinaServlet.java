import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;


public class AlinaServlet extends HttpServlet {
	
	private String username, password, num1, num2, num3, num4, num5, num6;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// initialize paratemers
		username = request.getParameter("username");
		password = request.getParameter("password");
		num1 = request.getParameter("num1");
		num2 = request.getParameter("num2");
		num3 = request.getParameter("num3");
		num4 = request.getParameter("num4");
		num5 = request.getParameter("num5");
		num6 = request.getParameter("num6");
		
		
		PrintWriter out = response.getWriter(); // print writer
		response.setContentType("text/html");
		/*String docType = "<!DOCTYPE HTML PUBLIC \" -//W3C//DTD HTML 4.0 " + "Transitional//EN\">\n";

		out.println(docType + "<HTML>\n" + "<HEAD>\n" + "<TITLE> " + "Your numbers" + "</TITLE></HEAD>\n"
				+ "<BODY>\n " + "Your details: <br>");
			out.println("<p>" + username + " " + password + "</p>");
		out.println("</BODY></HTML>");*/
		
		// if statement to check if user already exists in database, and if so, notify user & log them in
		createUser();
		response.setContentType("text/html"); 
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", "inputNumbers.html"); // redirect
		storeNumbers();
		
		out.close();

	}// end doGet
	
	public AlinaServlet() {
		
	}
	
	public void createUser() {
		
		
		System.out.println(username + "something" + password);
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/dt354jdbc?"+"user=root&password=RootRoot");
			PreparedStatement createUser = connection.prepareStatement("INSERT into user (username, password)" +" VALUES (?, ?)");
			
			createUser.setString(1, username);
			createUser.setString(2, password);
			
			int rowsUpdated = createUser.executeUpdate();
			createUser.close();
			connection.close();
			
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		public void storeNumbers() {
		
			
			
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/dt354jdbc?"+"user=root&password=RootRoot");
				PreparedStatement createNumbers = connection.prepareStatement("INSERT into userNumbers (user, num1, num2, num3, num4, num5, num6)" +" VALUES (?, ?, ?, ?, ?, ?, ?)");
				
				createNumbers.setString(1, username);
				createNumbers.setString(2, num1);
				createNumbers.setString(3, num2);
				createNumbers.setString(4, num3);
				createNumbers.setString(5, num4);
				createNumbers.setString(6, num5);
				createNumbers.setString(7, num6);
				
				int rowsUpdated = createNumbers.executeUpdate();
				createNumbers.close();
				connection.close();
				
			} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
		
		      doGet(request, response);
		   }
	
	public static void main (String[]args) throws Exception {
		
		
		AlinaServlet as = new AlinaServlet();
		as.storeNumbers();
	}
	
}