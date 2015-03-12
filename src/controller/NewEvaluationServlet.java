package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.sun.istack.internal.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import model.Assignment;
import model.Employee;
import model.Employer;
import model.Evaluation;
import model.Factor;
import model.LineItem;
import model.Project;
import model.Timesheet;
import DAO.DataManager;

import java.io.FileOutputStream;

 
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@WebServlet("/newEvaluation")
public class NewEvaluationServlet extends DBConnectionServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strError;
	private String strSuccess;
	private boolean dbOK = false;
	private boolean valOK = false;
	
	private DataManager dataManager;
	private String path="";
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public NewEvaluationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		dataManager=super.getDataManager();
		ServletContext context = getServletContext();
        path = context.getInitParameter("filesystempath");
	}
	
	
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		
		String status= request.getParameter("status");
		int employeeID =0;
		if(request.getParameter("employeeID")!=null)
		employeeID = Integer.parseInt(request.getParameter("employeeID"));
		
		
		Employee employee=dataManager.getEmployeeDataByEmployeeId(employeeID);
		Employer unamEmp =(Employer)request.getSession().getAttribute("employerBean");
		Employee supervisor=dataManager.getEmployeeDataByEmployeeId(employee.getSupervisorId());
		List<LineItem> lineItemList=null;
		Evaluation evaluation=null;
		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//Employer emp =(Employer)request.getSession().getAttribute("employerBean");
		
		if(employeeID!=0 && request.getParameter("startDate")!=null && request.getParameter("endDate")!=null){
			
				
					
					valOK=true;
					
					lineItemList= new ArrayList<LineItem>();
					
					
					
					try {
						startDate = (Date) formatter.parse(request.getParameter("startDate"));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						strError = "Start Date has missing fields. Please select all!";
						dbOK = false;
						valOK=false;
					}
					try {
						endDate = (Date) formatter.parse(request.getParameter("endDate"));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						strError = "End Date has missing fields. Please select all!";
						dbOK = false;
						valOK=false;
					}
							
					int reportFileID=0;		
					//Performance Evaluation Report
					
					
					Workbook workbook = null;
			        String fileName="Performance_Evaluation Report_"+employee.getFirstname()+"_"+employee.getLastname()+"_"+formatter.format(new java.util.Date())+".xlsx";
			        if(fileName.endsWith("xlsx")){
			            workbook = new XSSFWorkbook();
			        }else if(fileName.endsWith("xls")){
			            workbook = new HSSFWorkbook();
			        }else{
			            try {
							throw new Exception("invalid file name, should be xls or xlsx");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			         
			        Sheet sheet = workbook.createSheet("Performance Evaluation Report");
			         
			        List<Assignment> allAssignments=dataManager.getMyProjectAssignments(employee.getId());
			        //All Employer timesheets
			        List<Timesheet> allTimesheets=dataManager.getAllTimesheetsByEmployer(unamEmp.getEid(), "Approved");
			        
			        
			        List<Assignment> assignmentList=new ArrayList<Assignment>();
			        for(Assignment assignment:allAssignments){
			          if((assignment.getEndDate().compareTo(startDate)==0 || assignment.getEndDate().compareTo(startDate)==1) && (assignment.getEndDate().compareTo(endDate)==0 || assignment.getEndDate().compareTo(endDate)==-1)){
			        	  assignmentList.add(assignment);
			          }
			        }
			        
			        List<Timesheet> timesheetList=new ArrayList<Timesheet>();
			        for(Timesheet timesheet:allTimesheets){
			        	
			        	String timesheetStrDate=timesheet.getYear()+"-"+timesheet.getMonth()+"-"+1;
			        	
			        	Date timesheetDate=null;
			        	try {
			        		timesheetDate = (Date) formatter.parse(timesheetStrDate);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.getStackTrace();
						}
			        	Calendar calendar = Calendar.getInstance();  
					    calendar.setTime(timesheetDate);  
			
					    calendar.add(Calendar.MONTH, 1);  
					    calendar.set(Calendar.DAY_OF_MONTH, 1);  
					    calendar.add(Calendar.DATE, -1);  
			
					    @SuppressWarnings("deprecation")
						int lastDayOfMonth = calendar.getTime().getDate(); 
					    timesheetDate.setDate(lastDayOfMonth);
					    System.out.println("last date: "+formatter.format(timesheetDate));
					    
			          if(timesheet.getEmployeeID()==employee.getId() && (timesheetDate.compareTo(startDate)==0 || timesheetDate.compareTo(startDate)==1) && (timesheetDate.compareTo(endDate)==0 || timesheetDate.compareTo(endDate)==-1)){
			        	  timesheetList.add(timesheet);
			        	  System.out.println("timesheet"+timesheet.getId());
			        	  for(LineItem item:timesheet.getLineItems())
			        	  lineItemList.add(item);
			        	  
			          }
			        }
			        
			        
			        sheet.addMergedRegion(new CellRangeAddress(/*Row*/0,0,/*Column*/0,1));
			        
			        int rowIndex = 0;
			        
			       
			        Row row0 = sheet.createRow(rowIndex++);
			        Cell cell0 = row0.createCell(0);
		            cell0.setCellValue("Performance Evaluation Report");
		            //Report Name Cell Style
		            CellStyle style=workbook.createCellStyle();
		            style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
			        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			        
		            
		            style.setAlignment(CellStyle.ALIGN_CENTER);
			        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			        style.setBorderBottom(CellStyle.BORDER_THIN);
			        style.setBorderRight(CellStyle.BORDER_THIN);
			        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			        
			        Font reportNameFont=workbook.createFont();
			        reportNameFont.setColor(IndexedColors.WHITE.getIndex());
			        reportNameFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			        reportNameFont.setFontHeightInPoints((short)14);
			        reportNameFont.setFontName("Calibri");
			        style.setFont(reportNameFont);
			        cell0.setCellStyle(style);
			        
			        CellStyle style2=workbook.createCellStyle();
		            
			        
		            style2.setAlignment(CellStyle.ALIGN_LEFT);
		            style2.setBorderTop(CellStyle.BORDER_THIN);
			        style2.setBorderBottom(CellStyle.BORDER_THIN);
			        style2.setBorderLeft(CellStyle.BORDER_THIN);
			        style2.setBorderRight(CellStyle.BORDER_THIN);
			       
			        
			        
			        CellStyle style3=workbook.createCellStyle();
		           
			        
		            style3.setAlignment(CellStyle.ALIGN_LEFT);
		            style3.setBorderTop(CellStyle.BORDER_THIN);
			        style3.setBorderBottom(CellStyle.BORDER_THIN);
			        style3.setBorderRight(CellStyle.BORDER_THIN);
			        
			        
			        Font fieldNameFont=workbook.createFont();
			        fieldNameFont.setColor(IndexedColors.BLACK.getIndex());
			        fieldNameFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			        fieldNameFont.setFontHeightInPoints((short)11);
			        fieldNameFont.setFontName("Calibri");
			        style2.setFont(fieldNameFont);
			        
			        Row row1 = sheet.createRow(rowIndex++);
			        Cell cell10 = row1.createCell(0);
		            cell10.setCellValue("Name:");
			        cell10.setCellStyle(style2);
			        Cell cell11 = row1.createCell(1);
			        cell11.setCellStyle(style3);
			        cell11.setCellValue(employee.getFirstname());
			        Row row2 = sheet.createRow(rowIndex++);
			        Cell cell20 = row2.createCell(0);
		            cell20.setCellValue("Surname:");
			        cell20.setCellStyle(style2);
			        Cell cell21 = row2.createCell(1);
			        cell21.setCellValue(employee.getLastname());
			        cell21.setCellStyle(style3);
			        Row row3 = sheet.createRow(rowIndex++);
			        Cell cell30 = row3.createCell(0);
		            cell30.setCellValue("Company Name:");
			        cell30.setCellStyle(style2);
			        Cell cell31 = row3.createCell(1);
			        cell31.setCellValue(unamEmp.getCompanyName());
			        cell31.setCellStyle(style3);
			        Row row4 = sheet.createRow(rowIndex++);
			        Cell cell40 = row4.createCell(0);
		            cell40.setCellValue("Hire Date:");
			        cell40.setCellStyle(style2);
			        Cell cell41 = row4.createCell(1);
			        cell41.setCellValue(formatter.format(employee.getHiringDate()));
			        cell41.setCellStyle(style3);
			        Row row5 = sheet.createRow(rowIndex++);
			        Cell cell50 = row5.createCell(0);
		            cell50.setCellValue("Position:");
			        cell50.setCellStyle(style2);
			        Cell cell51 = row5.createCell(1);
			        cell51.setCellValue(employee.getJob());
			        cell51.setCellStyle(style3);
			        Row row6 = sheet.createRow(rowIndex++);
			        Cell cell60 = row6.createCell(0);
		            cell60.setCellValue("Supervisor:");
			        cell60.setCellStyle(style2);
			        Cell cell61 = row6.createCell(1);
			        cell61.setCellValue(supervisor.getFirstname()+" "+supervisor.getLastname());
			        cell61.setCellStyle(style3);
			        Row row7 = sheet.createRow(rowIndex++);
			        Cell cell70 = row7.createCell(0);
		            cell70.setCellValue("Reporting Date:");
			        cell70.setCellStyle(style2);
			        Cell cell71 = row7.createCell(1);
			        cell71.setCellValue(sdf.format(new java.util.Date()));
			        cell71.setCellStyle(style3);
			        Row row8 = sheet.createRow(rowIndex++);
			        Cell cell80 = row8.createCell(0);
		            cell80.setCellValue("Evaluation Period:");
			        cell80.setCellStyle(style2);
			        Cell cell81 = row8.createCell(1);
			        cell81.setCellValue(request.getParameter("startDate")+"/"+request.getParameter("endDate"));
			        cell81.setCellStyle(style3);
			        
			        Row row9 = sheet.createRow(rowIndex++);
			        Cell cell90 = row9.createCell(0);
		            cell90.setCellValue("Project Code");
		            cell90.setCellStyle(style2);
		            Cell cell91 = row9.createCell(1);
		            cell91.setCellValue("Project Title");
		            cell91.setCellStyle(style2);
		            Cell cell92 = row9.createCell(2);
		            cell92.setCellValue("Start Date");
		            cell92.setCellStyle(style2);
		            Cell cell93 = row9.createCell(3);
		            cell93.setCellValue("Start Date");
		            cell93.setCellStyle(style2);
		            Cell cell94 = row9.createCell(4);
		            cell94.setCellValue("Goal");
		            cell94.setCellStyle(style2);
		            Cell cell95 = row9.createCell(5);
		            cell95.setCellValue("Actual");
		            cell95.setCellStyle(style2);
		            Cell cell96 = row9.createCell(6);
		            cell96.setCellValue("Percentage");
		            cell96.setCellStyle(style2);
		            
		            Iterator<Assignment> iterator = assignmentList.iterator();
			        while(iterator.hasNext()){
			            Assignment assignment = iterator.next();
			            int actualWorkHour=0;
			            for(LineItem item:lineItemList){
			            	if(item.getProjectCode().equals(assignment.getProjectCode()))
			            		actualWorkHour+=item.getWorkhours();
			            		 
			            }
			            Project project=dataManager.getProjectDataByEmployer(assignment.getProjectCode(), unamEmp.getEid());
			            int percentage=0;
			            		if(assignment.getGoal()!=0)
			            			percentage=(actualWorkHour/assignment.getGoal())*100;
			            		else
			            			percentage=0;
			            		
			            Row row10 = sheet.createRow(rowIndex++);
			            Cell cell100 = row10.createCell(0);
			            cell100.setCellValue(assignment.getProjectCode());
			            cell100.setCellStyle(style3);
			            Cell cell101 = row10.createCell(1);
			            cell101.setCellValue(project.getTitle());
			            cell101.setCellStyle(style3);
			            Cell cell102 = row10.createCell(2);
			            cell102.setCellValue(formatter.format(assignment.getStartDate()));
			            cell102.setCellStyle(style3);
			            Cell cell103 = row10.createCell(3);
			            cell103.setCellValue(formatter.format(assignment.getEndDate()));
			            cell103.setCellStyle(style3);
			            Cell cell104 = row10.createCell(4);
			            cell104.setCellValue(assignment.getGoal()+" hours");
			            cell104.setCellStyle(style3);
			            Cell cell105 = row10.createCell(5);
			            cell105.setCellValue(actualWorkHour+" hours");
			            cell105.setCellStyle(style3);
			            Cell cell106 = row10.createCell(6);
			            cell106.setCellValue("% "+percentage);
			            cell106.setCellStyle(style3);
			        }
			         
			        //autosizing the columns according to the with of of the inputs
			        sheet.autoSizeColumn(0);
			        sheet.autoSizeColumn(1);
			        sheet.autoSizeColumn(2);
			        sheet.autoSizeColumn(3);
			        sheet.autoSizeColumn(4);
			        sheet.autoSizeColumn(5);
			        sheet.autoSizeColumn(6);
			        
			        //lets write the excel data to file now
			        String fullpath=path + File.separator + fileName;
			        FileOutputStream fos = new FileOutputStream(fullpath);
			        workbook.write(fos);
			        fos.close();
			        //System.out.println(fullpath + " written successfully");
	
			        reportFileID=dataManager.insertFileDataUnchecked(employee.getUsername(),"performance_evaluation_report", fullpath);
			        
			       String[] factorNames={"Job Knowledge","Productivity","Work Quality","Technical Skills","Work Consistency","Enthusiasm","Cooperation","Attitude","Initiative","Work Relations","Creativity","Punctuality","Attendance","Dependability","Communication Skills","Overall Rating"};
			       List<Factor> factorList=new ArrayList<Factor>();
			        
			        for(String name:factorNames){	
					    
						Factor factor=new Factor();
						factor.setName(name);
						
						factorList.add(factor);
					}
					
			        
			        
			        
							evaluation=new Evaluation();
							evaluation.setCreatedDate(new java.util.Date());
							evaluation.setStartDate(startDate);
							evaluation.setEndDate(endDate);
							evaluation.setEmployeeID(employeeID);
							evaluation.setSupervisorID(employee.getSupervisorId());
							evaluation.setStatus(status);
							evaluation.setReportFileID(reportFileID);
							evaluation.setFactors(factorList);
							evaluation.setNotes("");
							
							if(evaluation!=null)
								dbOK=dataManager.saveNewEvaluation(evaluation);
							else
								System.out.println("Performance Evaluation cannot be created");
				
		}
		else{
			strError="Employee, Start and End Date fields cannot be null.";	
			
			valOK=false;
			dbOK=false;
		}
	
		
		
		RequestDispatcher dispatcher;
		if (!dbOK || !valOK)
		{	
			
			request.setAttribute("error", strError);
			dispatcher = request.getRequestDispatcher("/newperformanceeval.jsp");
		}
		else{
			
			
				
			String subject="Employee Performance Evaluation Request Notification for "+employee.getFirstname()+" "+employee.getLastname()+" by "+unamEmp.getCompanyName()+" in BestJobs.";
			String content="<i>Dear "+supervisor.getFirstname()+" "+supervisor.getLastname()+",</i><br/>";
			content+="<i>Since performance evaluation period has started for the period between "+formatter.format(evaluation.getStartDate())+" and "+formatter.format(evaluation.getEndDate())+", you are requested to conduct the performance evaluation of \""+employee.getFirstname()+" "+employee.getLastname()+"\" on "+sdf.format(evaluation.getCreatedDate())+".</i><br/>";
			content+="<i>Please conduct your evaluation in Performance Evaluation records assigned to you in Best Jobs, utilizing the Performance Evaluation Report provided by the system. After your submission your feedbacks will be shared with \""+supervisor.getFirstname()+" "+supervisor.getLastname()+"\" and stored in our system.</i><br/>";
			content+="<i>Best Regards,</i><br/>";
			content+="<i>"+unamEmp.getCompanyName()+" Human Resources Team </i><br/>";
			content+="<i><b>Contact Email : "+unamEmp.getEmail()+" | Phone : "+unamEmp.getPhone()+"</b><i><br/>";
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("recipient",employee.getCompanyEmail()));
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
			}
			strSuccess="You have successfully posted a project!";
			request.setAttribute("success", strSuccess);
			dispatcher = request.getRequestDispatcher("/performanceevals.jsp");
	
		dispatcher.forward( request, response);
		
	}
	
	

}









