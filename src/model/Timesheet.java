package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Timesheet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2951032488984249775L;
	private int id=0;
	private java.util.Date createdDate;
	private int employeeID;
	private int year;
	private int month;
	private int supervisorID;
	private String status;
	private List<LineItem> lineItems=new ArrayList<LineItem>();
	public Timesheet() {
		// TODO Auto-generated constructor stub
		
	}
	
	
	public Timesheet(int id, Date createdDate, int employeeID, int year,
			int month, int supervisorID, String status, List<LineItem> lineItems) {
		this.id = id;
		this.createdDate = createdDate;
		this.employeeID = employeeID;
		this.year = year;
		this.month = month;
		this.supervisorID = supervisorID;
		this.status = status;
		this.lineItems = lineItems;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getSupervisorID() {
		return supervisorID;
	}
	public void setSupervisorID(int supervisorID) {
		this.supervisorID = supervisorID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<LineItem> getLineItems() {
		return lineItems;
	}
	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
