package model;

import java.io.Serializable;
import java.util.*;

public class Job  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int jid;
	private int eid;
	private String title;
	private String companyTitle;
	private List<Skill> jobSkills;
	private String description;
	private java.util.Date postDate;
	private String status;
	private int sectorID;
	private int countryID;
	private int cityID;
	private int experience;
	private int salary;
	private String currency;
	
	public Job(){
		jid=0;
		eid=0;
		sectorID=0;
		countryID=0;
		cityID=0;
		experience=0;
		title="";
		companyTitle="";
		jobSkills= new ArrayList<Skill>();
	}
	public String getCompanyTitle() {
		return companyTitle;
	}

	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}

	public int getJid() {
		return jid;
	}

	public void setJid(int jid) {
		this.jid = jid;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public List<Skill> getJobSkills() {
		return jobSkills;
	}
	public void setJobSkills(List<Skill> jobSkills) {
		this.jobSkills = jobSkills;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(java.util.Date postDate) {
		this.postDate = postDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSectorID() {
		return sectorID;
	}
	public void setSectorID(int sectorID) {
		this.sectorID = sectorID;
	}
	
	public int getCountryID() {
		return countryID;
	}
	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}
	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
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
