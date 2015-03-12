<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Employer"%>
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
	String adminusername=(String)session.getAttribute("username");
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	List<Employer> employerList = new ArrayList<Employer>();
	boolean isActiveChecked=false;
	if(request.getAttribute("isActive")!=null)
		isActiveChecked=((Boolean)request.getAttribute("isActive")).booleanValue();
	
	if(request.getAttribute("employerList")==null){
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet(basePath+"/queryEmployerServlet?action=list&status=&isActive=&userName=&companyname=");
	
	HttpResponse httpResponse = httpClient.execute(httpGet);
	HttpEntity httpEntity = httpResponse.getEntity();
	InputStream is = httpEntity.getContent();
	ObjectInputStream in = new ObjectInputStream(is);
	employerList=(List<Employer>) in.readObject();
	httpClient.close();
	} 
	else
	employerList=(List<Employer>)request.getAttribute("employerList");
	

	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
	String URI=request.getRequestURI();
	String jspName=URI.substring(URI.lastIndexOf("/")+1);
	
	if(adminusername!=null){%>
	<b>You logged as <%=adminusername%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<li><input type="button" onclick="location.href='employersubscriptions.jsp' " value="Employers"></li>
		<li><input type="button" onclick="location.href='valuelists.jsp' " value="Value Lists"></li>
	</ul>
	</div>
	<div id="content">
	<form action="queryEmployerServlet" name="queryEmployerForm" method="get">
		<h2>SEARCH EMPLOYERS BY...</h2>
		<table>
			<tr>
				<td style="height: 20px;">
				<label>Company Name:</label>
				</td>
				<td style="text-align: left">
				<input class="loginform_text" type="text" name="companyname" id="companyname" value="${param.companyname}" style="width: 120px; height: 18px;" onclick="this.value='';" />
				</td>
				<td style="height: 20px;">
				<label>User Name:</label>
				</td>
				<td style="text-align: left">
				<input class="loginform_text" type="text" name="userName" id="userName" value="${param.userName}" style="width: 120px; height: 18px;" onclick="this.value='';" />
				</td>
				<td style="height: 20px;">
				<label>Is Active?:</label>
				</td>
				<td style="text-align: left">
				<input type="checkbox" name="isActive" id="isActive" value="true" <%=isActiveChecked?" checked":""%> />	
				</td>
				<td>
				<input type="hidden" name="action" value="search"/>
				<input type="submit" value="Search!" name="queryButton"/>
				</td>
			</tr>
			
		</table>
		</form>
		<table>
		<tr>
		<td>Monthly Job Posts By Employer Report</td>
		<td>
		<a href="retrieveEmployerReport" name="reportFileLink" ><img src="images/report.png" height="40" width="40" /></a>
		</td>
		</tr>
		</table>
		
	<h2>EMPLOYER SUBSCRIPTIONS</h2>	
	<table border="1">
	<tr>
	<th>Company Name</th>
	<th>User Name</th>
	<th>Phone Number</th>
	<th>Address</th>
	<th>Created Date</th>
	<th>Activation Date</th>
	<th>Status</th>
	<th>Action</th>
	
	</tr>
	<% for(int i=0 ; i <employerList.size() ; i ++){ %>


		<tr <%=employerList.get(i).isActive()?" style='background-color: #00FF00;' ":" style='background-color: #FF0000;' "%>>
	
			<td style="width: 120px;">
			
				<label><%=employerList.get(i).getCompanyName()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=employerList.get(i).getUsername()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=employerList.get(i).getPhone()%></label>
			</td>
			<td style="width: 120px;">
			
				<label><%=employerList.get(i).getAddress()%></label>
			</td>
			
			<td style="width: 120px;">
				<label><%=employerList.get(i).getCreatedDate()!=null?sdf.format(employerList.get(i).getCreatedDate()):""%></label>
			</td>
			<td style="width: 120px;">
				<label><%=employerList.get(i).getActivationDate()!=null?sdf.format(employerList.get(i).getActivationDate()):""%></label>
			</td>
			<td style="width: 60px;">
			
				<label><%=employerList.get(i).isActive()?"Active":"Inactive"%></label>
			</td>
			
			
			<td style="width: 20px;">
				<form name="EmployeeActivationForm" method="post" action="employerActivation" >
				<% if (employerList.get(i).isActive()){%>
				<input  type="submit" class="listbutton" name="Activate" value="Activate" disabled="disabled"/>
				<input  type="submit" class="listbutton" name="Deactivate" value="Deactivate"  />
				<%}
				else{%>	
				<input  type="submit" class="listbutton" name="Activate" value="Activate" />
				<input  type="submit" class="listbutton" name="Deactivate" value="Deactivate" disabled="disabled" />
				<%}%>	
				<input type="hidden" name="employerID" value="<%=employerList.get(i).getEid()%>" />
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