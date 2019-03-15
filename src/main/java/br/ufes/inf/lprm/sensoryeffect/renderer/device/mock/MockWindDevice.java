package br.ufes.inf.lprm.sensoryeffect.renderer.device.mock;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.WindMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;

public class MockWindDevice extends SensoryEffectDeviceBase {

	@Override
	public Vector<TimeLineDeviceCommand> messageToDeviceCommand(SensoryEffectMessage sensoryEffectMessage) {
		Vector<TimeLineDeviceCommand> ret = new Vector<TimeLineDeviceCommand>();
		WindMessage windMessage = (WindMessage)sensoryEffectMessage;
		if (windMessage.getLocation() == null || windMessage.getLocation().equals("") || windMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)) {
			WindMessage windMessage1 = new WindMessage();
			windMessage1.setIntensity(windMessage.getIntensity());
			windMessage1.setOccurrenceTime(windMessage.getOccurrenceTime());
			windMessage1.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT);
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
		char command = 'F';
		char deviceIndex = 0;
		if (windMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT)){
			deviceIndex = 1;
		} 
		else if (windMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT)){
			deviceIndex = 2;
		}
		byte[] message = {(byte)command,(byte)deviceIndex,(byte)((char)windMessage.getIntensity().intValue()),(byte)'.'}; 
		return message;
	}
}
