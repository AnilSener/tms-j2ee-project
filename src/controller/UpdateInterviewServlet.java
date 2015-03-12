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
 * Servlet implementation class UpdateInterviewServlet
 */
@WebServlet(description = "Update Interview Servlet", urlPatterns = { "/updateinterview" })
public class UpdateInterviewServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
	
	InterviewValidator intVal=new InterviewValidator();
	EmailFormatValidator emailVal=new EmailFormatValidator();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateInterviewServlet() {
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
		int interviewID = Integer.parseInt(request.getParameter("interviewID"));
		String jspName=request.getParameter("fileName");
		Interview interview=null;
		Interview interviewOld=null;
		int jobID=0;
		int jobSeekerID=0;
		int salary=0;
		if (jspName.equals("interviewdetail.jsp")){
			jobID = Integer.parseInt(request.getParameter("jobID"));
			jobSeekerID = Integer.parseInt(request.getParameter("jobSeekerID"));
			salary=Integer.parseInt(request.getParameter("salary"));
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = null;
		if (jspName.equals("interviewdetail.jsp")){
		try {
			date = (Date) formatter.parse(request.getParameter("interviewDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			strError = "Interview Date has missing fields. Please select all!";
			dbOK = false;
			valOK=false;
		}
		}

		String status=request.getParameter("status");
		String location=request.getParameter("location");
		String interviewer=request.getParameter("interviewer");
		String contactEmail=request.getParameter("contactEmail");
		String notes=request.getParameter("notes");
		String resultCode=request.getParameter("resultcode");
			
		
		if(jspName.equals("interviewdetail.jsp")){
		if(!status.equals("Closed") && intVal.notNull(location, interviewer, contactEmail) || status.equals("Closed") && intVal.notNullOnClosure(location, interviewer, contactEmail, notes, resultCode)){
			if(emailVal.validate(contactEmail)){
				if(intVal.isLaterDate(date)){
				valOK=true;
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
			if(!status.equals("Closed")){
			strError="Location, Interviewer, and Contact Email fields cannot be null";
			} else{
			strError="Location, Interviewer, Contact Email, Result Code and Interview Notes fields cannot be null";	
			}
			dbOK = false;
			valOK=false;
		}
		} 
		
		if(valOK || jspName.equals("myinterviewcalls.jsp")){
			
			interview=dataManager.getInterviewData(interviewID);
			interviewOld=new Interview(interview);
			if (jspName.equals("interviewdetail.jsp")){
				interview.setDate(date);
				interview.setContactEmail(contactEmail);
				interview.setInterviewer(interviewer);
				interview.setLocation(location);
				interview.setNotes(notes);
				interview.setResultCode(resultCode);
				interview.setSalary(salary);
			}
		interview.setStatus(status);
		dbOK=dataManager.updateInterview(interview);
		}
		
		RequestDispatcher dispatcher; 
		if (dbOK){
			
			//To send Interview Request Mail
			JobSeeker jobseeker=dataManager.getJobSeeker(jobSeekerID);
			Employer unamEmp =(Employer)request.getSession().getAttribute("employerBean");	
			Job job=dataManager.getJobData(jobID);
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String tempDate=sdf.format(interview.getDate());
			
			String email="";
			String subject="";
			String content="";
			
			if (!interviewOld.getStatus().equals("Open") && interview.getStatus().equals("Open") || !interview.getLocation().equals(interviewOld.getLocation()) || !interview.getInterviewer().equals(interviewOld.getInterviewer()) || !interview.getDate().equals(interviewOld.getDate())){
			email=jobseeker.getEmail();
			subject="Interview request by "+unamEmp.getCompanyName()+" for "+job.getTitle()+" position is updated in BestJobs.";
			content="<i>Dear "+jobseeker.getFirstname()+" "+jobseeker.getLastname()+",</i><br/>";
			content+="<i>We have rescheduled your job interview for "+job.getTitle()+" position on "+tempDate+" with "+interview.getInterviewer()+".</i><br/>";
			content+="<i>The interview will be conducted in the following address: "+interview.getLocation()+". If you agree with our terms, please accept the interview call requested to you in Best Jobs.</i><br/><br/>";
			content+="<i>Best Regards,</i><br/>";
			content+="<i>"+unamEmp.getCompanyName()+" Human Resources Team </i><br/>";
			content+="<i><b>Contact Email : "+interview.getContactEmail()+" | Phone : "+unamEmp.getPhone()+"</b><i><br/>";
			}
			
			if (!email.equals("") && !subject.equals("") && !content.equals("")){
				
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
			}
			
			strSuccess="You have successfully updated the interview record";
			request.setAttribute("success", strSuccess);
			JobApplication jobApp=dataManager.getJobApplication(jobSeekerID, jobID);
			jobApp.setApplicationStatus("In Progress");
			dataManager.updateJobApplicationStatus(jobApp.getId(),jobApp.getApplicationStatus());
			
		}
		else if(jspName.equals("interviewdetail.jsp")){
			
			request.setAttribute("error", strError);
			request.setAttribute("interviewID", interviewID);
			dispatcher = request.getRequestDispatcher("/interviewdetail.jsp");
		}
		
		request.setAttribute("interviewID", interviewID);
		dispatcher = request.getRequestDispatcher("/"+jspName);
	
		dispatcher.forward( request, response);
	}

}
