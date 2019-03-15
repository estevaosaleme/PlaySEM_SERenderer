package br.ufes.inf.lprm.sensoryeffect.renderer.message;

public abstract class SensoryEffectMessageBase implements SensoryEffectMessage {
	private String location = "";
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	private Long occurrenceTime;
	public Long getOccurrenceTime() {
		return occurrenceTime;
	}
	public void setOccurrenceTime(Long occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}
}
