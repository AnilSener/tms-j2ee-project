<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,model.Employer,model.Assignment,model.Employee,model.Evaluation,model.Factor" %>
  <%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>

<body><center> <div align="center">
<div id="top" >
	<%
	String registerMsg="";
	String error=(String)request.getAttribute("error");
	if (error!=null)
	registerMsg=error;
	
	Employee uname =(Employee)request.getSession().getAttribute("employee");
	Employer unameEmp=null;
	if(request.getSession().getAttribute("employee")==null)
		unameEmp =(Employer)request.getSession().getAttribute("employerBean");
	int evaluationID=0;
	
	if(request.getParameter("id")!=null)
		evaluationID=Integer.parseInt(request.getParameter("id"));
	else if(request.getAttribute("id")!=null)
		evaluationID=((Integer)request.getAttribute("id")).intValue();
	
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	
	Evaluation evaluation=null;
	if (evaluationID!=0){
		//Timesheet object
		DefaultHttpClient httpClient = new DefaultHttpClient();
	  	HttpGet httpGet = new HttpGet(basePath+"/getEvaluationDataByIDServlet?evaluationid="+evaluationID);
	  	
	  	HttpResponse httpResponse = httpClient.execute(httpGet);
	  	HttpEntity httpEntity = httpResponse.getEntity();
	  	InputStream is = httpEntity.getContent();
	  	ObjectInputStream in = new ObjectInputStream(is);
	  	evaluation=(Evaluation) in.readObject();
	  	httpClient.close();
		
	  	boolean isSupervisor=false;
	  	if(uname!=null && evaluation.getSupervisorID()==uname.getId())
	  		isSupervisor=true;
	  	boolean isEmployee=false;
	  	if(uname!=null && evaluation.getEmployeeID()==uname.getId())
	  		isEmployee=true;
	  	
	  	boolean timesheetReadOnly=false;
	  	if ((isSupervisor && evaluation.getStatus().equals("Submitted"))  || isEmployee || (!isSupervisor && !isEmployee))
	  		timesheetReadOnly=true;
	  	
	  	//Employee object
	  	DefaultHttpClient httpClient4 = new DefaultHttpClient();
	  	HttpGet httpGet4 = new HttpGet(basePath+"/getEmployeeByIDServlet?employeeID="+evaluation.getEmployeeID());
	  	
	  	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
	  	HttpEntity httpEntity4 = httpResponse4.getEntity();
	  	InputStream is4 = httpEntity4.getContent();
	  	ObjectInputStream in4 = new ObjectInputStream(is4);
	  	Employee employee=(Employee) in4.readObject();
	  	httpClient4.close();
		String fullName=employee.getFirstname()+" "+employee.getLastname();
		
		//To retrieve reviewer/supervisor
		DefaultHttpClient httpClient3 = new DefaultHttpClient();
	  	HttpGet httpGet3 = new HttpGet(basePath+"/getEmployeeByIDServlet?employeeID="+evaluation.getSupervisorID());
	  	
	  	HttpResponse httpResponse3 = httpClient3.execute(httpGet3);
	  	HttpEntity httpEntity3 = httpResponse3.getEntity();
	  	InputStream is3 = httpEntity3.getContent();
	  	ObjectInputStream in3 = new ObjectInputStream(is3);
	  	Employee supervisor=(Employee) in3.readObject();
	  	httpClient3.close();
	  	String fullName2=supervisor.getFirstname()+" "+supervisor.getLastname();
	  	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		String reportHref="retrievefile?type=performance_evaluation_report&contenttype=application/vnd.openxmlformats-officedocument.spreadsheetml.sheet&fileID="+evaluation.getReportFileID();
		
	%>
	
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
	<%if(uname!=null){%>
<b>You logged as <%=uname.getUsername()%></b><% }
	else if(unameEmp!=null){%>
	<b>You logged as <%=unameEmp.getUsername()
%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<%if (unameEmp!=null){ %>
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
	
				          
					<form name="evaluationform" method="post" action="updateEvaluation" id="evaluationform">
			
						<table>
							<tr>
								<td style="height: 20px;">Status:</td>
								<td style="text-align: left">
								
								<input class="loginform_text" name="status" id="status" type="text"  value="<%=evaluation.getStatus()%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								
								</td>	
											
							</tr>
							<tr>
								<td style="height: 20px;">Created Date:</td>
								<td style="text-align: left">
								<input class="loginform_text" name="createdDate" id="createdDate" type="text"  value="<%=sdf.format(evaluation.getCreatedDate())%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								</td>
												
							</tr>
							<tr>
								<td style="height: 20px;">Employee:</td>
								<td style="text-align: left">
								
								<input class="loginform_text" name="employeeName" id="employeeName" type="text"  value="<%=fullName%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								<input  name="employeeID" id="employeeID" type="hidden"  value="<%=evaluation.getEmployeeID()%>"  style="width: 100px; height: 18px;"  />
						
								</td>
												
							</tr>
							<tr>
								<td style="height: 20px;">Evaluator:</td>
								<td style="text-align: left">
								
								<input class="loginform_text" name="supervisorName" id="supervisorName" type="text"  value="<%=fullName2%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								<input  name="supervisorID" id="supervisorID" type="hidden"  value="<%=evaluation.getSupervisorID()%>"  style="width: 100px; height: 18px;"  />
						
								</td>
												
							</tr>
							<tr>
								<td style="height: 20px;">Start Date:</td>
								<td style="text-align: left">
								<input class="loginform_text" name="startDateDisplay" id="startDateDisplay" type="text"  value="<%=formatter.format(evaluation.getStartDate())%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								<input  name="startDate" id="startDate" type="hidden"  value="<%=formatter.format(evaluation.getStartDate())%>"  />
	
								</td>
												
							</tr>
							<tr>
								<td style="height: 20px;">End Date:</td>
								<td style="text-align: left">
								<input class="loginform_text" name="endDateDisplay" id="endDateDisplay" type="text"  value="<%=formatter.format(evaluation.getEndDate())%>" readonly="readonly" style="width: 100px; height: 18px;"  />
								<input  name="endDate" id="endDate" type="hidden"  value="<%=formatter.format(evaluation.getEndDate())%>"  />
	
								</td>
												
							</tr>
							<tr>
								<td style="height: 20px;">Performance Evaluation Report:</td>
								<td style="text-align: left">
								<a href="<%=reportHref%>" name="reportFileLink" ><img src="images/report.png" height="40" width="40" /></a>
								</td>
												
							</tr>
							<tr>
							<td style="height: 10px;"></td>
							<td style="text-align: left">
							
							
							</td>
							</tr>
							
						</table>
						<table id="lineItems" border=2>
								<tr><th>Factor Name</th><th>Score</th><th>Comments</th></tr>
								<%
								
								List<Factor> factorList=evaluation.getFactors();
								for (int i=0;i<factorList.size();i++){
									
								%>
								<tr <%=i==(factorList.size()-1)?" style='background-color: #867970;'":"style='background-color: #EFECE5;'"%>>
								<td>
								<label><b><%=factorList.get(i).getName()%></b></label>
								<input  name="factorName" id="factorName" type="hidden"  value="<%=factorList.get(i).getName()%>"  style="width: 100px; height: 18px;"  />
								</td>
								<td>
								<label><b></b></label>
								
								<INPUT TYPE="radio" ID="option1" NAME="scores<%=i%>" VALUE="1" <%=factorList.get(i).getScore()==1?" checked":""%> <%=timesheetReadOnly?" disabled":""%> <%=i==(factorList.size()-1)?"disabled":""%>>
								<label for="option1">1-Poor</label>
								<INPUT TYPE="radio" ID="option2" NAME="scores<%=i%>" VALUE="2" <%=factorList.get(i).getScore()==2?" checked":""%> <%=timesheetReadOnly?" disabled":""%> <%=i==(factorList.size()-1)?"disabled":""%>>
								<label for="option2">2-Fair</label>
								<INPUT TYPE="radio" ID="option3" NAME="scores<%=i%>" VALUE="3" <%=factorList.get(i).getScore()==3?" checked":""%> <%=timesheetReadOnly?" disabled":""%> <%=i==(factorList.size()-1)?"disabled":""%>>
							    <label for="option3">3-Average</label>
							    <INPUT TYPE="radio" ID="option4" NAME="scores<%=i%>" VALUE="4" <%=factorList.get(i).getScore()==4?" checked":""%> <%=timesheetReadOnly?" disabled":""%> <%=i==(factorList.size()-1)?"disabled":""%>>
								<label for="option4">4-Good</label>
								<INPUT TYPE="radio" ID="option5" NAME="scores<%=i%>" VALUE="5" <%=factorList.get(i).getScore()==5?" checked":""%> <%=timesheetReadOnly?" disabled":""%> <%=i==(factorList.size()-1)?"disabled":""%>>
								<label for="option5">5-Excellent</label>
								
								</td>
								<td>
								<%if(i!=(factorList.size()-1)){ %>
								<textarea name="comment" id="comment" cols="20" rows="3" <%=timesheetReadOnly?" disabled":""%>><%=factorList.get(i).getComment()%></textarea>
								<%} %>
								</td>
								</tr>
								<% 
								} %>
						</table>	
						<table>
						<%if(!isEmployee){ %>
						<tr>
						<td>
						Hidden Notes: <br/>(This field will be <br/> confidential to only <br/> HR and Supervisor)
						</td>
						<td>
						<textarea name="notes" id="notes" cols="60" rows="5" <%=timesheetReadOnly?" disabled":""%>><%=evaluation.getNotes()%></textarea>
						</td>
						</tr>
						<%} %>
								<%if(isSupervisor){ %>
							<tr>
								<td style="height: 20px;">
								
								<input name="Save" type="submit" id="Save" value="Save" style="width: 120px; height: 20px;" <%=timesheetReadOnly?" disabled":""%> />
								
								</td>
								<td style="text-align: left">
								
								<input name="Submit" type="submit" id="Submit" value="Submit" style="width: 120px; height: 20px;" <%=timesheetReadOnly?" disabled":""%>/>
								
								</td>
								
							</tr>
							<%} %>
						</table>
					</form>
			<%} %>		
				<label class="errorMessage"><%=registerMsg%></label>
	</div>
	<div id="footer">Copyright DGNet LTD © 2014</div>
</div>
	</div>
	
	


</center>
</body>
</html>