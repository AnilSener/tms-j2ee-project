package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.JobApplication;
import model.JobSeeker;
import model.Employee;
import DAO.DataManager;


@WebServlet("/applyJob")
public class ApplyJobServlet extends DBConnectionServlet {

	
	private static final long serialVersionUID = 1L;
	
	
	private DataManager dataManager;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		dataManager=super.getDataManager();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String jobIdStr = request.getParameter("jobID");
		int jid = Integer.parseInt(jobIdStr);
		JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
		int jobseekerID=0;
		if (uname!=null){
			jobseekerID=uname.getId();
		}
		else{
			uname=(JobSeeker)request.getSession().getAttribute("employee");
			jobseekerID=((Employee)uname).getJobseekerId();
		}
		JobApplication application=dataManager.applyJob(jobseekerID, jid);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/searchjob.jsp");
		dispatcher.forward( request, response);	
				
	}

}
