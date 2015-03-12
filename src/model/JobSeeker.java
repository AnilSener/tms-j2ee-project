package model;

import java.io.Serializable;
import java.util.ArrayList;

public class JobSeeker implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2825630005503577558L;
	protected int id;
	protected String username;
	protected String firstname;
	protected String lastname;
	protected String phone;
	protected String email;
	protected String address;
	protected String password;
	protected String job;
	ArrayList<JobApplication> jobapplications;
	
	public JobSeeker(){
		
		jobapplications=new ArrayList<JobApplication>();
	}
	
	public JobSeeker(JobSeeker js){
		this.id=js.id;
		this.username=js.username;
		this.firstname=js.firstname;
		this.lastname=js.lastname;
		this.phone=js.phone;
		this.email=js.email;
		this.address=js.address;
		this.password=js.password;
		this.job=js.job;
		jobapplications=new ArrayList<JobApplication>();
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public ArrayList<JobApplication> getJobapplications() {
		return jobapplications;
	}

	public void setJobapplications(ArrayList<JobApplication> jobapplications) {
		this.jobapplications = jobapplications;
	}
	
}
