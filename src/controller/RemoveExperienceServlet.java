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

/**
 * Servlet implementation class RemoveExperienceServlet
 */
@WebServlet(description = "Remove Job Seeket Experience Servlet", urlPatterns = { "/removeexperience" })
public class RemoveExperienceServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveExperienceServlet() {
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
		int experienceID = Integer.parseInt(request.getParameter("experienceID"));
		JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
		int jobseekerID=0;
		
		if (uname!=null){
			jobseekerID=uname.getId();
		}
		else{
			uname=(JobSeeker)request.getSession().getAttribute("employee");
			jobseekerID=((Employee)uname).getJobseekerId();
			
		}
		dataManager.removeJobSeekerExperience(jobseekerID,experienceID);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/savedetails.jsp");
		dispatcher.forward( request, response);
	}

}
