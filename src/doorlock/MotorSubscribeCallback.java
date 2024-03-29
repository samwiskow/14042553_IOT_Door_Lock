package doorlock;

import org.eclipse.paho.client.mqttv3.*;

import com.google.gson.Gson;

import common.PubSubClient;
import common.Topic;
import controller.DBUtils;
import mqtt.utils.Utils;


public class MotorSubscribeCallback implements MqttCallback {

    Topic userid = Topic.UUID; // change this to be your student-id
    Gson gson = new Gson();
    PubSubClient pub;
    
    public MotorSubscribeCallback(PubSubClient client){
    	this.pub = client;
    }

    @Override
    public void connectionLost(Throwable cause) {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Message arrived. Topic: " + topic + "  Message: " + message.toString());
        
        //Double position = Double.parseDouble(message.toString());
        
       if(message.toString().contains("open")){
    	   // Move motor to open, then shut after pausing
           PhidgetMotorMover.moveServoTo(180);
           System.out.println("Waiting until motor at position " + 180);
           pub.publish("unlocked", String.valueOf(PhidgetMotorMover.getInstance().getDeviceSerialNumber()) + "/status");
           Utils.waitFor(5);
           PhidgetMotorMover.moveServoTo(0.0);
           Utils.waitFor(2);
           pub.publish("locked", String.valueOf(PhidgetMotorMover.getInstance().getDeviceSerialNumber()) + "/status");
       }
        
        if ((userid.getTopic()+"/LWT").equals(topic)) {
            System.err.println("Sensor gone!");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //no-op
    }
    
}
