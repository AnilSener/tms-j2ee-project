package model;

import java.io.Serializable;

public class Qualification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7811815163377832790L;
	private String schoolName="";
	private int qualificationId=0;
	private String degree="";
	private String gpa="";
	private int personId =0;
	
	public Qualification(){
		
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGpa() {
		return gpa;
	}

	public void setGpa(String gpa) {
		this.gpa = gpa;
	}

	public int getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(int qualificationId) {
		this.qualificationId = qualificationId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	

}
