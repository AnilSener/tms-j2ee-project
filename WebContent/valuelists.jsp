<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Sector,model.City,model.Country,model.Skill"%>
<%@ page import="java.util.*,org.apache.http.message.BasicNameValuePair"%>

<%@ page import="java.net.HttpURLConnection,java.net.URL,java.io.*"%>
<%@ page import="org.apache.http.HttpEntity,org.apache.http.HttpResponse,org.apache.http.NameValuePair,org.apache.http.client.ClientProtocolException,org.apache.http.client.entity.UrlEncodedFormEntity,org.apache.http.client.methods.HttpGet,org.apache.http.impl.client.DefaultHttpClient,org.apache.http.message.BasicNameValuePair;"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.11.1.js" type="text/javascript"></script>
<script>
$(document).ready(function() {
	
$("#queryButton").click(function(){
	
	   $("#queryValueForm").attr("action", "queryValueServlet");
	   $("#queryValueForm").attr("method", "get");
	   $("#queryValueForm").submit();
});
	$("#addButton").click(function(){
		
	   $("#queryValueForm").attr("action", "addValueServlet");
	   $("#queryValueForm").attr("method", "post");
	   $("#queryValueForm").submit();
	});
});
</script>
</head>
<body><center> <div align="center">
<div id="top" >
	
	<div id="header" title="Talent Management System"><img src="images/Bestjobs.gif" height="70" width="200" /></div>
	<div id="Login" >
	<%
	String adminusername=(String)session.getAttribute("username");
	String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
	List<BasicNameValuePair> valueList=null;
	if(request.getAttribute("valueList")!=null){
		valueList =(List<BasicNameValuePair>)request.getAttribute("valueList");
		
		}
		else{
		
		//To retrieve all active employees
	  	DefaultHttpClient httpClient = new DefaultHttpClient();
	  	HttpGet httpGet = new HttpGet(basePath+"/queryValueServlet?type=&name=&action=list&dependentField=");
	  	
	  	HttpResponse httpResponse = httpClient.execute(httpGet);
	  	HttpEntity httpEntity = httpResponse.getEntity();
	  	InputStream is = httpEntity.getContent();
	  	ObjectInputStream in = new ObjectInputStream(is);
	  	valueList=(List<BasicNameValuePair>) in.readObject();
	  	httpClient.close();
		
		}
	
	if(adminusername!=null){%>
	<b>You logged as <%=adminusername%></b><% }%><a onClick="location.href='logout'"><img alt="" src="images/logout.png" width="30" height="30" /></a>
	</div>
	<hr id="Line" >
	<div id="topmenu" >
	<ul class="in">
		<li><input type="button" onclick="location.href='employersubscriptions.jsp' " value="Employers"></li>
		<li><input type="button" onclick="location.href='valuelists.jsp' " value="Value Lists"></li>
	</ul>
	</div>
	<div id="content">
	<form action="queryValueServlet" name="queryValueForm" id="queryValueForm" method="get">
		<h2>SEARCH/ADD VALUES BY...</h2>
		<table>
			<tr>
				<td style="height: 20px;">
				<label>Name:</label>
				</td>
				<td style="text-align: left">
				<input class="loginform_text" type="text" name="name" id="name" value="${param.name}" style="width: 120px; height: 18px;" onclick="this.value='';" />
				</td>
				<td style="height: 20px;">
				<label>Value Type:</label>
				</td>
				<td style="text-align: left">
				<select name="type" id="type" style="width: 120px; height: 18px;">
									<option value=""></option>
									<% 
									String valueTypes[]={"skill","sector","country","city"};
									for (int i=0;i<valueTypes.length;i++){
										
										if(request.getAttribute("type")!=null){
											
											if (((String)request.getAttribute("type")).equals(valueTypes[i])) {		
											%>
											<option value="<%=valueTypes[i]%>" selected="selected"><%=valueTypes[i]%></option>
											<%
											}else{
												%>
												<option value="<%=valueTypes[i]%>"><%=valueTypes[i]%></option>
												<%
											}
										} 
										
										else{
											%>
											<option value="<%=valueTypes[i]%>"><%=valueTypes[i]%></option>
											<%
										}
									}
									%>
				</select>
				</td>
				<td style="height: 20px;">
				<label>Dependent Field:</label>
				</td>
				<td style="text-align: left">
				<input class="loginform_text" type="text" name="dependentField" id="dependentField" value="${param.dependentField}" style="width: 120px; height: 18px;" onclick="this.value='';" />
				</td>
				<td>
				<input type="hidden" name="action" value="search"/>
				<input type="submit" value="Search!" name="queryButton" id="queryButton"/>
				<input type="submit" value="Add!" name="addButton" id="addButton"/>
				</td>
			</tr>
			
		</table>
		</form>
	<form action="updateValueServlet" name="updateValueForm" method="post">
	<input type="submit" value="Update" name="updateButton"/>
	<h2>Value Lists</h2>	
	<table border="1">
	<tr>
	<th>ID</th>
	<th>Type</th>
	<th>Name</th>
	<th>Dependent Field</th>
	
	</tr>
	<% for(int i=0 ; i <valueList.size() ; i ++){ 
		Sector sector=null;
		City city=null;
		Country country=null;
		Skill skill=null;
		int id=0;
		String name="";
		String type="";
		String dependentField="";
	
		NameValuePair pair=valueList.get(i);
		id=Integer.parseInt(pair.getName().substring(pair.getName().lastIndexOf("-")+1));
		type=pair.getName().substring(0, pair.getName().lastIndexOf("-"));
		if(type.equals("country")){
		name=pair.getValue().substring(0, pair.getValue().lastIndexOf("-"));
		dependentField=pair.getValue().substring(pair.getValue().lastIndexOf("-")+1);
		}
		else if(type.equals("city")){
			name=pair.getValue().substring(0, pair.getValue().lastIndexOf("-"));
			//To retrieve all country values
		  	Country countryObj = new Country();
			//System.out.println("Country id"+pair.getValue().substring(pair.getValue().lastIndexOf("-")+1));
			DefaultHttpClient httpClient2 = new DefaultHttpClient();
			HttpGet httpGet2 = new HttpGet(basePath+"/getCountryDataByIDServlet?countryID="+pair.getValue().substring(pair.getValue().lastIndexOf("-")+1)+"&type=stream");
			        	
			HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
			HttpEntity httpEntity2 = httpResponse2.getEntity();
			InputStream is2 = httpEntity2.getContent();
			ObjectInputStream in2 = new ObjectInputStream(is2);
			countryObj=(Country) in2.readObject();
			httpClient2.close();
			
			dependentField=countryObj.getName();
			}
		else{
			name=pair.getValue();
		}
	
	%>


		<tr>
	
			<td style="width: 40px;">
			
				<label><%=id%></label>
				<input type="hidden" name="id" value="<%=id%>" />
			</td>
			<td style="width: 120px;">
			
				<label><%=type%></label>
				<input type="hidden" name="type" value="<%=type%>" />
			</td>
			<td style="width: 120px;">
			
				<input class="loginform_text" name="name" id="name" type="text" value="<%=name%>" style="width: 120px; height: 18px;"/>
			</td>
			<td style="width: 120px;">
			
				<input class="loginform_text" name="dependentField" id="dependentField" type="text" value="<%=type.equals("country") || type.equals("city")?dependentField:""%>" <%=!type.equals("country") && !type.equals("city")?" readonly":""%> style="width: 120px; height: 18px;"/>
			</td>
	
			
		</tr>
		
		<% } %>
	</table>	
	</form>
	</div>
</div>
</div>

</center>
</body>
</html>