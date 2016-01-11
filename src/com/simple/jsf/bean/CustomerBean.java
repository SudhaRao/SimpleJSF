package com.simple.jsf.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.simple.jsf.model.Customer;

@ManagedBean(name="customerBean")
@SessionScoped
public class CustomerBean implements Serializable{
	
	private static final Logger LOG = Logger.getLogger(CustomerBean.class.getName());
	private static final long serialVersionUID = 1L;	

	public CustomerBean(){		
	}
	
	public String viewCustomer(){
		try {
			try {
				getCustomerList();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "customerlist";
	}
		
	//connect to DB and get customer list
	public List<Customer> getCustomerList() throws SQLException, ClassNotFoundException{
				
		Connection conn = null;	
		PreparedStatement ps = null;  // Or PreparedStatement if needed
		ResultSet rs = null;
		List<Customer> list = new ArrayList<Customer>();
		
		try{
			
			Connection connect = null;
			 
		    String url = "jdbc:db2://nfdbw2:50010/crm";

		    String username = "web";
		    String password = "caz124";
		    try {
		        Class.forName("com.ibm.db2.jcc.DB2Driver");
		        LOG.log(Level.INFO, "Connecting to database...");
		        connect = DriverManager.getConnection(url, username, password);
		        // System.out.println("Connection established"+connect);

		    } catch (SQLException ex) {
		        System.out.println("in exec");
		        System.out.println(ex.getMessage());
		    }
		    
		
			 if(null == connect ){
				  LOG.log(Level.INFO, "Connection to the database unsuccessfull...");
			  }else {
				//Open a connection
				conn = connect;		      
				LOG.log(Level.INFO, "Connected database successfully..."); 
						  
			
				ps = conn.prepareStatement(
					   "SELECT USERID,EMPLOYEE_NBR,LAST_NAME,FIRST_NAME,FULL_NAME,LAST_SIGNON FROM CRMADMIN.T_WHSE_USER WHERE TYPE='Y'");
				rs = ps.executeQuery();
			      
			    while(rs.next()){
			    	Customer cust = new Customer();					
					cust.setCustomerId(rs.getString("USERID"));					
					cust.setCustomerName(rs.getString("FULL_NAME"));					
					cust.setCustomerAddress("Edina,MN");					
					cust.setCreatedDate(rs.getString("LAST_SIGNON"));
					
					
					System.out.println("User ID..........."+cust.getCustomerId());
					System.out.println("FULL_NAME..........."+cust.getCustomerName());
					System.out.println("Address..........."+cust.getCustomerAddress());
					System.out.println("LAST_SIGNON..........."+cust.getCreatedDate());
					
					//store all data into a List
					list.add(cust);
				
			    }
			    
			    rs.close();
			    rs=null;
			    ps.close();
			    ps=null;
			    conn.close();
			    conn=null;			 		
			  }
		}catch (SQLException e) {
		    e.printStackTrace();
		  } finally {
		    // Always make sure result sets and statements are closed,
		    // and the connection is returned to the pool
		    if (rs != null) {
		      try { rs.close(); } catch (SQLException e) { ; }
		      rs = null;
		    }
		    if (ps != null) {
		      try { ps.close(); } catch (SQLException e) { ; }
		      ps = null;
		    }
		    if (conn != null) {
		      try { conn.close(); } catch (SQLException e) { ; }
		      conn = null;
		    }
		  }
		  return list;
		}	
}
