package common;

import java.util.ArrayList;

public class SystemResponse {
	
	private ArrayList<SensorData> datalist;
	private rtype rtype;
	
	public enum rtype {
		GET_LOGS,
		GET_ROOMS
	}

	public SystemResponse(ArrayList<SensorData> datalist, rtype t) {
		this.setDatalist(datalist);
		this.setRtype(t);
	}


	/**
	 * @return the rtype
	 */
	public rtype getRtype() {
		return rtype;
	}



	/**
	 * @param rtype the rtype to set
	 */
	public void setRtype(rtype rtype) {
		this.rtype = rtype;
	}


	/**
	 * @return the datalist
	 */
	public ArrayList<SensorData> getDatalist() {
		return datalist;
	}


	/**
	 * @param datalist the datalist to set
	 */
	public void setDatalist(ArrayList<SensorData> datalist) {
		this.datalist = datalist;
	}

}
