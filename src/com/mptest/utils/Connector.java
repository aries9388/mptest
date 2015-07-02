package com.mptest.utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.mptest.Models.Employee;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

// An adaptor used by other classes to access the database with jdbc

public class Connector {
	private static Connection conn;
	private final static String URL = "jdbc:mysql://localhost:3306/mptest";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "";
	
	// Connect to a SQL database with Connection, URL, USERNAME, and PASSWORD
	
	public static void connect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	};
	
	// Disconnect and close the connection
	
	public static void disconnect()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method to insert a new record into Employee table
	
	public static void addEmployeeRecord(Employee employee)
	{
		Statement statement = null;
		String query = "INSERT INTO "
				+ "Employees (EmployeeNumber, FullName, Location, JobTitle, PhoneNumber, Email, Salary, ManagerNumber) "
				+ "VALUES ("
				+ employee.getEmployeeNumber()+",'"
				+ employee.getFullName()+"','"
				+ employee.getLocation()+"','"
				+ employee.getJobTitle()+"','"
				+ employee.getPhoneNumber()+"','"
				+ employee.getEmail()+"',"
				+ employee.getSalary()+","
				+ ((getEmployeeNumber(employee.getManager())==0) ? "null":getEmployeeNumber(employee.getManager()))
				+");";
		try {
			connect();
			statement = (Statement) conn.createStatement();
			statement.executeUpdate(query);
			disconnect();
			
		} catch (Exception e) {
		}
	}
	
	// Method to get all records from Employee table
	
	public static ArrayList<Employee> getAll()
	{
		ArrayList<Employee> arrayEmployee = new ArrayList<Employee>();
		Statement statement = null;
		String query = "SELECT * FROM Employees;";
		try {
			connect();
			statement = (Statement) conn.createStatement();
			ResultSet resultset = (ResultSet) statement.executeQuery(query);
			while(resultset.next())
			{
				Employee e = new Employee();
				e.setAllFromQuery(resultset);
				arrayEmployee.add(e);
			}
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrayEmployee;
	}
	
	// Method to update a record in Employee table
	
	public static void updateEmployee(Employee employee)
	{
		Statement statement = null;
		int managerid = getEmployeeNumber(employee.getManager());
		String query = "UPDATE Employees SET FullName = '"
		+ employee.getFullName()+"',Location = '"
		+ employee.getLocation()+"',JobTitle = '"
		+ employee.getJobTitle()+"',PhoneNumber = '"
		+ employee.getPhoneNumber()+"',Email = '"
		+ employee.getEmail() + "', StartDate = "
		+ ((StringUtils.isEmpty(employee.getSimpleStartDate())) ? "null," : "'"+ employee.getSimpleStartDate()+"',") + "ManagerNumber = "
		+ ((managerid == 0) ? "null," : managerid +",") +"Salary = "
		+ employee.getSalary()+ " WHERE EmployeeNumber = "
		+ employee.getEmployeeNumber() + ";";
		
		try {
			connect();
			statement = (Statement) conn.createStatement();
			statement.executeUpdate(query);
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method to delete a record in Employee table
	
	public static void deleteEmployee(int id)
	{
		Statement statement = null;
		String query = "DELETE FROM Employees WHERE EmployeeNumber = "+id+";";
		try {
			connect();
			statement = (Statement) conn.createStatement();
			statement.executeUpdate(query);
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method to get EmployeeNumber by FullName
	
	public static int getEmployeeNumber(String name)
	{
		Statement statement = null;
		int id = 0;
		String query = "SELECT * FROM Employees WHERE FullName = '"+name+"';";
		try {
			connect();
			statement = (Statement) conn.createStatement();
			ResultSet resultset = (ResultSet) statement.executeQuery(query);
			if(resultset.next()) id = resultset.getInt("EmployeeNumber");
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	// Method to get FullName by EmployeeNumber
	
	public static String getName(int id)
	{
		Statement statement = null;
		String name = "";
		String query = "SELECT * FROM Employees WHERE EmployeeNumber = "+id+";";
		try {
			connect();
			statement = (Statement) conn.createStatement();
			ResultSet resultset = (ResultSet) statement.executeQuery(query);
			if(resultset.next()) name = resultset.getString("FullName");
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	
	// Method to get Subordinates by EmployeeNumber
	
	public static ArrayList<String> getSubordinates(int id)
	{
		ArrayList<String> subordinates = new ArrayList<String>();
		Statement statement = null;
		String query = "SELECT * FROM Employees WHERE ManagerNumber = "+id+";";
		try {
			connect();
			statement = (Statement) conn.createStatement();
			ResultSet resultset = (ResultSet) statement.executeQuery(query);
			while(resultset.next()){
				Employee e = new Employee();
				e.setAllFromQuery(resultset);
				subordinates.add(e.getFullName());
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subordinates;
	}
	
	// Method to search records by some condition
	
	public static ArrayList<Employee> searchEmployees(String condition)
	{
		ArrayList<Employee> result = new ArrayList<Employee>();
		Statement statement = null;
		String query = "SELECT * FROM Employees WHERE "+condition+";";
		try {
			connect();
			statement = (Statement) conn.createStatement();
			ResultSet resultset = (ResultSet) statement.executeQuery(query);
			while(resultset.next()){
				Employee e = new Employee();
				e.setAllFromQuery(resultset);
				e.setSubordinatesByDB();
				result.add(e);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
