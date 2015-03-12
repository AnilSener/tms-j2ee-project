package model;

import java.io.Serializable;

public class Experience implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2113722007482317398L;
	private int experienceId=0;
	private String company="";
	private String years="";
	private int personid =0;
	private String position="";
	
	public Experience(){
		
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public int getExperienceId() {
		return experienceId;
	}

	public void setExperienceId(int experienceId) {
		this.experienceId = experienceId;
	}

	public int getPersonid() {
		return personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
