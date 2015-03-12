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
 * Servlet implementation class RemoveQualificationServlet
 */
@WebServlet(description = "Remove Jobseeker Qualification Servlet", urlPatterns = { "/removequalification" })
public class RemoveQualificationServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveQualificationServlet() {
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
		int qualificationID = Integer.parseInt(request.getParameter("qualificationID"));
		JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
		int jobseekerID=0;
		
		if (uname!=null){
			jobseekerID=uname.getId();
		}
		else{
			uname=(JobSeeker)request.getSession().getAttribute("employee");
			jobseekerID=((Employee)uname).getJobseekerId();
			
		}
		dataManager.removeJobSeekerQualification(jobseekerID,qualificationID);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/savedetails.jsp");
		dispatcher.forward( request, response);
	}

}
