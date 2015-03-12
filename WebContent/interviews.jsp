<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="DAO.DataManager"%>
<%@ page import="java.net.HttpURLConnection,java.net.URL,java.io.*"%>
<%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair;"%>

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
		Employer uname =(Employer)request.getSession().getAttribute("employerBean");
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	List<Interview> interviewList = new ArrayList<Interview>();
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet(basePath+"/getAllInteviewsByEmployer?eid="+uname.getEid()+"&status=");
	
	HttpResponse httpResponse = httpClient.execute(httpGet);
	HttpEntity httpEntity = httpResponse.getEntity();
	InputStream is = httpEntity.getContent();
	ObjectInputStream in = new ObjectInputStream(is);
	interviewList=(List<Interview>) in.readObject();
	httpClient.close();

	
	String URI=request.getRequestURI();
	String jspName=URI.substring(URI.lastIndexOf("/")+1);
	
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
	
	<h2>INTERVIEWS LIST</h2>	
	<table border="1">
	<tr>
	<th>Job Title</th>
	<th>Interviewer</th>
	<th>Candidate</th>
	<th>Sequence No</th>
	<th>Job Post Status</th>
	<th>Job Post Date</th>
	<th>Interview Status</th>
	<th>Interview Date</th>
	<th>Application Status</th>
	
	<th>Location</th>
	<th>Action</th>
	</tr>
	<% for(int i=0 ; i <interviewList.size() ; i ++){ 
	

	DefaultHttpClient httpClient2 = new DefaultHttpClient();
	HttpGet httpGet2 = new HttpGet(basePath+"/getJobApplicationByJobAppID?jobAppID="+interviewList.get(i).getApplicationId());

	HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
	HttpEntity httpEntity2 = httpResponse2.getEntity();
	InputStream is2 = httpEntity2.getContent();
	ObjectInputStream in2 = new ObjectInputStream(is2);
	JobApplication jobApp=(JobApplication) in2.readObject();
	httpClient2.close();
	
	

	DefaultHttpClient httpClient3 = new DefaultHttpClient();
	HttpGet httpGet3 = new HttpGet(basePath+"/getJobDataByID?jid="+jobApp.getJobID());

	HttpResponse httpResponse3 = httpClient3.execute(httpGet3);
	HttpEntity httpEntity3 = httpResponse3.getEntity();
	InputStream is3 = httpEntity3.getContent();
	ObjectInputStream in3 = new ObjectInputStream(is3);
	Job job=(Job) in3.readObject();
	httpClient3.close();
	
	
	DefaultHttpClient httpClient4 = new DefaultHttpClient();
	HttpGet httpGet4 = new HttpGet(basePath+"/getJobSeekerByJobAppID?jobAppID="+interviewList.get(i).getApplicationId());

	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
	HttpEntity httpEntity4 = httpResponse4.getEntity();
	InputStream is4 = httpEntity4.getContent();
	ObjectInputStream in4 = new ObjectInputStream(is4);
	JobSeeker jobSeeker=(JobSeeker) in4.readObject();
	httpClient4.close();
	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	
	if (interviewList.get(i).getStatus().equals("Open")){%>
		
		<tr style="background-color: #00FFFF;" onclick="window.location='interviewdetail.jsp?interviewID=<%=interviewList.get(i).getId()%>&fileName=<%=jspName%>'">
	<%}
	else if (interviewList.get(i).getStatus().equals("Accepted")){%>	
		<tr style="background-color: #00FF00;" onclick="window.location='interviewdetail.jsp?interviewID=<%=interviewList.get(i).getId()%>&fileName=<%=jspName%>'">
	<%}
	else if (interviewList.get(i).getStatus().equals("Rejected")){%>	
		<tr style="background-color: #0000FF;" onclick="window.location='interviewdetail.jsp?interviewID=<%=interviewList.get(i).getId()%>&fileName=<%=jspName%>'">
	<%}
	else if (interviewList.get(i).getStatus().equals("Closed")){%>	
		<tr style="background-color: #FF0000;" onclick="window.location='interviewdetail.jsp?interviewID=<%=interviewList.get(i).getId()%>&fileName=<%=jspName%>'">
	<%}
	else{%>
		<tr>
	<%}%>
			<td style="width: 120px;">
			
				<label><%=job.getTitle()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=interviewList.get(i).getInterviewer()%></label>
			</td>
			<td style="width: 120px;">
				<label><%=jobSeeker.getFirstname()+" "+jobSeeker.getLastname()%></label>
			</td>
			<td style="width: 40px;">
				<label><%=interviewList.get(i).getSequenceNo()%></label>
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
			<td style="width: 120px;">
			
				<label><%=interviewList.get(i).getLocation()%></label>
			</td>
			<td style="width: 20px;">
				<input  type="button" class="listbutton"  value="Interview Details" onclick="window.location='interviewdetail.jsp?interviewID=<%=interviewList.get(i).getId()%>&fileName=<%=jspName%>'"/>
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