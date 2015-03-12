/**
 * 
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author SYSTEM
 *
 */
public class Employee extends JobSeeker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7790124030355957570L;
	
	private int id;
	private int jobseekerId;
	private int eid;
	private java.util.Date hiringDate;
	private String status="";
	private String companyEmail="";
	private int supervisorId;
	private int salary;
	private String currency="";
	/**
	 * 
	 */
	public Employee() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	

	public Employee(JobSeeker js, int eid, Date hiringDate, String status,
			String companyEmail, int supervisorId,int salary,String currency) {
		
		super(js);
		this.eid = eid;
		this.hiringDate = hiringDate;
		this.status = status;
		this.companyEmail = companyEmail;
		this.supervisorId = supervisorId;
		this.salary=salary;
		this.currency=currency;
		this.jobseekerId=js.getId();
	}



	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public java.util.Date getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(java.util.Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJobseekerId() {
		return jobseekerId;
	}

	public void setJobseekerId() {
		this.jobseekerId = super.getId();
	}
	public void setJobseekerId(int jobseekerId) {
		this.jobseekerId = jobseekerId;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public int getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
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
