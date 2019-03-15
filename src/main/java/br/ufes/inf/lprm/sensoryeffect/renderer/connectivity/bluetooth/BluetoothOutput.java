package br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.bluetooth;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererBroker;
import br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.ConnectivityBase;
import br.ufes.inf.lprm.utils.Utils;

public class BluetoothOutput extends ConnectivityBase {

	List<BluetoothDeviceConnection> listBluetoothDevices = new ArrayList<>();	
	int numberOfDevices = 0;
	
	@Override
	public boolean isConnected() {
		for (int i = 0; i<listBluetoothDevices.size(); i++) {
			if (listBluetoothDevices.get(i).isConnected())
				return true;
		}
		return false;
	}
	
	@Override
	public void openConnection() {
		if (!isConnected())
			try {
				listBluetoothDevices.clear();
				if (getProperties().get("number-of-devices") != null) {
					numberOfDevices = Integer.parseInt(getProperties().get("number-of-devices"));
					for (int i = 1; i <= numberOfDevices; i++) {
						if (getProperties().get("device0"+i+"-connection-url") != null) {	
							try {
								BluetoothDeviceConnection bluetoothDevice = new BluetoothDeviceConnection();
								bluetoothDevice.setUrlDevice(getProperties().get("device0"+i+"-connection-url"));
								
								if (SERendererBroker.debugMode)
									System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Connecting to "+ bluetoothDevice.getUrlDevice());
							
								StreamConnection connDevice = (StreamConnection) Connector.open(bluetoothDevice.getUrlDevice());
								DataOutputStream doutDevice = new DataOutputStream(connDevice.openOutputStream());
							
								bluetoothDevice.setConnDevice(connDevice);
								bluetoothDevice.setDoutDevice(doutDevice);
								bluetoothDevice.setConnected(true);
								listBluetoothDevices.add(bluetoothDevice);
								setConnected(true);
								
							} catch (NumberFormatException | IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
		        if (isConnected())
		        	System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Bluetooth connection has been opened.");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void closeConnection() {
		try {
			if (isConnected()) {
				for (int i = 0; i<listBluetoothDevices.size(); i++) {
					if (listBluetoothDevices.get(i).isConnected()) {
						if (SERendererBroker.debugMode)
							System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Closing connection with "+ listBluetoothDevices.get(i).getUrlDevice());
						
						listBluetoothDevices.get(i).getDoutDevice().close();
						listBluetoothDevices.get(i).getConnDevice().close();
					}
				}
			}
	        setConnected(false);
			System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Bluetooth connection has been closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(byte[] message) {
		if (!isConnected())
			openConnection();
		
        try {
        	
        	for (int i = 0; i<listBluetoothDevices.size(); i++) {
    			if (listBluetoothDevices.get(i).isConnected()) {
    				listBluetoothDevices.get(i).getDoutDevice().writeUTF(new String(message, StandardCharsets.UTF_8));
    				listBluetoothDevices.get(i).getDoutDevice().flush();
    				
    				if (SERendererBroker.debugMode)
    					System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Bluetooth message '"+ message +"' sent to "+ listBluetoothDevices.get(i).getUrlDevice());
    			}
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
