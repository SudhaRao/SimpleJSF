package com.simple.jsf.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {	
	
	private static final Logger LOG = Logger.getLogger(DBConnection.class.getName());
	
   
	public static void main(String[] args) {		
		getWholeSaleUsers();
	}
	
	
	 public static Connection getConnection(){
	      Connection con = null;

	      String url = "jdbc:db2://nfdbw2:50010/crm";
	      String user = "web";
	      String password = "caz124";
	      try {
	    	 Class.forName("com.ibm.db2.jcc.DB2Driver");
	         con = DriverManager.getConnection(url, user, password);
	         System.out.println("Connection completed.");
	      } catch (SQLException ex) {
	         System.out.println(ex.getMessage());
	      } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      finally{
	      }
	      return con;
	   }
	 
   /*
	public static Connection getConnection() {
	        Properties props = new Properties();
	        FileInputStream fis = null;
	        Connection con = null;
	        try {
	            fis = new FileInputStream("db.properties");
	            props.load(fis);
	 
	            // load the Driver Class
	            Class.forName(props.getProperty("DB_DRIVER_CLASS"));
	 
	            // create the connection now
	            con = DriverManager.getConnection(props.getProperty("DB_URL"),
	                    props.getProperty("DB_USERNAME"),
	                    props.getProperty("DB_PASSWORD"));
	        } catch (IOException io){
	            // TODO Auto-generated catch block
	            io.printStackTrace();
	        }catch(ClassNotFoundException cnf){
	        	cnf.printStackTrace();
	        }catch(SQLException sqlEx) {
	        	sqlEx.printStackTrace();
	        }catch (Exception e){
	        	e.printStackTrace();
	        }
	        return con;
	}
	*/
	
	public static Connection getConnection(String s)
	{
		Connection connection = null;
		try
		{
			Hashtable hashtable = new Hashtable();
			InitialContext initialcontext = new InitialContext();
			DataSource datasource = (DataSource)initialcontext.lookup("java:comp/env/" +s);
			connection = datasource.getConnection();

		}
		catch(NamingException namingexception)
		{
			namingexception.printStackTrace();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}
		return connection;
	}
	   
	public static List<Object> getWholeSaleUsers(){
	   
	   Connection conn = null;
	   Statement stmt = null;
	   List<Object> result = new ArrayList<Object>();
	   
	   try{		  
			  LOG.log(Level.INFO, "Connecting to database...");
			  //Open a connection
			  if(null == getConnection() ){
				  LOG.log(Level.INFO, "Connection to the database unsuccessfull...");
			  }else {
				  
				  conn = getConnection();		      
				  LOG.log(Level.INFO, "Connected database successfully...");
				
				  //Execute a query
				  LOG.log(Level.INFO, "Creating statement...");		 
				  stmt = conn.createStatement();
				  String sql = "SELECT USERID,EMPLOYEE_NBR,LAST_NAME,FIRST_NAME,FULL_NAME,LAST_SIGNON FROM CRMADMIN.T_WHSE_USER WHERE TYPE='Y' ";
				  ResultSet rs = stmt.executeQuery(sql);
				  int numcols = rs.getMetaData().getColumnCount();		      
				  System.out.println("Number of Columns.............."+numcols);
				  
				  //Extract data from result set			      
				  while(rs.next()){
					//Retrieve by column name
					  String id = rs.getString("USERID");
					  String empNumber = rs.getString("EMPLOYEE_NBR");
					  String lastName = rs.getString("LAST_NAME");
					  String firstName = rs.getString("FIRST_NAME");
					  String fullName = rs.getString("FULL_NAME");
					  String lastlogin = rs.getString("LAST_SIGNON");
					  
					  LOG.log(Level.INFO, "UserID............" +id);
					  LOG.log(Level.INFO, "empNumber............" +empNumber);
					  LOG.log(Level.INFO, "firstName.........." +firstName);
					  LOG.log(Level.INFO, "lastName..........." +lastName);
					  LOG.log(Level.INFO, "fullName............" +fullName);
					  LOG.log(Level.INFO, "lastlogin............" +lastlogin);
					  
					  
					  List<String> row = new ArrayList<String>();
					  		    	  
					  for (int i=1; i<= numcols; i++) {  // don't skip the last column, use <=
						  row.add(rs.getString(i));
						  
						  //System.out.println(rs.getString(i) + "\t");
					  }
					  result.add(row); // add it to the result		          
					  System.out.println("Number of rows in the list ....."+result.size());
				  }
				  //Clean-up environment
			      rs.close();
			      stmt.close();
			      conn.close();
			  }
		  }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		  }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		  }finally{
		      //finally block used to close resources
			  try{
			     if(stmt!=null)
			        stmt.close();
			  }catch(SQLException se2){
			  }// nothing we can do
			  try{
			     if(conn!=null)
			        conn.close();
			  }catch(SQLException se){
			     se.printStackTrace();
			  }//end finally try
		   }//end try
	   LOG.log(Level.INFO, "Goodbye!");	
	   
		   
	   return result;
	   
	}
}

