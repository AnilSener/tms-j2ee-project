package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

import model.Employer;
import model.Job;
import model.Skill;
import DAO.DataManager;

@WebServlet("/newjob")
public class PostNewJobServlet extends DBConnectionServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
	
	private DataManager dataManager;
	private JobPostValidator jobVal=new JobPostValidator();
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		dataManager=super.getDataManager();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		String title = request.getParameter("title");
		int sectorID = Integer.parseInt(request.getParameter("sectorID"));
		int countryID = Integer.parseInt(request.getParameter("countryID"));
		int cityID = Integer.parseInt(request.getParameter("cityID"));
		
		int salary =0;
		int experience=0;
		try{
			salary = Integer.parseInt(request.getParameter("salary"));
		    experience = Integer.parseInt(request.getParameter("experience"));
		}
		catch(NumberFormatException ex){
			strError="Salary and Experience has to be in numberic format";	
			
			valOK=false;
			dbOK=false;
		}
		String currency = request.getParameter("currency");
		String description = request.getParameter("description");
		java.util.Date postDate=new java.util.Date();
		List<Skill> selectedJobSkillList=(ArrayList<Skill>) request.getSession().getAttribute("selectedJobSkillList");
		
		
		Employer emp =(Employer)request.getSession().getAttribute("employerBean");
		
		if(jobVal.notNull(title, description,countryID,cityID)){
			if(jobVal.checkExperience(experience)){
				if(jobVal.checkSize(salary) && jobVal.checkSize(experience)){
						valOK=true;
						
						Job job = new Job();
						job.setEid(emp.getEid());
						job.setTitle(title);
						job.setSectorID(sectorID);
						job.setCountryID(countryID);
						job.setCityID(cityID);
						job.setSalary(salary);
						job.setCurrency(currency);
						job.setExperience(experience);
						job.setCompanyTitle(emp.getCompanyName());
						job.setDescription(description);
						job.setPostDate(postDate);	
						job.setJobSkills(selectedJobSkillList);
						job.setStatus("Open");
						
						if(emp.getEid()!=0 && job!=null){
							dbOK=dataManager.saveNewJob(job);
							request.getSession().removeAttribute("selectedJobSkillList");
						}
						else
						{
							System.out.println("Job cannot be created");	
						}
			
		}
			else{
				strError="Salary cannot be a number less than 0 and more than "+Integer.MAX_VALUE+".Experience cannot be less than 0.";	
				
				valOK=false;
				dbOK=false;
			}
		}	
			else{
				strError="Work Experience cannot be a number less than 0 and more than 60.";	
				
				valOK=false;
				dbOK=false;
			}
		} 
		else{
			strError="Job Title, Experience, Country, City and Description fields cannot be blank.";	
			
			valOK=false;
			dbOK=false;
		}
		

		
		
		RequestDispatcher dispatcher;
		if (!dbOK)
		{	
			request.setAttribute("error", strError);
			dispatcher = request.getRequestDispatcher("/postnewjob.jsp");
		}
		else{
			strSuccess="You have successfully posted a job!";
			request.setAttribute("success", strSuccess);
			dispatcher = request.getRequestDispatcher("/employerdashboard.jsp");
		}
		
		
		dispatcher.forward( request, response);
		
	}

}
