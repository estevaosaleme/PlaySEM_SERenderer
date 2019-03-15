package br.ufes.inf.lprm.sensoryeffect.renderer.device.bluno;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.ScentMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;

public class BlunoScentDevice extends SensoryEffectDeviceBase {

	@Override
	public Vector<TimeLineDeviceCommand> messageToDeviceCommand(SensoryEffectMessage sensoryEffectMessage) {
		Vector<TimeLineDeviceCommand> ret = new Vector<TimeLineDeviceCommand>();
		ScentMessage scentMessage = (ScentMessage)sensoryEffectMessage;
		byte[] message = formatMessage(scentMessage);
		Long time = getTimeCompensated(scentMessage.getOccurrenceTime());
		ret.add(new TimeLineDeviceCommand(time, message));
		return ret;
	}

	@Override
	public void resetDevice() {
		ScentMessage scentMessage = new ScentMessage();
		scentMessage.setIntensity(0);
		scentMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE);
		byte[] messageScent = formatMessage(scentMessage);
		this.getDeviceConnectivity().sendMessage(messageScent);
	}

	@Override
	public byte[] formatMessage(SensoryEffectMessage sensoryEffectMessage) {
		ScentMessage scentMessage = (ScentMessage)sensoryEffectMessage;
		char command = 'F';
		char deviceIndex = 0;
		byte[] message = {(byte)command,(byte)deviceIndex,(byte)((char)scentMessage.getIntensity().intValue()),(byte)'.'}; 
		return message;
	}
}
