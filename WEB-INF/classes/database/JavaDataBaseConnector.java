package database;

import java.sql.*;
import java.util.*;
import java.util.Properties;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

class JavaDataBaseConnector{

	Connection connect;
	static String rst="";
	static ResultSet result_stat;
	static Statement st_stat;

	/*method for connect with database*/
	boolean getConnect(){

		String userName = null;
		String password = null;
		String url = null;
		String dbName = null;
		String UTF84SQL = null;
		String driver = null;
		Properties configFile = new Properties();

		//normal connection
		try {
			//load config.properties. that stored data for database.
			configFile.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));

			//retrieve data from config.propertis
			userName = configFile.getProperty("userName");
			password = configFile.getProperty("password");
			url = configFile.getProperty("url");
			dbName = configFile.getProperty("dbName");
			UTF84SQL = configFile.getProperty("UTF84SQL");
			driver = configFile.getProperty("driver");

			//connecting to database
			Class.forName (driver).newInstance ();
			connect = DriverManager.getConnection (url+dbName+UTF84SQL, userName, password);

			System.out.println ("Database connection established");
			return true;
		}
		//if database not found
		catch(MySQLSyntaxErrorException msqlse) {
			System.out.println("error 1 "+msqlse);

			//create new database
			try{
				//connect without database
				connect = DriverManager.getConnection(url+""+UTF84SQL, userName, password);
				//create database
				createTable("CREATE DATABASE "+dbName);

				//reconnect with new database
				connenctionClose();
				connect = DriverManager.getConnection (url+dbName+UTF84SQL, userName, password);
				return true;
			}
			catch (Exception e){
				System.out.println("error 2 "+e);
			}
        }
        //other exception
        catch (Exception e){
			System.out.println("error 3 "+e);
		}

		//if not connection executed
		return false;
	}

	/*checking caonnection status*/
	boolean isConnected(){
		try{
			return !connect.isClosed();
		}
		catch(SQLException sqle){return false;}
	}

	/*closing connenction*/
	void connenctionClose(){
		try{
			connect.close();
		}catch(SQLException sqle){};
	}


	/*creating table*/
	public boolean createTable(String sql)
	{
		try{
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("create table ");
			stmt.close();
			return true;
		}
		catch(SQLException e){
			System.out.println(e+"create table error");
			return false;
		}
	}

	/*inserting table*/
	boolean insertTable(String sql){
		return createTable(sql);
	}


	/*return ResultSet of sql for retrieving data*/
	public ResultSet findStatement(String par)
	{
		try{
			st_stat = connect.createStatement();
			result_stat = st_stat.executeQuery(par);
		}
		catch(SQLException e){
			System.out.println(e+"show findResult error");
		}
		return result_stat;
	}

	/*convert ResultSet of data to vector for showing*/
	public Vector getTableElement(String query, int column){
		ResultSet rs = findStatement(query);
		Vector data = new Vector();
		try{
		   while(rs.next()) {
				Vector tmp = new Vector();
				for(int I=1; I<=column; I++)
					tmp.addElement(rs.getString(I));
				data.addElement(tmp);
			}
		}
		catch(Exception ex){}
		return data;
	}

}
