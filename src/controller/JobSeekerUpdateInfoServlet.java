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

import model.JobSeeker;
import DAO.DataManager;

/**
 * Servlet implementation class JobSeekerUpdateInfoServlet
 */
@WebServlet(description = "JobSeeker Update Info Servlet", urlPatterns = { "/jobseekerupdateinfo" })
public class JobSeekerUpdateInfoServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
     
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
	//Instance of beans
		private DataManager dataManager;
		private LoginValidator val = new LoginValidator();
		EmailFormatValidator emailval=new EmailFormatValidator();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobSeekerUpdateInfoServlet() {
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
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String job = request.getParameter("job");
		String phone = request.getParameter("phone");
		//String returnjspname="jobseekerupdateinfo.jsp";
		
		
		if(val.notNull(username, password, firstname, lastname, address, email, job, phone)){
		
			if(val.validate(username, password))
			{
				if (emailval.validate(email)){
					valOK=true;
				}
				else{
					
					strError = "Invalid email format. Please enter a correct email!";
					dbOK = false;
					valOK=false;
				}
					
			}
			else
			{
				strError = "Invalid username or password. Employer and passwprd cannot be less than 6, more than 20 characters.";
				dbOK = false;
				valOK=false;
			}		
			
		}
		
		else{
			strError="username, password, firstname, lastname, address, email, job, phone cannot be null";
			dbOK = false;
			valOK=false;
		}
	
		if(valOK){
		JobSeeker jobSeeker = new JobSeeker();
		jobSeeker.setUsername(username);
		jobSeeker.setAddress(address);
		jobSeeker.setEmail(email);
		jobSeeker.setFirstname(firstname);
		jobSeeker.setLastname(lastname);
		jobSeeker.setPassword(password);
		jobSeeker.setPhone(phone);
		jobSeeker.setJob(job);
		dbOK=dataManager.updateJobSeeker(jobSeeker);
		}
		
		RequestDispatcher dispatcher;
			if (dbOK){
				String subject="Your details for user"+username+" is successfully updated BestJobs.";
				String content="<i>Greetings "+firstname+" "+lastname+"!</i><br>";
				content+="<i>Your jobseeker user registeration details are successfully updated in Best Jobs with the following information.</i><br>";
				content+="<b>User Name = "+username+"</b><br>";
				content+="<b>Password = "+password+"</b><br>";
				content+="<b>Job = "+job+"</b><br>";
				content+="<b>Address = "+address+"</b><br>";
				content+="<b>Email = "+email+"</b><br>";
				content+="<b>Phone = "+phone+"</b><br>";
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("recipient",email));
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
				
				strSuccess = "Job Seeker user information  is succefully updated";
				
				/*request.setAttribute("success", strSuccess);
				request.setAttribute("recipient",email );
				request.setAttribute("subject", subject);
				request.setAttribute("content", content);
				request.setAttribute("returnjspname", returnjspname);
				*/
				
			request.setAttribute("success", strSuccess);
		
			}
			else{
				
			request.setAttribute("error", strError);
			
			}
			
			dispatcher = request.getRequestDispatcher("/jobseekerupdateinfo.jsp");
		
		dispatcher.forward( request, response);	
	}

}
