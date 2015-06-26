package br.ufes.inf.lprm.sensoryeffect.renderer;

import java.beans.PropertyChangeSupport;

import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

import br.ufes.inf.lprm.sensoryeffect.renderer.parser.ClassificationScheme;
import br.ufes.inf.lprm.sensoryeffect.renderer.parser.HelperFunctions;
import br.ufes.inf.lprm.sensoryeffect.renderer.parser.SEMParser;
import br.ufes.inf.lprm.sensoryeffect.renderer.serial.SerialMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.TimeLine;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.TimeLine.Status;

@UpnpService(
    serviceId = @UpnpServiceId("RendererControl"),
    serviceType = @UpnpServiceType(value = "RendererControl", version = 1)
)
public class SERendererService {
	
	private static PropertyChangeSupport propertyChangeSupport = null;
	
	public SERendererService() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	public static PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private static String currentTime = ""; 
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private String duration = "";
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private String sensoryEffectMetadata = "";
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private static String capabilitiesMetadata = "";
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private static String hexLeft = "";
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private static String hexCenter = "";
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private static String hexRight = "";
	
	@UpnpStateVariable(defaultValue = "false", sendEvents = false)
	private static Boolean lightAutoExtraction = false;
	
	@UpnpStateVariable(defaultValue = "false", sendEvents = true)
	public static Boolean semPrepared = false;
	
	@UpnpAction
	public void setSem(
			@UpnpInputArgument(name = "SensoryEffectMetadata") 
			String newSensoryEffectMetadata,
			@UpnpInputArgument(name = "Duration") 
			String newDuration) {
		semPrepared = false;
		getPropertyChangeSupport().firePropertyChange("SemPrepared", "", semPrepared);
		sensoryEffectMetadata = newSensoryEffectMetadata;
		duration = newDuration;
		currentTime = "0";
		lightAutoExtraction = false;
		if (SERendererDevice.debugMode)
			System.out.println("SetSem");
		Thread semParser = new Thread(new SEMParser(sensoryEffectMetadata, Long.parseLong(duration)));
		semParser.start();
	}
	
	@UpnpAction
	public void setPlay(
			@UpnpInputArgument(name = "CurrentTime") 
			String newCurrentTime) {
		SERendererDevice.timeLine.play(Long.parseLong(newCurrentTime));
	}
	
	@UpnpAction
	public void setPause(
			@UpnpInputArgument(name = "CurrentTime") 
			String newCurrentTime) {
		SERendererDevice.timeLine.pause(Long.parseLong(newCurrentTime));
	}
	
	@UpnpAction
	public void setStop() {
		SERendererDevice.timeLine.stop();
	}
		    
	@UpnpAction
	public void setCurrentTime(
			@UpnpInputArgument(name = "CurrentTime") 
			String newCurrentTime) {
		currentTime = newCurrentTime;
		SERendererDevice.timeLine.setCurrentTime(Long.parseLong(currentTime));
	}
	
	@UpnpAction
	public void setLightColors(
			@UpnpInputArgument(name = "HexLeft") 
			String newHexLeft,
			@UpnpInputArgument(name = "HexCenter") 
			String newHexCenter,
			@UpnpInputArgument(name = "HexRight") 
			String newHexRight) {
		if (lightAutoExtraction && TimeLine.status.getId() == Status.PLAYING.getId()){
			hexLeft = newHexLeft;
			hexCenter = newHexCenter;
			hexRight = newHexRight;
			int[] rgbLeft = HelperFunctions.hexToRGB(hexLeft);
			int[] rgbCenter = HelperFunctions.hexToRGB(hexCenter);
			int[] rgbRight = HelperFunctions.hexToRGB(hexRight);
			byte[] messageLedLeft = SerialMessage.prepareMessageToLed(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT, (char)rgbLeft[0], (char)rgbLeft[1], (char)rgbLeft[2]);
			byte[] messageLedCenter = SerialMessage.prepareMessageToLed(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER, (char)rgbCenter[0], (char)rgbCenter[1], (char)rgbCenter[2]);
			byte[] messageLedRight = SerialMessage.prepareMessageToLed(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT, (char)rgbRight[0], (char)rgbRight[1], (char)rgbRight[2]);

//			System.out.println(currentTime + " autoextraction: " + SerialMessage.byteArrayToString(messageLedLeft));
//			System.out.println(currentTime + " autoextraction: " + SerialMessage.byteArrayToString(messageLedCenter));
//			System.out.println(currentTime + " autoextraction: " + SerialMessage.byteArrayToString(messageLedRight));

			SerialMessage.sendMessage(messageLedLeft);
			SerialMessage.sendMessage(messageLedCenter);
			SerialMessage.sendMessage(messageLedRight);
			if (SERendererDevice.debugMode)
				System.out.println("SetLightColors");
		}
		else {
			hexLeft = "";
			hexCenter = "";
			hexRight = "";
		}
	}
	
	public static void setLocalCurrentTime(long newCurrentTime){
		currentTime = String.valueOf(newCurrentTime);
	}
	
	public static void setCapabilitiesMetadata(String newCapabilitiesMetadata){
		capabilitiesMetadata = newCapabilitiesMetadata;
	}
	
	public static void setLightAutoExraction(Boolean newLightAutoExtraction){
		lightAutoExtraction = newLightAutoExtraction;
	}
}


