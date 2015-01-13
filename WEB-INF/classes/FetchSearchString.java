import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.Enumeration;

import registraform.*;
import database.*;


public class FetchSearchString extends HttpServlet {

	/*
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//parameter name
		String paramSearchString = "textSearchString";
		String paramSearchType = "textSearchType";

		//get input from form
		String searchString = request.getParameter(paramSearchString);
		String searchType = request.getParameter(paramSearchType);

		if( searchType==null || searchString==null ){
			response.sendRedirect("home.jsp");
			return ;
		}

		RegUserInfoTable regUserInfoTable = new RegUserInfoTable();

		//store header
		Vector headerInfo = null;
		//sotre search info
		Vector info = null;

		headerInfo = new Vector();
		//storing header
		headerInfo.add("User Name");
		headerInfo.add("First Name");
		headerInfo.add("Last Name");
		headerInfo.add("Blood Group");
		headerInfo.add("City");
		headerInfo.add("Mobile Number");
		headerInfo.add("Email Id");

		//storing info
		if( searchType.equals("userName")==true ){
			info = regUserInfoTable.getModifiedUserInfoUserName(searchString);
		}
		else if( searchType.equals("bloodGroup")==true ){
			info = regUserInfoTable.getModifiedUserInfoBloodGroup(searchString);
		}
		else if( searchType.equals("city")==true ){
			info = regUserInfoTable.getModifiedUserInfoCity(searchString);
		}

		//store to session.
		//HttpSession session = request.getSession();
		request.setAttribute("headerInfo", headerInfo);
		request.setAttribute("info", info);

		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	/*
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}