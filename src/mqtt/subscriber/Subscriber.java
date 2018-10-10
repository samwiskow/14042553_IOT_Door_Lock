package mqtt.subscriber;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import rfid.LockStatusCallback;

/**
 * @author Dominik Obermaier
 * @author Christian Götz
 */
public class Subscriber {

    public static final String BROKER_URL = "tcp://iot.eclipse.org:1883";
    // public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";

    public static final String userid = "14042553"; // change this to be your student-id

    //We have to generate a unique Client id.
    //private String clientId = Utils.getMacAddress() + "-sub";
    private String clientId;
    private MqttClient mqttClient;

    public Subscriber(String brokerUrl, String userId) {
    	
    	clientId = userid + "-sub";

        try {
            mqttClient = new MqttClient(brokerUrl, clientId);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start(String topic, LockStatusCallback callback) {
        try {
            mqttClient.setCallback(callback);
            mqttClient.connect();
            final String Topic = userid + "/" + topic;
            mqttClient.subscribe(Topic);
            System.out.println("Subscriber is now listening to " + Topic);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /*public static void main(String... args) {
        final Subscriber subscriber = new Subscriber(BROKER_URL, userid);
        subscriber.start("test", new LockStatusCallback());
    }*/
}
