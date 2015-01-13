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
import java.util.Vector;

import registraform.*;
import database.*;


public class ExecuteLogout extends HttpServlet {

	/*
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String selectValue = request.getParameter("submitButton");
		if( selectValue.equals("Yes")==true ){
			//removing userName when logout because of it will help to prevent unathorized access without login by LoginFilter
			HttpSession session = request.getSession();
			session.removeAttribute("userName");

			response.sendRedirect("login.jsp");
		}
		else{
			response.sendRedirect("home.jsp");
		}

	}

	/*
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}