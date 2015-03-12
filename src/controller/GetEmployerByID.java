package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import DAO.DataManager;
import model.Employer;

/**
 * Servlet implementation class GetCityDataByIDServlet
 */
@WebServlet("/getEmployerByID")
public class GetEmployerByID extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEmployerByID() {
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
		int eid=Integer.parseInt(request.getParameter("eid"));
		
		
		Employer employer=(Employer) dataManager.getEmployerData(eid);
		 
	
		    ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		    oos.writeObject(employer);
		    oos.flush();
		    oos.close();
		    oos=null;;
		
	}

}
