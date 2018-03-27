package br.ufes.inf.lprm.sensoryeffect.renderer.timer;

public class TimeLineAction {
	
	public TimeLineAction(long occursTime, byte[] serialMessage){
		this.occurrenceTime = occursTime;
		this.serialMessage = serialMessage;
	}
	
	private long occurrenceTime;
	private byte[] serialMessage;
	
	public Long getOccurrenceTime() {
		return occurrenceTime;
	}
	public void setOccurrenceTime(long occursTime) {
		this.occurrenceTime = occursTime;
	}
	public byte[] getSerialMessage() {
		return serialMessage;
	}
	public void setSerialMessage(byte[] serialMessage) {
		this.serialMessage = serialMessage;
	} 
}
