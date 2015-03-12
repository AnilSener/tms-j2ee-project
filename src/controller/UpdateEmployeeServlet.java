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

import model.Employee;
import model.Employer;
import model.JobSeeker;
import DAO.DataManager;

/**
 * Servlet implementation class JobSeekerUpdateInfoServlet
 */
@WebServlet(description = "Employee Update Info Servlet", urlPatterns = { "/updateEmployeeServlet" })
public class UpdateEmployeeServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
     
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
	//Instance of beans
		private DataManager dataManager;
		
		EmailFormatValidator emailval=new EmailFormatValidator();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateEmployeeServlet() {
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
		
		int id=0;
		if (request.getParameter("id")!=null)
			id=Integer.parseInt((String)request.getParameter("id"));
		
		int jobSeekerID=0;
		if (request.getParameter("jobSeekerID")!=null)
			jobSeekerID=Integer.parseInt((String)request.getParameter("jobSeekerID"));
		
		
		int supervisorId=0;
		Employee supervisor=null;
		if (request.getParameter("supervisorID")!=null){
			supervisorId=Integer.parseInt((String)request.getParameter("supervisorID"));
			if (supervisorId!=0)
			supervisor=dataManager.getEmployeeDataByEmployeeId(supervisorId);
		}
		
		String status = request.getParameter("status");
		String companyEmail = request.getParameter("companyemail");
		Employer unamEmp =(Employer)request.getSession().getAttribute("employerBean");	
		
	
			
		
		if(!companyEmail.equals("")){
		
			
				if (emailval.validate(companyEmail)){
					valOK=true;
				}
				else{
					
					strError = "Invalid email format. Please enter a correct Company Email!";
					dbOK = false;
					valOK=false;
				}

		}
		
		else{
			strError="Company Email cannot be null";
			dbOK = false;
			valOK=false;
		}
		
	
		String oldStatus="";
		String oldCompanyEmail="";
		int oldSupervisorId=0;
		Employee employee=null;
		if(valOK){
		employee = dataManager.getEmployeeDataByEmployeeId(id);
		oldStatus=employee.getStatus();
		oldCompanyEmail=employee.getCompanyEmail();
		oldSupervisorId=employee.getSupervisorId();
		
		
		employee.setId(id);
		employee.setCompanyEmail(companyEmail);
		employee.setStatus(status);
		employee.setSupervisorId(supervisorId);
		dbOK=dataManager.updateEmployee(employee);
		}
		
		RequestDispatcher dispatcher;
			if (dbOK){
				String subject="Your details for user "+employee.getUsername()+" is successfully updated BestJobs.";
				String content="<i>Dear "+employee.getFirstname()+" "+employee.getLastname()+",</i><br>";
				content+="<i>Your employment details in "+unamEmp.getCompanyName()+" are successfully updated in Best Jobs with the following information.</i><br>";
				if (!status.equals(oldStatus))
				content+="<i>Your Employement Status is changed as \"<b>"+status+"</b>\".</i><br>";
				if (supervisorId!=oldSupervisorId)
					content+="<i>Your new supervisor is \"<b>"+supervisor.getFirstname()+" "+supervisor.getLastname()+"</b>\".</i><br>";
				if (!companyEmail.equals(oldCompanyEmail))
				content+="<i>You have a new Company Email as \"<b>"+companyEmail+"</b>\", you will receive all updates regarding your employment to your \"Company Mail Address\".</i><br>";
				content+="<i>Best Regards,</i><br/>";
				content+="<i>"+unamEmp.getCompanyName()+" Human Resources Team </i><br/>";
				content+="<i><b>Contact Email : "+unamEmp.getEmail()+" | Phone : "+unamEmp.getPhone()+"</b><i><br/>";
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				if (oldCompanyEmail.equals("") ||  oldCompanyEmail==null)
				params.add(new BasicNameValuePair("recipient",employee.getEmail()));
				else if (!companyEmail.equals(oldCompanyEmail))
				params.add(new BasicNameValuePair("recipient",oldCompanyEmail));
				else
				params.add(new BasicNameValuePair("recipient",companyEmail));
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
				strSuccess = "Employer user information  is successfully updated";
				
				
				dispatcher = request.getRequestDispatcher("/talentdetails.jsp");
				
			request.setAttribute("success", strSuccess);
			request.setAttribute("mailresultmessage", resultMessage);
		
			}
			else{
				
			request.setAttribute("error", strError);
			
			}
			
		request.setAttribute("jobSeekerID", jobSeekerID);
		dispatcher = request.getRequestDispatcher("/talentdetails.jsp");
		dispatcher.forward( request, response);	
	}

}

