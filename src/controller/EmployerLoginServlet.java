package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Employer;
import DAO.DataManager;

@WebServlet("/employerlogin")
public class EmployerLoginServlet extends DBConnectionServlet{

	
	private static final long serialVersionUID = 1L;
	private String strError;
	private boolean dbOK = false;
	
	
	//Instance of beans
	private Employer employerBean;
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
		 	System.out.println("Log in servlet works");	
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(val.validate(username, password))
		{
			
			employerBean = new Employer();
			employerBean = dataManager.getEmployerData(username, password);
		
			try
			{
				if(!(employerBean.getUsername()==(null)))
					dbOK = true;
				System.out.println("User detected");	
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
		RequestDispatcher dispatcher=null;
		if(dbOK)
		{
			
				session.setAttribute( "employerBean", employerBean);
				
				if(employerBean.isActive())
				dispatcher = request.getRequestDispatcher("/employerhome.jsp");
				else
				dispatcher = request.getRequestDispatcher("/employerinactive.jsp");
				
				dispatcher.forward( request, response);				
			
			
		}
		else
		{
			strError = "Invalid username or password.";
			//Error after DB login, redirect back to index.jsp
			session.setAttribute( "error", strError);
			dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward( request, response);	
		}		
	}		
}
