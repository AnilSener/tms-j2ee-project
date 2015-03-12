<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ page import="model.JobSeeker,model.Employee"%>
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

JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
boolean isEmployee=false;
if (uname==null){
	uname=(JobSeeker)request.getSession().getAttribute("employee");
	isEmployee=true;
}

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
		<form name="jobseekerForm" method="post" action="jobseekerupdateinfo" id="form">

			<table>
				<tr>
					<td style="height: 20px;">User Name:</td>
					<td style="text-align: left"><input class="loginform_text"
						name="usernametxt" type="text" id="usernametxt" value="<%=uname.getUsername()%>" disabled="disabled"
						style="width: 100px; height: 18px;">
						<input 
						name="username" type="hidden" id="username" value="<%=uname.getUsername()%>" >
						</td>
				</tr>
				<tr>
					<td style="height: 20px;">First Name:</td>
					<td style="text-align: left"><input class="loginform_text"
						name="firstname" type="text" id="firstname" value="<%=uname.getFirstname()%>"
						style="width: 100px; height: 18px;"></td>
				</tr>
				
				<tr>
					<td style="height: 20px;">Last Name:</td>
					<td style="text-align: left"><input class="loginform_text"
						name="lastname" type="text" id="lastname" value="<%=uname.getLastname()%>"
						style="width: 100px; height: 18px;"></td>
				</tr>
			
				<tr>
					<td style="height: 20px;">Email :</td>
					<td style="text-align: left"><input class="loginform_text"
						name="email" type="text" id="email" value="<%=uname.getEmail()%>"
						style="width: 100px; height: 18px;"></td>
				</tr>
				
				<tr>
					<td style="height: 20px;">Address :</td>
					<td style="text-align: left"><input class="loginform_text"
						name="address" type="text" id="address" value="<%=uname.getAddress()%>"
						style="width: 100px; height: 18px;"></td>
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
					<td style="height: 20px;">Job :</td>
					<td style="text-align: left"><input class="loginform_text"
						name="job" type="text" id="job" value="<%=uname.getJob()%>" 
						<%=isEmployee?" readonly ":""%> style="width: 100px; height: 18px;"></td>
				</tr>
				
				<tr >
					
					<td colspan="2" style="text-align: left"><input
						name="save" type="submit" id="save" value="Save"
						style="width: 100px; height: 18px;"></td>
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