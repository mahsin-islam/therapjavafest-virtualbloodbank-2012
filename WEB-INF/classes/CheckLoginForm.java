import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

import registraform.*;
import database.*;


public class CheckLoginForm extends HttpServlet {

	/*
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//sotre error messages
		Map<String, String> errors = new HashMap<String, String>();

		RegUserInfoTable regUserInfoTable = new RegUserInfoTable();
		boolean isRegistrationValid = true;

		String paramUserName = "textLoginUserName";
		String paramPassword = "passwordLoginPassword";

		//get input from form
		String userName = request.getParameter(paramUserName);
		String password = request.getParameter(paramPassword);

		//check user name
		if( regUserInfoTable.isUserExist(userName)==false ){
			isRegistrationValid = false;
			errors.put(paramUserName, "Please insert correct user name.");
		}

		//check user name & password
		if( isRegistrationValid==true && regUserInfoTable.isValidUser(userName, password)==false ){
			isRegistrationValid = false;
			errors.put(paramPassword, "Please insert correct password.");
		}

		//call previous reg-form.jsp page for ERRORanous submission
		if( isRegistrationValid==false ){
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		//SUCCESSFUL login.
		else{
			//store user name to session. NB- user name is not a primary key. but it is unique by manual checking
			HttpSession session = request.getSession();
			//this userName attribute also used for prevent unauthorized access without logged in
			session.setAttribute("userName", userName);

			response.sendRedirect("home.jsp");
			//request.getRequestDispatcher("show-profile.jsp").forward(request, response);
		}
	}

	/*
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}