package br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.bluetooth;

import java.io.DataOutputStream;

import javax.microedition.io.StreamConnection;

public class BluetoothDeviceConnection {
	private DataOutputStream doutDevice;
	private StreamConnection connDevice;
	private String urlDevice = "";
	private boolean connected = false;
	
	public DataOutputStream getDoutDevice() {
		return doutDevice;
	}
	public void setDoutDevice(DataOutputStream doutDevice) {
		this.doutDevice = doutDevice;
	}
	public StreamConnection getConnDevice() {
		return connDevice;
	}
	public void setConnDevice(StreamConnection connDevice) {
		this.connDevice = connDevice;
	}
	public String getUrlDevice() {
		return urlDevice;
	}
	public void setUrlDevice(String urlDevice) {
		this.urlDevice = urlDevice;
	}
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}
