package doorlock;

import com.phidget22.PhidgetException;
import common.MqttSettings;
import common.PubSubClient;

public class DoorLockClient {

	public DoorLockClient() {
	}
	
	public static void main(String... args){
		MqttSettings uuid = MqttSettings.USER;
	    MqttSettings broker = MqttSettings.BROKER;
		PubSubClient doorLock = new PubSubClient(broker.getSetting(), uuid.getSetting());
		try {
			doorLock.subscribe(String.valueOf(PhidgetMotorMover.getInstance().getDeviceSerialNumber()), new MotorSubscribeCallback(doorLock));
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
