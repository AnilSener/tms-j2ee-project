package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import model.Interview;
import model.Job;
import model.JobApplication;
import model.JobSeeker;
import DAO.DataManager;

/**
 * Servlet implementation class NewInterviewServlet
 */
@WebServlet(description = "New Interview Servlet", urlPatterns = { "/newinterview" })
public class NewInterviewServlet extends DBConnectionServlet  {
	private static final long serialVersionUID = 1L;
	
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
     
	private DataManager dataManager;
	InterviewValidator intVal=new InterviewValidator();
	EmailFormatValidator emailVal=new EmailFormatValidator();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewInterviewServlet() {
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
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int jobID = Integer.parseInt(request.getParameter("jobID"));
		int jobAppID = Integer.parseInt(request.getParameter("jobAppID"));
		int jobSeekerID = Integer.parseInt(request.getParameter("jobSeekerID"));
		int sequenceNo = Integer.parseInt(request.getParameter("sequenceno"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = null;
		
		try {
			date = (Date) formatter.parse(request.getParameter("interviewDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			strError = "Interview Date has missing fields. Please select all!";
			dbOK = false;
			valOK=false;
		}
	
		
		String status="Open";
		String location=request.getParameter("location");
		String interviewer=request.getParameter("interviewer");
		String contactEmail=request.getParameter("contactEmail");
		String notes="";
		int salary=0;
		String currency="";
		Interview interview=null;
		
		if(intVal.notNull(location, interviewer, contactEmail)){
			if(emailVal.validate(contactEmail)){
				if(intVal.isLaterDate(date)){
					valOK=true;
					
				
				interview=new Interview(jobID, jobAppID, date, status, location, interviewer,contactEmail,sequenceNo,notes,salary,currency);
				dbOK=dataManager.saveNewInterview(interview);
				}
				else{
					strError = "Interview date should be later than now!";
					dbOK = false;
					valOK=false;
				}
				
				}
			else{
				strError = "Invalid email format. Please enter a correct email!";
				dbOK = false;
				valOK=false;
			}
		}
		else{
			strError="Location, Interviewer, and Contact Email fields cannot be null";
			dbOK = false;
			valOK=false;
		}
		RequestDispatcher dispatcher;
		if (dbOK){
			
			//To send Interview Request Mail
			JobSeeker jobseeker=dataManager.getJobSeeker(jobSeekerID);
			Employer unamEmp =(Employer)request.getSession().getAttribute("employerBean");	
			Job job=dataManager.getJobData(jobID);
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String tempDate=sdf.format(interview.getDate());
			String subject="Interview requested by "+unamEmp.getCompanyName()+" for "+job.getTitle()+" position in BestJobs.";
			String content="<i>Dear "+jobseeker.getFirstname()+" "+jobseeker.getLastname()+",</i><br/>";
			content+="<i>We have evaluated your application to "+job.getTitle()+" position through Best Jobs and decided to invite you for an interview on "+tempDate+" with "+interview.getInterviewer()+".</i><br/>";
			content+="<i>The interview will be conducted in the following address: "+interview.getLocation()+". If you agree with our terms, please accept the interview call requested to you in Best Jobs.</i><br/><br/>";
			content+="<i>Best Regards,</i><br/>";
			content+="<i>"+unamEmp.getCompanyName()+" Human Resources Team </i><br/>";
			content+="<i><b>Contact Email : "+interview.getContactEmail()+" | Phone : "+unamEmp.getPhone()+"</b><i><br/>";
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("recipient",jobseeker.getEmail()));
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
			
			strSuccess="You have successfully created an interview";
			request.setAttribute("success", strSuccess);
			request.setAttribute("mailresultmessage", resultMessage);
			JobApplication jobApp=dataManager.getJobApplication(jobSeekerID, jobID);
			jobApp.setApplicationStatus("In Progress");
			dataManager.updateJobApplicationStatus(jobApp.getId(),jobApp.getApplicationStatus());
			dispatcher = request.getRequestDispatcher("/interviews.jsp");
		}
		else{
			request.setAttribute("success", strSuccess);
			dispatcher = request.getRequestDispatcher("/jobapplicants.jsp");
		}
		
		dispatcher.forward( request, response);
	}

}
