package controller;

public class JobPostValidator {

	 public boolean notNull(String title, String description, int countryID,int cityID)
		{
			if(title.equals("") || description.equals("") || countryID==0 || cityID==0)
			{
				return false;
			}
			return true;
		}
	 
	 public boolean checkExperience(int experience){
		 
		 
		 	if (experience<0 && experience>60)
			 return false;
		 	else
		 return true;
	 }
	 
	 public boolean checkSize(int num){
		 if (num<0 || num>Integer.MAX_VALUE){
			
			 return false;
		 }
		return true;
	 }
	 
}
