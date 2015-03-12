package controller;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;

import DAO.DataManager;
import model.Employee;
import model.JobSeeker;
@WebServlet("/login")

public class LoginServlet extends DBConnectionServlet
{
	
	private static final long serialVersionUID = 1L;
	
	
	private String strError;
	private boolean dbOK = false;

	private JobSeeker jobseeker;
	private Employee employee;
	private DataManager dataManager;
	private LoginValidator val = new LoginValidator();
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		dataManager=super.getDataManager();
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		 
		 // Get user data fro submited form
		 		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(val.validate(username, password))
		{
			employee = new Employee();
			employee= dataManager.getEmployeeData(username, password);
			if (employee.getId()==0){
			
			jobseeker = new JobSeeker();
			jobseeker = dataManager.getUserData(username, password);
			System.out.println("User Name:"+jobseeker.getUsername());
			}
			else{
			System.out.println("Employer with User Name:"+employee.getUsername());	
			}
			try
			{
				if((employee.getId()!=0  && !(employee.getUsername()==(null))) || (employee.getId()==0 && !(jobseeker.getUsername()==(null))))
					dbOK = true;
			}
			catch(NullPointerException npe)
			{
				System.out.println("Error on DB return");
				npe.printStackTrace();
				strError = "Invalid username or password.";
				dbOK = false;				
			}
			
		}
		else
		{
			strError = "Invalid username or password.";
			dbOK = false;
		}
				
		HttpSession session = request.getSession(true);
		
		if(dbOK)
		{
			String redirectUrl="";
				if (employee.getId()!=0){
				session.setAttribute( "employee", employee);
				redirectUrl="/employeehome.jsp";
				}
				else{
				session.setAttribute( "jobseeker", jobseeker);
				redirectUrl="/home.jsp";
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher(redirectUrl);
				dispatcher.forward( request, response);				
			
			
		}
		else
		{
			strError = "Invalid username or password.";
			//Error after DB login, redirect back to index.jsp
			session.setAttribute( "error", strError);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward( request, response);	
		}		
	}		
}
