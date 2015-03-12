package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChooseEmployeeServlet
 */
@WebServlet("/chooseEmployeeServlet")
public class ChooseEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChooseEmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
    	String code = request.getParameter("code");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String status= request.getParameter("status");
		String jspName= request.getParameter("jspName");
		
		request.setAttribute("code", code);
		request.setAttribute("title", title);
		request.setAttribute("description", description);
		request.setAttribute("status", status);
		request.setAttribute("jspName", jspName);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/searchtalent.jsp");
		dispatcher.forward( request, response);
		
	}

	

}
