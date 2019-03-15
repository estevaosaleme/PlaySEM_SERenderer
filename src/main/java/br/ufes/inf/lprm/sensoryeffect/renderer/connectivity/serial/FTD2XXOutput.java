package br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.serial;

import java.util.Calendar;
import java.util.List;

import com.ftdi.BitModes;
import com.ftdi.FTD2XXException;
import com.ftdi.FTDevice;
import com.ftdi.Parity;
import com.ftdi.StopBits;
import com.ftdi.WordLength;

import br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.ConnectivityBase;
import br.ufes.inf.lprm.utils.Utils;

public class FTD2XXOutput extends ConnectivityBase {

	private FTDevice fTDevice01;
	
	@Override
	public void openConnection() {
		if (!isConnected())
			try {
				List<FTDevice> fTDevices = FTDevice.getDevices();
				for (FTDevice fTDevice : fTDevices) 
					if (getProperties().get("device01-id").equals(String.valueOf(fTDevice.getDevID()))) {
						fTDevice01 = fTDevice;
						fTDevice01.open();
						fTDevice01.setBaudRate(9600);
						fTDevice01.setDataCharacteristics(WordLength.BITS_8, StopBits.STOP_BITS_1, Parity.PARITY_NONE);
						fTDevice01.setTimeouts(1000, 1000);
						fTDevice01.setBitMode((byte)0xFF, BitModes.BITMODE_SYNC_BITBANG);
						
						setConnected(true);
			    		System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Serial/FTD2XX connection has been opened.");
					}
			} catch (FTD2XXException e) {
				setConnected(false);
				e.printStackTrace();
			}
	}


	@Override
	public void closeConnection() {
		try {
			fTDevice01.close();
			setConnected(false);
			System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Serial/FTD2XX connection has been closed.");
		} catch (FTD2XXException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(byte[] message) {
		if (!isConnected()) 
			openConnection();
		
		try {
			int value = 0;
			try {
				Byte b = message[0];
				value = b;
			}
			catch (Exception e) {
			}
			fTDevice01.resetDevice();
			fTDevice01.write(value);
			System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Serial/FTD2XX message has been sent.");
		} catch (FTD2XXException e) {
			e.printStackTrace();
		}
	}
}
