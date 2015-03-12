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

import com.sun.istack.internal.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import model.Employee;
import model.Employer;
import model.LineItem;
import model.Timesheet;
import DAO.DataManager;

@WebServlet("/newTimesheet")
public class NewTimesheetServlet extends DBConnectionServlet {

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
    public NewTimesheetServlet() {
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
		
		
		String status= request.getParameter("status");
		int employeeID =0;
		if(request.getParameter("employeeID")!=null)
		employeeID = Integer.parseInt(request.getParameter("employeeID"));
		int year =0;
		if (request.getParameter("year")!=null)
		year = Integer.parseInt(request.getParameter("year"));
		int month =0;
		if (request.getParameter("month")!=null)
		month = Integer.parseInt(request.getParameter("month"));
		
		Employee employee=dataManager.getEmployeeDataByEmployeeId(employeeID);
	
		List<LineItem> lineItemList=null;
		Timesheet timesheet=null;
		
		
		//Employer emp =(Employer)request.getSession().getAttribute("employerBean");
		
		if(employeeID!=0 && year!=0 && month!=0){
			
				if(dataManager.getTimesheetByEmployeeID(employeeID,year,month)!=null){
					
					valOK=true;
					
					lineItemList= new ArrayList<LineItem>();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String strDate=year+"-"+month+"-"+1;
					Date date = null;
					try {
						date = (Date) formatter.parse(strDate);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					Calendar calendar = Calendar.getInstance();  
				    calendar.setTime(date);  
		
				    calendar.add(Calendar.MONTH, 1);  
				    calendar.set(Calendar.DAY_OF_MONTH, 1);  
				    calendar.add(Calendar.DATE, -1);  
		
				    @SuppressWarnings("deprecation")
					int lastDayOfMonth = calendar.getTime().getDate(); 
				    System.out.println("Last Day"+lastDayOfMonth);
						       
							for(int i=1;i<=lastDayOfMonth;i++){	
								
								String strLineDate=year+"-"+month+"-"+i;
								Date lineDate = null;
								try {
									lineDate = (Date) formatter.parse(strLineDate);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							    
								LineItem lineItem=new LineItem();
								lineItem.setDate(lineDate);
								lineItem.setDay(lineDate.getDay());
								
								
								lineItemList.add(lineItem);
							}
							
							
							
							timesheet=new Timesheet();
							timesheet.setCreatedDate(new java.util.Date());
							timesheet.setEmployeeID(employeeID);
							timesheet.setMonth(month);
							timesheet.setYear(year);
							timesheet.setSupervisorID(employee.getSupervisorId());
							timesheet.setStatus(status);
							timesheet.setLineItems(lineItemList);
							
							if(timesheet!=null)
								dbOK=dataManager.saveNewTimesheet(timesheet);
							else
								System.out.println("Timesheet cannot be created");
				}
				else{
					strError="There is already a timesheet for "+employee.getFirstname()+" "+employee.getLastname()+" in "+month+"//"+year+".";	
					
					valOK=false;
					dbOK=false;
				}
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
			dispatcher = request.getRequestDispatcher("/newmonthlytimesheet.jsp");
		}
		else{
			
			Employer unamEmp =(Employer)request.getSession().getAttribute("employerBean");	
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Employee supervisor=dataManager.getEmployeeDataByEmployeeId(employee.getSupervisorId());
			
				
			String subject="Timesheet Assignment Notification by "+unamEmp.getCompanyName()+" in BestJobs.";
			String content="<i>Dear "+employee.getFirstname()+" "+employee.getLastname()+",</i><br/>";
			content+="<i>Your monthly timesheet for "+year+"/"+month+" is assigned to you on "+sdf.format(timesheet.getCreatedDate())+".</i><br/>";
			content+="<i>Please fill in your work hours using the project codes and submit the timesheet. Consequently your timesheet will be sent to your supervisor \""+supervisor.getFirstname()+" "+supervisor.getLastname()+"\" for approval.</i><br/>";
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
			dispatcher = request.getRequestDispatcher("/timesheets.jsp");

		dispatcher.forward( request, response);
		
	}
	
	

}








