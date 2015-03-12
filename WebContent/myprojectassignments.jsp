<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Employee,model.Assignment,model.Project"%>
<%@ page import="java.util.*"%>

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
	
	Employee uname=(Employee)request.getSession().getAttribute("employee");
	int employeeID=((Employee)uname).getId();
		
		
		
	
	List<Assignment> assignmentList = new ArrayList<Assignment>();
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet(basePath+"/getMyProjectAssignmentsServlet?employeeId="+employeeID);
	
	HttpResponse httpResponse = httpClient.execute(httpGet);
	HttpEntity httpEntity = httpResponse.getEntity();
	InputStream is = httpEntity.getContent();
	ObjectInputStream in = new ObjectInputStream(is);
	assignmentList=(List<Assignment>) in.readObject();
	httpClient.close();

	
	if(uname!=null){%>
	<b>You logged as <%=uname.getUsername()%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		
		<li><input type="button" onclick="location.href='employeehome.jsp' " value="Home"></li>
		<li><input type="button" onclick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onclick="location.href='savedetails.jsp' " value="Talent Profile"></li>
		<li><input type="button" onclick="location.href='myinterviewcalls.jsp' " value="Interview Calls"></li>
		<li><input type="button" onclick="location.href='jobseekerupdateinfo.jsp' " value="User Info"></li>
	</ul>
	</div>
	<div id="content">
	
	<h2>MY PROJECT ASSIGNMENTS</h2>	
	<table border="1">
	<tr>
	<th>Project Code</th>
	<th>Project Title</th>
	<th>Project Status</th>
	<th>Project Description</th>
	<th>Assignment Date</th>
	<th>Assignment Start Date</th>
	<th>Assignment End Date</th>
	<th>Goal</th>
	
	</tr>
	<% for(int i=0 ; i <assignmentList.size() ; i ++){ 
	
		List<Project> projectList = new ArrayList<Project>();
		
		DefaultHttpClient httpClient2 = new DefaultHttpClient();
		HttpGet httpGet2 = new HttpGet(basePath+"/getAllProjectsByEmployer?eid="+uname.getEid()+"&status=");
		
		HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
		HttpEntity httpEntity2 = httpResponse2.getEntity();
		InputStream is2 = httpEntity2.getContent();
		ObjectInputStream in2 = new ObjectInputStream(is2);
		projectList=(List<Project>) in2.readObject();
		httpClient2.close();	
		Project project=null;
		for (Project p:projectList){
			
			if(assignmentList.get(i).getProjectCode().equals(p.getCode()))
				project=new Project(p);
		}
		
	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
	java.util.Date now=new java.util.Date();
	
	if (assignmentList.get(i).getStartDate().compareTo(now)==1){%>
		
		<tr style="background-color: #00FF00;" >
	<%}
	else if ((assignmentList.get(i).getStartDate().compareTo(now)==-1 || assignmentList.get(i).getStartDate().compareTo(now)==0) && (assignmentList.get(i).getEndDate().compareTo(now)==1 || assignmentList.get(i).getEndDate().compareTo(now)==0)){%>	
		<tr style="background-color: #00FFFF;">
	<%}
	else if (assignmentList.get(i).getStartDate().compareTo(now)==-1 && assignmentList.get(i).getEndDate().compareTo(now)==-1){%>	
		<tr style="background-color: #FF0000;">
	<%}
	else{%>
		<tr>
	<%}%>
			<%if(project!=null){ %>
			<td style="width: 40px;">
			
				<label><%=project.getCode()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=project.getTitle()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=project.getStatus()%></label>
			</td>
			<td style="width: 120px;">
				<label><%=project.getDescription()%></label>
			</td>
			<%} %>
			<td style="width: 120px;">
				<label><%=sdf.format(assignmentList.get(i).getCreatedDate())%></label>
			</td>
			<td style="width: 120px;">
				<label><%=sdf.format(assignmentList.get(i).getStartDate())%></label>
			</td>
			<td style="width: 120px;">
				<label><%=sdf.format(assignmentList.get(i).getEndDate())%></label>
			</td>
			<td style="width: 80px;">
				<label><%=assignmentList.get(i).getGoal()%></label>
				<label><b>Hours</b></label>
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