package model;

import java.io.Serializable;

public class Employer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8422592972128578247L;
	private int eid;
	private String username;
	private String password;
	private String phone;
	private String companyName;
	private String email;
	private String address;
	private String description;
	private boolean isActive;
	private java.util.Date createdDate;
	private java.util.Date activationDate;
	
	public int getEid() {
		return this.eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	public java.util.Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(java.util.Date activationDate) {
		this.activationDate = activationDate;
	}
	
	
}
