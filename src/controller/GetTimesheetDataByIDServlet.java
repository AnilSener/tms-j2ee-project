package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DataManager;
import model.Timesheet;

/**
 * Servlet implementation class GetJobDataByID
 */
@WebServlet("/getTimesheetDataByIDServlet")
public class GetTimesheetDataByIDServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTimesheetDataByIDServlet() {
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
		int timesheetID=Integer.parseInt(request.getParameter("timesheetid"));
		
		Timesheet timesheet=dataManager.getTimesheetData(timesheetID);
		 
		
		    ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		    oos.writeObject(timesheet);
		    oos.flush();
		    oos.close();
		    oos=null;
		    
	}

}




