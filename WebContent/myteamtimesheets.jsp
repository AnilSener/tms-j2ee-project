<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Timesheet,model.Employee"%>
<%@ page import="java.util.*"%>

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
		Employee uname =(Employee)request.getSession().getAttribute("employee");
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	List<Timesheet> timesheetList = new ArrayList<Timesheet>();
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet(basePath+"/getMyTeamsTimesheets?supervisorid="+uname.getId()+"&status=");
	
	HttpResponse httpResponse = httpClient.execute(httpGet);
	HttpEntity httpEntity = httpResponse.getEntity();
	InputStream is = httpEntity.getContent();
	ObjectInputStream in = new ObjectInputStream(is);
	timesheetList=(List<Timesheet>) in.readObject();
	httpClient.close();

	
	String URI=request.getRequestURI();
	String jspName=URI.substring(URI.lastIndexOf("/")+1);
	
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
	
	<h2>MY Teams TIMESHEETS LIST</h2>	
	<table border="1">
	<tr>
	<th>Employee</th>
	<th>Year</th>
	<th>Month</th>
	<th>Status</th>
	<th>Reviewer</th>
	<th>Creation Date</th>
	<th>Action</th>
	
	</tr>
	<% for(int i=0 ; i <timesheetList.size() ; i ++){ 
	
		DefaultHttpClient httpClient4 = new DefaultHttpClient();
	  	HttpGet httpGet4 = new HttpGet(basePath+"/getEmployeeByIDServlet?employeeID="+timesheetList.get(i).getEmployeeID());
	  	
	  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
	  	HttpEntity httpEntity4 = httpResponse4.getEntity();
	  	InputStream is4 = httpEntity4.getContent();
	  	ObjectInputStream in4 = new ObjectInputStream(is4);
	  	Employee employee=(Employee) in4.readObject();
	  	httpClient4.close();
		String fullName=employee.getFirstname()+" "+employee.getLastname();
		
		//To retrieve supervisor employee
		DefaultHttpClient httpClient3 = new DefaultHttpClient();
	  	HttpGet httpGet3 = new HttpGet(basePath+"/getEmployeeByIDServlet?employeeID="+employee.getSupervisorId());
	  	
	  	HttpResponse httpResponse3 = httpClient3.execute(httpGet3);
	  	HttpEntity httpEntity3 = httpResponse3.getEntity();
	  	InputStream is3 = httpEntity3.getContent();
	  	ObjectInputStream in3 = new ObjectInputStream(is3);
	  	Employee supervisor=(Employee) in3.readObject();
	  	httpClient3.close();
	  	String fullName2=supervisor.getFirstname()+" "+supervisor.getLastname();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	
	if (timesheetList.get(i).getStatus().equals("New")){%>
		
		<tr style="background-color: #00FFFF;" onclick="window.location='timesheetdetail.jsp?id=<%=timesheetList.get(i).getId()%>&jspName=<%=jspName%>'">
	<%}
	else if (timesheetList.get(i).getStatus().equals("Submitted")){%>	
	<tr style="background-color: #FFFF00;" onclick="window.location='timesheetdetail.jsp?id=<%=timesheetList.get(i).getId()%>&jspName=<%=jspName%>'">
	<%}
	else if (timesheetList.get(i).getStatus().equals("Approved")){%>	
	<tr style="background-color: #00FF00;" onclick="window.location='timesheetdetail.jsp?id=<%=timesheetList.get(i).getId()%>&jspName=<%=jspName%>'">
	<%}
	else if (timesheetList.get(i).getStatus().equals("Rejected")){%>	
		<tr style="background-color: #FF0000;" onclick="window.location='timesheetdetail.jsp?id=<%=timesheetList.get(i).getId()%>&jspName=<%=jspName%>'">
	<%}
	else{%>
		<tr>
	<%}%>
			<td style="width: 120px;">
			
				<label><%=fullName%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=timesheetList.get(i).getYear()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=timesheetList.get(i).getMonth()%></label>
			</td>
			
			<td style="width: 80px;">
				<label><%=timesheetList.get(i).getStatus()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=fullName2%></label>
			</td>
			<td style="width: 120px;">
				<label><%=sdf.format(timesheetList.get(i).getCreatedDate())%></label>
			</td>
			
			<td style="width: 20px;">
				<input  type="button" class="listbutton"  value="Timesheet Details" onclick="window.location='timesheetdetail.jsp?id=<%=timesheetList.get(i).getId()%>&jspName=<%=jspName%>'"/>
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