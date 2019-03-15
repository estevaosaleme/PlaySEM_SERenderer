package br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.serial;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.ConnectivityBase;
import br.ufes.inf.lprm.utils.Utils;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialOutput extends ConnectivityBase {

	private SerialPort serialPort;
	
	@Override
	public void openConnection() {
		if (!isConnected())
			try {
				String portName = getProperties().get("serialPort");
				int baudRate = Integer.parseInt(getProperties().get("baudRate"));
				
				CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
				if(portIdentifier.isCurrentlyOwned()) {
					setConnected(false);
			    } 
				else {
			    	int timeout = 2000;
			    	CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);
			 
			    	if(commPort instanceof SerialPort) {
			    		serialPort = (SerialPort)commPort;
			    		serialPort.setSerialPortParams(baudRate,
			                                        SerialPort.DATABITS_8,
			                                        SerialPort.STOPBITS_1,
			                                        SerialPort.PARITY_NONE );
			    		setConnected(true);
			    		System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Serial connection has been opened.");
			    	} 
			    	else {
			    		setConnected(false);
			    	}
				}
			}
			catch (UnsatisfiedLinkError e) {
				System.err.println("ERROR: RXTX was not configured properly");
			} catch (UnsupportedCommOperationException e) {
				System.err.println("ERROR: Unsupported operation.");
				e.printStackTrace();
			} catch (PortInUseException e) {
				System.err.println("ERROR: Port in use.");
			} catch (NoSuchPortException e) {
				System.err.println("ERROR: Port unavailable.");
			} catch (Exception e){
				e.printStackTrace();
			}
	}

	@Override
	public void closeConnection() {
		serialPort.close();
		setConnected(false);
		System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Serial connection has been closed.");
	}

	@Override
	public void sendMessage(byte[] message) {
		if (!isConnected())
			openConnection();
			
		OutputStream out;
		try {
			out = serialPort.getOutputStream();
			out.write(message);
			System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Serial message has been sent.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
