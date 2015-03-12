<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*"%>

<%@ page import="java.util.*"%>

 <%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.11.1.js" type="text/javascript"></script>
<script type="text/javascript" src="js/getfieldsbycountry.js"></script>
</head>
<script type="text/javascript">
function storeJobFields(skillForm){
	document.getElementById('hiddenTitle').value=document.getElementById('title').value;
	document.getElementById('hiddenSectorID').value=document.getElementById('sectorID').value;
	document.getElementById('hiddenCityID').value=document.getElementById('cityID').value;
	document.getElementById('hiddenSalary').value=document.getElementById('salary').value;
	document.getElementById('hiddenExperience').value=document.getElementById('experience').value;
	document.getElementById('hiddenDescription').value=document.getElementById('description').value;
	document.getElementById('hiddenTitle2').value=document.getElementById('title').value;
	document.getElementById('hiddenSectorID2').value=document.getElementById('sectorID').value;
	document.getElementById('hiddenCityID2').value=document.getElementById('cityID').value;
	document.getElementById('hiddenSalary2').value=document.getElementById('Salary').value;
	document.getElementById('hiddenExperience2').value=document.getElementById('Experience').value;
	document.getElementById('hiddenDescription2').value=document.getElementById('description').value;
}
</script>
<body><center> <div align="center">
<div id="top" >
	
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
	<%
	String registerMsg="";
	String error=(String)request.getAttribute("error");
	String success=(String)request.getAttribute("success");
	if (success!=null)
		registerMsg=success;	
	else if (error!=null)
		registerMsg=error;
	
	
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
		Employer unameEmp =(Employer)request.getSession().getAttribute("employerBean"); 
		String username="";
		JobSeeker uname=null;
		String actionName="";
		int jobseekerID=0;
		if (unameEmp==null && request.getSession().getAttribute("jobseeker")!=null){
		uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
		username=uname.getUsername();
		jobseekerID=uname.getId();
		actionName="applyJob";
		}
		else if (unameEmp==null && request.getSession().getAttribute("employee")!=null){
		uname=(JobSeeker)request.getSession().getAttribute("employee");
		jobseekerID=((Employee)uname).getJobseekerId();
		username=uname.getUsername();
		actionName="applyJob";
		}
		else if (unameEmp!=null){
		username=unameEmp.getUsername();
		actionName="updateJob";
		}
		int jobID=0;
		
		if (request.getServletPath()=="addjobskill")		
		jobID=((Integer)request.getAttribute("jobID")).intValue();
		
		if (jobID==0){
			jobID=Integer.parseInt(request.getParameter("jobID"));
		}
		String URI=request.getRequestURI();
		String jspName=URI.substring(URI.lastIndexOf("/")+1);
		
		
		
		//To retrieve job object
	  	
				DefaultHttpClient httpClient8 = new DefaultHttpClient();
				HttpGet httpGet8 = new HttpGet(basePath+"/getJobDataByID?jid="+jobID);
				        	
				HttpResponse httpResponse8 = httpClient8.execute(httpGet8);
				HttpEntity httpEntity8 = httpResponse8.getEntity();
				InputStream is8 = httpEntity8.getContent();
				ObjectInputStream in8 = new ObjectInputStream(is8);
				Job job=(Job) in8.readObject();
				httpClient8.close();
		
		//To retrieve sector value
	  	
		DefaultHttpClient httpClient7 = new DefaultHttpClient();
		HttpGet httpGet7 = new HttpGet(basePath+"/getSectorDataByID?sectorid="+job.getSectorID());
		        	
		HttpResponse httpResponse7 = httpClient7.execute(httpGet7);
		HttpEntity httpEntity7 = httpResponse7.getEntity();
		InputStream is7 = httpEntity7.getContent();
		ObjectInputStream in7 = new ObjectInputStream(is7);
		Sector jobSector=(Sector) in7.readObject();
		httpClient7.close();
		
		//To retrieve city value
	  	
				DefaultHttpClient httpClient6 = new DefaultHttpClient();
				HttpGet httpGet6 = new HttpGet(basePath+"/getCityDataByIDServlet?cityID="+job.getCityID());
				        	
				HttpResponse httpResponse6 = httpClient6.execute(httpGet6);
				HttpEntity httpEntity6 = httpResponse6.getEntity();
				InputStream is6 = httpEntity6.getContent();
				ObjectInputStream in6 = new ObjectInputStream(is6);
				City jobCity=(City) in6.readObject();
				httpClient6.close();
		
		//To retrieve country value
	  	
		DefaultHttpClient httpClient5 = new DefaultHttpClient();
		HttpGet httpGet5 = new HttpGet(basePath+"/getCountryDataByIDServlet?type=stream&countryID="+job.getCountryID());
		        	
		HttpResponse httpResponse5 = httpClient5.execute(httpGet5);
		HttpEntity httpEntity5 = httpResponse5.getEntity();
		InputStream is5 = httpEntity5.getContent();
		ObjectInputStream in5 = new ObjectInputStream(is5);
		Country jobCountry=(Country) in5.readObject();
		httpClient5.close();
		
		
		//To retrieve employer object
	  	Employer employer=null;
				DefaultHttpClient httpClient9 = new DefaultHttpClient();
				HttpGet httpGet9 = new HttpGet(basePath+"/getEmployerByID?eid="+job.getEid());
				        	
				HttpResponse httpResponse9 = httpClient9.execute(httpGet9);
				HttpEntity httpEntity9 = httpResponse9.getEntity();
				InputStream is9 = httpEntity9.getContent();
				ObjectInputStream in9 = new ObjectInputStream(is9);
				employer=(Employer) in9.readObject();
				httpClient9.close();
		JobApplication jobApp=null;
		
		String jobAppDate="";
		String jobAppStatus="";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try{
		
		if (uname!=null && jobID!=0){
		//To retrieve job application object
	  	
		DefaultHttpClient httpClient4 = new DefaultHttpClient();
			  	HttpGet httpGet4 = new HttpGet(basePath+"/getJobApplicationServlet?jobID="+jobID+"&jobseekerID="+jobseekerID);
			  	
			  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
			  	HttpEntity httpEntity4 = httpResponse4.getEntity();
			  	InputStream is4 = httpEntity4.getContent();
			  	ObjectInputStream in4 = new ObjectInputStream(is4);
			  	jobApp=(JobApplication) in4.readObject();
			  	httpClient4.close();
		
		jobAppDate=sdf.format(jobApp.getApplicationDate());
		jobAppStatus=jobApp.getApplicationStatus();
		
		
		}
		
		}
		catch(NullPointerException ex){
			jobAppDate="";
			jobAppStatus="";
			
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
	    
	    	//To retrieve all skill values
	   List<Skill> skillList = new ArrayList<Skill>();
	   DefaultHttpClient httpClient4 = new DefaultHttpClient();
	   	HttpGet httpGet4 = new HttpGet(basePath+"/getValueListServlet?type=skill");
	   	        	
	   	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
	   	HttpEntity httpEntity4 = httpResponse4.getEntity();
	   	InputStream is4 = httpEntity4.getContent();
	   	ObjectInputStream in4 = new ObjectInputStream(is4);
	   	skillList=(List<Skill>) in4.readObject();
	   	httpClient4.close();
		
		
		List<Skill> newSelectedJobSkillList=(List<Skill>) request.getSession().getAttribute("selectedJobSkillList");
		List<Skill> selectedJobSkillList=job.getJobSkills();
		
		if (unameEmp!=null){
			
		request.getSession().setAttribute("selectedJobSkillList",selectedJobSkillList);
		
		if (newSelectedJobSkillList!=null){
		  	if (newSelectedJobSkillList.size()!=selectedJobSkillList.size()){
		  		selectedJobSkillList=newSelectedJobSkillList;
		  		request.getSession().setAttribute("selectedJobSkillList",selectedJobSkillList);
		  	}
		  	else{
		  		
		  		for (int m=0;m<newSelectedJobSkillList.size();m++){
		  			if(!newSelectedJobSkillList.get(m).equals(selectedJobSkillList.get(m))){
		  				selectedJobSkillList=newSelectedJobSkillList;
		  				request.getSession().setAttribute("selectedJobSkillList",selectedJobSkillList);
		  				break;
		  			}
		  					
		  		}
		  	}
		}
		}
		if(unameEmp!=null || uname!=null){
		if(unameEmp!=null){%>
		<b>You logged as <%=unameEmp.getUsername()%></b><% }
			else if(uname!=null){
		%>
		<b>You logged as <%=uname.getUsername()%></b><% }
		%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
		<%}
		else{%>
		<a href="login.jsp" style=" padding-right: 20px; padding-top:20px"> <b>Login as a Job Seeker/Employee</b></a><br/>	
		<a href="employerlogin.jsp" style=" padding-right: 20px;"> <b>Login as an Employer</b></a>
		<%} %>
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
	else if (request.getSession().getAttribute("jobseeker")!=null){%>
		
		<li><input type="button" onclick="location.href='home.jsp' " value="Home"></li>
		<li><input type="button" onclick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onclick="location.href='savedetails.jsp' " value="Talent Profile"></li>
		<li><input type="button" onclick="location.href='myinterviewcalls.jsp' " value="Interview Calls"></li>
		<li><input type="button" onclick="location.href='jobseekerupdateinfo.jsp' " value="User Info"></li>
	<%}
	else if (request.getSession().getAttribute("employee")!=null){%>
	
		<li><input type="button" onclick="location.href='employeehome.jsp' " value="Home"></li>
		<li><input type="button" onclick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onclick="location.href='savedetails.jsp' " value="Talent Profile"></li>
		<li><input type="button" onclick="location.href='myinterviewcalls.jsp' " value="Interview Calls"></li>
		<li><input type="button" onclick="location.href='jobseekerupdateinfo.jsp' " value="User Info"></li>
	<%}
	else{%>
		<li><input type="button" onClick="location.href='index.jsp' " value="Home"></li>
		<li><input type="button" onClick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onClick="location.href='jobseekersignup.jsp'" value="Register as JobSeeker "></li>
		<li><input type="button" onClick="location.href='employersignup.jsp' " value="Register as Employer"></li>
		<li><input type="button" onclick="location.href='aboutbestjobs.jsp' " value="About Best Jobs"></li>
	<%}%>
	</ul>
	</div>
	<div id="content">
		<table>
				<tr>
					<td>		          
					
					<form name="jobform" method="post" action="<%=actionName %>" id="form">
					
						<table>
							<tr>
								<td style="height: 20px;">Job Title:</td>
								<td style="text-align: left">
								<%if (unameEmp!=null){ %>
								<input class="loginform_text" name="title" id="title" type="text" value="<%=job.getTitle()%>" style="width: 100px; height: 18px;" />
								<%} 
								else{%>
								<input class="loginform_text" name="title" id="title" type="text" value="<%=job.getTitle()%>" readonly="readonly"  style="width: 100px; height: 18px;" />
								<%}%>
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Post Date:</td>
								<td style="text-align: left">
								<input class="loginform_text" name="jobPostDate" id="jobPostDate" type="text" value="<%=sdf.format(job.getPostDate())%>" readonly="readonly"  style="width: 100px; height: 18px;" />
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Job Status:</td>
								<td style="text-align: left">
								<input class="loginform_text" name="status" id="status" type="text" value="<%=job.getStatus()%>" readonly="readonly" style="width: 100px; height: 18px;" />
								<input  type="hidden" name="status" id="status" value="<%=job.getStatus()%>" />
								</td>				
							</tr>
							<%if (uname!=null){ %>
							<tr>
								<td style="height: 20px;">Application Status:</td>
								<td style="text-align: left">
								<input class="loginform_text" name="jobApplicationStatus" id="jobApplicationStatus" type="text" value="<%=jobAppStatus%>" readonly="readonly" style="width: 100px; height: 18px;" />
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Application Date:</td>
								<td style="text-align: left">
								<input class="loginform_text" name="jobApplicationDate" id="jobApplicationDate" type="text" value="<%=jobAppDate%>" readonly="readonly" style="width: 100px; height: 18px;" />
								</td>				
							</tr>
							<%}%>
							<tr>
								<td style="height: 20px;">Sector Name:</td>
								
								<td style="text-align: left">
								<%if (unameEmp!=null){ %>
								<select name="sectorID" id="sectorID">
								<%
								for (Sector sector:sectorList){	
								if (job.getSectorID()==sector.getId()) {
								%>
								<option value="<%=sector.getId()%>" selected="selected"><%=sector.getName()%></option>
								<%}
								else{%>
								<option value="<%=sector.getId()%>" ><%=sector.getName()%></option>
								<%}
								}
								%>
								</select>
								<%}
								else{%>
								
								<input class="loginform_text" name="sectorName" id="sectorName" type="text" value="<%=jobSector.getName()%>" readonly="readonly" style="width: 100px; height: 18px;" />
								
								<%} %>							
								</td>	
											
							</tr>
							<tr>
								<td style="height: 20px;">Country Name:</td>
								<td style="text-align: left">
								<%if (unameEmp!=null){ %>
								<select name="countryID" id="countryID">
								<option value="0" selected="selected">Select a Country</option>
								<%
								for (Country country:countryList){	
								if (job.getCountryID()==country.getId()) {
								%>
								<option value="<%=country.getId()%>" selected="selected"><%=country.getName()%></option>
								<%}
								else{%>
								<option value="<%=country.getId()%>" ><%=country.getName()%></option>
								<%}
								}
								%>
								</select>
								<%}
								else{%>
								
								<input class="loginform_text" name="countryName" id="countryName" type="text" value="<%=jobCountry.getName()%>" readonly="readonly" style="width: 100px; height: 18px;" />
								
								<%} %>									
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">City Name:</td>
								
								<td style="text-align: left">
								<%if (unameEmp!=null){ %>
								<select name="cityID" id="cityID">
								<%
								for (City city:cityList){	
								if (job.getCityID()==city.getId()) {
								%>
								<option value="<%=city.getId()%>" selected="selected"><%=city.getName()%></option>
								<%}
								else{%>
								<option value="<%=city.getId()%>" ><%=city.getName()%></option>
								<%}
								}
								%>
								</select>
								<%}
								else{%>
								
								<input class="loginform_text" name="cityName" id="cityName" type="text" value="<%=jobCity.getName()%>" readonly="readonly" style="width: 100px; height: 18px;" />
								
								<%} %>							
								</td>	
											
							</tr>
							<tr>
								<td style="height: 20px;">Required Experience:</td>
								<td style="text-align: left">
								<%if (unameEmp!=null){ %>
								<input class="loginform_text" name="experience" id="experience" type="text" value="<%=job.getExperience()%>" style="width: 100px; height: 18px;" />
								<%} 
								else{%>
								<input class="loginform_text" name="experience" id="experience" type="text" value="<%=job.getExperience()%>" readonly="readonly" style="width: 100px; height: 18px;" />
								<%}%>
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Salary:</td>
								<td style="text-align: left">
								<%if (unameEmp!=null){ %>
								<input class="loginform_text" name="salary" id="salary" type="text" value="<%=job.getSalary()%>" style="width: 100px; height: 18px;" />
								<%} 
								else{%>
								<input class="loginform_text" name="salary" id="salary" type="text" value="<%=job.getSalary()%>" readonly="readonly" style="width: 100px; height: 18px;" />
								<%}%>
								<input class="loginform_text"  name="currency" id="currency" type="text" value="<%=job.getCurrency()%>" readonly="readonly" style="border-color: buttonface;width: 40px; height: 18px;background-color: buttonface;" />
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;" valign="top" >Job Description:</td>
								<td style="text-align: left">
								<%if (unameEmp!=null){ %>
								<textarea name="description" id="description" cols="30" rows="10"><%=job.getDescription()%></textarea>
								<%}
								else{%>
								<textarea name="description" id="description" readonly="readonly" cols="30" rows="10"><%=job.getDescription()%></textarea>
								<%} %>
								</td>				
							</tr>
							<%if(uname!=null){ %>
							<tr>
								<td style="height: 20px;">Employment Salary Report (YoY):</td>
								<td style="text-align: left">
								<a href="retrieveSalaryReport?jobID=<%=job.getJid()%>" name="reportFileLink" ><img src="images/report.png" height="40" width="40" /></a>
								</td>				
							</tr>
							<%} %>
							<tr>
								<td style="height: 20px;">&nbsp;</td>
								<td colspan="2" style="text-align: left">
								<%if (unameEmp!=null){ %>
								<input type="hidden" name="fileName" id="fileName" value="<%=jspName%>"/>
								<input type="hidden" name="jobID" value="<%=jobID%>" />
								<input name="save" type="submit" id="save" value="Save Job" style="width: 100px; height: 18px;">
								<%} 
								else if(uname!=null){
									
										if (jobAppStatus.equals("") && job.getStatus().equals("Open")){%>
											<input type="hidden" name="jobID" value="<%=jobID%>" />
											<input  type="submit" value="Apply" />
											
										<%}
										else{%>	
											<input  type="submit" value="Apply" disabled="disabled" />
										<%}
									}%>
								
		
								</td>
							</tr>
						</table>
					</form>
					</td>
					<td>
					&nbsp;
					</td>
					<td>
					&nbsp;
					</td>
					<td>
					&nbsp;
					</td>
					<%if (unameEmp!=null){ %>
					<td valign="top">
						<form name="newjobform" method="post" action="addjobskill" id="form">
							<table>	
								<tr>
									<td> 
									<select name="skillName" style="width: 120px;">
										<% 
										
										for (Skill skill:skillList){
											boolean displaySkill=true;
											
											if (selectedJobSkillList.size()>0){
												
												for (Skill skillAvail:selectedJobSkillList){	
													if (skill.getSkillID()==skillAvail.getSkillID())
													{
														displaySkill=false;
														break;
													}
												}
											}
											if (displaySkill)
											{
										%>
										<option value="<%=skill.getSkillID()%>" ><%=skill.getName()%></option>
										<%}
										}
										%>
										</select>
									</td>
									
								</tr>
								<tr >
									
									<td colspan="2" style="text-align: left">
									<input type="hidden" name="jspFileName" id="jspFileName" value="<%=jspName%>"/>
									<input type="hidden" name="jobID" id="jobID" value="<%=jobID%>" />
									<input type="hidden" name="hiddenTitle" id="hiddenTitle"/>
									<input type="hidden" name="hiddenSectorID" id="hiddenSectorID" />
									<input type="hidden" name="hiddenCityID" id="hiddenCityID" />
									<input type="hidden" name="hiddenSalary" id="hiddenSalary" />
									<input type="hidden" name="hiddenExperience" id="hiddenExperience" />
									<input type="hidden" name="hiddenDescription" id="hiddenDescription"/>
									<input name="save" type="submit" id="saveexp" value="Add Skill" style="width: 100px; height: 18px;" onClick="document.forms[0].save.click();">
									</td>
								</tr>
								
				
							</table>
						</form>
						
					</td>
					<%}%>
					<td valign="top">
						
						<table valign="top" frame="box" style="border: 2px solid white;border-collapse:collapse;" >
								<tr >
									<th style="width: 80px">REQUIRED</th>
									<th style="width: 80px">SKILLS</th>
									<th style="width: 80px"></th>
									<th style="width: 80px"></th>
								</tr>
						</table>
						<table valign="top" frame="box" style="border: 2px solid white;" >
						<tr >
									<td style="heigth: 0px;width: 80px"></td>
									<td style="heigth: 0px;width: 80px"></td>
									<td style="heigth: 0px;width: 80px"></td>
									<td style="heigth: 0px;width: 80px"></td>
								</tr>
							<% 
							for (int i=0; i<selectedJobSkillList.size();i++){ 
							if (i%4==0){ %>
								<tr>
							<%}%>
								<td style="width: 80px;" >
									<form method="post" action="removejobskill" name="<%=selectedJobSkillList.get(i).getName()%>">
										<label><%=selectedJobSkillList.get(i).getName()%></label>
										<%if (unameEmp!=null){%>
										<input type="hidden" name="skillId" value="<%=selectedJobSkillList.get(i).getSkillID()%>" />
										<input type="hidden" name="jspFileName" id="jspFileName" value="<%=jspName%>" />
										<input type="hidden" name="jobID" id="jobID" value="<%=jobID%>" />
										<input type="hidden" name="hiddenTitle2" id="hiddenTitle2"/>
										<input type="hidden" name="hiddenSectorID2" id="hiddenSectorID2" />
										<input type="hidden" name="hiddenCityID2" id="hiddenCityID2" />
										<input type="hidden" name="hiddenSalary2" id="hiddenSalary2" />
										<input type="hidden" name="hiddenExperience2" id="hiddenExperience2" />
										<input type="hidden" name="hiddenDescription2" id="hiddenDescription2"/>
										<input type="submit" value="x" onClick="document.forms[0].save.click();" />
										<%}%>
									</form>
								</td>
								
								<% if (i%4==3){%>
								</tr>
							<%}
							} %>
							</table>
							
							
						</td>
						<%if (unameEmp==null){ %>
						<td>
						&nbsp;
						</td>
						<%}%>
					</tr>	
							<tr>
									<td>
									</td>
									<td>
									</td>
							</tr>	
							<%if(employer!=null){ %>
							<tr>
							<td>
								<table frame="box" style="border: 2px solid white;" >
									
									<tr>
									<td>
									<h2><b>About <%=employer.getCompanyName()%></b></h2>
									</td>
									<td>
									<img src="<%="retrievefile?type=logo_image&contenttype=image/gif&username="+employer.getUsername()%>" height="200" width="200" name="profileImage"/>
									</td>
									</tr>
									<tr>
									<td>
									<label><i><%=employer.getDescription()%></i></label>
									</td>
									<td>
									<label><i>Address: <%=employer.getAddress()%></i></label>
									<label><i>Phone No: <%=employer.getPhone()%></i></label>
									<label><i>Email: <%=employer.getEmail()%></i></label>
									</td>
							</tr>
								</table>
							</td>
							</tr>	
							<%} %>
				</table>
	<label><%=registerMsg%></label>
	</div>
</div>
</div>

</center>
</body>
</html>