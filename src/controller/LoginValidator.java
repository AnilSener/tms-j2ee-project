package controller;

public class LoginValidator {

    public boolean validate(String username, String password)
	{
		if(username.length() <6 || username.length()>20|| (password.length() < 6 || password.length() > 20))
		{
			return false;
		}
		return true;
	}    
    
    public boolean validatePassword(String password)
	{
		if((password.length() < 6 || password.length() > 20))
		{
			return false;
		}
		return true;
	}
    public boolean notNull(String username, String password,String firstname,String lastname,String address,String email,String job,String phone)
	{
		if(username.equals("") || password.equals("")|| firstname.equals("") || lastname.equals("")||job.equals("")|| address.equals("") || email.equals("")|| phone.equals(""))
		{
			return false;
		}
		return true;
	}
    
    public boolean notNullEmployer(String username, String password,String companyname,String email,String phone, String address, String description)
  	{
  		if(username.equals("") || password.equals("") || companyname.equals("") || email.equals("") || phone.equals("") || address.equals("") || description.equals(""))
  		{
  			return false;
  		}
  		return true;
  	}
    
    public boolean lessThan30Chars(String text)
	{
		if(text.length()>30)
		{
			return false;
		}
		return true;
	}
    public boolean lessThan40Chars(String text)
	{
		if(text.length()>40)
		{
			return false;
		}
		return true;
	}
    public boolean lessThan100Chars(String text)
	{
		if(text.length()>100)
		{
			return false;
		}
		return true;
	}
    public boolean lessThan255Chars(String text)
   	{
   		if(text.length()>255)
   		{
   			return false;
   		}
   		return true;
   	}
}
