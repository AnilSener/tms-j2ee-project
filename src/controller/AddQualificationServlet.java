package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.JobSeeker;
import model.Employee;
import DAO.DataManager;

/**
 * Servlet implementation class AddQualificationServlet
 */
@WebServlet("/addqualification")
public class AddQualificationServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager; 
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
	private QualificationValidator qualVal=new QualificationValidator();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddQualificationServlet() {
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
		String schoolName = request.getParameter("schoolName");
		String degree = request.getParameter("degree");
		String gpaStr = request.getParameter("gpa");
		if (qualVal.notNull(schoolName, degree, gpaStr)){
			float gpa=0.0F;
		try{
			gpa=Float.parseFloat(gpaStr);
		}
		catch(NumberFormatException ex){
			valOK=false;
			dbOK=false;
			strError="GPA should be in numerical format!";	
		}
		if (qualVal.checkGPA(gpa)){
			valOK=true;
			JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
			int jobseekerID=0;
			
			if (uname!=null){
				jobseekerID=uname.getId();
			}
			else{
				uname=(JobSeeker)request.getSession().getAttribute("employee");
				jobseekerID=((Employee)uname).getJobseekerId();
				
			}
			dbOK=dataManager.addQualification(schoolName,degree, gpa, jobseekerID);
		}
		else{
			strError="GPA cannot be less than 0 and higher than 100, it cannot have more than 2 digits.";	
			
			valOK=false;
			dbOK=false;
		}
		
		}
	else{
		strError="School Name, Degree and GPA fields cannot be blank.";	
		
		valOK=false;
		dbOK=false;
		}
		
		if (!dbOK)
		{	
			request.setAttribute("error", strError);
		}
		else{
			strSuccess="You have successfully added a qualification!";
			request.setAttribute("success", strSuccess);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/savedetails.jsp");
		dispatcher.forward( request, response);
		
	}

}
