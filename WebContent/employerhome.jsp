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
		request.getSession().setAttribute("selectedJobSkillList",null);
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
	


<div id="content" align="center">
<table width=582 border="1" align=center cellPadding=3 cellspacing="0" 
borderColor=#0000ff bgColor=#0000ff style="WIDTH: 582px; HEIGHT: 35px">
            <tr>
              <td height="497"><table style="WIDTH: 853px; HEIGHT: 151px" border="0" cellspacing="0"  cellPadding=3 width=853 bgColor=#f0f0ff align=left>
                 		 <tr>
                    		<td width="211" height="121" align="center" valign="middle">
                    		<font color="#000000"><br></font>
                        		<table style="WIDTH: 28px; HEIGHT: 63px" border="0" cellspacing="0" cellPadding=3 width=28 align=left>
                          			<tr>
                            			<td></td>
                          			</tr>
                        		</table>
                                  
                              <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" 
            borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff 
            align=center>
                                <tr>
                                  <td><a class="five" href="postnewjob.jsp" ><img style="WIDTH: 85px; HEIGHT: 68px" border="0" hspace="0" 
                  src="images/post.png" width=255 
            height=233></a></td>
                                </tr>
                              </table>
                          <br>
                       </td>
                        <td><font size="+0"><font color="#400080" size="4" 
                                    face="Arial Rounded MT Bold">&nbsp;<a class="five" href="postnewjob.jsp" >Post&nbsp;a 
                          Job</a></font> </font></td>
                        <td><br>
                          &nbsp;&nbsp;&nbsp;
                          <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" 
            borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff 
            align=center>
                            <tr>
                              <td><a class="five" href="companyinfo.jsp" ></a><a class="five" href="employerdashboard.jsp" ><img 
                  style="WIDTH: 87px; HEIGHT: 71px" border=0 hspace=0 
                  src="images/postedjobs.jpg" width=400 
          height=300></a></td>
                            </tr>
                          </table>
                             </td>
                        <td><font size="+0"><font color="#400080" size="4" 
                                    face="Arial Rounded MT Bold"><a class="five" href="employerdashboard.jsp" >View&nbsp;Posted 
                            Jobs</a></font>&nbsp;<font color="#400080" size="4" 
                                    face="Arial Rounded MT Bold"><a class="five" href="companyinfo.jsp" ></a></font> </font></td>
                      </tr>
                      <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                      </tr>
                      <tr>
                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <table style="WIDTH: 28px; HEIGHT: 63px" border="0" cellspacing="0" 
            cellPadding=3 width=28 align=left>
                              <tr>
                                <td></td>
                              </tr>
                            </table>
                         
                            <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" 
            borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff 
            align=center>
                              <tr>
                                <td><a class="five" href="queryjobapplication?title=&firstName=&status=Open&appStatus=Applied&experience=&lastName=&queryButton=Search%21" ><img 
                  style="WIDTH: 85px; HEIGHT: 74px" border=0 hspace=0 
                  src="images/jobapplication.jpg" width=426 
            height=282></a></td>
                              </tr>
                            </table></td>
                        <td><font color="#400080" size="4" face="Arial Rounded MT Bold"><br>
                              <a class="five" href="queryjobapplication?title=&firstName=&status=Open&appStatus=Applied&experience=&lastName=&queryButton=Search%21" >Job 
                          Applications</a>&nbsp;<br>
                          <br>
                        </font></td>
                        <td>
                          <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" 
            borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff 
            align=center>
                            <tr>
                              <td><a class="five" href="postnewproject.jsp" ><img style="WIDTH: 84px; 
                  HEIGHT: 74px" border="0" hspace="0" src="images/project_assignments.png" width="142" height="122" ></a></td>
                            </tr>
                          </table></td>
                        <td><font >
                          </font><font size="4" 
            face="Arial &#13;&#10; &#13;&#10;      &#13;&#10; &#13;&#10; &#13;&#10; &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           Rounded &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           MT &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10; &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           &#13;&#10;           Bold"><font 
            color=#400080><font 
                          face="Arial Rounded MT Bold"><a class="five" href="postnewproject.jsp" >Create a New Project</a></font>&nbsp;&nbsp;</font></font><br>                        </td>
                      </tr>
                      <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                      </tr>
                      <tr>
                        <td><table style="WIDTH: 28px; HEIGHT: 63px" border="0" cellspacing="0" 
            cellPadding=3 width=28 align=left>
                            <tr>
                              <td></td>
                            </tr>
                        </table>
                            <br>
                            <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" 
            borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff 
            align=center>
                              <tr>
                                <td><a class="five" href="newperformanceeval.jsp" ><img style="WIDTH: 92px; HEIGHT: 67px" border="0" hspace="0" src="images/performance_feedbacks.png" width="400" height="300" ></a></td>
                              </tr>
                            </table></td>
                        <td><font color="#400080" size="4" face="Arial Rounded MT Bold"><a class="five" href="newperformanceeval.jsp" >Create Performance Evaluation</a><br>
                        </font></td>
                        <td>
                            <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" 
            borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff 
            align=center>
                              <tr>
                                <td><a class="five" href="newmonthlytimesheet.jsp" ><img 
                  style="WIDTH: 93px; HEIGHT: 69px" border=0 hspace=0 
                  src="images/monthly_timesheet.png" width=320 
            height=400></a></td>
                              </tr>
                            </table></td>
                        <td><font size="+0"><font color="#400080" size="4" 
                                    face="Arial Rounded MT Bold"><a class="five" href="newmonthlytimesheet.jsp" >Create Monthly Timesheet</a></font></font><font color="#400080" size="4" 
                    face="Arial Rounded MT Bold">
                        </font></td>
                      </tr>
                      <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                      </tr>
                      <tr>
                        <td><table style="WIDTH: 28px; HEIGHT: 63px" border="0" cellspacing="0" 
            cellPadding=3 width=28 align=left>
                            <tr>
                              <td></td>
                            </tr>
                        </table>
                            <br>
                            <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" 
            borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff 
            align=center>
                              <tr>
                                <td><a class="five" href="performanceevals.jsp" ><img style="WIDTH: 92px; HEIGHT: 67px" border="0" hspace="0" src="images/performance_eval.jpg" width="400" height="300" ></a></td>
                              </tr>
                            </table></td>
                        <td><font color="#400080" size="4" face="Arial Rounded MT Bold"><a class="five" href="performanceevals.jsp" >All Performance Evaluations</a><br>
                        </font></td>
                        <td>
                            <table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" 
            borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff 
            align=center>
                              <tr>
                                <td><a class="five" href="timesheets.jsp" ><img 
                  style="WIDTH: 93px; HEIGHT: 69px" border=0 hspace=0 
                  src="images/time_sheet.png" width=320 
            height=400></a></td>
                              </tr>
                            </table></td>
                        <td><font size="+0"><font color="#400080" size="4" 
                                    face="Arial Rounded MT Bold"><a class="five" href="timesheets.jsp" >All Timesheets</a></font></font><font color="#400080" size="4" 
                    face="Arial Rounded MT Bold">
                        </font></td>
                      </tr>
                      <tr>
                        <td height="54"></td>
                        <td></td>
                        <td></td>
                        <td></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
		 </table>
</div>
</div>
<div id="footer">Copyright DGNet LTD © 2014</div>
</div>
</center>
</body>
</html>