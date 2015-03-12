<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<a href="login.jsp" style=" padding-right: 20px; padding-top:20px"> <b>Login as a Job Seeker/Employee</b></a><br/>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<li><input type="button" onclick="location.href='index.jsp' " value="Home"></li>
		<li><input type="button" onclick="location.href='searchjob.jsp' " value="Search Jobs"></li>	
		<li><input type="button" onclick="location.href='jobseekersignup.jsp'" value="Register as JobSeeker "></li>
		<li><input type="button" onclick="location.href='employersignup.jsp' " value="Register as Employer"></li>
		<li><input type="button" onclick="location.href='aboutbestjobs.jsp' " value="About Best Jobs"></li>
	</ul>
	</div>
	<div id="content">
	
	
		 <% 
		 String success=(String)request.getAttribute("success");
			
			if (success!=null){
				
				out.println(success);
			}
			
		 String e = (String) session.getAttribute("error" );
            if(e != null)
            {
                out.println(e); }
                
            if(request.getAttribute("mailresultmessage" )!= null){
            	out.println((String) request.getAttribute("mailresultmessage" ));
            }     
                
          String imageUploadStatus=(String)request.getAttribute("status"); 
          if (imageUploadStatus!=null){
				
				out.println(imageUploadStatus);
			}
          
          
                %>
                
	<div id="LoginForm" >
	<form name="loginform" method="post" action="employerlogin" id="loginform">
		<input type="hidden" name="form_name" value="loginform">
		<table class="loginform_table" >
			<tr>
  				 <td class="loginform_header" align="left"><h2><b>Log In</b></h2></td>
			</tr>
			<tr>
   				 <td style="height:20px;"><b>User Name:</b></td>
   				 <td style="text-align:left"><input class="loginform_text" name="username" type="text" id="username"  style="width:150px;height:18px;"></td>
			</tr>
			<tr>
  				 <td style="height:20px"><b>Password:</b></td>
   				 <td style="text-align:left"><input class="loginform_text" name="password" type="password" id="password" style="width:150px;height:18px;"></td>
			</tr>
			<tr>
   				<td>&nbsp;</td><td style="text-align:left;vertical-align:bottom"><input class="loginform_button" type="submit" name="login" value="Log In" id="login"></td>
			</tr>
		</table>
	</form>
	</div>
	</div>
	<div id="footer">Copyright © 2014 ...</div>
</div>
</div>

</center>
</body>
</html>