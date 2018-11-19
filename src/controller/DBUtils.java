	package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;

import common.SystemRequest;
import common.SensorData;

public class DBUtils {
	
	Connection conn = null;
	Statement stmt;
	Gson gson = new Gson();
	SystemRequest req;
	SensorData data;

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
	
	public SensorData checkAccess(SensorData data){
		
		String selectSQL = "Select u.fullname, u.userid, r.roomid, p.access, r.servoserialnum "
				+ "From users u inner join user_permissions p on u.userid = p.userid inner join rooms r on p.roomid = r.roomid "
				+ "where r.rfidserialnum Like '" + data.getRfidserialnum() + "' And u.accesskey = '" + data.getTagid() + "'";
		ResultSet rs;
		String permission = "";
		
		try{
			rs = stmt.executeQuery(selectSQL);
			
			
			while (rs.next()) {
			    data.setResult(rs.getBoolean("access"));
			    data.setName(rs.getString("fullname"));
			    data.setUserid(rs.getInt("userid"));
			    data.setRoomid(rs.getInt("roomid"));
			    data.setServoserialnum(rs.getString("servoserialnum"));
			   	// debug print this sensor to console
			   	//System.out.println("DEBUG: data = " + data.getResult());
			   	//System.out.println("DEBUG: access: " + rs.getBoolean("access"));
			}
		}
		catch(SQLException e){
			System.out.println("SQL Error: " + e);
		}
		
		return data;
	}
	
	public void updateSensorTable(SensorData data){
		try {
			// Create the INSERT statement from the parameters
			// set time inserted to be the current time on database server
			String updateSQL = 
					"INSERT INTO `MAD`.`access_logs` (`userid`, `roomid`, `result`, `timestamp`)"
							+ " VALUES("
							 + data.getUserid() +"," + data.getRoomid() +", " + data.getResult() + ",NOW())";
		     	           
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
	
	public ArrayList<SensorData> getRooms(){
		ArrayList<SensorData> datalist = new ArrayList<SensorData>();
		String selectSQL = "SELECT * FROM MAD.rooms;";
		ResultSet rs;
		
		try{
			rs = stmt.executeQuery(selectSQL);
			SensorData data = new SensorData("unknown", "unknown");
			
			while (rs.next()) {
			   	data.setName(rs.getString("roomname"));
			   	data.setRoomid(rs.getInt("roomid"));
			   	data.setServoserialnum(rs.getString("servoserialnum"));
			   	data.setRfidserialnum(rs.getString("rfidserialnum"));
			   	// add this sensor to ArrayList of Sensors
			   	datalist.add(data);
			   	// debug print this sensor to console
			   	System.out.println("DEBUG: senor = " + data.toString());
			}
		}
		catch(SQLException e){
			System.out.println("SQL Error: " + e);
		}
		
		return datalist;
	}
	
	public ArrayList<SensorData> getAccessLogs(){
		
		ArrayList<SensorData> datalist = new ArrayList<SensorData>();
		String selectSQL = "SELECT u.fullname, l.userid, l.roomid, l.result, l.timestamp "
				+ "FROM MAD.access_logs l "
				+ "inner join users u on l.userid = u.userid "
				+ "order by l.timestamp desc "
				+ "Limit 10;";
		ResultSet rs;
		
		try{
			rs = stmt.executeQuery(selectSQL);
			SensorData data = new SensorData("unknown", "unknown");
			
			while (rs.next()) {
			   	data.setName(rs.getString("fullname"));
			   	data.setUserid(rs.getInt("userid"));
			   	data.setRoomid(rs.getInt("roomid"));
			   	data.setResult(rs.getBoolean("result"));
			   	data.setTimestamp(rs.getDate("timestamp"));
			   	// add this sensor to ArrayList of Sensors
			   	datalist.add(data);
			   	// debug print this sensor to console
			   	System.out.println("DEBUG: senor = " + data.toString());
			}
		}
		catch(SQLException e){
			System.out.println("SQL Error: " + e);
		}
		
		return datalist;
	}
	
	//extra function if time
	public void addSecurityUser(){
		
	}

}
