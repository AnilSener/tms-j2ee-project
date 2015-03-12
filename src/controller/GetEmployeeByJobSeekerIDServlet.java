package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employee;
import DAO.DataManager;

/**
 * Servlet implementation class GetJobApplicationsByID
 */
@WebServlet("/getEmployeeByJobSeekerIDServlet")
public class GetEmployeeByJobSeekerIDServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEmployeeByJobSeekerIDServlet() {
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
		int jobSeekerID=Integer.parseInt(request.getParameter("jobSeekerID"));
		
		
		Employee employee=dataManager.getEmployeeData(jobSeekerID);
		
		    ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		    oos.writeObject(employee);
		    oos.close();
		    oos=null;
	}

}


