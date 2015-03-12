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

import model.Job;
import DAO.DataManager;

/**
 * Servlet implementation class QueryJobServlet
 */
@WebServlet("/queryjob")
public class QueryJobServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryJobServlet() {
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
		String keyword=request.getParameter("keyword");
		int sectorID=0;
		if(request.getParameter("sectorID")!=null)
		sectorID=Integer.parseInt(request.getParameter("sectorID"));
		int countryID=0;
		if(request.getParameter("countryID")!=null)
		countryID=Integer.parseInt(request.getParameter("countryID"));
		int cityID=0;
		if(request.getParameter("cityID")!=null)
		cityID=Integer.parseInt(request.getParameter("cityID"));
		String status=request.getParameter("status");
		String appStatus=request.getParameter("appStatus");
		String experience=request.getParameter("experience");
		String companyname=request.getParameter("companyname");
		int jobSeekerID=0;
		if (appStatus!=null && request.getParameter("jobSeekerID")!=null)
		jobSeekerID=Integer.parseInt(request.getParameter("jobSeekerID"));
		
		List<Job> jobList=dataManager.queryJobs(keyword,sectorID,status,appStatus,jobSeekerID,countryID,cityID,experience,companyname);
		request.setAttribute("jobList", jobList);
		request.setAttribute("status", status);
		if(request.getParameter("sectorID")!=null)
		request.setAttribute("sectorID", sectorID);
		if(request.getParameter("countryID")!=null)
		request.setAttribute("countryID", countryID);
		if(request.getParameter("cityID")!=null)
		request.setAttribute("cityID", cityID);
		request.setAttribute("experience", experience);
		if (appStatus!=null && request.getParameter("jobSeekerID")!=null)
		request.setAttribute("appStatus", appStatus);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/searchjob.jsp");
		dispatcher.forward( request, response);
	}

}
