package controller;

public class QualificationValidator {

	public boolean notNull(String schoolName, String degree, String gpa)
	{
		if(schoolName.equals("") || degree.equals("") || gpa.equals(""))
		{
			return false;
		}
		return true;
	}
	public boolean checkGPA(Float gpa){
		String gpaStr=Float.toString(gpa);
		int noOfDecimalPlaces=gpaStr.substring(gpaStr.indexOf('.')+1).length();
	
		if(gpa<0.0F || gpa>100.0F || gpa>Float.MAX_VALUE || noOfDecimalPlaces>2)
		return false;
			
		return true;
	}
	
}
