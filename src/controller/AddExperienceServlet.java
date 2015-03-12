package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employee;
import model.JobSeeker;
import DAO.DataManager;


@WebServlet("/addexperience")
public class AddExperienceServlet extends DBConnectionServlet {
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
	private ExperienceValidator exVal=new ExperienceValidator();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DataManager dataManager;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		dataManager=super.getDataManager();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String companyName = request.getParameter("company");
		String years = request.getParameter("year");
		String position = request.getParameter("position");
		JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
		int jobseekerID=0;
		
		if (uname!=null){
			jobseekerID=uname.getId();
		}
		else{
			uname=(JobSeeker)request.getSession().getAttribute("employee");
			jobseekerID=((Employee)uname).getJobseekerId();
			
		}
		if(exVal.notNull(years, companyName)){
			if(exVal.checkExperience(years)){
				valOK=true;
				dbOK=dataManager.addExperience(companyName, years,jobseekerID,position);
				}
				else{
					strError="Work Experience should be in numeric format. It cannot be a number less than 0 and more than 60.";	
					
					valOK=false;
					dbOK=false;
				}
			} 
			else{
				strError="Experience and Company Name fields cannot be blank.";	
				
				valOK=false;
				dbOK=false;
			}
		

		if (!dbOK)
		{	
			request.setAttribute("error", strError);
		}
		else{
			strSuccess="You have successfully added an experience!";
			request.setAttribute("success", strSuccess);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/savedetails.jsp");
		dispatcher.forward( request, response);
		
	}

}
