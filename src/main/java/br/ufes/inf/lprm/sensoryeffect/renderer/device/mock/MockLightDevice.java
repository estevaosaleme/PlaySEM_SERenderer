package br.ufes.inf.lprm.sensoryeffect.renderer.device.mock;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.LightMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;

public class MockLightDevice extends SensoryEffectDeviceBase {

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
		LightMessage leftMessage = new LightMessage();
		leftMessage.setRed(0);
		leftMessage.setGreen(0);
		leftMessage.setBlue(0);
		leftMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT);
		byte[] messageLedLeft = formatMessage(leftMessage);
		
		LightMessage centerMessage = new LightMessage();
		centerMessage.setRed(0);
		centerMessage.setGreen(0);
		centerMessage.setBlue(0);
		centerMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER);
		byte[] messageLedCenter = formatMessage(centerMessage);
		
		LightMessage rightMessage = new LightMessage();
		rightMessage.setRed(0);
		rightMessage.setGreen(0);
		rightMessage.setBlue(0);
		rightMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT);
		byte[] messageLedRight = formatMessage(rightMessage);
		
		this.getDeviceConnectivity().sendMessage(messageLedLeft);
		this.getDeviceConnectivity().sendMessage(messageLedCenter);
		this.getDeviceConnectivity().sendMessage(messageLedRight);
	}

	@Override
	public byte[] formatMessage(SensoryEffectMessage sensoryEffectMessage) {
		LightMessage lightMessage = (LightMessage)sensoryEffectMessage;
		char command = 'L';
		char led = 0;
		char count = 60;
		
		if (lightMessage.getLocation() != null)
			if (lightMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE + ClassificationScheme.LOC_X_LEFT)){
				led = 0; count = 12;
			} 
			else if (lightMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER)){
				led = 12; count = 36;
			}
			else if (lightMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT)){
				led = 48; count = 12;
			}
			else if (lightMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)){
				led = 0; count = 60;
			} 

		byte[] message = {(byte)command,(byte)led,(byte)count,(byte)((char)lightMessage.getRed().intValue()),(byte)((char)lightMessage.getGreen().intValue()),(byte)((char)lightMessage.getBlue().intValue()),(byte)'.'}; 
		return message;
	}
}
