package rfid;

import com.phidget22.PhidgetException;
import com.phidget22.RFID;
import com.phidget22.RFIDTagEvent;
import com.phidget22.RFIDTagListener;
import com.phidget22.RFIDTagLostEvent;
import com.phidget22.RFIDTagLostListener;
import common.MqttSettings;
import common.PubSubClient;
import common.SystemRequest;
import common.SensorData;
import common.Topic;
import org.eclipse.paho.client.mqttv3.MqttException;
import com.google.gson.Gson;

	public class RFIDScannerClient  {
	    
	    SystemRequest req;
	    SensorData data;
	    Gson gson = new Gson();
	    
	    public static void main(String[] args) throws PhidgetException {
		    MqttSettings uuid = MqttSettings.USER;
		    MqttSettings broker = MqttSettings.BROKER;
	    	PubSubClient accessControl = new PubSubClient(broker.getSetting(), uuid.getSetting());
	    	//accessControl.subscribe(t.getTopic(), new LockStatusCallback());
	        new RFIDScannerClient(accessControl);
	    }

	    public RFIDScannerClient(PubSubClient client) throws PhidgetException {
	    	
	    	RFID rfid = new RFID();
	    	data = new SensorData("unknown", String.valueOf(rfid.getDeviceSerialNumber()));
	    	req = new SystemRequest(data, SystemRequest.rtype.CHECK_ACCESS);
	    	rfid.addTagListener(new RFIDTagListener() {
				public void onTag(RFIDTagEvent e) {
					
					String tagStr = e.getTag();
					data.setTagid(tagStr);
					try {
						data.setRfidserialnum(String.valueOf(rfid.getDeviceSerialNumber()));
						System.out.println("DEBUG: serial num: " + data.getRfidserialnum());

					} catch (PhidgetException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					req.setSensordata(data);
					System.out.println("Tag read: " + tagStr);
  					String rfidJson = gson.toJson(req);
	        		
	        		try {
						client.publish(rfidJson, Topic.RFID.getTopic());
					} catch (MqttException e1) {
						e1.printStackTrace();
					}
				}
	        });

	        rfid.addTagLostListener(new RFIDTagLostListener() {
				public void onTagLost(RFIDTagLostEvent e) {
					String tagStr = e.getTag();
					req.setRtype(SystemRequest.rtype.TAG_LOST);
  					String rfidJson = gson.toJson(req);
					/*try {
						client.publish(rfidJson, Topic.RFID.getTopic());
					} catch (MqttException e1) {
						e1.printStackTrace();
					}*/
					System.out.println("DEBUG Tag lost: " + tagStr);
				}
	        });
	        
	        // Open and start detecting rfid cards
	        
	        rfid.open(5000);
	        //System.out.println("DEBUG serial number subscribe : " + rfid.getDeviceID() + " :" + rfid.getDeviceSerialNumber() + " :" + rfid.getPhidgetIDString());
	    	client.subscribe(String.valueOf(rfid.getDeviceSerialNumber()), new LockStatusCallback());
	        
	    }
}
