package common;

import java.sql.Date;

public class SensorData {
	
	String tagid, rfidserialnum, name, servoserialnum;
	int roomid, userid;
	Boolean result;
	Date timestamp;

	public SensorData(String tagid, String serialnum) {
		this.tagid = tagid;
		this.rfidserialnum = serialnum;
		this.name = "unknown";
		this.servoserialnum = "unknown";
		this.result = false;
		this.timestamp = null;
	}

	// Constructors depending on which parameters are known
	public SensorData(String tagid, String rfidserialnum, String name, String servoserialnum, int roomid, int userid, Boolean result) {
		this.tagid = tagid;
		this.rfidserialnum = rfidserialnum;
		this.name = name;
		this.servoserialnum = servoserialnum;
		this.roomid = roomid;
		this.userid = userid;
		this.result = false;
		this.timestamp = null;
	}
	
	public SensorData(String name, int userid, int roomid, boolean result, Date timestamp){
		this.name = name;
		this.userid = userid;
		this.roomid = roomid;
		this.result = result;
		this.timestamp = timestamp;
	}
	
	
	/**
	 * @return the tagid
	 */
	public String getTagid() {
		return tagid;
	}

	/**
	 * @param tagid the tagid to set
	 */
	public void setTagid(String tagid) {
		this.tagid = tagid;
	}

	/**
	 * @return the rfidserialnum
	 */
	public String getRfidserialnum() {
		return rfidserialnum;
	}

	/**
	 * @param rfidserialnum the rfidserialnum to set
	 */
	public void setRfidserialnum(String rfidserialnum) {
		this.rfidserialnum = rfidserialnum;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the servoserialnum
	 */
	public String getServoserialnum() {
		return servoserialnum;
	}

	/**
	 * @param servoserialnum the servoserialnum to set
	 */
	public void setServoserialnum(String servoserialnum) {
		this.servoserialnum = servoserialnum;
	}

	/**
	 * @return the roomid
	 */
	public int getRoomid() {
		return roomid;
	}

	/**
	 * @param roomid the roomid to set
	 */
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * @return the result
	 */
	public Boolean getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Boolean result) {
		this.result = result;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
