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
	
	private String username, num1, num2, num3, num4, num5, num6;
	Connection connection;
	PrintWriter out;
	String results;
	String numbers;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		out = response.getWriter();
		numbers = "";
		
		// get values from input fields
		username = request.getParameter("username");
		num1 = request.getParameter("num1");
		numbers += num1;
		num2 = request.getParameter("num2");
		numbers +=num2;
		num3 = request.getParameter("num3");
		numbers += num3;
		num4 = request.getParameter("num4");
		numbers += num4;
		num5 = request.getParameter("num5");
		numbers += num5;
		num6 = request.getParameter("num6");
		numbers += num6;
		
		if(validateInput()) { // if numbers are in correct format
			
			createUser(); // add user to database
		}
			
		else {
			// otherwise display an error message letting the user know what input is expected
			out.println("<!DOCTYPE html>\n" + 
					"<html>\n" + 
					"<head>\n" + 
					"    <meta charset=\"utf-8\" />\n" + 
					"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" + 
					"    <title>Alina's Lotto Game</title>\n" + 
					"    \n" + 
					"</head>\n" + 
					"<body>\n" + 
					"    <script>\n" + 
					"        alert(\"Incorrect input. REMINDER: Numbers must be between 1-47 and ALL fields must be filled in\");\n" +  
					"    </script>\n");
			
			out.println("</body>\n</html>");
			
		}
		// then display home page again
		out.println("<html>\n" + 
				"<head>\n" + 
				"<title>Alina's Lotto Game</title>\n" + 
				"<style>\n" + 
				"	html {\n" + 
				"	  font-family: sans-serif;\n" + 
				"	}\n" + 
				"	body {\n" + 
				"	  width: 50%;\n" + 
				"	  max-width: 800px;\n" + 
				"	  min-width: 480px;\n" + 
				"	  margin: 0 auto;\n" + 
				"	  background-color: rgb(182, 207, 253);\n" + 
				"	}\n" + 
				"	\n" + 
				"  </style>");
		out.println("<h1>ALINA'S LOTTO GAME</h1>\n" + 
				"</head>\n" + 
				"<body>\n" + 
				"\n" + 
				"<FORM METHOD = GET ACTION = \"playGame\"> <br> \n" + 
				"		Welcome to Alina's Lotto Game! <br>\n" + 
				"		Please intput username<br><br>\n" + 
				"		<br><br>\n" + 
				"		<div>Username: <INPUT TYPE = TEXT NAME = \"username\"> <br><br>\n" + 
				"			Numbers:<br><br> <INPUT TYPE = TEXT NAME = \"num1\"> <br><br>\n" + 
				"							<INPUT TYPE = TEXT NAME = \"num2\"> <br><br>\n" + 
				"							<INPUT TYPE = TEXT NAME = \"num3\"> <br><br>\n" + 
				"							<INPUT TYPE = TEXT NAME = \"num4\"> <br><br>\n" + 
				"							<INPUT TYPE = TEXT NAME = \"num5\"> <br><br>\n" + 
				"							<INPUT TYPE = TEXT NAME = \"num6\"> <br><br>\n" + 
				"			<button type = \"Submit\" onclick = \"createUser\">SUBMIT</button></div><br><br>");
		try {
			out.println("<div>Your previous attempts <br><br> " + displayAttempts() + "</div>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		out.println("</body>\n</html>");
		out.close();

	}// end doGet
	
	public AlinaServlet() {
		
	}
	
	
	public boolean validateInput() { // a method to validate user-input numbers 
		
		try {
			
			if (Integer.parseInt(num1) < 1 || Integer.parseInt(num1) > 47 || Integer.parseInt(num2) < 1 || Integer.parseInt(num2) > 47 ||
					Integer.parseInt(num3) < 1 || Integer.parseInt(num3) > 47 ||
					Integer.parseInt(num4) < 1 || Integer.parseInt(num4) > 47 ||
					Integer.parseInt(num5) < 1 || Integer.parseInt(num5) > 47 || Integer.parseInt(num6) < 1 || Integer.parseInt(num6) > 47) {

				return false;

			}// end if
			
			else if (num1.equals("") || num2.equals("") || num3.equals("") || num4.equals("") || num5.equals("")
					|| num6.equals("")) {
				
				return false;
				
			} // end else if blank
			else {
				
				return true;
			} // end else
			}  catch (NumberFormatException ex) { // end try 
			
			return false;
			
			}
		
	}// end validateInput()
	
	public String displayAttempts() throws SQLException { // fetch the last 5 attempts at the game
		
		results = ""; // reset results each time method is caller (otherwise it will keep appending all input numbers)
		String stmnt = "select numbers from user order by user_id desc limit 5;";
		connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/dt354jdbc?"+"user=root&password=RootRoot");
		Statement select = connection.createStatement(); // SQL statement
		ResultSet rs = select.executeQuery(stmnt); // result set + query execution
		
		while(rs.next()) {
			
		results += "\n" + rs.getString(1) + "\n"; // store numbers in a string

		}
		
		return results;
		
		
	}
	
	public void createUser() { // here the user details + numbers are added to the database
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/dt354jdbc?"+"user=root&password=RootRoot");
			PreparedStatement createUser = connection.prepareStatement("INSERT into user (username, numbers)" +" VALUES (?, ?)");
			createUser.setString(1, username);
			createUser.setString(2, numbers);
			numbers = "";
			
			int rowsUpdated = createUser.executeUpdate();
			createUser.close();
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
}