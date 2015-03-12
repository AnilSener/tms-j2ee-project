package model;

import java.io.Serializable;

public class Skill implements Serializable{
	
	private int skillID;
	private String name="";
	
	public Skill(){
		
	}
	
	public Skill(String name){
		this.name=name;
	}

	public int getSkillID() {
		return skillID;
	}

	public void setSkillID(int skillID) {
		this.skillID = skillID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Skill other = (Skill) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (skillID != other.skillID)
			return false;
		return true;
	}

	

}
