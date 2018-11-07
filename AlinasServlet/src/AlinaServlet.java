import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql*;

//Don't forget to add the servlet-api.jar to your classpath. It's in the lib folder in Tomcat.

public class AlinaServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String one, two, three, four, five, six;
		one = request.getParameter("num1");
		two = request.getParameter("num2");
		three = request.getParameter("num3");
		four = request.getParameter("num4");
		five = request.getParameter("num5");
		six = request.getParameter("num6");
		ArrayList<Integer> nums = new ArrayList<Integer>();
		PrintWriter out = response.getWriter(); // print writer
		response.setContentType("text/html");
		String docType = "<!DOCTYPE HTML PUBLIC \" -//W3C//DTD HTML 4.0 " + "Transitional//EN\">\n";
		
		try {
		if (Integer.parseInt(one) < 1 || Integer.parseInt(one) > 47 || Integer.parseInt(two) < 1 || Integer.parseInt(two) > 47 ||
				Integer.parseInt(three) < 1 || Integer.parseInt(three) > 47 ||
				Integer.parseInt(four) < 1 || Integer.parseInt(four) > 47 ||
				Integer.parseInt(five) < 1 || Integer.parseInt(five) > 47 || Integer.parseInt(six) < 1 || Integer.parseInt(six) > 47) {

			out.println(docType + "<HTML>\n " + "<HEAD\n>" + "<TITLE> " + "Incorrect numbers" + "</TITLE></HEAD>\n"
					+ "<BODY>\n" + "Incorrect numbers! Must be between 1-47<br><br>" + 
					"<a href = http://localhost:8080/AlinaLotto/index.html>Back</a>\n" + "</BODY>\n" + "</HTML>");

		}// end if
		
		else if (one.equals("") || two.equals("") || three.equals("") || four.equals("") || five.equals("")
				|| six.equals("")) {
			
			out.println(docType + "<HTML>\n " + "<HEAD\n>" + "<TITLE> " + "Incorrect numbers" + "</TITLE></HEAD>\n"
					+ "<BODY>\n" + "No boxes should be left blank!<br><br>" + 
					"<a href = http://localhost:8080/AlinaLotto/index.html>Back</a>\n" + "</BODY>\n" + "</HTML>");
			
		} // end else if blank
		
		else {

			nums.add(0, Integer.parseInt(one));
			nums.add(1, Integer.parseInt(two));
			nums.add(2, Integer.parseInt(three));
			nums.add(3, Integer.parseInt(four));
			nums.add(4, Integer.parseInt(five));
			nums.add(5, Integer.parseInt(six));
			// sort numbers
			Collections.sort(nums);

			out.println(docType + "<HTML>\n" + "<HEAD>\n" + "<TITLE> " + "Your numbers" + "</TITLE></HEAD>\n"
					+ "<BODY>\n " + "Your numbers : <br>" + "<OL>");

			for (int j = 0; j < nums.size(); j++) {

				out.println("<LI>" + nums.get(j));
			}
			out.println("</OL></BODY></HTML>");
		}
		} catch (NumberFormatException ex) {
			
			out.println(docType + "<HTML>\n " + "<HEAD>\n" + "<TITLE> " + "Incorrect numbers" + "</TITLE></HEAD>\n"
					+ "<BODY>\n" + "You didn't fill all fields or didn't enter a number<br><br>" + 
					"<a href = http://localhost:8080/AlinaLotto/index.html>Back</a>\n" + "</BODY>\n" + "</HTML>");
			
		}
		
	out.close();

	}// end doGet

	// Method to handle POST method request.
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
	      
	      doGet(request, response);
	   }
	
	public static void main (String[]args) {
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lottoNumbers/dt354jdbc");
		
		Statement statement = connection.createStatement();
		
		//
		// insert into table
		statement.executeUpdate("INSERT INTO user(username, password) " + "VALUES ('Alina', '1234');");
		
		
		
		
	}
}