package br.ufes.inf.lprm.sensoryeffect.renderer.connectivity;

import java.util.HashMap;

public abstract class ConnectivityBase implements Connectivity {
	private HashMap<String, String> properties = null;
	public HashMap<String, String> getProperties() {
		return properties;
	}
	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}
	
	private boolean isConnected = false;
	public boolean isConnected() {
		return isConnected;
	}
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
}
