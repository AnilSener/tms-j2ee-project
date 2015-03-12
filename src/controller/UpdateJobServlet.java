package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Job;
import DAO.DataManager;

/**
 * Servlet implementation class UpdateJobServlet
 */
@WebServlet(description = "Update Job Servlet", urlPatterns = { "/updateJob" })
public class UpdateJobServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
	
	private DataManager dataManager;    
	private JobPostValidator jobVal=new JobPostValidator();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateJobServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		dataManager=super.getDataManager();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int jobID = Integer.parseInt(request.getParameter("jobID"));
		String jspName=request.getParameter("fileName");
		String status = request.getParameter("status");
		//System.out.println(status);
		String title="";
		int sectorID=0;
		int countryID=0;
		int cityID=0;
		int salary=0;
		int experience=0;
		String description="";
		String currency="";
		if (jspName.equals("jobdetails.jsp"))
		{
		title= request.getParameter("title");
		sectorID = Integer.parseInt(request.getParameter("sectorID"));
		countryID = Integer.parseInt(request.getParameter("countryID"));
		cityID = Integer.parseInt(request.getParameter("cityID"));
		
		try{
		salary = Integer.parseInt(request.getParameter("salary"));
		experience = Integer.parseInt(request.getParameter("experience"));
		}
		catch(NumberFormatException ex){
			strError="Salary and Experience has to be in numberic format";	
			
			valOK=false;
			dbOK=false;
		}
		currency = request.getParameter("currency");
		description = request.getParameter("description");
		}
		
		Job job=dataManager.getJobData(jobID);
		
		if (jspName.equals("jobdetails.jsp"))
		{	
			if(jobVal.notNull(title, description,countryID,cityID)){
				if(jobVal.checkExperience(experience)){
					if(jobVal.checkSize(salary) && jobVal.checkSize(experience)){
							valOK=true;
								job.setTitle(title);
								job.setStatus(status);
								job.setCountryID(countryID);
								job.setCityID(cityID);
								job.setSectorID(sectorID);
								job.setExperience(experience);
								job.setSalary(salary);
								job.setCurrency(currency);
								job.setDescription(description);
								dbOK=dataManager.updateJob(job);
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
		}
		
		if (jspName.equals("employerdashboard.jsp"))
		{
			job.setStatus(status);
			dataManager.updateJobStatus(jobID, status);
		}
		
		RequestDispatcher dispatcher;
		if (!dbOK)
		{	
			request.setAttribute("error", strError);
		}
		else{
			strSuccess="You have successfully updated the job!";
			request.setAttribute("success", strSuccess);
			
		}
		
		dispatcher = request.getRequestDispatcher("/"+jspName);
		dispatcher.forward( request, response);
		
	}

}
