package model;

import java.io.Serializable;
import java.util.Date;

public class LineItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6260248383527215938L;
	private int id;
	private int timesheetid;
	private int workhours;
	private java.util.Date date;
	private String projectCode;
	private String description;
	private int day;
	
	public LineItem() {
		// TODO Auto-generated constructor stub
		id=0;
		timesheetid=0;
		workhours=0;
		date=new java.util.Date();
		projectCode="";
		description="";
		day=0;
	}

	

	public LineItem(int id, int timesheetid, int workhours, Date date,
			String projectCode, String description, int day) {
		this.id = id;
		this.timesheetid = timesheetid;
		this.workhours = workhours;
		this.date = date;
		this.projectCode = projectCode;
		this.description = description;
		this.day = day;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTimesheetid() {
		return timesheetid;
	}

	public void setTimesheetid(int timesheetid) {
		this.timesheetid = timesheetid;
	}

	public int getWorkhours() {
		return workhours;
	}

	public void setWorkhours(int workhours) {
		this.workhours = workhours;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public int getDay() {
		return day;
	}



	public void setDay(int day) {
		this.day = day;
	}
	
	

}
