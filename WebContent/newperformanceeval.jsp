<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,model.Skill,model.Employer,model.Assignment,model.Employee,model.JobSeeker,model.Evaluation" %>
  <%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.11.1.js" type="text/javascript"></script>
<script>
/* Set days in month script
   Copyright (c) 2003-2006 Michel Plungjan "javascripts (a) planet.nl" */
// Change the values below if needed:
adjustYears=true; // fix the year drop down to be around now.
yearAdjustment=-2; // how many years prior to this year
numberOfYears = 5; // how many years in the dropdown

function setStartDate(theForm,id) {

  var year = theForm.year.options[theForm.year.selectedIndex].value;
  if (!year) return;
  var month = theForm.month.options[theForm.month.selectedIndex].value;
  if (!month) return;
  var d = new Date(year,month,0); // last day in the previous month because months start on 0
  var daysInMonth = d.getDate();
  theForm.day.options.length=29;// save the minimum days
  if (daysInMonth>28) for (var i=29;i<=daysInMonth;i++) {
    theForm.day.options.length++;
    theForm.day.options[theForm.day.options.length-1] = new Option(i,i);
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
	
function formatDate(theForm) {
	
	  var year = theForm.year.options[theForm.year.selectedIndex].value;
	  var month = theForm.month.options[theForm.month.selectedIndex].value;
	  if (month.length=1) month='0'+month; 
	  var day = theForm.day.options[theForm.day.selectedIndex].value;
	  if (day.length=1) day='0'+day;  
	  theForm.startDate.value=year+'-'+month+'-'+day;
	  
	 
	  var year = theForm.year2.options[theForm.year2.selectedIndex].value;
	  var month = theForm.month2.options[theForm.month2.selectedIndex].value;
	  if (month.length=1) month='0'+month; 
	  var day = theForm.day2.options[theForm.day2.selectedIndex].value;
	  if (day.length=1) day='0'+day; 
	 theForm.endDate.value=year+'-'+month+'-'+day;
	  
	  
}

/*function setNow() {
  var now = new Date();
  var ySel = document.forms[0].year;
  var mSel = document.forms[0].month;
  var dSel = document.forms[0].day;
  var hSel = document.forms[0].hour;
  var minSel = document.forms[0].min;

  var year = now.getFullYear();

  if (adjustYears) {
    var firstYear = year + yearAdjustment;
    ySel.options.length=1; // remove all but 1st option
    for (var i=firstYear, n=firstYear+numberOfYears;i<n;i++) {
      ySel.options.length++
      ySel.options[ySel.options.length-1] = new Option(i,i)
    }
  }
  var minYear = parseInt(ySel.options[1].value);
  var diff = year-minYear+1;
  ySel.selectedIndex=((diff)>0)?diff:0;

  mSel.selectedIndex = now.getMonth()+1;
  dSel.selectedIndex = now.getDate(); // NB: Not 0 based

  hSel.selectedIndex=now.getHours()+1;
  minSel.selectedIndex= now.getMinutes()+1;
  formatDate(document.forms[0]);
}*/
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
	
				          
					<form name="newevaluationform" method="post" action="newEvaluation" id="newevaluationform">
			
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
								<td style="height: 20px;">Start Date:</td>
								<td style="text-align: left">
								<input type="hidden" name="startDate" id="startDate"  value=""  style="width: 150px; height: 18px;" />	
								<select name="year" id="year" onChange="setStartDate(this.form,this.id)">
									<option value="">Year</option>
									<% Calendar now = Calendar.getInstance();
									int currYear = now.get(Calendar.YEAR);
									for (int i=currYear;i<currYear+5;i++){%>
										
										<option value="<%=i%>"><%=i%></option>
										<% 
									}%>
									</select>
									<select name="month" id="month" onChange="setStartDate(this.form,this.id)">
									<option value="">Month</option>
									<% 
									String months[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
									for (int i=1;i<=12;i++){
									
									%>
	
										<option value="<%=i%>"><%=months[i-1]%></option>
										<%
									}%>
									</select>
									<select name="day" id="day"><!-- Use all days to help NS4 -->
									<option value="">Day</option>
									<% for (int i=1;i<=31;i++){
									
									%>
										<option value="<%=i%>"><%=i%></option>
										<%
									}%>
									</select>
									
								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">End Date:</td>
								<td style="text-align: left">
								<input type="hidden" name="endDate" id="endDate"  value=""  style="width: 150px; height: 18px;" />	
									<select name="year2" onChange="setEndDate(this.form)">
									<option value="">Year</option>
									<% Calendar now2 = Calendar.getInstance();
									//int currYear = now2.get(Calendar.YEAR);
									
									for (int i=currYear;i<currYear+5;i++){
										
										%>
										<option value="<%=i%>"><%=i%></option>
										<%}	
									%>
									</select>
									<select name="month2" onChange="setEndDate(this.form)">
									<option value="">Month</option>
									<% 
									String months2[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
									for (int i=1;i<=12;i++){
									%>
										<option value="<%=i%>"><%=months2[i-1]%></option>
										<%}
									%>
									</select>
									<select name="day2"><!-- Use all days to help NS4 -->
									<option value="">Day</option>
									<% for (int i=1;i<=31;i++){
									%>
										<option value="<%=i%>"><%=i%></option>
										<%}
									
									%>
									</select>
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">
								
								</td>
								<td colspan="2" style="text-align: left">
								<input name="save" type="submit" id="save" value="New Evaluation" style="width: 120px; height: 20px;" onClick="formatDate(this.form);"/>
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