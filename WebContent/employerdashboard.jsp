<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.*"%>
<%@ page import="java.util.*"%>

<%@ page import="java.io.*"%>
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
		Employer uname =(Employer)request.getSession().getAttribute("employerBean");
		request.getSession().setAttribute("selectedJobSkillList",null);
		
    	String URI=request.getRequestURI();
    	String jspName=URI.substring(URI.lastIndexOf("/")+1);
    	
		
	List<Job> jobList = new ArrayList<Job>();
	
//jobList=dm.getAllJobsByEmployer(uname.getEid(),"");
DefaultHttpClient httpClient4 = new DefaultHttpClient();
	HttpGet httpGet4 = new HttpGet(basePath+"/getAllJobsByEmployer?eid="+uname.getEid()+"&status");
	
	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
	HttpEntity httpEntity4 = httpResponse4.getEntity();
	InputStream is4 = httpEntity4.getContent();
	ObjectInputStream in4 = new ObjectInputStream(is4);
	jobList=(List<Job>) in4.readObject();
	httpClient4.close();
	
	if(uname!=null){%>
		<b>You logged as <%=uname.getUsername()%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<li><input type="button" onclick="location.href='employerhome.jsp' " value="Home"></li>
		<li><input type="button" onclick="location.href='employerdashboard.jsp'" value="Dashboad"></li>
		<li><input type="button" onclick="location.href='searchtalent.jsp'" value="Browse Talent"></li>
		<li><input type="button" onclick="location.href='projects.jsp'" value="Projects"></li>
		<li><input type="button" onclick="location.href='interviews.jsp' " value="Interviews"></li>
		<li><input type="button" onclick="location.href='employerupdateinfo.jsp' " value="Company Info"></li>
	</ul>
	</div>
	<div id="content">
	
	<h2>POSTED JOBS LIST</h2>	
	<table border="1">
	<tr>
	<th>Job Title</th>
	<th>Sector Name</th>
	<th>Country</th>
	<th>City</th>
	<th>Required Experience</th>
	<th>Salary</th>
	<th>Post Date</th>
	<th>Post Status</th>
	<th>No of Applications</th>
	<th>Actions</th>
	</tr>
	<% for(int i=0 ; i <jobList.size() ; i ++){ 
	//Job Applications by Employer
	DefaultHttpClient httpClient8 = new DefaultHttpClient();
  	HttpGet httpGet8 = new HttpGet(basePath+"/getJobApplicationsByID?jid="+jobList.get(i).getJid());
  	
  	HttpResponse httpResponse8 = httpClient8.execute(httpGet8);
  	HttpEntity httpEntity8 = httpResponse8.getEntity();
  	InputStream is8 = httpEntity8.getContent();
  	ObjectInputStream in8 = new ObjectInputStream(is8);
  	List<JobApplication> jobApps=(List<JobApplication>) in8.readObject();
  	httpClient8.close();
  	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
	
	
	//To retrieve country value
  	
			DefaultHttpClient httpClient5 = new DefaultHttpClient();
			HttpGet httpGet5 = new HttpGet(basePath+"/getCountryDataByIDServlet?type=stream&countryID="+jobList.get(i).getCountryID());
			        	
			HttpResponse httpResponse5 = httpClient5.execute(httpGet5);
			HttpEntity httpEntity5 = httpResponse5.getEntity();
			InputStream is5 = httpEntity5.getContent();
			ObjectInputStream in5 = new ObjectInputStream(is5);
			Country jobCountry=(Country) in5.readObject();
			httpClient5.close();
			
			//To retrieve city value
		  	
			DefaultHttpClient httpClient6 = new DefaultHttpClient();
			HttpGet httpGet6 = new HttpGet(basePath+"/getCityDataByIDServlet?cityID="+jobList.get(i).getCityID());
			        	
			HttpResponse httpResponse6 = httpClient6.execute(httpGet6);
			HttpEntity httpEntity6 = httpResponse6.getEntity();
			InputStream is6 = httpEntity6.getContent();
			ObjectInputStream in6 = new ObjectInputStream(is6);
			City jobCity=(City) in6.readObject();
			httpClient6.close();
			
			//To retrieve sector value
		  	
			DefaultHttpClient httpClient7 = new DefaultHttpClient();
			HttpGet httpGet7 = new HttpGet(basePath+"/getSectorDataByID?sectorid="+jobList.get(i).getSectorID());
			        	
			HttpResponse httpResponse7 = httpClient7.execute(httpGet7);
			HttpEntity httpEntity7 = httpResponse7.getEntity();
			InputStream is7 = httpEntity7.getContent();
			ObjectInputStream in7 = new ObjectInputStream(is7);
			Sector jobSector=(Sector) in7.readObject();
			httpClient7.close();
			
	if (jobList.get(i).getStatus().equals("Open")){%>
		
		<tr style="background-color: #00FF00;" onclick="window.location='jobdetails.jsp?jobID=<%=jobList.get(i).getJid()%>'">
	<%}
	else if (jobList.get(i).getStatus().equals("Closed")){%>	
		<tr style="background-color: #FF0000;" onclick="window.location='jobdetails.jsp?jobID=<%=jobList.get(i).getJid()%>'">
	<%}
	else{%>
		<tr>
	<%}%>
			<td style="width: 120px;">
			
				<label><%=jobList.get(i).getTitle()%></label>
			</td>
			<td style="width: 120px;">
				<label><%=jobSector.getName()%></label>
			</td>
			<td style="width: 120px;">
				<label><%=jobCountry.getName()%></label>
			</td>
			<td style="width: 120px;">
				<label><%=jobCity.getName()%></label>
			</td>
			<td style="width: 80px;">
				<label><%=jobList.get(i).getExperience()%></label>
			</td>
			<td style="width: 80px;">
				<label><%=jobList.get(i).getSalary()%></label>
			</td>
			<td style="width: 110px;">
				<label><%=sdf.format(jobList.get(i).getPostDate())%></label>
			</td>
			<td style="width: 40px;">
				<label><%=jobList.get(i).getStatus()%></label>
			</td>
			<td style="width: 40px;">
				<label><%=jobApps.size()%></label>
				
			</td>
			<td style="width: 20px;">
				<input  type="button" class="listbutton" value="Job Details" onclick="window.location='jobdetails.jsp?jobID=<%=jobList.get(i).getJid()%>'"/>
				<form name="ViewApplicantsList" method="post" action="jobapplicants.jsp" >
				<% if (jobApps.size()>0){ %>
				<input  type="submit" class="listbutton" value="View Applicants"  />
				<%}
				else{%>
				<input  type="submit" class="listbutton" value="View Applicants"  disabled="disabled" />
				<%}%>
				<input type="hidden" name="jobID" value="<%=jobList.get(i).getJid() %>" />
				</form>
				
				<form name="CloseJob" method="post" action="updateJob" >
				<input type="hidden" name="fileName" value="<%=jspName%>" />
				<input type="hidden" name="jobID" value="<%=jobList.get(i).getJid() %>" />
				<input type="hidden" name="status" value="Closed" />
				<%if (jobList.get(i).getStatus().equals("Open")) {%>
				<input  type="submit" class="listbutton" value="Close Job" />
				<%}
				else{%>
				<input  type="submit" class="listbutton" value="Close Job" disabled="disabled" />
				<%}%>
				</form>
			
		</tr>
		
		<% } %>
	</table>	
	
	</div>
</div>
</div>

</center>
</body>
</html>