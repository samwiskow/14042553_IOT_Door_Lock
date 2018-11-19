package controller;

import com.phidget22.PhidgetException;
import common.*;

public class DoorAccessSystemClient {

	public DoorAccessSystemClient() {
	}
	
	public static void main(String[] args) throws PhidgetException {
		MqttSettings uuid = MqttSettings.USER;
	    MqttSettings broker = MqttSettings.BROKER;
        PubSubClient accessController = new PubSubClient(broker.getSetting(), uuid.getSetting());
   
        accessController.subscribe("rfid", new DoorAccessSystemCallback(accessController));
    }

}
