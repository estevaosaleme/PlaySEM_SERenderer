package br.ufes.inf.lprm.sensoryeffect.renderer.timer;

public class TimeLineAction {
	
	public TimeLineAction(long occursTime, byte[] serialMessage){
		this.occursTime = occursTime;
		this.serialMessage = serialMessage;
	}
	
	private long occursTime;
	private byte[] serialMessage;
	
	public Long getOccursTime() {
		return occursTime;
	}
	public void setOccursTime(long occursTime) {
		this.occursTime = occursTime;
	}
	public byte[] getSerialMessage() {
		return serialMessage;
	}
	public void setSerialMessage(byte[] serialMessage) {
		this.serialMessage = serialMessage;
	} 
}
