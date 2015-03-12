package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1250060866739847354L;
	private String code="";
	private int eid=0;
	private String status="";
	private String title="";
	private String description="";
	private List<Assignment> assignments=new ArrayList<Assignment>();
	private java.util.Date postDate;
	
	public Project(Project project) {
		// Copy Constructor
		this.code = project.code;
		this.eid = project.eid;
		this.status = project.status;
		this.title = project.title;
		this.description = project.description;
		this.assignments = project.assignments;
		this.postDate = project.postDate;
		
	}
	public Project() {
		// TODO Auto-generated constructor stub
		
	}
	

	public Project(String code, int eid, String status, String title,
			String description, List<Assignment> assignments, Date postDate) {
		this.code = code;
		this.eid = eid;
		this.status = status;
		this.title = title;
		this.description = description;
		this.assignments = assignments;
		this.postDate = postDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Assignment> getAssignments() {
		return assignments;
	}
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
	public java.util.Date getPostDate() {
		return postDate;
	}
	public void setPostDate(java.util.Date postDate) {
		this.postDate = postDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
