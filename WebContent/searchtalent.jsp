<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*"%>

<%@ page import="java.util.*,java.io.FileNotFoundException"%>

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
		String jspName="";
		if (request.getAttribute("jspName")!=null)
		jspName=(String)request.getParameter("jspName");
		
		if (success!=null)
			registerMsg=success;	
		else if (error!=null)
			registerMsg=error;
		
		Employer uname =(Employer)request.getSession().getAttribute("employerBean");
		boolean isEmployeeChecked=true; 
    	//String URI=request.getRequestURI();
    	//String jspName=URI.substring(URI.lastIndexOf("/")+1);
    List<JobSeeker> talentList = new ArrayList<JobSeeker>();
	
	if(request.getAttribute("talentList")!=null){
	talentList =(List<JobSeeker>)request.getAttribute("talentList");
	isEmployeeChecked= (Boolean)request.getAttribute("isEmployee");
	}
	else{
	isEmployeeChecked=true;
	//To retrieve all active employees
  	DefaultHttpClient httpClient = new DefaultHttpClient();
  	HttpGet httpGet = new HttpGet(basePath+"/queryTalentServlet?eid="+uname.getEid()+"&action=list&status=&isEmployee="+isEmployeeChecked+"&firstName=&lastName=&supervisorid=0");
  	
  	HttpResponse httpResponse = httpClient.execute(httpGet);
  	HttpEntity httpEntity = httpResponse.getEntity();
  	InputStream is = httpEntity.getContent();
  	ObjectInputStream in = new ObjectInputStream(is);
  	talentList=(List<JobSeeker>) in.readObject();
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
	
		<form action="queryTalentServlet" name="queryTalentForm" method="get">
		<h2>SEARCH TALENTS BY...</h2>
		<table>
			<tr>
				<td style="height: 20px;">
				<label>Job:</label>
				</td>
				<td style="text-align: left">
				<input class="loginform_text" type="text" name="job" id="job" value="${param.job}" style="width: 120px; height: 18px;" onclick="this.value='';" />
				</td>
				<td style="height: 20px;">
				<label>First Name:</label>
				</td>
				<td style="text-align: left">
				<input class="loginform_text" type="text" name="firstName" id="firstName" value="${param.firstName}" style="width: 120px; height: 18px;" onclick="this.value='';" />
				</td>
				<td style="height: 20px;">
				<label>Employment Status:</label>
				</td>
				<td style="text-align: left">
				<select name="status" id="status" style="width: 120px; height: 18px;">
									<option value=""></option>
									<% 
									String statusTypes[]={"","New Hired","Working","Retired","Fired","Resigned"};
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
				<label>Is Our Employee?:</label>
				</td>
				<td style="text-align: left">
				<input type="checkbox" name="isEmployee" id="isEmployee" value="true" <%=jspName.equals("postnewproject.jsp") || jspName.equals("projectdetail.jsp")?" disabled":""%> <%=isEmployeeChecked?" checked":""%> />	
				</td>
				
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
				<input type="hidden" name="eid" id="eid" value="<%=uname.getEid()%>"/>
				<input type="hidden" name="action" id="action" value="search"/>
				<input type="submit" value="Search!" name="queryButton"/>
				</td>
			</tr>
		</table>
		</form>
	<%if(jspName.equals("postnewproject.jsp") || jspName.equals("projectdetail.jsp")){%>
	<form name="assignEmployee" method="post" action="assignEmployee" >
	<input type="submit" value="Done" name="doneButton"/>
	
	<%} %>
	<h2>TALENTS</h2><label><%=registerMsg%></label>
	
	<table border="1">
	<tr>
	<%if(jspName.equals("postnewproject.jsp") || jspName.equals("projectdetail.jsp")){%>
	<th></th>
	<%} %>
	<th>Photo</th>
	<th>Candidate Full Name</th>
	<th>Job</th>
	<th>Hiring Date</th>
	<th>Is Our Employee?</th>
	<th>Status</th>
	<th>Work Experiences</th>
	<th>Education</th>
	<th>Communication Info</th>
	<th>No of Conducted<br/> Interviews</th>
	<th>CV</th>
	<th>Skills</th>
	<th>Action</th>
	</tr>
	<% 
	if (talentList!=null){
	for(int i=0 ; i <talentList.size() ; i++){ 
	
	
	//To retrieve as is employee object
  	DefaultHttpClient httpClient4 = new DefaultHttpClient();
  	HttpGet httpGet4 = new HttpGet(basePath+"/getEmployeeByJobSeekerIDServlet?jobSeekerID="+talentList.get(i).getId());
  	
  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
  	HttpEntity httpEntity4 = httpResponse4.getEntity();
  	InputStream is4 = httpEntity4.getContent();
  	ObjectInputStream in4 = new ObjectInputStream(is4);
  	Employee employee=(Employee) in4.readObject();
  	httpClient4.close();
	
  	
	
	
	//To retrieve as is profile object
  	DefaultHttpClient httpClient2 = new DefaultHttpClient();
  	HttpGet httpGet2 = new HttpGet(basePath+"/getProfileServlet?username="+talentList.get(i).getUsername());
  	
  	HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
  	HttpEntity httpEntity2 = httpResponse2.getEntity();
  	InputStream is2 = httpEntity2.getContent();
  	ObjectInputStream in2 = new ObjectInputStream(is2);
  	Profile profile=(Profile) in2.readObject();
  	httpClient2.close();
  	
	List<Interview> interviewList = new ArrayList<Interview>();
	
	DefaultHttpClient httpClient6 = new DefaultHttpClient();
	HttpGet httpGet6 = new HttpGet(basePath+"/getMyInteviewCallsServlet?uid="+talentList.get(i).getId()+"&status=");
	
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
		
			if (interview.getStatus().equals("Open") || interview.getStatus().equals("Accepted") || interview.getStatus().equals("Rejected"))
				existsInterviewRequest=true;
			else
				noOfConductedInterviews++;		
	}
	
	}
	List<Skill> jobSeekerSkillList=profile.getSkills();
	List<Qualification> jobSeekerQualifications=profile.getQualifications();
	List<Experience> jobSeekerExperiences=profile.getExperiences();
	
	String CVHref="retrievefile?type=CV&contenttype=application/pdf&username="+talentList.get(i).getUsername();
	String imageHref="retrievefile?type=profile_image&contenttype=image/gif&username="+talentList.get(i).getUsername();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	boolean isEmployeeRecordChecked=false;
		
	if (employee==null){%>
		
		<tr>
	<%}
	else if (employee.getStatus().equals("Retired") || employee.getStatus().equals("Resigned") || employee.getStatus().equals("Fired")){%>	
		<tr style="background-color: #FF0000;">
	<%}
	else if (employee.getStatus().equals("Working")){%>
		<tr style="background-color: #FFFF00;">
	<%}
	else if (employee.getStatus().equals("New Hired")){%>
		<tr style="background-color: #00FF00;">
	<%}%>
			<%if(jspName.equals("postnewproject.jsp") || jspName.equals("projectdetail.jsp")){%>
			<td>
			<input type="checkbox" name="isEmployeeChecked" id="isEmployeeChecked" value="<%=employee.getId()%>" <%=isEmployeeRecordChecked?" checked":""%> />	
			
			</td>
			<%} %>
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
			
				<%=talentList.get(i).getFirstname()+" "+talentList.get(i).getLastname() %>
			</td>
			
			<td style="width: 120px;">
			<%if(talentList.get(i).getJob()!=null && !talentList.get(i).getJob().equals("")){ %>
			<p><label><%=talentList.get(i).getJob()%></label></p>
			<%} %>	
			</td>
			<td style="width: 40px;">
			<%try{ %>
				<%=sdf.format(employee.getHiringDate())%>
			<%}
			catch(NullPointerException ex){%>
			N/A
			<%} %>
			</td>
			<td style="width: 120px;">
			<%try{
			if(employee!=null && (employee.getStatus().equals("New Hired") || employee.getStatus().equals("Working"))){ %>
			<p><label>YES</label></p>
			<%}
			else{
			%><p><label>NO</label></p>
			<% }
			}
			catch(NullPointerException ex){%>	
			<p><label>NO</label></p>
			<%} %>
			</td>
			<td style="width: 40px;">
			<%try{ %>
				<%=employee.getStatus()%>
			<%}
			catch(NullPointerException ex){%>
			N/A
			<%} %>
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
				<p><label>Company Email: <%=employee.getCompanyEmail()%></label></p>
				<p><label>Job Seeker Email: <%=talentList.get(i).getEmail()%></label></p> 
				<p><label>Phone: <%=talentList.get(i).getPhone()%></label></p> 
				<p><label>Address: <%=talentList.get(i).getAddress()%></label></p> 

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
				<input  type="button" class="listbutton" value="Talent Details" onClick="window.location='talentdetails.jsp?jobSeekerID=<%=talentList.get(i).getId()%>'"/>
			</td>
		</tr>
		
		<% } 
	}
		%>
	</table>
	<%if(jspName.equals("postnewproject.jsp") || jspName.equals("projectdetail.jsp")){%>
	<input type="hidden" name="jspName" id="jspName" value="<%=jspName%>"/>
	<input type="hidden" name="code" value="<%=request.getAttribute("code") %>" />
	<input type="hidden" name="title" value="<%=request.getAttribute("title") %>" />
	<input type="hidden" name="status" value="<%=request.getAttribute("status") %>" />
	<input type="hidden" name="description" value="<%=request.getAttribute("description") %>" />
	
	</form>
	<% }%>
	</div>
</div>
<div id="footer">Copyright DGNet LTD © 2014</div>
</div>

</center>
</body>
</html>