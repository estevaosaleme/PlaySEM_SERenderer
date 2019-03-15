package br.ufes.inf.lprm.sensoryeffect.renderer.message;

public class VibrationMessage extends SensoryEffectMessageBase {
	private Integer intensity = 0;
	public Integer getIntensity() {
		return intensity;
	}
	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}
}
