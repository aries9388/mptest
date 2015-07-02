package com.mptest.Controllers;

import java.util.ArrayList;

import com.mptest.Models.Employee;
import com.mptest.utils.Connector;

// Json Action class

public class JsonAction {
	private String term;
	private ArrayList<String> jsonResult;
	
	// Getters and Setters
	
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
	
	public ArrayList<String> getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(ArrayList<String> jsonResult) {
		this.jsonResult = jsonResult;
	}
	
	// Method to get Managers whose Name has a specific pattern
	
	public String getManagers()
	{
		ArrayList<String> list = new ArrayList<String>();
		for(Employee e : Connector.searchEmployees("JobTitle = 'Manager'"+((term == null)? "": " AND FullName LIKE '%"+term+"%'")))
		{
			list.add(e.getFullName());
		}
		this.setJsonResult(list);
		return "success";
	}

}
