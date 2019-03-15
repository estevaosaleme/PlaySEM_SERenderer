package br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.wifi;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererBroker;
import br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.ConnectivityBase;
import br.ufes.inf.lprm.utils.Utils;

public class WiFiOutput extends ConnectivityBase {	
	
	List<WiFiDeviceConnection> listWiFiDevices = new ArrayList<>();	
	int numberOfDevices = 0;
	
	@Override
	public boolean isConnected() {
		for (int i = 0; i<listWiFiDevices.size(); i++) {
			if (listWiFiDevices.get(i).isConnected())
				return true;
		}
		return false;
	}

	@Override
	public void openConnection() {
		if (!isConnected())
			try {
				listWiFiDevices.clear();
				if (getProperties().get("number-of-devices") != null) {
					numberOfDevices = Integer.parseInt(getProperties().get("number-of-devices"));
					for (int i = 1; i <= numberOfDevices; i++) {
						if (getProperties().get("device0"+i+"-address") != null) {	
							try {
								WiFiDeviceConnection wifiDevice = new WiFiDeviceConnection();
								String[] address = getProperties().get("device0"+i+"-address").split(":");		
								wifiDevice.setIpAddress(address[0]);
								wifiDevice.setPort(Integer.valueOf(address[1]));
								
								if (SERendererBroker.debugMode)
									System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Connecting to "+ wifiDevice.getIpAddress() + ":" + wifiDevice.getPort());
							
								Socket clientSocketDevice = new Socket(wifiDevice.getIpAddress(), wifiDevice.getPort());
								PrintWriter outDevice = new PrintWriter(clientSocketDevice.getOutputStream(), true);
							
								wifiDevice.setClientSocketDevice(clientSocketDevice);
								wifiDevice.setOutDevice(outDevice);

								wifiDevice.setConnected(true);
								listWiFiDevices.add(wifiDevice);
								setConnected(true);
								
							} catch (NumberFormatException | IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
		        if (isConnected())
		        	System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": WiFi connection has been opened.");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void closeConnection() {
		try {
			if (isConnected()) {
				for (int i = 0; i<listWiFiDevices.size(); i++) {
					if (listWiFiDevices.get(i).isConnected()) {
						if (SERendererBroker.debugMode)
							System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Closing connection with "+ listWiFiDevices.get(i).getIpAddress() + ":" + listWiFiDevices.get(i).getPort());
						
						listWiFiDevices.get(i).getOutDevice().close();
						listWiFiDevices.get(i).getClientSocketDevice().close();
					}
				}
			}
	        setConnected(false);
			System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": WiFi connection has been closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(byte[] message) {
		if (!isConnected())
 			openConnection();

    	try {
          	for (int i = 0; i<listWiFiDevices.size(); i++) {
      			if (listWiFiDevices.get(i).isConnected()) {
      				listWiFiDevices.get(i).getOutDevice().println(new String(message, StandardCharsets.UTF_8));
      				
      				if (SERendererBroker.debugMode)
      					System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": WiFi message '"+ messageToString(message) +"' sent to "+ listWiFiDevices.get(i).getIpAddress() + ":" + listWiFiDevices.get(i).getPort());
      			}
      		}
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
	}
	
	public String messageToString(byte[] bytes) {
        StringBuilder ret = new StringBuilder();
	    if (bytes != null && bytes.length > 0) {
            switch ((char) bytes[0]) {
                case 'L':
                    ret.append("LIGHT");
                    break;
                case 'V':
                    ret.append("VIBRATION");
                    break;
                case 'F':
                    ret.append("WIND");
                break;
                case 'S':
                    ret.append("SCENT");
                break;
                default:
                    break;
            }
        }
        return ret.toString();
    }
}
