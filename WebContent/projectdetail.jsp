<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,model.Skill,model.Employer,model.Assignment,model.Employee,model.Project" %>
  <%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.11.1.js" type="text/javascript"></script>
<script>
$(document).ready(function() {
$("#save").click(function(){
	
	   $("#updateProjectForm").attr("action", "updateProject");
	   $("#updateProjectForm").submit();
});
	$("#chooseEmployee").click(function(){
		
	   $("#updateProjectForm").attr("action", "chooseEmployeeServlet");
	   $("#updateProjectForm").submit();
	});
});
</script>
<script>
/* Set days in month script
   Copyright (c) 2003-2006 Michel Plungjan "javascripts (a) planet.nl" */
// Change the values below if needed:
adjustYears=true; // fix the year drop down to be around now.
yearAdjustment=-2; // how many years prior to this year
numberOfYears = 5; // how many years in the dropdown

function setStartDate(theForm,id) {
	id=$("assignmentRow").selectedIndex;
  var year = theForm.year.options[theForm.year.selectedIndex].value;
  if (!year) return;
  var month = theForm.month.options[theForm.month.selectedIndex].value;
  if (!month) return;
  var d = new Date(year,month,0); // last day in the previous month because months start on 0
  var daysInMonth = d.getDate();
  theForm.day.options.length=29;// save the minimum days
  if (daysInMonth>28) for (var i=29;i<=daysInMonth;i++) {
    //theForm.day.options.length++;
    //theForm.day.options[theForm.day.options.length-1] = new Option(i,i);
    
    document.getElementByID('day'+id).options.length++;
    document.getElementByID('day'+id).options[document.getElementByID('day'+id).options.length-1] = new Option(i,i);
  }
}

function setEndDate(theForm) {
	  var year = theForm.year2.options[theForm.year2.selectedIndex].value;
	  if (!year) return;
	  var month = theForm.month2.options[theForm.month2.selectedIndex].value;
	  if (!month) return;
	  var d = new Date(year,month,0); // last day in the previous month because months start on 0
	  var daysInMonth = d.getDate();
	  theForm.day2.options.length=29;// save the minimum days
	  if (daysInMonth>28) for (var i=29;i<=daysInMonth;i++) {
	    theForm.day2.options.length++;
	    theForm.day2.options[theForm.day2.options.length-1] = new Option(i,i);
	  }
	}
	
