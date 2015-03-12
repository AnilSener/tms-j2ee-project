package model;

import java.io.Serializable;

public class Country implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3664316086320349658L;
	private int id;
	private String name;
	private String currency;
	
	
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
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
