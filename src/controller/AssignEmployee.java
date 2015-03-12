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

import model.Assignment;
import model.Project;

/**
 * Servlet implementation class AssignEmployeeServlet
 */
@WebServlet("/assignEmployee")
public class AssignEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Multiple row parameter selection !!!
		
		
		String[] employeeIDsArray=request.getParameterValues("isEmployeeChecked");
		String code = request.getParameter("code");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String status= request.getParameter("status");
		String jspName=request.getParameter("jspName");
		
		Project project=new Project();
		List<Integer> employeeIDs=new ArrayList<Integer>();
		List<Assignment> assignments=new ArrayList<Assignment>();
		try{
		for (int i=0;i<employeeIDsArray.length;i++){
			Assignment assignment=new Assignment();
			employeeIDs.add(new Integer(Integer.parseInt(employeeIDsArray[i])));
			System.out.println("Employee id "+employeeIDs.get(i).intValue());
			assignment.setEmployeeId(employeeIDs.get(i).intValue());
			assignments.add(assignment);
		}
		}
		catch(NullPointerException ex){ex.printStackTrace();};
		project.setCode(code);
		project.setTitle(title);
		project.setDescription(description);
		project.setStatus(status);
		project.setAssignments(assignments);
		
		request.setAttribute("project", project);
		RequestDispatcher dispatcher= request.getRequestDispatcher("/"+jspName);
		dispatcher.forward( request, response);
	}

}
