package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.SensorData;

public class DBUtils {
	
	Connection conn = null;
	Statement stmt;

	public DBUtils() {
		String user = "test_user";
	    String password = "test";
	    // Note none default port used, 6306 not 3306
	    String url = "jdbc:mysql://localhost:3306/mad";

		// Load the database driver
		try {  Class.forName("com.mysql.jdbc.Driver").newInstance();
	        } catch (Exception e) {
	            System.out.println(e);
	        }

			// get a connection with the user/pass
	        try {
	            conn = DriverManager.getConnection(url, user, password);
	            System.out.println("DEBUG: Connection to database successful.");
	            stmt = conn.createStatement();
	        } catch (SQLException se) {
	            System.out.println(se);
	            System.out.println("\nDid you alter the lines to set user/password in the sensor server code?");
	        }
	}
	
	public void destroy() {
        try { conn.close(); } catch (SQLException se) {
            System.out.println(se);
        }
	}
	
	public void updateSensorTable(SensorData data){
		try {
			// Create the INSERT statement from the parameters
			// set time inserted to be the current time on database server
			String updateSQL = 
		     	"insert into sensorusage(userid, sensorname, sensorvalue, latitude, longitude, timeinserted, accessrequest) " +
		     	"values('"+data.getUserid()      + "','" +
				           data.getSensorname()  + "','" +
			 	           data.getSensorvalue()  + "'," +
		     	           data.getLatitude()  + "," +
		     	           data.getLongitude()  + "," +
		     	           "now(),"
		     	           + data.getAccessrequest() + ");";
		     	           
		        System.out.println("DEBUG: Update: " + updateSQL);
		        stmt.executeUpdate(updateSQL);
		        System.out.println("DEBUG: Update successful ");
		} catch (SQLException se) {
			// Problem with update, return failure message
		    System.out.println(se);
	        System.out.println("\nDEBUG: Update error - see error trace above for help. ");
		    return;
		}
	
		// all ok,  return
		return;
	}
	
	public Boolean returnAccessStatus(String tag){
			
			String selectSQL = "Select * from security_access where AccessKey ='" + tag + "'";
			ResultSet rs;
			String permission = "";
			
			try{
				rs = stmt.executeQuery(selectSQL);
				
				
				while (rs.next()) {
				    permission = rs.getString("AccessPermission");
				    
				   	// debug print this sensor to console
				   	System.out.println("DEBUG: Permission = " + permission);
				}
			}
			catch(SQLException e){
				System.out.println("SQL Error: " + e);
			}
			
			Boolean access = permission.equals("Full");
			// test
			return access;
			
		}

}
