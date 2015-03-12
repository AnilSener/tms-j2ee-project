package model;

import java.io.Serializable;
import java.util.List;

public class Profile implements Serializable {

/**
	 * 
	 */
private static final long serialVersionUID = 6722702339638500510L;
private List<Qualification> qualifications=null;
private List<Experience> experiences=null;
private List<Skill> skills=null;


public Profile(){
	
}

public Profile(List<Qualification> qualifications,
	
List<Experience> experiences, List<Skill> skills) {
	super();
	this.qualifications = qualifications;
	this.experiences = experiences;
	this.skills = skills;
	
}
public List<Qualification> getQualifications() {
	return qualifications;
}
public void setQualifications(List<Qualification> qualifications) {
	this.qualifications = qualifications;
}
public List<Experience> getExperiences() {
	return experiences;
}
public void setExperiences(List<Experience> experiences) {
	this.experiences = experiences;
}
public List<Skill> getSkills() {
	return skills;
}
public void setSkills(List<Skill> skills) {
	this.skills = skills;
}



}
