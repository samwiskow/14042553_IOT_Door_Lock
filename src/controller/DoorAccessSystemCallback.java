package controller;

import org.eclipse.paho.client.mqttv3.*;
import com.google.gson.Gson;
import common.PubSubClient;
import common.SystemRequest;
import common.SystemResponse;
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
    	SystemRequest req;
    	
		try {
			
			req = gson.fromJson(message.toString(), SystemRequest.class);
			data = req.getSensordata();
			
			if(req.getRtype() == SystemRequest.rtype.CHECK_ACCESS){
				data = db.checkAccess(data);
				db.updateSensorTable(data);
				if(data.getResult()){
		    		System.out.println("DEBUG: Publish lock open");
		    		pub.publish("open", req.getSensordata().getServoserialnum());
		    		pub.publish("accepted", req.getSensordata().getRfidserialnum());
		    	} else {
		    		System.out.println("DEBUG: Publish lock close");
		    		pub.publish("close", req.getSensordata().getServoserialnum());
		    		pub.publish("rejected", req.getSensordata().getRfidserialnum());
		    	}
			}else if(req.getRtype() == SystemRequest.rtype.GET_LOGS){
				SystemResponse r = new SystemResponse(db.getAccessLogs(), SystemResponse.rtype.GET_LOGS);
				String responseStr = gson.toJson(r);
				pub.publish(responseStr, "androidsysadmin/logs");
			}else if(req.getRtype() == SystemRequest.rtype.GET_ROOMS){
				SystemResponse r = new SystemResponse(db.getRooms(), SystemResponse.rtype.GET_ROOMS);
				String responseStr = gson.toJson(r);
				pub.publish(responseStr, "androidsysadmin/rooms");
			}else if(req.getRtype() == SystemRequest.rtype.TAG_LOST){
	    		//pub.publish("close", req.getSensordata().getServoserialnum());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
