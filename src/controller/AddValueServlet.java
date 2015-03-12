package controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.GetValueListServlet.ValueEnum;


import model.City;
import model.Country;
import model.Employee;
import model.JobSeeker;
import model.Sector;
import model.Skill;


import DAO.DataManager;

/**
 * Servlet implementation class AddSkillServlet
 */
@WebServlet(description = "Add Value Servlet", urlPatterns = { "/addValueServlet" })
public class AddValueServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager; 
	boolean dbOK=false;
	boolean valOK=false;
	String strSuccess="";
	String strError="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddValueServlet() {
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
		
		String type=request.getParameter("type");
		String name=request.getParameter("name");
		String dependentField=request.getParameter("dependentField");
		
		if(!type.equals("") && !name.equals("")){
			if(type.equals("skill") || type.equals("sector") || ((type.equals("city") || type.equals("country")) && !dependentField.equals(""))){	
					valOK=true;
					ValueEnum enumval = ValueEnum.valueOf(type.toUpperCase(Locale.ENGLISH));
		
					switch (enumval){
					case SECTOR:dbOK=dataManager.addSectorValue(name);break;
					case COUNTRY:dbOK=dataManager.addCountryValue(name,dependentField);break;
					case CITY:int cityID=dataManager.getCountryData(dependentField).getId();dbOK=dataManager.addCityValue(name,cityID);break;
					case SKILL:dbOK=dataManager.addSkillValue(name);break;
					}
					if(dbOK)
						strSuccess="You have successfully added the value!";
					else
						strError="Value cannot be added to DB!";
			}
			else{
				valOK=false;
				strError="Please fill a dependent field!";
			}
		}
		else{
			valOK=false;
			strError="Please fill value name and type!";
		}
		RequestDispatcher dispatcher;
		if (!dbOK || !valOK)
		{	
			request.setAttribute("error", strError);
		}
		else{
			
			request.setAttribute("success", strSuccess);
			request.setAttribute("type", type);
		}
		
		dispatcher = request.getRequestDispatcher("/valuelists.jsp");
		dispatcher.forward( request, response);
		
	}

}
