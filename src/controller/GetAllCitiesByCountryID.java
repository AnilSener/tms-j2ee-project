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

import model.City;

import DAO.DataManager;
import com.google.gson.Gson;
/**
 * Servlet implementation class GetAllCitiesByCountryID
 */
@WebServlet("/getAllCitiesByCountryID")
public class GetAllCitiesByCountryID extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllCitiesByCountryID() {
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
		int countryID=Integer.parseInt(request.getParameter("countryID"));
		String type=request.getParameter("type");
		String jspName=request.getParameter("jspName");
		List<City> cities=dataManager.getAllCitiesByCountryId(countryID);
		
		
		
		//The condition will be added to enable this servlet for also home screen
		
		   
		/* ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		    oos.writeObject(cities);
		    oos.flush();
		    oos.close();
		    oos=null;*/
		if (type.equals("text/html")){
			
			request.setAttribute("cities", cities);
			request.setAttribute("countryID", countryID);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/"+jspName);
			dispatcher.forward( request, response);	
		}
		else if (type.equals("application/json")){
		
		String json = null;
		json = new Gson().toJson(cities);
        response.setContentType(type);
        response.getWriter().write(json);
		}
	}

}
