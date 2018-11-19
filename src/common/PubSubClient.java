package common;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import mqtt.utils.Utils;

public class PubSubClient {

    //public static final String BROKER_URL = "tcp://iot.eclipse.org:1883";
    // public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";

    public static final String userid = "14042553";
    public static final String TOPIC_GENERIC    = userid + "/";

    //We have to generate a unique Client id.
    //private String clientId = Utils.getMacAddress() + "-sub";
    private String clientId;
    private MqttClient mqttClient;

    public PubSubClient(String brokerUrl, String userId) {
    	
    	clientId = userid + Utils.createRandomNumberBetween(0, 6473);
    	//clientId = Utils.getMacAddress();

        try {
            mqttClient = new MqttClient(brokerUrl, clientId);
            // create mqtt session
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setWill(mqttClient.getTopic(userid + "/LWT"), "I'm gone :(".getBytes(), 0, false);
            mqttClient.connect(options);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    

    public void subscribe(String topic, MqttCallback callback) {
        try {
            mqttClient.setCallback(callback);
        	topic = TOPIC_GENERIC + topic;
            mqttClient.subscribe(topic, 2);
            System.out.println("Subscriber is now listening to " + topic);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void publish(String sensorValue, String topic) throws MqttException {
    	topic = TOPIC_GENERIC + topic;
    	mqttClient.publish(topic, sensorValue.getBytes(), 2, true);
        System.out.println("Published data. Topic: " + topic + "   Message: " + sensorValue);
    }
}
