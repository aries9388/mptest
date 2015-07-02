package com.mptest.Models;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.mptest.utils.Connector;
import com.mysql.jdbc.ResultSet;

// Employee Model

public class Employee{
	private int employeeNumber;
	private String fullName;
	private String location;
	private String manager;
	private Date startDate;
	private String simpleStartDate;
	private String jobTitle;
	private String phoneNumber;
	private String email;
	private int salary;
	private boolean isNull = true;
	private ArrayList<String> subordinates;
	private String subordinatesString;
	
	// ----- Getters and Setters
	
	public int getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		this.simpleStartDate = ((startDate==null) ? "" : new SimpleDateFormat("yyyy-MM-dd").format(startDate));
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public ArrayList<String> getSubordinates() {
		return subordinates;
	}
	
	public void setSubordinates(ArrayList<String> subordinates) {
		this.subordinates = subordinates;
	}
	
	public String getSimpleStartDate() {
		return simpleStartDate;
	}
	
	public boolean checkisNull()
	{
		return isNull;
	}
	
	public String getSubordinatesString() {
		return subordinatesString;
	}
	
	// Method to set properties based on the query results from SQL
	
	public void setAllFromQuery(ResultSet resultset)
	{
		try {
			this.setEmployeeNumber(resultset.getInt("EmployeeNumber"));
			this.setFullName(resultset.getString("FullName"));
			this.setJobTitle(resultset.getString("JobTitle"));
			this.setLocation(resultset.getString("Location"));
			this.setPhoneNumber(resultset.getString("PhoneNumber"));
			this.setEmail(resultset.getString("Email"));
			this.setSalary(resultset.getInt("Salary"));
			this.setStartDate(resultset.getDate("StartDate"));
			this.setManager(Connector.getName(resultset.getInt("ManagerNumber")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Method to set subordinates string from query result
	
	public void setSubordinatesByDB()
	{
		this.subordinates = Connector.getSubordinates(employeeNumber);
		String result = "";
		if(subordinates.size()==0) result = "<p>None</p>";
		else{
			for(String sub : subordinates){
				result+="<p>"+sub+"</p>";
			}
		}
		this.subordinatesString = result;
	}

	// Method to check if an Employee is a manager
	
	public boolean isManager()
	{
		if(jobTitle==null) return false;
		return jobTitle.equals("Manager");
	}
	
	// A static class used for sorting when called by Collection.sort(List,Comparator)
	
	public static class Comparators
	{
		public static Comparator<Employee> employeeNumberAscending = (Employee o1, Employee o2) -> o1.employeeNumber - o2.employeeNumber;
		public static Comparator<Employee> employeeNumberDescending = (Employee o1, Employee o2) -> o2.employeeNumber - o1.employeeNumber;
		public static Comparator<Employee> fullNameAscending = (Employee o1, Employee o2) -> o1.fullName.compareTo(o2.fullName);
		public static Comparator<Employee> fullNameDescending = (Employee o1, Employee o2) -> o2.fullName.compareTo(o1.fullName);
		public static Comparator<Employee> locationAscending = (Employee o1, Employee o2) -> o1.location.compareTo(o2.location);
		public static Comparator<Employee> locationDescending = (Employee o1, Employee o2) -> o2.location.compareTo(o1.location);
		public static Comparator<Employee> managerAscending = (Employee o1, Employee o2) -> o1.manager.compareTo(o2.manager);
		public static Comparator<Employee> managerDescending = (Employee o1, Employee o2) -> o2.manager.compareTo(o1.manager);
		public static Comparator<Employee> startDateAscending = (Employee o1, Employee o2) -> o1.simpleStartDate.compareTo(o2.simpleStartDate);
		public static Comparator<Employee> startDateDescending = (Employee o1, Employee o2) -> o2.simpleStartDate.compareTo(o1.simpleStartDate);
		public static Comparator<Employee> jobTitleAscending = (Employee o1, Employee o2) -> o1.jobTitle.compareTo(o2.jobTitle);
		public static Comparator<Employee> jobTitleDescending = (Employee o1, Employee o2) -> o2.jobTitle.compareTo(o1.jobTitle);
		public static Comparator<Employee> phoneNumberAscending = (Employee o1, Employee o2) -> o1.fullName.compareTo(o2.fullName);
		public static Comparator<Employee> phoneNumberDescending = (Employee o1, Employee o2) -> o2.phoneNumber.compareTo(o1.phoneNumber);
		public static Comparator<Employee> emailAscending = (Employee o1, Employee o2) -> o1.email.compareTo(o2.email);
		public static Comparator<Employee> emailDescending = (Employee o1, Employee o2) -> o2.email.compareTo(o1.email);
		public static Comparator<Employee> salaryAscending = (Employee o1, Employee o2) -> o1.salary - o2.salary;
		public static Comparator<Employee> salaryDescending = (Employee o1, Employee o2) -> o2.salary - o1.salary;
	}
	
}
