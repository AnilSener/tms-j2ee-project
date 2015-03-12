<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="model.Employer"%>
    <%@ page import="DAO.DataManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cloud.github.com/downloads/digitalBush/jquery.maskedinput/jquery.maskedinput-1.3.min.js"></script>
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/phonenumbermasking.js"></script>
</head>
<% 
String registerMsg="";
String error=(String)request.getAttribute("error");
String success=(String)request.getAttribute("success");
if (success!=null)
	registerMsg=success;	
else if (error!=null)
	registerMsg=error;
Employer uname =(Employer)request.getSession().getAttribute("employerBean");
String imageHref="retrievefile?type=logo_image&contenttype=image/gif&username="+uname.getUsername();
String imageUploadStatus=(String)request.getAttribute("status"); 

%>

<body onload="document.getElementById('txtPhoneNumber').value=document.getElementById('phone').value;"><center> <div align="center">
<div id="top" >
	
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
		<form name="employeeForm" method="post" action="employerupdateinfo" id="form" enctype="multipart/form-data">
			<table >
				<tr valign="top">
					<td>
					<table>
						<tr>
							<td style="height: 20px;">User Name:</td>
							<td style="text-align: left"><input class="loginform_text"
								name="usernametxt" type="text" id="usernametxt" disabled="disabled" value="<%=uname.getUsername()%>"
								style="width: 100px; height: 18px;">
								<input class="loginform_text"
								name="username" type="hidden" id="username" value="<%=uname.getUsername()%>">
								</td>
						</tr>
						
						<tr>
							<td style="height: 20px;">Password :</td>
							<td style="text-align: left"><input class="loginform_text"
								name="password" type="password" id="password" value="<%=uname.getPassword()%>"
								style="width: 100px; height: 18px;"></td>
						</tr>
						
							<tr>
							<td style="height: 20px;">Phone :</td>
							<td style="text-align: left"><input class="loginform_text"
								name="txtPhoneNumber" type="text" id="txtPhoneNumber" value="${param.phone}"
								style="width: 100px; height: 18px;">
								<input class="loginform_text"
								name="phone" type="hidden" id="phone" value="<%=uname.getPhone()%>"
								style="width: 100px; height: 18px;">
								</td>
						</tr>
						
						<tr>
							<td style="height: 20px;">Company Name :</td>
							<td style="text-align: left"><input class="loginform_text"
								name="companyname" type="text" id="companyname" value="<%=uname.getCompanyName()%>"
								style="width: 100px; height: 18px;"></td>
						</tr>
						
						<tr>
							<td style="height: 20px;">Company Email :</td>
							<td style="text-align: left"><input class="loginform_text"
								name="email" type="text" id="email" value="<%=uname.getEmail()%>"
								style="width: 100px; height: 18px;"></td>
						</tr>
						<tr>
							<td style="height: 20px;">Company Address:</td>
							<td style="text-align: left"><input class="loginform_text"
								name="address" type="text" id="address" value="<%=uname.getAddress()%>"
								style="width: 100px; height: 18px;"></td>
						</tr>
						<tr valign="top">
						<td style="height: 20px;">Company Description :</td>
						<td style="text-align: left">
						<textarea name="description" id="description" cols="30" rows="10"><%=uname.getDescription()%></textarea></td>
						</tr>
						<tr>
							
							<td colspan="2" style="text-align: left"><input
								name="save" type="submit" id="save" value="Save" 
								style="width: 100px; height: 18px;"></td>
						</tr>
					</table>
					</td>
					<td>
					<table >
							<tr>
							<td></td>
								<td style="border:2pt solid black;">
									<img src="<%=imageHref%>" height="200" width="200" name="profileImage"/>
								</td>
								
							  </tr>
							  <tr>
							  <td></td>
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
							  <tr>
						   				 <td style="text-align:left">Upload Logo:</td>
										<td style="text-align:left;vertical-align:bottom">
										<input type="hidden" name="type" id="type" value="logo_image"/>
										<input class="" name="file" type="file" id="file"  style="width:200px;height:18px;">
										</td>
									
						</tr>
						</table>
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