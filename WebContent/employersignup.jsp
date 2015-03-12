<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

if (error!=null){
	
		registerMsg=error;
}

%>

<body><center> <div align="center">
<div id="top" >
	
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
		<a href="login.jsp" style=" padding-right: 20px; padding-top:20px"> <b>Login as a Job Seeker/Employee</b></a><br/>	
		<a href="employerlogin.jsp" style=" padding-right: 20px;"> <b>Login as an Employer</b></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<li><input type="button" onclick="location.href='index.jsp' " value="Home"></li>
		<li><input type="button" onclick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onclick="location.href='jobseekersignup.jsp' " value="Register as JobSeeker "></li>
		<li><input type="button" onclick="location.href='employersignup.jsp' " value="Register as Employer"></li>
		<li><input type="button" onclick="location.href='aboutbestjobs.jsp' " value="About Best Jobs"></li>
	</ul>
	</div>
	<div id="content">
		<form name="employeeForm" method="post" action="employersignup" id="employeeForm" enctype="multipart/form-data">
	
					<table>
						<tr>
							<td style="height: 20px;">User Name:</td>
							<td style="text-align: left"><input class="loginform_text"
								name="username" type="text" id="username" value="${param.username}"
								style="width: 100px; height: 18px;"></td>
						</tr>
						
						<tr>
							<td style="height: 20px;">Password :</td>
							<td style="text-align: left"><input class="loginform_text"
								name="password" type="password" id="password" value="${param.password}"
								style="width: 100px; height: 18px;"></td>
						</tr>
						
							<tr>
							<td style="height: 20px;">Phone :</td>
							<td style="text-align: left"><input class="loginform_text"
								name="txtPhoneNumber" type="text" id="txtPhoneNumber" value="${param.txtPhoneNumber}"
								style="width: 100px; height: 18px;">
								<input 
								name="phone" type="hidden" id="phone" value="${param.phone}"
								style="width: 100px; height: 18px;">
								
								</td>
						</tr>
						
						<tr>
							<td style="height: 20px;">Company Name :</td>
							<td style="text-align: left"><input class="loginform_text"
								name="companyname" type="text" id="companyname" value="${param.companyname}"
								style="width: 100px; height: 18px;"></td>
						</tr>
						
							<tr>
							<td style="height: 20px;">Email :</td>
							<td style="text-align: left"><input class="loginform_text"
								name="email" type="text" id="email" value="${param.email}"
								style="width: 100px; height: 18px;"></td>
						</tr>
						<tr>
							<td style="height: 20px;">Company Address:</td>
							<td style="text-align: left"><input class="loginform_text"
								name="address" type="text" id="address" value="${param.address}"
								style="width: 100px; height: 18px;"></td>
						</tr>
						<tr valign="top">
						<td style="height: 20px;">Company Description :</td>
						<td style="text-align: left">
						<textarea name="description" id="description" cols="30" rows="10">${param.description}</textarea></td>
						</tr>
						<tr>
						   	<td style="text-align:left">Upload Logo:</td>
							<td style="text-align:left;vertical-align:bottom">
								<input type="hidden" name="type" id="type" value="logo_image"/>
								<input class="" name="file" type="file" id="file"  style="width:200px;height:18px;">
							</td>
						</tr>
						
						<tr>
							
							<td colspan="2" style="text-align: left">
							<input type="hidden" name="type" id="type" value="logo_image"/>
							<input name="save" type="submit" id="save" value="Save"
								style="width: 100px; height: 18px;"></td>
						</tr>
						<tr>
							
							<td colspan="2" style="text-align: left"></td>
						</tr>
					</table>
		
						
		</form>
		<label class="errorMessage"><%=registerMsg%></label>
	</div>
	
	</div>
	<div id="footer">Copyright © 2014 ...</div>
</div>


</center>
</body>
</html>