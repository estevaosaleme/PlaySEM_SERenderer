package br.ufes.inf.lprm.sensoryeffect.renderer.device.mock;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.ScentMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;

public class MockScentDevice extends SensoryEffectDeviceBase {

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
		char command = 'S';
		int slotToActivate = (1 << 4) + (1 << 3) + (1 << 2) + (1 << 1);
		
		String scentSlot01 = this.getProperties().get("ScentSlot01");
		String scentSlot02 = this.getProperties().get("ScentSlot02");
		String scentSlot03 = this.getProperties().get("ScentSlot03");
		String scentSlot04 = this.getProperties().get("ScentSlot04");
		
		if (scentMessage.getScent().equals(scentSlot01))
			slotToActivate = (0 << 4) + (1 << 3) + (1 << 2) + (1 << 1);
		else if (scentMessage.getScent().equals(scentSlot02))
			slotToActivate = (1 << 4) + (0 << 3) + (1 << 2) + (1 << 1);
		else if (scentMessage.getScent().equals(scentSlot03))
			slotToActivate = (1 << 4) + (1 << 3) + (0 << 2) + (1 << 1);
		else if (scentMessage.getScent().equals(scentSlot04))
			slotToActivate = (1 << 4) + (1 << 3) + (1 << 2) + (0 << 1);
			
		byte[] message = {(byte)command,(byte)slotToActivate,(byte)((char)scentMessage.getIntensity().intValue())};
		return message;
	}
}
