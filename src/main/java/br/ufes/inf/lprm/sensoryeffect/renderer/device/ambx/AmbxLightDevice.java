package br.ufes.inf.lprm.sensoryeffect.renderer.device.ambx;

import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererBroker;
import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.LightMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.ClassificationScheme;

public class AmbxLightDevice extends SensoryEffectDeviceBase {

	@Override
	public Vector<TimeLineDeviceCommand> messageToDeviceCommand(SensoryEffectMessage sensoryEffectMessage) {
		Vector<TimeLineDeviceCommand> ret = new Vector<TimeLineDeviceCommand>();
		LightMessage lightMessage = (LightMessage)sensoryEffectMessage;
		if (lightMessage.getLocation() == null || lightMessage.getLocation().equals("") || lightMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)) {
			LightMessage lightMessageLeft = new LightMessage();
			lightMessageLeft.setRed(lightMessage.getRed());
			lightMessageLeft.setGreen(lightMessage.getGreen());
			lightMessageLeft.setBlue(lightMessage.getBlue());
			lightMessageLeft.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT);
			lightMessageLeft.setOccurrenceTime(lightMessage.getOccurrenceTime());
			byte[] messageLeft = SERendererBroker.lightDevice.formatMessage(lightMessageLeft);
			Long time1 = getTimeCompensated(lightMessageLeft.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time1, messageLeft));
			
			LightMessage lightMessageCenter = new LightMessage();
			lightMessageCenter.setRed(lightMessage.getRed());
			lightMessageCenter.setGreen(lightMessage.getGreen());
			lightMessageCenter.setBlue(lightMessage.getBlue());
			lightMessageCenter.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER);
			lightMessageCenter.setOccurrenceTime(lightMessage.getOccurrenceTime());
			byte[] messageCenter = SERendererBroker.lightDevice.formatMessage(lightMessageCenter);
			Long time2 = getTimeCompensated(lightMessageCenter.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time2, messageCenter));
			
			LightMessage lightMessageCenterLeft = new LightMessage();
			lightMessageCenterLeft.setRed(lightMessage.getRed());
			lightMessageCenterLeft.setGreen(lightMessage.getGreen());
			lightMessageCenterLeft.setBlue(lightMessage.getBlue());
			lightMessageCenterLeft.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTERLEFT);
			lightMessageCenterLeft.setOccurrenceTime(lightMessage.getOccurrenceTime());
			byte[] messageCenterLeft = SERendererBroker.lightDevice.formatMessage(lightMessageCenter);
			Long time2L = getTimeCompensated(lightMessageCenterLeft.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time2L, messageCenterLeft));
			
			LightMessage lightMessageCenterRight = new LightMessage();
			lightMessageCenterRight.setRed(lightMessage.getRed());
			lightMessageCenterRight.setGreen(lightMessage.getGreen());
			lightMessageCenterRight.setBlue(lightMessage.getBlue());
			lightMessageCenterRight.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTERRIGHT);
			lightMessageCenterRight.setOccurrenceTime(lightMessage.getOccurrenceTime());
			byte[] messageCenterRight = SERendererBroker.lightDevice.formatMessage(lightMessageCenter);
			Long time2R = getTimeCompensated(lightMessageCenterRight.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time2R, messageCenterRight));

			LightMessage lightMessageRight = new LightMessage();
			lightMessageRight.setRed(lightMessage.getRed());
			lightMessageRight.setGreen(lightMessage.getGreen());
			lightMessageRight.setBlue(lightMessage.getBlue());
			lightMessageRight.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT);
			lightMessageRight.setOccurrenceTime(lightMessage.getOccurrenceTime());
			byte[] messageRight = SERendererBroker.lightDevice.formatMessage(lightMessageRight);
			Long time3 = getTimeCompensated(lightMessageRight.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time3, messageRight));
		} 
		else if (lightMessage.getLocation() != null || !lightMessage.getLocation().equals("") && lightMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER)) {
			LightMessage lightMessageCenter = new LightMessage();
			lightMessageCenter.setRed(lightMessage.getRed());
			lightMessageCenter.setGreen(lightMessage.getGreen());
			lightMessageCenter.setBlue(lightMessage.getBlue());
			lightMessageCenter.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER);
			lightMessageCenter.setOccurrenceTime(lightMessage.getOccurrenceTime());
			byte[] messageCenter = SERendererBroker.lightDevice.formatMessage(lightMessageCenter);
			Long time2 = getTimeCompensated(lightMessageCenter.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time2, messageCenter));
			
			LightMessage lightMessageCenterLeft = new LightMessage();
			lightMessageCenterLeft.setRed(lightMessage.getRed());
			lightMessageCenterLeft.setGreen(lightMessage.getGreen());
			lightMessageCenterLeft.setBlue(lightMessage.getBlue());
			lightMessageCenterLeft.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTERLEFT);
			lightMessageCenterLeft.setOccurrenceTime(lightMessage.getOccurrenceTime());
			byte[] messageCenterLeft = SERendererBroker.lightDevice.formatMessage(lightMessageCenter);
			Long time2L = getTimeCompensated(lightMessageCenterLeft.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time2L, messageCenterLeft));
			
			LightMessage lightMessageCenterRight = new LightMessage();
			lightMessageCenterRight.setRed(lightMessage.getRed());
			lightMessageCenterRight.setGreen(lightMessage.getGreen());
			lightMessageCenterRight.setBlue(lightMessage.getBlue());
			lightMessageCenterRight.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTERRIGHT);
			lightMessageCenterRight.setOccurrenceTime(lightMessage.getOccurrenceTime());
			byte[] messageCenterRight = SERendererBroker.lightDevice.formatMessage(lightMessageCenter);
			Long time2R = getTimeCompensated(lightMessageCenterRight.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time2R, messageCenterRight));
		} 
		else {
			byte[] message = formatMessage(lightMessage);
			Long time = getTimeCompensated(lightMessage.getOccurrenceTime());
			ret.add(new TimeLineDeviceCommand(time, message));
		}
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
		
		LightMessage centerMessageLeft = new LightMessage();
		centerMessageLeft.setRed(0);
		centerMessageLeft.setGreen(0);
		centerMessageLeft.setBlue(0);
		centerMessageLeft.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTERLEFT);
		byte[] messageLedCenterLeft = formatMessage(centerMessageLeft);
		
		LightMessage centerMessageRight = new LightMessage();
		centerMessageRight.setRed(0);
		centerMessageRight.setGreen(0);
		centerMessageRight.setBlue(0);
		centerMessageRight.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTERRIGHT);
		byte[] messageLedCenterRight = formatMessage(centerMessageRight);
		
		LightMessage rightMessage = new LightMessage();
		rightMessage.setRed(0);
		rightMessage.setGreen(0);
		rightMessage.setBlue(0);
		rightMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT);
		byte[] messageLedRight = formatMessage(rightMessage);
		
		this.getDeviceConnectivity().sendMessage(messageLedLeft);
		this.getDeviceConnectivity().sendMessage(messageLedCenter);
		this.getDeviceConnectivity().sendMessage(messageLedCenterLeft);
		this.getDeviceConnectivity().sendMessage(messageLedCenterRight);
		this.getDeviceConnectivity().sendMessage(messageLedRight);
	}

	@Override
	public byte[] formatMessage(SensoryEffectMessage sensoryEffectMessage) {
		LightMessage lightMessage = (LightMessage)sensoryEffectMessage;

		final byte LEFT_SP_LIGHT = 0x0B;
		final byte RIGHT_SP_LIGHT = 0x1B;
		final byte LEFT_WW_LIGHT = 0x2B;
		final byte CENTER_WW_LIGHT = 0x3B;
		final byte RIGHT_WW_LIGHT = 0x4B;
		
		byte location = 0;
		
		if (lightMessage.getLocation() != null)
			if (lightMessage.getLocation().startsWith(ClassificationScheme.LOCATIONURIBASE + ClassificationScheme.LOC_X_LEFT)){
				location = LEFT_SP_LIGHT;
			} 
			else if (lightMessage.getLocation().endsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTER)){
				location = CENTER_WW_LIGHT;
			}
			else if (lightMessage.getLocation().endsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTERLEFT)){
				location = LEFT_WW_LIGHT;
			}
			else if (lightMessage.getLocation().endsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_CENTERRIGHT)){
				location = RIGHT_WW_LIGHT;
			}
			else if (lightMessage.getLocation().endsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT)){
				location = RIGHT_SP_LIGHT;
			}
			else if (lightMessage.getLocation().endsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)){
				location = CENTER_WW_LIGHT;
			} 

		// light p1, p2 (location), p3 (device type), p4 (red), p5 (green), p6 (blue)
		byte p1 =  (byte)0xA1;
		byte deviceType = 0x03;
		
		byte[] message = {p1, location, deviceType,(byte)((char)lightMessage.getRed().intValue()),(byte)((char)lightMessage.getGreen().intValue()),(byte)((char)lightMessage.getBlue().intValue())}; 
		return message;
	}
}
