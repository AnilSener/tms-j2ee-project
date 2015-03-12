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
import model.LineItem;
import model.Timesheet;
import DAO.DataManager;

@WebServlet("/updateTimesheet")
public class UpdateTimesheetServlet extends DBConnectionServlet {

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
    public UpdateTimesheetServlet() {
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
		else if (request.getParameter("Approve")!=null)
		status= "Approved";
		else if (request.getParameter("Reject")!=null)
		status= "Rejected";
		
		int employeeID =0;
		if(request.getParameter("employeeID")!=null)
		employeeID = Integer.parseInt(request.getParameter("employeeID"));
		int year =0;
		if (request.getParameter("year")!=null)
		year = Integer.parseInt(request.getParameter("year"));
		int month =0;
		if (request.getParameter("month")!=null)
		month = Integer.parseInt(request.getParameter("month"));
		String[] datesArray=request.getParameterValues("date");
		String[] daysArray=request.getParameterValues("day");
		String[] projectCodesArray=request.getParameterValues("projectCode");
		String[] workHoursArray=request.getParameterValues("workHour");
		String[] descriptionsArray=request.getParameterValues("description");
		
		Employee employee=dataManager.getEmployeeDataByEmployeeId(employeeID);
		Employee uname =(Employee)request.getSession().getAttribute("employee");
		
		List<LineItem> lineItemList=null;
		Timesheet timesheet=dataManager.getTimesheetByEmployeeID(employeeID,year,month);
		Timesheet timesheetUpdated=null;
		
		if(employeeID!=0 && year!=0 && month!=0){
			
				
					
					valOK=true;
					lineItemList= dataManager.getAllLineItemsByTimesheetID(timesheet.getId());
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					
					if(uname.getId()==timesheet.getEmployeeID()){
						for(int i=0;i<lineItemList.size();i++){
							try {
								lineItemList.get(i).setDate((Date) formatter.parse(datesArray[i]));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							lineItemList.get(i).setDay(Integer.parseInt(daysArray[i]));
							lineItemList.get(i).setDescription(descriptionsArray[i]);
							lineItemList.get(i).setProjectCode(projectCodesArray[i]);
							lineItemList.get(i).setWorkhours(Integer.parseInt(workHoursArray[i]));
						}
					}
												
							timesheetUpdated=new Timesheet();
							timesheetUpdated.setId(timesheet.getId());
							timesheetUpdated.setCreatedDate(timesheet.getCreatedDate());
							timesheetUpdated.setEmployeeID(timesheet.getEmployeeID());
							timesheetUpdated.setMonth(timesheet.getMonth());
							timesheetUpdated.setYear(timesheet.getYear());
							timesheetUpdated.setSupervisorID(timesheet.getSupervisorID());
							timesheetUpdated.setStatus(status);
							timesheetUpdated.setLineItems(lineItemList);
							dbOK=dataManager.updateTimesheet(timesheetUpdated);
							
				
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
			String email="";
			String subject="";
			String content="";
			if(status.equals("Submitted")){
				subject="Timesheet Submission Notification by "+unamEmp.getCompanyName()+" in BestJobs.";
				content="<i>Dear "+supervisor.getFirstname()+" "+supervisor.getLastname()+",</i><br/>";
				content+="<i>"+employee.getFirstname()+" "+employee.getLastname()+" who is actively working in your team, has submitted the timesheet for "+timesheetUpdated.getYear()+"/"+timesheetUpdated.getMonth()+".</i><br/>";
				content+="<i>Please review the details provided by your workmate and approve or reject it based on the input provided.</i><br/>";
				content+="<i>Best Regards,</i><br/>";
				content+="<i>"+unamEmp.getCompanyName()+" Human Resources Team </i><br/>";
				content+="<i><b>Contact Email : "+unamEmp.getEmail()+" | Phone : "+unamEmp.getPhone()+"</b><i><br/>";
			email=supervisor.getCompanyEmail();
			}
			else if(status.equals("Rejected")){
			
				subject="Timesheet Rejection Notification by "+unamEmp.getCompanyName()+" in BestJobs.";
				content="<i>Dear "+employee.getFirstname()+" "+employee.getLastname()+",</i><br/>";
				content+="<i>The monthly timesheet that you have submitted for "+timesheetUpdated.getYear()+"/"+timesheetUpdated.getMonth()+" is rejected by your supervisor "+supervisor.getFirstname()+" "+supervisor.getLastname()+".</i><br/>";
				content+="<i>Please refill and  your work hours using the project codes based on your actual work activities and resubmit the timesheet to your supervisor for approval.</i><br/>";
				content+="<i>Best Regards,</i><br/>";
				content+="<i>"+unamEmp.getCompanyName()+" Human Resources Team </i><br/>";
				content+="<i><b>Contact Email : "+unamEmp.getEmail()+" | Phone : "+unamEmp.getPhone()+"</b><i><br/>";
			email=employee.getCompanyEmail();
			}
			else if(status.equals("Approved")){
				
				subject="Timesheet Approval Notification by "+unamEmp.getCompanyName()+" in BestJobs.";
				content="<i>Dear "+employee.getFirstname()+" "+employee.getLastname()+",</i><br/>";
				content+="<i>The monthly timesheet that you have submitted for "+timesheetUpdated.getYear()+"/"+timesheetUpdated.getMonth()+" is approved by your supervisor "+supervisor.getFirstname()+" "+supervisor.getLastname()+".</i><br/>";
				content+="<i>Thank you very much for reporting your  work activities.</i><br/>";
				content+="<i>Best Regards,</i><br/>";
				content+="<i>"+unamEmp.getCompanyName()+" Human Resources Team </i><br/>";
				content+="<i><b>Contact Email : "+unamEmp.getEmail()+" | Phone : "+unamEmp.getPhone()+"</b><i><br/>";
				email=employee.getCompanyEmail();
			}
			
			
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
			strSuccess="You have successfully posted a project!";
			request.setAttribute("success", strSuccess);
			}
			
			request.setAttribute("id", timesheet.getId());
			dispatcher = request.getRequestDispatcher("/timesheetdetail.jsp");
			dispatcher.forward( request, response);
		
	}
	
	

}













