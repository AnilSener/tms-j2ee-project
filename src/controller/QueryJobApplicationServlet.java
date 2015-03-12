package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employer;
import model.JobApplication;
import model.Job;
import DAO.DataManager;

/**
 * Servlet implementation class QueryJobApplicationServlet
 */
@WebServlet(description = "Query Job Application Servlet", urlPatterns = { "/queryjobapplication" })
public class QueryJobApplicationServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryJobApplicationServlet() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String title=request.getParameter("title");
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		Employer uname =(Employer)request.getSession().getAttribute("employerBean");
		int eid=uname.getEid();
		
		String status=request.getParameter("status");
		String appStatus=request.getParameter("appStatus");
		String experience=request.getParameter("experience");
		
		List<JobApplication> jobApplicationList=dataManager.queryJobApplications(eid,title,firstName,lastName,status,appStatus,experience);
		request.setAttribute("jobApplicationList", jobApplicationList);
		request.setAttribute("status", status);
		request.setAttribute("experience", experience);
		request.setAttribute("appStatus", appStatus);
		request.setAttribute("title", title);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jobapplicants.jsp");
		dispatcher.forward( request, response);
	}

}
