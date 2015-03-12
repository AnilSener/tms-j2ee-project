package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import model.Employee;
import model.Employer;
import model.Factor;
import model.Evaluation;
import DAO.DataManager;

@WebServlet("/updateEvaluation")
public class UpdateEvaluationServlet extends DBConnectionServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
	
	private DataManager dataManager;

	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateEvaluationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		dataManager=super.getDataManager();
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String status="";
		if (request.getParameter("Save")!=null)
		status= request.getParameter("status");
		else if (request.getParameter("Submit")!=null)
		status= "Submitted";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String notes= request.getParameter("notes");
		int employeeID =0;
		if(request.getParameter("employeeID")!=null)
		employeeID = Integer.parseInt(request.getParameter("employeeID"));
		Date startDate = null;
		Date endDate = null;
		if(request.getParameter("startDate")!=null){
			try {
				startDate=(Date)formatter.parse(request.getParameter("startDate"));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(request.getParameter("endDate")!=null){
			try {
				endDate=(Date)formatter.parse(request.getParameter("endDate"));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
		}
		String[] factorsArray=request.getParameterValues("factorName");
		String[] scoresArray=new String[factorsArray.length];
		for(int i=0;i<scoresArray.length;i++){
			
			scoresArray[i]=request.getParameter("scores"+i);
			
		}
		
		
		String[] commentsArray=request.getParameterValues("comment");
		
		Employee employee=dataManager.getEmployeeDataByEmployeeId(employeeID);
		//Employee uname =(Employee)request.getSession().getAttribute("employee");
		
		List<Factor> factorList=null;
		Evaluation evaluation=dataManager.getAllEvaluationByEmployeeID(employeeID,startDate,endDate);
		Evaluation evaluationUpdated=null;
		int overallScore=0;
		
		if(employeeID!=0 && startDate != null && endDate != null){
			
				
					
					valOK=true;
					factorList= dataManager.getAllFactorsByEvaluationID(evaluation.getId());
					
					
					
						for(int i=0;i<factorList.size();i++){
							if(i<factorList.size()-1){
								if(scoresArray[i]==null)
									continue;
								
							overallScore+=Integer.parseInt(scoresArray[i]);
							factorList.get(i).setScore(Integer.parseInt(scoresArray[i]));
							factorList.get(i).setComment(commentsArray[i]);
							}
							else{
							overallScore=Math.round(overallScore/i);
							factorList.get(i).setScore(overallScore);
							}
							System.out.println("Overall Score: "+overallScore);
							
							
						
						}
					
												
							evaluationUpdated=new Evaluation();
							evaluationUpdated.setId(evaluation.getId());
							evaluationUpdated.setCreatedDate(evaluation.getCreatedDate());
							evaluationUpdated.setEmployeeID(evaluation.getEmployeeID());
							evaluationUpdated.setStartDate(startDate);
							evaluationUpdated.setEndDate(endDate);
							evaluationUpdated.setSupervisorID(evaluation.getSupervisorID());
							evaluationUpdated.setStatus(status);
							evaluationUpdated.setFactors(factorList);
							evaluationUpdated.setNotes(notes);
							dbOK=dataManager.updateEvaluation(evaluationUpdated);
							
				
		}
		else{
			strError="Employee, Year and Month fields cannot be null.";	
			
			valOK=false;
			dbOK=false;
		}

		
		
		RequestDispatcher dispatcher;
		if (!dbOK || !valOK)
		{	
			
			request.setAttribute("error", strError);
			
		}
		else{
			
			Employer unamEmp =dataManager.getEmployerData(employee.getEid());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Employee supervisor=dataManager.getEmployeeDataByEmployeeId(employee.getSupervisorId());
			String[] scoreName={"1-Poor","2-Fair","3-Average","4-Good","5-Excellent"};
			String subject="";
			String content="";
			if(status.equals("Submitted")){
				subject="Performance Evaluation Feedback by "+unamEmp.getCompanyName()+" in BestJobs.";
				content="<i>Dear "+employee.getFirstname()+" "+employee.getLastname()+",</i><br/>";
				content+="<i>Since your activities in performance evaluation period between "+formatter.format(evaluation.getStartDate())+" and "+formatter.format(evaluation.getEndDate())+" are evaluated by your supervisor, "+supervisor.getFirstname()+" "+supervisor.getLastname()+" and your performance evaluation is submitted.</i><br/>";
				content+="<i>Consequently your \"Overall Performance Rating\" is confirmed as "+scoreName[overallScore-1]+". Please review the details of your performance evaluation provided by your workmate.</i><br/>";
				content+="<i>Best Regards,</i><br/>";
				content+="<i>"+unamEmp.getCompanyName()+" Human Resources Team </i><br/>";
				content+="<i><b>Contact Email : "+unamEmp.getEmail()+" | Phone : "+unamEmp.getPhone()+"</b><i><br/>";

			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("recipient",employee.getCompanyEmail()));
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
			strSuccess="You have successfully posted a project!";
			request.setAttribute("success", strSuccess);
			}
			
			request.setAttribute("id", evaluation.getId());
			dispatcher = request.getRequestDispatcher("/evaluationdetail.jsp");
			dispatcher.forward( request, response);
		
	}
	
	

}







