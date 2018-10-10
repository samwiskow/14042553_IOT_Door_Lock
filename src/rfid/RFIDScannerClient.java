package rfid;

import com.phidget22.PhidgetException;
import com.phidget22.RFID;
import com.phidget22.RFIDTagEvent;
import com.phidget22.RFIDTagListener;
import com.phidget22.RFIDTagLostEvent;
import com.phidget22.RFIDTagLostListener;
import common.PubSubClient;
import common.SensorData;
import org.eclipse.paho.client.mqttv3.MqttException;
import com.google.gson.Gson;

	public class RFIDScannerClient  {
	    
	    //PhidgetPublisher publisher = new PhidgetPublisher();
	    SensorData rfidData = new SensorData("rfid", "");
	    Gson gson = new Gson();
	    public static String sensorServerURL = "http://localhost:8080/PhidgetServer/RfidHandler";
	    
	    public static void main(String[] args) throws PhidgetException {
	        PubSubClient accessControl = new PubSubClient("tcp://iot.eclipse.org:1883","14042553");
	    	accessControl.subscribe("token", new LockStatusCallback());
	        new RFIDScannerClient(accessControl);
	    }

	    public RFIDScannerClient(PubSubClient client) throws PhidgetException {
	    	
	    	RFID rfid = new RFID();
	    	
	    	rfid.addTagListener(new RFIDTagListener() {
				public void onTag(RFIDTagEvent e) {
					String tagStr = e.getTag();
					rfidData.setSensorvalue(tagStr);
					rfidData.setUserid(rfid.getPhidgetIDString());
					System.out.println("Tag read: " + tagStr);
  					String rfidJson = gson.toJson(rfidData);
	        		// 
	        		try {
						//publisher.publishRfid(rfidJson);
						client.publish(rfidJson, rfidData.getSensorname());
					} catch (MqttException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	        });

	        rfid.addTagLostListener(new RFIDTagLostListener() {
				public void onTagLost(RFIDTagLostEvent e) {
					String tagStr = e.getTag();
					try {
						client.publish("Tag Lost", rfidData.getSensorname());
					} catch (MqttException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("Tag lost: " + tagStr);
				}
	        });
	        // Open and start detecting rfid cards
	        rfid.open(5000);
	        
	    }
}
