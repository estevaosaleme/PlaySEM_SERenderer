package br.ufes.inf.lprm.sensoryeffect.renderer.device.exhalia;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.ScentMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;

public class ExhaliaScentDevice extends SensoryEffectDeviceBase {

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
		byte slotToActivate = 0x00;
		
		String scentSlot01 = this.getProperties().get("ScentSlot01");
		String scentSlot02 = this.getProperties().get("ScentSlot02");
		String scentSlot03 = this.getProperties().get("ScentSlot03");
		String scentSlot04 = this.getProperties().get("ScentSlot04");
		
		boolean increasedIntensity = Boolean.valueOf(this.getProperties().get("increasedIntensity"));
		
		// to have two or more fans turned on at the same time, we must add the values, 
		// for instance: 1 + 2 = 3, then two devices will be turned on
		
		if (!increasedIntensity) {
			if (scentMessage.getScent().equals(scentSlot01))
				slotToActivate = 0x01;
			else if (scentMessage.getScent().equals(scentSlot02))
				slotToActivate = 0x02;
			else if (scentMessage.getScent().equals(scentSlot03))
				slotToActivate = 0x04;
			else if (scentMessage.getScent().equals(scentSlot04))
				slotToActivate = 0x08;
		}
		else {
			if (scentMessage.getIntensity() > 0 && scentMessage.getIntensity() <= 64)
				slotToActivate = 0x01;
			else if (scentMessage.getIntensity() > 64 && scentMessage.getIntensity() <= 128)
				slotToActivate = 0x01 + 0x02;
			else if (scentMessage.getIntensity() > 128)
				slotToActivate = 0x01 + 0x02 + 0x04 + 0x08;
		}
			
		byte[] message = new byte[] { 0x00, slotToActivate, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		return message;
	}
}
