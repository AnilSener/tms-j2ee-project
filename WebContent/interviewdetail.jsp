<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,model.Job,model.JobApplication,model.Employer,model.Interview" %>
 <%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<script>
/* Set days in month script
   Copyright (c) 2003-2006 Michel Plungjan "javascripts (a) planet.nl" */
// Change the values below if needed:
adjustYears=true; // fix the year drop down to be around now.
yearAdjustment=-2; // how many years prior to this year
numberOfYears = 5; // how many years in the dropdown

function setDate(theForm) {
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
function formatDate(theForm) {
	
	  var year = theForm.year.options[theForm.year.selectedIndex].value;
	  var month = theForm.month.options[theForm.month.selectedIndex].value;
	  if (month.length=1) month='0'+month; 
	  var day = theForm.day.options[theForm.day.selectedIndex].value;
	  if (day.length=1) day='0'+day; 
	  var hour = theForm.hour.options[theForm.hour.selectedIndex].value;
	  if (hour.length=1) hour='0'+hour; 
	  var min = theForm.min.options[theForm.min.selectedIndex].value;
	  if (min.length=1) min='0'+min; 
	  theForm.interviewDate.value=year+'-'+month+'-'+day+' '+hour+':'+min+":00";
	}
function setNow() {
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
}
</script>

<body onload=""><center> <div align="center">
<div id="top" >
	<% 
	String registerMsg="";
		String error=(String)request.getAttribute("error");
		String success=(String)request.getAttribute("success");
		if (success!=null)
			registerMsg=success;	
		else if (error!=null)
			registerMsg=error;
	
	
	Employer unamEmp =(Employer)request.getSession().getAttribute("employerBean");	
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	int jobID=0;
	int jobSeekerID=0;
	int interviewID=0;
	try
	{
	if (request.getParameter("fileName").equals("interviews.jsp")){	
		interviewID=Integer.parseInt(request.getParameter("interviewID"));
		if (interviewID==0)
			interviewID=((Integer)request.getAttribute("interviewID")).intValue();
	}
	}
	catch(NullPointerException ex){
		jobID=Integer.parseInt(request.getParameter("jobID"));
		jobSeekerID=Integer.parseInt(request.getParameter("jobSeekerID"));
	}
	
	if (request.getParameter("fileName").equals("interviewdetail.jsp") && interviewID==0)
		interviewID=((Integer)request.getAttribute("interviewID")).intValue();
	
	if (request.getParameter("fileName").equals("jobapplicants.jsp")){
		jobID=Integer.parseInt(request.getParameter("jobID"));
		jobSeekerID=Integer.parseInt(request.getParameter("jobSeekerID"));
	}
	

	String URI=request.getRequestURI();
	String jspName=URI.substring(URI.lastIndexOf("/")+1);
	
	Job job=null;
	JobApplication jobApp=null;
	Interview interview=null;
	int jobAppID=0;
	int dayOfMonth=0;
	String formAction="";
	
	if (interviewID!=0){
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
  	HttpGet httpGet = new HttpGet(basePath+"/getInterviewDataByIDServlet?interviewid="+interviewID);
  	
  	HttpResponse httpResponse = httpClient.execute(httpGet);
  	HttpEntity httpEntity = httpResponse.getEntity();
  	InputStream is = httpEntity.getContent();
  	ObjectInputStream in = new ObjectInputStream(is);
  	interview=(Interview) in.readObject();
  	httpClient.close();
  	
	jobAppID=interview.getApplicationId();
	
	DefaultHttpClient httpClient2 = new DefaultHttpClient();
  	HttpGet httpGet2 = new HttpGet(basePath+"/getJobApplicationByJobAppID?jobAppID="+jobAppID);
  	
  	HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
  	HttpEntity httpEntity2 = httpResponse2.getEntity();
  	InputStream is2 = httpEntity2.getContent();
  	ObjectInputStream in2 = new ObjectInputStream(is2);
  	jobApp=(JobApplication) in2.readObject();
  	httpClient2.close();
  	
	jobID=jobApp.getJobID();
	jobSeekerID=jobApp.getJobSeekerID();
	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	String tempDate=sdf.format(interview.getDate());
	dayOfMonth=Integer.parseInt(tempDate.substring(tempDate.lastIndexOf('-')+1,tempDate.indexOf(' ')));
	formAction="updateinterview";
	
	}
	else{
	
	DefaultHttpClient httpClient4 = new DefaultHttpClient();
  	HttpGet httpGet4 = new HttpGet(basePath+"/getJobApplicationServlet?jobID="+jobID+"&jobseekerID="+jobSeekerID);
  	
  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
  	HttpEntity httpEntity4 = httpResponse4.getEntity();
  	InputStream is4 = httpEntity4.getContent();
  	ObjectInputStream in4 = new ObjectInputStream(is4);
  	jobApp=(JobApplication) in4.readObject();
  	httpClient4.close();
	formAction="newinterview";
	}			
	
	DefaultHttpClient httpClient3 = new DefaultHttpClient();
  	HttpGet httpGet3 = new HttpGet(basePath+"/getJobDataByID?jid="+jobID);
  	
  	HttpResponse httpResponse3 = httpClient3.execute(httpGet3);
  	HttpEntity httpEntity3 = httpResponse3.getEntity();
  	InputStream is3 = httpEntity3.getContent();
  	ObjectInputStream in3 = new ObjectInputStream(is3);
  	job=(Job) in3.readObject();
  	httpClient3.close();
  	
  	int sequenceNo=1;
	if (formAction.equals("newinterview")){
		
		DefaultHttpClient httpClient6 = new DefaultHttpClient();
		HttpGet httpGet6 = new HttpGet(basePath+"/getMyInteviewCallsServlet?uid="+jobSeekerID+"&status=");
		
		HttpResponse httpResponse6 = httpClient6.execute(httpGet6);
		HttpEntity httpEntity6 = httpResponse6.getEntity();
		InputStream is6 = httpEntity6.getContent();
		ObjectInputStream in6 = new ObjectInputStream(is6);
		List<Interview> interviewList=(List<Interview>) in6.readObject();
		httpClient6.close();
		if(interviewList!=null){
			
		for (Interview interviewRecord:interviewList){
			if(interviewRecord.getJobId()==jobID){
					sequenceNo++;		
				}
			}
		}
	}
	else{
		sequenceNo=interview.getSequenceNo();
	}
	
	%>
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
	<%if(unamEmp!=null){%>
<b>You logged as <%=unamEmp.getUsername()%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
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
			       
					<form name="interviewform" method="post" action="<%=formAction%>" id="form">
				
						<table>
							<tr>
								<td style="height: 20px;">Job Title:</td>
								<td style="text-align: left;" >
								<input class="loginform_text" name="title" id="title" type="text" value="<%=job.getTitle()%>" disabled="disabled" style="width: 150px; height: 18px;" />					
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Sequence No:</td>
								<td style="text-align: left;" >
								<input class="loginform_text" name="txtsequenceno" id="txtsequenceno" type="text" value="<%=sequenceNo%>" disabled="sequenceno" style="width: 150px; height: 18px;" />					
								<input type="hidden" name="sequenceno" id="sequenceno"  value="<%=sequenceNo%>"  style="width: 150px; height: 18px;" />	
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Interview Status:</td>
								<td style="text-align: left;">
								<select name="status" id="status">
									<% 
									String statusTypes[]={"Open","Accepted","Rejected","Closed"};
									for (int i=0;i<statusTypes.length;i++){
										if(interview!=null){ 
										if (statusTypes[i].equals(interview.getStatus()))
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
								<td style="height: 20px;">Interview Date:</td>
								<td style="text-align: left";>
								<input type="hidden" name="interviewDate" id="interviewDate"  value=""  style="width: 150px; height: 18px;" />							
								<select name="year" onChange="setDate(this.form)">
									<option value="">Year</option>
									<% Calendar now = Calendar.getInstance();
									int currYear = now.get(Calendar.YEAR);
									int d=new java.util.Date().getYear();
									for (int i=currYear;i<currYear+5;i++){
										if(interview!=null){ 
											if(d==interview.getDate().getYear()){
									
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
									<select name="month" onChange="setDate(this.form)">
									<option value="">Month</option>
									<% 
									String months[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
									for (int i=1;i<=12;i++){
										if(interview!=null){ 
											if(i==(interview.getDate().getMonth()+1)){
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
									<select name="day"><!-- Use all days to help NS4 -->
									<option value="">Day</option>
									<% for (int i=1;i<=31;i++){
										
									if(interview!=null){
											if (i==dayOfMonth){
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
									<select name="hour">
									<option value="">Hour</option>
									<% for (int i=0;i<24;i++){
										
									if(interview!=null){
											if (i==interview.getDate().getHours()){
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
									<select name="min">
									<option value="">Min</option>
									<% for (int i=0;i<60;i++){
										
									if(interview!=null){
											if (i==interview.getDate().getMinutes()){
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
							</tr>
							<tr>
								<td style="height: 20px;">Interviewer:</td>
								<td style="text-align: left;">
								<%if (interview==null){%>
								<input class="loginform_text" name="interviewer" id="interviewer" type="text" value="Name Surname"  onclick="this.value='';" style="width: 150px; height: 18px;" />					
								<%}
								else{%>
								<input class="loginform_text" name="interviewer" id="interviewer" type="text" value="<%=interview.getInterviewer()%>"  style="width: 150px; height: 18px;" />					
								<%}%>
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Location:</td>
								<td style="text-align: left;">
								<%if (interview==null){%>
								<input class="loginform_text" name="location" id="location" type="text" value="Interview Address"  onclick="this.value='';" style="width: 150px; height: 18px;" />					
								<%}
								else{%>
								<input class="loginform_text" name="location" id="location" type="text" value="<%=interview.getLocation()%>"  style="width: 150px; height: 18px;" />					
								<%}%>
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Contact Email:</td>
								<td style="text-align: left;">
								<%if (interview==null){%>
								<input class="loginform_text" name="contactEmail" id="contactEmail" type="text" value="<%=unamEmp.getEmail()%>"  style="width: 150px; height: 18px;" />					
								<%}
								else{%>
								<input class="loginform_text" name="contactEmail" id="contactEmail" type="text" value="<%=interview.getContactEmail()%>"  style="width: 150px; height: 18px;" />					
								<%}%>
								</td>				
							</tr>
							<%if(formAction.equals("updateinterview")){  %>
							<tr>
								<td style="height: 20px;">Result Code:</td>
								<td style="text-align: left;">
								<select name="resultcode" id="resultcode">
									<% 
									String resultCodes[]={"","Failed","On Hold","Success","To Be Hired"};
									for (int i=0;i<resultCodes.length;i++){
										if(interview!=null){ 
										if (resultCodes[i].equals(interview.getResultCode()))
										{
									%>
									<option value="<%=resultCodes[i]%>" selected="selected"><%=resultCodes[i]%></option>
										<%} 
										else{%>
										<option value="<%=resultCodes[i]%>"><%=resultCodes[i]%></option>
										<%}
										}
										
									}%>
								</select>								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Salary:</td>
								<td style="text-align: left;">
								<input class="loginform_text" name="salary" id="salary" type="text" value="<%=interview.getSalary()%>" onclick="this.value='';" style="width: 150px; height: 18px;" /><label><%=interview.getCurrency()%></label>					
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Interview Notes:</td>
								<td style="text-align: left;">
								<textarea name="notes" id="notes" cols="30" rows="10"><%=interview.getNotes()%></textarea>
								</td>				
							</tr>
							<%} %>
							<tr>
								<td style="height: 20px;">&nbsp;</td>
								<td colspan="2" style="text-align: left">
								<%if (interview!=null){%>
								<input type="hidden" name="interviewID" value="<%=interviewID%>" />
								<input type="hidden" name="fileName" value="<%=jspName%>" />
								<%}%>
								<input type="hidden" name="jobID" value="<%=jobID%>" />
								<input type="hidden" name="jobAppID" value="<%=jobApp.getId()%>" />
								<input type="hidden" name="jobSeekerID" value="<%=jobSeekerID%>" />
								
									<%if (interview!=null){%>
								<input name="save" type="submit" id="save" value="Update" style="width: 100px; height: 18px;" onClick="formatDate(this.form);">
								<%}
								else{%>
								<input name="save" type="submit" id="save" value="New Interview" style="width: 100px; height: 18px;" onClick="formatDate(this.form);">
								<%}%>
								</td>
							</tr>
						</table>
					</form>
				<label><%=registerMsg%></label>	
	</div>
	
	</div>
	<div id="footer">Copyright © 2014 ...</div>
</div>


</center>
</body>
</html>