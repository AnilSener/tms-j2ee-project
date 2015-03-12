package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.EmailUtility;
 
/**
 * A servlet that takes message details from user and send it as a new e-mail
 * through an SMTP server.
 *
 * 
 *
 */
@WebServlet("/EmailSendingServlet")
public class EmailSendingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String host;
    private String port;
    private String user;
    private String pass;
 
    public void init() {
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    }
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // reads form fields
        String recipient = request.getParameter("recipient");
        String subject =  request.getParameter("subject");
        String content = request.getParameter("content");
       // String returnjspname = (String) request.getAttribute("returnjspname");
        // String strSuccess = (String) request.getAttribute("success");
 
        String resultMessage = "";
 
        try {
            EmailUtility.sendEmail(host, port, user, pass, recipient, subject,
                    content);
            resultMessage = "The e-mail is sent successfully";
        } catch (Exception ex) {
            ex.printStackTrace();
            resultMessage = "There were an error: " + ex.getMessage();
        } finally {
          // request.setAttribute("mailresultmessage", resultMessage);
           //request.setAttribute("success", strSuccess);
        /*   getServletContext().getRequestDispatcher("/"+returnjspname).forward(
                    request, response);
         
		    System.out.println("Servlet done.");*/
           ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		    oos.writeObject(resultMessage);
		    oos.flush();
		    oos.close();
		    oos=null;
        }
    }
}