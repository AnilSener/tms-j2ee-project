package model;

import java.io.Serializable;
import java.util.Date;

public class Interview implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5440696559612378877L;
	int id;
	Date date;
	int jobId;
	int applicationId;
	String status;
	String location;
	String interviewer;
	String contactEmail;
	int sequenceNo;
	String notes;
	String resultCode;
	int salary;
	String currency;
	
	public Interview(int jobId, int applicationId,Date date, 
			String status, String location, String interviewer,String contactEmail,int sequenceNo, String notes,int salary, String currency) {
		
		
		this.jobId = jobId;
		this.applicationId = applicationId;
		this.date = date;
		this.status = status;
		this.location=location;
		this.interviewer=interviewer;
		this.contactEmail=contactEmail;
		this.sequenceNo=sequenceNo;
		this.notes=notes;
		this.salary=salary;
		this.currency=currency;
	}
	
	public Interview(){
		
	}
	
	public Interview(Interview interview){
		this.jobId = interview.jobId;
		this.applicationId = interview.applicationId;
		this.date = interview.date;
		this.status = interview.status;
		this.location=interview.location;
		this.interviewer=interview.interviewer;
		this.contactEmail=interview.contactEmail;
		this.sequenceNo=interview.sequenceNo;
		this.notes=interview.notes;
		this.resultCode=interview.resultCode;
		this.salary=interview.salary;
		this.currency=interview.currency;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getInterviewer() {
		return interviewer;
	}
	public void setInterviewer(String interviewer) {
		this.interviewer = interviewer;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
