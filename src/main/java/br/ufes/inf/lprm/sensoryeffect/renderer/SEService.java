package br.ufes.inf.lprm.sensoryeffect.renderer;

public interface SEService {
	public void setSem(String sensoryEffectMetadata, String duration);
	
	public void setSemEvent(String sensoryEffectMetadata, String duration, int eventId);
	
	public void setPlay(String newCurrentTime);
	
	public void setPlayEvent(int eventId);
	
	public void setCleanEventList();
	
	public void setPause(String newCurrentTime);

	public void setStop();
		    
	public void setCurrentTime(String newCurrentTime);
	
	public void setLightColors(String newHexLeft, String newHexCenter, String newHexRight);
	
	public void setLocalCurrentTime(long newCurrentTime);
	
	public void setCapabilitiesMetadata(String capabilitiesMetadata);
	
	public void setLightAutoExraction(Boolean newLightAutoExtraction);
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue);
	
	public void setSemPrepared(Boolean value);
	
	public void setSemEventPrepared(int value);

}
