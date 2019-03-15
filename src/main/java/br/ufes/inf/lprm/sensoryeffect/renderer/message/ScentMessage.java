package br.ufes.inf.lprm.sensoryeffect.renderer.message;

public class ScentMessage extends SensoryEffectMessageBase {
	private Integer intensity = 0;
	public Integer getIntensity() {
		return intensity;
	}
	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}
	
	private String scent = "";
	public String getScent() {
		return scent;
	}
	public void setScent(String scent) {
		this.scent = scent;
	}
}
