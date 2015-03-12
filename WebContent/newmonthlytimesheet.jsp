<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,model.Skill,model.Employer,model.Assignment,model.Employee,model.JobSeeker" %>
  <%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>

<body><center> <div align="center">
<div id="top" >
	<%
	String registerMsg="";
	String error=(String)request.getAttribute("error");
	if (error!=null)
	registerMsg=error;
	
	Employer uname =(Employer)request.getSession().getAttribute("employerBean");
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	
	
	List<JobSeeker> employeeList = new ArrayList<JobSeeker>();

	//To retrieve all working employees
  	DefaultHttpClient httpClient = new DefaultHttpClient();
  	HttpGet httpGet = new HttpGet(basePath+"/queryTalentServlet?eid="+uname.getEid()+"&action=list&status=Working&isEmployee=true&firstName=&lastName=&supervisorid=0");
  	
  	HttpResponse httpResponse = httpClient.execute(httpGet);
  	HttpEntity httpEntity = httpResponse.getEntity();
  	InputStream is = httpEntity.getContent();
  	ObjectInputStream in = new ObjectInputStream(is);
  	employeeList=(List<JobSeeker>) in.readObject();
  	httpClient.close();
	
		
	%>
	
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
	<%if(uname!=null){%>
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
	
				          
					<form name="newtimesheetform" method="post" action="newTimesheet" id="newtimesheetform">
			
						<table>
							<tr>
								<td style="height: 20px;">Status:</td>
								<td style="text-align: left">
								
								<input class="loginform_text" name="status" id="status" type="text" value="New" readonly="readonly" style="width: 100px; height: 18px;"  />
								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Employee:</td>
								<td style="text-align: left">
								<select name="employeeID" id="employeeID">
									<option value="">Employee</option>
									<% 
									for (JobSeeker jobseeker:employeeList){
										//To retrieve as is employee object
									  	DefaultHttpClient httpClient4 = new DefaultHttpClient();
									  	HttpGet httpGet4 = new HttpGet(basePath+"/getEmployeeByJobSeekerIDServlet?jobSeekerID="+jobseeker.getId());
									  	
									  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
									  	HttpEntity httpEntity4 = httpResponse4.getEntity();
									  	InputStream is4 = httpEntity4.getContent();
									  	ObjectInputStream in4 = new ObjectInputStream(is4);
									  	Employee employee=(Employee) in4.readObject();
									  	httpClient4.close();
										String fullName=employee.getFirstname()+" "+employee.getLastname();
										%>
										<option value="<%=employee.getId()%>"><%=fullName%></option>
										<%}%>
								</select>
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Year:</td>
								<td style="text-align: left">
								
								<select name="year" id="year">
									<option value="">Year</option>
									<% Calendar now = Calendar.getInstance();
									int currYear = now.get(Calendar.YEAR);
									//int d=new java.util.Date().getYear();
									for (int i=currYear;i<currYear+5;i++){
										%>
										<option value="<%=i%>"><%=i%></option>
										<%}%>
									</select>
								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Month:</td>
								<td style="text-align: left">
								<select name="month" id="month">
									<option value="">Month</option>
									<% 
									String months[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
									for (int i=1;i<=12;i++){
										%>
										<option value="<%=i%>"><%=months[i-1]%></option>
										<%}%>
									</select>
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">
								
								</td>
								<td colspan="2" style="text-align: left">
								<input name="save" type="submit" id="save" value="New Timesheet" style="width: 120px; height: 20px;"/>
								</td>
							</tr>
						</table>
					</form>
					
				<label class="errorMessage"><%=registerMsg%></label>
	</div>
	
	</div>
	<div id="footer">Copyright DGNet LTD © 2014</div>
</div>


</center>
</body>
</html>