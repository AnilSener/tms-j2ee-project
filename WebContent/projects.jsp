<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Project,model.Employer"%>
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
	List<Project> projectList = new ArrayList<Project>();
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet(basePath+"/getAllProjectsByEmployer?eid="+uname.getEid()+"&status=");
	
	HttpResponse httpResponse = httpClient.execute(httpGet);
	HttpEntity httpEntity = httpResponse.getEntity();
	InputStream is = httpEntity.getContent();
	ObjectInputStream in = new ObjectInputStream(is);
	projectList=(List<Project>) in.readObject();
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
	
	<h2>PROJECTS LIST</h2>	
	<table border="1">
	<tr>
	<th>Project Code</th>
	<th>Project Title</th>
	<th>Status</th>
	<th>Post Date</th>
	<th>Description</th>
	
	</tr>
	<% for(int i=0 ; i <projectList.size() ; i ++){ 
	
	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	
	if (projectList.get(i).getStatus().equals("Open")){%>
		
		<tr style="background-color: #00FF00;" onclick="window.location='projectdetail.jsp?code=<%=projectList.get(i).getCode()%>&fileName=<%=jspName%>'">
	<%}
	else if (projectList.get(i).getStatus().equals("Closed")){%>	
		<tr style="background-color: #FF0000;" onclick="window.location='projectdetail.jsp?code=<%=projectList.get(i).getCode()%>&fileName=<%=jspName%>'">
	<%}
	else{%>
		<tr>
	<%}%>
			<td style="width: 40px;">
			
				<label><%=projectList.get(i).getCode()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=projectList.get(i).getTitle()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=projectList.get(i).getStatus()%></label>
			</td>
			
			<td style="width: 80px;">
				<label><%=sdf.format(projectList.get(i).getPostDate())%></label>
			</td>
			<td style="width: 40px;">
				<label><%=projectList.get(i).getDescription()%></label>
			</td>
			
			<td style="width: 20px;">
				<input  type="button" class="listbutton"  value="Project Details" onclick="window.location='projectdetail.jsp?code=<%=projectList.get(i).getCode()%>&fileName=<%=jspName%>'"/>
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