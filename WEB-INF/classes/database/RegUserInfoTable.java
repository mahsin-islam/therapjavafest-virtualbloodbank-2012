package database;

import java.util.Vector;

public class RegUserInfoTable{

	final String regUserInfoTableName = "registered_user_info";
	final int totalColumn = 12;

	/*
	create table where User_Id is primary key
	*/
	private boolean makeTable(){
		String query = "CREATE TABLE "+regUserInfoTableName+" ("+
			"User_Id int NOT NULL AUTO_INCREMENT,"+
			"First_Name varchar(255)  NOT NULL,"+
			"Last_Name varchar(255)  NOT NULL,"+
			"User_Name varchar(255)  NOT NULL,"+
			"Birth_Date int  NOT NULL,"+
			"Birth_Month int  NOT NULL,"+
			"Birth_Year int  NOT NULL,"+
			"Blood_Group varchar(10)  NOT NULL,"+
			"Password varchar(255)  NOT NULL,"+
			"City varchar(255)  NOT NULL,"+
			"Mobile_Number varchar(255)  NOT NULL,"+
			"Email_Id varchar(255)  NOT NULL,"+
			"PRIMARY KEY(User_Id));";
		JavaDataBaseConnector jdbc = new JavaDataBaseConnector();
        jdbc.getConnect();
        boolean result = jdbc.createTable(query);
        jdbc.connenctionClose();
        return result;
	}

	/*
	add user to table
	*/
	public boolean addUser(String firstName, String lastName, String userName, String birthDate, String birthMonth,
					String birthYear, String bloodGroup, String password, String city, String mobileNumber, String emailId){
		String query = "INSERT INTO "+regUserInfoTableName+" VALUES ("+
			"null,"+
			"'"+firstName+"',"+
			"'"+lastName+"',"+
			"'"+userName+"',"+
			"'"+birthDate+"',"+
			"'"+birthMonth+"',"+
			"'"+birthYear+"',"+
			"'"+bloodGroup+"',"+
			"'"+password+"',"+
			"'"+city+"',"+
			"'"+mobileNumber+"',"+
			"'"+emailId+"');";
		//create table. if table exists then return false.
		makeTable();
		//add user
		JavaDataBaseConnector jdbc = new JavaDataBaseConnector();
        jdbc.getConnect();
        boolean result = jdbc.createTable(query);
        jdbc.connenctionClose();
        return result;
	}

	/*
	update user to table
	*/
	public boolean updateUser(String prevUserName, String firstName, String lastName, String newUserName, String birthDate,
								String birthMonth, String birthYear, String bloodGroup, String password, String city,
								String mobileNumber, String emailId){
		String query = "UPDATE "+regUserInfoTableName+" SET "+
			"First_Name='"+firstName+"',"+
			"Last_Name='"+lastName+"',"+
			"User_Name='"+newUserName+"',"+
			"Birth_Date='"+birthDate+"',"+
			"Birth_Month='"+birthMonth+"',"+
			"Birth_Year='"+birthYear+"',"+
			"Blood_Group='"+bloodGroup+"',"+
			"Password='"+password+"',"+
			"City='"+city+"',"+
			"Mobile_Number='"+mobileNumber+"',"+
			"Email_Id='"+emailId+"'"+
			"WHERE User_Name='"+prevUserName+"';";
		System.out.println(query);
		//add user
		JavaDataBaseConnector jdbc = new JavaDataBaseConnector();
        jdbc.getConnect();
        boolean result = jdbc.createTable(query);
        jdbc.connenctionClose();
        return result;
	}

	/*
	returns a vector which every element is a vector also (2D vector)
	*/
	public Vector getSomeInfo(String query, int column){
		JavaDataBaseConnector jdbc = new JavaDataBaseConnector();
		jdbc.getConnect();
		Vector result = jdbc.getTableElement(query, column);
		jdbc.connenctionClose();
		return result;
	}

	/*
	returns a vector which every element is a vector also (2D vector)
	*/
	public Vector getAllInfo(){
		String query = "SELECT * from "+regUserInfoTableName+";";
		return getSomeInfo(query, totalColumn);
	}

	/*
	returns a vector of a specific user by user name
	*/
	public Vector getUserInfoUserName(String userName){
		String query = "SELECT * from "+regUserInfoTableName+" where User_Name='"+userName+"';";
		return getSomeInfo(query, totalColumn);
	}

	/*
	returns a vector of some users according to User Name
	*/
	public Vector getModifiedUserInfoUserName(String userName){
		String query = "SELECT User_Name, First_Name, Last_Name, Blood_Group, City, Mobile_Number, Email_Id from "+regUserInfoTableName+" WHERE User_Name='"+userName+"';";
		return getSomeInfo(query, 7);
	}

	/*
	returns a vector of some users according to blood group
	*/
	public Vector getModifiedUserInfoBloodGroup(String bloodGroup){
		String query = "SELECT User_Name, First_Name, Last_Name, Blood_Group, City, Mobile_Number, Email_Id from "+regUserInfoTableName+" WHERE Blood_Group='"+bloodGroup+"';";
		return getSomeInfo(query, 7);
	}

	/*
	returns a vector of some users according to city name
	*/
	public Vector getModifiedUserInfoCity(String city){
		String query = "SELECT User_Name, First_Name, Last_Name, Blood_Group, City, Mobile_Number, Email_Id from "+regUserInfoTableName+" WHERE City='"+city+"';";
		return getSomeInfo(query, 7);
	}

	/*
	returns a vector of some user's mobile no. according to blood group and city
	*/
	public Vector getUserMobileNumber(String userName, String bloodGroup, String city){
		String query = "SELECT Mobile_Number from "+regUserInfoTableName+" WHERE Blood_Group='"+bloodGroup+
						"' AND City='"+city+"' AND User_Name<>'"+userName+"';";
		return getSomeInfo(query, 1);
	}

	/*
	returns a vector of donor's mobile no. according to user name and blood group and city
	*/
	public Vector getDonorMobileNumber(String userName){
		//get user's info
		Vector userInfo = (Vector)(getUserInfoUserName(userName).get(0));
		String bloodGroup = (String)userInfo.get(7);
		String city = (String)userInfo.get(9);
		//System.out.println(bloodGroup+" " +city);
		return getUserMobileNumber(userName, bloodGroup, city);
	}

	/*
	returns existence of a specific user by user name
	*/
	public boolean isUserExist(String userName){
		Vector result = getUserInfoUserName(userName);
		return (!result.isEmpty());
	}

	/*
	returns validity of user according to user name & password
	*/
	public boolean isValidUser(String userName, String password){
		String query = "SELECT * from "+regUserInfoTableName+" where User_Name='"+userName+"' and Password='"+password+"';";
		Vector result = getSomeInfo(query, totalColumn);
		return (!result.isEmpty());
	}

	/*public static void main(String args[]){
		RegUserInfoTable ruit = new RegUserInfoTable();
		System.out.println(ruit.getUserInfoUserName("ratul"));
	}*/
}