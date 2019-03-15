package br.ufes.inf.lprm.sensoryeffect.renderer.message;

public class LightMessage extends SensoryEffectMessageBase {
	private Integer red;
	public Integer getRed() {
		return red;
	}
	public void setRed(Integer red) {
		this.red = red;
	}
	
	private Integer green;
	public Integer getGreen() {
		return green;
	}
	public void setGreen(Integer green) {
		this.green = green;
	}
	
	private Integer blue;
	public Integer getBlue() {
		return blue;
	}
	public void setBlue(Integer blue) {
		this.blue = blue;
	}
}
