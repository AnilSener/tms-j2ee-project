package model;

import java.io.Serializable;

public class Factor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137174135054056460L;
	private int id;
	private int evaluationid;
	private String name;
	private int score;
	private String comment;

	public Factor() {
		// TODO Auto-generated constructor stub
		id=0;
		evaluationid=0;
		name="";
		score=0;
		comment="";
	}

	public Factor(int id, int evaluationid, String name, int score,
			String comment) {
		this.id = id;
		this.evaluationid = evaluationid;
		this.name = name;
		this.score = score;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEvaluationid() {
		return evaluationid;
	}

	public void setEvaluationid(int evaluationid) {
		this.evaluationid = evaluationid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
