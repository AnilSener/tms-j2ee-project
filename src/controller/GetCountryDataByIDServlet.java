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

import com.google.gson.Gson;

import DAO.DataManager;
import model.Country;

/**
 * Servlet implementation class GetCountryDataByIDServlet
 */
@WebServlet("/getCountryDataByIDServlet")
public class GetCountryDataByIDServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCountryDataByIDServlet() {
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
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int countryID=Integer.parseInt(request.getParameter("countryID"));
		String type=request.getParameter("type");
		
		Country country=(Country) dataManager.getCountryData(countryID);
		 
		if (type.equals("stream")){
		    ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		    oos.writeObject(country);
		    oos.flush();
		    oos.close();
		    oos=null;
		}
		else if (type.equals("application/json")){
			
			List<Country> countryList=new ArrayList<Country>();
			countryList.add(country);
			String json = null;
			json = new Gson().toJson(countryList);
	        response.setContentType(type);
	        response.getWriter().write(json);
		}
	}

}
