package model;

import java.io.Serializable;

public class City implements Serializable {

	private int id;
	private String name;
	private int country_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCountryId() {
		return country_id;
	}
	public void setCountryId(int country_id) {
		this.country_id = country_id;
	}
	
	
}
