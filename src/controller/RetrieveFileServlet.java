package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DataManager;

/**
 * Servlet implementation class RetrieveFileServlet
 */
@WebServlet(description = "Retrieve File Servlet", urlPatterns = { "/retrievefile" })
public class RetrieveFileServlet extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
	String path = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//String src=request.getParameter("src");
		String type=request.getParameter("type");
		String username="";
		int fileID=0;
		if(request.getParameter("username")!=null)
		username=request.getParameter("username");
		else if(request.getParameter("fileID")!=null)
		fileID=Integer.parseInt(request.getParameter("fileID"));
		
		String contenttype=request.getParameter("contenttype");
		String src="";
		if(!username.equals(""))
		src=dataManager.getFilePath(type,username);	
		else if(fileID!=0)
		src=dataManager.getFilePath(fileID);	
		
		File f=new File(src);
			
			if (!f.exists()){

				if (type.equals("profile_image")){
					
					src=path+ File.separator +"default_profile.jpg";
					f=new File(src);
				}
				else if (type.equals("logo_image")){
					
					src=path+ File.separator +"default_logo.png";
					f=new File(src);
				}
				System.out.println("Folder doesn't exist in the specified directory.");
			}
			src=src.replace("/","\\");
			response.setContentType(contenttype); 
			
		    ServletOutputStream out;  
		    out = response.getOutputStream();  
			   try{
				    FileInputStream fin = new FileInputStream(src);  
				      
				    BufferedInputStream bin = new BufferedInputStream(fin);  
				    BufferedOutputStream bout = new BufferedOutputStream(out);  
				    int ch =0; ;  
				    while((ch=bin.read())!=-1)  
				    {  
				    bout.write(ch);  
				    }  
				      
				    bin.close();  
				    fin.close();  
				    bout.close();  
				    out.close();  
				}  
				catch(Exception ex){
					
					ex.printStackTrace();
				}
			
	}

}
