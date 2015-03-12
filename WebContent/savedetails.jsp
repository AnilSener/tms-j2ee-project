<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.*,model.Skill,model.Experience,model.JobSeeker,model.Employee,model.Qualification,model.Profile" %>
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
		if (success!=null)
			registerMsg=success;	
		else if (error!=null)
			registerMsg=error;
		
		JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
		int jobseekerID=0;
		boolean isEmployee=false;
		if (uname!=null){
			jobseekerID=uname.getId();
		}
		else{
			uname=(JobSeeker)request.getSession().getAttribute("employee");
			jobseekerID=((Employee)uname).getJobseekerId();
			isEmployee=true;
		}
		
		  List<Skill> skillList = new ArrayList<Skill>();
      	
		//To retrieve all skill values
      	DefaultHttpClient httpClient = new DefaultHttpClient();
      	HttpGet httpGet = new HttpGet(request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()))+"/getValueListServlet?type=skill");
      	
      	HttpResponse httpResponse = httpClient.execute(httpGet);
      	HttpEntity httpEntity = httpResponse.getEntity();
      	InputStream is = httpEntity.getContent();
      	ObjectInputStream in = new ObjectInputStream(is);
      	skillList=(List<Skill>) in.readObject();
      	httpClient.close();
      	
      	//To retrieve as is profile object
      	DefaultHttpClient httpClient2 = new DefaultHttpClient();
      	HttpGet httpGet2 = new HttpGet(request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()))+"/getProfileServlet?username="+uname.getUsername());
      	
      	HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
      	HttpEntity httpEntity2 = httpResponse2.getEntity();
      	InputStream is2 = httpEntity2.getContent();
      	ObjectInputStream in2 = new ObjectInputStream(is2);
      	Profile profile=(Profile) in2.readObject();
      	httpClient2.close();
      	
      	
		List<Skill> jobSeekerSkillList=profile.getSkills();
		List<Qualification> jobSeekerQualifications=profile.getQualifications();
		List<Experience> jobSeekerExperiences=profile.getExperiences();
		
		
		String CVHref="retrievefile?type=CV&contenttype=application/pdf&username="+uname.getUsername();
		String CVUploadStatus=(String)request.getAttribute("status"); 
		
		String imageHref="retrievefile?type=profile_image&contenttype=image/gif&username="+uname.getUsername();
		String imageUploadStatus=(String)request.getAttribute("status"); 
		
		if(uname!=null){%>
<b>You logged as <%=uname.getUsername()%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<%if(isEmployee){
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
	</ul>
	</div>
	
	
	<div id="content">
	
	<table  cellspacing="0" cellpadding="0" style="border-spacing: 0;">	
		<tr>
			<td valign="top">		
				<form name="imageUploadForm" method="post" action="upload" enctype="multipart/form-data">
						<h3>
						 UPLOAD YOUR PHOTO !
						</h3>
						<input type="hidden" name="form_name" value="imageUploadForm">
						
						<table class="uploadform_table" width="200px" >
							
							<tr>
				   				 <td style="text-align:left"><input class="" name="file" type="file" id="file"  style="width:200px;height:18px;"></td>
								<td style="text-align:left;vertical-align:bottom">
								<input type="hidden" name="type" id="type" value="profile_image"/>
								<input class="loginform_button" type="submit" name="upload" value="Upload" id="login"></td>
							
							</tr>
							
						</table>
				</form>
			</td>
			<td>
				<table >
					<tr>
						<td style="border:2pt solid black;">
							<img src="<%=imageHref%>" height="100" width="100" name="profileImage"/>
						</td>
						<td>
				<% if (imageUploadStatus!=null) {%>
					   				<label><%=imageUploadStatus%></label>
					   				<%}
					   				else{
					   				%>
					   				&nbsp;
					   				<%}%>
					   	 </td>
					  </tr>
				</table>			
			</td>
		</tr>	
		
		
		
		<tr>
			<td valign="top">
				<form name="qualificationForm" method="post" action="addqualification" id="form" style="width: 337px; ">
		
					<h4>
					 Add Qualifications
					</h4>
					<table>
						<tr>
							<td style="height: 20px;">School:</td>
							<td style="text-align: left">
							<input class="loginform_text" name="schoolName" type="text" style="width: 100px; height: 18px;">
							</td>
						</tr>
						<tr>
							<td style="height: 20px;">Degree:</td>
							<td style="text-align: left">
							<input class="loginform_text" name="degree" type="text" style="width: 100px; height: 18px;"></td>
						</tr>
						<tr>
							<td style="height: 20px;">GPA:</td>
							<td style="text-align: left">
							<input class="loginform_text" name="gpa" type="text" value="0.00" style="width: 100px; height: 18px;">
							</td>
						</tr>
								
						
						<tr >
							
							<td colspan="2" style="text-align: left"><input
								name="save" type="submit" id="save" value="Add"
								style="width: 100px; height: 18px;"></td>
						</tr>
		
					</table>
				</form>
			</td>
			<td valign="top" align="center">
						<table  border="1">
							<tr>
								<th style="width: 120px;">School Name</th>
								<th style="width: 120px;">Degree</th>
								<th style="width: 40px;">GPA</th>
								<th>&nbsp;</th>
							</tr>
							<% 
						for (int i=0; i<jobSeekerQualifications.size();i++){ 
						%>
							<tr><td style="width: 120px;" ><label><%=jobSeekerQualifications.get(i).getSchoolName()%></label></td><td style="width: 120px;" ><label><%=jobSeekerQualifications.get(i).getDegree()%></label></td><td><label><%=jobSeekerQualifications.get(i).getGpa()%></label></td><td><form method="post" action="removequalification" name="<%=jobSeekerQualifications.get(i).getQualificationId()%>"><input type="hidden" name="qualificationID" value="<%=jobSeekerQualifications.get(i).getQualificationId()%>" /><input type="submit" value="x"/></form></td></tr>
						<%}%>
						</table>
			</td>
		</tr>
		<tr>
			<td valign="top" align="center">
				<form name="experienceForm" method="post" action="addexperience" id="form">
				
				<h4>Add Experience</h4>
					<table>
						<tr>
							<td style="height: 20px;">Company:</td>
							<td style="text-align: left"><input class="loginform_text"
								name="company" type="text" 
								style="width: 100px; height: 18px;"></td>
						</tr>
						<tr>
							<td style="height: 20px;">Position:</td>
							<td style="text-align: left"><input class="loginform_text"
								name="position" type="text" 
								style="width: 100px; height: 18px;"></td>
						</tr>
						<tr>
							<td style="height: 20px;">Year worked:</td>
							<td style="text-align: left"><input class="loginform_text"
								name="year" type="text" 
								style="width: 100px; height: 18px;"></td>
						</tr>
								
						
						<tr >
							
							<td colspan="2" style="text-align: left"><input
								name="save" type="submit" id="saveexp" value="Add"
								style="width: 100px; height: 18px;"></td>
						</tr>
		
					</table>
				
				</form>
			</td>
			<td valign="top">
					<table   border="1">
						<tr>
							<th style="width: 120px;">Company</th>
							<th style="width: 120px;">Position</th>
							<th style="width: 120px;">Years Worked On</th>
							<th style="width: 40px;">&nbsp;</th>
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
						<td>
						<form method="post" action="removeexperience" name="<%=jobSeekerExperiences.get(i).getExperienceId()%>">
						<input type="hidden" name="experienceID" value="<%=jobSeekerExperiences.get(i).getExperienceId()%>" />
						<input type="submit" value="x"/>
						</form>
						</td>
						</tr>
					<%}%>
					</table>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<form name="skillForm" method="POST" action="addskill" id="form">
				
				<h4>Add Skill</h4>
					<table>
						<tr>
							<td style="height: 20px;">Skill Name:</td>
							<td style="text-align: left">
								<select style="width: 120px;" name="skillName">
								
								
								<%
								for (Skill skill:skillList){
									boolean displaySkill=true;
									
									for (Skill skillAvail:jobSeekerSkillList){	
										if (skill.getSkillID()==skillAvail.getSkillID())
										{
											displaySkill=false;
											break;
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
							
							<td colspan="2" style="text-align: left"><input
								name="save" type="submit" id="saveexp" value="Add"
								style="width: 100px; height: 18px;"></td>
						</tr>
		
					</table>
				
				</form>
			</td>
			<td>
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
						<td style="width: 100px;" ><form method="post" action="removeskill" name="<%=jobSeekerSkillList.get(i).getName()%>"><label><%=jobSeekerSkillList.get(i).getName()%></label><input type="hidden" name="skillId" value="<%=jobSeekerSkillList.get(i).getSkillID()%>" /><input type="submit" value="x"/></form></td>
						
						<% if (i%4==3){%>
						</tr>
					<%}
					} %>	
					</table>
			</td>
		</tr>
		<tr>
			<td valign="top">		
				<form name="cvUploadForm" method="post" action="upload" enctype="multipart/form-data">
						<h3>
						 UPLOAD YOUR CV !
						</h3>
						<input type="hidden" name="form_name" value="cvUploadForm">
						
						<table class="uploadform_table" width="200px" >
							
							<tr>
				   				 <td style="text-align:left"><input class="" name="file" type="file" id="file"  style="width:200px;height:18px;"></td>
								<td style="text-align:left;vertical-align:bottom">
								<input type="hidden" name="type" id="type" value="CV"/>
								<input class="loginform_button" type="submit" name="upload" value="Upload" id="login"></td>
							
							</tr>
							
						</table>
				</form>
			</td>
			<td>
				<table>
					<tr>
						<td>
							<a href="<%=CVHref%>" name="CVFileLink" ><img src="images/CV_icon.png" height="20" width="20" /></a>
						</td>
						<td>
				<% if (CVUploadStatus!=null) {%>
					   				<label><%=CVUploadStatus%></label>
					   				<%}
					   				else{
					   				%>
					   				&nbsp;
					   				<%}%>
					   	 </td>
					  </tr>
				</table>			
			</td>	
		</tr>
	</table>
	</td>
	</tr>
	</table>
	<label><%=registerMsg%></label>
	

	</div>
</div>
</div>

</center>
</body>
</html>