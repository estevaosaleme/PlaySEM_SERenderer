package br.ufes.inf.lprm.sensoryeffect.renderer;

import java.util.HashMap;
import java.util.Vector;

import org.fourthline.cling.binding.annotations.UpnpInputArgument;

import br.ufes.inf.lprm.sensoryeffect.renderer.parser.ClassificationScheme;
import br.ufes.inf.lprm.sensoryeffect.renderer.parser.HelperFunctions;
import br.ufes.inf.lprm.sensoryeffect.renderer.parser.SEMParser;
import br.ufes.inf.lprm.sensoryeffect.renderer.serial.SerialMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.TimeLine;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.TimeLine.Status;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.TimeLineAction;

public abstract class SEServiceBase implements SEService {
	
	private static String currentTime = ""; 
	private String duration = "";
	private String sensoryEffectMetadata = "";
	private static String capabilitiesMetadata = "";
	private static String hexLeft = "";
	private static String hexCenter = "";
	private static String hexRight = "";
	private static int eventId = 0;
	private static Boolean lightAutoExtraction = false;
	private static Boolean semPrepared = false;
	private static int semEventIdPrepared = 0;
	
	private HashMap<Integer,HashMap<Vector<TimeLineAction>,Long>> eventList = new HashMap<Integer,HashMap<Vector<TimeLineAction>,Long>>();
	public HashMap<Integer,HashMap<Vector<TimeLineAction>,Long>> getEventList() {
		return eventList;
	}
	public void setEventList(HashMap<Integer,HashMap<Vector<TimeLineAction>,Long>> eventList) {
		this.eventList = eventList;
	}
		
	private void initializeSetSem(String newSensoryEffectMetadata, String newDuration) {
		semPrepared = false;
		semEventIdPrepared = 0;
		sensoryEffectMetadata = newSensoryEffectMetadata;
		duration = newDuration;
		currentTime = "0";
		lightAutoExtraction = false;
	}
	
	public void setSem(String newSensoryEffectMetadata,	String newDuration) {
		initializeSetSem(newSensoryEffectMetadata,newDuration);
		//firePropertyChange("SemPrepared", "", semPrepared);
		if (SERendererBroker.debugMode)
			System.out.println("SetSem");
		Thread semParser = new Thread(new SEMParser(sensoryEffectMetadata, Long.parseLong(duration)));
		semParser.start();
	}
	
	public void setSemEvent(
			String newSensoryEffectMetadata,
			String newDuration,
			int eventId) {
		initializeSetSem(newSensoryEffectMetadata, newDuration);
		//firePropertyChange("SemEventIdPrepared", "", semEventIdPrepared);
		Thread semParser = new Thread(new SEMParser(sensoryEffectMetadata, Long.parseLong(duration), eventId));
		semParser.start();	
		if (SERendererBroker.debugMode)
			System.out.println("SetSemEvent-> EventId="+eventId);
	}
	
	public void setPlay(String newCurrentTime) {
		SERendererBroker.timeLine.play(Long.parseLong(newCurrentTime));
	}
	
	public void setPlayEvent(int eventId) {
		HashMap<Vector<TimeLineAction>,Long> timeLineEvent = SERendererBroker.service.getEventList().get(Integer.valueOf(eventId));
		if (timeLineEvent != null) {
			SERendererBroker.timeLine.setActions(timeLineEvent.entrySet().iterator().next().getKey(), timeLineEvent.entrySet().iterator().next().getValue());
			SERendererBroker.timeLine.play(0);
			if (SERendererBroker.debugMode)
				System.out.println("SetPlayEvent-> EventId="+eventId);
		} else
			System.out.println("The event "+ eventId + " does not exist.");
	}
	
	public void setCleanEventList() {
		SERendererBroker.service.setEventList(new HashMap<Integer,HashMap<Vector<TimeLineAction>,Long>>());
	}
	
	public void setPause(String newCurrentTime) {
		SERendererBroker.timeLine.pause(Long.parseLong(newCurrentTime));
	}
	
	public void setStop() {
		SERendererBroker.timeLine.stop();
	}
	
	public void setCurrentTime(
			@UpnpInputArgument(name = "CurrentTime") 
			String newCurrentTime) {
		currentTime = newCurrentTime;
		SERendererBroker.timeLine.setCurrentTime(Long.parseLong(currentTime));
	}
	
	public void setLightColors(String newHexLeft, String newHexCenter, String newHexRight) {
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
			if (SERendererBroker.debugMode)
				System.out.println("SetLightColors");
		}
		else {
			hexLeft = "";
			hexCenter = "";
			hexRight = "";
		}
	}
	
	public void setLocalCurrentTime(long newCurrentTime){
		currentTime = String.valueOf(newCurrentTime);
	}
	
	public void setCapabilitiesMetadata(String newCapabilitiesMetadata){
		capabilitiesMetadata = newCapabilitiesMetadata;
	}
	
	public void setLightAutoExraction(Boolean newLightAutoExtraction){
		lightAutoExtraction = newLightAutoExtraction;
	}
	
	public void setSemPrepared(Boolean value) {
		semPrepared = value;
	}
	
	public void setSemEventPrepared(int value) {
		semEventIdPrepared = value;
	}
}
