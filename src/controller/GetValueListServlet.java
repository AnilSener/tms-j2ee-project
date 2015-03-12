package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Locale;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.City;
import model.Country;


import model.Sector;
import model.Skill;

import DAO.DataManager;

/**
 * Servlet implementation class GetValueListServlet
 */
@WebServlet("/getValueListServlet")
public class GetValueListServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetValueListServlet() {
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
		
		 ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		ValueEnum enumval = ValueEnum.valueOf(type.toUpperCase(Locale.ENGLISH));
		
		switch (enumval){
		case SECTOR:List<Sector> sectorlist= dataManager.getAllSectors();oos.writeObject(sectorlist);break;
		case COUNTRY:List<Country> countrylist=  dataManager.getAllCountries();oos.writeObject(countrylist);break;
		case CITY:List<City> citylist=dataManager.getAllCities();oos.writeObject(citylist);break;
		case SKILL:List<Skill> skilllist=dataManager.getAllSkills();oos.writeObject(skilllist);break;
		}
		    
		    oos.flush();
		    oos.close();
		    oos=null;
	}

	public enum ValueEnum {
		SECTOR,
		COUNTRY,
		CITY,
		SKILL
		;

	}

}
