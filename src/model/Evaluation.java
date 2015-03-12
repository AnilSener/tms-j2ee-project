package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evaluation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4109880969320309492L;
	private int id=0;
	private java.util.Date createdDate;
	private java.util.Date startDate;
	private java.util.Date endDate;
	private int employeeID=0;
	private int supervisorID=0;
	private int reportFileID=0;
	private String notes="";
	private String status="";
	private List<Factor> factors=new ArrayList<Factor>();
	
	public Evaluation() {
		// TODO Auto-generated constructor stub
	}



	public Evaluation(int id, Date createdDate, Date startDate, Date endDate,
			int employeeID, int supervisorID, int reportFileID, String notes,
			String status, List<Factor> factors) {
		this.id = id;
		this.createdDate = createdDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.employeeID = employeeID;
		this.supervisorID = supervisorID;
		this.reportFileID = reportFileID;
		this.notes = notes;
		this.status = status;
		this.factors = factors;
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

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public int getReportFileID() {
		return reportFileID;
	}

	public void setReportFileID(int reportFileID) {
		this.reportFileID = reportFileID;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSupervisorID() {
		return supervisorID;
	}

	public void setSupervisorID(int supervisorID) {
		this.supervisorID = supervisorID;
	}



	public List<Factor> getFactors() {
		return factors;
	}



	public void setFactors(List<Factor> factors) {
		this.factors = factors;
	}
	
	
}
