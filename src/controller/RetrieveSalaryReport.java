package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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


import model.Country;
import model.Job;
import model.JobSeeker;
import model.Sector;

import DAO.DataManager;

/**
 * Servlet implementation class RetrieveReportServlet
 */
@WebServlet("/retrieveSalaryReport")
public class RetrieveSalaryReport extends DBConnectionServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;
	private String path="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveSalaryReport() {
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
		
		int jobID=0;
		
		if (request.getParameter("jobID")!=null){
			jobID=Integer.parseInt(request.getParameter("jobID"));
		}
		Job job=dataManager.getJobData(jobID);
		Sector sector=dataManager.getSectorData(job.getSectorID());
		Country country=dataManager.getCountryData(job.getCountryID());
		
		
		java.text.SimpleDateFormat sdfFile = new java.text.SimpleDateFormat("yyyy-MM-dd_hh_mm_ss");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		java.util.Date now=new java.util.Date();
		
		JobSeeker uname =(JobSeeker)request.getSession().getAttribute("jobseeker");
		
		
		if (uname==null){
			uname=(JobSeeker)request.getSession().getAttribute("employee");
		}
		
		int reportFileID=0;
		Workbook workbook = null;
        String fileName="Employment_Salary_Report"+"_job_id_"+job.getJid()+"_"+sdfFile.format(now)+".xlsx";
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
         
        Sheet sheet = workbook.createSheet("Employment Salary Report");
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, -1);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        System.out.println("last month"+month);
        System.out.println("year"+year);
        sheet.addMergedRegion(new CellRangeAddress(/*Row*/0,0,/*Column*/0,2));
        
        int rowIndex = 0;
        
       
        Row row0 = sheet.createRow(rowIndex++);
        Cell cell0 = row0.createCell(0);
        cell0.setCellValue("Employment Salary Report (Year on Year)");
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
        cell10.setCellValue("Reporting Date:");
        cell10.setCellStyle(style2);
        Cell cell11 = row1.createCell(1);
        cell11.setCellStyle(style3);
        cell11.setCellValue(sdf.format(new java.util.Date()));
        Row row2 = sheet.createRow(rowIndex++);
        Cell cell20 = row2.createCell(0);
        cell20.setCellValue("Job Position:");
        cell20.setCellStyle(style2);
        Cell cell21 = row2.createCell(1);
        cell21.setCellValue(job.getTitle());
        cell21.setCellStyle(style3);
        
        Row row3 = sheet.createRow(rowIndex++);
        Cell cell30 = row3.createCell(0);
        cell30.setCellValue("Country:");
        cell30.setCellStyle(style2);
        Cell cell31 = row3.createCell(1);
        cell31.setCellValue(country.getName());
        cell31.setCellStyle(style3);
        
        Row row4 = sheet.createRow(rowIndex++);
        Cell cell40 = row4.createCell(0);
        cell40.setCellValue("Sector:");
        cell40.setCellStyle(style2);
        Cell cell41 = row4.createCell(1);
        cell41.setCellValue(sector.getName());
        cell41.setCellStyle(style3);
        
        Row row5 = sheet.createRow(rowIndex++);
        Cell cell50 = row5.createCell(0);
        cell50.setCellValue("Experience");
        cell50.setCellStyle(style2);
        Cell cell51 = row5.createCell(1);
        cell51.setCellValue(job.getExperience());
        cell51.setCellStyle(style3);
        
        //Collecting data
        List<Integer> salaryList=dataManager.getMatchingJobSalaryList(job.getJid());
        
        
        int averageSalary=0;
        int minimumSalary=0;
        int maximumSalary=0;
        int sum=0;
        int temp=0;
        int temp2=0;
        for(int i=0;i<salaryList.size();i++){
        	System.out.println("Salary:"+salaryList.get(i).intValue());
        	sum+=salaryList.get(i).intValue();
        	if(temp==0 || salaryList.get(i).intValue()>temp)
        		temp=salaryList.get(i).intValue();
        	if(temp2==0 || salaryList.get(i).intValue()<temp2)
        		temp2=salaryList.get(i).intValue();
        	if(i==salaryList.size()-1){
        		averageSalary=(int)sum/salaryList.size();
        		maximumSalary=temp;
        		minimumSalary=temp2;
        	}
        		
        }
        	
        Row row6 = sheet.createRow(rowIndex++);
        Cell cell60 = row6.createCell(0);
        cell60.setCellValue("Average Salary");
        cell60.setCellStyle(style2);
        Cell cell61 = row6.createCell(1);
        cell61.setCellValue(averageSalary+" "+country.getCurrency());
        cell61.setCellStyle(style3);
        
        
		
        Row row7 = sheet.createRow(rowIndex++);
        Cell cell70 = row7.createCell(0);
        cell70.setCellValue("Minimum Salary");
        cell70.setCellStyle(style2);
        Cell cell71 = row7.createCell(1);
        cell71.setCellValue(minimumSalary+" "+country.getCurrency());
        cell71.setCellStyle(style3);
        
        
		
        Row row8 = sheet.createRow(rowIndex++);
        Cell cell80 = row8.createCell(0);
        cell80.setCellValue("Maximum Salary");
        cell80.setCellStyle(style2);
        Cell cell81 = row8.createCell(1);
        cell81.setCellValue(maximumSalary+" "+country.getCurrency());
        cell81.setCellStyle(style3);
        
        
        //Collect Data
        /*List<NameValuePair> employerJobList=(List<NameValuePair>) dataManager.getAllEmployerMonthlyPosts(year,month);
        Iterator<NameValuePair> iterator = employerJobList.iterator();
        while(iterator.hasNext()){
        	NameValuePair pair = iterator.next();
           
            		
            Row row5 = sheet.createRow(rowIndex++);
            Cell cell50 = row5.createCell(0);
            cell50.setCellValue(pair.getName());
            cell50.setCellStyle(style3);
            Cell cell51 = row5.createCell(1);
            cell51.setCellValue(pair.getValue());
            cell51.setCellStyle(style3);
 
        }*/
         
        //autosizing the columns according to the with of of the inputs
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        String fullpath=path + File.separator + fileName;
        FileOutputStream fos = new FileOutputStream(fullpath);
        workbook.write(fos);
        fos.close();
        //System.out.println(fullpath + " written successfully");

        reportFileID=dataManager.insertFileDataUnchecked(uname.getUsername(),"employment_salary_report", fullpath);
        

        String basePath=request.getRequestURL().toString().substring(0,request.getRequestURL().toString().indexOf(request.getServletPath()));
        try  {
        DefaultHttpClient httpClient = new DefaultHttpClient();
	  	HttpGet httpGet = new HttpGet(basePath+"/retrievefile?type=employment_salary_report&contenttype=application/vnd.openxmlformats-officedocument.spreadsheetml.sheet&fileID="+reportFileID);
	  	
	  	HttpResponse httpResponse = httpClient.execute(httpGet);
	  	HttpEntity httpEntity = httpResponse.getEntity();
	  	InputStream is = httpEntity.getContent();
	  	BufferedInputStream in = new BufferedInputStream(is);
	  	ServletOutputStream out;  
	    out = response.getOutputStream();  
	    BufferedOutputStream bout = new BufferedOutputStream(out); 
	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	  	int ch =0;
	  	while((ch=in.read())!=-1)  
	    {  
	    bout.write(ch);  
	    }  
	      
	    in.close();  
	    in.close();  
	    bout.close();  
	    out.close();  
	  	out.flush();
	  	out=null;
	  	httpClient.close();
		}
        catch(Exception ex){ex.printStackTrace();}
        
	}

}


