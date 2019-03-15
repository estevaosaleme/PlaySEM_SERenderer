package br.ufes.inf.lprm.sensoryeffect.renderer.service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Vector;

import org.fourthline.cling.binding.annotations.UpnpInputArgument;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererBroker;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.EventBasedData;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.LightMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.Helper;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.MainTimeLine;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.MainTimeLine.Status;

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
	private HashMap<String, String> properties = null;
	
	public HashMap<String, String> getProperties() {
		return properties;
	}
	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}
	
	private HashMap<Integer,HashMap<String, EventBasedData>> eventList = new HashMap<Integer,HashMap<String, EventBasedData>>();
	public HashMap<Integer,HashMap<String, EventBasedData>> getEventList() {
		return eventList;
	}
	public void setEventList(HashMap<Integer,HashMap<String, EventBasedData>> eventList) {
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
		
		Class<?> clazz;
		try {
			clazz = Class.forName(SERendererBroker.metadataParserClass);	
			Constructor<?> constructorMetadataParser = clazz.getConstructor(String.class, Long.class);
			Runnable metadataParserInstance = (Runnable)constructorMetadataParser.newInstance(sensoryEffectMetadata, Long.parseLong(duration));
			Thread metadataParser = new Thread(metadataParserInstance);
			metadataParser.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setSemEvent(
			String newSensoryEffectMetadata,
			String newDuration,
			int eventId) {
		initializeSetSem(newSensoryEffectMetadata, newDuration);
		//firePropertyChange("SemEventIdPrepared", "", semEventIdPrepared);
		
		Class<?> clazz;
		try {
			clazz = Class.forName(SERendererBroker.metadataParserClass);	
			Constructor<?> constructorMetadataParser = clazz.getConstructor(String.class, Long.class, Integer.class);
			Runnable metadataParserInstance = (Runnable)constructorMetadataParser.newInstance(sensoryEffectMetadata, Long.parseLong(duration), eventId);
			Thread metadataParser = new Thread(metadataParserInstance);
			metadataParser.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (SERendererBroker.debugMode)
			System.out.println("SetSemEvent-> EventId="+eventId);
	}
	
	public void setPlay(String newCurrentTime) {
		SERendererBroker.timeLine.play(Long.parseLong(newCurrentTime));
	}
	
	public void setPlayEvent(int eventId) {
		HashMap<String, EventBasedData> eventBasedDataList = SERendererBroker.service.getEventList().get(Integer.valueOf(eventId));
		Long durationEvent = 0L;
		if (eventBasedDataList != null) {
			EventBasedData eventBasedDataLight = eventBasedDataList.get(SERendererBroker.lightDevice.getId());
			if (eventBasedDataLight != null) {
				Vector<TimeLineDeviceCommand> commands = (Vector<TimeLineDeviceCommand>) eventBasedDataLight.getCommands().clone();
				SERendererBroker.lightDevice.setTimeLineDeviceCommands(commands);
				SERendererBroker.lightDevice.setTempTimeLineDeviceCommands(commands);
				durationEvent = eventBasedDataLight.getDuration();
			}
			EventBasedData eventBasedDataWind = eventBasedDataList.get(SERendererBroker.windDevice.getId());
			if (eventBasedDataWind != null) {
				Vector<TimeLineDeviceCommand> commands = (Vector<TimeLineDeviceCommand>) eventBasedDataWind.getCommands().clone();
				SERendererBroker.windDevice.setTimeLineDeviceCommands(commands);
				SERendererBroker.windDevice.setTempTimeLineDeviceCommands(commands);
				durationEvent = eventBasedDataWind.getDuration();
			}
			EventBasedData eventBasedDataVibration = eventBasedDataList.get(SERendererBroker.vibrationDevice.getId());
			if (eventBasedDataVibration != null) {
				Vector<TimeLineDeviceCommand> commands = (Vector<TimeLineDeviceCommand>) eventBasedDataVibration.getCommands().clone();
				SERendererBroker.vibrationDevice.setTimeLineDeviceCommands(commands);
				SERendererBroker.vibrationDevice.setTempTimeLineDeviceCommands(commands);
				durationEvent = eventBasedDataVibration.getDuration();
			}
			EventBasedData eventBasedDataScent = eventBasedDataList.get(SERendererBroker.scentDevice.getId());
			if (eventBasedDataScent != null) {
				Vector<TimeLineDeviceCommand> commands = (Vector<TimeLineDeviceCommand>) eventBasedDataScent.getCommands().clone();
				SERendererBroker.scentDevice.setTimeLineDeviceCommands(commands);
				SERendererBroker.scentDevice.setTempTimeLineDeviceCommands(commands);
				durationEvent = eventBasedDataScent.getDuration();
			}	
			SERendererBroker.timeLine.getReady(durationEvent);
			SERendererBroker.timeLine.play(0);
			if (SERendererBroker.debugMode)
				System.out.println("SetPlayEvent-> EventId="+eventId);
		} else
			System.out.println("The event "+ eventId + " does not exist.");
	}
	
	public void setClearEventList() {
		SERendererBroker.service.getEventList().clear();
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
		if (lightAutoExtraction && MainTimeLine.getStatus().getId() == Status.PLAYING.getId()){
			hexLeft = newHexLeft;
			hexCenter = newHexCenter;
			hexRight = newHexRight;
			int[] rgbLeft = Helper.hexToRGB(hexLeft);
			int[] rgbCenter = Helper.hexToRGB(hexCenter);
			int[] rgbRight = Helper.hexToRGB(hexRight);
			
			LightMessage lightMessageLeft = new LightMessage();
			lightMessageLeft.setRed(rgbLeft[0]);
			lightMessageLeft.setGreen(rgbLeft[1]);
			lightMessageLeft.setBlue(rgbLeft[2]);
			lightMessageLeft.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT);
			byte[] messageLeft = SERendererBroker.lightDevice.formatMessage(lightMessageLeft);
			
			LightMessage lightMessageCenter = new LightMessage();
			lightMessageCenter.setRed(rgbCenter[0]);
			lightMessageCenter.setGreen(rgbCenter[1]);
			lightMessageCenter.setBlue(rgbCenter[2]);
			lightMessageCenter.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER);
			byte[] messageCenter = SERendererBroker.lightDevice.formatMessage(lightMessageCenter);

			LightMessage lightMessageRight = new LightMessage();
			lightMessageRight.setRed(rgbRight[0]);
			lightMessageRight.setGreen(rgbRight[1]);
			lightMessageRight.setBlue(rgbRight[2]);
			lightMessageRight.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT);
			byte[] messageRight = SERendererBroker.lightDevice.formatMessage(lightMessageRight);
			
			SERendererBroker.lightDevice.getDeviceConnectivity().sendMessage(messageLeft);
			SERendererBroker.lightDevice.getDeviceConnectivity().sendMessage(messageCenter);
			SERendererBroker.lightDevice.getDeviceConnectivity().sendMessage(messageRight);

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
	
	public String getCurrentTime() {
		return currentTime;
	}
	
	public String getCapabilitiesMetadata() {
		return capabilitiesMetadata;
	}
}
