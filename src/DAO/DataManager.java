package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import model.*;

import com.mysql.jdbc.Statement;


public class DataManager 
{
	
	private String dbURL="";
	private String dbUserName="";
	private String dbPassword="";
	
	public void setDbURL(String dbURL)
	{
		this.dbURL= dbURL ;
	}
	
	public String getDbURL()
	{
		return this.dbURL;
	}
	
	public void setDbUserName(String dbUserName) 
	{
		this.dbUserName = dbUserName;
    }
    
    public String getDbUserName() 
    {
    	return this.dbUserName;
    }
    
    public void setDbPassword(String dbPassword) 
    {
    	this.dbPassword = dbPassword;
    }
    
    public String getDbPassword() 
    {
    	return this.dbPassword;
    }
    
   
    public Connection getConnection() 
    {
    	
	    java.sql.Connection conn = null;
	    try {
	    	Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(getDbURL(), getDbUserName(),getDbPassword());// burada hata var??
	      }
	    catch (Exception e) {
	      System.out.println("Could not connect to DB");
	      e.printStackTrace();
	      }
	    return conn;
    }
    
    public void putConnection(Connection conn) 
    {
	    if (conn != null) 
	    {
	    	try 
	    	{ 
	    		conn.close(); 
	    	}
	      catch (SQLException e) {
	      	System.out.println("Enable to close DB connection");
	      	e.printStackTrace(); }
	    }
    }
    
    @SuppressWarnings("finally")
	public boolean saveJobSeeker(JobSeeker jobseeker){
    	
    	Connection conn = getConnection();
    	boolean result=true;
    	if(conn != null)
    	{
    		try {
    		String sql = "insert into jobseekers "
    				+ "  (username, firstname, lastname,phone,email,address,password,job)"
    				+ "  values(?,?,?,?,?,?,?,?)";
    		
	    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
    		
    		preparedStatement.setString(1, jobseeker.getUsername());
    		preparedStatement.setString(2, jobseeker.getFirstname());
    		preparedStatement.setString(3, jobseeker.getLastname());
    		preparedStatement.setString(4, jobseeker.getPhone());
    		preparedStatement.setString(5, jobseeker.getEmail());
    		preparedStatement.setString(6, jobseeker.getAddress());
    		preparedStatement.setString(7, jobseeker.getPassword());
    		preparedStatement.setString(8, jobseeker.getJob());
    	
    		preparedStatement.executeUpdate();
    		preparedStatement.close(); 

    		}
			catch(SQLException ex){
				ex.printStackTrace();
				result=false;
				}
			finally 
			{
				putConnection(conn);
				return result;
	        }
    	}
    	return result;
    }
    
    
    @SuppressWarnings("finally")
   	public boolean saveNewEmployee(Employee employee){
       	
       	Connection conn = getConnection();
       	boolean result=true;
       	if(conn != null)
       	{
       		try {
       		String sql = "insert into employees "
       				+ "  (jobseekerid, eid, hiringdate,status,supervisorid,companyEmail,salary,currency)"
       				+ "  values(?,?,?,?,?,?,?,?)";
       		
   	    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
       		
       		preparedStatement.setInt(1, employee.getJobseekerId());
       		preparedStatement.setInt(2, employee.getEid());
       		preparedStatement.setTimestamp(3, new java.sql.Timestamp(employee.getHiringDate().getTime()));
       		preparedStatement.setString(4, employee.getStatus());
       		preparedStatement.setInt(5, employee.getSupervisorId());
       		preparedStatement.setString(6, employee.getCompanyEmail());
       		preparedStatement.setInt(7, employee.getSalary());
       		preparedStatement.setString(8, employee.getCurrency());
       		preparedStatement.executeUpdate();
       		preparedStatement.close(); 

       		}
   			catch(SQLException ex){
   				ex.printStackTrace();
   				result=false;
   				}
   			finally 
   			{
   				putConnection(conn);
   				return result;
   	        }
       	}
       	return result;
       }
    
public boolean updateEmployee(Employee employee){
    	
    	Connection conn = getConnection();
    	if(conn != null)
    	{
    		try {
    		String sql = "update employees "
    				+ "  set status=?, companyEmail=?, supervisorid=?"
    				+ "  where id=?";
    		
	    	PreparedStatement preparedStatement = conn.prepareStatement(sql);

    		preparedStatement.setString(1, employee.getStatus());
    		preparedStatement.setString(2, employee.getCompanyEmail());
    		preparedStatement.setInt(3, employee.getSupervisorId());
    		preparedStatement.setInt(4, employee.getId());
    		
    		
    		preparedStatement.executeUpdate();
    		preparedStatement.close(); 

    		}
			catch(SQLException ex){
				ex.printStackTrace();
				return false;
			}
    		
    	}
    	return true;
    }
    
public boolean updateJobSeeker(JobSeeker jobseeker){
    	
    	Connection conn = getConnection();
    	if(conn != null)
    	{
    		try {
    		String sql = "update jobseekers "
    				+ "  set firstname=?, lastname=?,phone=?,email=?,address=?,password=?,job=?"
    				+ "  where username=?";
    		
	    	PreparedStatement preparedStatement = conn.prepareStatement(sql);

    		preparedStatement.setString(1, jobseeker.getFirstname());
    		preparedStatement.setString(2, jobseeker.getLastname());
    		preparedStatement.setString(3, jobseeker.getPhone());
    		preparedStatement.setString(4, jobseeker.getEmail());
    		preparedStatement.setString(5, jobseeker.getAddress());
    		preparedStatement.setString(6, jobseeker.getPassword());
    		preparedStatement.setString(7, jobseeker.getJob());
    		preparedStatement.setString(8, jobseeker.getUsername());
    		
    		preparedStatement.executeUpdate();
    		preparedStatement.close(); 

    		}
			catch(SQLException ex){
				ex.printStackTrace();
				return false;
			}
    		
    	}
    	return true;
    }
   
 public boolean addQualification(String schoolName,String degree,float gpa ,int personid){
    	
    	Connection conn = getConnection();
    	boolean result=true;
    	if(conn != null)
    	{
    		try {
    		String sql = "insert into qualifications "
    				+ "  (schoolname, degree, gpa,personid)"
    				+ "  values(?,?,?,?)";
    		
	    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    	preparedStatement.setString(1, schoolName);
	    	preparedStatement.setString(2, degree);
    		preparedStatement.setFloat(3, gpa);
    		preparedStatement.setInt(4,personid);
    		preparedStatement.executeUpdate();
    		preparedStatement.close(); 
    		}
			catch(SQLException ex){
					ex.printStackTrace();
					result=false;
			}
			finally 
			{
				putConnection(conn);
				return result;
	        }
    	}
    	return result;
    }
 
 public List<Qualification> getJobSeekerQualifications(String userName)
	{	
		List<Qualification> userQualifications= new ArrayList<Qualification>();
		Connection conn = getConnection();	
	    if (conn != null) 
	    {
	    	ResultSet rs = null;
	    	//Statement stmt = null;
	    	PreparedStatement preparedStatement = null;
			try
			{
				String strQuery = "SELECT B.*"
					+" FROM jobseekers A, qualifications B WHERE A.id=B.personid and A.username=?";
					
				preparedStatement = conn.prepareStatement(strQuery);
				preparedStatement.setString(1,userName);
				rs = preparedStatement.executeQuery();
				
				while(rs.next())
				{
					Qualification qualification= new Qualification();
					qualification.setQualificationId(rs.getInt("id"));
					qualification.setSchoolName(rs.getString("schoolname"));
					qualification.setDegree(rs.getString("degree"));
					qualification.setGpa(rs.getString("gpa"));
					qualification.setPersonId(rs.getInt("personid"));
					userQualifications.add(qualification);
				}				
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
	        	try 
	        	{ 
	        		rs.close();
	        		preparedStatement.close(); 
	        	}
	          	catch (SQLException e) {e.printStackTrace();}
	          	putConnection(conn);	        
	        }
	    }
	    return userQualifications;
	}	
 
 public void removeJobSeekerQualification(int personId,int qualificationId){
	 
	 	Connection conn = getConnection();
	 	if(conn != null)
	 	{
	 		try {
	 		String sql = "delete from qualifications "
	 				+ "  where id=?";
	 		
	 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
	 		preparedStatement.setInt(1,qualificationId);
	 		preparedStatement.executeUpdate();
	 		preparedStatement.close(); 
	 		}
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
					putConnection(conn);	        
		        }
	 	}
	 	
}
 @SuppressWarnings("finally")
public boolean addExperience(String  companyName,String years ,int personId,String position){
 	
 	Connection conn = getConnection();
 	boolean result=true;
 	if(conn != null)
 	{
 		try {
 		String sql = "insert into experience "
 				+ "  (companyname, years,personId,position)"
 				+ "  values(?,?,?,?)";
 		
 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
 		preparedStatement.setString(1,companyName);
 		preparedStatement.setString(2,years);
 		preparedStatement.setInt(3,personId);
 		preparedStatement.setString(4,position);
 		preparedStatement.executeUpdate();
 		preparedStatement.close(); 
 		
 		}
			catch(SQLException ex){
				ex.printStackTrace();
				result=false;
				}
			finally 
			{
				putConnection(conn);
				return result;
	        }
 	}
 	return result;
 }
 
 
 public List<Experience> getJobSeekerExperiences(String userName)
	{	
		List<Experience> userExperiences= new ArrayList<Experience>();
		Connection conn = getConnection();	
	    if (conn != null) 
	    {
	    	ResultSet rs = null;
	    	//Statement stmt = null;
	    	PreparedStatement preparedStatement = null;
			try
			{
				String strQuery = "SELECT B.*"
					+" FROM jobseekers A, experience B WHERE A.id=B.personid and A.username=?";
					
				preparedStatement = conn.prepareStatement(strQuery);
				preparedStatement.setString(1,userName);
				rs = preparedStatement.executeQuery();
				
				while(rs.next())
				{
					Experience experience = new Experience();
					experience.setExperienceId(rs.getInt("id"));
					experience.setCompany(rs.getString("companyname"));
					experience.setYears(rs.getString("years"));
					experience.setPersonid(rs.getInt("personid"));
					experience.setPosition(rs.getString("position"));
					userExperiences.add(experience);
				}				
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
	        	try 
	        	{ 
	        		rs.close();
	        		preparedStatement.close(); 
	        	}
	          	catch (SQLException e) {e.printStackTrace();}
	          	putConnection(conn);	        
	        }
	    }
	    return userExperiences;
	}	
 
 public void removeJobSeekerExperience(int personId,int experienceId){
	 
	 	Connection conn = getConnection();
	 	if(conn != null)
	 	{
	 		try {
	 		String sql = "delete from experience "
	 				+ "  where id=?";
	 		
	 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
	 		preparedStatement.setInt(1,experienceId);
	 		preparedStatement.executeUpdate();
	 		preparedStatement.close(); 
	 		}
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
					putConnection(conn);	        
		        }
	 	}
	 	
}
    
 public void addSkillToJobSeeker(int personId,int skillId){
	 	
	 	Connection conn = getConnection();
	 	if(conn != null)
	 	{
	 		try {
	 		String sql = "insert into jobseekerskills "
	 				+ "  (jobseekerid, skillid)"
	 				+ "  values(?,?)";
	 		
	 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
	 		preparedStatement.setInt(1,personId);
	 		preparedStatement.setInt(2,skillId);
	 		preparedStatement.executeUpdate();
	 		preparedStatement.close(); 
	 		}
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
					putConnection(conn);	        
		        }
	 	}
	 	
}
    
 public void removeJobSeekerSkill(int personId,int skillId){
	 
	 	Connection conn = getConnection();
	 	if(conn != null)
	 	{
	 		try {
	 		String sql = "delete from jobseekerskills "
	 				+ "  where jobseekerid=? and skillid=?";
	 		
	 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
	 		preparedStatement.setInt(1,personId);
	 		preparedStatement.setInt(2,skillId);
	 		preparedStatement.executeUpdate();
	 		preparedStatement.close(); 
	 		}
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
					putConnection(conn);	        
		        }
	 	}
	 	
}
 public void addSkillToJob(int jobId,int skillId){
	 	
	 	Connection conn = getConnection();
	 	if(conn != null)
	 	{
	 		try {
	 		String sql = "insert into jobskills "
	 				+ "  (jobid, skillid)"
	 				+ "  values(?,?)";
	 		
	 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
	 		preparedStatement.setInt(1,jobId);
	 		preparedStatement.setInt(2,skillId);
	 		preparedStatement.executeUpdate();
	 		preparedStatement.close(); 
	 		}
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
					putConnection(conn);	        
		        }
	 	}
	 	
}
 
 public JobApplication applyJob(int personId,int jobId){
	 	JobApplication jobApplication = null;
		Connection conn = getConnection();
	 	if(conn != null)
	 	{
	 		try {
	 		String sql = "insert into jobapplication"
	 				+ "  (personId, jobId, applicationdate, status)"
	 				+ "  values(?,?,?,?)";
	 		
	 		PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	 		preparedStatement.setInt(1,personId);
	 		preparedStatement.setInt(2,jobId);
	 		//java.sql.Date applicationSQLDate=new java.sql.Date(new java.util.Date().getTime());
	 		//preparedStatement.setDate(3, applicationSQLDate);
	 		java.sql.Timestamp applicationSQLDate=new java.sql.Timestamp(new java.util.Date().getTime());
	 		preparedStatement.setTimestamp(3,applicationSQLDate);
	 		preparedStatement.setString(4, "Applied");
	 		preparedStatement.executeUpdate();
	 		ResultSet rs=preparedStatement.getGeneratedKeys();
	 		jobApplication = new JobApplication(jobId,personId,applicationSQLDate);
	 		
	 		while(rs.next())
			{
	 			jobApplication.setId(rs.getInt(1));
			}
	 		preparedStatement.close();
	 		
	 		}
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
					putConnection(conn);	        
		        }
	 	}
	 return jobApplication;
 }
    
 @SuppressWarnings("finally")
public boolean saveEmployer(Employer employer){
    	
    	Connection conn = getConnection();
    	boolean result=true;
    	if(conn != null)
    	{
    		try {
	    		String sql = "insert into employers "
	    				+ "  (username,password,phone,companyname,email,address,description,isactive,createddate)"
	    				+ "  values(?,?,?,?,?,?,?,?,?)";
	    		
		    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    		
	    		preparedStatement.setString(1, employer.getUsername());
	    		preparedStatement.setString(2, employer.getPassword());
	    		preparedStatement.setString(3, employer.getPhone());
	    		preparedStatement.setString(4, employer.getCompanyName());
	    		preparedStatement.setString(5, employer.getEmail());
	    		preparedStatement.setString(6, employer.getAddress());
	    		preparedStatement.setString(7, employer.getDescription());
			    preparedStatement.setString(8, "N");
	    		preparedStatement.setTimestamp(9, new java.sql.Timestamp(employer.getCreatedDate().getTime()));
	    		
	    		preparedStatement.executeUpdate();
	    		preparedStatement.close(); 

    		}
			catch(SQLException ex){
				
				ex.printStackTrace();
				result=false;
			}
			finally 
			{
				putConnection(conn);
				return result;
	        }
    	}
		return result;
    	
    }
 

public boolean updateEmployer(Employer employer){
    	
    	Connection conn = getConnection();
    	boolean result=true;
    	if(conn != null)
    	{
    		try {
	    		String sql = "UPDATE employers SET password=?,phone=?,companyname=?,email=?,address=?,description=?,isactive=?,createddate=?,activationdate=? WHERE username=?";
	    		
		    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    		
	    		preparedStatement.setString(1, employer.getPassword());
	    		preparedStatement.setString(2, employer.getPhone());
	    		preparedStatement.setString(3, employer.getCompanyName());
	    		preparedStatement.setString(4, employer.getEmail());
	    		preparedStatement.setString(5, employer.getAddress());
	    		preparedStatement.setString(6, employer.getDescription());
	    		if(employer.isActive())
			    	preparedStatement.setString(7, "Y");
		    		else
				    preparedStatement.setString(7, "N");
	    		
	    		preparedStatement.setTimestamp(8, new java.sql.Timestamp(employer.getCreatedDate().getTime()));
	    		if(employer.getActivationDate()!=null)
	    		preparedStatement.setTimestamp(9, new java.sql.Timestamp(employer.getActivationDate().getTime()));
	    		else
	    		preparedStatement.setTimestamp(9, null);
	    		preparedStatement.setString(10, employer.getUsername());
	    		
	    		preparedStatement.executeUpdate();
	    		preparedStatement.close(); 

    		}
			catch(SQLException ex){
				
				ex.printStackTrace();
				result=false;
			}
		
    	}
		return result;
    	
    }
 
 public boolean saveNewJob(Job job){
	 
		Connection conn = getConnection();
		List<Skill> jobSkills=job.getJobSkills();
		boolean result=true;
    	if(conn != null)
    	{
    		try {
    		
    		String sql = "insert into job (title,eid,description,postdate, status,sectorid,cityid,salary,experience,countryid,currency) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

	    	PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    		
    		preparedStatement.setString(1, job.getTitle());
    		preparedStatement.setInt(2, job.getEid());
    		preparedStatement.setString(3, job.getDescription());
			//java.sql.Date postSQLDate=new java.sql.Date(job.getPostDate().getTime());
			
    		//preparedStatement.setDate(4, postSQLDate);
    		preparedStatement.setTimestamp(4, new java.sql.Timestamp(job.getPostDate().getTime()));
    		preparedStatement.setString(5, job.getStatus());
    		preparedStatement.setInt(6, job.getSectorID());
    		preparedStatement.setInt(7, job.getCityID());
    		preparedStatement.setInt(8, job.getSalary());
    		preparedStatement.setInt(9, job.getExperience());
    		preparedStatement.setInt(10, job.getCountryID());
    		preparedStatement.setString(11, job.getCurrency());
    		preparedStatement.executeUpdate();
    		ResultSet rs=preparedStatement.getGeneratedKeys();
    		
    		while(rs.next())
			{
    		job.setJid(rs.getInt(1));
			}
    		preparedStatement.close(); 
    		
    		}
			catch(SQLException ex){
				
				ex.printStackTrace();
			result=false;	
			return result;
			}
			finally 
			{
				putConnection(conn);
				
	        }
    		
    	
    		for (Skill skill:jobSkills)
    			
    			addSkillToJob(job.getJid(),skill.getSkillID());
    		
    	}
	 return result;
 }
 
 @SuppressWarnings("finally")
