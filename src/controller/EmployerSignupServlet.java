package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import model.Employer;
import DAO.DataManager;

/**
 * Servlet implementation class EmployerSignupServlet
 */
@WebServlet("/employersignup")
@MultipartConfig
public class EmployerSignupServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
	String path = "";
	//Instance of beans
	private DataManager dataManager;
	private LoginValidator val = new LoginValidator();
	EmailFormatValidator emailval=new EmailFormatValidator();
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		dataManager=super.getDataManager();
		ServletContext context = getServletContext();
        path = context.getInitParameter("filesystempath");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Employer emp =null;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String companyname = request.getParameter("companyname");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String description = request.getParameter("description");
		final Part filePart = request.getPart("file");
		String type = request.getParameter("type");
		
		
		if(val.notNullEmployer(username, password, companyname, email, phone, address, description))
		{
		if(val.validate(username, password))
		{
			if (emailval.validate(email)){
				
					if (val.lessThan40Chars(companyname)){
						valOK=true;
					}
				else{
					
					strError = "Company Name cannot be more than 40 characters!";
					dbOK = false;
					valOK=false;
				}
			}
			else{
				
				strError = "Invalid email format. Please enter a correct email!";
				dbOK = false;
				valOK=false;
			}
				
		}
		else
		{
			strError = "Invalid username or password. Employer password cannot be less than 6, more than 20 characters.";
			dbOK = false;
			valOK=false;
		}	
		}
		else
		{
			strError="Username, password, email, phone, address, description fields cannot be null";
			dbOK = false;
			valOK=false;
		}
		if(valOK){
			emp = new Employer();
			emp.setUsername(username);
			emp.setEmail(email);
			emp.setPassword(password);
			emp.setPhone(phone);
			emp.setCompanyName(companyname);
			emp.setAddress(address);
			emp.setDescription(description);
			emp.setActive(false);
			emp.setCreatedDate(new java.util.Date());
			dbOK=dataManager.saveEmployer(emp);
			strSuccess="You have successfully registered. Please login to continue.";
		}
		
		RequestDispatcher dispatcher;
		if (!dbOK)
		{
			
			request.setAttribute("error", strError);
			dispatcher = request.getRequestDispatcher("/employersignup.jsp");
		}
		else{
			//Logo file upload
			final String fileName = getFileName(filePart);
			OutputStream out = null;
			InputStream filecontent = null;
				try {
					
					
			        out = new FileOutputStream(path + File.separator + fileName);
			        filecontent = filePart.getInputStream();

			        int read = 0;
			        final byte[] bytes = new byte[1024];

			        while ((read = filecontent.read(bytes)) != -1) {
			            out.write(bytes, 0, read);
			        }
			        request.setAttribute("status", "File upload is successful!");
			     
			        dataManager.insertFileData(emp.getUsername(),type, path + File.separator + fileName);
			    } catch (FileNotFoundException fne) {
			    	request.setAttribute("status", fne.getMessage());

			    } finally {
			        if (out != null) {
			            out.close();
			        }
			        if (filecontent != null) {
			            filecontent.close();
			        }  
			    }
			
				//Registration Mail Content
			String subject=companyname+" is successfully registered to BestJobs.";
			String content="<i>Dear Employer,</i><br>";
			content+="<i>Your company is registered successfully to Best Jobs. We are evaluating and verifying your application. You will be able to login to our system after activation in 24 hours with the following information.</i><br>";
			content+="<b>Company Name = "+companyname+"</b><br>";
			content+="<b>User Name = "+username+"</b><br>";
			content+="<b>Password = "+password+"</b><br>";
			content+="<b>Address = "+address+"</b><br>";
			content+="<b>Email = "+email+"</b><br>";
			content+="<b>Phone = "+phone+"</b><br>";
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("recipient",email));
			params.add(new BasicNameValuePair("subject", subject));
			params.add(new BasicNameValuePair("content", content));
			@SuppressWarnings("deprecation")
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()))+"/EmailSendingServlet");
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream is = httpEntity.getContent();
			ObjectInputStream in = new ObjectInputStream(is);
			String resultMessage="";
			try {
				resultMessage = (String) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			httpClient.close();
			
			request.setAttribute("mailresultmessage", resultMessage);
			request.setAttribute("success", strSuccess);
			dispatcher = request.getRequestDispatcher("/employerlogin.jsp");
		}
		
		
		dispatcher.forward( request, response);
	}

	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.lastIndexOf("\\")+1,content.lastIndexOf("\""));
	        }
	    }
	    return null;
	}
}
