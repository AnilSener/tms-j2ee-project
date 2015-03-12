package controller;


import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.JobSeeker;

import DAO.DataManager;

/**
 * Servlet implementation class GetJobSeekerByJobAppID
 */
@WebServlet("/getJobSeekerByJobAppID")
public class GetJobSeekerByJobAppID extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetJobSeekerByJobAppID() {
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
		int jobAppID=Integer.parseInt(request.getParameter("jobAppID"));
		JobSeeker jobSeeker=dataManager.getJobSeekerByApplication(jobAppID);
		 
		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		oos.writeObject(jobSeeker);
		oos.close();
		oos=null;
	}

}
