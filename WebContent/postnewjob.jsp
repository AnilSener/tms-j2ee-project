<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,model.Skill,model.Employer,model.Sector,model.Country,model.City" %>
 <%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.11.1.js" type="text/javascript"></script>
<script type="text/javascript" src="js/getfieldsbycountry.js"></script>
</head>
<script type="text/javascript">
function storeJobFields(){
document.getElementById('hiddenTitle').value=document.getElementById('title').value;
document.getElementById('hiddenSectorID').value=document.getElementById('sectorID').value;
document.getElementById('hiddenCountryID').value=document.getElementById('countryID').value;
document.getElementById('hiddenCityID').value=document.getElementById('cityID').value;
document.getElementById('hiddenSalary').value=document.getElementById('salary').value;
document.getElementById('hiddenExperience').value=document.getElementById('experience').value;
document.getElementById('hiddenDescription').value=document.getElementById('description').value;
document.getElementById('hiddenTitle2').value=document.getElementById('title').value;
document.getElementById('hiddenSectorID2').value=document.getElementById('sectorID').value;
document.getElementById('hiddenCountryID2').value=document.getElementById('countryID').value;
document.getElementById('hiddenCityID2').value=document.getElementById('cityID').value;
document.getElementById('hiddenSalary2').value=document.getElementById('Salary').value;
document.getElementById('hiddenExperience2').value=document.getElementById('Experience').value;
document.getElementById('hiddenDescription2').value=document.getElementById('description').value;
}
</script>


