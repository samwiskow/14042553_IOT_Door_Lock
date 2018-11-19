package rfid;


import org.eclipse.paho.client.mqttv3.*;

import common.MqttSettings;


public class LockStatusCallback implements MqttCallback {

	MqttSettings uuid = MqttSettings.USER;
	
    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Message arrived. Topic: " + topic + "  Message: " + message.toString());
        DoorAccessIndicator access = new DoorAccessIndicator();
        String messageStr = message.toString();
        if(messageStr.contains("accepted")){
        	System.out.println("DEBUG : LIGHT Accepted");
        	access.turnOnLight(true, 1);
        } else{
        	System.out.println("DEBUG : LIGHT REJECTED");
        	access.turnOnLight(true, 2);
        }

        if ((uuid.getSetting()+"/LWT").equals(topic)) {
            System.err.println("Sensor gone!");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //no-op
    }
}
