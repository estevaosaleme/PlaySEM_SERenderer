package br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.wifi;

import java.io.PrintWriter;
import java.net.Socket;

public class WiFiDeviceConnection {

	private Socket clientSocketDevice;
    private PrintWriter outDevice;
    private String ipAddress = "";
    private int port = 8080;
	private boolean connected = false;
	
	public Socket getClientSocketDevice() {
		return clientSocketDevice;
	}
	public void setClientSocketDevice(Socket clientSocketDevice) {
		this.clientSocketDevice = clientSocketDevice;
	}
	public PrintWriter getOutDevice() {
		return outDevice;
	}
	public void setOutDevice(PrintWriter outDevice) {
		this.outDevice = outDevice;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}
