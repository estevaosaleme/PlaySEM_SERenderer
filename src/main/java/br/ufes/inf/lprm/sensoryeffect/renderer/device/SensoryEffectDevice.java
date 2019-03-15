package br.ufes.inf.lprm.sensoryeffect.renderer.device;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;

public interface SensoryEffectDevice {
	public Vector<TimeLineDeviceCommand> messageToDeviceCommand(SensoryEffectMessage sensoryEffectMessage);
	public void resetDevice();
	public byte[] formatMessage(SensoryEffectMessage sensoryEffectMessage);
}
