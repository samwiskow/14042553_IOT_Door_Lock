package common;

public enum MqttSettings {
	USER ("14042553"),
	BROKER ("tcp://iot.eclipse.org:1883")
	;
	
	private final String setting;
	
	private MqttSettings(String setting){
		this.setting = setting;
	}
	
	public String getSetting(){
		return this.setting;
	}
}
