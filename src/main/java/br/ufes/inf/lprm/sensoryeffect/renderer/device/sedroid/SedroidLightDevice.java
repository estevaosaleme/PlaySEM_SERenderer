package br.ufes.inf.lprm.sensoryeffect.renderer.device.sedroid;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.LightMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;

public class SedroidLightDevice extends SensoryEffectDeviceBase {

	@Override
	public Vector<TimeLineDeviceCommand> messageToDeviceCommand(SensoryEffectMessage sensoryEffectMessage) {
		Vector<TimeLineDeviceCommand> ret = new Vector<TimeLineDeviceCommand>();
		LightMessage lightMessage = (LightMessage)sensoryEffectMessage;
		byte[] message = formatMessage(lightMessage);
		Long time = getTimeCompensated(lightMessage.getOccurrenceTime());
		ret.add(new TimeLineDeviceCommand(time, message));
		return ret;
	}

	@Override
	public void resetDevice() {
		LightMessage centerMessage = new LightMessage();
		centerMessage.setRed(0);
		centerMessage.setGreen(0);
		centerMessage.setBlue(0);
		centerMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER);
		byte[] messageLedCenter = formatMessage(centerMessage);
		this.getDeviceConnectivity().sendMessage(messageLedCenter);
	}

	@Override
	public byte[] formatMessage(SensoryEffectMessage sensoryEffectMessage) {
		LightMessage lightMessage = (LightMessage)sensoryEffectMessage;
		char command = 'L';
		byte[] message = {(byte)command,(byte)((char)lightMessage.getRed().intValue()),(byte)((char)lightMessage.getGreen().intValue()),(byte)((char)lightMessage.getBlue().intValue())}; 
		return message;
	}
}
