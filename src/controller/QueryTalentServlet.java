package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employer;
import model.JobSeeker;
import DAO.DataManager;

/**
 * Servlet implementation class QueryJobServlet
 */
@WebServlet("/queryTalentServlet")
public class QueryTalentServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryTalentServlet() {
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
		
		
		int eid=0;
		if(request.getParameter("eid")!=null)
		eid=Integer.parseInt(request.getParameter("eid"));
		String action="list";
		if(request.getParameter("action")!=null)
			action=request.getParameter("action");
		int supervisorID=0;
		if(request.getParameter("supervisorID")!=null)
		supervisorID=Integer.parseInt(request.getParameter("supervisorID"));
		String status="";
		if(request.getParameter("status")!=null)
		status=request.getParameter("status");
		String firstName="";
		if(request.getParameter("firstName")!=null)
		firstName=request.getParameter("firstName");
		String lastName="";
		if(request.getParameter("lastName")!=null)
		lastName=request.getParameter("lastName");
		boolean isEmployee=true;
		if(request.getParameter("isEmployee")!=null)
		isEmployee=Boolean.parseBoolean(request.getParameter("isEmployee"));
		
		System.out.println(""+isEmployee);
		
		
		List<JobSeeker> talentList=new ArrayList<JobSeeker>();
		try{
		talentList=dataManager.queryTalents(status,isEmployee,firstName,lastName,supervisorID,eid);
		}
		catch(NullPointerException ex){
			ex.printStackTrace();
		}
		
		if (action.equals("list")){
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			oos.writeObject(talentList);
			oos.flush();
			oos.close();
			oos=null;
		}
		else{
		request.setAttribute("talentList", talentList);
		request.setAttribute("status", status);
		request.setAttribute("firstName", firstName);
		request.setAttribute("lastName", lastName);
		request.setAttribute("isEmployee", isEmployee);
		if(request.getParameter("supervisorID")!=null)
			request.setAttribute("supervisorID", supervisorID);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/searchtalent.jsp");
		dispatcher.forward( request, response);
		}
	}

}
