package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DataManager;
import model.Skill;

/**
 * Servlet implementation class AddJobSkillServlet
 */
@WebServlet(description = "Add Job Skill Servlet", urlPatterns = { "/addjobskill" })
public class AddJobSkillServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddJobSkillServlet() {
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
		
		int skillID =Integer.parseInt(request.getParameter("skillName"));
		String jspFileName=request.getParameter("jspFileName");
		int jobID=0;
		if (jspFileName=="jobdetails.jsp"){
			jobID=Integer.parseInt(request.getParameter("jobID"));
			request.setAttribute("jobID", jobID);
		}
		Skill skill=dataManager.getSkillData(skillID);

		List<Skill> selectedJobSkillList=(ArrayList<Skill>) request.getSession().getAttribute("selectedJobSkillList");
		
		if (skill!=null){
		selectedJobSkillList.add(skill);
		request.getSession().setAttribute("selectedJobSkillList", selectedJobSkillList);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/"+jspFileName);
		dispatcher.forward( request, response);
	}

}
