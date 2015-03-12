package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;


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

import model.Employee;
import model.JobSeeker;

import DAO.DataManager;



@WebServlet("/upload")
@MultipartConfig
public class Upload extends DBConnectionServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DataManager dataManager;
	String path = "";
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		dataManager=super.getDataManager();
		 ServletContext context = getServletContext();
	        path = context.getInitParameter("filesystempath");
	}
	
	protected void doPost(HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {
	    //response.setContentType("text/html;charset=UTF-8");
		//String type=request.getParameter("type");
	    // Create path components to save the file
	    final Part filePart = request.getPart("file");
	    String type = request.getParameter("type");
	    
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
	        JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
			
			
			if (uname==null){
			
				uname=(JobSeeker)request.getSession().getAttribute("employee");

			}
	        dataManager.insertFileData(uname.getUsername(),type, path + File.separator + fileName);
	    } catch (FileNotFoundException fne) {
	    	request.setAttribute("status", fne.getMessage());

	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/savedetails.jsp");
			dispatcher.forward( request, response);	
	    }
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
