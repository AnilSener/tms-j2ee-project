package controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import DAO.DataManager;


@WebServlet("/searchEmployee")
public class SearchEmployeeServlet extends DBConnectionServlet {

	/**
	 * 
	 */

	private DataManager dataManager;
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dataManager = super.getDataManager();
	}
}
