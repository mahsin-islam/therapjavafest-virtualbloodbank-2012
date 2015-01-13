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


public class FetchUserProfile extends HttpServlet {

	/*
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//checking user exists or not
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("userName");
		//if not exists
		if( userName==null ){
			response.sendRedirect("login.jsp");
			return ;
		}

		//sotre error messages
		Map<String, String> regInfo = new HashMap<String, String>();

		RegUserInfoTable regUserInfoTable = new RegUserInfoTable();

		//get parameter name
		String paramFirstName = RegistrationFieldName.firstName;
		String paramLastName = RegistrationFieldName.lastName;
		String paramUserName = RegistrationFieldName.userName;
		String paramBirthDate = RegistrationFieldName.birthDate;
		String paramBirthMonth = RegistrationFieldName.birthMonth;
		String paramBirthYear = RegistrationFieldName.birthYear;
		String paramBloodGroup = RegistrationFieldName.bloodGroup;
		String paramNewPassword = RegistrationFieldName.newPassword;
		String paramConfirmPassword = RegistrationFieldName.confirmPassword;
		String paramCity = RegistrationFieldName.city;
		String paramMobileNumber = RegistrationFieldName.mobileNumber;
		String paramEmailId = RegistrationFieldName.emailId;

		//retrieve data from database
		Vector infos = regUserInfoTable.getUserInfoUserName(userName);
		Vector info = (Vector)infos.get(0);

		//store in map
		regInfo.put(paramFirstName, (String)info.get(1));
		regInfo.put(paramLastName, (String)info.get(2));
		regInfo.put(paramUserName, (String)info.get(3));
		regInfo.put(paramBirthDate, (String)info.get(4));
		regInfo.put(paramBirthMonth, (String)info.get(5));
		regInfo.put(paramBirthYear, (String)info.get(6));
		regInfo.put(paramBloodGroup, (String)info.get(7));
		regInfo.put(paramNewPassword, (String)info.get(8));
		regInfo.put(paramConfirmPassword, (String)info.get(8));
		regInfo.put(paramCity, (String)info.get(9));
		regInfo.put(paramMobileNumber, (String)info.get(10));
		regInfo.put(paramEmailId, (String)info.get(11));

		//store to session.
		session.setAttribute("regInfo", regInfo);

		response.sendRedirect("profile.jsp");

	}

	/*
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}