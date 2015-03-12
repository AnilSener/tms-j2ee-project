package controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;

import DAO.DataManager;

/**
 * Servlet implementation class DBConnectionServlet
 */
@WebServlet(name="DBConnectionServlet",urlPatterns={"/DBConnectionServlet"})
public class DBConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DataManager dataManager;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		dataManager = new DataManager();
	    dataManager.setDbURL(getServletContext().getInitParameter("dbURL"));
	    dataManager.setDbUserName(getServletContext().getInitParameter("dbUserName"));
	    dataManager.setDbPassword(getServletContext().getInitParameter("dbPassword"));
	}
	
	public DataManager getDataManager(){
		
		return this.dataManager;
	}
}