</script>

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
	
	List<Assignment> selectedAssignments=new ArrayList<Assignment>();
	//selectedAssignments=(List<Assignment>)request.getSession().getAttribute("selectedAssignments");
	Project project=null;
	String code=request.getParameter("code");
	String title="";
	String description="";
	
	if(code!=null && !code.equals("")){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(basePath+"/getProjectDataByID?eid="+uname.getEid()+"&code="+code);
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream is = httpEntity.getContent();
		ObjectInputStream in = new ObjectInputStream(is);
		project=(Project) in.readObject();
		httpClient.close();
		
		selectedAssignments=project.getAssignments();
		code=project.getCode();
		title=project.getTitle();
		description=project.getDescription();
	}
	else if(request.getAttribute("project")!=null){
	project=(Project)request.getAttribute("project");
	selectedAssignments=project.getAssignments();
	code=project.getCode();
	title=project.getTitle();
	description=project.getDescription();
	}
	else if(request.getAttribute("assignmentList")!=null){
	selectedAssignments=(List<Assignment>)request.getAttribute("assignmentList");
	}
	
	
	//Used as an input to talent browsing
	String URI=request.getRequestURI();
	String jspName=URI.substring(URI.lastIndexOf("/")+1);
	
	
		
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
	
				          
					<form name="updateProjectForm" method="post" action="updateProject" id="updateProjectForm">
			
						<table>
							<tr>
								<td style="height: 20px;">Project Code:</td>
								<td style="text-align: left">
								
								<input class="loginform_text" name="code" id="code" type="text" value="<%=code%>" style="width: 50px; height: 18px;" onclick="this.value='';" />
								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Project Title:</td>
								<td style="text-align: left">
								
								<input class="loginform_text" name="title" id="title" type="text" value="<%=title%>" style="width: 100px; height: 18px;" onclick="this.value='';" />
								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Status:</td>
								<td style="text-align: left">
								
								<select name="status" id="status">
									<% 
									String statusTypes[]={"Open","Closed"};
									for (int i=0;i<statusTypes.length;i++){
										if(project!=null){ 
										if (statusTypes[i].equals(project.getStatus()))
										{
									%>
									<option value="<%=statusTypes[i]%>" selected="selected"><%=statusTypes[i]%></option>
										<%} 
										else{%>
										<option value="<%=statusTypes[i]%>"><%=statusTypes[i]%></option>
										<%}
										}
										else{%>
											<option value="<%=statusTypes[i]%>" disabled><%=statusTypes[i]%></option>
										<%}
									}%>
								</select>	
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;" valign="top" >Description:</td>
								<td style="text-align: left">
								
								<textarea name="description" id="description" cols="30" rows="10"  ><%=description%></textarea>
								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;" valign="top" >Assignments:<br/>
								
									
								
								
								</td>
								<td style="text-align: left">
								<table>
								<tr><th>Assignee Name</th><th>Start Date</th><th>End Date</th><th>Goal</th></tr>
								<%//Assignment assignment=null;
								int startDateDayOfMonth=0;
								int endDateDayOfMonth=0;
								java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
								String tempDate="";
								Employee employee=null;
								String fullName="";
								int counter=0;
								for (Assignment assignment:selectedAssignments){
								
								
								DefaultHttpClient httpClient4 = new DefaultHttpClient();
							  	HttpGet httpGet4 = new HttpGet(basePath+"/getEmployeeByIDServlet?employeeID="+assignment.getEmployeeId());
							  	
							  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
							  	HttpEntity httpEntity4 = httpResponse4.getEntity();
							  	InputStream is4 = httpEntity4.getContent();
							  	ObjectInputStream in4 = new ObjectInputStream(is4);
							  	employee=(Employee) in4.readObject();
							  	httpClient4.close();
							  	
							  	fullName=employee.getFirstname()+" "+employee.getLastname();
								if(assignment.getStartDate()!=null){
								  	tempDate=sdf.format(assignment.getStartDate());
								  	startDateDayOfMonth=Integer.parseInt(tempDate.substring(tempDate.lastIndexOf('-')+1,tempDate.indexOf(' ')));
								}
								if(assignment.getEndDate()!=null){
								  	tempDate=sdf.format(assignment.getEndDate());
								  	endDateDayOfMonth=Integer.parseInt(tempDate.substring(tempDate.lastIndexOf('-')+1,tempDate.indexOf(' ')));
								}
								
								%>
								<tr id="assignmentRow">
								<td>
								<input type="hidden" name="employeeID" id="employeeID"  value="<%=assignment.getEmployeeId()%>"  style="width: 150px; height: 18px;" />		
								<input class="loginform_text" name="employeeName" id="employeeName" type="text" value="<%=fullName%>" style="width: 100px; height: 18px;" readonly/></td>
								<td>						
								<select name="year" id="<%=counter%>" onChange="setStartDate(this.form,<%=counter%>)">
									<option value="">Year</option>
									<% Calendar now = Calendar.getInstance();
									int currYear = now.get(Calendar.YEAR);
									int d=new java.util.Date().getYear();
									for (int i=currYear;i<currYear+5;i++){
										if(assignment.getStartDate()!=null){ 
											if(d==assignment.getStartDate().getYear()){
									
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
									d++;
									}%>
									</select>
									<select name="month" id="<%=counter%>" onChange="setStartDate(this.form,<%=counter%>)">
									<option value="">Month</option>
									<% 
									String months[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
									for (int i=1;i<=12;i++){
										if(assignment.getStartDate()!=null){ 
											if(i==(assignment.getStartDate().getMonth()+1)){
									%>
									<option value="<%=i%>" selected="selected"><%=months[i-1]%></option>
										<%}
										else{%>
										<option value="<%=i%>"><%=months[i-1]%></option>
										<%}
											}
										else{%>
										<option value="<%=i%>"><%=months[i-1]%></option>
										<%}
									}%>
									</select>
									<select name="day" id="day<%=counter%>"><!-- Use all days to help NS4 -->
									<option value="">Day</option>
									<% for (int i=1;i<=31;i++){
										
									if(startDateDayOfMonth!=0){
											if (i==startDateDayOfMonth){
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
								</td>
								<td>
								<select name="year2" onChange="setEndDate(this.form)">
									<option value="">Year</option>
									<% Calendar now2 = Calendar.getInstance();
									int currYear2 = now2.get(Calendar.YEAR);
									int d2=new java.util.Date().getYear();
									for (int i=currYear2;i<currYear2+5;i++){
										if(assignment.getEndDate()!=null){ 
											if(d2==assignment.getEndDate().getYear()){
									
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
									d2++;
									}%>
									</select>
									<select name="month2" onChange="setEndDate(this.form)">
									<option value="">Month</option>
									<% 
									String months2[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
									for (int i=1;i<=12;i++){
										if(assignment.getStartDate()!=null){ 
											if(i==(assignment.getEndDate().getMonth()+1)){
									%>
									<option value="<%=i%>" selected="selected"><%=months2[i-1]%></option>
										<%}
										else{%>
										<option value="<%=i%>"><%=months2[i-1]%></option>
										<%}
											}
										else{%>
										<option value="<%=i%>"><%=months2[i-1]%></option>
										<%}
									}%>
									</select>
									<select name="day2"><!-- Use all days to help NS4 -->
									<option value="">Day</option>
									<% for (int i=1;i<=31;i++){
										
									if(endDateDayOfMonth!=0){
											if (i==endDateDayOfMonth){
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
									
								</td>
								<td>
								<input type="hidden" name="createdDate" id="createdDate" value="<%=sdf.format(assignment.getCreatedDate())%>"/>
								<input class="loginform_text" name="goal" id="goal" type="text" value="<%=assignment.getGoal()%>" style="width: 40px; height: 18px;" onclick="this.value='';" /><label><b>hours</b></label></td>
								</tr>
								<% counter++;
								} %>
								</table>
							
								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">
								<input name="save" type="submit" id="save" value="Update Project" style="width: 100px; height: 20px;"/>
								</td>
								<td colspan="2" style="text-align: left">
								<input type="hidden" name="jspName" value="<%=jspName %>"/>
								<input type="submit" id="chooseEmployee" value="Choose Employee!" name="chooseEmployee"  />
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