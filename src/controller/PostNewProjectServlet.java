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

import model.Assignment;
import model.Employee;
import model.Employer;
import model.JobSeeker;
import model.Project;
import DAO.DataManager;

@WebServlet("/newproject")
public class PostNewProjectServlet extends DBConnectionServlet {

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
    public PostNewProjectServlet() {
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
		
		String code = request.getParameter("code");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String status= request.getParameter("status");
		java.util.Date postDate=new java.util.Date();
		String[] employeeIDsArray=request.getParameterValues("employeeID");
		String[] startYearArray=request.getParameterValues("year");
		String[] startMonthArray=request.getParameterValues("month");
		String[] startDayArray=request.getParameterValues("day");
		String[] endYearArray=request.getParameterValues("year2");
		String[] endMonthArray=request.getParameterValues("month2");
		String[] endDayArray=request.getParameterValues("day2");
		String[] startDatesArray=null;
		String[] endDatesArray=null;
		if(request.getParameterValues("employeeID")!=null){
		startDatesArray=new String[employeeIDsArray.length];
		endDatesArray=new String[employeeIDsArray.length];
		}
		String[] goalsArray=request.getParameterValues("goal");
		//List<Assignment> assignmentIDList=(ArrayList<Assignment>) request.getSession().getAttribute("selectedJobSkillList");
		List<Assignment> assignmentList=null;
		Project project=null;
		
		
		Employer emp =(Employer)request.getSession().getAttribute("employerBean");
		
		if(!dataManager.getProjectDataByEmployer(code,emp.getEid()).getCode().equals(code)){
			
			assignmentList= new ArrayList<Assignment>();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (employeeIDsArray!=null){
				for (int i=0;i<employeeIDsArray.length;i++){
				    
					int employeeId=0;
					java.util.Date startDate = null;
					java.util.Date endDate = null;
					int goal=0;
					Assignment assignment=new Assignment();
					employeeId=Integer.parseInt(employeeIDsArray[i]);
					if (!startYearArray[i].equals("") && !startMonthArray[i].equals("") && !startDayArray[i].equals("") && !endYearArray[i].equals("") && !endMonthArray[i].equals("") && !endDayArray[i].equals("")){
						
						startDatesArray[i]=startYearArray[i]+"-"+startMonthArray[i]+"-"+startDayArray[i]+" 00:00:00";
						endDatesArray[i]=endYearArray[i]+"-"+endMonthArray[i]+"-"+endDayArray[i]+" 00:00:00";
						System.out.println("Start Date"+startDatesArray[i]);
						System.out.println("End Date"+endDatesArray[i]);
						
						
						try {
							startDate = (Date) formatter.parse(startDatesArray[i]);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							endDate = (Date) formatter.parse(endDatesArray[i]);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Calendar calStartDate = Calendar.getInstance();
				        calStartDate.setTime(startDate);
				        Calendar calEndDate = Calendar.getInstance();
				        calEndDate.setTime(endDate);
				        if (daysBetween(calStartDate, calEndDate)<1){
				        	strError="Assignment End Date should be at least 1 day later than Assignment Start Date.";							
							valOK=false;
				        }
				       
						
						goal=Integer.parseInt(goalsArray[i]);
						if(goal>0){
						valOK=true;
						}
						else{
							strError="Assignment Goal should be in terms of hours and more than 0.";							
							valOK=false;
						}
					}
					else{
						strError="Assignment Start and End Dates should be selected.";							
						valOK=false;
					}
						assignment.setEmployeeId(employeeId);
						assignment.setStartDate(startDate);
						assignment.setEndDate(endDate);
						assignment.setCreatedDate(new java.util.Date());
						assignment.setGoal(goal);
						assignmentList.add(assignment);
				
				}
			}
			
		}
		if((assignmentList==null || assignmentList!=null && valOK) && !code.equals("") && !title.equals("") && !description.equals("")){
									
						if(!dataManager.getProjectDataByEmployer(code,emp.getEid()).getCode().equals(code)){
						
											
											valOK=true;
											
											
											project = new Project();
											project.setCode(code);
											project.setEid(emp.getEid());
											project.setTitle(title);
											project.setDescription(description);
											project.setPostDate(postDate);
											project.setAssignments(assignmentList);
											project.setStatus(status);
								
											
											if(valOK && emp.getEid()!=0 && project!=null){
												dbOK=dataManager.saveNewProject(project);
												//request.getSession().removeAttribute("selectedJobSkillList");
											}
											else
											{
												System.out.println("Project cannot be created");	
											}
						}
						else{
							strError="There is already a project with a project code as \""+code+"\".";	
							
							valOK=false;
							dbOK=false;
						}
		
		} 
		else if(assignmentList!=null && valOK){
			strError="Project Code, Title and Description fields cannot be blank.";	
			
			valOK=false;
			dbOK=false;
		}
		

		
		
		RequestDispatcher dispatcher;
		if (!dbOK || !valOK)
		{	
			if(assignmentList!=null){
				request.setAttribute("assignmentList", assignmentList);
			}
			request.setAttribute("error", strError);
			dispatcher = request.getRequestDispatcher("/postnewproject.jsp");
		}
		else{
			
			Employer unamEmp =(Employer)request.getSession().getAttribute("employerBean");	
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			for (Assignment assignment:assignmentList){
				Employee employee=dataManager.getEmployeeDataByEmployeeId(assignment.getEmployeeId());
				Employee supervisor=dataManager.getEmployeeDataByEmployeeId(employee.getSupervisorId());
				
			String subject="Project Assigment Notification by "+unamEmp.getCompanyName()+" in BestJobs.";
			String content="<i>Dear "+employee.getFirstname()+" "+employee.getLastname()+",</i><br/>";
			content+="<i>You are assigned to "+project.getTitle()+" project on "+sdf.format(assignment.getCreatedDate())+".</i><br/>";
			content+="<i>You need to start working on  "+sdf.format(assignment.getStartDate())+" reporting your work to your supervisor \""+supervisor.getFirstname()+" "+supervisor.getLastname()+"\".</i><br/>";
			content+="<i>Your have "+assignment.getGoal()+" man/hours to finish your tasks according to the forecasted schedule. Your assignment period will be completed on "+assignment.getEndDate()+".</i><br/>";
			content+="<i>Project Details: "+project.getDescription()+".</i><br/><br/>";
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
			dispatcher = request.getRequestDispatcher("/projects.jsp");
		}
		
		
		dispatcher.forward( request, response);
		
	}
	
	public static long daysBetween(@NotNull final Calendar startDate, @NotNull final Calendar endDate) {

	    //Forwards or backwards?
	    final boolean forward = startDate.before(endDate);
	    // Which direction are we going
	    final int multiplier = forward ? 1 : -1;

	    // The date we are going to move.
	    final Calendar date = (Calendar) startDate.clone();

	    // Result
	    long daysBetween = 0;

	    // Start at millis (then bump up until we go back a day)
	    int fieldAccuracy = 4;
	    int field;
	    int dayBefore, dayAfter;
	    while (forward && date.before(endDate) || !forward && endDate.before(date)) {
	        // We start moving slowly if no change then we decrease accuracy.
	        switch (fieldAccuracy) {
	            case 4:
	                field = Calendar.MILLISECOND;
	                break;
	            case 3:
	                field = Calendar.SECOND;
	                break;
	            case 2:
	                field = Calendar.MINUTE;
	                break;
	            case 1:
	                field = Calendar.HOUR_OF_DAY;
	                break;
	            default:
	            case 0:
	                field = Calendar.DAY_OF_MONTH;
	                break;
	        }
	        // Get the day before we move the time, Change, then get the day after.
	        dayBefore = date.get(Calendar.DAY_OF_MONTH);
	        date.add(field, multiplier);
	        dayAfter = date.get(Calendar.DAY_OF_MONTH);

	        // This shifts lining up the dates, one field at a time.
	        if (dayBefore == dayAfter && date.get(field) == endDate.get(field))
	            fieldAccuracy--;
	        // If day has changed after moving at any accuracy level we bump the day counter.
	        if (dayBefore != dayAfter) {
	            daysBetween += multiplier;
	        }
	    }
	    return daysBetween;
	}

}