public boolean updateJob(Job job){
	 
		Connection conn = getConnection();
		//List<Skill> jobSkills=job.getJobSkills();
		boolean result=true;
 	if(conn != null)
 	{
 		try {
 		
 		String sql = "update job set title=? ,description=? ,status=? ,sectorid=? ,cityid=? ,salary=? ,experience=?, countryid=?, currency=? where jid=?";

	    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
 		
 		preparedStatement.setString(1, job.getTitle());
 		preparedStatement.setString(2, job.getDescription());
 		preparedStatement.setString(3, job.getStatus());
 		preparedStatement.setInt(4, job.getSectorID());
 		preparedStatement.setInt(5, job.getCityID());
 		preparedStatement.setInt(6, job.getSalary());
 		preparedStatement.setInt(7, job.getExperience());
 		preparedStatement.setInt(8, job.getCountryID());
 		preparedStatement.setString(9, job.getCurrency());
 		preparedStatement.setInt(10, job.getJid());
 		
 		preparedStatement.executeUpdate();

 		preparedStatement.close(); 
 		result=true;
 		}
			catch(SQLException ex){
				ex.printStackTrace();
			result=false;	
			}
			finally 
			{
				putConnection(conn);
				return result;
	        }
 		
 		//System.out.println("Job ID "+job.getJid());
 		/*for (Skill skill:jobSkills)
 			
 			addSkillToJob(job.getJid(),skill.getSkillID());
 		*/
 	}
 	return result;
}
 @SuppressWarnings("finally")
public boolean saveNewInterview(Interview interview){
	 
		Connection conn = getConnection();
		boolean result = false;
 	if(conn != null)
 	{
 		try {
 		
 		String sql = "insert into interviews(jobid,applicationid,date, status,location,interviewer,email,sequenceno,notes,salary,currency,resultcode) values(?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";

	    PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
 		
 		preparedStatement.setInt(1, interview.getJobId());
 		preparedStatement.setInt(2, interview.getApplicationId());
 		
 		//java.sql.Date postSQLDate=new java.sql.Date(interview.getDate().getTime());
 		//preparedStatement.setDate(3, postSQLDate);
 		preparedStatement.setTimestamp(3, new java.sql.Timestamp(interview.getDate().getTime()));
 		preparedStatement.setString(4, interview.getStatus());
 		preparedStatement.setString(5, interview.getLocation());
 		preparedStatement.setString(6, interview.getInterviewer());
 		preparedStatement.setString(7, interview.getContactEmail());
 		preparedStatement.setInt(8, interview.getSequenceNo());
 		preparedStatement.setString(9, interview.getNotes());
 		preparedStatement.setInt(10, interview.getSalary());
 		preparedStatement.setString(11, interview.getCurrency());
 		preparedStatement.setString(12, interview.getResultCode());
 		preparedStatement.executeUpdate();
 		ResultSet rs=preparedStatement.getGeneratedKeys();
 		
 		while(rs.next())
			{
 			interview.setId(rs.getInt(1));
			}
 		preparedStatement.close(); 
 		result=true;
 		}
			catch(SQLException ex){
				ex.printStackTrace();
				result=false;
				}
			finally 
			{
				putConnection(conn);	
				return result;
	        }
 		
 	}
 	return result;
}

 @SuppressWarnings("finally")
public boolean updateInterview(Interview interview){
	 
		Connection conn = getConnection();
		boolean result = true;
	if(conn != null)
	{
		try {
		
		String sql = "update interviews set date=?,status=?,location=?,interviewer=?,email=?,notes=?,salary=?,resultcode=? where id=?";

	    PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    
	    //java.sql.Date date=new java.sql.Date(interview.getDate().getTime());
	    
		//preparedStatement.setDate(1, date);
	    preparedStatement.setTimestamp(1, new java.sql.Timestamp(interview.getDate().getTime()));
		preparedStatement.setString(2, interview.getStatus());
		preparedStatement.setString(3, interview.getLocation());
		preparedStatement.setString(4, interview.getInterviewer());
		preparedStatement.setString(5, interview.getContactEmail());
		preparedStatement.setString(6, interview.getNotes());
 		preparedStatement.setInt(7, interview.getSalary());
 		preparedStatement.setString(8, interview.getResultCode());
		preparedStatement.setInt(9, interview.getId());
		preparedStatement.executeUpdate();

		preparedStatement.close(); 
		result=true;
		}
			catch(SQLException ex){
				ex.printStackTrace();
				result=false;
				}
			finally 
			{
				putConnection(conn);	
				return result;
	        }
		
	}
	return result;
}
 public List<Interview> getAllInteviewsByEmployer(int eid,String status){
	  	
	  	List<Interview> interviewList = new ArrayList<Interview>();
	  	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{	
					String strQuery;
					if (status==""){
						strQuery = 
							"SELECT i.*"
							+" FROM interviews i , job j, jobapplication a where a.jobid=j.jid and a.id=i.applicationid and j.eid=? order by i.date asc";
						preparedStatement = conn.prepareStatement(strQuery);
						preparedStatement.setInt(1, eid);
					}
					else
					{
						strQuery = 
								"SELECT i.*"
							+" FROM interviews i , job j, jobapplication a where a.jobid=j.jid and a.id=i.applicationid and j.eid=? and i.status=? order by i.date asc";
						preparedStatement = conn.prepareStatement(strQuery);
						preparedStatement.setInt(1, eid);
						preparedStatement.setString(2, status);
					}
					/*
					stmt = conn.createStatement();
					rs = stmt.executeQuery( strQuery);*/
					
					
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						Interview interview = new Interview();
						interview.setId(rs.getInt("id"));
						interview.setApplicationId(rs.getInt("applicationid"));
						interview.setJobId(rs.getInt("jobid"));
						interview.setStatus(rs.getString("status"));
						interview.setDate(rs.getTimestamp("date"));
						interview.setInterviewer(rs.getString("interviewer"));
						interview.setContactEmail(rs.getString("email"));
						interview.setLocation(rs.getString("location"));
						interview.setSequenceNo(rs.getInt("sequenceno"));
						interview.setNotes(rs.getString("notes"));
						interview.setCurrency(rs.getString("currency"));
						interview.setResultCode(rs.getString("resultcode"));
						interview.setSalary(rs.getInt("salary"));
						interviewList.add(interview);
					}				
					rs.close();
	        		preparedStatement.close(); 
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
		    }
	  	return interviewList;
	  }
 public List<Interview> getMyInteviewCalls(int jobSeekerId,String status){
	  	
	  	List<Interview> interviewList = new ArrayList<Interview>();
	  	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{	
					String strQuery;
					if (status==""){
						strQuery = 
							"SELECT i.*"
							+" FROM interviews i , jobapplication a where a.id=i.applicationid and a.personId=? order by i.date asc";
						preparedStatement = conn.prepareStatement(strQuery);
						preparedStatement.setInt(1, jobSeekerId);
					}
					else
					{
						strQuery = 
								"SELECT i.*"
							+" FROM interviews i , jobapplication a where a.id=i.applicationid and a.personId=? and i.status=? order by i.date asc";
						preparedStatement = conn.prepareStatement(strQuery);
						preparedStatement.setInt(1, jobSeekerId);
						preparedStatement.setString(2, status);
					}
					/*
					stmt = conn.createStatement();
					rs = stmt.executeQuery( strQuery);*/
					
					
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						Interview interview = new Interview();
						interview.setId(rs.getInt("id"));
						interview.setApplicationId(rs.getInt("applicationid"));
						interview.setJobId(rs.getInt("jobid"));
						interview.setStatus(rs.getString("status"));
						interview.setDate(rs.getTimestamp("date"));
						interview.setInterviewer(rs.getString("interviewer"));
						interview.setContactEmail(rs.getString("email"));
						interview.setLocation(rs.getString("location"));
						interview.setSequenceNo(rs.getInt("sequenceno"));
						interview.setNotes(rs.getString("notes"));
						interview.setCurrency(rs.getString("currency"));
						interview.setResultCode(rs.getString("resultcode"));
						interview.setSalary(rs.getInt("salary"));
						
						interviewList.add(interview);
					}				
					rs.close();
	        		preparedStatement.close(); 
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
		    }
	  	return interviewList;
	  }
