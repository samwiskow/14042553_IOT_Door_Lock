package controller;

import org.eclipse.paho.client.mqttv3.*;
import com.google.gson.Gson;
import common.PubSubClient;
import common.SensorData;


public class DoorAccessSystemCallback implements MqttCallback {

    public static final String userid = "14042553"; // change this to be your student-id
    Gson gson = new Gson();
    DBUtils db = new DBUtils();
    PubSubClient pub;
    
    public DoorAccessSystemCallback(PubSubClient client){
    	this.pub = client;
    }

    @Override
    public void connectionLost(Throwable cause) {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        
    	System.out.println("DEBUG: Door Access Message Topic - " + topic + " /n/n Message: " + message.toString());
    	
    	SensorData data;
    	Boolean openLock = false;
    	
		if (!message.toString().contains("Tag Lost")) {
			try {
				data = gson.fromJson(message.toString(), SensorData.class);
				openLock = db.returnAccessStatus(data.getSensorvalue());
				data.setAccessrequest(openLock);

				db.updateSensorTable(data);
				System.out.println("DEBUG: table updated");
				
				if(openLock){
		    		System.out.println("DEBUG: Publish lock open");
		    		pub.publish("open", "doorlock");
		    		pub.publish("accepted", "token");
		    	} else {
		    		System.out.println("DEBUG: Publish lock close");
		    		pub.publish("close", "doorlock");
		    		pub.publish("rejected", "token");
		    	}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} else{
    		pub.publish("close", "doorlock");
		}
		

        if ((userid+"/LWT").equals(topic)) {
            System.err.println("Sensor gone!");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //no-op
    }
}
