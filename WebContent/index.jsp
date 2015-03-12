<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Sector,model.Country,model.City,java.util.List,java.util.ArrayList" %>
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
	<div id="content" align="center">
		
		<% String e = (String) session.getAttribute("error" );
			final int maxSectorColumnNo=2;
			final int maxCityColumnNo=3;
			List<City> cityList=(List<City>)request.getAttribute("cities");
			int selectedCountryID=0;
			try{
			selectedCountryID=((Integer)request.getAttribute("countryID")).intValue();
			}
			catch(NullPointerException ex){
				selectedCountryID=0;
			}
            if(e != null)
            {
                out.print(e); }
            String success = (String) request.getAttribute("success" );
            if(success != null)
            {
                out.println(success); }    
            
            List<Sector> sectorList = new ArrayList<Sector>();
        	
        	DefaultHttpClient httpClient = new DefaultHttpClient();
        	HttpGet httpGet = new HttpGet(request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()))+"/getValueListServlet?type=sector");
        	
        	HttpResponse httpResponse = httpClient.execute(httpGet);
        	HttpEntity httpEntity = httpResponse.getEntity();
        	InputStream is = httpEntity.getContent();
        	ObjectInputStream in = new ObjectInputStream(is);
        	sectorList=(List<Sector>) in.readObject();
        	httpClient.close();
           
        	String URI=request.getRequestURI();
        	String jspName=URI.substring(URI.lastIndexOf("/")+1);
                %>
               <div> <table style="border-collapse: collapse;width:800px;">
				<tr>
				<th align="left" style="background-color: buttonface;">
				<b>JOBS BY SECTOR</b>
				</th>
				<th style="background-color: buttonface;"></th>
				</tr>
				<tr><td style="border-bottom:2pt solid black;"></td><td style="border-bottom:2pt solid black;"></td><td style="border-bottom:2pt solid black;"></td><td style="border-bottom:2pt solid black;"></td><td style="border-bottom:2pt solid black;"></td></tr>
				</table>
				<br/>
				<table>
				<tr>
				<%if(sectorList!=null) {
					for(int i=0;i<sectorList.size();i++){
						
				
						switch((int)Math.ceil((i+1)/Math.ceil(sectorList.size()/maxSectorColumnNo))){
						case 1:
							if (i==0){%>
							<td>
							<table>
							<%} %>
							<tr><td><a href="queryjob?keyword=&sectorID=<%=sectorList.get(i).getId()%>&status=&experience=&cityID=0&companyname=&queryButton=Search%21"><%=sectorList.get(i).getName()%></a></td></tr>
							<%if ((i+1)%(Math.ceil(sectorList.size()/maxSectorColumnNo))==0){%>
							</table>
							</td>
							<%} 
							
							break;
						case 2:
							if ((i+1)%Math.ceil(sectorList.size()/maxSectorColumnNo)==1) {%>
							<td>
							<table>
							<%} %>
							<tr><td><a href="queryjob?keyword=&sectorID=<%=sectorList.get(i).getId()%>&status=&experience=&cityID=0&companyname=&queryButton=Search%21"><%=sectorList.get(i).getName()%></a></td></tr>
							<%if ((i+1)%(Math.ceil(sectorList.size()/maxSectorColumnNo))==0){%>
							</table>
							</td>
							<%} 
							
							break;
						}
					
					}
				}%>
				</tr>
				</table>
				</div>
				<br/>
				<br/>
				<% 
				List<Country> countryList = new ArrayList<Country>();
	        	
	        	DefaultHttpClient httpClient2 = new DefaultHttpClient();
	        	HttpGet httpGet2 = new HttpGet(request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()))+"/getValueListServlet?type=country");
	        	
	        	HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
	        	HttpEntity httpEntity2 = httpResponse2.getEntity();
	        	InputStream is2 = httpEntity2.getContent();
	        	ObjectInputStream in2 = new ObjectInputStream(is2);
	        	countryList=(List<Country>) in2.readObject();
	        	httpClient2.close();
	           
	                %>
				<div>
				<table style="border-collapse: collapse; width:800px;">
				<tr>
				<th align="left" style="background-color: buttonface;">
				<b>JOBS BY LOCATION</b>
				</th><th style="background-color: buttonface;"></th>
				</tr>
				<tr><td style="border-bottom:2pt solid black;"></td><td style="border-bottom:2pt solid black;"></td><td style="border-bottom:2pt solid black;"></td><td style="border-bottom:2pt solid black;"></td><td style="border-bottom:2pt solid black;"></td></tr>
				</table>
				<br/>
				<table style="border-collapse: collapse;">
				<tr>
				<%if(countryList!=null) {
					for(int i=0;i<countryList.size();i++){
						%>
				
						<td><a href="getAllCitiesByCountryID?countryID=<%=countryList.get(i).getId()%>&type=text/html&jspName=<%=jspName%>"><img src="images/flag_<%=countryList.get(i).getName().replaceAll(" ","_")%>.gif" height="20" width="40" /></a></td>
							
					<%}
					}%>
				</tr>
				</table>
				 <table>
				<tr>
				<%if(cityList!=null) {
					for(int i=0;i<cityList.size();i++){
						
				
						switch((int)Math.ceil((i+1)/Math.ceil(cityList.size()/maxCityColumnNo))){
						case 1:
							if (i==0){%>
							<td>
							<table>
							<%} %>
							<tr><td><a href="queryjob?keyword=&sectorID=0&status=&experience=&countryID=<%=selectedCountryID%>&cityID=<%=cityList.get(i).getId()%>&companyname=&queryButton=Search%21"><%=cityList.get(i).getName()%></a></td></tr>
							<%if ((i+1)%(Math.ceil(cityList.size()/maxCityColumnNo))==0 || i==cityList.size()){%>
							</table>
							</td>
							<%} 
							
							break;
						case 2:
							if ((i+1)%Math.ceil(cityList.size()/maxCityColumnNo)==1) {%>
							<td>
							<table>
							<%} %>
							<tr><td><a href="queryjob?keyword=&sectorID=0&status=&experience=&countryID=<%=selectedCountryID%>&cityID=<%=cityList.get(i).getId()%>&companyname=&queryButton=Search%21"><%=cityList.get(i).getName()%></a></td></tr>
							<%if ((i+1)%(Math.ceil(cityList.size()/maxCityColumnNo))==0 || i==cityList.size()){%>
							</table>
							</td>
							<%} 
							
							break;
						case 3:
							if ((i+1)%Math.ceil(cityList.size()/maxCityColumnNo)==1) {%>
							<td>
							<table>
							<%} %>
							<tr><td><a href="queryjob?keyword=&sectorID=0&status=&experience=&countryID=<%=selectedCountryID%>&cityID=<%=cityList.get(i).getId()%>&companyname=&queryButton=Search%21"><%=cityList.get(i).getName()%></a></td></tr>
							<%if ((i+1)%(Math.ceil(cityList.size()/maxCityColumnNo))==0 || i==cityList.size()){%>
							</table>
							</td>
							<%} 
							
							break;
						}
					
					}
				}%>
				</tr>
				</table>
				</div>
	</div>
	
</div>
<div id="footer">Copyright © 2014 ...</div>
</div>

</center>
</body>
</html>