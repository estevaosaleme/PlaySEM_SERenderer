package br.ufes.inf.lprm.sensoryeffect.renderer.serial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.OutputStream;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererDevice;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.TimeLine;
 
public class SerialCom {
 
	private SerialPort serialPort;
	private boolean connected = false;
	public static String port = "COM3";
	public static boolean emulateDevices = true;
	
	public SerialCom(){
		if (!emulateDevices){
			connect(port);
		}
	}
	
	private void connect(String portName) {
		try {
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			if(portIdentifier.isCurrentlyOwned()) {
				connected = false;
		    } 
			else {
		    	int timeout = 2000;
		    	CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);
		 
		    	if(commPort instanceof SerialPort) {
		    		serialPort = ( SerialPort )commPort;
		    		serialPort.setSerialPortParams( 9600,
		                                        SerialPort.DATABITS_8,
		                                        SerialPort.STOPBITS_1,
		                                        SerialPort.PARITY_NONE );
		    		connected = true;
		    	} 
		    	else {
		    		connected = false;
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
  
  protected void sendMessage(byte[] message) throws Exception{
	  if (!emulateDevices){
		  if (connected){
			  OutputStream out = serialPort.getOutputStream();
		      out.write(message);
		  }
		  else {
			  connect(port);
			  OutputStream out = serialPort.getOutputStream();
		      out.write(message);
		  }
	  } 
	  if (SERendererDevice.debugMode)
		  System.out.println(TimeLine.getCurrentTime() + ": " + SerialMessage.messageToString(message));
  }
}