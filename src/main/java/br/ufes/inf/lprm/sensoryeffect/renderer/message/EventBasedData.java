package br.ufes.inf.lprm.sensoryeffect.renderer.message;

import java.util.Vector;

public class EventBasedData {
	
	private String deviceId = "";

	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	private Vector<TimeLineDeviceCommand> commands = new Vector<TimeLineDeviceCommand>();
	public Vector<TimeLineDeviceCommand> getCommands() {
		return commands;
	}
	public void setCommands(Vector<TimeLineDeviceCommand> commands) {
		this.commands = commands;
	}
	
	private long duration = 0;
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
}
