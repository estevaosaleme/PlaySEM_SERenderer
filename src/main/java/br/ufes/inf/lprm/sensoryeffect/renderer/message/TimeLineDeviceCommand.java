package br.ufes.inf.lprm.sensoryeffect.renderer.message;

public class TimeLineDeviceCommand {
	
	public TimeLineDeviceCommand(long occurrenceTime, byte[] message){
		this.occurrenceTime = occurrenceTime;
		this.message = message;
	}
	
	private long occurrenceTime;
	public Long getOccurrenceTime() {
		return occurrenceTime;
	}
	public void setOccurrenceTime(long occursTime) {
		this.occurrenceTime = occursTime;
	}
	
	private byte[] message;
	public byte[] getMessage() {
		return message;
	}
	public void setMessage(byte[] message) {
		this.message = message;
	} 
}
