import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

import registraform.*;
import database.*;
import sendmail.*;

public class CheckRegistrationForm extends HttpServlet {
	/*

	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get submitted value
		String submitValue = request.getParameter("submitButton");
		if( submitValue!=null && submitValue.equals("Cancel")==true ){
			response.sendRedirect("login.jsp");
			return ;
		}

		//sotre error messages
		Map<String, String> errors = new HashMap<String, String>();

		RegUserInfoTable regUserInfoTable = new RegUserInfoTable();
		boolean isRegistrationValid = true;

		//get input from form
		String firstName = request.getParameter(RegistrationFieldName.firstName);
		String lastName = request.getParameter(RegistrationFieldName.lastName);
		String userName = request.getParameter(RegistrationFieldName.userName);

		String birthDateString = request.getParameter(RegistrationFieldName.birthDate);
		String birthMonthString = request.getParameter(RegistrationFieldName.birthMonth);
		String birthYearString = request.getParameter(RegistrationFieldName.birthYear);

		String bloodGroup = request.getParameter(RegistrationFieldName.bloodGroup);

		String newPassword = request.getParameter(RegistrationFieldName.newPassword);
		String confirmPassword = request.getParameter(RegistrationFieldName.confirmPassword);

		String city = request.getParameter(RegistrationFieldName.city);
		String mobileNumber = request.getParameter(RegistrationFieldName.mobileNumber);
		String emailId = request.getParameter(RegistrationFieldName.emailId);

		try{
			//first name check
			if( firstName.equals("")==true ){
				isRegistrationValid = false;
				errors.put(RegistrationFieldName.firstName, "Please enter your first name.");
			}

			//last name check
			if( lastName.equals("")==true ){
				isRegistrationValid = false;
				errors.put(RegistrationFieldName.lastName, "Please enter your last name.");
			}

			//user name check
			if( userName.equals("")==true ){
				isRegistrationValid = false;
				errors.put(RegistrationFieldName.userName, "Please enter your user name.");
			}
			//UNIQUE user name check
			else if( regUserInfoTable.isUserExist(userName)==true ){
				isRegistrationValid = false;
				errors.put(RegistrationFieldName.userName, "User name exists. Please try a new user name.");
			}


			//birth check
			int birthDate = Integer.valueOf(birthDateString);
			int birthMonth = Integer.valueOf(birthMonthString);
			int birthYear = Integer.valueOf(birthYearString);
			if( (birthMonth==4 || birthMonth==6 || birthMonth==9 || birthMonth==11) && (birthDate>30) ){
				isRegistrationValid = false;
				errors.put("textBirth", "Please enter your correct date of birth.");
			}
			else if( birthMonth==2 && ((birthYear%4==0&&birthDate>29) || (birthYear%4!=0&&birthDate>28)) ){
				isRegistrationValid = false;
				errors.put("textBirth", "Please enter your correct date of birth.");
			}

			//password check
			if( newPassword.equals("")==true || confirmPassword.equals("")==true || newPassword.equals(confirmPassword)==false ){
				isRegistrationValid = false;
				errors.put(RegistrationFieldName.newPassword, "Please enter your new password.");
				errors.put(RegistrationFieldName.confirmPassword, "Please confirm your password.");
			}

			//city check
			if( city.equals("")==true ){
				isRegistrationValid = false;
				errors.put(RegistrationFieldName.city, "Please enter your city name.");
			}

			//mobile number check. it must not be contain any character without digit.
			/* also check via sending sms*/
			if( mobileNumber.equals("")==true || mobileNumber.matches(".*[\\D].*")==true || mobileNumber.length()!=11){
				isRegistrationValid = false;
				errors.put(RegistrationFieldName.mobileNumber, "Please enter your correct mobile number.");
			}

			//email id check
			MailSenderVBB mailSenderVBB = new MailSenderVBB();
			/* also check via sending email*/
			if( emailId.equals("")==true ){
				isRegistrationValid = false;
				errors.put(RegistrationFieldName.emailId, "Please enter your email id.");
			}
			//check email validation.
			else if( isRegistrationValid==true && mailSenderVBB.sendNew(emailId, userName, newPassword)==false ){
				isRegistrationValid = false;
				errors.put(RegistrationFieldName.emailId, "Invalid email id. Please enter your valid email id. <b>OR</b> intrnet connection failure. Check your internet connection.");
			}
		}
		catch(Exception e){
			isRegistrationValid = false;
		}

		//call previous reg-form.jsp page for ERRORanous submission
		if( isRegistrationValid==false ){
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("reg-form.jsp").forward(request, response);
		}
		else{
			//add user to table
			regUserInfoTable.addUser(firstName, lastName, userName, birthDateString, birthMonthString, birthYearString, bloodGroup, newPassword, city, mobileNumber, emailId);
			response.sendRedirect("registration-success.jsp");
			//request.getRequestDispatcher("show-profile.jsp").forward(request, response);
		}
	}

	/*

	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}