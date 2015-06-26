package br.ufes.inf.lprm.sensoryeffect.renderer.serial;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererDevice;
import br.ufes.inf.lprm.sensoryeffect.renderer.parser.ClassificationScheme;

public class SerialMessage {
	
	public static SerialCom serialCom = new SerialCom();

	@SuppressWarnings("unused")
	private void log(String text){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("SER_log.txt", true)))) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
			out.println(sdf.toString() + ": "+ text);
		}catch (IOException e) {
		}
	}
	
	public static String byteArrayToString(byte[] bytes) {
	    StringBuilder ret = new StringBuilder();
	    for(int i=0; i<bytes.length; i++) {
	    	int r = 0;
	    	if ((byte)bytes[i] < 0)
	    	    r = (byte)bytes[i] + 256;
	    	else
	    		r = (byte)bytes[i];
	    	ret.append((char)r+",");
	    }
	    return ret.toString();
	}
	
	private static int normalizeValue(byte data){
		if (data < 0)
		    return data + 256;
		else
			return data;
	}
	
	public static String messageToString(byte[] bytes) {
		StringBuilder ret = new StringBuilder();
		switch ((char)bytes[0]) {
			case 'L':
				if (bytes[2] == 60)
					ret.append("LIGHT, location=everywhere" + ", red="+normalizeValue(bytes[3])+ ", green="+normalizeValue(bytes[4])+ ", blue="+normalizeValue(bytes[5]));
				else {
					switch (bytes[1]) {
						case 0:
							ret.append("LIGHT, location=left" + ", red="+normalizeValue(bytes[3])+ ", green="+normalizeValue(bytes[4])+ ", blue="+normalizeValue(bytes[5]));
							break;
						case 12:
							ret.append("LIGHT, location=center" + ", red="+normalizeValue(bytes[3])+ ", green="+normalizeValue(bytes[4])+ ", blue="+normalizeValue(bytes[5]));
							break;
						case 48:
							ret.append("LIGHT, location=right" + ", red="+normalizeValue(bytes[3])+ ", green="+normalizeValue(bytes[4])+ ", blue="+normalizeValue(bytes[5]));
							break;
						default:
							break;
						}
				}
				break;
			case 'V':
				ret.append("VIBRATION, location=" + (bytes[1] == 1 ? "left" : "right") +", intensity=" + normalizeValue(bytes[2]));
				break;
			case 'F':
				ret.append("WIND, location=" + (bytes[1] == 1 ? "left" : "right") +", intensity=" + normalizeValue(bytes[2]));
				break;
			default:
				break;
		}
	    return ret.toString();
	}
	
	public static byte[] prepareMessageToLed(String location, char r, char g, char b){
		char command = 'L';
		char led = 1;
		char count = 1;
		if (location.startsWith(ClassificationScheme.LOCATIONURIBASE + ClassificationScheme.LOC_X_LEFT)){
			led = 0; count = 12;
		} 
		else if (location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER)){
			led = 12; count = 36;
		}
		else if (location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT)){
			led = 48; count = 12;
		}
		else if (location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)){
			led = 0; count = 60;
		} 
		else {
			led = 0; count = 60;
		}
		byte[] messageLed = {(byte)command,(byte)led,(byte)count,(byte)r,(byte)g,(byte)b,(byte)'.'}; 
//		System.out.println("Manual R"+(byte)r+", G"+(byte)g+", B"+ (byte)b);
		return messageLed;
	}
	
	public static byte[] prepareMessageToFan(String location, char intensity){
		char command = 'F';
		char deviceIndex = 0;
		if (location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT)){
			deviceIndex = 1;
		} 
		else if (location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT)){
			deviceIndex = 2;
		}
		byte[] messageFan = {(byte)command,(byte)deviceIndex,(byte)intensity,(byte)'.'}; 
		return messageFan;
	}
	
	public static byte[] prepareMessageToVibration(String location, char intensity){
		char command = 'V';
		char deviceIndex = 0;
		if (location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT)){
			deviceIndex = 1;
		} 
		else if (location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT)){
			deviceIndex = 2;
		}
		byte[] messageVib = {(byte)command,(byte)deviceIndex,(byte)intensity,(byte)'.'}; 
		return messageVib;
	}
	
	public static void resetLed(){
		byte[] messageLedLeft = prepareMessageToLed(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT, (char)0, (char)0, (char)0);
		byte[] messageLedCenter = prepareMessageToLed(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER, (char)0, (char)0, (char)0);
		byte[] messageLedRight = prepareMessageToLed(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT, (char)0, (char)0, (char)0);
		SerialMessage.sendMessage(messageLedLeft);
		SerialMessage.sendMessage(messageLedCenter);
		SerialMessage.sendMessage(messageLedRight);
	}
	
	public static void resetVib(){
		byte[] messageVib1 = prepareMessageToVibration(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT, (char)0);
		byte[] messageVib2 = prepareMessageToVibration(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT, (char)0);
		SerialMessage.sendMessage(messageVib1);
		SerialMessage.sendMessage(messageVib2);
	}
	
	public static void resetFan(){
		byte[] messageFan1 = prepareMessageToFan(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT, (char)0);
		byte[] messageFan2 = prepareMessageToFan(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT, (char)0);
		SerialMessage.sendMessage(messageFan1);
		SerialMessage.sendMessage(messageFan2);	
	}
	
	public static void resetDevices(){
		resetLed();
		resetVib();
		resetFan();	
		if (SERendererDevice.debugMode){
			System.out.println("Devices reseted");
			System.out.println("---");
		}
	}
	
	public static void sendMessage(byte[] message){
		try {
			serialCom.sendMessage(message);
			//log(byteArrayToString(messageLed));
			//System.out.println("Serial message: " + byteArrayToString(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
