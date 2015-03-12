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

import DAO.DataManager;
import model.Skill;


/**
 * Servlet implementation class RemoveJobSkillServlet
 */
@WebServlet(description = "Remove Job Skill Servlet", urlPatterns = { "/removejobskill" })
public class RemoveJobSkillServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveJobSkillServlet() {
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
		
		int skillID =Integer.parseInt(request.getParameter("skillId"));
		//System.out.println("Title"+request.getParameter("hiddenTitle2"));
		String jspFileName=request.getParameter("jspFileName");
		//System.out.println("File Name:"+jspFileName);
		int jobID=0;
		if (jspFileName=="jobdetails.jsp"){
			jobID=Integer.parseInt(request.getParameter("jobID"));
			request.setAttribute("jobID", jobID);
		}
		List<Skill> selectedJobSkillList=(List<Skill>) request.getSession().getAttribute("selectedJobSkillList");
	
		for (int i=0;i<selectedJobSkillList.size();i++){
			
				if (skillID==selectedJobSkillList.get(i).getSkillID()){
					selectedJobSkillList.remove(i);
					break;
				}
		}
		
		
		request.getSession().setAttribute("selectedJobSkillList", selectedJobSkillList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/"+jspFileName);
		dispatcher.forward( request, response);
	}

}
