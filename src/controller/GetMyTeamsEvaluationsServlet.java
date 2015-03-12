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

import model.Evaluation;
import DAO.DataManager;

/**
 * Servlet implementation class GetAllInteviewsByEmployerServlet
 */
@WebServlet("/getMyTeamsEvaluations")
public class GetMyTeamsEvaluationsServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyTeamsEvaluationsServlet() {
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
		int supervisorid=Integer.parseInt(request.getParameter("supervisorid"));
		String status=request.getParameter("status");
		
		List<Evaluation> projectList=dataManager.getAllEvaluationsBySupervisorID(supervisorid);
		 
		
		    ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		    oos.writeObject(projectList);
		    oos.flush();
		    oos.close();
		    oos=null;
	}

}



