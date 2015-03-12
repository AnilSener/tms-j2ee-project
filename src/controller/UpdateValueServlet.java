package controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DataManager;

import controller.GetValueListServlet.ValueEnum;

/**
 * Servlet implementation class UpdateValueServlet
 */
@WebServlet("/updateValueServlet")
public class UpdateValueServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	boolean dbOK=false;
	boolean valOK=false;
	String strSuccess="";
	String strError="";
	private DataManager dataManager;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateValueServlet() {
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
		
		String[] idsArray=request.getParameterValues("id");
		String[] typesArray=request.getParameterValues("type");
		String[] namesArray=request.getParameterValues("name");
		String[] dependentFieldsArray=request.getParameterValues("dependentField");
		for(int i=0;i<idsArray.length;i++){
		int id=Integer.parseInt(idsArray[i]);
		String type=typesArray[i];
		String name=namesArray[i];
		String dependentField=dependentFieldsArray[i];
		if(!type.equals("") && !name.equals("")){
			if(type.equals("skill") || type.equals("sector") || ((type.equals("city") || type.equals("country")) && !dependentField.equals(""))){	
					valOK=true;
					ValueEnum enumval = ValueEnum.valueOf(type.toUpperCase(Locale.ENGLISH));
		
					switch (enumval){
					case SECTOR:dbOK=dataManager.updateSectorValue(id,name);break;
					case COUNTRY:dbOK=dataManager.updateCountryValue(id,name,dependentField);break;
					case CITY:int cityID=dataManager.getCountryData(dependentField).getId();dbOK=dataManager.updateCityValue(id,name,cityID);break;
					case SKILL:dbOK=dataManager.updateSkillValue(id,name);break;
					}
					if(dbOK)
						strSuccess="You have successfully added the value!";
					else
						strError="Value cannot be updated in DB!";
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
		}
		
		RequestDispatcher dispatcher;
		if (!dbOK || !valOK)
		{	
			request.setAttribute("error", strError);
		}
		else{
			
			request.setAttribute("success", strSuccess);
			
		}
		
		dispatcher = request.getRequestDispatcher("/valuelists.jsp");
		dispatcher.forward( request, response);
		
		
	}

}



