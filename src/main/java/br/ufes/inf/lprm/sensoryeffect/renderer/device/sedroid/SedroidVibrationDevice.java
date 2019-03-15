package br.ufes.inf.lprm.sensoryeffect.renderer.device.sedroid;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.VibrationMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;

public class SedroidVibrationDevice extends SensoryEffectDeviceBase {

	@Override
	public Vector<TimeLineDeviceCommand> messageToDeviceCommand(SensoryEffectMessage sensoryEffectMessage) {
		Vector<TimeLineDeviceCommand> ret = new Vector<TimeLineDeviceCommand>();
		VibrationMessage vibrationMessage = (VibrationMessage)sensoryEffectMessage;
		byte[] message = formatMessage(vibrationMessage);
		Long time = getTimeCompensated(vibrationMessage.getOccurrenceTime());
		ret.add(new TimeLineDeviceCommand(time, message));
		return ret;
	}

	@Override
	public void resetDevice() {
		VibrationMessage vibrationMessage = new VibrationMessage();
		vibrationMessage.setIntensity(0);
		vibrationMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER);
		byte[] messageVib = formatMessage(vibrationMessage);
		this.getDeviceConnectivity().sendMessage(messageVib);
	}

	@Override
	public byte[] formatMessage(SensoryEffectMessage sensoryEffectMessage) {
		VibrationMessage vibrationMessage = (VibrationMessage)sensoryEffectMessage;
		char command = 'V';
		byte[] message = {(byte)command,(byte)((char)vibrationMessage.getIntensity().intValue())};
		return message;
	}
}
