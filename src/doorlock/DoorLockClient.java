package doorlock;

import common.PubSubClient;

public class DoorLockClient {

	public DoorLockClient() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String... args){
		PubSubClient doorLock = new PubSubClient("tcp://iot.eclipse.org:1883","14042553");
		doorLock.subscribe("doorlock", new MotorSubscribeCallback(doorLock));
	}

}
