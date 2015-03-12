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
import model.Interview;
import model.Job;
import model.JobApplication;
import model.JobSeeker;
import DAO.DataManager;

/**
 * Servlet implementation class UpdateJobApplicationServlet
 */
@WebServlet("/updateJobApplication")
public class UpdateJobApplicationServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	
	
	private DataManager dataManager;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateJobApplicationServlet() {
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
		String applicationStatus=request.getParameter("applicationStatus");
		int applicationID=Integer.parseInt(request.getParameter("applicationID"));
		String jspName=request.getParameter("fileName");
		JobApplication jobApp=dataManager.getJobApplication(applicationID);
		jobApp.setApplicationStatus(applicationStatus);
		dataManager.updateJobApplicationStatus(jobApp.getId(),jobApp.getApplicationStatus());
		JobSeeker jobseeker=dataManager.getJobSeekerByApplication(applicationID);
		Job job=dataManager.getJobData(jobApp.getJobID());
		Employer employer=(Employer)request.getSession().getAttribute("employerBean");
		int eid=employer.getEid();
		String email=jobseeker.getEmail();
		String subject="";
		String content="";
		if (applicationStatus.equals("Hired")){
		java.util.Date hiringDate=new java.util.Date();
		
		
		int salary=0;
		String currency="";
		List<Interview> interviewList=dataManager.getAllInteviewsByEmployer(eid, "Closed");
		for(int i=interviewList.size()-1;i>=0;i--){
			if(interviewList.get(i).getSalary()!=0 && (interviewList.get(i).getResultCode().equals("To Be Hired") || interviewList.get(i).getResultCode().equals("Success"))){
				salary=interviewList.get(i).getSalary();
				currency=interviewList.get(i).getCurrency();
				break;
			}
		}
		
		jobseeker.setJob(job.getTitle());
		
		boolean dbOK=dataManager.updateJobSeeker(jobseeker);
		
		Employee employee=new Employee(jobseeker,eid,hiringDate,"New Hired","",0,salary,currency);
		dbOK=dataManager.saveNewEmployee(employee);
		
			if (dbOK){
				subject="Regarding your Application to "+employer.getCompanyName()+" for "+job.getTitle()+" position is in BestJobs.";
				content="<i>Dear "+jobseeker.getFirstname()+" "+jobseeker.getLastname()+",</i><br/>";
				content+="<i>We are grateful inform you that you are approved to \""+job.getTitle()+"\" position for the contracted gross salary amount \""+employee.getSalary()+" "+employee.getCurrency()+"\" per year.</i><br/>";
				content+="<i>Besides, your account in Best Jobs has new features such as tracking your project assignments, filling your timesheets and performance evaluations during your career in our company. Please login with the same credentials and observe your status.</i><br/><br/>";
				content+="<i>Best Regards,</i><br/>";
				content+="<i>"+employer.getCompanyName()+" Human Resources Team </i><br/>";
				content+="<i><b>Contact Email : "+employer.getEmail()+" | Phone : "+employer.getPhone()+"</b><i><br/>";
			}
		}
		else if(applicationStatus.equals("Rejected")){
			subject="Regarding your Application to "+employer.getCompanyName()+" for "+job.getTitle()+" position is in BestJobs.";
			content="<i>Dear "+jobseeker.getFirstname()+" "+jobseeker.getLastname()+",</i><br/>";
			content+="<i>We have carefully reviewed your application to \""+job.getTitle()+"\" position and we decided to proceed further with another candidate.</i><br/>";
			content+="<i>Besides, we have recorded your profile for future positions that may arise.</i><br/><br/>";
			content+="<i>Best Regards,</i><br/>";
			content+="<i>"+employer.getCompanyName()+" Human Resources Team </i><br/>";
			content+="<i><b>Contact Email : "+employer.getEmail()+" | Phone : "+employer.getPhone()+"</b><i><br/>";
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
			request.setAttribute("mailresultmessage", resultMessage);
		}
		
		request.setAttribute("jobID",jobApp.getJobID());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/"+jspName);
		dispatcher.forward( request, response);
	}

}
