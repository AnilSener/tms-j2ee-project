package model;

import java.io.Serializable;
import java.util.Date;

public class JobApplication implements Serializable {

	int id;
	int jobID;
	int jobSeekerID;
	Date applicationDate;
	String applicationStatus;
	int matchingSkillCount;
	public JobApplication(){
		
	}
	
	public JobApplication(int jobID,int jobSeekerID,Date applicationDate){
		
		this.jobID=jobID;
		this.jobSeekerID=jobSeekerID;
		this.applicationDate=applicationDate;
		this.applicationStatus="Applied";
		this.matchingSkillCount=0;
	}
	
	public int getJobID() {
		return jobID;
	}
	public void setJobID(int jobID) {
		this.jobID = jobID;
	}
	public int getJobSeekerID() {
		return jobSeekerID;
	}
	public void setJobSeekerID(int jobSeekerID) {
		this.jobSeekerID = jobSeekerID;
	}
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public int getMatchingSkillCount() {
		return matchingSkillCount;
	}

	public void setMatchingSkillCount(int matchingSkillCount) {
		this.matchingSkillCount = matchingSkillCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
		
	
}
