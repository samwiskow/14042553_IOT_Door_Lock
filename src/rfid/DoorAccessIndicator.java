package rfid;
import com.phidget22.DigitalOutput;
import com.phidget22.PhidgetException;

	public class DoorAccessIndicator {

		DigitalOutput Access = new DigitalOutput();
		
		public DoorAccessIndicator() throws PhidgetException{
		}
	
	   public void turnOnLight(boolean lightState, int channel){
	        try {
	        	Access.setChannel(channel);
	        	Access.open(2000);
	        	Access.setState(lightState);
	        	Thread.sleep(3000);
	        	Access.close();
			} catch (PhidgetException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
