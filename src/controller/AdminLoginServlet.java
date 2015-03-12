package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String ctxUsername="";
    private String ctxPassword="";
    private LoginValidator val = new LoginValidator();
    private String strError;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		ServletContext context = getServletContext();
        ctxUsername = context.getInitParameter("adminusername");
        ctxPassword = context.getInitParameter("adminpassword");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		RequestDispatcher dispatcher=null;
		
		if(!val.validate(username, password) || !username.equals(ctxUsername) || !password.equals(ctxPassword)){
				
				strError = "Invalid username or password.";
				dispatcher = request.getRequestDispatcher("/adminlogin.jsp");
				dispatcher.forward( request, response);	
		}
		else{
			HttpSession session = request.getSession(true);
			session.setAttribute("username", username);
			dispatcher = request.getRequestDispatcher("/employersubscriptions.jsp");
			dispatcher.forward( request, response);	
		}
	}

}
