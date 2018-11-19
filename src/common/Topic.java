package common;

public enum Topic {
	UUID ("14042553"),
	DOORLOCK ("doorlock"),
	RFID ("rfid"),
	TOKEN ("token"),
	LOCKSTATUS ("lockstatus")
	;
	
	private final String topic;
	
	private Topic(String topic){
		this.topic = topic;
	}
	
	public String getTopic(){
		return this.topic;
	}

}
