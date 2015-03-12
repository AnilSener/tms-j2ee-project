<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,model.Employer,model.JobSeeker,model.Employee,model.Profile,model.Skill,model.Qualification,model.Experience" %>
 <%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>

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
	Employee uname =(Employee)request.getSession().getAttribute("employee");
	
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	int jobID=0;
	int jobSeekerID=0;
	if (request.getParameter("jobSeekerID")!=null){
	jobSeekerID=Integer.parseInt(request.getParameter("jobSeekerID"));
	}
	else if (request.getAttribute("jobSeekerID")!=null){
	jobSeekerID=((Integer)request.getAttribute("jobSeekerID")).intValue();	
	}
	else if(uname!=null)
	jobSeekerID=uname.getJobseekerId();
	
	//Might be useful
	String URI=request.getRequestURI();
	String jspName=URI.substring(URI.lastIndexOf("/")+1);
	
	
	int jobAppID=0;
	int dayOfMonth=0;
	String formAction="";
	JobSeeker jobseeker=null;
	List<Skill> jobSeekerSkillList=null;
	List<Qualification> jobSeekerQualifications=null;
	List<Experience> jobSeekerExperiences=null;
	boolean isEmployee=false;
	Employee employee=null;
	List<Employee> supervisorList=null;
	if (jobSeekerID!=0){
	
		//To retrieve as is jobseeker object
	  	DefaultHttpClient httpClient = new DefaultHttpClient();
	  	HttpGet httpGet = new HttpGet(basePath+"/getJobSeekerByIDServlet?jobSeekerID="+jobSeekerID);
	  	
	  	HttpResponse httpResponse = httpClient.execute(httpGet);
	  	HttpEntity httpEntity = httpResponse.getEntity();
	  	InputStream is = httpEntity.getContent();
	  	ObjectInputStream in = new ObjectInputStream(is);
	  	jobseeker=(JobSeeker) in.readObject();
	  	httpClient.close();
	  	
		//To retrieve as is profile object
      	DefaultHttpClient httpClient2 = new DefaultHttpClient();
      	HttpGet httpGet2 = new HttpGet(request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()))+"/getProfileServlet?username="+jobseeker.getUsername());
      	
      	HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
      	HttpEntity httpEntity2 = httpResponse2.getEntity();
      	InputStream is2 = httpEntity2.getContent();
      	ObjectInputStream in2 = new ObjectInputStream(is2);
      	Profile profile=(Profile) in2.readObject();
      	httpClient2.close();
      	
      	
		jobSeekerSkillList=profile.getSkills();
		jobSeekerQualifications=profile.getQualifications();
		jobSeekerExperiences=profile.getExperiences();
	  	
	  	
		//To retrieve as is employee object
		if(uname==null){
		  try{
			  
			DefaultHttpClient httpClient4 = new DefaultHttpClient();
		  	HttpGet httpGet4 = new HttpGet(basePath+"/getEmployeeByJobSeekerIDServlet?jobSeekerID="+jobSeekerID);
		  	
		  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
		  	HttpEntity httpEntity4 = httpResponse4.getEntity();
		  	InputStream is4 = httpEntity4.getContent();
		  	ObjectInputStream in4 = new ObjectInputStream(is4);
		  	employee=(Employee) in4.readObject();
		  	httpClient4.close();
		  	isEmployee=true;
		  	}
			catch(NullPointerException ex){
			isEmployee=false;
			}
		} else{
			employee=uname;
		}
		
		//To retrieve all active employees
		supervisorList = new ArrayList<Employee>();
		  	DefaultHttpClient httpClient3 = new DefaultHttpClient();
		  	HttpGet httpGet3=null;
		  	if(unamEmp!=null){
		  	httpGet3 = new HttpGet(basePath+"/getAllEmployeesByEmployer?eid="+unamEmp.getEid());
		  	}
		  	else if(uname!=null){
			httpGet3 = new HttpGet(basePath+"/getAllEmployeesByEmployer?eid="+uname.getEid());
		  	}
		  	HttpResponse httpResponse3 = httpClient3.execute(httpGet3);
		  	HttpEntity httpEntity3 = httpResponse3.getEntity();
		  	InputStream is3 = httpEntity3.getContent();
		  	ObjectInputStream in3 = new ObjectInputStream(is3);
		  	supervisorList=(List<Employee>) in3.readObject();
		  	httpClient.close();
		
	}
		
	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	String imageHref="retrievefile?type=profile_image&contenttype=image/gif&username="+jobseeker.getUsername();

	%>
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
	<%if(unamEmp!=null){%>
<b>You logged as <%=unamEmp.getUsername()%></b><% }
	else if(uname!=null){
%>
<b>You logged as <%=uname.getUsername()%></b><% }%>
<a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<%if (unamEmp!=null){ %>
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
			       
					<form name="talentform" method="post" action="updateEmployeeServlet" id="form">
						<table>
							<tr>
							<td>
						<table>
							<tr>
								<td style="height: 20px;">First Name:</td>
								<td style="text-align: left;" >
								<input class="loginform_text" name="firstname" id="firstname" type="text" value="<%=jobseeker.getFirstname()%>" readonly="readonly" style="width: 150px; height: 18px;" />					
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Last Name:</td>
								<td style="text-align: left;" >
								<input class="loginform_text" name="firstname" id="lastname" type="text" value="<%=jobseeker.getLastname()%>" readonly="readonly" style="width: 150px; height: 18px;" />					
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">JobSeeker Email:</td>
								<td style="text-align: left;">
								<input class="loginform_text" name="contactEmail" id="contactEmail" type="text" value="<%=jobseeker.getEmail()%>"  readonly="readonly"  style="width: 150px; height: 18px;" />							
								</td>					
							</tr>
							<tr>
								<td style="height: 20px;">Address:</td>
								<td style="text-align: left;">				
								<input class="loginform_text" name="address" type="text" id="address"  value="<%=jobseeker.getAddress()%>" readonly="readonly" style="width: 150px; height: 18px;">
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Phone Number:</td>
								<td style="text-align: left;">	
								<input class="loginform_text" name="phonenumber" type="text" id="phonenumber" value="<%=jobseeker.getPhone()%>" readonly="readonly" style="width: 150px; height: 18px;">
								</td>
							</tr>
							<tr>
								<td style="height: 20px;">Job :</td>
								<td style="text-align: left">
								<input class="loginform_text" name="job" type="text" id="job"  value="<%=jobseeker.getJob()%>" readonly="readonly" style="width: 150px; height: 18px;">
								</td>				
							</tr>
							<%if (isEmployee && employee.getId()!=0 || uname!=null) {%>
							
							<tr>
								<td style="height: 20px;">Employment Status:</td>
								<td style="text-align: left;">								
								<select name="status" id="status" <%=uname!=null?" disabled ":""%>>
									<% 
									String statusTypes[]={"New Hired","Working","Resigned","Retired","Fired"};
									for (int i=0;i<statusTypes.length;i++){
										
										if (statusTypes[i].equals(employee.getStatus()))
										{
									%>
									<option value="<%=statusTypes[i]%>" selected="selected"><%=statusTypes[i]%></option>
										<%} 
										else{%>
										<option value="<%=statusTypes[i]%>"><%=statusTypes[i]%></option>
										<%}
										
									}%>
								</select>	
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Hiring Date:</td>
								<td style="text-align: left;">
								
								<input class="loginform_text" name="hiringdate" type="text" id="hiringdate"  value="<%=(String)sdf.format(employee.getHiringDate())%>" readonly="readonly" style="width: 150px; height: 18px;">
								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Supervisor Name:</td>
								<td style="text-align: left">
								<select name="supervisorID" id="supervisorID" <%=uname!=null?" disabled ":""%>>
								<option value="0" ></option>
								<%
								for (Employee supervisor:supervisorList){	
									if (supervisor.getId()==employee.getId() || supervisor.getSupervisorId()==employee.getId())
										continue;
									String fullname=supervisor.getFirstname()+" "+supervisor.getLastname();
									if(employee.getSupervisorId()==supervisor.getId()){
								%>
								<option value="<%=supervisor.getId()%>" selected="selected"><%=fullname%></option>
								<%}
									else{%>
								<option value="<%=supervisor.getId()%>" ><%=fullname%></option>		
									<%}
								}
								%>
								</select>								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Company Email:</td>
								<td style="text-align: left;">
								<input class="loginform_text" name="companyemail" type="text" id="companyemail"  value="<%=employee.getCompanyEmail()%>"  <%=uname!=null?" readonly ":""%> style="width: 150px; height: 18px;">
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Salary:</td>
								<td style="text-align: left;">
								<input class="loginform_text" name="salary" id="salary" type="text" value="<%=employee.getSalary()%>" readonly="readonly" style="width: 150px; height: 18px;" /><label><%=employee.getCurrency()%></label>					
								</td>				
							</tr>
							<%} %>
							
							
							<tr>
								<td style="height: 20px;">&nbsp;</td>
								<td colspan="2" style="text-align: left">
								
								
								<%if (isEmployee && employee.getId()!=0){%>
								<input type="hidden" name="id" id="id" value="<%=employee.getId()%>"/>
								<input type="hidden" name="jobSeekerID" id="jobSeekerID" value="<%=employee.getJobseekerId()%>"/>
								<input name="save" type="submit" id="save" value="Update" style="width: 100px; height: 18px;" />
								<%}
								else{%>
								<input name="save" type="submit" id="save" value="Update" disabled="disabled" style="width: 100px; height: 18px;" />
								<%}%>
								</td>
							</tr>
						</table>
						</td>
						<td>
							<table>
								<tr>
								<td valign="top" align="right">
								<img src="<%=imageHref%>" height="100" width="100" name="profileImage"/>
								</td>
								</tr>
								<tr>
								<td valign="top" align="right">
								<table  border="1">
									<tr>
										<th style="width: 120px;">School Name</th>
										<th style="width: 120px;">Degree</th>
										<th style="width: 40px;">GPA</th>
									</tr>
									<% 
								for (int i=0; i<jobSeekerQualifications.size();i++){ 
								%>
									<tr><td style="width: 120px;" ><label><%=jobSeekerQualifications.get(i).getSchoolName()%></label></td><td style="width: 120px;" ><label><%=jobSeekerQualifications.get(i).getDegree()%></label></td><td><label><%=jobSeekerQualifications.get(i).getGpa()%></label></td></tr>
								<%}%>
								
								</table>
								</td>
								</tr>
								<tr>
								<td valign="top" align="right">
								<table   border="1">
										<tr>
											<th style="width: 120px;">Company</th>
											<th style="width: 120px;">Position</th>
											<th style="width: 120px;">Years Worked On</th>
										</tr>
									<%
									for (int i=0; i<jobSeekerExperiences.size();i++){ 
									%>
										<tr>
										<td style="width: 120px;" >
										<label><%=jobSeekerExperiences.get(i).getCompany()%></label>
										</td>
										<td style="width: 120px;" >
										<label><%=jobSeekerExperiences.get(i).getPosition()%></label>
										</td>
										<td>
										<label><%=jobSeekerExperiences.get(i).getYears()%></label>
										</td>
										</tr>
									<%}%>
									</table>
								</td>
								</tr>
								<tr>
								<td valign="top" align="right">
								<table  frame="box" cellspacing="0" cellpadding="0" style="border-spacing: 0;">
										<tr>
											<th>MY SKILLS</th>
											<th>&nbsp;</th>
											<th>&nbsp;</th>
											<th>&nbsp;</th>
										</tr>
									<% 
									for (int i=0; i<jobSeekerSkillList.size();i++){ 
									if (i%4==0){%>
										<tr>
									<%}%>
										<td style="width: 100px;" ><label><%=jobSeekerSkillList.get(i).getName()%></label></td>
										
										<% if (i%4==3){%>
										</tr>
									<%}
									} %>	
									</table>
								</td>
								</tr>
							</table>
							</td>
							<td>
							</td>
							<td>
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