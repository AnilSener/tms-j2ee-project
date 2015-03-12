package model;

import java.io.Serializable;
import java.util.Date;

public class Assignment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5767485623491187137L;
	private int employeeId=0;
	private String projectCode="";
	private java.util.Date createdDate;
	private java.util.Date startDate;
	private java.util.Date endDate;
	private int goal=0;
	
	public Assignment() {
		// TODO Auto-generated constructor stub
	}

	

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getProjectCode() {
		return projectCode;
	}



	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	
	
	
}
