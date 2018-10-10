package controller;

import com.google.gson.Gson;
import com.phidget22.PhidgetException;
import common.*;

public class DoorAccessSystemClient {
	
	//PhidgetPublisher publisher = new PhidgetPublisher();
    SensorData data = new SensorData("", "");
    Gson gson = new Gson();

	public DoorAccessSystemClient() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws PhidgetException {
    	//final Subscriber subscriber = new Subscriber(BROKER_URL, userid);
        //subscriber.start("test", new LockStatusCallback());
        PubSubClient accessController = new PubSubClient("tcp://iot.eclipse.org:1883","14042553");
        /*try {
			accessController.publish("operen", "doorlock");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        accessController.subscribe("rfid", new DoorAccessSystemCallback(accessController));
    }

}
