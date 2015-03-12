<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.JobSeeker"%>
<%@ page import="model.Sector,model.Country,model.City,java.util.List,java.util.ArrayList" %>
<%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair,java.io.ObjectInputStream,java.io.InputStream;"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body><center> 
<div id="top" >
	
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
		<% JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
		if(uname!=null){%>
		<b>You logged as <%=uname.getUsername()%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		
		<li><input type="button" onclick="location.href='home.jsp' " value="Home"></li>
		<li><input type="button" onclick="location.href='searchjob.jsp' " value="Browse Jobs"></li>
		<li><input type="button" onclick="location.href='savedetails.jsp' " value="Talent Profile"></li>
		<li><input type="button" onclick="location.href='myinterviewcalls.jsp' " value="Interview Calls"></li>
		<li><input type="button" onclick="location.href='jobseekerupdateinfo.jsp' " value="User Info"></li>
	
		<li>
		  
		</li>
	</ul>
	</div>
	<div id="content" align="center">
	<table width=582 border="1" align=center cellPadding=3 cellspacing="0" 
borderColor=#0000ff bgColor=#0000ff style="WIDTH: 582px; HEIGHT: 35px">
            <tr>
              <td><table style="WIDTH: 853px; HEIGHT: 151px" border="0" cellspacing="0" 
      cellPadding=3 width=853 bgColor=#f0f0ff align=left>
                  <tr>
                    <td width="211" height="121" align="center" valign="middle"><font color="#000000"><br 
            >
                      </font>
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
                            <td><font color="#000000"><a class="five" href="queryjob?keyword=&sectorID=0&status=&appStatus=Applied&jobSeekerID=<%=uname.getId()%>&experience=&cityID=0&companyname=&queryButton=Search%21" ><img 
                  style="WIDTH: 87px; HEIGHT: 72px" border=0 hspace=0 
                  src="images/appliedjobs.gif" width=200 
          height=97></a><a class="five" href="jobnotifications.jsp" ></a></font></td>
                          </tr>
                      </table>
                      <font color="#000000"><br 
            >
                      </font></td>
                    <td width="206"><font size="+0"><font><br 
            >
                    </font><font color="#400080" size="4" 
            face="Arial Rounded MT Bold"><a class="five" href="queryjob?keyword=&sectorID=0&status=&appStatus=Applied&jobSeekerID=<%=uname.getId()%>&experience=&cityID=0&companyname=&queryButton=Search%21" >Applied Jobs</a></font><font 
            color=#400080 size=4 face="Arial Rounded MT Bold" 
            ><a class="five" href="jobnotifications.jsp" ></a> </font></font></td>
                    <td width="183" align="center" valign="middle"><table style="WIDTH: 58px; HEIGHT: 39px" border="1" cellspacing="0" 
            borderColor=#ffb062 cellPadding=3 width=58 bgColor=#8000ff 
            align=center>
                          <tr>
                            <td><a class="five" href="queryjob?keyword=&sectorID=0&status=&companyname=&experience=&countryID=0&cityID=0&appStatus=Hired&jobSeekerID=<%=uname.getId()%>&&queryButton=Search%21" ><img 
                  style="WIDTH: 89px; HEIGHT: 70px" border=0 hspace=0 
                  src="images/feedback.jpg" width=200 
							height=200></a></td>
                          </tr>
                      </table></td>
                    <td width="229"><font size="+0"><font 
            color=#400080 size=4 face="Arial Rounded MT Bold" 
            ><a class="five" href="queryjob?keyword=&sectorID=0&status=&companyname=&experience=&countryID=0&cityID=0&appStatus=Hired&jobSeekerID=<%=uname.getId()%>&&queryButton=Search%21" >Application Feedbacks</a>&nbsp;&nbsp;&nbsp; </font></font></td>
                  </tr>
                  <tr>
                    <td align="center" valign="middle"></td>
                    <td></td>
                    <td align="center" valign="middle"></td>
                    <td></td>
                  </tr>
               </table></td>
            </tr>
		    </table>   
                  <% 
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
<div id="footer">Copyright © 2014 ...</div>
</div>
</center>
</body>
</html>