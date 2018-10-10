package rfid;


import org.eclipse.paho.client.mqttv3.*;


public class LockStatusCallback implements MqttCallback {

    public static final String userid = "14042553"; // change this to be your student-id

    @Override
    public void connectionLost(Throwable cause) {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Message arrived. Topic: " + topic + "  Message: " + message.toString());
        DoorAccessIndicator access = new DoorAccessIndicator();
        String messageStr = message.toString();
        if(messageStr.contains("accepted")){
        	access.turnOnLight(true, 1);
        	Thread.sleep(3000);
        	//access.turnOnLight(false, 1);
        } else{
        	access.turnOnLight(true, 0);
        	Thread.sleep(3000);
        	access.turnOnLight(false, 0);
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