<body><center> <div align="center">
<div id="top" >
	<%
	String registerMsg="";
	String error=(String)request.getAttribute("error");
	if (error!=null)
	registerMsg=error;
		
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	Employer uname =(Employer)request.getSession().getAttribute("employerBean");
	String hiddenTitle=request.getParameter("hiddenTitle");
	
	if (hiddenTitle==null)
		hiddenTitle=request.getParameter("hiddenTitle2");
	
	String hiddenIDText=request.getParameter("hiddenSectorID");
	int hiddenSectorID=0;
	
	if (hiddenIDText!=null){
	hiddenSectorID=Integer.parseInt(hiddenIDText);
	}
	
	String hiddenCountryIDText=request.getParameter("hiddenCountryID");
	int hiddenCountryID=0;
	if (hiddenCountryIDText!=null){
		hiddenCountryID=Integer.parseInt(hiddenCountryIDText);
	}
	
	String hiddenCityIDText=request.getParameter("hiddenCityID");
	int hiddenCityID=0;
	if (hiddenCityIDText!=null){
		hiddenCityID=Integer.parseInt(hiddenCityIDText);
	}
	
	String hiddenSalaryText=request.getParameter("hiddenSalary");
	int hiddenSalary=0;
	if (hiddenSalaryText!=null){
		hiddenSalary=Integer.parseInt(hiddenSalaryText);
	}
	String hiddenExperienceText=request.getParameter("hiddenExperience");
	int hiddenExperience=0;
	if (hiddenExperienceText!=null){
		hiddenExperience=Integer.parseInt(hiddenExperienceText);
	}
	String hiddenDescription=request.getParameter("hiddenDescription");
	if (hiddenDescription==null)
		hiddenDescription=request.getParameter("hiddenDescription2");
	String URI=request.getRequestURI();
	String jspName=URI.substring(URI.lastIndexOf("/")+1);

	//To retrieve all sector values
	 List<Sector> sectorList = new ArrayList<Sector>();
  	DefaultHttpClient httpClient = new DefaultHttpClient();
  	HttpGet httpGet = new HttpGet(basePath+"/getValueListServlet?type=sector");
  	
  	HttpResponse httpResponse = httpClient.execute(httpGet);
  	HttpEntity httpEntity = httpResponse.getEntity();
  	InputStream is = httpEntity.getContent();
  	ObjectInputStream in = new ObjectInputStream(is);
  	sectorList=(List<Sector>) in.readObject();
  	httpClient.close();
  	
  //To retrieve all country values
  	List<Country> countryList = new ArrayList<Country>();
	        	
	DefaultHttpClient httpClient2 = new DefaultHttpClient();
	HttpGet httpGet2 = new HttpGet(basePath+"/getValueListServlet?type=country");
	        	
	HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
	HttpEntity httpEntity2 = httpResponse2.getEntity();
	InputStream is2 = httpEntity2.getContent();
	ObjectInputStream in2 = new ObjectInputStream(is2);
	countryList=(List<Country>) in2.readObject();
	httpClient2.close();
	           
    
   //To retrieve all city values
     	 List<City> cityList = new ArrayList<City>();
     	DefaultHttpClient httpClient3 = new DefaultHttpClient();
    	HttpGet httpGet3 = new HttpGet(basePath+"/getValueListServlet?type=city");
    	        	
    	HttpResponse httpResponse3 = httpClient3.execute(httpGet3);
    	HttpEntity httpEntity3 = httpResponse3.getEntity();
    	InputStream is3 = httpEntity3.getContent();
    	ObjectInputStream in3 = new ObjectInputStream(is3);
    	cityList=(List<City>) in3.readObject();
    	httpClient3.close();
    
    	//To retrieve all skill values
   List<Skill> skillList = new ArrayList<Skill>();
   DefaultHttpClient httpClient4 = new DefaultHttpClient();
   	HttpGet httpGet4 = new HttpGet(basePath+"/getValueListServlet?type=skill");
   	        	
   	HttpResponse httpResponse4 = httpClient4.execute(httpGet4);
   	HttpEntity httpEntity4 = httpResponse4.getEntity();
   	InputStream is4 = httpEntity4.getContent();
   	ObjectInputStream in4 = new ObjectInputStream(is4);
   	skillList=(List<Skill>) in4.readObject();
   	httpClient4.close();
	
	
	List<Skill> selectedJobSkillList=(List<Skill>) request.getSession().getAttribute("selectedJobSkillList");
	if (selectedJobSkillList==null){
			selectedJobSkillList=new ArrayList<Skill>();
			request.getSession().setAttribute("selectedJobSkillList", selectedJobSkillList);
		}
						
	%>
	
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
	
		<table>
				<tr>
					<td>		          
					<form name="newjobform" method="post" action="newjob" id="newjobform">
			
						<table>
							<tr>
								<td style="height: 20px;">Job Title:</td>
								<td style="text-align: left">
								<% if (hiddenTitle==null){ %>
								<input class="loginform_text" name="title" id="title" type="text" value="Job Title" style="width: 100px; height: 18px;" onclick="this.value='';" />
								<%}
								else{ %>
								<input class="loginform_text" name="title" id="title" type="text" value="<%=hiddenTitle%>" style="width: 100px; height: 18px;" />
								<%}%>
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Sector Name:</td>
								<td style="text-align: left">
								<select name="sectorID" id="sectorID">
								<%
								for (Sector sector:sectorList){	
								if (hiddenSectorID==sector.getId()) {
								%>
								<option value="<%=sector.getId()%>" selected="selected"><%=sector.getName()%></option>
								<%}
								else{%>
								<option value="<%=sector.getId()%>" ><%=sector.getName()%></option>
								<%}
								}
								%>
								</select>								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">Country Name:</td>
								<td style="text-align: left">
								<select name="countryID" id="countryID">
								<option value="0" selected="selected">Select a Country</option>
								<%
								for (Country country:countryList){	
								if (hiddenCountryID==country.getId()) {
								%>
								<option value="<%=country.getId()%>" selected="selected"><%=country.getName()%></option>
								<%}
								else{%>
								<option value="<%=country.getId()%>" ><%=country.getName()%></option>
								<%}
								}
								%>
								</select>								
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">City Name:</td>
								<td style="text-align: left">
								<select name="cityID" id="cityID">
								<option value="0" selected="selected" >Select a City</option>
								<%
								for (City city:cityList){	
								if (hiddenCityID==city.getId()) {
								%>
								<option value="<%=city.getId()%>" selected="selected"><%=city.getName()%></option>
								<%}
								else{%>
								<option value="<%=city.getId()%>"><%=city.getName()%></option>
								<%}
								}
								%>
								</select>								
								</td>				
							</tr>
							<tr>
							<td style="height: 20px;">Required Experience:</td>
								<td>
								<%if (hiddenExperience==0) {%>
								<input class="loginform_text" name="experience" id="experience" type="text" value="0" style="width: 100px; height: 18px;" onclick="this.value='';" />&nbsp;<label><strong>Years</strong></label>
								<%}
								else{%>
								<input class="loginform_text" name="experience" id="experience" type="text" value="<%=hiddenExperience%>" style="width: 100px; height: 18px;" onclick="this.value='';" />&nbsp;<label><strong>Years</strong></label>
								<%}%>
								</td>
							</tr>
							<tr>
							<td style="height: 20px;">Salary:</td>
								<td>
								<%if (hiddenSalary==0) {%>
								<input class="loginform_text" name="salary" id="salary" type="text" value="0" style="width: 100px; height: 18px;" onclick="this.value='';" />&nbsp;
								
								<%}
								else{ %>
								<input class="loginform_text" name="salary" id="salary" type="text" value="<%=hiddenSalary%>" style="width: 100px; height: 18px;" onclick="this.value='';" />&nbsp;
								
								<%}%>
								<input class="loginform_text"  name="currency" id="currency" type="text" value="" readonly="readonly" style="border-color: buttonface;width: 40px; height: 18px;background-color: buttonface;" />
								
								</td>
							</tr>
							
							<tr>
								<td style="height: 20px;" valign="top" >Job Description:</td>
								<td style="text-align: left">
								<% if (hiddenDescription==null){ %>
								<textarea name="description" id="description" cols="30" rows="10" onclick="this.value='';" >Job Details</textarea>
								<%}
								else{ %>
								<textarea name="description" id="description" cols="30" rows="10" ><%=hiddenDescription%></textarea>
								<%}%>
								</td>				
							</tr>
							<tr>
								<td style="height: 20px;">&nbsp;</td>
								<td colspan="2" style="text-align: left">
								<input name="save" type="submit" id="save" value="Add New Job" style="width: 100px; height: 18px;">
								</td>
							</tr>
						</table>
					</form>
					</td>
					<td>
					&nbsp;
					</td>
					<td>
					&nbsp;
					</td>
					<td>
					&nbsp;
					</td>
					<td valign="top">
						<form name="newjobform" method="post" action="addjobskill" id="form">
							<table>	
								<tr>
									<td> 
									<select style="width: 120px;" name="skillName">
										<% 
										
										for (Skill skill:skillList){
											boolean displaySkill=true;
											
											if (selectedJobSkillList.size()>0){
												
												for (Skill skillAvail:selectedJobSkillList){	
													if (skill.getSkillID()==skillAvail.getSkillID())
													{
														displaySkill=false;
														break;
													}
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
									
									<td colspan="2" style="text-align: left">
									<input type="hidden" name="jspFileName" id="jspFileName" value="<%=jspName%>" />
									<input type="hidden" name="hiddenTitle" id="hiddenTitle"/>
									<input type="hidden" name="hiddenSectorID" id="hiddenSectorID" />
									<input type="hidden" name="hiddenCountryID" id="hiddenCountryID" />
									<input type="hidden" name="hiddenCityID" id="hiddenCityID" />
									<input type="hidden" name="hiddenSalary" id="hiddenSalary" />
									<input type="hidden" name="hiddenExperience" id="hiddenExperience" />
									<input type="hidden" name="hiddenDescription" id="hiddenDescription"/>
									<input name="save" type="submit" id="saveexp" value="Add Skill" style="width: 100px; height: 18px;" onClick="storeJobFields();">
									</td>
								</tr>
								
				
							</table>
						</form>
						
					</td>
					<td valign="top">
						
						<table valign="top" frame="box" style="border: 2px solid white;border-collapse:collapse;" >
								<tr >
									<th style="width: 80px">REQUIRED</th>
									<th style="width: 80px">SKILLS</th>
									<th style="width: 80px"></th>
									<th style="width: 80px"></th>
								</tr>
						</table>
						<table valign="top" frame="box" style="border: 2px solid white;" >
						<tr >
									<td style="heigth: 0px;width: 80px"></td>
									<td style="heigth: 0px;width: 80px"></td>
									<td style="heigth: 0px;width: 80px"></td>
									<td style="heigth: 0px;width: 80px"></td>
								</tr>
							<% 
							for (int i=0; i<selectedJobSkillList.size();i++){ 
							if (i%4==0){%>
								<tr>
							<%}%>
								<td style="width: 80px;" >
									<form method="post" action="removejobskill" name="<%=selectedJobSkillList.get(i).getName()%>">
										<label><%=selectedJobSkillList.get(i).getName()%></label>
										<input type="hidden" name="skillId" value="<%=selectedJobSkillList.get(i).getSkillID()%>" />
										<input type="hidden" name="jspFileName" id="jspFileName" value="<%=jspName%>" />
										<input type="hidden" name="hiddenTitle2" id="hiddenTitle2"/>
										<input type="hidden" name="hiddenSectorID2" id="hiddenSectorID2" />
										<input type="hidden" name="hiddenCountryID2" id="hiddenCountryID2" />
										<input type="hidden" name="hiddenCityID2" id="hiddenCityID2" />
										<input type="hidden" name="hiddenSalary2" id="hiddenSalary2" />
										<input type="hidden" name="hiddenExperience2" id="hiddenExperience2" />
										<input type="hidden" name="hiddenDescription2" id="hiddenDescription2"/>
										<input type="submit" value="x" onClick="storeJobFields();" />
									</form>
								</td>
							
								<% if (i%4==3){%>
								</tr>
							<%}
							} %>	
							
							</table>
						</td>
		
					</tr>	
				</table>
				<label class="errorMessage"><%=registerMsg%></label>
	</div>
	
	</div>
	<div id="footer">Copyright © 2014 ...</div>
</div>


</center>
</body>
</html>