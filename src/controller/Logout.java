package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class Logout extends HttpServlet {
	String strSuccess="";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
	        HttpServletResponse response) throws IOException, ServletException
	{
		request.getSession(true).invalidate();
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		strSuccess="You have successfully logged out";
		request.setAttribute("success", strSuccess);
		dispatcher.forward( request, response);		}

}
