<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Employee"%>
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
		<% Employee uname =(Employee)request.getSession().getAttribute("employee");
		if(uname!=null){%>
		<b>You logged as <%=uname.getUsername()%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		
		<li><input type="button" onclick="location.href='employeehome.jsp' " value="Home"></li>
		<li><input type="button" onclick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onclick="location.href='savedetails.jsp' " value="Talent Profile"></li>
		<li><input type="button" onclick="location.href='myinterviewcalls.jsp' " value="Interview Calls"></li>
		<li><input type="button" onclick="location.href='jobseekerupdateinfo.jsp' " value="User Info"></li>
	
	</ul>
	</div>
	<div id="content" align="center">
	 <table width=582 border="1" align=center cellPadding=3 cellspacing="0" borderColor=#0000ff bgColor=#0000ff style="WIDTH: 582px; HEIGHT: 35px">
            <tr>
              <td>
              		<table style="WIDTH: 853px; HEIGHT: 151px" border="0" cellspacing="0"  cellPadding=3 width=853 bgColor=#f0f0ff align=left>
                 		 <tr>
                    		<td width="211" height="121" align="center" valign="middle">
                    		<font color="#000000"><br></font>
                        		<table style="WIDTH: 28px; HEIGHT: 63px" border="0" cellspacing="0" cellPadding=3 width=28 align=left>
                          			<tr>
                            			<td></td>
                          			</tr>
                        		</table>
                     <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff align=center>
                          <tr>
                            <td>
                            <font color="#000000">
                            <a class="five" href="queryjob?keyword=&sectorID=0&status=&appStatus=Applied&jobSeekerID=<%=uname.getJobseekerId()%>&experience=&cityID=0&companyname=&queryButton=Search%21" >
                            <img style="WIDTH: 87px; HEIGHT: 72px" border=0 hspace=0 src="images/appliedjobs.gif" width=200 height=97>
                            </a>
                            <a class="five" href="jobnotifications.jsp" ></a>
                            </font>
                            </td>
                          </tr>
                      </table>
	                      <font color="#000000">
	                      <br>
	                      </font>
                      </td>
                    <td width="206"><font size="+0">
                    <font><br>
                    </font>
                    <font color="#400080" size="4" face="Arial Rounded MT Bold"><a class="five" href="queryjob?keyword=&sectorID=0&status=&appStatus=Applied&jobSeekerID=<%=uname.getJobseekerId()%>&experience=&cityID=0&companyname=&queryButton=Search%21" >
                    Applied Jobs
                    </a>
	                    </font>
	                    <font color=#400080 size=4 face="Arial Rounded MT Bold">
	                    <a class="five" href="jobnotifications.jsp" >
	                    </a> 
	                    </font>
	                    </font>
                    </td>
                    <td width="183" align="center" valign="middle">
                    	<table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff align=center>
                          <tr>
                            <td>
                            <a class="five" href="queryjob?keyword=&sectorID=0&status=&companyname=&experience=&countryID=0&cityID=0&appStatus=Hired&jobSeekerID=<%=uname.getJobseekerId()%>&&queryButton=Search%21" ><img style="WIDTH: 87px; HEIGHT: 75px" border="0" hspace="0" src="images/feedback.jpg" width="70" height="70" >
                            </a>
                            </td>
                          </tr>
                      </table>
                      </td>
                    <td width="229">
                    <font size="+0">
                    <font color=#400080 size=4 face="Arial Rounded MT Bold">
                    <a class="five" href="queryjob?keyword=&sectorID=0&status=&companyname=&experience=&countryID=0&cityID=0&appStatus=Hired&jobSeekerID=<%=uname.getJobseekerId()%>&&queryButton=Search%21" >Application Feedback</a><br>
                    </font>
                    </font>
                    </td>
                  </tr>
                  <tr>
                    <td align="center" valign="middle"></td>
                    <td></td>
                    <td align="center" valign="middle"></td>
                    <td></td>
                  </tr>
                  <tr>
                    <td height="108" align="center" valign="middle">
                    <table style="WIDTH: 28px; HEIGHT: 63px" border="0" cellspacing="0" cellPadding=3 width=28 align=left>
                        <tr>
                          <td></td>
                        </tr>
                    </table>
                        <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff align=center>
                          <tr>
                            <td>
                            <a class="five" href="talentdetails.jsp?jobSeekerID=<%=uname.getJobseekerId()%>" ><img style="WIDTH: 87px; HEIGHT: 75px" border="0" hspace="0" src="images/employment_profile.jpg" width="70" height="70" >
                            </a>
                            </td>
                          </tr>
                      </table>
                      </td>
                    <td>
                    <font color="#400080" size="4" face="Arial Rounded MT Bold">
                    <br>
                          <a class="five" href="talentdetails.jsp?jobSeekerID=<%=uname.getJobseekerId()%>" >Employment Profile</a><br>
                      <br>
                    </font>
                    </td>
                    <td align="center" valign="middle">
                    <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" borderColor=#ffb062 cellPadding=3 width=117 bgColor=#8000ff align=center>
                          <tr>
                            <td width="107">
                            <a class="five" href="application.jsp" ></a><a class="five" href="myprojectassignments.jsp" >
                            <img style="WIDTH: 84px; HEIGHT: 74px" border="0" hspace="0" src="images/project_assignments.png" width="142" height="122" >
                            </a>
                            </td>
                          </tr>
                      </table>
                      </td>
                    	<td>&nbsp; <br>
                      <font size="4" face="Arial &#13;&#10; &#13;&#10;      &#13;&#10; &#13;&#10; &#13;&#10; &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           Rounded &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           MT &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           Bold">
                      <font color="#400080">
                      <font face="Arial Rounded MT Bold"><a class="five" href="myprojectassignments.jsp" >
                      My Project Assignments
                      </a>
                      </font>
                      </font>
                      </font>
                      </td>
                  </tr>
                  <tr>
                    <td align="center" valign="middle"></td>
                    <td></td>
                    <td align="center" valign="middle"></td>
                    <td></td>
                  </tr>
                  <tr>
                    <td height="99" align="center" valign="middle">
                    <table style="WIDTH: 28px; HEIGHT: 63px" border="0" cellspacing="0" cellPadding=3 width=28 align=left>
                        <tr>
                          <td></td>
                        </tr>
                    </table>
                        <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff align=center>
                          <tr>
                            <td>
                            <a class="five" href="myperformancefeedbacks.jsp" >
                            <img style="WIDTH: 89px; HEIGHT: 70px" border=0 hspace=0 src="images/performance_feedbacks.png" width=200 height=200>
                            </a>
                            </td>
                          </tr>
                      </table>
                      </td>
                    <td>
                    <font color="#400080" size="4"  face="Arial Rounded MT Bold"> <a class="five" href="myperformancefeedbacks.jsp" >
                    My Performance Feedbacks
                    <br>
                    </a>
                    </font>
                    </td>
                    <td align="center" valign="middle"><table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff  align=center>
                        <tr>
                          <td>
                          <a class="five" href="mytimesheets.jsp" ><img style="WIDTH: 92px; HEIGHT: 67px" border="0" hspace="0" src="images/timesheet.png" width="400" height="300" ></a>
                          </td>
                        </tr>
                    </table></td>
                    <td>
                    <font color="#400080" size="4" face="Arial Rounded MT Bold"><a class="five" href="mytimesheets.jsp" >
		            My Timesheets
		            <br>
                    </a>
                    </font>
                    </td>
                  </tr>
					<tr>
                    <td align="center" valign="middle"></td>
                    <td></td>
                    <td align="center" valign="middle"></td>
                    <td></td>
                  </tr>
                  <tr>
                    <td height="99" align="center" valign="middle">
                    <table style="WIDTH: 28px; HEIGHT: 63px" border="0" cellspacing="0" cellPadding=3 width=28 align=left>
                        <tr>
                          <td></td>
                        </tr>
                    </table>
                        <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff align=center>
                          <tr>
                            <td>
                            <a class="five" href="myteamperformanceeval.jsp" >
                            <img style="WIDTH: 89px; HEIGHT: 70px" border=0 hspace=0 src="images/performance_eval.jpg" width=200 height=200>
                            </a>
                            </td>
                          </tr>
                      </table>
                      </td>
                    <td>
                    <font color="#400080" size="4"  face="Arial Rounded MT Bold"> <a class="five" href="myteamperformanceeval.jsp" >
                    My Team's Performance Evaluations
                    <br>
                    </a>
                    </font>
                    </td>
                    <td align="center" valign="middle"><table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff  align=center>
                        <tr>
                          <td>
                          <a class="five" href="myteamtimesheets.jsp" ><img style="WIDTH: 92px; HEIGHT: 67px" border="0" hspace="0" src="images/team_timesheet.jpg" width="400" height="300" ></a>
                          </td>
                        </tr>
                    </table></td>
                    <td>
                    <font color="#400080" size="4" face="Arial Rounded MT Bold"><a class="five" href="myteamtimesheets.jsp" >
		            My Team's Timesheets
		            <br>
                    </a>
                    </font>
                    </td>
                  </tr>
                  <tr>
                    <td align="center" valign="middle"><p><br>
                      &nbsp;</p></td>
                    <td></td>
                    <td align="center" valign="middle"></td>
                    <td></td>
                  </tr>

              </table>
              <table style="WIDTH: 28px; HEIGHT: 63px" border="0" cellspacing="0" cellPadding=3 width=28 align=left>
                        <tr>
                          <td></td>
                        </tr>
                    </table>
                        
				</table>
             
		 </div>   
</div>
<div id="footer">Copyright © 2014 ...</div>
</div>
</center>
</body>
</html>