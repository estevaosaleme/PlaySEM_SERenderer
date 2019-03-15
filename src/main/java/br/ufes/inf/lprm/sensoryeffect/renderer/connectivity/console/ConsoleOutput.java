package br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.console;

import java.util.Calendar;

import br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.ConnectivityBase;
import br.ufes.inf.lprm.utils.Utils;

public class ConsoleOutput extends ConnectivityBase {

	@Override
	public void openConnection() {
		System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Console connection has been opened.");
		setConnected(true);
	}

	@Override
	public void closeConnection() {
		setConnected(false);
		System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Console connection has been closed.");
	}

	@Override
	public void sendMessage(byte[] message) {
		if (isConnected())
			System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": Console message has been sent. " + messageToString(message));
	}
	
	private String messageToString(byte[] bytes) {
		StringBuilder ret = new StringBuilder();
		switch ((char)bytes[0]) {
			case 'L':
				if (bytes[2] == 60)
					ret.append("LIGHT, location=everywhere" + ", red="+Utils.normalizeValue(bytes[3])+ ", green="+Utils.normalizeValue(bytes[4])+ ", blue="+Utils.normalizeValue(bytes[5]));
				else {
					switch (bytes[1]) {
						case 0:
							ret.append("LIGHT, location=left" + ", red="+Utils.normalizeValue(bytes[3])+ ", green="+Utils.normalizeValue(bytes[4])+ ", blue="+Utils.normalizeValue(bytes[5]));
							break;
						case 12:
							ret.append("LIGHT, location=center" + ", red="+Utils.normalizeValue(bytes[3])+ ", green="+Utils.normalizeValue(bytes[4])+ ", blue="+Utils.normalizeValue(bytes[5]));
							break;
						case 48:
							ret.append("LIGHT, location=right" + ", red="+Utils.normalizeValue(bytes[3])+ ", green="+Utils.normalizeValue(bytes[4])+ ", blue="+Utils.normalizeValue(bytes[5]));
							break;
						default:
							break;
						}
				}
				break;
			case 'V':
				ret.append("VIBRATION, location=" + (bytes[1] == 1 ? "left" : "right") +", intensity=" + Utils.normalizeValue(bytes[2]));
				break;
			case 'F':
				ret.append("WIND, location=" + (bytes[1] == 1 ? "left" : "right") +", intensity=" + Utils.normalizeValue(bytes[2]));
				break;
			case 'S':
				ret.append("SCENT, slot=" + Utils.normalizeValue(bytes[1]) +", intensity=" + Utils.normalizeValue(bytes[2]));
				break;
			default:
				break;
		}
	    return ret.toString();
	}
}
