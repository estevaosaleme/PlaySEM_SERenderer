package br.ufes.inf.lprm.sensoryeffect.renderer.device;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.ConnectivityBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessageBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;

public abstract class SensoryEffectDeviceBase implements SensoryEffectDevice {
	private String id = "";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	private HashMap<String, String> properties = null;
	public HashMap<String, String> getProperties() {
		return properties;
	}
	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}
	
	private ConnectivityBase deviceConnectivity = null;
	public ConnectivityBase getDeviceConnectivity() {
		return deviceConnectivity;
	}
	public void setDeviceConnectivity(ConnectivityBase deviceConnectivity) {
		this.deviceConnectivity = deviceConnectivity;
	}
	
	private Vector<SensoryEffectMessageBase> timeLineSensoryEffectMessages = null;
	public Vector<SensoryEffectMessageBase> getTimeLineSensoryEffectMessages() {
		return timeLineSensoryEffectMessages;
	}
	public <T extends SensoryEffectMessageBase> void setTimeLineSensoryEffectMessages(Vector<T> timeLineSensoryEffectMessages) {
		this.timeLineSensoryEffectMessages = (Vector<SensoryEffectMessageBase>)timeLineSensoryEffectMessages;
		this.timeLineDeviceCommands = new Vector<TimeLineDeviceCommand>();
		this.tempTimeLineDeviceCommands = new Vector<TimeLineDeviceCommand>();
		// It converts sensory effect messages into hardware commands
		for (SensoryEffectMessage sensoryEffectMessageBase : timeLineSensoryEffectMessages) {
			this.timeLineDeviceCommands.addAll(this.messageToDeviceCommand(sensoryEffectMessageBase));
			this.tempTimeLineDeviceCommands.addAll(this.messageToDeviceCommand(sensoryEffectMessageBase));
		}
		// It queues the messages by time
		Comparator<TimeLineDeviceCommand> comp = new Comparator<TimeLineDeviceCommand>() {
		      public int compare(TimeLineDeviceCommand ta1, TimeLineDeviceCommand ta2) {
		        return ta1.getOccurrenceTime().compareTo(ta2.getOccurrenceTime());
		      }
		    };
		Collections.sort(this.timeLineDeviceCommands, comp);
		Collections.sort(this.tempTimeLineDeviceCommands, comp);
	}
	
	private Vector<TimeLineDeviceCommand> timeLineDeviceCommands = null;
	public Vector<TimeLineDeviceCommand> getTimeLineDeviceCommands() {
		return timeLineDeviceCommands;
	}
	public void setTimeLineDeviceCommands(Vector<TimeLineDeviceCommand> timeLineDeviceCommands) {
		this.timeLineDeviceCommands = timeLineDeviceCommands;
	}
	
	private Vector<TimeLineDeviceCommand> tempTimeLineDeviceCommands = null;
	public Vector<TimeLineDeviceCommand> getTempTimeLineDeviceCommands() {
		return tempTimeLineDeviceCommands;
	}
	public void setTempTimeLineDeviceCommands(Vector<TimeLineDeviceCommand> tempTimeLineDeviceCommands) {
		this.tempTimeLineDeviceCommands = tempTimeLineDeviceCommands;
	}
	
	private int delayChainToBeCompensated = 0;
	public int getDelayChainToBeCompensated() {
		return delayChainToBeCompensated;
	}
	public void setDelayChainToBeCompensated(int delayChainToBeCompensated) {
		this.delayChainToBeCompensated = delayChainToBeCompensated;
	}
	
	public Long getTimeCompensated(Long time) {
		if (getDelayChainToBeCompensated() > 0) {
			Long adjustedTime = time - getDelayChainToBeCompensated();
			if (adjustedTime < 0)
				return 0L;
			else
				return adjustedTime;
		} else
			return time;
	}
}
