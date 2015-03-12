<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*"%>

<%@ page import="java.util.*,java.io.FileNotFoundException"%>
<%@ page import="DAO.DataManager"%>
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
	String registerMsg="";
		String error=(String)request.getAttribute("error");
		String success=(String)request.getAttribute("success");
		String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
		if (success!=null)
			registerMsg=success;	
		else if (error!=null)
			registerMsg=error;
			
		Employer uname =(Employer)request.getSession().getAttribute("employerBean");
		
		int jobID=0;
		
		if(request.getAttribute("jobApplicationList")==null){ 
		if(request.getParameter("jobID")!=null)
			jobID=Integer.parseInt(request.getParameter("jobID"));
		else
			jobID=((Integer)request.getAttribute("jobID")).intValue();
		}	
		
    	String URI=request.getRequestURI();
    	String jspName=URI.substring(URI.lastIndexOf("/")+1);
	List<JobApplication> jobApplicationList = new ArrayList<JobApplication>();
	
	if(request.getAttribute("jobApplicationList")!=null)
	jobApplicationList =(List<JobApplication>)request.getAttribute("jobApplicationList");
	else{
	
	//To retrieve all matching job applications for a job post
  	DefaultHttpClient httpClient = new DefaultHttpClient();
  	HttpGet httpGet = new HttpGet(basePath+"/getJobApplicationsByMatchingSkillsServlet?jid="+jobID);
  	
  	HttpResponse httpResponse = httpClient.execute(httpGet);
  	HttpEntity httpEntity = httpResponse.getEntity();
  	InputStream is = httpEntity.getContent();
  	ObjectInputStream in = new ObjectInputStream(is);
  	jobApplicationList=(List<JobApplication>) in.readObject();
  	httpClient.close();
	
	}
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
	
		<form action="queryjobapplication" name="queryJobApplicationForm" method="get">
		<h2>SEARCH JOB APPLICATION BY...</h2>
		<table>
			<tr>
				<td style="height: 20px;">
				<label>Job Title:</label>
				</td>
				<td style="text-align: left">
				<select name="title" id="title" style="width: 120px; height: 18px;">
				<option value=""></option>
				<%
				
				//To retrieve all open job objects opened by an employer 
			  	DefaultHttpClient httpClient5 = new DefaultHttpClient();
			  	HttpGet httpGet5 = new HttpGet(basePath+"/getAllJobsByEmployer?eid="+uname.getEid()+"&status=Open");
			  	
			  	HttpResponse httpResponse5 = httpClient5.execute(httpGet5);
			  	HttpEntity httpEntity5 = httpResponse5.getEntity();
			  	InputStream is5 = httpEntity5.getContent();
			  	ObjectInputStream in5 = new ObjectInputStream(is5);
			  	List<Job> empJobList=(List<Job>) in5.readObject();
			  	httpClient5.close();
				
				for(Job job:empJobList){
				
				if (jobID==job.getJid() || (request.getAttribute("title")!=null && ((String)request.getAttribute("title")).equals(job.getTitle()))) {		
											%>
											<option value="<%=job.getTitle()%>" selected="selected"><%=job.getTitle()%></option>
											<%
											}else{
												%>
												<option value="<%=job.getTitle()%>"><%=job.getTitle()%></option>
												<%}
							
							} %>
				</select>
				</td>
				<td style="height: 20px;">
				<label>First Name:</label>
				</td>
				<td style="text-align: left">
				<input class="loginform_text" type="text" name="firstName" id="firstName" value="${param.firstName}" style="width: 120px; height: 18px;" onclick="this.value='';" />
				</td>
				<td style="height: 20px;">
				<label>Job Status:</label>
				</td>
				<td style="text-align: left">
				<select name="status" id="status" style="width: 120px; height: 18px;">
									<option value=""></option>
									<% 
									String statusTypes[]={"Open","Closed"};
									for (int i=0;i<statusTypes.length;i++){
										
										if(request.getAttribute("status")!=null){
											
											if (((String)request.getAttribute("status")).equals(statusTypes[i])) {		
											%>
											<option value="<%=statusTypes[i]%>" selected="selected"><%=statusTypes[i]%></option>
											<%
											}else{
												%>
												<option value="<%=statusTypes[i]%>"><%=statusTypes[i]%></option>
												<%
											}
										} 
										
										else{
											%>
											<option value="<%=statusTypes[i]%>"><%=statusTypes[i]%></option>
											<%
										}
									}
									%>
				</select>
				</td>
				<%if (uname!=null){%>
				<td style="height: 20px;">
				<label>Application Status:</label>
				</td>
				<td style="text-align: left">
				
				<select name="appStatus" id="appStatus" style="width: 120px; height: 18px;">
									<option value=""></option>
									<% 
									String appStatusTypes[]={"Applied","In Progress","Rejected","Hired"};
									for (int i=0;i<appStatusTypes.length;i++){
										
										if(request.getAttribute("appStatus")!=null){
											
											if (((String)request.getAttribute("appStatus")).equals(appStatusTypes[i])) {		
											%>
											<option value="<%=appStatusTypes[i]%>" selected="selected"><%=appStatusTypes[i]%></option>
											<%
											}else{
												%>
												<option value="<%=appStatusTypes[i]%>"><%=appStatusTypes[i]%></option>
												<%
											}
										} 
										
										else{
											%>
											<option value="<%=appStatusTypes[i]%>"><%=appStatusTypes[i]%></option>
											<%
										}
									}
									%>
				</select>
				
				
				</td>
				<%}%>
			</tr>
			<tr>
				
				<td style="height: 20px;">
				<label>Work Experience</label>
				</td>
				
				<td style="text-align: left">
				<select name="experience" id="experience" style="width: 80px; height: 18px;">
									<option value=""></option>
									<% 
									String experienceTypes[]={"0","0-1","1-3","3-5","5-10","10+"};
									for (int i=0;i<experienceTypes.length;i++){
										
										if(request.getAttribute("experience")!=null){
											
											if (((String)request.getAttribute("experience")).equals(experienceTypes[i])) {		
											%>
											<option value="<%=experienceTypes[i]%>" selected="selected"><%=experienceTypes[i]%></option>
											<%
											}else{
												%>
												<option value="<%=experienceTypes[i]%>"><%=experienceTypes[i]%></option>
												<%
											}
										} 
										
										else{
											%>
											<option value="<%=experienceTypes[i]%>"><%=experienceTypes[i]%></option>
											<%
										}
									}
									%>
				</select>
				</td>
				<td style="height: 20px;">
				<label>Last Name</label>
				</td>
				<td style="text-align: left">
				<input class="loginform_text" type="text" name="lastName" id="lastName" value="${param.lastName}" style="width: 120px; height: 18px;" onclick="this.value='';"><td style="text-align: left">
				
				</td>
				
				<td style="height: 20px;">
				</td>
				<td style="text-align: left">
				<td style="height: 20px;">
				<input type="submit" value="Search!" name="queryButton"/>
				</td>
			</tr>
		</table>
		</form>

	
	<h2>JOB APPLICATIONS</h2><label><%=registerMsg%></label>
	
	<table border="1">
	<tr>
	<th>Photo</th>
	<th>Candidate Full Name</th>
	<th>Job Title</th>
	<th>Application Date</th>
	<th>Application Status</th>
	<th>Work Experiences</th>
	<th>Education</th>
	<th>Communication Info</th>
	<th>No of Conducted<br/> Interviews</th>
	<th>CV</th>
	<th>Skills</th>
	<th>Action</th>
	</tr>
	<% 
	ArrayList<Integer> uniqueMatchingSkillsCount=new ArrayList<Integer>();
	for(int i=0 ; i <jobApplicationList.size() ; i++){ 
	
	
	
	
	//JobSeeker jobseeker=dm.getJobSeeker(jobApplicationList.get(i).getJobSeekerID());
	//To retrieve as is jobseeker object
  	DefaultHttpClient httpClient4 = new DefaultHttpClient();
  	HttpGet httpGet4 = new HttpGet(basePath+"/getJobSeekerByJobAppID?jobAppID="+jobApplicationList.get(i).getId());
  	
  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
  	HttpEntity httpEntity4 = httpResponse4.getEntity();
  	InputStream is4 = httpEntity4.getContent();
  	ObjectInputStream in4 = new ObjectInputStream(is4);
  	JobSeeker jobseeker=(JobSeeker) in4.readObject();
  	httpClient4.close();
	
  	//To retrieve as is job object
  	Job job=null;
	
	jobID=jobApplicationList.get(i).getJobID();
  	DefaultHttpClient httpClient3 = new DefaultHttpClient();
  	HttpGet httpGet3 = new HttpGet(basePath+"/getJobDataByID?jid="+jobApplicationList.get(i).getJobID());
  	
  	HttpResponse httpResponse3 = httpClient3.execute(httpGet3);
  	HttpEntity httpEntity3 = httpResponse3.getEntity();
  	InputStream is3 = httpEntity3.getContent();
  	ObjectInputStream in3 = new ObjectInputStream(is3);
  	job=(Job) in3.readObject();
  	httpClient3.close();
	
	
	//To retrieve as is profile object
  	DefaultHttpClient httpClient2 = new DefaultHttpClient();
  	HttpGet httpGet2 = new HttpGet(basePath+"/getProfileServlet?username="+jobseeker.getUsername());
  	
  	HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
  	HttpEntity httpEntity2 = httpResponse2.getEntity();
  	InputStream is2 = httpEntity2.getContent();
  	ObjectInputStream in2 = new ObjectInputStream(is2);
  	Profile profile=(Profile) in2.readObject();
  	httpClient2.close();
  	
	//To retrieve list of conducted interviews with the candidate for a particular job
	List<Interview> interviewList = new ArrayList<Interview>();
	
	DefaultHttpClient httpClient6 = new DefaultHttpClient();
	HttpGet httpGet6 = new HttpGet(basePath+"/getMyInteviewCallsServlet?uid="+jobseeker.getId()+"&status=");
	
	HttpResponse httpResponse6 = httpClient6.execute(httpGet6);
	HttpEntity httpEntity6 = httpResponse6.getEntity();
	InputStream is6 = httpEntity6.getContent();
	ObjectInputStream in6 = new ObjectInputStream(is6);
	interviewList=(List<Interview>) in6.readObject();
	httpClient6.close();
	
	int noOfConductedInterviews=0;
	boolean existsInterviewRequest=false;
	if(interviewList!=null){
		
	for (Interview interview:interviewList){
		if(interview.getJobId()==jobApplicationList.get(i).getJobID()){
			if (interview.getStatus().equals("Open") || interview.getStatus().equals("Accepted") || interview.getStatus().equals("Rejected"))
				existsInterviewRequest=true;
			else
				noOfConductedInterviews++;		
			}
		}
	}
		
	List<Skill> jobSeekerSkillList=profile.getSkills();
	List<Qualification> jobSeekerQualifications=profile.getQualifications();
	List<Experience> jobSeekerExperiences=profile.getExperiences();
	
	String CVHref="retrievefile?type=CV&contenttype=application/pdf&username="+jobseeker.getUsername();
	String imageHref="retrievefile?type=profile_image&contenttype=image/gif&username="+jobseeker.getUsername();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	if(request.getAttribute("jobApplicationList")==null){ 
	boolean isSkillCountAvail=false;
	int newMatchingSkillCount=jobApplicationList.get(i).getMatchingSkillCount();
	
		if (uniqueMatchingSkillsCount.size()==0)
			uniqueMatchingSkillsCount.add(new Integer(newMatchingSkillCount));
		
		else{
			
			for(int j=0 ; j <uniqueMatchingSkillsCount.size() ; j++){ 
			
				if (newMatchingSkillCount==uniqueMatchingSkillsCount.get(j).intValue())
				{
					isSkillCountAvail=true;
					break;
				}
			}
			
			if (isSkillCountAvail==false)
				uniqueMatchingSkillsCount.add(new Integer(newMatchingSkillCount));
		}
	
		if (isSkillCountAvail==false){%>
			<tr>
				<td>
					<strong><label style="background-color: #FF0000;" >Matching Skills: <%=newMatchingSkillCount%></label></strong>
				</td>
			</tr>
		<%}}
		
	if (jobApplicationList.get(i).getApplicationStatus().equals("Applied")){%>
		
		<tr>
	<%}
	else if (jobApplicationList.get(i).getApplicationStatus().equals("Rejected")){%>	
		<tr style="background-color: #FF0000;">
	<%}
	else if (jobApplicationList.get(i).getApplicationStatus().equals("In Progress")){%>
		<tr style="background-color: #FFFF00;">
	<%}
	else if (jobApplicationList.get(i).getApplicationStatus().equals("Accepted")){%>
		<tr style="background-color: #0000FF;">
	<%}
	else if (jobApplicationList.get(i).getApplicationStatus().equals("Hired")){%>
		<tr style="background-color: #00FF00;">
	<%}%>
			<td style="width: 80px;">
			<%try{ %>
			<img src="<%=imageHref%>" height="100" width="100" name="profileImage"/>
			<%}
			catch(FileNotFoundException ex){
			imageHref="images/default_profile.jpg";%>
			<img src="<%=imageHref%>" height="100" width="100" name="profileImage"/>
			<%}%>
			</td>

			<td style="width: 120px;">
			
				<%=jobseeker.getFirstname()+" "+jobseeker.getLastname() %>
			</td>
			
			<td style="width: 120px;">
			<%if(job!=null){ %>
			<p><label><%=job.getTitle()%></label></p>
			<%} %>	
			</td>
			
			<td style="width: 40px;">
				<%=sdf.format(jobApplicationList.get(i).getApplicationDate())%>
			</td>
			<td style="width: 40px;">
				<%=jobApplicationList.get(i).getApplicationStatus()%>
			</td>
			<td style="width: 120px;">
				<% 
				for(Experience exp:jobSeekerExperiences){
				%>
				<p><label><%=exp.getYears()+" : "+exp.getCompany()%></label></p>
				<%}
				%>	
			</td>
			<td style="width: 120px;">
				<% 
				for(Qualification qual:jobSeekerQualifications){
				%>
				<p><label>School Name: <%=qual.getSchoolName()%></label></p>	
				<p><label>Degree: <%=qual.getDegree()+" with CGPA "+qual.getGpa()%></label></p>	  
				<%}
				%>	
			</td>
			<td style="width: 80px;">
				<p><label>Email: <%=jobseeker.getEmail()%></label></p> 
				<p><label>Phone: <%=jobseeker.getPhone()%></label></p> 
				<p><label>Address: <%=jobseeker.getAddress()%></label></p> 

			</td>
			<td style="width: 80px;">
			<label><%=noOfConductedInterviews%></label>
			</td>
			<td style="width: 40px;" align="center" valign="middle" >
				<a href="<%=CVHref%>" name="CVFileLink" ><img src="images/CV_icon.png" height="20" width="20" /></a>
			</td>
			<td style="width: 120px;">
				<% 
				for(int k=0;k<jobSeekerSkillList.size();k++){
					
				%>
				<label><%=jobSeekerSkillList.get(k).getName()%></label>
				<% if (k%2==1){%>
				<p></p>
				<%}
				}
				%>	
				
			</td>
			<td style="width: 20px;">
				<form name="RejectApplication" method="post" action="updateJobApplication" >
				<% if (jobApplicationList.get(i).getApplicationStatus().equals("Applied") || jobApplicationList.get(i).getApplicationStatus().equals("In Progress")){ %>
				<input  type="hidden" name="applicationStatus" value="Rejected"/>
				<input  type="submit" class="listbutton" value="Reject" />
				<input type="hidden" name="applicationID" value="<%=jobApplicationList.get(i).getId()%>" />
				<input type="hidden" name="fileName" value="<%=jspName%>" />
				<%}
				else{%>
				<input  type="submit" class="listbutton" value="Reject" disabled="disabled" />
				<%}%>
				</form>
				<p></p>
				<form name="Invite" method="post" action="interviewdetail.jsp" >
				<% if (jobApplicationList.get(i).getApplicationStatus().equals("Applied") || !existsInterviewRequest){ %>
				<input  type="submit" class="listbutton" value="Invite" />
				<%}
				else{%>
				<input  type="submit" class="listbutton"  value="Invite" disabled="disabled" />
				<%}%>
				<input type="hidden" name="jobID" value="<%=jobID%>" />
				<input type="hidden" name="jobSeekerID" value="<%=jobseeker.getId()%>" />
				<input type="hidden" name="fileName" value="<%=jspName%>" />
				</form>
				<p></p>
				<form name="HireApplication" method="post" action="updateJobApplication" >
				<% if (jobApplicationList.get(i).getApplicationStatus().equals("Applied") || jobApplicationList.get(i).getApplicationStatus().equals("In Progress")){ %>
				<input  type="hidden" name="applicationStatus" value="Hired"/>
				<input  type="submit" class="listbutton"  value="Hire!" />
				<input type="hidden"  name="applicationID" value="<%=jobApplicationList.get(i).getId()%>" />
				<input type="hidden"  name="fileName" value="<%=jspName%>" />
				<%}
				else{%>
				<input  type="submit" class="listbutton" value="Hire!" disabled="disabled" />
				<%}%>
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