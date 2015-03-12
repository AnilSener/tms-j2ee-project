package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.message.BasicNameValuePair;

import model.City;
import model.Country;


import model.Sector;
import model.Skill;

import DAO.DataManager;

/**
 * Servlet implementation class GetValueListServlet
 */
@WebServlet("/queryValueServlet")
public class QueryValueServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryValueServlet() {
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
		String type=request.getParameter("type");
		String name=request.getParameter("name");
		String action=request.getParameter("action");
		String dependentField=request.getParameter("dependentField");
	
		 List<BasicNameValuePair> valuelist=dataManager.queryAllValues(name,type);
		 	if(action.equals("search")){
		 		request.setAttribute("valueList", valuelist);
		 		request.setAttribute("type", type);
		 		request.setAttribute("name", name);
		 		RequestDispatcher dispatcher = request.getRequestDispatcher("/valuelists.jsp");
				dispatcher.forward( request, response);
		 	}
		 	else{
		 		 ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			 	oos.writeObject(valuelist); 
			    oos.flush();
			    oos.close();
			    oos=null;
		 	}
	}

	

}
