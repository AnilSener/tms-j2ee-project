package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DataManager;
import model.Project;

/**
 * Servlet implementation class GetSectorDataByIDServlet
 */
@WebServlet("/getProjectDataByID")
public class GetProjectDataByID extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProjectDataByID() {
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
		String code=request.getParameter("code");
		int eid=Integer.parseInt(request.getParameter("eid"));
		
		Project project=(Project) dataManager.getProjectDataByEmployer(code, eid);
		 
		
		    ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		    oos.writeObject(project);
		    oos.flush();
		    oos.close();
		    oos=null;
	}

}
