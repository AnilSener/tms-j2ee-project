<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Evaluation,model.Employer,model.Employee"%>
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
		Employer uname =(Employer)request.getSession().getAttribute("employerBean");
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	List<Evaluation> evaluationList = new ArrayList<Evaluation>();
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet(basePath+"/getAllEvaluationsByEmployer?eid="+uname.getEid()+"&status=");
	
	HttpResponse httpResponse = httpClient.execute(httpGet);
	HttpEntity httpEntity = httpResponse.getEntity();
	InputStream is = httpEntity.getContent();
	ObjectInputStream in = new ObjectInputStream(is);
	evaluationList=(List<Evaluation>) in.readObject();
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
	
	<h2>PERFORMANCE EVALUATIONS LIST</h2>	
	<table border="1">
	<tr>
	<th>Employee</th>
	<th>Start Date</th>
	<th>End Date</th>
	<th>Status</th>
	<th>Evaluator</th>
	<th>Creation Date</th>
	<th>Action</th>
	
	</tr>
	<% for(int i=0 ; i <evaluationList.size() ; i ++){ 
	
		DefaultHttpClient httpClient4 = new DefaultHttpClient();
	  	HttpGet httpGet4 = new HttpGet(basePath+"/getEmployeeByIDServlet?employeeID="+evaluationList.get(i).getEmployeeID());
	  	
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
		java.text.SimpleDateFormat sdfShort = new java.text.SimpleDateFormat("yyyy-MM-dd");
	
	if (evaluationList.get(i).getStatus().equals("New")){%>
		
		<tr style="background-color: #00FFFF;" onclick="window.location='evaluationdetail.jsp?id=<%=evaluationList.get(i).getId()%>&jspName=<%=jspName%>'">
	<%}
	else if (evaluationList.get(i).getStatus().equals("Submitted")){%>	
	<tr style="background-color: #FF0000;" onclick="window.location='evaluationdetail.jsp?id=<%=evaluationList.get(i).getId()%>&jspName=<%=jspName%>'">
	<%}
	else{%>
		<tr>
	<%}%>
			<td style="width: 120px;">
			
				<label><%=fullName%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=sdfShort.format(evaluationList.get(i).getStartDate())%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=sdfShort.format(evaluationList.get(i).getEndDate())%></label>
			</td>
			
			<td style="width: 80px;">
				<label><%=evaluationList.get(i).getStatus()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=fullName2%></label>
			</td>
			<td style="width: 120px;">
				<label><%=sdf.format(evaluationList.get(i).getCreatedDate())%></label>
			</td>
			
			<td style="width: 20px;">
				<input  type="button" class="listbutton"  value="Evaluation Details" onclick="window.location='evaluationdetail.jsp?id=<%=evaluationList.get(i).getId()%>&jspName=<%=jspName%>'"/>
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