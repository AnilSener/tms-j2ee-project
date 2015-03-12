package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import model.Employer;
import DAO.DataManager;

/**
 * Servlet implementation class QueryJobServlet
 */
@WebServlet("/employerActivation")
public class EmployerActivationServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
	private String strSuccess="";
	private String strError="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployerActivationServlet() {
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
		
		int eid=Integer.parseInt(request.getParameter("employerID"));
		Employer employer=dataManager.getEmployerData(eid);
		String subject="";
		String content="";
		
		if (request.getParameter("Activate")!=null){
			employer.setActive(true);
			employer.setActivationDate(new java.util.Date());
			subject="Employer Supscriotion Activation Notification by BestJobs.";
			content="<i>Dear Customer,</i><br/>";
			content+="<i>We have verified that your company named as \""+employer.getCompanyName()+"\" is fulfilling our subscription conditions.</i><br/>";
			content+="<i>Therefore, your subscription is activated by Best Jobs System Administrator.</i><br/>";
			content+="<i>Best Regards,</i><br/>";
			content+="<i>Best Jobs Administration,</i><br/>";
			content+="<i><b>Contact Email : bestjobs@gmail.com </b><i><br/>";
		}
		else if (request.getParameter("Deactivate")!=null){
			employer.setActive(false);
			content="<i>Dear Customer,</i><br/>";
			content+="<i>We are sorry to inform that the account of your company named as \""+employer.getCompanyName()+"\" is suspended, since you are not fulfilling our conditions.</i><br/>";
			content+="<i>Please contact with us in order to receive more detailed information.</i><br/>";
			content+="<i>Best Regards,</i><br/>";
			content+="<i>Best Jobs Administration,</i><br/>";
			content+="<i><b>Contact Email : bestjobs@gmail.com </b><i><br/>";
		}
		
		
		boolean dbOK=dataManager.updateEmployer(employer);
		
		if(dbOK){
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("recipient",employer.getEmail()));
			params.add(new BasicNameValuePair("subject", subject));
			params.add(new BasicNameValuePair("content", content));
			@SuppressWarnings("deprecation")
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()))+"/EmailSendingServlet");
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream is = httpEntity.getContent();
			ObjectInputStream in = new ObjectInputStream(is);
			String resultMessage="";
			try {
				resultMessage = (String) in.readObject();
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			httpClient.close();
			request.setAttribute("mailresultmessage", resultMessage);
			strSuccess="Employee is updated";
			request.setAttribute("success", strSuccess);
		}else{
			strError="Employee cannot be updated";
			request.setAttribute("error", strError);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/employersubscriptions.jsp");
		dispatcher.forward( request, response);
	}

}