/*
    public void insertCVData(String username, String CVPath)
    {
    	Connection conn = getConnection();
    	if(conn != null)
    	{
    		ResultSet rs = null;
    		PreparedStatement preparedStatement=null;
    		try {
    		String sql;	
    		sql = "SELECT * FROM CV WHERE USERNAME = ?";
    		preparedStatement = conn.prepareStatement(sql);
    		preparedStatement.setString(1, username);
    		rs = preparedStatement.executeQuery();
			int cvID=0;
			while(rs.next())
			{
				cvID=rs.getInt("id");
			}				
    		rs.close();
    		preparedStatement.close(); 
			if (cvID>0){
				
				sql = "UPDATE CV SET PATH=? WHERE id=?";
				preparedStatement = conn.prepareStatement(sql);
	    		preparedStatement.setString(1, CVPath);
	    		preparedStatement.setInt(2, cvID);
	    		 
			}
			else{
				
				sql = "insert into cv values(0, ?, ?)";
				
				preparedStatement = conn.prepareStatement(sql);
	    		preparedStatement.setString(1, username);
	    		preparedStatement.setString(2, CVPath);
	    		
			}
	    	
			preparedStatement.executeUpdate();
    		preparedStatement.close();
    		
    		

    		}
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
				putConnection(conn);	        
	        }
    	}
    }
    */
 
    public void insertFileData(String username,String type, String path)
    {
    	Connection conn = getConnection();
    	if(conn != null)
    	{
    		ResultSet rs = null;
    		PreparedStatement preparedStatement=null;
    		try {
    		String sql;	
    		sql = "SELECT * FROM FILE WHERE USERNAME = ? AND TYPE=?";
    		preparedStatement = conn.prepareStatement(sql);
    		preparedStatement.setString(1, username);
    		preparedStatement.setString(2, type);
    		rs = preparedStatement.executeQuery();
			int id=0;
			while(rs.next())
			{
				id=rs.getInt("id");
			}				
    		rs.close();
    		preparedStatement.close(); 
			if (id>0){
				
				sql = "UPDATE FILE SET PATH=? WHERE id=?";
				preparedStatement = conn.prepareStatement(sql);
	    		preparedStatement.setString(1, path);
	    		preparedStatement.setInt(2, id);
	    		 
			}
			else{
				
				sql = "insert into FILE(type,path,username) values(?, ?, ?)";
				
				preparedStatement = conn.prepareStatement(sql);
	    		preparedStatement.setString(1, type);
	    		preparedStatement.setString(2, path);
	    		preparedStatement.setString(3, username);
			}
	    	
			preparedStatement.executeUpdate();
    		preparedStatement.close();
    		
    		

    		}
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
				putConnection(conn);	        
	        }
    	}
    }
    
    public int insertFileDataUnchecked(String username,String type, String path)
    {
    	Connection conn = getConnection();
    	int fileID=0;
    	if(conn != null)
    	{
    		ResultSet rs = null;
    		PreparedStatement preparedStatement=null;
    		String sql;	
    		
				
    		try{
				
				sql = "insert into FILE(type,path,username) values(?, ?, ?)";
				
				preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    		preparedStatement.setString(1, type);
	    		preparedStatement.setString(2, path);
	    		preparedStatement.setString(3, username);
	    		preparedStatement.executeUpdate();
	    		rs=preparedStatement.getGeneratedKeys();
	    		
	    		while(rs.next())
				{
	    			fileID=rs.getInt(1);
				}
	    		preparedStatement.close();
	    		
    		}
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
				putConnection(conn);	        
	        }
    	}
		return fileID;
    }
    
    public int insertFileData(String type, String path)
    {
    	Connection conn = getConnection();
    	int fileID=0;
    	if(conn != null)
    	{
    		ResultSet rs = null;
    		PreparedStatement preparedStatement=null;
    		String sql;	
    		
				
    		try{
				
				sql = "insert into FILE(type,path) values(?, ?)";
				
				preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    		preparedStatement.setString(1, type);
	    		preparedStatement.setString(2, path);
	    		preparedStatement.executeUpdate();
	    		rs=preparedStatement.getGeneratedKeys();
	    		
	    		while(rs.next())
				{
	    			fileID=rs.getInt(1);
				}
	    		preparedStatement.close();
	    		
    		}
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
				putConnection(conn);	        
	        }
    	}
		return fileID;
    }
    /*
    public String getCVPath(String username)
    {
    	Connection conn = getConnection();
    	String CVPath="";
    	if(conn != null)
    	{
    		ResultSet rs = null;
    		PreparedStatement preparedStatement=null;
    		try {
    		String sql;	
    		sql = "SELECT * FROM CV WHERE USERNAME = ?";
    		preparedStatement = conn.prepareStatement(sql);
    		preparedStatement.setString(1, username);
    		rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				CVPath=rs.getString("path");
			}				
    		rs.close();
    		preparedStatement.close();
    		}
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
				putConnection(conn);	        
	        }
    	}
    	return CVPath;
    }
    */
    public String getFilePath(String type,String username)
    {
    	Connection conn = getConnection();
    	String path="";
    	if(conn != null)
    	{
    		ResultSet rs = null;
    		PreparedStatement preparedStatement=null;
    		try {
    		String sql;	
    		sql = "SELECT * FROM FILE WHERE USERNAME = ? AND TYPE=?";
    		preparedStatement = conn.prepareStatement(sql);
    		preparedStatement.setString(1, username);
    		preparedStatement.setString(2, type);
    		rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				path=rs.getString("path");
			}				
    		rs.close();
    		preparedStatement.close();
    		}
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
				putConnection(conn);	        
	        }
    	}
    	return path;
    }
    
    public String getFilePath(int fileID)
    {
    	Connection conn = getConnection();
    	String path="";
    	if(conn != null)
    	{
    		ResultSet rs = null;
    		PreparedStatement preparedStatement=null;
    		try {
    		String sql;	
    		sql = "SELECT * FROM FILE WHERE id=?";
    		preparedStatement = conn.prepareStatement(sql);
    		preparedStatement.setInt(1, fileID);
    		
    		rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				path=rs.getString("path");
			}				
    		rs.close();
    		preparedStatement.close();
    		}
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
				putConnection(conn);	        
	        }
    	}
    	System.out.println("path"+path);
    	return path;
    }
    
  public List<Job> getAllJobs(String status){
    	
    	List<Job> jobList = new ArrayList<Job>();
    	Connection conn = getConnection();	
	    if (conn != null) 
	    {
	    	ResultSet rs = null;
	    	//Statement stmt = null;
	    	PreparedStatement preparedStatement = null;
			try
			{	
				String strQuery;
				if (status==""){
					strQuery = 
						"SELECT j.* , e.companyname as companyname  "
						+" FROM job j , employers e where e.eid=j.eid order by j.postdate desc";
					preparedStatement = conn.prepareStatement(strQuery);
					System.out.println(preparedStatement.toString());
				}
				else
				{
					strQuery = 
							"SELECT j.* , e.companyname as companyname  "
							+" FROM job j , employers e where j.status= ? and e.eid=j.eid order by j.postdate desc";
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setString(1, status);
				}
				/*
				stmt = conn.createStatement();
				rs = stmt.executeQuery( strQuery);*/
				
				
				rs = preparedStatement.executeQuery();
				
				while(rs.next())
				{
					Job job = new Job();
					job.setTitle(rs.getString("title"));
					job.setCompanyTitle(rs.getString("companyname"));
					job.setJid(rs.getInt("jid"));
					job.setDescription(rs.getString("description"));
					job.setPostDate(rs.getTimestamp("postdate"));
					job.setStatus(rs.getString("status"));
					job.setSectorID(rs.getInt("sectorid"));
					job.setCountryID(rs.getInt("countryid"));
					job.setCityID(rs.getInt("cityid"));
					job.setExperience(rs.getInt("experience"));
					job.setSalary(rs.getInt("salary"));
					job.setCurrency(rs.getString("currency"));
					jobList.add(job);
				}				
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
	        	try 
	        	{ 
	        		rs.close();
	        		preparedStatement.close(); 
	        	}
	          	catch (SQLException e) {e.printStackTrace();}
	          	putConnection(conn);	        
	        }
	    }
    	return jobList;
    }
  
  public List<Job> queryJobs(String keyword,int sectorId,String status,String appStatus, int jobSeekerID,int countryId,int cityId,String experience,String companyname){
  	
  	List<Job> jobList = new ArrayList<Job>();
  	Connection conn = getConnection();	
	    if (conn != null) 
	    {
	    	ResultSet rs = null;
	    	//Statement stmt = null;
	    	PreparedStatement preparedStatement = null;
			try
			{	
				
				String keywordStmt="";
				try{
						if(keyword!=""){
							keywordStmt="j.title like '%"+keyword+"%' or j.description like '%"+keyword+"%'";
						}
						else{
							keywordStmt="1=1";
						}
				}
				catch(NullPointerException ex){
					
					keywordStmt="1=1";
				}
				
				String statusStmt="";
				try{		
						if(status!=""){
							statusStmt="j.status='"+status+"'";
						}
						else{
							statusStmt="1=1";
						}
				}
				catch(NullPointerException ex){
					
					statusStmt="1=1";
				}
				
				String sectorStmt="";
				
				try{
					if(sectorId!=0){
						sectorStmt="j.sectorid="+sectorId+"";
					}
					else{
						sectorStmt="1=1";
					}
				}
				catch(NullPointerException ex){
					
					sectorStmt="1=1";
				}
				
				String appStatusStmt="";
				String tableStmt="";
				try{		
						if(appStatus!="" && jobSeekerID!=0){
							//System.out.println("JobSeekerID"+jobSeekerID);
							//System.out.println("appStatus"+appStatus);
							appStatusStmt="a.jobid=j.jid and a.personId="+jobSeekerID+" and a.status='"+appStatus+"'";
							tableStmt=", jobapplication a";
						}
						else{
							appStatusStmt="1=1";
							tableStmt="";
						}
				}
				catch(NullPointerException ex){
					
					appStatusStmt="1=1";
				}
				
				String countryStmt="";
				
				try{
					if(countryId!=0){
						countryStmt="j.countryid="+countryId+"";
					}
					else{
						countryStmt="1=1";
					}
				}
				catch(NullPointerException ex){
					
					countryStmt="1=1";
				}
				
				String cityStmt="";
				
				try{
					if(cityId!=0){
						cityStmt="j.cityid="+cityId+"";
					}
					else{
						cityStmt="1=1";
					}
				}
				catch(NullPointerException ex){
					
					cityStmt="1=1";
				}
				
				String experienceStmt="";
				
				try{
					if(experience!=""){
						
						switch(experience){
						case("0"): experienceStmt="j.experience=0"; break;
						case("0-1"): experienceStmt="j.experience>0 and j.experience<=1"; break;
						case("1-3"): experienceStmt="j.experience>1 and j.experience<=3"; break;
						case("3-5"): experienceStmt="j.experience>3 and j.experience<=5"; break;
						case("5-10"): experienceStmt="j.experience>5 and j.experience<=10"; break;
						case("10+"): experienceStmt="j.experience>10"; break;
						default:experienceStmt="1=1"; break;
						}
						
					}
					else{
						experienceStmt="1=1";
					}
				}
				catch(NullPointerException ex){
					
					experienceStmt="1=1";
				}
				
				String companyNameStmt="";
				try{
						if(companyname!=""){
							companyNameStmt="e.companyname like '%"+companyname+"%'";
						}
						else{
							companyNameStmt="1=1";
						}
				}
				catch(NullPointerException ex){
					
					companyNameStmt="1=1";
				}
				
				
				String strQuery = "SELECT j.* , e.companyname as companyname  "
						+" FROM job j , employers e"+tableStmt+" where e.eid=j.eid and "+appStatusStmt+" and "+keywordStmt+" and "+statusStmt+" and "+sectorStmt+" and "+countryStmt+" and "+cityStmt+" and "+experienceStmt+" and "+companyNameStmt+" order by j.postdate desc";
					
					Statement stmt=(Statement) conn.createStatement();
					rs=stmt.executeQuery(strQuery);
				
				
				while(rs.next())
				{
					Job job = new Job();
					job.setTitle(rs.getString("title"));
					job.setCompanyTitle(rs.getString("companyname"));
					job.setJid(rs.getInt("jid"));
					job.setDescription(rs.getString("description"));
					job.setPostDate(rs.getTimestamp("postdate"));
					job.setStatus(rs.getString("status"));
					job.setSectorID(rs.getInt("sectorid"));
					job.setCountryID(rs.getInt("countryid"));
					job.setCityID(rs.getInt("cityid"));
					job.setExperience(rs.getInt("experience"));
					job.setSalary(rs.getInt("salary"));
					job.setCurrency(rs.getString("currency"));
					jobList.add(job);
				}				
				rs.close();
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			
	    }
  	return jobList;
  }
    
  public List<JobSeeker> queryTalents(String status,boolean isEmployee,String firstName,String lastName,int supervisorID,int eid){
	  	
	  List<JobSeeker> talentList = new ArrayList<JobSeeker>();
	  	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{	
					
					
					
					String statusStmt="";
					try{		
							if(status!=""){
								statusStmt="e.status='"+status+"'";
							}
							else{
								statusStmt="1=1";
							}
					}
					catch(NullPointerException ex){
						
						statusStmt="1=1";
					}
					
					
					String firstNameStmt="";
					try{
							if(firstName!=""){
								firstNameStmt="j.firstname like '%"+firstName+"%'";
							}
							else{
								firstNameStmt="1=1";
							}
					}
					
					catch(NullPointerException ex){
						
						firstNameStmt="1=1";
					}
					
					String supervisorStmt="";
					
					try{
						if(isEmployee && supervisorID!=0){
							supervisorStmt="e.supervisorid="+supervisorID+"";
						}
						else{
							supervisorStmt="1=1";
						}
					}
					catch(NullPointerException ex){
						
						supervisorStmt="1=1";
					}
					
					
					String isEmployeeStmt="";
					String tableStmt="";
					
					try{
						if(isEmployee){
							isEmployeeStmt="j.id=e.jobseekerid and e.eid="+eid+" and e.id in (SELECT A.max_id from (select e.eid,e.jobseekerid,max(e.id) as max_id FROM EMPLOYEES e group by e.eid,e.jobseekerid) A)";
							tableStmt=", employees e";
						}
						else{
							isEmployeeStmt="1=1";
						}
					}
					catch(NullPointerException ex){
						
						isEmployeeStmt="1=1";
					}
					
					/*
					
					String experienceStmt="";
					
					try{
						if(experience!=""){
							
							switch(experience){
							case("0"): experienceStmt="j.experience=0"; break;
							case("0-1"): experienceStmt="j.experience>0 and j.experience<=1"; break;
							case("1-3"): experienceStmt="j.experience>1 and j.experience<=3"; break;
							case("3-5"): experienceStmt="j.experience>3 and j.experience<=5"; break;
							case("5-10"): experienceStmt="j.experience>5 and j.experience<=10"; break;
							case("10+"): experienceStmt="j.experience>10"; break;
							default:experienceStmt="1=1"; break;
							}
							
						}
						else{
							experienceStmt="1=1";
						}
					}
					catch(NullPointerException ex){
						
						experienceStmt="1=1";
					}
					*/
					
					
					String strQuery = "SELECT j.*  "
							+" FROM jobseekers j "+tableStmt+" where "+isEmployeeStmt+" and "+supervisorStmt+" and "+firstNameStmt+" and "+statusStmt+" order by j.firstname desc";
						System.out.println(strQuery);
						Statement stmt=(Statement) conn.createStatement();
						rs=stmt.executeQuery(strQuery);
					
					
					while(rs.next())
					{
						JobSeeker jobseeker = new JobSeeker();
						jobseeker.setId(rs.getInt("id"));
						jobseeker.setUsername(rs.getString("username"));
						jobseeker.setPassword(rs.getString("password"));
						jobseeker.setFirstname(rs.getString("firstName"));
						jobseeker.setLastname(rs.getString("lastName"));
						jobseeker.setAddress(rs.getString("address"));
						jobseeker.setEmail(rs.getString("email"));
						jobseeker.setPhone(rs.getString("phone"));
						jobseeker.setJob(rs.getString("job"));
						talentList.add(jobseeker);
					}				
					rs.close();
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				
		    }
	  	return talentList;
	  }
	    
  public List<Job> getAllJobsByEmployer(int eid,String status){
  	
  	List<Job> jobList = new ArrayList<Job>();
  	Connection conn = getConnection();	
	    if (conn != null) 
	    {
	    	ResultSet rs = null;
	    	//Statement stmt = null;
	    	PreparedStatement preparedStatement = null;
			try
			{	
				String strQuery;
				if (status==""){
					strQuery = 
						"SELECT j.* , e.companyname as companyname  "
						+" FROM job j , employers e where e.eid=j.eid and e.eid=? order by j.postdate desc";
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setInt(1, eid);
				}
				else
				{
					strQuery = 
							"SELECT j.* , e.companyname as companyname  "
							+" FROM job j , employers e where e.eid=j.eid and e.eid=? and j.status=? order by j.postdate desc";
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setInt(1, eid);
					preparedStatement.setString(2, status);
				}
				/*
				stmt = conn.createStatement();
				rs = stmt.executeQuery( strQuery);*/
				
				
				rs = preparedStatement.executeQuery();
				
				while(rs.next())
				{
					Job job = new Job();
					job.setTitle(rs.getString("title"));
					job.setCompanyTitle(rs.getString("companyname"));
					job.setJid(rs.getInt("jid"));
					job.setDescription(rs.getString("description"));
					job.setPostDate(rs.getTimestamp("postdate"));
					job.setStatus(rs.getString("status"));
					job.setSectorID(rs.getInt("sectorid"));
					job.setCountryID(rs.getInt("countryid"));
					job.setCityID(rs.getInt("cityid"));
					job.setExperience(rs.getInt("experience"));
					job.setSalary(rs.getInt("salary"));
					job.setCurrency(rs.getString("currency"));
					jobList.add(job);
				}				
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
	        	try 
	        	{ 
	        		rs.close();
	        		preparedStatement.close(); 
	        	}
	          	catch (SQLException e) {e.printStackTrace();}
	          	putConnection(conn);	        
	        }
	    }
  	return jobList;
  }
  
  public Job getJobData(int jobID){
  	
	Job job = new Job();
  	Connection conn = getConnection();	
	    if (conn != null) 
	    {
	    	ResultSet rs = null;
	    	PreparedStatement preparedStatement = null;
			try
			{	
				String strQuery= 
				"SELECT j.*, e.companyname as companyname "
				+" FROM job j, employers e where e.eid=j.eid and j.jid=?";
				
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setInt(1, jobID);
				
				rs = preparedStatement.executeQuery();
				
				while(rs.next())
				{
					
					job.setTitle(rs.getString("title"));
					job.setCompanyTitle(rs.getString("companyname"));
					job.setJid(rs.getInt("jid"));
					job.setDescription(rs.getString("description"));
					job.setPostDate(rs.getTimestamp("postdate"));
					job.setStatus(rs.getString("status"));
					job.setSectorID(rs.getInt("sectorid"));
					job.setJobSkills(getJobSkills(jobID));
					job.setCountryID(rs.getInt("countryid"));
					job.setCityID(rs.getInt("cityid"));
					job.setExperience(rs.getInt("experience"));
					job.setSalary(rs.getInt("salary"));
					job.setEid(rs.getInt("eid"));
					job.setCurrency(rs.getString("currency"));
				}				
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
	        	try 
	        	{ 
	        		rs.close();
	        		preparedStatement.close(); 
	        	}
	          	catch (SQLException e) {e.printStackTrace();}
	          	putConnection(conn);	        
	        }
	    }
  	return job;
  }
  
  
  
  public List<Skill> getJobSkills(int jobID)
 	{	
 		List<Skill> jobSkills= new ArrayList<Skill>();
 		Connection conn = getConnection();	
 	    if (conn != null) 
 	    {
 	    	ResultSet rs = null;
 	    	//Statement stmt = null;
 	    	PreparedStatement preparedStatement = null;
 			try
 			{
 				String strQuery = "SELECT C.* FROM jobskills B, skills C WHERE B.skillid=C.id and B.jobID=?";
 					
 				preparedStatement = conn.prepareStatement(strQuery);
 				preparedStatement.setInt(1,jobID);
 				rs = preparedStatement.executeQuery();
 				
 				while(rs.next())
 				{
 					Skill skill = new Skill();
 					skill.setSkillID(rs.getInt("id"));
 					skill.setName(rs.getString("name"));
 					jobSkills.add(skill);
 				}				
 			}//end of try
 			catch(SQLException ex){ex.printStackTrace();}
 			finally 
 			{
 	        	try 
 	        	{ 
 	        		rs.close();
 	        		preparedStatement.close(); 
 	        	}
 	          	catch (SQLException e) {e.printStackTrace();}
 	          	putConnection(conn);	        
 	        }
 	    }
 	    return jobSkills;
 	}
  
    public JobSeeker getUserData(String username, String password)
	{	
    	JobSeeker jobseeker = new JobSeeker();
		Connection conn = getConnection();	
	    if (conn != null) 
	    {
	    	ResultSet rs = null;
	    	//Statement stmt = null;
	    	PreparedStatement preparedStatement = null;
			try
			{
				String strQuery = 
					"SELECT u.*"
					+" FROM jobseekers u  WHERE username=? AND password=? ";
					
				/*
				stmt = conn.createStatement();
				rs = stmt.executeQuery( strQuery);*/
				preparedStatement = conn.prepareStatement(strQuery);
				preparedStatement.setString(1,username);
				preparedStatement.setString(2,password);
				rs = preparedStatement.executeQuery();
				
				while(rs.next())
				{
					jobseeker.setId(rs.getInt("id"));
					jobseeker.setUsername(rs.getString("username"));
					jobseeker.setPassword(rs.getString("password"));
					jobseeker.setFirstname(rs.getString("firstName"));
					jobseeker.setLastname(rs.getString("lastName"));
					jobseeker.setAddress(rs.getString("address"));
					jobseeker.setEmail(rs.getString("email"));
					jobseeker.setPhone(rs.getString("phone"));
					jobseeker.setJob(rs.getString("job"));
				}				
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			finally 
			{
	        	try 
	        	{ 
	        		rs.close();
	        		preparedStatement.close(); 
	        	}
	          	catch (SQLException e) {e.printStackTrace();}
	          	putConnection(conn);	        
	        }
	    }
	    return jobseeker;
	}	
    
    public Employee getEmployeeData(String username, String password)
  	{	
  		Employee employee = new Employee();
  		Connection conn = getConnection();	
  	    if (conn != null) 
  	    {
  	    	ResultSet rs = null;
  	    	//Statement stmt = null;
  	    	PreparedStatement preparedStatement = null;
  			try
  			{
  				String strQuery = 
  					"SELECT e.id as employeeid,e.jobseekerid,e.eid,e.hiringdate,e.status,e.supervisorid,e.companyEmail,e.salary,e.currency,u.*"
  					+" FROM jobseekers u,employees e  WHERE e.jobseekerid=u.id "
  					+"and e.id in (select A.max_id from (select jobseekerid,max(id) as max_id FROM EMPLOYEES where status in ('New Hired','Working') group by jobseekerid) A)"
  					+"AND u.username=? AND u.password=? ";
  					
  				/*
  				stmt = conn.createStatement();
  				rs = stmt.executeQuery( strQuery);*/
  				preparedStatement = conn.prepareStatement(strQuery);
  				preparedStatement.setString(1,username);
  				preparedStatement.setString(2,password);
  				rs = preparedStatement.executeQuery();
  				
  				while(rs.next())
  				{
  					employee.setId(rs.getInt("employeeid"));
  					employee.setUsername(rs.getString("username"));
  					employee.setPassword(rs.getString("password"));
  					employee.setFirstname(rs.getString("firstName"));
  					employee.setLastname(rs.getString("lastName"));
  					employee.setAddress(rs.getString("address"));
  					employee.setEmail(rs.getString("email"));
  					employee.setPhone(rs.getString("phone"));
  					employee.setJob(rs.getString("job"));
  					employee.setCompanyEmail(rs.getString("companyEmail"));
  					employee.setCurrency(rs.getString("currency"));
  					employee.setHiringDate(rs.getTimestamp("hiringDate"));
  					employee.setJobseekerId(rs.getInt("jobseekerid"));
  					employee.setSalary(rs.getInt("salary"));
  					employee.setStatus(rs.getString("status"));
  					employee.setSupervisorId(rs.getInt("supervisorid"));
  					employee.setEid(rs.getInt("eid"));
  					
  				}				
  			}//end of try
  			catch(SQLException ex){ex.printStackTrace();}
  			finally 
  			{
  	        	try 
  	        	{ 
  	        		rs.close();
  	        		preparedStatement.close(); 
  	        	}
  	          	catch (SQLException e) {e.printStackTrace();}
  	          	putConnection(conn);	        
  	        }
  	    }
  	    return employee;
  	}	
    
    public Employee getEmployeeData(int jobSeekerID)
  	{	
  		Employee employee = new Employee();
  		Connection conn = getConnection();	
  	    if (conn != null) 
  	    {
  	    	ResultSet rs = null;
  	    	//Statement stmt = null;
  	    	PreparedStatement preparedStatement = null;
  			try
  			{
  				String strQuery = 
  					"SELECT e.id as employeeid,e.jobseekerid,e.eid,e.hiringdate,e.status,e.supervisorid,e.companyEmail,e.salary,e.currency,u.*"
  					+" FROM jobseekers u,employees e  WHERE e.jobseekerid=u.id "
  					+"and e.id in (select A.max_id from (select eid,jobseekerid,max(id) as max_id FROM EMPLOYEES group by eid,jobseekerid) A)"
  					+"AND u.id=?";
  					
  				/*
  				stmt = conn.createStatement();
  				rs = stmt.executeQuery( strQuery);*/
  				preparedStatement = conn.prepareStatement(strQuery);
  				preparedStatement.setInt(1,jobSeekerID);
  				rs = preparedStatement.executeQuery();
  				
  				while(rs.next())
  				{
  					employee.setId(rs.getInt("employeeid"));
  					employee.setUsername(rs.getString("username"));
  					employee.setPassword(rs.getString("password"));
  					employee.setFirstname(rs.getString("firstName"));
  					employee.setLastname(rs.getString("lastName"));
  					employee.setAddress(rs.getString("address"));
  					employee.setEmail(rs.getString("email"));
  					employee.setPhone(rs.getString("phone"));
  					employee.setJob(rs.getString("job"));
  					employee.setCompanyEmail(rs.getString("companyEmail"));
  					employee.setCurrency(rs.getString("currency"));
  					employee.setHiringDate(rs.getTimestamp("hiringDate"));
  					employee.setJobseekerId(rs.getInt("jobseekerid"));
  					employee.setSalary(rs.getInt("salary"));
  					employee.setStatus(rs.getString("status"));
  					employee.setSupervisorId(rs.getInt("supervisorid"));
  					
  				}				
  			}//end of try
  			catch(SQLException ex){ex.printStackTrace();}
  			finally 
  			{
  	        	try 
  	        	{ 
  	        		rs.close();
  	        		preparedStatement.close(); 
  	        	}
  	          	catch (SQLException e) {e.printStackTrace();}
  	          	putConnection(conn);	        
  	        }
  	    }
  	    return employee;
  	}	
    
    public Employee getEmployeeDataByEmployeeId(int employeeID)
  	{	
  		Employee employee = new Employee();
  		Connection conn = getConnection();	
  	    if (conn != null) 
  	    {
  	    	ResultSet rs = null;
  	    	//Statement stmt = null;
  	    	PreparedStatement preparedStatement = null;
  			try
  			{
  				String strQuery = 
  					"SELECT e.id as employeeid,e.jobseekerid,e.eid,e.hiringdate,e.status,e.supervisorid,e.companyEmail,e.salary,e.currency,u.*"
  					+" FROM jobseekers u,employees e  WHERE e.jobseekerid=u.id "
  					+"and e.id in (select A.max_id from (select eid,jobseekerid,max(id) as max_id FROM EMPLOYEES group by eid,jobseekerid) A)"
  					+"AND e.id=?";
  					
  				/*
  				stmt = conn.createStatement();
  				rs = stmt.executeQuery( strQuery);*/
  				preparedStatement = conn.prepareStatement(strQuery);
  				preparedStatement.setInt(1,employeeID);
  				rs = preparedStatement.executeQuery();
  				
  				while(rs.next())
  				{
  					employee.setId(rs.getInt("employeeid"));
  					employee.setUsername(rs.getString("username"));
  					employee.setPassword(rs.getString("password"));
  					employee.setFirstname(rs.getString("firstName"));
  					employee.setLastname(rs.getString("lastName"));
  					employee.setAddress(rs.getString("address"));
  					employee.setEmail(rs.getString("email"));
  					employee.setPhone(rs.getString("phone"));
  					employee.setJob(rs.getString("job"));
  					employee.setCompanyEmail(rs.getString("companyEmail"));
  					employee.setCurrency(rs.getString("currency"));
  					employee.setHiringDate(rs.getTimestamp("hiringDate"));
  					employee.setJobseekerId(rs.getInt("jobseekerid"));
  					employee.setSalary(rs.getInt("salary"));
  					employee.setStatus(rs.getString("status"));
  					employee.setSupervisorId(rs.getInt("supervisorid"));
  					employee.setEid(rs.getInt("eid"));
  					
  				}				
  			}//end of try
  			catch(SQLException ex){ex.printStackTrace();}
  			finally 
  			{
  	        	try 
  	        	{ 
  	        		rs.close();
  	        		preparedStatement.close(); 
  	        	}
  	          	catch (SQLException e) {e.printStackTrace();}
  	          	putConnection(conn);	        
  	        }
  	    }
  	    return employee;
  	}	
    
    public List<Employee> getAllEmployeesByEmployerId(int eid)
  	{	
    	List<Employee> employeeList=new ArrayList<Employee>();
  		
  		Connection conn = getConnection();	
  	    if (conn != null) 
  	    {
  	    	ResultSet rs = null;
  	    	//Statement stmt = null;
  	    	PreparedStatement preparedStatement = null;
  			try
  			{
  				String strQuery = 
  					"SELECT e.id as employeeid,e.jobseekerid,e.eid,e.hiringdate,e.status,e.supervisorid,e.companyEmail,e.salary,e.currency,u.*"
  					+" FROM jobseekers u,employees e  WHERE e.jobseekerid=u.id "
  					+"and e.id in (select A.max_id from (select eid,jobseekerid,max(id) as max_id FROM EMPLOYEES group by eid,jobseekerid) A)"
  					+"AND e.eid=?";
  					
  				/*
  				stmt = conn.createStatement();
  				rs = stmt.executeQuery( strQuery);*/
  				preparedStatement = conn.prepareStatement(strQuery);
  				preparedStatement.setInt(1,eid);
  				rs = preparedStatement.executeQuery();
  				
  				while(rs.next())
  				{
  					Employee employee = new Employee();
  					employee.setId(rs.getInt("employeeid"));
  					employee.setUsername(rs.getString("username"));
  					employee.setPassword(rs.getString("password"));
  					employee.setFirstname(rs.getString("firstName"));
  					employee.setLastname(rs.getString("lastName"));
  					employee.setAddress(rs.getString("address"));
  					employee.setEmail(rs.getString("email"));
  					employee.setPhone(rs.getString("phone"));
  					employee.setJob(rs.getString("job"));
  					employee.setCompanyEmail(rs.getString("companyEmail"));
  					employee.setCurrency(rs.getString("currency"));
  					employee.setHiringDate(rs.getTimestamp("hiringDate"));
  					employee.setJobseekerId(rs.getInt("jobseekerid"));
  					employee.setSalary(rs.getInt("salary"));
  					employee.setStatus(rs.getString("status"));
  					employee.setSupervisorId(rs.getInt("supervisorid"));
  					employeeList.add(employee);
  				}				
  			}//end of try
  			catch(SQLException ex){ex.printStackTrace();}
  			finally 
  			{
  	        	try 
  	        	{ 
  	        		rs.close();
  	        		preparedStatement.close(); 
  	        	}
  	          	catch (SQLException e) {e.printStackTrace();}
  	          	putConnection(conn);	        
  	        }
  	    }
  	    return employeeList;
  	}	
    public Employer getEmployerData(String username, String password)
   	{	
   		Employer employer = new Employer();
   		Connection conn = getConnection();	
   	    if (conn != null) 
   	    {
   	    	ResultSet rs = null;
   	    	//Statement stmt = null;
   	    	PreparedStatement preparedStatement = null;
   			try
   			{
   				String strQuery = "SELECT *"
   					+" FROM employers WHERE username=? AND password=? ";
   					
   				preparedStatement = conn.prepareStatement(strQuery);
   				preparedStatement.setString(1,username);
   				preparedStatement.setString(2,password);
   				rs = preparedStatement.executeQuery();
   				
   				while(rs.next())
   				{
   					employer.setUsername(rs.getString("username"));
   					employer.setEmail(rs.getString("email"));
   					employer.setPhone(rs.getString("phone"));
   					employer.setAddress(rs.getString("address"));
   					employer.setDescription(rs.getString("description"));
   					employer.setCompanyName(rs.getString("companyname"));
   					employer.setPassword(rs.getString("password"));
   					employer.setEid(rs.getInt("eid"));
   					
   					if(rs.getString("isactive").equals("Y")){
   						employer.setActive(true);
   					}
   					else{
   						employer.setActive(false);
   					}
   					employer.setCreatedDate(rs.getTimestamp("createddate"));
   					employer.setActivationDate(rs.getTimestamp("activationdate"));
   				}				
   			}//end of try
   			catch(SQLException ex){ex.printStackTrace();}
   			finally 
   			{
   	        	try 
   	        	{ 
   	        		rs.close();
   	        		preparedStatement.close(); 
   	        	}
   	          	catch (SQLException e) {e.printStackTrace();}
   	          	putConnection(conn);	        
   	        }
   	    }
   	    return employer;
   	}	
		
    public Employer getEmployerData(int eid)
   	{	
   		Employer employer = new Employer();
   		Connection conn = getConnection();	
   	    if (conn != null) 
   	    {
   	    	ResultSet rs = null;
   	    	//Statement stmt = null;
   	    	PreparedStatement preparedStatement = null;
   			try
   			{
   				String strQuery = "SELECT *"
   					+" FROM employers WHERE eid=? ";
   					
   				preparedStatement = conn.prepareStatement(strQuery);
   				preparedStatement.setInt(1,eid);
   				
   				rs = preparedStatement.executeQuery();
   				
   				while(rs.next())
   				{
   					employer.setUsername(rs.getString("username"));
   					employer.setEmail(rs.getString("email"));
   					employer.setPhone(rs.getString("phone"));
   					employer.setAddress(rs.getString("address"));
   					employer.setDescription(rs.getString("description"));
   					employer.setCompanyName(rs.getString("companyname"));
   					employer.setPassword(rs.getString("password"));
   					employer.setEid(rs.getInt("eid"));
   					if(rs.getString("isactive").equals("Y")){
   						employer.setActive(true);
   					}
   					else{
   						employer.setActive(false);
   					}
   					employer.setCreatedDate(rs.getTimestamp("createddate"));
   					employer.setActivationDate(rs.getTimestamp("activationdate"));
   				}				
   			}//end of try
   			catch(SQLException ex){ex.printStackTrace();}
   			finally 
   			{
   	        	try 
   	        	{ 
   	        		rs.close();
   	        		preparedStatement.close(); 
   	        	}
   	          	catch (SQLException e) {e.printStackTrace();}
   	          	putConnection(conn);	        
   	        }
   	    }
   	    return employer;
   	}	
    
    public List<Employer> queryEmployers(boolean isActive,String companyName,String userName){
	  	
  	  List<Employer> employerList = new ArrayList<Employer>();
  	  	Connection conn = getConnection();	
  		    if (conn != null) 
  		    {
  		    	ResultSet rs = null;
  		    	//Statement stmt = null;
  		    	PreparedStatement preparedStatement = null;
  				try
  				{	
  					
  					
  					
  					String isActiveStmt="";
  					try{		
  							
  							if(isActive){
  								isActiveStmt="e.isactive='Y'";
  							}
  							else{
  								isActiveStmt="e.isactive='N'";
  							}
  						
  					}
  					catch(NullPointerException ex){
  						
  						isActiveStmt="1=1";
  					}
  					
  					
  					String companyNameStmt="";
  					try{
  							if(companyName!=""){
  								companyNameStmt="e.companyname like '%"+companyName+"%'";
  							}
  							else{
  								companyNameStmt="1=1";
  							}
  					}
  					
  					catch(NullPointerException ex){
  						
  						companyNameStmt="1=1";
  					}
  					
  					String userNameStmt="";
  					try{
  							if(userName!=""){
  								userNameStmt="e.username like '%"+userName+"%'";
  							}
  							else{
  								userNameStmt="1=1";
  							}
  					}
  					
  					catch(NullPointerException ex){
  						
  						userNameStmt="1=1";
  					}
  					
  					
  					
  					String strQuery = "SELECT e.*  "
  							+" FROM employers e where "+isActiveStmt+" and "+companyNameStmt+" and "+userNameStmt+" order by e.createddate asc";
  						System.out.println(strQuery);
  						Statement stmt=(Statement) conn.createStatement();
  						rs=stmt.executeQuery(strQuery);
  					
  					
  					while(rs.next())
  					{
  						Employer employer = new Employer();
  						employer.setUsername(rs.getString("username"));
  	   					employer.setEmail(rs.getString("email"));
  	   					employer.setPhone(rs.getString("phone"));
  	   					employer.setAddress(rs.getString("address"));
  	   					employer.setDescription(rs.getString("description"));
  	   					employer.setCompanyName(rs.getString("companyname"));
  	   					employer.setPassword(rs.getString("password"));
  	   					employer.setEid(rs.getInt("eid"));
  	   					if(rs.getString("isactive").equals("Y")){
  	   						employer.setActive(true);
  	   					}
  	   					else{
  	   						employer.setActive(false);
  	   					}
  	   					employer.setCreatedDate(rs.getTimestamp("createddate"));
  	   					employer.setActivationDate(rs.getTimestamp("activationdate"));
  						employerList.add(employer);
  					}				
  					rs.close();
  				}//end of try
  				catch(SQLException ex){ex.printStackTrace();}
  				
  		    }
  	  	return employerList;
  	  }
    
    public List<NameValuePair> getAllEmployerMonthlyPosts(int year,int month){
	  	
    	  //List<Employer> employerList = new ArrayList<Employer>();
    	  List<NameValuePair> params = new ArrayList<NameValuePair>();
    	  	Connection conn = getConnection();
    	  	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	  	java.util.Date startDate = new java.util.Date();
			try {
				startDate = sdf.parse(year+"-"+month+"-"+1+" 00:00:00");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
    	  	Calendar calendar = Calendar.getInstance();  
		    calendar.setTime(startDate);  

		    calendar.add(Calendar.MONTH, 1);  
		    calendar.set(Calendar.DAY_OF_MONTH, 1);  
		    calendar.add(Calendar.DATE, -1);  

		    java.util.Date endDate = calendar.getTime();
    	  	
    		    if (conn != null) 
    		    {
    		    	ResultSet rs = null;
    		    	//Statement stmt = null;
    		    	PreparedStatement preparedStatement = null;
    				try
    				{	
    					
    					String strQuery = "SELECT e.eid,e.companyname,count(j.jid) as jobno  "
    							+" FROM employers e,job j where e.eid=j.jid and j.postdate between ? and ? group by e.eid,e.companyname order by e.companyname asc";
    						
    						preparedStatement = conn.prepareStatement(strQuery);
    						preparedStatement.setString(1,sdf.format(startDate));
    						preparedStatement.setString(2,sdf.format(endDate));
    						System.out.println(preparedStatement.toString());
    						rs = preparedStatement.executeQuery();;
    					
    					
    					while(rs.next())
    					{
    						
    						params.add(new BasicNameValuePair(rs.getString("companyname"),""+rs.getInt("jobno")));
    						
    					}				
    					rs.close();
    				}//end of try
    				catch(SQLException ex){ex.printStackTrace();}
    				
    		    }
    	  	return params;
    	  }
    
    
    public List<Integer> getMatchingJobSalaryList(int jobID){
	  	
  	  //List<Employer> employerList = new ArrayList<Employer>();
  	  List<Integer> salaries = new ArrayList<Integer>();
  	  	Connection conn = getConnection();
  	  	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  	  	java.util.Date reportingDate = new java.util.Date();
			
			
			
  	  	Calendar calendar = Calendar.getInstance();  
		    calendar.setTime(reportingDate);    
		    calendar.add(Calendar.YEAR, -1);  

		    java.util.Date startDate = calendar.getTime();
  	  	
  		    if (conn != null) 
  		    {
  		    	ResultSet rs = null;
  		    	//Statement stmt = null;
  		    	PreparedStatement preparedStatement = null;
  				try
  				{	
  					
  					String strQuery = "SELECT distinct e.id,e.salary FROM JOB j1,jobskills js1,JOB j2,jobskills js2,employees e,jobapplication ja   "
  							+" where j1.jid=? and e.hiringdate between ? and ? and j1.jid=js1.jobid and j2.jid=js2.jobid and j1.jid<>j2.jid and j1.sectorid=j2.sectorid and j1.countryid=j2.countryid and j1.experience=j2.experience and js1.skillid=js2.skillid and ja.status='Hired' and ja.jobid=j2.jid and ja.personId=e.jobseekerid and e.salary<>0";
  						
  						preparedStatement = conn.prepareStatement(strQuery);
  						preparedStatement.setInt(1, jobID);
  						preparedStatement.setString(2,sdf.format(startDate));
						preparedStatement.setString(3,sdf.format(reportingDate));
  						
  						System.out.println(preparedStatement.toString());
  						rs = preparedStatement.executeQuery();;
  					
  					
  					while(rs.next())
  					{
  						
  						salaries.add(new Integer(rs.getInt("salary")));
  						
  					}				
  					rs.close();
  				}//end of try
  				catch(SQLException ex){ex.printStackTrace();}
  				
  		    }
  	  	return salaries;
  	  }
    public Skill getSkillData(int skillId)
   	{	
   		Skill skill = new Skill();
   		Connection conn = getConnection();	
   	    if (conn != null) 
   	    {
   	    	ResultSet rs = null;
   	    	//Statement stmt = null;
   	    	PreparedStatement preparedStatement = null;
   			try
   			{
   				String strQuery = "SELECT id , name"
   					+" FROM skills WHERE id=?";
   					
   				preparedStatement = conn.prepareStatement(strQuery);
   				preparedStatement.setInt(1,skillId);
   				rs = preparedStatement.executeQuery();
   				
   				while(rs.next())
   				{
   					skill.setSkillID(rs.getInt("id"));
   					skill.setName(rs.getString("name"));
   				}				
   			}//end of try
   			catch(SQLException ex){ex.printStackTrace();}
   			finally 
   			{
   	        	try 
   	        	{ 
   	        		rs.close();
   	        		preparedStatement.close(); 
   	        	}
   	          	catch (SQLException e) {e.printStackTrace();}
   	          	putConnection(conn);	        
   	        }
   	    }
   	    return skill;
   	}	
    
    public Interview getInterviewData(int interviewId)
   	{	
    	Interview interview = new Interview();
   		Connection conn = getConnection();	
   	    if (conn != null) 
   	    {
   	    	ResultSet rs = null;
   	    	//Statement stmt = null;
   	    	PreparedStatement preparedStatement = null;
   			try
   			{
   				String strQuery = "SELECT *"
   					+" FROM interviews WHERE id=?";
   					
   				preparedStatement = conn.prepareStatement(strQuery);
   				preparedStatement.setInt(1,interviewId);
   				rs = preparedStatement.executeQuery();
   				
   				while(rs.next())
   				{
   					interview.setId(rs.getInt("id"));
					interview.setApplicationId(rs.getInt("applicationid"));
					interview.setJobId(rs.getInt("jobid"));
					interview.setStatus(rs.getString("status"));
					//interview.setDate(new java.util.Date(rs.getDate("date").getTime()));
					interview.setDate(rs.getTimestamp("date"));
					interview.setInterviewer(rs.getString("interviewer"));
					interview.setContactEmail(rs.getString("email"));
					interview.setLocation(rs.getString("location"));
					interview.setSequenceNo(rs.getInt("sequenceno"));
					interview.setNotes(rs.getString("notes"));
					interview.setCurrency(rs.getString("currency"));
					interview.setResultCode(rs.getString("resultcode"));
					interview.setSalary(rs.getInt("salary"));
   				}				
   			}//end of try
   			catch(SQLException ex){ex.printStackTrace();}
   			finally 
   			{
   	        	try 
   	        	{ 
   	        		rs.close();
   	        		preparedStatement.close(); 
   	        	}
   	          	catch (SQLException e) {e.printStackTrace();}
   	          	putConnection(conn);	        
   	        }
   	    }
   	    return interview;
   	}	
    
    public List<Skill> getJobSeekerSkills(String userName)
   	{	
   		List<Skill> userSkills= new ArrayList<Skill>();
   		Connection conn = getConnection();	
   	    if (conn != null) 
   	    {
   	    	ResultSet rs = null;
   	    	//Statement stmt = null;
   	    	PreparedStatement preparedStatement = null;
   			try
   			{
   				String strQuery = "SELECT C.id , C.name"
   					+" FROM jobseekers A, jobseekerskills B, skills C WHERE A.id=B.jobseekerid AND B.skillid=C.id and A.username=?";
   					
   				preparedStatement = conn.prepareStatement(strQuery);
   				preparedStatement.setString(1,userName);
   				rs = preparedStatement.executeQuery();
   				
   				while(rs.next())
   				{
   					Skill skill = new Skill();
   					skill.setSkillID(rs.getInt("id"));
   					skill.setName(rs.getString("name"));
   					userSkills.add(skill);
   				}				
   			}//end of try
   			catch(SQLException ex){ex.printStackTrace();}
   			finally 
   			{
   	        	try 
   	        	{ 
   	        		rs.close();
   	        		preparedStatement.close(); 
   	        	}
   	          	catch (SQLException e) {e.printStackTrace();}
   	          	putConnection(conn);	        
   	        }
   	    }
   	    return userSkills;
   	}	
    
    
    public JobSeeker getJobSeeker(int userID)
   	{	
    	JobSeeker jobSeeker = new JobSeeker();
   		Connection conn = getConnection();	
   	    if (conn != null) 
   	    {
   	    	ResultSet rs = null;
   	    	//Statement stmt = null;
   	    	PreparedStatement preparedStatement = null;
   			try
   			{
   				String strQuery = "SELECT * FROM jobseekers WHERE id=?";
   					
   				preparedStatement = conn.prepareStatement(strQuery);
   				preparedStatement.setInt(1,userID);
   				rs = preparedStatement.executeQuery();
   				
   				while(rs.next())
   				{
   					jobSeeker.setId(rs.getInt("id"));
   					jobSeeker.setUsername(rs.getString("username"));
   					jobSeeker.setFirstname(rs.getString("firstname"));
   					jobSeeker.setLastname(rs.getString("lastname"));
   					jobSeeker.setEmail(rs.getString("email"));
   					jobSeeker.setPhone(rs.getString("phone"));
   					jobSeeker.setAddress(rs.getString("address"));
   					jobSeeker.setJob(rs.getString("job"));
   					jobSeeker.setPassword(rs.getString("password"));
   				}				
   			}//end of try
   			catch(SQLException ex){ex.printStackTrace();}
   			finally 
   			{
   	        	try 
   	        	{ 
   	        		rs.close();
   	        		preparedStatement.close(); 
   	        	}
   	          	catch (SQLException e) {e.printStackTrace();}
   	          	putConnection(conn);	        
   	        }
   	    }
   	    return jobSeeker;
   	}	
    
    public JobSeeker getJobSeekerByApplication(int applicationID)
   	{	
    	JobSeeker jobSeeker = new JobSeeker();
   		Connection conn = getConnection();	
   	    if (conn != null) 
   	    {
   	    	ResultSet rs = null;
   	    	//Statement stmt = null;
   	    	PreparedStatement preparedStatement = null;
   			try
   			{
   				String strQuery = "SELECT j.* FROM jobseekers j, jobapplication a WHERE a.personId=j.id and a.id=?";
   					
   				preparedStatement = conn.prepareStatement(strQuery);
   				preparedStatement.setInt(1,applicationID);
   				rs = preparedStatement.executeQuery();
   				
   				while(rs.next())
   				{
   					jobSeeker.setId(rs.getInt("id"));
   					jobSeeker.setUsername(rs.getString("username"));
   					jobSeeker.setFirstname(rs.getString("firstname"));
   					jobSeeker.setLastname(rs.getString("lastname"));
   					jobSeeker.setEmail(rs.getString("email"));
   					jobSeeker.setPhone(rs.getString("phone"));
   					jobSeeker.setAddress(rs.getString("address"));
   					jobSeeker.setJob(rs.getString("job"));
   					jobSeeker.setPassword(rs.getString("password"));
   				}				
   			}//end of try
   			catch(SQLException ex){ex.printStackTrace();}
   			finally 
   			{
   	        	try 
   	        	{ 
   	        		rs.close();
   	        		preparedStatement.close(); 
   	        	}
   	          	catch (SQLException e) {e.printStackTrace();}
   	          	putConnection(conn);	        
   	        }
   	    }
   	    return jobSeeker;
   	}	

	 public List<Skill> getAllSkills(){
	    	
	    	List<Skill> skillList = new ArrayList<Skill>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{
					String strQuery = "SELECT * from skills ORDER BY NAME ASC";
						
					/*
					stmt = conn.createStatement();
					rs = stmt.executeQuery( strQuery);*/
					preparedStatement = conn.prepareStatement(strQuery);
				
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						Skill skill = new Skill();
						skill.setSkillID(rs.getInt("id"));
						skill.setName(rs.getString("name"));
						skillList.add(skill);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
	    return skillList;
	   }
	 

	 public List<Skill> querySkills(String name){
	    	
	    	List<Skill> skillList = new ArrayList<Skill>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
		    	String strQuery="";
		    	try
				{
					if(!name.equals("") && name!=null){
					strQuery = "SELECT * from skills where name like ? ORDER BY NAME ASC";
				
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setString(1,name);
					}
					else{
						strQuery = "SELECT * from skills ORDER BY NAME ASC";
						
						preparedStatement = conn.prepareStatement(strQuery);
					}
					
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						Skill skill = new Skill();
						skill.setSkillID(rs.getInt("id"));
						skill.setName(rs.getString("name"));
						skillList.add(skill);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
		    System.out.println("Skilllist size:"+skillList.size());
	    return skillList;
	   }
	 
	 public List<BasicNameValuePair> queryAllValues(String name,String type){
	    	
		 List<BasicNameValuePair> valueList = new ArrayList<BasicNameValuePair>();
	    	if(type.contentEquals("") || type.contentEquals("skill")){
	    	List<Skill> skillList = querySkills(name);
	    	for(Skill skill:skillList)
	    		valueList.add(new BasicNameValuePair("skill-"+skill.getSkillID(),skill.getName()));
	    	}
	    	if(type.contentEquals("") || type.contentEquals("sector")){
	    	List<Sector> sectorList = querySectors(name);
	    	for(Sector sector:sectorList)
	    		valueList.add(new BasicNameValuePair("sector-"+sector.getId(),sector.getName()));
	    	}
	    	if(type.contentEquals("") || type.contentEquals("country")){
	    	List<Country> countryList = queryCountries(name);
	    	for(Country country:countryList)
	    		valueList.add(new BasicNameValuePair("country-"+country.getId(),country.getName()+"-"+country.getCurrency()));
	    	}
	    	if(type.contentEquals("") || type.contentEquals("city")){
	    	List<City> cityList = queryCities(name);
	    	for(City city:cityList)
	    		valueList.add(new BasicNameValuePair("city-"+city.getId(),city.getName()+"-"+city.getCountryId()));
	    	}
	    	System.out.println("Value list size"+valueList.size());
	    	
	    return valueList;
	   }
	 
	 public boolean addSkillValue(String name){
		 	
		 	Connection conn = getConnection();
		 	boolean result = false;
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "insert into skills "
		 				+ "  (name)"
		 				+ "  values(?)";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setString(1,name);

		 		preparedStatement.executeUpdate();
		 		preparedStatement.close(); 
		 		result = true;
		 		}
					catch(SQLException ex){ex.printStackTrace();
					result = false;}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	return result;
	}
	 
	 public boolean updateSkillValue(int id,String name){
		 	
		 	Connection conn = getConnection();
		 	boolean result = false;
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "update skills "
		 				+ " set name=? "
		 				+ " where id=?";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setString(1,name);
		 		preparedStatement.setInt(2,id);
		 		
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close(); 
		 		result = true;
		 		}
					catch(SQLException ex){ex.printStackTrace();
					result = false;
					}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	return result;
	}
	 public boolean addSectorValue(String name){
		 	
		 	Connection conn = getConnection();
		 	boolean result = false;
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "insert into sectors "
		 				+ "  (name)"
		 				+ "  values(?)";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setString(1,name);

		 		preparedStatement.executeUpdate();
		 		preparedStatement.close(); 
		 		result = true;
		 		}
					catch(SQLException ex){ex.printStackTrace();
					result = false;
					}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	return result;
	}
	 
	 public boolean updateSectorValue(int id,String name){
		 	
		 	Connection conn = getConnection();
		 	boolean result = false;
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "update sectors "
		 				+ " set name=? "
		 				+ " where id=?";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setString(1,name);
		 		preparedStatement.setInt(2,id);
		 		
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close(); 
		 		result = true;
		 		}
					catch(SQLException ex){ex.printStackTrace();
					result = false;
					}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	return result;
	}
	 public boolean addCityValue(String name,int countryID){
		 	
		 	Connection conn = getConnection();
		 	boolean result = false;
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "insert into city "
		 				+ "  (name,country_id)"
		 				+ "  values(?,?)";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setString(1,name);
		 		preparedStatement.setInt(2,countryID);
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close();
		 		result = true;
		 		}
					catch(SQLException ex){ex.printStackTrace();
					result = false;
					}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	return result;
	}
	 
	 public boolean updateCityValue(int id,String name,int countryID){
		 	
		 	Connection conn = getConnection();
		 	boolean result = false;
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "update city "
		 				+ "  set name=?,country_id=?"
		 				+ "  where id=?";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setString(1,name);
		 		preparedStatement.setInt(2,countryID);
		 		preparedStatement.setInt(3,id);
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close();
		 		result = true;
		 		}
					catch(SQLException ex){ex.printStackTrace();
					result = false;
					}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	return result;
	}
	 public boolean addCountryValue(String name,String currency){
		 	
		 	Connection conn = getConnection();
		 	boolean result = false;
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "insert into country "
		 				+ "  (name,currency)"
		 				+ "  values(?,?)";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setString(1,name);
		 		preparedStatement.setString(2,currency);
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close();
		 		result = true;
		 		}
					catch(SQLException ex){ex.printStackTrace();
					result = false;
					}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	return result;
	}
	 
	 public boolean updateCountryValue(int id,String name,String currency){
		 	
		 	Connection conn = getConnection();
		 	boolean result = false;
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "update country "
		 				+ "  set name=?,currency=?"
		 				+ "  where id=?";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setString(1,name);
		 		preparedStatement.setString(2,currency);
		 		preparedStatement.setInt(3,id);
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close();
		 		result = true;
		 		}
					catch(SQLException ex){ex.printStackTrace();
					result = false;
					}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	return result;
	}
	 
	 public JobApplication getJobApplication(int jobseekerID, int jobID)
	   	{	
		 JobApplication jobApplication = new JobApplication();
	   		Connection conn = getConnection();	
	   	    if (conn != null) 
	   	    {
	   	    	ResultSet rs = null;
	   	    	//Statement stmt = null;
	   	    	PreparedStatement preparedStatement = null;
	   			try
	   			{
	   				String strQuery = "SELECT * FROM jobapplication WHERE personId= ? and jobid= ?";
	   					
	   				preparedStatement = conn.prepareStatement(strQuery);
	   				preparedStatement.setInt(1,jobseekerID);
	   				preparedStatement.setInt(2,jobID);
	   				rs = preparedStatement.executeQuery();
	   				
	   				while(rs.next())
	   				{
	   					jobApplication.setId(rs.getInt("id"));
	   					jobApplication.setJobID(rs.getInt("jobid"));
	   					jobApplication.setJobSeekerID(rs.getInt("personId"));
	   					jobApplication.setApplicationStatus(rs.getString("status"));
	   					jobApplication.setApplicationDate(rs.getTimestamp("applicationdate"));
	   				}				
	   			}//end of try
	   			catch(SQLException ex){ex.printStackTrace();}
	   			finally 
	   			{
	   	        	try 
	   	        	{ 
	   	        		rs.close();
	   	        		preparedStatement.close(); 
	   	        	}
	   	          	catch (SQLException e) {e.printStackTrace();}
	   	          	putConnection(conn);	        
	   	        }
	   	    }
	   	    return jobApplication;
	   	}	
	 
	 public JobApplication getJobApplication(int applicationID)
	   	{	
		 JobApplication jobApplication = new JobApplication();
	   		Connection conn = getConnection();	
	   	    if (conn != null) 
	   	    {
	   	    	ResultSet rs = null;
	   	    	//Statement stmt = null;
	   	    	PreparedStatement preparedStatement = null;
	   			try
	   			{
	   				String strQuery = "SELECT * FROM jobapplication WHERE id= ?";
	   					
	   				preparedStatement = conn.prepareStatement(strQuery);
	   				preparedStatement.setInt(1,applicationID);
	   				
	   				rs = preparedStatement.executeQuery();
	   				
	   				while(rs.next())
	   				{
	   					jobApplication.setId(rs.getInt("id"));
	   					jobApplication.setJobID(rs.getInt("jobid"));
	   					jobApplication.setJobSeekerID(rs.getInt("personId"));
	   					jobApplication.setApplicationStatus(rs.getString("status"));
	   					jobApplication.setApplicationDate(rs.getTimestamp("applicationdate"));
	   				}				
	   			}//end of try
	   			catch(SQLException ex){ex.printStackTrace();}
	   			finally 
	   			{
	   	        	try 
	   	        	{ 
	   	        		rs.close();
	   	        		preparedStatement.close(); 
	   	        	}
	   	          	catch (SQLException e) {e.printStackTrace();}
	   	          	putConnection(conn);	        
	   	        }
	   	    }
	   	    return jobApplication;
	   	}	
	 
	 public List<JobApplication> getJobApplications(int jobID)
	   	{	
		 List<JobApplication> jobApplications = new ArrayList<JobApplication>();
	   		Connection conn = getConnection();	
	   	    if (conn != null) 
	   	    {
	   	    	ResultSet rs = null;
	   	    	//Statement stmt = null;
	   	    	PreparedStatement preparedStatement = null;
	   			try
	   			{
	   				String strQuery = "SELECT * FROM jobapplication WHERE jobid= ?";
	   					
	   				preparedStatement = conn.prepareStatement(strQuery);
	   				preparedStatement.setInt(1,jobID);
	   				rs = preparedStatement.executeQuery();
	   				
	   				while(rs.next())
	   				{
	   					JobApplication jobApplication=new JobApplication();
	   					jobApplication.setId(rs.getInt("id"));
	   					jobApplication.setJobID(rs.getInt("jobid"));
	   					jobApplication.setJobSeekerID(rs.getInt("personId"));
	   					jobApplication.setApplicationStatus(rs.getString("status"));
	   					jobApplication.setApplicationDate(rs.getTimestamp("applicationdate"));
	   					jobApplications.add(jobApplication);
	   				}				
	   			}//end of try
	   			catch(SQLException ex){ex.printStackTrace();}
	   			finally 
	   			{
	   	        	try 
	   	        	{ 
	   	        		rs.close();
	   	        		preparedStatement.close(); 
	   	        	}
	   	          	catch (SQLException e) {e.printStackTrace();}
	   	          	putConnection(conn);	        
	   	        }
	   	    }
	   	    return jobApplications;
	   	}
	 
	 
	 public List<JobApplication> getJobApplicationsByMatchingSkills(int jobID)
	   	{	
		 List<JobApplication> jobApplications = new ArrayList<JobApplication>();
	   		Connection conn = getConnection();	
	   	    if (conn != null) 
	   	    {
	   	    	ResultSet rs = null;
	   	    	//Statement stmt = null;
	   	    	PreparedStatement preparedStatement = null;
	   			try
	   			{
	   				String strQuery = "SELECT  A.id,A.personId,A.jobid,A.status,A.applicationdate,SUM((CASE WHEN A.skillid!=0 THEN 1 ELSE 0 END)) as matchingskillcount from"+
	   						" (SELECT distinct A.id,A.personId,A.jobid,A.status,A.applicationdate,IFNULL(e.skillid,0) AS skillid  from"+
	   						" jobapplication A INNER JOIN jobseekers B ON A.personId=B.id"+ 
	   						" INNER JOIN job C ON A.jobid=C.jid"+
	   						" LEFT OUTER JOIN  jobseekerskills D ON B.id=D.jobseekerid"+
							" LEFT OUTER JOIN  jobskills E ON  E.jobid=C.jid AND D.skillid=E.skillid WHERE C.jid=?) A"+
							" GROUP BY A.id,A.personId,A.jobid,A.status,A.applicationdate"+
							" order by matchingskillcount desc,A.applicationdate desc";
	   					
	   				preparedStatement = conn.prepareStatement(strQuery);
	   				preparedStatement.setInt(1,jobID);
	   				rs = preparedStatement.executeQuery();
	   				
	   				while(rs.next())
	   				{
	   					JobApplication jobApplication=new JobApplication();
	   					jobApplication.setId(rs.getInt("id"));
	   					jobApplication.setJobID(rs.getInt("jobid"));
	   					jobApplication.setJobSeekerID(rs.getInt("personId"));
	   					jobApplication.setApplicationStatus(rs.getString("status"));
	   					jobApplication.setApplicationDate(rs.getTimestamp("applicationdate"));
	   					jobApplication.setMatchingSkillCount(rs.getInt("matchingskillcount"));
	   					jobApplications.add(jobApplication);
	   				}				
	   			}//end of try
	   			catch(SQLException ex){ex.printStackTrace();}
	   			finally 
	   			{
	   	        	try 
	   	        	{ 
	   	        		rs.close();
	   	        		preparedStatement.close(); 
	   	        	}
	   	          	catch (SQLException e) {e.printStackTrace();}
	   	          	putConnection(conn);	        
	   	        }
	   	    }
	   	    return jobApplications;
	   	}
	 
	 public List<JobApplication> queryJobApplications(int eid,String title,String firstName,String lastName,String status,String appStatus,String experience){
		  	
		  	List<JobApplication> jobApplicationList = new ArrayList<JobApplication>();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	//Statement stmt = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						
						String titleStmt="";
						try{
								if(title!=""){
									titleStmt="c.title ='"+title+"'";
								}
								else{
									titleStmt="1=1";
								}
						}
						catch(NullPointerException ex){
							
							titleStmt="1=1";
						}
						
						String firstNameStmt="";
						try{
								if(firstName!=""){
									firstNameStmt="b.firstname like '%"+firstName+"%'";
								}
								else{
									firstNameStmt="1=1";
								}
						}
						catch(NullPointerException ex){
							
							firstNameStmt="1=1";
						}
						
						String lastNameStmt="";
						try{
								if(lastName!=""){
									lastNameStmt="b.lastname like '%"+lastName+"%'";
								}
								else{
									lastNameStmt="1=1";
								}
						}
						catch(NullPointerException ex){
							
							lastNameStmt="1=1";
						}
					
						
						String statusStmt="";
						try{		
								if(status!=""){
									statusStmt="c.status='"+status+"'";
								}
								else{
									statusStmt="1=1";
								}
						}
						catch(NullPointerException ex){
							
							statusStmt="1=1";
						}
						
						
						
						String appStatusStmt="";
						try{		
								if(appStatus!=""){
	
									appStatusStmt="a.status='"+appStatus+"'";
								}
								else{
									appStatusStmt="1=1";
									
								}
						}
						catch(NullPointerException ex){
							
							appStatusStmt="1=1";
						}
						
						
						
						String experienceStmt="";
						String col="";
						try{
							if(experience!=""){
								experienceStmt=" having ";
								String sum="sum(CAST(E.years AS UNSIGNED))";
								switch(experience){
								case("0"): experienceStmt+=sum+"=0"; break;
								case("0-1"): experienceStmt+=sum+">0 and "+sum+"<=1"; break;
								case("1-3"): experienceStmt+=sum+">1 and "+sum+"<=3"; break;
								case("3-5"): experienceStmt+=sum+">3 and "+sum+"<=5"; break;
								case("5-10"): experienceStmt+=sum+">5 and "+sum+"<=10"; break;
								case("10+"): experienceStmt+=sum+">10"; break;
								default:experienceStmt=""; break;
								}
								col=","+sum;
							}
							else{
								experienceStmt="";
							}
						}
						catch(NullPointerException ex){
							
							experienceStmt="";
						}
						
						
						
						String strQuery = 
		   				" SELECT distinct A.id,A.personId,A.jobid,A.status,A.applicationdate from"+
   						" jobapplication A INNER JOIN jobseekers B ON A.personId=B.id"+
   						" INNER JOIN job C ON A.jobid=C.jid"+
   						" LEFT OUTER JOIN  experience E ON E.personid=B.id"+
   						" WHERE C.eid="+eid+" AND "+appStatusStmt+" AND "+statusStmt+" AND "+firstNameStmt+" AND "+lastNameStmt+" AND "+titleStmt
   						+" GROUP BY A.id,A.personId,A.jobid,A.status,A.applicationdate"+experienceStmt
   						+" ORDER BY A.applicationdate DESC" ;
						
							Statement stmt=(Statement) conn.createStatement();
							rs=stmt.executeQuery(strQuery);
						
						
						while(rs.next())
						{
							JobApplication jobApplication = new JobApplication();
							jobApplication.setId(rs.getInt("id"));
		   					jobApplication.setJobID(rs.getInt("jobid"));
		   					jobApplication.setJobSeekerID(rs.getInt("personId"));
		   					jobApplication.setApplicationStatus(rs.getString("status"));
		   					jobApplication.setApplicationDate(rs.getTimestamp("applicationdate"));
							jobApplicationList.add(jobApplication);
						}				
						rs.close();
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
					
			    }
		  	return jobApplicationList;
		  }
		    
	 public Sector getSectorData(int sectorID)
	   	{	
	   		Sector sector = new Sector();
	   		Connection conn = getConnection();	
	   	    if (conn != null) 
	   	    {
	   	    	ResultSet rs = null;
	   	    	//Statement stmt = null;
	   	    	PreparedStatement preparedStatement = null;
	   			try
	   			{
	   				String strQuery = "SELECT id , name"
	   					+" FROM sectors WHERE id=?";
	   					
	   				preparedStatement = conn.prepareStatement(strQuery);
	   				preparedStatement.setInt(1,sectorID);
	   				rs = preparedStatement.executeQuery();
	   				
	   				while(rs.next())
	   				{
	   					sector.setId(rs.getInt("id"));
	   					sector.setName(rs.getString("name"));
	   				}				
	   			}//end of try
	   			catch(SQLException ex){ex.printStackTrace();}
	   			finally 
	   			{
	   	        	try 
	   	        	{ 
	   	        		rs.close();
	   	        		preparedStatement.close(); 
	   	        	}
	   	          	catch (SQLException e) {e.printStackTrace();}
	   	          	putConnection(conn);	        
	   	        }
	   	    }
	   	    return sector;
	   	}	
	 
	
	 
	 public List<Sector> getAllSectors(){
	    	
	    	List<Sector> sectorList = new ArrayList<Sector>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{
					String strQuery = "SELECT * from sectors ORDER BY NAME ASC";
				
					preparedStatement = conn.prepareStatement(strQuery);
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						Sector sector = new Sector();
						sector.setId(rs.getInt("id"));
						sector.setName(rs.getString("name"));
						sectorList.add(sector);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
	    return sectorList;
	   }
	 public List<Sector> querySectors(String name){
	    	
	    	List<Sector> sectorList = new ArrayList<Sector>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{
					if(!name.equals("") && name!=null){
					String strQuery = "SELECT * from sectors where name like ? ORDER BY NAME ASC";
				
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setString(1,name);
					}
					else{
						String strQuery = "SELECT * from sectors ORDER BY NAME ASC";
						
						preparedStatement = conn.prepareStatement(strQuery);
						
					}
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						Sector sector = new Sector();
						sector.setId(rs.getInt("id"));
						sector.setName(rs.getString("name"));
						sectorList.add(sector);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
	    return sectorList;
	   }
	 public List<Country> getAllCountries(){
	    	
	    	List<Country> countryList = new ArrayList<Country>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{
					String strQuery = "SELECT * from country ORDER BY NAME ASC";
				
					preparedStatement = conn.prepareStatement(strQuery);
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						Country country = new Country();
						country.setId(rs.getInt("id"));
						country.setName(rs.getString("name"));
						country.setCurrency(rs.getString("currency"));
						countryList.add(country);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
	    return countryList;
	   }
	 
	 public List<Country> queryCountries(String name){
	    	
	    	List<Country> countryList = new ArrayList<Country>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{
					if(!name.equals("") && name!=null){
					String strQuery = "SELECT * from country where name like ? ORDER BY NAME ASC";
				
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setString(1,name);
					}
					else{
						String strQuery = "SELECT * from country ORDER BY NAME ASC";
						
						preparedStatement = conn.prepareStatement(strQuery);
					}
					rs = preparedStatement.executeQuery();
					
					
					while(rs.next())
					{
						Country country = new Country();
						country.setId(rs.getInt("id"));
						country.setName(rs.getString("name"));
						country.setCurrency(rs.getString("currency"));
						countryList.add(country);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
	    return countryList;
	   }
	 public Country getCountryData(String name)
	   	{	
		 Country country = new Country();
	   		Connection conn = getConnection();	
	   	    if (conn != null) 
	   	    {
	   	    	ResultSet rs = null;
	   	    	//Statement stmt = null;
	   	    	PreparedStatement preparedStatement = null;
	   			try
	   			{
	   				String strQuery = "SELECT *"
	   					+" FROM country WHERE name=?";
	   					
	   				preparedStatement = conn.prepareStatement(strQuery);
	   				preparedStatement.setString(1,name);
	   				rs = preparedStatement.executeQuery();
	   				
	   				while(rs.next())
	   				{
	   					country.setId(rs.getInt("id"));
						country.setName(rs.getString("name"));
						country.setCurrency(rs.getString("currency"));
						
	   				}				
	   			}//end of try
	   			catch(SQLException ex){ex.printStackTrace();}
	   			finally 
	   			{
	   	        	try 
	   	        	{ 
	   	        		rs.close();
	   	        		preparedStatement.close(); 
	   	        	}
	   	          	catch (SQLException e) {e.printStackTrace();}
	   	          	putConnection(conn);	        
	   	        }
	   	    }
	   	    return country;
	   	}	

	 public Country getCountryData(int countryID)
	   	{	
		 Country country = new Country();
	   		Connection conn = getConnection();	
	   	    if (conn != null) 
	   	    {
	   	    	ResultSet rs = null;
	   	    	//Statement stmt = null;
	   	    	PreparedStatement preparedStatement = null;
	   			try
	   			{
	   				String strQuery = "SELECT *"
	   					+" FROM country WHERE id=?";
	   					
	   				preparedStatement = conn.prepareStatement(strQuery);
	   				preparedStatement.setInt(1,countryID);
	   				rs = preparedStatement.executeQuery();
	   				
	   				while(rs.next())
	   				{
	   					country.setId(rs.getInt("id"));
						country.setName(rs.getString("name"));
						country.setCurrency(rs.getString("currency"));
						
	   				}				
	   			}//end of try
	   			catch(SQLException ex){ex.printStackTrace();}
	   			finally 
	   			{
	   	        	try 
	   	        	{ 
	   	        		rs.close();
	   	        		preparedStatement.close(); 
	   	        	}
	   	          	catch (SQLException e) {e.printStackTrace();}
	   	          	putConnection(conn);	        
	   	        }
	   	    }
	   	    return country;
	   	}	
	 public List<City> getAllCities(){
	    	
	    	List<City> cityList = new ArrayList<City>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{
					String strQuery = "SELECT * from city ORDER BY NAME ASC";
				
					preparedStatement = conn.prepareStatement(strQuery);
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						City city = new City();
						city.setId(rs.getInt("id"));
						city.setName(rs.getString("name"));
						city.setCountryId(rs.getInt("country_id"));
						cityList.add(city);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
	    return cityList;
	   }
	 public List<City> queryCities(String name){
	    	
	    	List<City> cityList = new ArrayList<City>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{
					if(!name.equals("") && name!=null){
					String strQuery = "SELECT * from city where name like ? ORDER BY NAME ASC";
				
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setString(1,name);
					rs = preparedStatement.executeQuery();
					}
					else{
						String strQuery = "SELECT * from city ORDER BY NAME ASC";
						
						preparedStatement = conn.prepareStatement(strQuery);
						
						rs = preparedStatement.executeQuery();
					}
					
					while(rs.next())
					{
						City city = new City();
						city.setId(rs.getInt("id"));
						city.setName(rs.getString("name"));
						city.setCountryId(rs.getInt("country_id"));
						cityList.add(city);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
	    return cityList;
	   }
	 public List<City> getAllCitiesByCountryId(int countryID){
	    	
	    	List<City> cityList = new ArrayList<City>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{
					String strQuery = "SELECT * from city WHERE country_id=? ORDER BY NAME ASC";
				
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setInt(1,countryID);
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						City city = new City();
						city.setId(rs.getInt("id"));
						city.setName(rs.getString("name"));
						city.setCountryId(rs.getInt("country_id"));
						cityList.add(city);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
	    return cityList;
	   }
	 
	 public City getCityData(int cityID)
	   	{	
	   		City city = new City();
	   		Connection conn = getConnection();	
	   	    if (conn != null) 
	   	    {
	   	    	ResultSet rs = null;
	   	    	//Statement stmt = null;
	   	    	PreparedStatement preparedStatement = null;
	   			try
	   			{
	   				String strQuery = "SELECT *"
	   					+" FROM city WHERE id=?";
	   					
	   				preparedStatement = conn.prepareStatement(strQuery);
	   				preparedStatement.setInt(1,cityID);
	   				rs = preparedStatement.executeQuery();
	   				
	   				while(rs.next())
	   				{
	   					city.setId(rs.getInt("id"));
	   					city.setName(rs.getString("name"));
	   					city.setId(rs.getInt("country_id"));
	   				}				
	   			}//end of try
	   			catch(SQLException ex){ex.printStackTrace();}
	   			finally 
	   			{
	   	        	try 
	   	        	{ 
	   	        		rs.close();
	   	        		preparedStatement.close(); 
	   	        	}
	   	          	catch (SQLException e) {e.printStackTrace();}
	   	          	putConnection(conn);	        
	   	        }
	   	    }
	   	    return city;
	   	}	

	 
	 
	 public void updateJobStatus(int jobID,String jobStatus){
		 
			Connection conn = getConnection();
			//List<Skill> jobSkills=job.getJobSkills();
	    	if(conn != null)
	    	{
	    		try {
	    		
	    		String sql = "UPDATE JOB SET status=? WHERE jid=?";

		    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    		
	    		
	    		preparedStatement.setString(1, jobStatus);
	    		preparedStatement.setInt(2, jobID);
	    		
	    		preparedStatement.executeUpdate();
	    		
	    		/*ResultSet rs=preparedStatement.getGeneratedKeys();
	    		
	    		while(rs.next())
				{
	    		job.setJid(rs.getInt(1));
				}*/
	    		preparedStatement.close(); 
	    		
	    		}
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
					putConnection(conn);	        
		        }
	    		
	    		//System.out.println("Job ID "+job.getJid());
	    		/*for (Skill skill:jobSkills)
	    			
	    			addSkillToJob(job.getJid(),skill.getSkillID());*/
	    		
	    	}
	   }
	 
	 public void updateJobApplicationStatus(int jobApplicationID,String jobStatus){
		 
			Connection conn = getConnection();
			//List<Skill> jobSkills=job.getJobSkills();
	    	if(conn != null)
	    	{
	    		try {
	    		
	    		String sql = "UPDATE jobapplication SET status=? WHERE id=?";

		    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    		
	    		
	    		preparedStatement.setString(1, jobStatus);
	    		preparedStatement.setInt(2, jobApplicationID);
	    		
	    		preparedStatement.executeUpdate();
	    		
	    		/*ResultSet rs=preparedStatement.getGeneratedKeys();
	    		
	    		while(rs.next())
				{
	    		job.setJid(rs.getInt(1));
				}*/
	    		preparedStatement.close(); 
	    		
	    		}
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
					putConnection(conn);	        
		        }
	    		
	    		//System.out.println("Job ID "+job.getJid());
	    		/*for (Skill skill:jobSkills)
	    			
	    			addSkillToJob(job.getJid(),skill.getSkillID());*/
	    		
	    	}
	   }
	 
	 public Profile getProfile(String username){
		
		 Profile profile=new Profile();
		 List<Experience> experiences=getJobSeekerExperiences(username);
		 List<Qualification> qualifications=getJobSeekerQualifications(username);
		 List<Skill> skills=getJobSeekerSkills(username);
		 
		 profile.setExperiences(experiences);
		 profile.setQualifications(qualifications);
		 profile.setSkills(skills);
		
		 return profile;
	 }
	
	 public boolean saveNewProject(Project project){
		 
			Connection conn = getConnection();
			List<Assignment> assignments=project.getAssignments();
			boolean result=true;
	    	if(conn != null)
	    	{
	    		try {
	    		
	    		String sql = "insert into project (code,eid,title,description,postdate,status) values(?, ?, ?, ?, ?, ?)";

		    	PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    		
		    	preparedStatement.setString(1, project.getCode());
		    	preparedStatement.setInt(2, project.getEid());
	    		preparedStatement.setString(3, project.getTitle());
	    		preparedStatement.setString(4, project.getDescription());
	    		preparedStatement.setTimestamp(5, new java.sql.Timestamp(project.getPostDate().getTime()));
	    		preparedStatement.setString(6, project.getStatus());
	    		preparedStatement.executeUpdate();
	    		/*ResultSet rs=preparedStatement.getGeneratedKeys();
	    		
	    		while(rs.next())
				{
	    		job.setJid(rs.getInt(1));
				}*/
	    		preparedStatement.close(); 
	    		
	    		}
				catch(SQLException ex){
					
					ex.printStackTrace();
				result=false;	
				return result;
				}
				finally 
				{
					putConnection(conn);
					
		        }
	    		
	    	try{
	    		for (Assignment assignment:assignments)
	    			addAssignmentToProject(project.getCode(),assignment);
	    	}
	    	catch(NullPointerException ex){};
	    		
	    		
	    	}
		 return result;
	 }
	 
	 public boolean updateProject(Project project){
		 
			Connection conn = getConnection();
			List<Assignment> assignments=project.getAssignments();
			boolean result=true;
	    	if(conn != null)
	    	{
	    		try {
	    		
	    		String sql = "update project set title=?,description=?,status=? where code=? and eid=?";

		    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    		
		    
	    		preparedStatement.setString(1, project.getTitle());
	    		preparedStatement.setString(2, project.getDescription());
	    		preparedStatement.setString(3, project.getStatus());
	    		preparedStatement.setString(4, project.getCode());
		    	preparedStatement.setInt(5, project.getEid());
	    		preparedStatement.executeUpdate();
	    		/*ResultSet rs=preparedStatement.getGeneratedKeys();
	    		
	    		while(rs.next())
				{
	    		job.setJid(rs.getInt(1));
				}*/
	    		preparedStatement.close(); 
	    		
	    		}
				catch(SQLException ex){
					
					ex.printStackTrace();
				result=false;	
				return result;
				}
				finally 
				{
					putConnection(conn);
					
		        }
	    		
	    	
	    	try{
	    		List<Assignment> availAssignments=getProjectAssignmentsByProject(project);
	    		for (Assignment assignment:assignments){
	    			boolean avail=false;
		    			for(Assignment availAssignment:availAssignments){
		    				if(assignment.getProjectCode().equals(availAssignment.getProjectCode()) && assignment.getEmployeeId()==availAssignment.getEmployeeId() && availAssignment.getCreatedDate().compareTo(assignment.getCreatedDate())==0){
		    					avail=true;
		    					break;
		    				}
		    			}
		    			if(!avail){
		    		addAssignmentToProject(project.getCode(),assignment);
		    			}
		    			else
		    		updateAssignmentOnProject(assignment);
		    			}
	    	}
	    	catch(NullPointerException ex){};
	    		
	    		
	    	}
		 return result;
	 }
	 public void addAssignmentToProject(String code,Assignment assignment){
		 	
		 	Connection conn = getConnection();
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "insert into assignment "
		 				+ "  (employeeid, projectcode,createddate,startdate,enddate,goal)"
		 				+ "  values(?,?,?,?,?,?)";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setInt(1,assignment.getEmployeeId());
		 		preparedStatement.setString(2,code);
		 		preparedStatement.setTimestamp(3, new java.sql.Timestamp(assignment.getCreatedDate().getTime()));
		 		preparedStatement.setTimestamp(4, new java.sql.Timestamp(assignment.getStartDate().getTime()));
		 		preparedStatement.setTimestamp(5, new java.sql.Timestamp(assignment.getEndDate().getTime()));
		 		preparedStatement.setInt(6,assignment.getGoal());
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close(); 
		 		}
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	
	}
	 
	 public void updateAssignmentOnProject(Assignment assignment){
		 	
		 	Connection conn = getConnection();
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "update assignment "
		 				+ "  set startdate=?,enddate=?,goal=?"
		 				+ "  where employeeid=? and projectcode=?";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		
		 		preparedStatement.setTimestamp(1, new java.sql.Timestamp(assignment.getStartDate().getTime()));
		 		preparedStatement.setTimestamp(2, new java.sql.Timestamp(assignment.getEndDate().getTime()));
		 		preparedStatement.setInt(3,assignment.getGoal());
		 		preparedStatement.setInt(4,assignment.getEmployeeId());
		 		preparedStatement.setString(5,assignment.getProjectCode());
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close(); 
		 		}
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	
	}
	 
	 public Project getProjectDataByEmployer(String code,int eid){
		  	
			Project project = new Project();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery= 
						"SELECT p.*  "
						+" FROM project p where p.code=? and p.eid=?";
						
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setString(1, code);
							preparedStatement.setInt(2, eid);
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							project.setCode(rs.getString("code"));
							project.setTitle(rs.getString("title"));
							project.setDescription(rs.getString("description"));
							project.setPostDate(rs.getTimestamp("postdate"));
							project.setEid(rs.getInt("eid"));
							project.setStatus(rs.getString("status"));
							project.setAssignments(getProjectAssignmentsByProject(project));
						}				
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
			        	try 
			        	{ 
			        		rs.close();
			        		preparedStatement.close(); 
			        	}
			          	catch (SQLException e) {e.printStackTrace();}
			          	putConnection(conn);	        
			        }
			    }
		  	return project;
		  }
		  
	 public List<Project> getAllProjectsByEmployer(int eid,String status){
		  	
		 List<Project> projectList = new ArrayList<Project>();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	//Statement stmt = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery;
						if (status==""){
							strQuery = 
								"SELECT p.*"
								+" FROM project p where p.eid=? order by p.postdate asc";
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, eid);
						}
						else
						{
							strQuery = 
									"SELECT p.*"
								+" FROM project p where p.eid=? and p.status=? order by p.postdate asc";
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, eid);
							preparedStatement.setString(2, status);
						}
						/*
						stmt = conn.createStatement();
						rs = stmt.executeQuery( strQuery);*/
						
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							Project project = new Project();
							project.setCode(rs.getString("code"));
							project.setTitle(rs.getString("title"));
							project.setDescription(rs.getString("description"));
							project.setPostDate(rs.getTimestamp("postdate"));
							project.setEid(rs.getInt("eid"));
							project.setStatus(rs.getString("status"));
							projectList.add(project);
						}				
						rs.close();
		        		preparedStatement.close(); 
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
			    }
		  	return projectList;
		  }
	 public List<Assignment> getMyProjectAssignments(int employeeId){
		  	
		  	List<Assignment> assignmentList = new ArrayList<Assignment>();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	//Statement stmt = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery;
						
							strQuery = 
								"SELECT a.*"
								+" FROM assignment a  where  a.employeeid=? order by a.startdate asc";
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, employeeId);
						
						/*
						stmt = conn.createStatement();
						rs = stmt.executeQuery( strQuery);*/
						
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							Assignment assignment = new Assignment();
							assignment.setEmployeeId(rs.getInt("employeeid"));
							assignment.setProjectCode(rs.getString("projectcode"));
							assignment.setCreatedDate(rs.getTimestamp("createddate"));
							assignment.setStartDate(rs.getTimestamp("startdate"));
							assignment.setEndDate(rs.getTimestamp("enddate"));
							assignment.setGoal(rs.getInt("goal"));
							
							assignmentList.add(assignment);
						}				
						rs.close();
		        		preparedStatement.close(); 
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
			    }
		  	return assignmentList;
		  }
	 public List<Assignment> getProjectAssignmentsByProject(Project project){
		  	
		  	List<Assignment> assignmentList = new ArrayList<Assignment>();
		  	List<Employee> employeeList = getAllEmployeesByEmployerId(project.getEid());
		  	Connection conn = getConnection();
		  	for(Employee employee:employeeList){
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	//Statement stmt = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery;
						
							strQuery = 
								"SELECT a.*"
								+" FROM assignment a  where  a.employeeid=? and a.projectcode=? order by a.startdate asc";
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, employee.getId());
							preparedStatement.setString(2, project.getCode());
							
							System.out.println(preparedStatement.toString());
						/*
						stmt = conn.createStatement();
						rs = stmt.executeQuery( strQuery);*/
						
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							Assignment assignment = new Assignment();
							assignment.setEmployeeId(rs.getInt("employeeid"));
							assignment.setProjectCode(rs.getString("projectcode"));
							assignment.setCreatedDate(rs.getTimestamp("createddate"));
							assignment.setStartDate(rs.getTimestamp("startdate"));
							assignment.setEndDate(rs.getTimestamp("enddate"));
							assignment.setGoal(rs.getInt("goal"));
							
							assignmentList.add(assignment);
						}				
						rs.close();
		        		preparedStatement.close(); 
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
			    }
		  	}
		  	return assignmentList;
		  }
	 
	 public boolean saveNewTimesheet(Timesheet timesheet){
		 
			Connection conn = getConnection();
			List<LineItem> lineItems=timesheet.getLineItems();
			boolean result=true;
			int id=0;
	    	if(conn != null)
	    	{
	    		try {
	    		
	    		String sql = "insert into timesheet(employeeid,supervisorid,year,month,createddate,status) values(?, ?, ?, ?, ?, ?)";

		    	PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    		
		    	preparedStatement.setInt(1, timesheet.getEmployeeID());
	    		preparedStatement.setInt(2, timesheet.getSupervisorID());
	    		preparedStatement.setInt(3, timesheet.getYear());
	    		preparedStatement.setInt(4, timesheet.getMonth());
	    		preparedStatement.setTimestamp(5, new java.sql.Timestamp(timesheet.getCreatedDate().getTime()));
	    		preparedStatement.setString(6, timesheet.getStatus());
	    		preparedStatement.executeUpdate();
	    		ResultSet rs=preparedStatement.getGeneratedKeys();
	    		
	    		while(rs.next())
				{
	    		id=rs.getInt(1);
				}
	    		preparedStatement.close(); 
	    		
	    		}
				catch(SQLException ex){
					
					ex.printStackTrace();
				result=false;	
				return result;
				}
				finally 
				{
					putConnection(conn);
					
		        }
	    		
	    	try{
	    		for (LineItem lineItem:lineItems){
	    			lineItem.setTimesheetid(id);
	    			addLineItemToTimesheet(lineItem);}
	    	}
	    	catch(NullPointerException ex){};
	    		
	    		
	    	}
		 return result;
	 }
	 
	 public boolean updateTimesheet(Timesheet timesheet){
		 
			Connection conn = getConnection();
			List<LineItem> lineItems=timesheet.getLineItems();
			boolean result=true;
			
	    	if(conn != null)
	    	{
	    		try {
	    		
	    		String sql = "update timesheet set status=? where id=?";

		    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    		
	    		preparedStatement.setString(1, timesheet.getStatus());
	    		preparedStatement.setInt(2, timesheet.getId());
	    		preparedStatement.executeUpdate();
	    		preparedStatement.close(); 
	    		
	    		}
				catch(SQLException ex){
					
					ex.printStackTrace();
				result=false;	
				return result;
				}
				finally 
				{
					putConnection(conn);
					
		        }
	    		
	    	try{
	    		List<LineItem> availlineItems=getAllLineItemsByTimesheetID(timesheet.getId());
	    		for (LineItem lineItem:lineItems){
	    			boolean avail=false;
		    			for(LineItem availLineItem:availlineItems){
		    				if(lineItem.getId()==availLineItem.getId()){
		    					avail=true;
		    					break;
		    				}
		    			}
		    			if(!avail){
	    			lineItem.setTimesheetid(timesheet.getId());
	    			addLineItemToTimesheet(lineItem);
		    			}
		    			else
	    			updateLineItemOnTimesheet(lineItem);}
	    	}
	    	catch(NullPointerException ex){};
	    		
	    		
	    	}
		 return result;
	 }
	 
	 public void addLineItemToTimesheet(LineItem lineItem){
		 	
		 	Connection conn = getConnection();
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "insert into lineitem "
		 				+ "  (timesheetid,projectcode,workhours,description,date,day)"
		 				+ "  values(?,?,?,?,?,?)";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setInt(1,lineItem.getTimesheetid());
		 		preparedStatement.setString(2,lineItem.getProjectCode());
		 		preparedStatement.setInt(3,lineItem.getWorkhours());
		 		preparedStatement.setString(4,lineItem.getDescription());
		 		preparedStatement.setTimestamp(5, new java.sql.Timestamp(lineItem.getDate().getTime()));
		 		preparedStatement.setInt(6,lineItem.getDay());
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close(); 
		 		}
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	
	}
	 
	 public void updateLineItemOnTimesheet(LineItem lineItem){
		 	
		 	Connection conn = getConnection();
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "update lineitem "
		 				+ "  set projectcode=?,workhours=?,description=?"
		 				+ "  where id=?";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setString(1,lineItem.getProjectCode());
		 		preparedStatement.setInt(2,lineItem.getWorkhours());
		 		preparedStatement.setString(3,lineItem.getDescription());
		 		preparedStatement.setInt(4,lineItem.getId());
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close(); 
		 		}
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	
	}
	 
	 public Timesheet getTimesheetByEmployeeID(int employeeid,int year,int month){
		  	
		 Timesheet timesheet = new Timesheet();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery= 
						"SELECT t.*  "
						+" FROM timesheet t where t.employeeid=? and t.year=? and t.month=?";
						
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, employeeid);
							preparedStatement.setInt(2, year);
							preparedStatement.setInt(3, month);
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							timesheet.setId(rs.getInt("id"));
							timesheet.setEmployeeID(rs.getInt("employeeid"));
							timesheet.setStatus(rs.getString("status"));
							timesheet.setCreatedDate(rs.getTimestamp("createddate"));
							timesheet.setYear(rs.getInt("year"));
							timesheet.setMonth(rs.getInt("month"));
							timesheet.setSupervisorID(rs.getInt("supervisorid"));
							
						}				
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
			        	try 
			        	{ 
			        		rs.close();
			        		preparedStatement.close(); 
			        	}
			          	catch (SQLException e) {e.printStackTrace();}
			          	putConnection(conn);	        
			        }
			    }
		  	return timesheet;
		  }
	 
	 public List<Timesheet> getAllTimesheetsByEmployeeID(int employeeid){
		  	
		 List<Timesheet> timesheetList = new ArrayList<Timesheet>();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery= 
						"SELECT t.*  "
						+" FROM timesheet t where t.employeeid=?";
						
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, employeeid);
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							Timesheet timesheet = new Timesheet();
							timesheet.setId(rs.getInt("id"));
							timesheet.setEmployeeID(rs.getInt("employeeid"));
							timesheet.setStatus(rs.getString("status"));
							timesheet.setCreatedDate(rs.getTimestamp("createddate"));
							timesheet.setYear(rs.getInt("year"));
							timesheet.setMonth(rs.getInt("month"));
							timesheet.setSupervisorID(rs.getInt("supervisorid"));
							timesheetList.add(timesheet);
						}				
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
			        	try 
			        	{ 
			        		rs.close();
			        		preparedStatement.close(); 
			        	}
			          	catch (SQLException e) {e.printStackTrace();}
			          	putConnection(conn);	        
			        }
			    }
		  	return timesheetList;
		  }
	 public List<Timesheet> getAllTimesheetsBySupervisorID(int supervisorid){
		  	
		 List<Timesheet> timesheetList = new ArrayList<Timesheet>();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery= 
						"SELECT t.*  "
						+" FROM timesheet t where t.supervisorid=?";
						
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, supervisorid);
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							Timesheet timesheet = new Timesheet();
							timesheet.setId(rs.getInt("id"));
							timesheet.setEmployeeID(rs.getInt("employeeid"));
							timesheet.setStatus(rs.getString("status"));
							timesheet.setCreatedDate(rs.getTimestamp("createddate"));
							timesheet.setYear(rs.getInt("year"));
							timesheet.setMonth(rs.getInt("month"));
							timesheet.setSupervisorID(rs.getInt("supervisorid"));
							timesheetList.add(timesheet);
						}				
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
			        	try 
			        	{ 
			        		rs.close();
			        		preparedStatement.close(); 
			        	}
			          	catch (SQLException e) {e.printStackTrace();}
			          	putConnection(conn);	        
			        }
			    }
		  	return timesheetList;
		  }
	 public List<Timesheet> getAllTimesheetsByEmployer(int eid,String status){
		  	
		 List<Timesheet> timesheetList = new ArrayList<Timesheet>();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	//Statement stmt = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery;
						if (status==""){
							strQuery = 
								"SELECT t.*"
								+" FROM timesheet t,employees e where t.employeeid=e.id and e.eid=? order by t.year,t.month desc";
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, eid);
						}
						else
						{
							strQuery = 
									"SELECT t.*"
								+" FROM timesheet t,employees e where t.employeeid=e.id and e.eid=? and t.status=? order by t.year,t.month desc";
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, eid);
							preparedStatement.setString(2, status);
						}
						/*
						stmt = conn.createStatement();
						rs = stmt.executeQuery( strQuery);*/
						
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							Timesheet timesheet = new Timesheet();
							List<LineItem> lineItems=getAllLineItemsByTimesheetID(rs.getInt("id"));
							timesheet.setId(rs.getInt("id"));
							timesheet.setEmployeeID(rs.getInt("employeeid"));
							timesheet.setStatus(rs.getString("status"));
							timesheet.setCreatedDate(rs.getTimestamp("createddate"));
							timesheet.setYear(rs.getInt("year"));
							timesheet.setMonth(rs.getInt("month"));
							timesheet.setSupervisorID(rs.getInt("supervisorid"));
							timesheet.setLineItems(lineItems);
							timesheetList.add(timesheet);
						}				
						rs.close();
		        		preparedStatement.close(); 
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
			    }
		  	return timesheetList;
		  }
	 
	 public Timesheet getTimesheetData(int timesheetId)
	   	{	
	    	Timesheet timesheet = new Timesheet();
	    	List<LineItem> lineItems=getAllLineItemsByTimesheetID(timesheetId);
	   		Connection conn = getConnection();	
	   	    if (conn != null) 
	   	    {
	   	    	ResultSet rs = null;
	   	    	//Statement stmt = null;
	   	    	PreparedStatement preparedStatement = null;
	   			try
	   			{
	   				String strQuery = "SELECT *"
	   					+" FROM timesheet WHERE id=?";
	   				
	   				preparedStatement = conn.prepareStatement(strQuery);
	   				preparedStatement.setInt(1,timesheetId);
	   				rs = preparedStatement.executeQuery();
	   				
	   				while(rs.next())
	   				{
	   					timesheet.setId(rs.getInt("id"));
						
						timesheet.setStatus(rs.getString("status"));
						timesheet.setCreatedDate(rs.getTimestamp("createddate"));
						timesheet.setEmployeeID(rs.getInt("employeeid"));
						timesheet.setLineItems(lineItems);
						timesheet.setMonth(rs.getInt("month"));
						timesheet.setYear(rs.getInt("year"));
						timesheet.setSupervisorID(rs.getInt("supervisorID"));
						
	   				}				
	   			}//end of try
	   			catch(SQLException ex){ex.printStackTrace();}
	   			finally 
	   			{
	   	        	try 
	   	        	{ 
	   	        		rs.close();
	   	        		preparedStatement.close(); 
	   	        	}
	   	          	catch (SQLException e) {e.printStackTrace();}
	   	          	putConnection(conn);	        
	   	        }
	   	    }
	   	    return timesheet;
	   	}	
	 	
	 public List<LineItem> getAllLineItemsByTimesheetID(int timesheetId){
	    	
	    	List<LineItem> lineItemList = new ArrayList<LineItem>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{
					String strQuery = "SELECT * from lineitem WHERE timesheetid=? ORDER BY date ASC";
				
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setInt(1,timesheetId);
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						LineItem lineItem = new LineItem();
						lineItem.setId(rs.getInt("id"));
						lineItem.setDate(rs.getDate("date"));
						lineItem.setDay(rs.getInt("day"));
						lineItem.setDescription(rs.getString("description"));
						lineItem.setProjectCode(rs.getString("projectcode"));
						lineItem.setTimesheetid(rs.getInt("timesheetid"));
						lineItem.setWorkhours(rs.getInt("workhours"));
						
						lineItemList.add(lineItem);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
	    return lineItemList;
	   }
	 
	 public boolean saveNewEvaluation(Evaluation evaluation){
		 
			Connection conn = getConnection();
			List<Factor> factors=evaluation.getFactors();
			boolean result=true;
			int id=0;
	    	if(conn != null)
	    	{
	    		try {
	    		
	    		String sql = "insert into evaluation(employeeid,supervisorid,createddate,status,reportfileid,startDate,endDate,notes) values(?, ?, ?, ?, ?, ?,?,?)";

		    	PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    		
		    	preparedStatement.setInt(1, evaluation.getEmployeeID());
	    		preparedStatement.setInt(2, evaluation.getSupervisorID());
	    		preparedStatement.setTimestamp(3, new java.sql.Timestamp(evaluation.getCreatedDate().getTime()));
	    		preparedStatement.setString(4, evaluation.getStatus());
	    		preparedStatement.setInt(5, evaluation.getReportFileID());
	    		preparedStatement.setTimestamp(6, new java.sql.Timestamp(evaluation.getStartDate().getTime()));
	    		preparedStatement.setTimestamp(7, new java.sql.Timestamp(evaluation.getEndDate().getTime()));
	    		preparedStatement.setString(8, evaluation.getNotes());
	    		preparedStatement.executeUpdate();
	    		ResultSet rs=preparedStatement.getGeneratedKeys();
	    		
	    		while(rs.next())
				{
	    		id=rs.getInt(1);
				}
	    		preparedStatement.close(); 
	    		
	    		}
				catch(SQLException ex){
					
					ex.printStackTrace();
				result=false;	
				return result;
				}
				finally 
				{
					putConnection(conn);
					
		        }
	    		
	    	try{
	    		for (Factor factor:factors){
	    			factor.setEvaluationid(id);
	    			addFactorToEvaluation(factor);}
	    	}
	    	catch(NullPointerException ex){};
	    		
	    		
	    	}
		 return result;
	 }
	 
	 public boolean updateEvaluation(Evaluation evaluation){
		 
			Connection conn = getConnection();
			
			boolean result=true;
			
	    	if(conn != null)
	    	{
	    		try {
	    		
	    		String sql = "update evaluation set status=?,notes=? where id=?";

		    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    		
	    		preparedStatement.setString(1, evaluation.getStatus());
	    		preparedStatement.setString(2, evaluation.getNotes());
	    		preparedStatement.setInt(3, evaluation.getId());
	    		preparedStatement.executeUpdate();
	    		preparedStatement.close(); 
	    		
	    		}
				catch(SQLException ex){
					
					ex.printStackTrace();
				result=false;	
				return result;
				}
				finally 
				{
					putConnection(conn);
					
		        }
	    		
	    	try{
	    		List<Factor> factors=evaluation.getFactors();
	    		for (Factor factor:factors){
	    			
	    			updateFactorOnEvaluation(factor);}
	    	}
	    	catch(NullPointerException ex){};
	    		
	    		
	    	}
		 return result;
	 }
	 public void addFactorToEvaluation(Factor factor){
		 	
		 	Connection conn = getConnection();
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "insert into factor "
		 				+ "  (evaluationid,name,score,comment)"
		 				+ "  values(?,?,?,?)";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setInt(1,factor.getEvaluationid());
		 		preparedStatement.setString(2,factor.getName());
		 		preparedStatement.setInt(3,factor.getScore());
		 		preparedStatement.setString(4,factor.getComment());
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close(); 
		 		}
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	
	}
	 
	 public void updateFactorOnEvaluation(Factor factor){
		 	
		 	Connection conn = getConnection();
		 	if(conn != null)
		 	{
		 		try {
		 		String sql = "update factor "
		 				+ "  set score=?,comment=?"
		 				+ "  where id=?";
		 		
		 		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 		preparedStatement.setInt(1,factor.getScore());
		 		preparedStatement.setString(2,factor.getComment());
		 		preparedStatement.setInt(3,factor.getId());
		 		preparedStatement.executeUpdate();
		 		preparedStatement.close(); 
		 		}
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
						putConnection(conn);	        
			        }
		 	}
		 	
	}
	 public List<Evaluation> getAllEvaluationsByEmployeeID(int employeeid,String status){
		  	
		 List<Evaluation> evaluationList = new ArrayList<Evaluation>();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery= 
						"SELECT e.*  "
						+" FROM evaluation e where e.employeeid=? and e.status=?";
						
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, employeeid);
							preparedStatement.setString(2, status);
							
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							Evaluation evaluation = new Evaluation();
							evaluation.setId(rs.getInt("id"));
							evaluation.setEmployeeID(rs.getInt("employeeid"));
							evaluation.setStatus(rs.getString("status"));
							evaluation.setCreatedDate(rs.getTimestamp("createddate"));
							evaluation.setNotes(rs.getString("notes"));
							evaluation.setStartDate(rs.getTimestamp("startdate"));
							evaluation.setEndDate(rs.getTimestamp("enddate"));
							evaluation.setReportFileID(rs.getInt("reportfileid"));
							evaluation.setSupervisorID(rs.getInt("supervisorid"));
							evaluationList.add(evaluation);
						}				
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
			        	try 
			        	{ 
			        		rs.close();
			        		preparedStatement.close(); 
			        	}
			          	catch (SQLException e) {e.printStackTrace();}
			          	putConnection(conn);	        
			        }
			    }
		  	return evaluationList;
		  }
	 
	 public List<Evaluation> getAllEvaluationsByEmployeeID(int employeeid){
		  	
		 List<Evaluation> evaluationList = new ArrayList<Evaluation>();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery= 
						"SELECT e.*  "
						+" FROM evaluation e where e.employeeid=?";
						
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, employeeid);
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							Evaluation evaluation = new Evaluation();
							evaluation.setId(rs.getInt("id"));
							evaluation.setEmployeeID(rs.getInt("employeeid"));
							evaluation.setStatus(rs.getString("status"));
							evaluation.setCreatedDate(rs.getTimestamp("createddate"));
							evaluation.setNotes(rs.getString("notes"));
							evaluation.setStartDate(rs.getTimestamp("startdate"));
							evaluation.setEndDate(rs.getTimestamp("enddate"));
							evaluation.setReportFileID(rs.getInt("reportfileid"));
							evaluation.setSupervisorID(rs.getInt("supervisorid"));
							evaluationList.add(evaluation);
						}				
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
			        	try 
			        	{ 
			        		rs.close();
			        		preparedStatement.close(); 
			        	}
			          	catch (SQLException e) {e.printStackTrace();}
			          	putConnection(conn);	        
			        }
			    }
		  	return evaluationList;
		  }
	 public Evaluation getAllEvaluationByEmployeeID(int employeeid,java.util.Date startDate,java.util.Date endDate){
		  	
		 Evaluation evaluation = new Evaluation();
		
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery= 
						"SELECT e.*  "
						+" FROM evaluation e where e.employeeid=? and e.startdate=? and e.enddate=?";
						
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, employeeid);
							preparedStatement.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
							preparedStatement.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
							
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							
							evaluation.setId(rs.getInt("id"));
							evaluation.setEmployeeID(rs.getInt("employeeid"));
							evaluation.setStatus(rs.getString("status"));
							evaluation.setCreatedDate(rs.getTimestamp("createddate"));
							evaluation.setNotes(rs.getString("notes"));
							evaluation.setStartDate(rs.getTimestamp("startdate"));
							evaluation.setEndDate(rs.getTimestamp("enddate"));
							evaluation.setReportFileID(rs.getInt("reportfileid"));
							evaluation.setSupervisorID(rs.getInt("supervisorid"));
							
						}				
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
			        	try 
			        	{ 
			        		rs.close();
			        		preparedStatement.close(); 
			        	}
			          	catch (SQLException e) {e.printStackTrace();}
			          	putConnection(conn);	        
			        }
			    }
		  	return evaluation;
		  }
	 
	 public List<Evaluation> getAllEvaluationsByEmployer(int eid,String status){
		  	
		 List<Evaluation> evaluationList = new ArrayList<Evaluation>();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery;
						if (status==""){
							strQuery = 
								"SELECT ev.*"
								+" FROM evaluation ev,employees e where ev.employeeid=e.id and e.eid=? order by ev.startdate desc";
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, eid);
						}
						else
						{
							strQuery = 
									"SELECT ev.*"
								+" FROM evaluation ev,employees e where ev.employeeid=e.id and e.eid=? and ev.status=? order by ev.startdate desc";
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, eid);
							preparedStatement.setString(2, status);
						}
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							Evaluation evaluation = new Evaluation();
							evaluation.setId(rs.getInt("id"));
							evaluation.setEmployeeID(rs.getInt("employeeid"));
							evaluation.setStatus(rs.getString("status"));
							evaluation.setCreatedDate(rs.getTimestamp("createddate"));
							evaluation.setNotes(rs.getString("notes"));
							evaluation.setStartDate(rs.getTimestamp("startdate"));
							evaluation.setEndDate(rs.getTimestamp("enddate"));
							evaluation.setReportFileID(rs.getInt("reportfileid"));
							evaluation.setSupervisorID(rs.getInt("supervisorid"));
							evaluationList.add(evaluation);
						}				
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
			        	try 
			        	{ 
			        		rs.close();
			        		preparedStatement.close(); 
			        	}
			          	catch (SQLException e) {e.printStackTrace();}
			          	putConnection(conn);	        
			        }
			    }
		  	return evaluationList;
		  }
	 
	 public Evaluation getEvaluationData(int evaluationId)
	   	{	
		 	Evaluation evaluation = new Evaluation();
	    	List<Factor> factors=getAllFactorsByEvaluationID(evaluationId);
	   		Connection conn = getConnection();	
	   	    if (conn != null) 
	   	    {
	   	    	ResultSet rs = null;
	   	    	//Statement stmt = null;
	   	    	PreparedStatement preparedStatement = null;
	   			try
	   			{
	   				String strQuery = "SELECT *"
	   					+" FROM evaluation WHERE id=?";
	   				
	   				preparedStatement = conn.prepareStatement(strQuery);
	   				preparedStatement.setInt(1,evaluationId);
	   				rs = preparedStatement.executeQuery();
	   				
	   				while(rs.next())
	   				{
	   					evaluation.setId(rs.getInt("id"));
						evaluation.setEmployeeID(rs.getInt("employeeid"));
						evaluation.setStatus(rs.getString("status"));
						evaluation.setCreatedDate(rs.getTimestamp("createddate"));
						evaluation.setNotes(rs.getString("notes"));
						evaluation.setStartDate(rs.getTimestamp("startdate"));
						evaluation.setEndDate(rs.getTimestamp("enddate"));
						evaluation.setReportFileID(rs.getInt("reportfileid"));
						evaluation.setSupervisorID(rs.getInt("supervisorid"));
						evaluation.setFactors(factors);
						
	   				}				
	   			}//end of try
	   			catch(SQLException ex){ex.printStackTrace();}
	   			finally 
	   			{
	   	        	try 
	   	        	{ 
	   	        		rs.close();
	   	        		preparedStatement.close(); 
	   	        	}
	   	          	catch (SQLException e) {e.printStackTrace();}
	   	          	putConnection(conn);	        
	   	        }
	   	    }
	   	    return evaluation;
	   	}	
	 public List<Factor> getAllFactorsByEvaluationID(int evaluationId){
	    	
		 List<Factor> factorList = new ArrayList<Factor>();
	    	Connection conn = getConnection();	
		    if (conn != null) 
		    {
		    	ResultSet rs = null;
		    	//Statement stmt = null;
		    	PreparedStatement preparedStatement = null;
				try
				{
					String strQuery = "SELECT * from factor WHERE evaluationid=?";
				
					preparedStatement = conn.prepareStatement(strQuery);
					preparedStatement.setInt(1,evaluationId);
					rs = preparedStatement.executeQuery();
					
					while(rs.next())
					{
						Factor factor = new Factor();
						factor.setId(rs.getInt("id"));
						factor.setName(rs.getString("name"));
						factor.setScore(rs.getInt("score"));
						factor.setComment(rs.getString("comment"));
						factor.setEvaluationid(rs.getInt("evaluationid"));

						factorList.add(factor);
					}				
				}//end of try
				catch(SQLException ex){ex.printStackTrace();}
				finally 
				{
		        	try 
		        	{ 
		        		rs.close();
		        		preparedStatement.close(); 
		        	}
		          	catch (SQLException e) {e.printStackTrace();}
		          	putConnection(conn);	        
		        }
		    }
	    return factorList;
	   }
	 public List<Evaluation> getAllEvaluationsBySupervisorID(int supervisorid){
		  	
		 List<Evaluation> evaluationList = new ArrayList<Evaluation>();
		  	Connection conn = getConnection();	
			    if (conn != null) 
			    {
			    	ResultSet rs = null;
			    	PreparedStatement preparedStatement = null;
					try
					{	
						String strQuery= 
						"SELECT e.*  "
						+" FROM evaluation e where e.supervisorid=?";
						
							preparedStatement = conn.prepareStatement(strQuery);
							preparedStatement.setInt(1, supervisorid);
						
						rs = preparedStatement.executeQuery();
						
						while(rs.next())
						{
							Evaluation evaluation = new Evaluation();
							evaluation.setId(rs.getInt("id"));
							evaluation.setEmployeeID(rs.getInt("employeeid"));
							evaluation.setStatus(rs.getString("status"));
							evaluation.setCreatedDate(rs.getTimestamp("createddate"));
							evaluation.setNotes(rs.getString("notes"));
							evaluation.setStartDate(rs.getTimestamp("startdate"));
							evaluation.setEndDate(rs.getTimestamp("enddate"));
							evaluation.setReportFileID(rs.getInt("reportfileid"));
							evaluation.setSupervisorID(rs.getInt("supervisorid"));
							evaluationList.add(evaluation);
						}				
					}//end of try
					catch(SQLException ex){ex.printStackTrace();}
					finally 
					{
			        	try 
			        	{ 
			        		rs.close();
			        		preparedStatement.close(); 
			        	}
			          	catch (SQLException e) {e.printStackTrace();}
			          	putConnection(conn);	        
			        }
			    }
		  	return evaluationList;
		  }
}