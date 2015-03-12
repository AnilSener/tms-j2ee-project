<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.JobSeeker,model.Employee"%>
<%@ page import="model.Job,model.City,model.Country,model.Sector,model.JobApplication" %>
<%@ page import="DAO.DataManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.11.1.js" type="text/javascript"></script>
<script type="text/javascript" src="js/getfieldsbycountry.js"></script>
</head>
<body onLoad=""><center> <div align="center">
<div id="top" >
	
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
	<% 
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
	int jobseekerID=0;
	boolean isEmployee=false;
	if (uname!=null){
		jobseekerID=uname.getId();
	}
	else if(request.getSession().getAttribute("employee")!=null){
		uname=(JobSeeker)request.getSession().getAttribute("employee");
		jobseekerID=((Employee)uname).getJobseekerId();
		isEmployee=true;
	}
	
	
	List<Job> jobList = new ArrayList<Job>();
	if(request.getAttribute("jobList")!=null)
		jobList =(List<Job>)request.getAttribute("jobList");
	else{
	DefaultHttpClient httpClient8 = new DefaultHttpClient();
	HttpGet httpGet8 = new HttpGet(basePath+"/getAllJobsByStatusServlet?status=");
	
	HttpResponse httpResponse8 = httpClient8.execute(httpGet8);
	HttpEntity httpEntity8 = httpResponse8.getEntity();
	InputStream is8 = httpEntity8.getContent();
	ObjectInputStream in8 = new ObjectInputStream(is8);
	jobList=(List<Job>) in8.readObject();
	httpClient8.close();
	}
	
	//To retrieve all sector values
	 List<Sector> sectorList = new ArrayList<Sector>();
  	DefaultHttpClient httpClient = new DefaultHttpClient();
  	HttpGet httpGet = new HttpGet(basePath+"/getValueListServlet?type=sector");
  	
  	HttpResponse httpResponse = httpClient.execute(httpGet);
  	HttpEntity httpEntity = httpResponse.getEntity();
  	InputStream is = httpEntity.getContent();
  	ObjectInputStream in = new ObjectInputStream(is);
  	sectorList=(List<Sector>) in.readObject();
  	httpClient.close();
  	
  //To retrieve all country values
  	List<Country> countryList = new ArrayList<Country>();
	        	
	DefaultHttpClient httpClient2 = new DefaultHttpClient();
	HttpGet httpGet2 = new HttpGet(basePath+"/getValueListServlet?type=country");
	        	
	HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
	HttpEntity httpEntity2 = httpResponse2.getEntity();
	InputStream is2 = httpEntity2.getContent();
	ObjectInputStream in2 = new ObjectInputStream(is2);
	countryList=(List<Country>) in2.readObject();
	httpClient2.close();
	           
    
   //To retrieve all city values
     	 List<City> cityList = new ArrayList<City>();
     	DefaultHttpClient httpClient3 = new DefaultHttpClient();
    	HttpGet httpGet3 = new HttpGet(basePath+"/getValueListServlet?type=city");
    	        	
    	HttpResponse httpResponse3 = httpClient3.execute(httpGet3);
    	HttpEntity httpEntity3 = httpResponse3.getEntity();
    	InputStream is3 = httpEntity3.getContent();
    	ObjectInputStream in3 = new ObjectInputStream(is3);
    	cityList=(List<City>) in3.readObject();
    	httpClient3.close();
    	           
    	
	%>
		<%
		try{
		if (uname!=null){%>
		<b>You logged as <%=uname.getUsername()%></b><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
		<% }
		else {%>
		<a href="login.jsp" style=" padding-right: 20px; padding-top:20px"> <b>Login as a Job Seeker/Employee</b></a><br/>	
		<a href="employerlogin.jsp" style=" padding-right: 20px;"> <b>Login as an Employer</b></a>
		<% } 
		}
		catch(NullPointerException ex){
		%>
		<a href="login.jsp" style=" padding-right: 20px; padding-top:20px"> <b>Login as a Job Seeker/Employee</b></a><br/>	
		<a href="employerlogin.jsp" style=" padding-right: 20px;"> <b>Login as an Employer</b></a>
			<%
		}%>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<%if (uname!=null){
				if(isEmployee){
			%>
			<li><input type="button" onclick="location.href='employeehome.jsp' " value="Home"></li>
			<%} 
			else{%>
			<li><input type="button" onclick="location.href='home.jsp' " value="Home"></li>
			<%} %>
		<li><input type="button" onclick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onclick="location.href='savedetails.jsp' " value="Talent Profile"></li>
		<li><input type="button" onclick="location.href='myinterviewcalls.jsp' " value="Interview Calls"></li>
		<li><input type="button" onclick="location.href='jobseekerupdateinfo.jsp' " value="User Info"></li>
		<% } 
		else {%>
		<li><input type="button" onClick="location.href='index.jsp' " value="Home"></li>
		<li><input type="button" onClick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onClick="location.href='jobseekersignup.jsp'" value="Register as JobSeeker "></li>
		<li><input type="button" onClick="location.href='employersignup.jsp' " value="Register as Employer"></li>
		<li><input type="button" onclick="location.href='aboutbestjobs.jsp' " value="About Best Jobs"></li>
		<% } %>
	</ul>
	</div>
	<div id="content">
		
		<form action="queryjob" name="queryJobForm" method="get">
		<h2>SEARCH BY...</h2>
		<table>
			<tr>
				<td style="height: 20px;">
				<label>Keyword</label>				</td>
				<td style="text-align: left">
				<input class="loginform_text" type="text" name="keyword" id="keyword" value="${param.keyword}" style="width: 120px; height: 18px;" onClick="this.value='';" />				</td>
				<td style="height: 20px;">
				<label>Sector</label>				</td>
				<td style="text-align: left">
				<select name="sectorID" id="sectorID" style="width: 150px; height: 18px;">
								<option value="0"></option>
								<%
								for (Sector sector:sectorList){	
									if(request.getAttribute("sectorID")!=null){
										
										if(((Integer)request.getAttribute("sectorID")).intValue()==sector.getId()){
								%>
								<option value="<%=sector.getId()%>" selected="selected"><%=sector.getName()%></option>
								<%
										}
										else{
											%>
											<option value="<%=sector.getId()%>"><%=sector.getName()%></option>
											<%	
										}
									}
									else{	
									%>
									<option value="<%=sector.getId()%>"><%=sector.getName()%></option>
									<%}
								}
									
								%>
				</select>				</td>
				<td style="height: 20px;">
				<label>Job Status</label>				</td>
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
				<td style="height: 20px;">
				<label>Company</label>				</td>
				<td style="text-align: left">
				<input class="loginform_text" type="text" name="companyname" id="companyname" value="${param.companyname}" style="width: 120px; height: 18px;" onClick="this.value='';" />				
				</td>
			</tr>
			<tr>
				
				<td style="height: 20px;">
				<label>Experience</label>				</td>
				
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
				</select>				</td>
				<td style="height: 20px;">
				<label>Country</label>				
				</td>
				<td style="text-align: left">
				<select name="countryID" id="countryID" style="width: 150px; height: 18px;">
								<option value="0"></option>
								<%
								for (Country country:countryList){	
									if(request.getAttribute("countryID")!=null){
										
										if(((Integer)request.getAttribute("countryID")).intValue()==country.getId()){
								%>
								<option value="<%=country.getId()%>" selected="selected"><%=country.getName()%></option>
								<%
										}
										else{
											%>
											<option value="<%=country.getId()%>"><%=country.getName()%></option>
											<%	
										}
									}
									else{	
									%>
									<option value="<%=country.getId()%>"><%=country.getName()%></option>
									<%}
								}
									
								%>
				</select>				
				</td>
				<td style="height: 20px;">
				<label>City</label>				
				</td>
				<td style="text-align: left">
				<select name="cityID" id="cityID" style="width: 150px; height: 18px;">
								<option value="0"></option>
								<%
								for (City city:cityList){	
									if(request.getAttribute("cityID")!=null){
										
										if(((Integer)request.getAttribute("cityID")).intValue()==city.getId()){
								%>
								<option value="<%=city.getId()%>" selected="selected"><%=city.getName()%></option>
								<%
										}
										else{
											%>
											<option value="<%=city.getId()%>"><%=city.getName()%></option>
											<%	
										}
									}
									else{	
									%>
									<option value="<%=city.getId()%>"><%=city.getName()%></option>
									<%}
								}
									
								%>
				</select>				
				</td>
				
				<%try{
					if (uname!=null){
				
					
				%>
				<td style="height: 20px;">
				<label>Application Status</label>				</td>
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
				<input type="hidden" name="jobSeekerID" value="<%=jobseekerID%>" />				
				</td>
				<%}
				}catch(NullPointerException ex){
					
					
				}%>
				<td style="height: 20px;">
				<input type="submit" value="Search!" name="queryButton"/>				
				</td>
			</tr>
		</table>
		</form>
	

	<h2>JOB LIST</h2>
	<form action="applyJob" method="POST">
	<table border="1" style="overflow-x: auto;">
	<tr>
	<th>Job Title</th>
	<th>Sector Name</th>
	<th>Company Name</th>
	<th>Country</th>
	<th>City</th>
	<th>Required Experience</th>
	<th>Salary</th>
	<th>Post Date</th>
	<th>Post Status</th>
	<%if (uname!=null){%>
	<th>Application Status</th>
	<th>Application Date</th>
	<%}%>
	<th>Action</th>
	</tr>
	<% for(int i=0 ; i <jobList.size() ; i ++){ 
	
	JobApplication jobApp=null;
	String jobAppStatus="";
	String jobAppDate="";
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
	String jobPostDate=sdf.format(jobList.get(i).getPostDate());
	
		try{
			if (uname!=null){
				//jobApp=dm.getJobApplication(uname.getId(),jobList.get(i).getJid());
				DefaultHttpClient httpClient4 = new DefaultHttpClient();
			  	HttpGet httpGet4 = new HttpGet(basePath+"/getJobApplicationServlet?jobID="+jobList.get(i).getJid()+"&jobseekerID="+jobseekerID);
			  	
			  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
			  	HttpEntity httpEntity4 = httpResponse4.getEntity();
			  	InputStream is4 = httpEntity4.getContent();
			  	ObjectInputStream in4 = new ObjectInputStream(is4);
			  	jobApp=(JobApplication) in4.readObject();
			  	httpClient4.close();
			  
			  	
				if(jobApp!=null){
				
				jobAppStatus=jobApp.getApplicationStatus();
				jobAppDate=sdf.format(jobApp.getApplicationDate());
				}
			}
		}
		catch(NullPointerException ex){
		jobAppStatus="";
		jobAppDate="";
		}
		//To retrieve country value
	  	
		DefaultHttpClient httpClient5 = new DefaultHttpClient();
		HttpGet httpGet5 = new HttpGet(basePath+"/getCountryDataByIDServlet?type=stream&countryID="+jobList.get(i).getCountryID());
		        	
		HttpResponse httpResponse5 = httpClient5.execute(httpGet5);
		HttpEntity httpEntity5 = httpResponse5.getEntity();
		InputStream is5 = httpEntity5.getContent();
		ObjectInputStream in5 = new ObjectInputStream(is5);
		Country jobCountry=(Country) in5.readObject();
		httpClient5.close();
		
		//To retrieve city value
	  	
		DefaultHttpClient httpClient6 = new DefaultHttpClient();
		HttpGet httpGet6 = new HttpGet(basePath+"/getCityDataByIDServlet?cityID="+jobList.get(i).getCityID());
		        	
		HttpResponse httpResponse6 = httpClient6.execute(httpGet6);
		HttpEntity httpEntity6 = httpResponse6.getEntity();
		InputStream is6 = httpEntity6.getContent();
		ObjectInputStream in6 = new ObjectInputStream(is6);
		City jobCity=(City) in6.readObject();
		httpClient6.close();
		
		//To retrieve sector value
	  	
		DefaultHttpClient httpClient7 = new DefaultHttpClient();
		HttpGet httpGet7 = new HttpGet(basePath+"/getSectorDataByID?sectorid="+jobList.get(i).getSectorID());
		        	
		HttpResponse httpResponse7 = httpClient7.execute(httpGet7);
		HttpEntity httpEntity7 = httpResponse7.getEntity();
		InputStream is7 = httpEntity7.getContent();
		ObjectInputStream in7 = new ObjectInputStream(is7);
		Sector jobSector=(Sector) in7.readObject();
		httpClient7.close();
	if (jobApp==null  && !jobList.get(i).getStatus().equals("Closed")){
		
	%>	
		<tr style="background-color: #00FF00;" onClick="window.location='jobdetails.jsp?jobID=<%=jobList.get(i).getJid()%>'">
		<%}
	else if (jobApp==null  && jobList.get(i).getStatus().equals("Closed")){%>
		<tr style="background-color: #FF0000;" onClick="window.location='jobdetails.jsp?jobID=<%=jobList.get(i).getJid()%>'">
	<%}
	else if (jobList.get(i).getJid()==jobApp.getJobID()  && jobAppStatus.equals("Applied") && !jobList.get(i).getStatus().equals("Closed")){%>
		<tr style="background-color: #00FF00;" onClick="window.location='jobdetails.jsp?jobID=<%=jobList.get(i).getJid()%>'">
	<%}
	else if (jobList.get(i).getJid()==jobApp.getJobID()  && jobAppStatus.equals("Rejected") || jobList.get(i).getStatus().equals("Closed")){
	%>	
		<tr style="background-color: #FF0000;" onClick="window.location='jobdetails.jsp?jobID=<%=jobList.get(i).getJid()%>'">
	<%}
	else{%>	
		<tr  onClick="window.location='jobdetails.jsp?jobID=<%=jobList.get(i).getJid()%>'">
	<%}%>
			<td style="width: 120px;">
			
				<label><%=jobList.get(i).getTitle()%></label>			</td>
			<td style="width: 120px;">
				<label><%=jobSector.getName()%></label>			</td>
			<td style="width: 120px;">
				<label><%=jobList.get(i).getCompanyTitle()%></label>			</td>
			<td style="width: 120px;">
				<label><%=jobCountry.getName()%></label>			</td>
			<td style="width: 120px;">
				<label><%=jobCity.getName()%></label>			</td>
			<td style="width: 80px;">
				<label><%=jobList.get(i).getExperience()%></label>			</td>
			<td style="width: 80px;">
				<label><%=jobList.get(i).getSalary()%></label>			</td>
			<td style="width: 80px;">
				<label><%=jobPostDate%></label>			</td>
			<td style="width: 40px;">
				<label><%=jobList.get(i).getStatus()%></label>			</td>
		<%if (uname!=null){%>
			<td style="width: 120px;">
				<label><%=jobAppStatus%></label>			</td>
			<td style="width: 80px;">
				<label><%=jobAppDate%></label>			</td>
			<% } %>
			<td style="width: 20px;">
				<input  type="button" class="listbutton" value="Job Details" onClick="window.location='jobdetails.jsp?jobID=<%=jobList.get(i).getJid()%>'"/>			</td>
		<%if (uname!=null){%>
			<td style="width: 20px;">
				<form name="ApplyJobForm" method="post" action="applyJob" >
				<% if (jobAppStatus.equals("") && jobList.get(i).getStatus().equals("Open")){%>
				<input  type="submit" class="listbutton" value="Apply" />
				<%}
				else{%>	
				<input  type="submit" class="listbutton" value="Apply" disabled="disabled" />
				<%}%>	
				<input type="hidden" name="jobID" value="<%out.print( jobList.get(i).getJid() );%>" />
				</form>
			</td>
		<% } %>
		</tr>
		
		<% } %>
	</table>	
		
	</form>
	</div>
</div>
</div>

</center>
</body>
</html>