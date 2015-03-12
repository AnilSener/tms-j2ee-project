<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,model.Employer,model.Assignment,model.Employee,model.Timesheet,model.LineItem" %>
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
	
	Employee uname =(Employee)request.getSession().getAttribute("employee");
	Employer unameEmp=null;
	if(request.getSession().getAttribute("employee")==null)
		unameEmp =(Employer)request.getSession().getAttribute("employerBean");
	int timesheetID=0;
	
	if(request.getParameter("id")!=null)
		timesheetID=Integer.parseInt(request.getParameter("id"));
	else if(request.getAttribute("id")!=null)
		timesheetID=((Integer)request.getAttribute("id")).intValue();
	
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	
	Timesheet timesheet=null;
	if (timesheetID!=0){
		//Timesheet object
		DefaultHttpClient httpClient = new DefaultHttpClient();
	  	HttpGet httpGet = new HttpGet(basePath+"/getTimesheetDataByIDServlet?timesheetid="+timesheetID);
	  	
	  	HttpResponse httpResponse = httpClient.execute(httpGet);
	  	HttpEntity httpEntity = httpResponse.getEntity();
	  	InputStream is = httpEntity.getContent();
	  	ObjectInputStream in = new ObjectInputStream(is);
	  	timesheet=(Timesheet) in.readObject();
	  	httpClient.close();
		
	  	boolean isSupervisor=false;
	  	if(uname!=null && timesheet.getSupervisorID()==uname.getId())
	  		isSupervisor=true;
	  	boolean isEmployee=false;
	  	if(uname!=null && timesheet.getEmployeeID()==uname.getId())
	  		isEmployee=true;
	  	
	  	boolean timesheetReadOnly=false;
	  	if (isEmployee && (timesheet.getStatus().equals("Submitted") || timesheet.getStatus().equals("Approved"))  || isSupervisor || (!isSupervisor && !isEmployee))
	  		timesheetReadOnly=true;
	  	
	  	//Employee object
	  	DefaultHttpClient httpClient4 = new DefaultHttpClient();
	  	HttpGet httpGet4 = new HttpGet(basePath+"/getEmployeeByIDServlet?employeeID="+timesheet.getEmployeeID());
	  	
	  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
	  	HttpEntity httpEntity4 = httpResponse4.getEntity();
	  	InputStream is4 = httpEntity4.getContent();
	  	ObjectInputStream in4 = new ObjectInputStream(is4);
	  	Employee employee=(Employee) in4.readObject();
	  	httpClient4.close();
		String fullName=employee.getFirstname()+" "+employee.getLastname();
		
		//To retrieve reviewer/supervisor
		DefaultHttpClient httpClient3 = new DefaultHttpClient();
	  	HttpGet httpGet3 = new HttpGet(basePath+"/getEmployeeByIDServlet?employeeID="+timesheet.getSupervisorID());
	  	
	  	HttpResponse httpResponse3 = httpClient3.execute(httpGet3);
	  	HttpEntity httpEntity3 = httpResponse3.getEntity();
	  	InputStream is3 = httpEntity3.getContent();
	  	ObjectInputStream in3 = new ObjectInputStream(is3);
	  	Employee supervisor=(Employee) in3.readObject();
	  	httpClient3.close();
	  	String fullName2=supervisor.getFirstname()+" "+supervisor.getLastname();
	  	
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
		
	%>
	
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
	<%if(uname!=null){%>
<b>You logged as <%=uname.getUsername()%></b><% }
	else if(unameEmp!=null){%>
	<b>You logged as <%=unameEmp.getUsername()
%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<%if (unameEmp!=null){ %>
		<li><input type="button" onclick="location.href='employerhome.jsp' " value="Home"></li>
		<li><input type="button" onclick="location.href='employerdashboard.jsp'" value="Dashboad"></li>
		<li><input type="button" onclick="location.href='searchtalent.jsp'" value="Browse Talent"></li>
		<li><input type="button" onclick="location.href='projects.jsp'" value="Projects"></li>
		<li><input type="button" onclick="location.href='interviews.jsp' " value="Interviews"></li>
		<li><input type="button" onclick="location.href='employerupdateinfo.jsp' " value="Employer Info"></li>
	<%} 
	else if (uname!=null){%>
		<li><input type="button" onclick="location.href='employeehome.jsp' " value="Home"></li>
		<li><input type="button" onclick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onclick="location.href='savedetails.jsp' " value="Talent Profile"></li>
		<li><input type="button" onclick="location.href='myinterviewcalls.jsp' " value="Interview Calls"></li>
		<li><input type="button" onclick="location.href='jobseekerupdateinfo.jsp' " value="User Info"></li>
	<%}
	%>
	</ul>
	</div>
	<div id="content">
	
				          
					<form name="timesheetform" method="post" action="updateTimesheet" id="timesheetform">
			
						<table>
							<tr>
								<td style="height: 20px;">Status:</td>
								<td style="text-align: left">
								
								<input class="loginform_text" name="status" id="status" type="text"  value="<%=timesheet.getStatus()%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								
								</td>	
											
							</tr>
							<tr>
								<td style="height: 20px;">Created Date:</td>
								<td style="text-align: left">
								<input class="loginform_text" name="createdDate" id="createdDate" type="text"  value="<%=sdf.format(timesheet.getCreatedDate())%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								</td>
												
							</tr>
							<tr>
								<td style="height: 20px;">Employee:</td>
								<td style="text-align: left">
								
								<input class="loginform_text" name="employeeName" id="employeeName" type="text"  value="<%=fullName%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								<input  name="employeeID" id="employeeID" type="hidden"  value="<%=timesheet.getEmployeeID()%>"  style="width: 100px; height: 18px;"  />
						
								</td>
												
							</tr>
							<tr>
								<td style="height: 20px;">Reviewer:</td>
								<td style="text-align: left">
								
								<input class="loginform_text" name="supervisorName" id="supervisorName" type="text"  value="<%=fullName2%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								<input  name="supervisorID" id="supervisorID" type="hidden"  value="<%=timesheet.getSupervisorID()%>"  style="width: 100px; height: 18px;"  />
						
								</td>
												
							</tr>
							<tr>
								<td style="height: 20px;">Year:</td>
								<td style="text-align: left">
								<input class="loginform_text" name="year" id="year" type="text"  value="<%=timesheet.getYear()%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								</td>
												
							</tr>
							<tr>
								<td style="height: 20px;">Month:</td>
								<td style="text-align: left">
								<% 
									String months[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
										%>
								<input class="loginform_text" name="year" id="year" type="text"  value="<%=months[timesheet.getMonth()-1]%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								
								<input  name="month" id="month" type="hidden"  value="<%=timesheet.getMonth()%>"  style="width: 100px; height: 18px;"  />
								</td>
													
							</tr>
							<tr>
							<td style="height: 20px;"></td>
							<td style="text-align: left">
							<table id="lineItems" border=2>
								<tr><th>Date</th><th>Day of Week</th><th>Project Code</th><th>Work Hours</th><th>Description</th></tr>
								<%
								java.text.SimpleDateFormat sdfShort = new java.text.SimpleDateFormat("yyyy-MM-dd");
								String days[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
								
								List<Assignment> assignmentList = new ArrayList<Assignment>();
								
								DefaultHttpClient httpClient2 = new DefaultHttpClient();
								HttpGet httpGet2 = new HttpGet(basePath+"/getMyProjectAssignmentsServlet?employeeId="+employee.getId());
								
								HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
								HttpEntity httpEntity2 = httpResponse2.getEntity();
								InputStream is2 = httpEntity2.getContent();
								ObjectInputStream in2 = new ObjectInputStream(is2);
								assignmentList=(List<Assignment>) in2.readObject();
								httpClient2.close();
								
								for (LineItem item:timesheet.getLineItems()){
									
								%>
								<tr <%=item.getDay()==6 || item.getDay()==0?" style='background-color: #867970;'":"style='background-color: #EFECE5;'"%>  >
								<td>
								<label><b><%=sdfShort.format(item.getDate())%></b></label>
								<input  name="date" id="date" type="hidden"  value="<%=sdfShort.format(item.getDate())%>"  style="width: 100px; height: 18px;"  />
								</td>
								<td>
								<label><b><%=days[item.getDay()]%></b></label>
								<input  name="day" id="day" type="hidden"  value="<%=item.getDay()%>"  style="width: 100px; height: 18px;"/>
								</td>
								<td>
								<select name="projectCode" id="projectCode" <%=timesheetReadOnly?" disabled":""%>>
									<option value="">Code</option>
									<% for (Assignment assignment:assignmentList){
										if ((item.getDate().compareTo(assignment.getStartDate())==0 || item.getDate().compareTo(assignment.getStartDate())==1) && (item.getDate().compareTo(assignment.getEndDate())==0 || item.getDate().compareTo(assignment.getEndDate())==-1)){
									if(item!=null){
											if (assignment.getProjectCode().equals(item.getProjectCode())){
									%>
									<option value="<%=assignment.getProjectCode()%>" selected="selected"><%=assignment.getProjectCode()%></option>
										<%} 
										else{%>
										<option value="<%=assignment.getProjectCode()%>"><%=assignment.getProjectCode()%></option>
										<%}
										}
										
										}
									}%>
								</select>
								</td>
								<td>
								<select name="workHour" id="workHour" <%=timesheetReadOnly?" disabled":""%>>
									<option value="0">Hour</option>
									<% for (int i=1;i<=24;i++){
										
									if(item!=null){
											if (i==item.getWorkhours()){
									%>
									<option value="<%=i%>" selected="selected"><%=i%></option>
										<%} 
										else{%>
										<option value="<%=i%>"><%=i%></option>
										<%}
										}
										else{%>
										<option value="<%=i%>"><%=i%></option>
										<%}
									
									}%>
									</select>
									<label><b>hours</b></label>
								</td>
								<td>
								<input class="loginform_text" name="description" id="description" type="text" value="<%=item.getDescription()%>" style="width: 200px; height: 18px;" onclick="this.value='';" <%=timesheetReadOnly?" disabled":""%>/>
								</td>
								</tr>
								<% 
								} %>
								</table>
							
							</td>
							</tr>
							<tr>
								<td style="height: 20px;">
								<%if (isEmployee){ %>
								<input name="Save" type="submit" id="Save" value="Save" style="width: 120px; height: 20px;" <%=timesheetReadOnly?" disabled":""%>/>
								<%} else if(isSupervisor){ %>
								<input name="Approve" type="submit" id="Approve" value="Approve" style="width: 120px; height: 20px;" <%=!timesheet.getStatus().equals("Submitted") || isEmployee?" disabled":""%>/>
								<%} %>
								</td>
								<td style="text-align: left">
								<%if (isEmployee){ %>
								<input name="Submit" type="submit" id="Submit" value="Submit" style="width: 120px; height: 20px;" <%=timesheetReadOnly?" disabled":""%>/>
								<%} else if(isSupervisor){ %>
								<input name="Reject" type="submit" id="Reject" value="Reject" style="width: 120px; height: 20px;" <%=!timesheet.getStatus().equals("Submitted") || isEmployee?" disabled":""%>/>
								<%} %>
								</td>
								
							</tr>
							
						</table>
					</form>
			<%} %>		
				<label class="errorMessage"><%=registerMsg%></label>
	</div>
	
	</div>
	<div id="footer">Copyright DGNet LTD © 2014</div>
</div>


</center>
</body>
</html>