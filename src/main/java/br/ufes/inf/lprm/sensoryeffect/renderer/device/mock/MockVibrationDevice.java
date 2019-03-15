package br.ufes.inf.lprm.sensoryeffect.renderer.device.mock;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.VibrationMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;

public class MockVibrationDevice extends SensoryEffectDeviceBase {

	@Override
	public Vector<TimeLineDeviceCommand> messageToDeviceCommand(SensoryEffectMessage sensoryEffectMessage) {
		Vector<TimeLineDeviceCommand> ret = new Vector<TimeLineDeviceCommand>();
		VibrationMessage vibrationMessage = (VibrationMessage)sensoryEffectMessage;
		if (vibrationMessage.getLocation() == null || vibrationMessage.getLocation().equals("") || vibrationMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)) {
			VibrationMessage vibrationMessage1 = new VibrationMessage();
			vibrationMessage1.setIntensity(vibrationMessage.getIntensity());
			vibrationMessage1.setOccurrenceTime(vibrationMessage.getOccurrenceTime());
			vibrationMessage1.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT);
			byte[] message1 = formatMessage(vibrationMessage1);
			Long time1 = getTimeCompensated(vibrationMessage1.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time1, message1));
			
			VibrationMessage vibrationMessage2 = new VibrationMessage();
			vibrationMessage2.setIntensity(vibrationMessage.getIntensity());
			vibrationMessage2.setOccurrenceTime(vibrationMessage.getOccurrenceTime());
			vibrationMessage2.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT);
			byte[] message2 = formatMessage(vibrationMessage2);
			Long time2 = getTimeCompensated(vibrationMessage2.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time2, message2));
		} else {
			byte[] message = formatMessage(vibrationMessage);
			Long time = getTimeCompensated(vibrationMessage.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time, message));
		}
		return ret;
	}

	@Override
	public void resetDevice() {
		VibrationMessage vibrationMessage = new VibrationMessage();
		vibrationMessage.setIntensity(0);
		vibrationMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT);
		byte[] messageVib1 = formatMessage(vibrationMessage);
		
		VibrationMessage vibrationMessage2 = new VibrationMessage();
		vibrationMessage2.setIntensity(0);
		vibrationMessage2.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT);
		byte[] messageVib2 = formatMessage(vibrationMessage2);
		
		this.getDeviceConnectivity().sendMessage(messageVib1);
		this.getDeviceConnectivity().sendMessage(messageVib2);
	}

	@Override
	public byte[] formatMessage(SensoryEffectMessage sensoryEffectMessage) {
		VibrationMessage vibrationMessage = (VibrationMessage)sensoryEffectMessage;
		char command = 'V';
		char deviceIndex = 0;
		if (vibrationMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT)){
			deviceIndex = 1;
		} 
		else if (vibrationMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT)){
			deviceIndex = 2;
		}
		byte[] message = {(byte)command,(byte)deviceIndex,(byte)((char)vibrationMessage.getIntensity().intValue()),(byte)'.'};
		return message;
	}
}
