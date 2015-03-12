package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employer;
import DAO.DataManager;

/**
 * Servlet implementation class QueryJobServlet
 */
@WebServlet("/queryEmployerServlet")
public class QueryEmployerServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryEmployerServlet() {
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
		
		
		String userName=request.getParameter("userName");
		String companyName=request.getParameter("companyname");
		String action=request.getParameter("action");
		boolean isActive=true;
		if(request.getParameter("isActive")!=null)
			isActive=Boolean.parseBoolean(request.getParameter("isActive"));
		else
			isActive=false;
		
		System.out.println(isActive);
		
		List<Employer> employerList=dataManager.queryEmployers(isActive, companyName, userName);
		if(action.equals("list")){
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			oos.writeObject(employerList);
			oos.flush();
			oos.close();
			oos=null;
		}
		else{
		
		request.setAttribute("isActive", isActive);
		request.setAttribute("employerList", employerList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/employersubscriptions.jsp");
		dispatcher.forward( request, response);
		}
	}

}

