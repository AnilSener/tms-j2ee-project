<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="DAO.DataManager"%>
<%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body><center> <div align="center">
<div id="top" >
	
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
	<%
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));	
	JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
	int jobseekerID=0;
	boolean isEmployee=false;
	if (uname!=null){
		jobseekerID=uname.getId();
	}
	else{
		uname=(JobSeeker)request.getSession().getAttribute("employee");
		jobseekerID=((Employee)uname).getJobseekerId();
		isEmployee=true;
	}
		
		ServletContext ctx = getServletContext();
    	String dbURL = ctx.getInitParameter("dbURL");
    	String dbUserName = ctx.getInitParameter("dbUserName");
    	String dbPassword = ctx.getInitParameter("dbPassword");
    	DataManager dm = new DataManager();
    	dm.setDbURL(dbURL);
   		dm.setDbUserName(dbUserName);
    	dm.setDbPassword(dbPassword); 
	System.out.println("jobseekerID"+jobseekerID);
	List<Interview> interviewList = new ArrayList<Interview>();
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet(basePath+"/getMyInteviewCallsServlet?uid="+jobseekerID+"&status=");
	
	HttpResponse httpResponse = httpClient.execute(httpGet);
	HttpEntity httpEntity = httpResponse.getEntity();
	InputStream is = httpEntity.getContent();
	ObjectInputStream in = new ObjectInputStream(is);
	interviewList=(List<Interview>) in.readObject();
	httpClient.close();

	
	if(uname!=null){%>
	<b>You logged as <%=uname.getUsername()%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<%if(isEmployee){
		%>
		<li><input type="button" onclick="location.href='employeehome.jsp' " value="Home"></li>
		<%} 
		else{%>
		<li><input type="button" onclick="location.href='home.jsp' " value="Home"></li>
		<%} %>
		<li><input type="button" onclick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onclick="location.href='savedetails.jsp' " value="Talent Profile"></li>
		<li><input type="button" onclick="location.href='myinterviewcalls.jsp' " value="Interview Calls"></li>
		<li><input type="button" onclick="location.href='jobseekerupdateinfo.jsp' " value="User Info"></li>
	</ul>
	</div>
	<div id="content">
	
	<h2>MY INTERVIEW CALLS</h2>	
	<table border="1">
	<tr>
	<th>Job Title</th>
	<th>Company Name</th>
	<th>Interviewer</th>
	<th>Sequence No</th>
	<th>Contact Email</th>
	<th>Location</th>
	<th>Job Post Status</th>
	<th>Job Post Date</th>
	<th>Interview Status</th>
	<th>Interview Date</th>
	<th>Application Status</th>
	
	<th>Location</th>
	</tr>
	<% for(int i=0 ; i <interviewList.size() ; i ++){ 
	
	JobApplication jobApp=dm.getJobApplication(interviewList.get(i).getApplicationId());
	Job job=dm.getJobData(jobApp.getJobID());
	JobSeeker jobSeeker=dm.getJobSeekerByApplication(interviewList.get(i).getApplicationId());
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
	Employer emp=dm.getEmployerData(job.getEid());
	String URI=request.getRequestURI();
	String jspName=URI.substring(URI.lastIndexOf("/")+1);
	
	if (interviewList.get(i).getStatus().equals("Open") && job.getStatus().equals("Open")){%>
		
		<tr style="background-color: #00FFFF;" >
	<%}
	else if (interviewList.get(i).getStatus().equals("Accepted") && job.getStatus().equals("Open")){%>	
		<tr style="background-color: #00FF00;">
	<%}
	else if (interviewList.get(i).getStatus().equals("Rejected") && job.getStatus().equals("Open")){%>	
		<tr style="background-color: #0000FF;">
	<%}
	else if (interviewList.get(i).getStatus().equals("Closed")){%>	
		<tr style="background-color: #FF0000;">
	<%}
	else{%>
		<tr>
	<%}%>
			<td style="width: 120px;">
			
				<label><%=job.getTitle()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=emp.getCompanyName()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=interviewList.get(i).getInterviewer()%></label>
			</td>
			<td style="width: 40px;">
				<label><%=interviewList.get(i).getSequenceNo()%></label>
			</td>
			<td style="width: 120px;">
				<label><%=interviewList.get(i).getContactEmail()%></label>
			</td>
			<td style="width: 120px;">
				<label><%=interviewList.get(i).getLocation()%></label>
			</td>
			<td style="width: 120px;">
				<label><%=job.getStatus()%></label>
			</td>
			<td style="width: 80px;">
				<label><%=sdf.format(job.getPostDate())%></label>
			</td>
			<td style="width: 40px;">
				<label><%=interviewList.get(i).getStatus()%></label>
			</td>
			<td style="width: 120px;">
				<label><%=sdf.format(interviewList.get(i).getDate())%></label>
				
			</td>
			<td style="width: 40px;">
				<label><%=jobApp.getApplicationStatus()%></label>
			</td>
			<td style="width: 20px;">
			<form name="updateinterviewform" method="post" action="updateinterview" id="form">
			<input type="hidden" name="interviewID" value="<%=interviewList.get(i).getId()%>" />
			<input type="hidden" name="fileName" value="<%=jspName%>" />
			<input type="hidden" name="status" value="Accepted" />
			<%if (interviewList.get(i).getStatus().equals("Open") && (jobApp.getApplicationStatus().equals("In Progress") || jobApp.getApplicationStatus().equals("Open"))){%>
				<input  type="submit" class="listbutton"  value="Accept"/>
				<%}
			else{%>
				<input  type="submit" class="listbutton" value="Accept" disabled="disabled" />
			<%} %>
			</form>
			<form name="updateinterviewform" method="post" action="updateinterview" id="form">
			<input type="hidden" name="interviewID" value="<%=interviewList.get(i).getId()%>" />
			<input type="hidden" name="fileName" value="<%=jspName%>" />
			<input type="hidden" name="status" value="Rejected" />
			<%if (interviewList.get(i).getStatus().equals("Open") && (jobApp.getApplicationStatus().equals("In Progress") || jobApp.getApplicationStatus().equals("Open"))){%>
				<input  type="submit" class="listbutton"  value="Reject"/>
				<%}
			else{%>
				<input  type="submit" class="listbutton" value="Reject" disabled="disabled" />
			<%} %>
			</form>
			</td>
			
		</tr>
		
		<% } %>
	</table>	
	
	</div>
</div>
</div>

</center>
</body>
</html>