<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Employer"%>
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
		<% Employer uname =(Employer)request.getSession().getAttribute("employerBean");
		
		if(uname!=null){%>
		<b>You logged as <%=uname.getUsername()%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		

	</ul>
	</div>
	


<div id="content" align="center">
<table class="inactivetable" >
			<tr>
  				 <td align="center"><h2><b><font color="#FF0000">EMPLOYER SUBSCRIPTION INACTIVE</font></b></h2></td>
			</tr>
			<tr>
   				 <td align="center" style="height:20px;"><label><i> Your Best Jobs Employer Account is currently inactive. If you have registered recently it might be activated in 48 hours. In order to receive more information please contact us via <a href="mailto=bestjobs@gmail.com">bestjobs@gmail.com</a></i></label></td>
   				 
			</tr>
		</table>
</div>
</div>
<div id="footer">Copyright DGNet LTD © 2014</div>
</div>
</center>
</body>
</html>