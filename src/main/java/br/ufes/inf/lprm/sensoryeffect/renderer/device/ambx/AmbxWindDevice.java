package br.ufes.inf.lprm.sensoryeffect.renderer.device.ambx;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.WindMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;

public class AmbxWindDevice extends SensoryEffectDeviceBase {

	@Override
	public Vector<TimeLineDeviceCommand> messageToDeviceCommand(SensoryEffectMessage sensoryEffectMessage) {
		Vector<TimeLineDeviceCommand> ret = new Vector<TimeLineDeviceCommand>();
		WindMessage windMessage = (WindMessage)sensoryEffectMessage;
		if (windMessage.getLocation() == null || windMessage.getLocation().equals("") || windMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)) {
			WindMessage windMessage1 = new WindMessage();
			windMessage1.setIntensity(windMessage.getIntensity());
			windMessage1.setOccurrenceTime(windMessage.getOccurrenceTime());
			windMessage1.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT);
			byte[] message1 = formatMessage(windMessage1);
			Long time1 = getTimeCompensated(windMessage1.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time1, message1));
			
			WindMessage windMessage2 = new WindMessage();
			windMessage2.setIntensity(windMessage.getIntensity());
			windMessage2.setOccurrenceTime(windMessage.getOccurrenceTime());
			windMessage2.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT);
			byte[] message2 = formatMessage(windMessage2);
			Long time2 = getTimeCompensated(windMessage2.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time2, message2));
		} else {
			byte[] message = formatMessage(windMessage);
			Long time = getTimeCompensated(windMessage.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time, message));
		}
		return ret;
	}

	@Override
	public void resetDevice() {
		WindMessage windMessage = new WindMessage();
		windMessage.setIntensity(0);
		windMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT);
		byte[] messageWind1 = formatMessage(windMessage);
		
		WindMessage windMessage2 = new WindMessage();
		windMessage2.setIntensity(0);
		windMessage2.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT);
		byte[] messageWind2 = formatMessage(windMessage2);
		
		this.getDeviceConnectivity().sendMessage(messageWind1);
		this.getDeviceConnectivity().sendMessage(messageWind2);
	}

	@Override
	public byte[] formatMessage(SensoryEffectMessage sensoryEffectMessage) {
		WindMessage windMessage = (WindMessage)sensoryEffectMessage;

		// fan p1, p2 (location), p3 (device type), p4 (intensity)
		byte p1 =  (byte)0xA1;
		byte deviceType = 0x01;
		
		final byte LEFT_FAN = 0x5B;
		final byte RIGHT_FAN = 0x6B;
			
		byte location = 0;
		if (windMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT)){
			location = LEFT_FAN;
		} 
		else if (windMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT)){
			location = RIGHT_FAN;
		}
		else if (windMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)){
			return new byte[] {0x076, LEFT_FAN, deviceType,(byte)((char)windMessage.getIntensity().intValue()), RIGHT_FAN, deviceType,(byte)((char)windMessage.getIntensity().intValue())};
		}

		byte[] message = {p1, location, deviceType,(byte)((char)windMessage.getIntensity().intValue())}; 
		return message;
	}
}
