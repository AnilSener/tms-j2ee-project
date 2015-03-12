package controller;

public class ExperienceValidator {
	 public boolean notNull(String experience, String company)
		{
			if(experience.equals("") || company.equals(""))
			{
				return false;
			}
			return true;
		}
	 
	 public boolean checkExperience(String experience){
		 int num=0;
		 try{
		 num=Integer.parseInt(experience);
		}
		catch(NumberFormatException ex){
			
			return false;
		}
				
		 	if (num<0 || num>60)
			 return false;
		 	else
		 return true;
	 }
}
