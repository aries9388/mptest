package com.mptest.Controllers;

import java.util.ArrayList;
import java.util.Collections;

import com.mptest.Models.Employee;
import com.mptest.utils.Connector;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

// Employee Action class

public class EmployeeAction extends ActionSupport implements Action, ModelDriven<Employee>{

	private static final long serialVersionUID = 1L;
	ArrayList<Employee> arrayEmployee;
	private Employee employee = new Employee ();
	private String sortByField = "";
	private String order = "";
	private String queryType = "";
	
	// Getters and Setters
	
	public ArrayList<Employee> getArrayEmployee() {
		return arrayEmployee;
	}

	public void setArrayEmployee(ArrayList<Employee> arrayEmployee) {
		this.arrayEmployee = arrayEmployee;
		for(Employee e : arrayEmployee)
		{
			e.setSubordinatesByDB();
		}
	}

	public String getSortByField() {
		return sortByField;
	}

	public void setSortByField(String sortByField) {
		this.sortByField = sortByField;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}


	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	@Override
	public Employee getModel() {
		return employee;
	}
	
	// Overridden method directed to showTable action
	
	public String execute()
	{
		if(arrayEmployee == null) setArrayEmployee(Connector.getAll());
		sortBy();
		return SUCCESS;
	}
		
	// Method directed to EmployeeForm.jsp to add Employee
	
	public String createEmployee()
	{
		try {
			return "create";
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		}
	}
	
	// Method directed to showTable action after successfully creating an employee record
	
	public String addEmployee()
	{	
		try {
			Connector.addEmployeeRecord(employee);
			return SUCCESS;
		} catch (Exception e) {
			return INPUT;
		}
	}
	
	// Method directed to EmployeeForm.jsp to edit employee
	
	public String editEmployee()
	{
		try {
			return "update";
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		}
	}
	
	// Method directed to showTable action after successfully updating an employee record
	
	public String updateEmployee()
	{
		try {
			Connector.updateEmployee(employee);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		}
	}
	
	// Method directed to showTable action after successfully deleting an employee record
	
	public String deleteEmployee()
	{
		try {
			Connector.deleteEmployee(employee.getEmployeeNumber());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		}
		
	}
	
	// Method to sort arrayEmployee using Comparator from Employee class
	
	public void sortBy()
	{
		if(arrayEmployee == null)setArrayEmployee(Connector.getAll());
		switch(getSortByField())
		{
			case "eNumberAsc":
				Collections.sort(arrayEmployee,Employee.Comparators.employeeNumberAscending);
				break;
			case "eNumberDes":
				Collections.sort(arrayEmployee,Employee.Comparators.employeeNumberDescending);
				break;
			case "fNameAsc":
				Collections.sort(arrayEmployee,Employee.Comparators.fullNameAscending);
				break;
			case "fNameDes":
				Collections.sort(arrayEmployee,Employee.Comparators.fullNameDescending);
				break;
			case "locAsc":
				Collections.sort(arrayEmployee,Employee.Comparators.locationAscending);
				break;
			case "locDes":
				Collections.sort(arrayEmployee,Employee.Comparators.locationDescending);
				break;
			case "manAsc":
				Collections.sort(arrayEmployee,Employee.Comparators.managerAscending);
				break;
			case "manDes":
				Collections.sort(arrayEmployee,Employee.Comparators.managerDescending);
				break;
			case "sDateAsc":
				Collections.sort(arrayEmployee,Employee.Comparators.startDateAscending);
				break;
			case "sDateDes":
				Collections.sort(arrayEmployee,Employee.Comparators.startDateDescending);
				break;
			case "jTitleAsc":
				Collections.sort(arrayEmployee,Employee.Comparators.jobTitleAscending);
				break;
			case "jTitleDes":
				Collections.sort(arrayEmployee,Employee.Comparators.jobTitleDescending);
				break;
			case "pNumberAsc":
				Collections.sort(arrayEmployee,Employee.Comparators.phoneNumberAscending);
				break;
			case "pNumberDes":
				Collections.sort(arrayEmployee,Employee.Comparators.phoneNumberDescending);
				break;
			case "emlAsc":
				Collections.sort(arrayEmployee,Employee.Comparators.emailAscending);
				break;
			case "emlDes":
				Collections.sort(arrayEmployee,Employee.Comparators.emailDescending);
				break;
			case "salAsc":
				Collections.sort(arrayEmployee,Employee.Comparators.salaryAscending);
				break;
			case "salDes":
				Collections.sort(arrayEmployee,Employee.Comparators.salaryDescending);
				break;
		}
		
	}
	
	// Method to search employees by some conditions through Employee table
	
	public String searchEmployees()
	{
		String condition = ((employee.getEmployeeNumber()==0)? "":""+"EmployeeNumber = "+employee.getEmployeeNumber())
				+((employee.getFullName()==null)? "":""+"FullName LIKE '%"+employee.getFullName()+"%'")
				+((employee.getJobTitle()==null)? "":""+"JobTitle LIKE '%"+employee.getJobTitle()+"%'")
				+((employee.getLocation()==null)? "":""+"Location LIKE '%"+employee.getLocation()+"%'")
				+((employee.getSalary()==0)? "":""+"Salary "+queryType+" "+employee.getSalary());
		arrayEmployee = Connector.searchEmployees(condition);
		return "search";
	}

	

}
