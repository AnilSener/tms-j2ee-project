package controller;


public class InterviewValidator {

	
	public boolean notNull(String location,String interviewer,String contactEmail)
	{
		if(location.equals("") || interviewer.equals("") || contactEmail.equals(""))
		{
			return false;
		}
		return true;
	}
	
	public boolean notNullOnClosure(String location,String interviewer,String contactEmail,String notes,String resultCode)
	{
		if(location.equals("") || interviewer.equals("") || contactEmail.equals("") || notes.equals("") || resultCode.equals(""))
		{
			return false;
		}
		return true;
	}
	
	public boolean isLaterDate(java.util.Date date){
		
		if(date.after(new java.util.Date()))
		return true;
		else
		return false;
	}
	
}
