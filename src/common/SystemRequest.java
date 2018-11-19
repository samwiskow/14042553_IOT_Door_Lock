package common;

public class SystemRequest {
	
	private SensorData sensordata;
	private rtype rtype;
	
	public enum rtype {
		GET_LOGS,
		UPDATE_LOGS,
		CHECK_ACCESS,
		TAG_LOST,
		GET_ROOMS
	}

	public SystemRequest(SensorData data, rtype t) {
		this.sensordata = data;
		this.setRtype(t);
	}


	/**
	 * @return the sensordata
	 */
	public SensorData getSensordata() {
		return sensordata;
	}



	/**
	 * @param sensordata the sensordata to set
	 */
	public void setSensordata(SensorData sensordata) {
		this.sensordata = sensordata;
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

}
