package mqtt.publisher;

import org.eclipse.paho.client.mqttv3.MqttException;

public class TestMotorMoverSolution {

    public static final String BROKER_URL = "tcp://iot.eclipse.org:1883";
    // public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";

    public static final String userid = "14042553"; // change this to be your student-id
    public static final String TOPIC_MOTOR = userid + "/motor";

    static PhidgetPublisher publisher = new PhidgetPublisher(); // source in PhidgetPublisher.java

	public static void main(String[] args) {
		try {
			while (true) {
				System.out.println("Tester publishing to move motor");
				publisher.publishMotor(90.0);
				// wait for motor to move
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Tester published and done.");
			}
		} catch (MqttException e) {
			System.out.println("Tester publish error");
			e.printStackTrace();
		}

	}

}
