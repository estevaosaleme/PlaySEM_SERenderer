/*
 * This file is part of SEVino.
 *
 * SEVino is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SEVino is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SEVino.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2008, 2009, 2010, 2011 Alpen-Adria-Universitaet Klagenfurt, Markus Waltl.
 */
package br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv;

import java.awt.Color;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.iso.mpeg.mpegv._2010.sedl.EffectBaseType;
import org.iso.mpeg.mpegv._2010.sedl.GroupOfEffectsType;
import org.iso.mpeg.mpegv._2010.sedl.SEM;
import org.iso.mpeg.mpegv._2010.sedl.SEMBaseType;
import org.iso.mpeg.mpegv._2010.sev.LightType;
import org.iso.mpeg.mpegv._2010.sev.ScentType;
import org.iso.mpeg.mpegv._2010.sev.VibrationType;
import org.iso.mpeg.mpegv._2010.sev.WindType;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererBroker;
import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.EventBasedData;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.LightMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.ScentMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.SensoryEffectMessageBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.VibrationMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.WindMessage;

/**
 * SEMParser
 * 
 * Class for parsing a sensory effects metadata descriptiony
 * 
 * @author Markus Waltl (Alpen-Adria-Universitaet Klagenfurt)
 * @version 1.0
 */

/**
 * Modified by
 * 
 * @author Estevao Bissoli Saleme 
 * @version 2.0
 */	

public class MPEGVSEMParser implements Runnable {
	private double curTime = -1;
	private long timeScale = 1;
	private Vector<EffectElement> effectElements = null;
	private EffectElement tmpElement = null;

	private Vector<LightMessage> lightMessages = new Vector<LightMessage>();
	private Vector<WindMessage> windMessages = new Vector<WindMessage>();
	private Vector<VibrationMessage> vibrationMessages = new Vector<VibrationMessage>();
	private Vector<ScentMessage> scentMessages = new Vector<ScentMessage>();

	private String sensoryEffectMetadata = "";
	private long duration = -1;
	
	private int eventId = 0;
	
	public MPEGVSEMParser(String newSensoryEffectMetadata, Long newDuration) {
		setDeviceMessages();	
		effectElements = new Vector<EffectElement>();
		sensoryEffectMetadata = newSensoryEffectMetadata;
		duration = newDuration;
	}
	
	public MPEGVSEMParser(String newSensoryEffectMetadata, Long newDuration, Integer newEventId) {
		setDeviceMessages();
		effectElements = new Vector<EffectElement>();
		sensoryEffectMetadata = newSensoryEffectMetadata;
		duration = newDuration;
		eventId = newEventId;
	}
	
	public static JAXBContext jaxbContext;
	
	private void setDeviceMessages() {
		SERendererBroker.lightDevice.setTimeLineSensoryEffectMessages(lightMessages);
	    SERendererBroker.windDevice.setTimeLineSensoryEffectMessages(windMessages);
	    SERendererBroker.vibrationDevice.setTimeLineSensoryEffectMessages(vibrationMessages);
	    SERendererBroker.scentDevice.setTimeLineSensoryEffectMessages(scentMessages);
	}
	
	private void printMessage(SensoryEffectMessageBase sensoryEffectMessage) {
		switch (sensoryEffectMessage.getClass().getSimpleName()) {
		case "LightMessage" :{
			LightMessage lightMessage = (LightMessage)sensoryEffectMessage;
			System.out.println(
					"Type: " + sensoryEffectMessage.getClass().getSimpleName() + "; " +
					"Time: " + lightMessage.getOccurrenceTime() + "; " +
					"Location: " + lightMessage.getLocation() + "; " +
					"RGB: " + lightMessage.getRed() + ", " + lightMessage.getGreen() + ", " +lightMessage.getBlue() + "; "
			);
		}
		break;
		case "VibrationMessage" :{
			VibrationMessage vibrationMessage = (VibrationMessage)sensoryEffectMessage;
			System.out.println(
					"Type: " + sensoryEffectMessage.getClass().getSimpleName() + "; " +
					"Time: " + vibrationMessage.getOccurrenceTime() + "; " +
					"Location: " + vibrationMessage.getLocation() + "; " +
					"Intensity: " + vibrationMessage.getIntensity() + "; "
			);
		}
		break;
		case "WindMessage" :{
			WindMessage windMessage = (WindMessage)sensoryEffectMessage;
			System.out.println(
					"Type: " + sensoryEffectMessage.getClass().getSimpleName() + "; " +
					"Time: " + windMessage.getOccurrenceTime() + "; " +
					"Location: " + windMessage.getLocation() + "; " +
					"Intensity: " + windMessage.getIntensity() + "; "
			);
		}
		break;
		case "ScentMessage" :{
			ScentMessage scentMessage = (ScentMessage)sensoryEffectMessage;
			System.out.println(
					"Type: " + sensoryEffectMessage.getClass().getSimpleName() + "; " +
					"Time: " + scentMessage.getOccurrenceTime() + "; " +
					"Location: " + scentMessage.getLocation() + "; " +
					"Scent: " + scentMessage.getScent() + "; " +
					"Intensity: " + scentMessage.getIntensity() + "; "
			);
		}
		break;
		default:
			break;
		}
	}
	
	@Override
	public void run() {
		try {
			parse(sensoryEffectMetadata);
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
		
		setDeviceMessages();
		
		if (SERendererBroker.debugMode){
			for (HashMap.Entry<String, SensoryEffectDeviceBase> entry : SERendererBroker.sensoryEffectDevices.entrySet()) {
				SensoryEffectDeviceBase sensoryEffectDeviceBase = entry.getValue();
				for (int i = 0; i < sensoryEffectDeviceBase.getTimeLineSensoryEffectMessages().size(); i++) {
					printMessage(sensoryEffectDeviceBase.getTimeLineSensoryEffectMessages().get(i));
				}
				System.out.println(sensoryEffectDeviceBase.getClass().getTypeName() + ": ready to start...");
				System.out.println("---");
			}
	    }
		  
		if (eventId != 0) {	
			HashMap<String, EventBasedData> eventList = new HashMap<String, EventBasedData>();
			for (HashMap.Entry<String, SensoryEffectDeviceBase> entry : SERendererBroker.sensoryEffectDevices.entrySet()) {
				EventBasedData eventBasedData = new EventBasedData();
				eventBasedData.setDeviceId(entry.getValue().getId());
				eventBasedData.setCommands((Vector<TimeLineDeviceCommand>) entry.getValue().getTimeLineDeviceCommands().clone());
				eventBasedData.setDuration(duration);
				eventList.put(eventBasedData.getDeviceId(), eventBasedData);
		    }
			SERendererBroker.service.getEventList().put(Integer.valueOf(eventId), eventList);
			SERendererBroker.service.setSemEventPrepared(eventId);
			try {
				Thread.sleep(100);
				SERendererBroker.service.firePropertyChange("SemEventIdPrepared", "", eventId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			for (HashMap.Entry<String, SensoryEffectDeviceBase> entry : SERendererBroker.sensoryEffectDevices.entrySet()) {
				SensoryEffectDeviceBase sensoryEffectDeviceBase = entry.getValue();
				sensoryEffectDeviceBase.setTempTimeLineDeviceCommands((Vector<TimeLineDeviceCommand>) sensoryEffectDeviceBase.getTimeLineDeviceCommands().clone());
				sensoryEffectDeviceBase.resetDevice();
		    }
			SERendererBroker.timeLine.getReady(duration);
			SERendererBroker.service.setSemPrepared(true);
			try {
				Thread.sleep(100);
				SERendererBroker.service.firePropertyChange("SemPrepared", "", true);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method for parsing a SEM description
	 * @param textOfSem
	 * @throws JAXBException - if an error occurred.
	 * @throws IOException - if an IO error occurred.
	 */
	private void parse(String textOfSem) throws JAXBException, IOException {
		
		try {
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(textOfSem);
			JAXBElement<SEM> jSem = unmarshaller.unmarshal(new StreamSource(reader), SEM.class);
			
			SEM sem = jSem.getValue();
			
			// extract time information
			Map<QName, String> semAttributes = sem.getOtherAttributes();
			if (semAttributes != null) {
				for (QName key : semAttributes.keySet()) {
					if (key.getLocalPart().equals(SEMDefinitions.TIMESCALE)) {
						timeScale = Long.valueOf(semAttributes.get(key));
					}
				}
			}

			// extract effects and groupofeffects
			List<SEMBaseType> semContent = sem.getDeclarationsOrGroupOfEffectsOrEffect();
			
			if (semContent != null) {
				for (int i = 0; i < semContent.size(); i++) {
					SEMBaseType sbt = semContent.get(i);
					
					if (sbt instanceof EffectBaseType) {
						handleEffect((EffectBaseType)sbt);						
						curTime = -1;
					}
					else if (sbt instanceof GroupOfEffectsType) {
						GroupOfEffectsType goe = (GroupOfEffectsType)sbt;
										
						Map<QName, String> goeAttributes = goe.getOtherAttributes();
						if (goeAttributes != null) {
							for (QName key : goeAttributes.keySet()) {
								if (key.getLocalPart().equals(SEMDefinitions.PTS)) {
									double lVal = Double.valueOf(goeAttributes.get(key));
									curTime = lVal/timeScale;
								}
								else if (key.getLocalPart().equals(SEMDefinitions.ANCHORELEMENT)) {
									// currently not needed
								}
							}
						}
								
						List<SEMBaseType> groupEffects = goe.getEffectOrReferenceEffect();
						if (groupEffects != null) {
							for (SEMBaseType goeSBT : groupEffects) {
								if (goeSBT instanceof EffectBaseType) {
									handleEffect((EffectBaseType)goeSBT);
								}
								// reference effect not supported (for now!)
							}
						}
						
						curTime = -1;
					}
				}
			}			
		} catch (JAXBException e) {
			throw new JAXBException("Parsing error: " + e.getMessage());
		} catch (Exception e) {
			throw new JAXBException("Unexpected error: " + e.getMessage());
		}		
	}
	
	/**
	 * Method for handling effects, e.g., extracting effect attributes
	 * @param ebt EffectBaseType with the effect to be handled
	 */
	private void handleEffect(EffectBaseType ebt) {
		tmpElement = new EffectElement();
		tmpElement.setEffectName(ebt.getClass().getSimpleName());
		
		Map<QName, String> effectAttributes = ebt.getOtherAttributes();
		
		if (effectAttributes != null) {
			for (QName key : effectAttributes.keySet()) {
				if (key.getLocalPart().equals(SEMDefinitions.PTS)) {
					double lVal = Double.valueOf(effectAttributes.get(key));
					curTime = lVal/timeScale;
				}
				else if (key.getLocalPart().equals(SEMDefinitions.ALT))
					tmpElement.setAlt(String.valueOf(effectAttributes.get(key)));
				else if (key.getLocalPart().equals(SEMDefinitions.DURATION)){
					duration = Long.parseLong(effectAttributes.get(key));
					tmpElement.setDuration(duration);
				}
				else if (key.getLocalPart().equals(SEMDefinitions.FADE)) {
					double time = Double.valueOf(effectAttributes.get(key))/timeScale;
					tmpElement.setFade(time);
				}
				else if (key.getLocalPart().equals(SEMDefinitions.LOCATION))
					tmpElement.setLocation(String.valueOf(effectAttributes.get(key)));
				else if (key.getLocalPart().equals(SEMDefinitions.PRIORITY))
					tmpElement.setPriority(Integer.parseInt(effectAttributes.get(key)));
				else if (key.getLocalPart().equals(SEMDefinitions.SCENT))
					tmpElement.setScent(String.valueOf(effectAttributes.get(key)));
				else if (key.getLocalPart().equals(SEMDefinitions.COLOR)) {
					Color rgbColor = ClassificationScheme.getRGBforColor(String.valueOf(effectAttributes.get(key)));
					tmpElement.setR(rgbColor.getRed());
					tmpElement.setG(rgbColor.getGreen());
					tmpElement.setB(rgbColor.getBlue());
				}
				else {
					String[] att = new String[2];
					att[0] = key.getNamespaceURI() + ":" + key.getLocalPart();
					att[1] = effectAttributes.get(key);
					tmpElement.addAttribute(att);
				}
			}
		}
		
		if (ebt.getAlt() != null && !ebt.getAlt().equals(""))
			tmpElement.setAlt(ebt.getAlt());
		
		if (ebt.getDuration() != null){
			duration = ebt.getDuration().longValue();
			tmpElement.setDuration(duration);
		}
		
		if (ebt.getFade() != null) {
			double time = ebt.getFade().doubleValue()/timeScale;
			tmpElement.setFade(time);
		}
		
		if (ebt.getLocation() != null && !ebt.getLocation().equals(""))
			tmpElement.setLocation(ebt.getLocation());
		
		if (ebt.getPriority() != null)
			tmpElement.setPriority(ebt.getPriority().intValue());
		
		// handle intensities
		if (ebt instanceof LightType) {			
			LightType effect = (LightType)ebt;
			if (ebt.getAutoExtraction() != null && ebt.getAutoExtraction().value() != null && (ebt.getAutoExtraction().value().equalsIgnoreCase("visual") || ebt.getAutoExtraction().value().equalsIgnoreCase("both"))){
				tmpElement.setAutoExtraction(true);
				SERendererBroker.service.setLightAutoExraction(true);
			}
			else {			
				if (effect.getIntensityRange() != null && effect.getIntensityRange().size() > 1){
					tmpElement.setMinIntensity(effect.getIntensityRange().get(0));
					tmpElement.setMaxIntensity(effect.getIntensityRange().get(1));
				}
				if (effect.getIntensityValue() != null && (tmpElement.getMinIntensity() <= effect.getIntensityValue() && tmpElement.getMaxIntensity() >= effect.getIntensityValue()))
					tmpElement.setIntensity(effect.getIntensityValue());
				if (effect.getColor() != null && !effect.getColor().isEmpty()) {
					Color rgbColor = ClassificationScheme.getRGBforColor(String.valueOf(effect.getColor().get(0)));
					tmpElement.setR(rgbColor.getRed());
					tmpElement.setG(rgbColor.getGreen());
					tmpElement.setB(rgbColor.getBlue());
				}
			}
		}
		else if (ebt instanceof WindType) {
			WindType effect = (WindType)ebt;
			if (effect.getIntensityRange() != null && effect.getIntensityRange().size() > 1){
				tmpElement.setMinIntensity(effect.getIntensityRange().get(0));
				tmpElement.setMaxIntensity(effect.getIntensityRange().get(1));
			}
			if (effect.getIntensityValue() != null && (tmpElement.getMinIntensity() <= effect.getIntensityValue() && tmpElement.getMaxIntensity() >= effect.getIntensityValue()))
				tmpElement.setIntensity(effect.getIntensityValue());
		}
		else if (ebt instanceof VibrationType) {
			VibrationType effect = (VibrationType)ebt;
			if (effect.getIntensityRange() != null && effect.getIntensityRange().size() > 1){
				tmpElement.setMinIntensity(effect.getIntensityRange().get(0));
				tmpElement.setMaxIntensity(effect.getIntensityRange().get(1));
			}
			if (effect.getIntensityValue() != null && (tmpElement.getMinIntensity() <= effect.getIntensityValue() && tmpElement.getMaxIntensity() >= effect.getIntensityValue()))
				tmpElement.setIntensity(effect.getIntensityValue());
		}
		else if (ebt instanceof ScentType) {
			ScentType effect = (ScentType)ebt;
			if (effect.getIntensityRange() != null && effect.getIntensityRange().size() > 1){
				tmpElement.setMinIntensity(effect.getIntensityRange().get(0));
				tmpElement.setMaxIntensity(effect.getIntensityRange().get(1));
			}
			if (effect.getIntensityValue() != null && (tmpElement.getMinIntensity() <= effect.getIntensityValue() && tmpElement.getMaxIntensity() >= effect.getIntensityValue()))
				tmpElement.setIntensity(effect.getIntensityValue());
			if (effect.getScent() != null && !effect.getScent().isEmpty()) {
				tmpElement.setScent(String.valueOf(effect.getScent()));
			}
		}
		
		if (ebt.isActivate() != null && ebt.isActivate()) {
			boolean found = false;
			for (int j = 0; j < effectElements.size() && !found; j++) {
				EffectElement ee = effectElements.elementAt(j);
				if (ee.getEffectName() != null && ee.getEffectName().equals(tmpElement.getEffectName())) {
					if (ee.getEndTime() == -1.0) {
						ee.setEndTime(curTime);
						found = true;
						tmpElement.setActivate(false);
						
						EffectElement searchPriorElementActivated = null;
						boolean priorElementActivated = false;
						for (int i = effectElements.size()-1; i >-1; i--){
							searchPriorElementActivated = effectElements.get(i);
							if (searchPriorElementActivated.getEffectName().equalsIgnoreCase(tmpElement.getEffectName()) && searchPriorElementActivated.isActivate()){
								priorElementActivated = true;
								break;
							}
						}
						if (priorElementActivated && tmpElement.getFade() > 0){
							float percentualIntensity = searchPriorElementActivated.getIntensity()/searchPriorElementActivated.getMaxIntensity();
							int intensityScale255 = Math.round((percentualIntensity * 255));
							if (!ebt.isActivate())
								prepareTimeLineActions(false, ebt, (long)(curTime * 1000), intensityScale255, searchPriorElementActivated.getDuration(), 
									tmpElement.getFade(), searchPriorElementActivated.getLocation(), searchPriorElementActivated.getR(), searchPriorElementActivated.getG(), searchPriorElementActivated.getB(), searchPriorElementActivated.getScent());
						}
						else
							prepareTimeLineActions(false, ebt, (long)(curTime * 1000), 0, 0, 0f, "", 0, 0, 0, "");
					}
				}
			}
			tmpElement.setActivate(true);
			tmpElement.setStartTime(curTime);
			effectElements.add(tmpElement);
			if (!tmpElement.isAutoExtraction()){
				if (tmpElement.getIntensity() > 0 && (tmpElement.getMinIntensity() <= tmpElement.getIntensity() && tmpElement.getMaxIntensity() >= tmpElement.getIntensity())){
					float percentualIntensity = tmpElement.getIntensity()/tmpElement.getMaxIntensity();
					int intensityScale255 = Math.round((percentualIntensity * 255));
					prepareTimeLineActions(true, ebt, (long)(curTime * 1000), intensityScale255, tmpElement.getDuration(), tmpElement.getFade(), tmpElement.getLocation(), tmpElement.getR(), tmpElement.getG(), tmpElement.getB(), tmpElement.getScent());
				}
			}
		}
		else {
			boolean found = false;
			for (int j = 0; j < effectElements.size() && !found; j++) {
				EffectElement ee = effectElements.elementAt(j);
				if (ee.getEffectName() != null && ee.getEffectName().equals(tmpElement.getEffectName())) {
					if (ee.getEndTime() == -1.0) {
						ee.setEndTime(curTime);
						found = true;
						tmpElement.setActivate(false);
						
						EffectElement searchPriorElementActivated = null;
						boolean priorElementActivated = false;
						for (int i = effectElements.size()-1; i >-1; i--){
							searchPriorElementActivated = effectElements.get(i);
							if (searchPriorElementActivated.getEffectName().equalsIgnoreCase(tmpElement.getEffectName()) && searchPriorElementActivated.isActivate()){
								priorElementActivated = true;
								break;
							}
						}
						if (priorElementActivated && tmpElement.getFade() > 0){
							float percentualIntensity = searchPriorElementActivated.getIntensity()/searchPriorElementActivated.getMaxIntensity();
							int intensityScale255 = Math.round((percentualIntensity * 255));
							prepareTimeLineActions(false, ebt, (long)(curTime * 1000), intensityScale255, searchPriorElementActivated.getDuration(), 
									tmpElement.getFade(), searchPriorElementActivated.getLocation(), searchPriorElementActivated.getR(), searchPriorElementActivated.getG(), searchPriorElementActivated.getB(), searchPriorElementActivated.getScent());
						}
						else
							prepareTimeLineActions(false, ebt, (long)(curTime * 1000), 0, 0, 0f, "", 0, 0, 0, "");
					}
				}
			}
		}
		tmpElement = null;
	}
	
	private void prepareTimeLineActions(boolean on, EffectBaseType ebt, long curTime, int intensityScale255, long duration, double fade, 
			String location, int r, int g, int b, String scent){
		if (on == false)
			curTime = curTime -1;
		if (ebt instanceof LightType) {
			if (fade > 0){
				HashMap<Long,Color> fadePairs = Helper.obtainFadeLight(on, curTime, intensityScale255, fade, r, g, b);	
				for (Long key : fadePairs.keySet()) {
					Color color = fadePairs.get(key);
					LightMessage lightMessage = new LightMessage();
					lightMessage.setOccurrenceTime(key);
					lightMessage.setLocation(location);
					lightMessage.setRed(color.getRed());
					lightMessage.setGreen(color.getGreen());
					lightMessage.setBlue(color.getBlue());
					lightMessages.addElement(lightMessage);
				}
			}
			else {
				int percentIntensity = 100; // not adjust luminance
				if (intensityScale255 > -1 && intensityScale255 <= 255)
					percentIntensity = (int)(Double.valueOf(String.valueOf(intensityScale255)) / 255 * 100);
				Color color = Helper.adjustColor(percentIntensity, r, g, b);
				LightMessage lightMessage = new LightMessage();
				lightMessage.setOccurrenceTime(curTime);
				lightMessage.setLocation(location);
				lightMessage.setRed(color.getRed());
				lightMessage.setGreen(color.getGreen());
				lightMessage.setBlue(color.getBlue());
				lightMessages.addElement(lightMessage);
			}
			if (on && duration > 0){
				long timeDeactivate = curTime + (duration * 1000);
				LightMessage lightMessage = new LightMessage();
				lightMessage.setOccurrenceTime(timeDeactivate);
				lightMessage.setLocation(location);
				lightMessage.setRed(0);
				lightMessage.setGreen(0);
				lightMessage.setBlue(0);
				lightMessages.addElement(lightMessage);
			}
		}
		else if (ebt instanceof WindType) {
			if (location == null || location.equals("") || location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)){
				if (fade > 0){
					HashMap<Long,Integer> fadePairs = Helper.obtainFadeFanOrVibration(on, curTime, intensityScale255, fade);
					for (Long key : fadePairs.keySet()) {
						WindMessage windMessage = new WindMessage();
						windMessage.setOccurrenceTime(key);
						windMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE);
						windMessage.setIntensity(fadePairs.get(key).intValue());
						windMessages.addElement(windMessage);
					}
				}
				else {
					WindMessage windMessage = new WindMessage();
					windMessage.setOccurrenceTime(curTime);
					windMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE);
					windMessage.setIntensity(intensityScale255);
					windMessages.addElement(windMessage);
				}
				if (on && duration > 0){
					long timeDeactivate = curTime + (duration * 1000);
					WindMessage windMessage = new WindMessage();
					windMessage.setOccurrenceTime(timeDeactivate);
					windMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE);
					windMessage.setIntensity(0);
					windMessages.addElement(windMessage);
				}
			}
			else {
				if (fade > 0){
					HashMap<Long,Integer> fadePairs = Helper.obtainFadeFanOrVibration(on, curTime, intensityScale255, fade);
					for (Long key : fadePairs.keySet()) {
						WindMessage windMessage = new WindMessage();
						windMessage.setOccurrenceTime(key);
						windMessage.setLocation(location);
						windMessage.setIntensity(fadePairs.get(key).intValue());
						windMessages.addElement(windMessage);
					}
				}
				else {
					WindMessage windMessage = new WindMessage();
					windMessage.setOccurrenceTime(curTime);
					windMessage.setLocation(location);
					windMessage.setIntensity(intensityScale255);
					windMessages.addElement(windMessage);
				}
				if (on && duration > 0){
					long timeDeactivate = curTime + (duration * 1000);
					WindMessage windMessage = new WindMessage();
					windMessage.setOccurrenceTime(timeDeactivate);
					windMessage.setLocation(location);
					windMessage.setIntensity(0);
					windMessages.addElement(windMessage);
				}
			}
		}
		else if (ebt instanceof VibrationType) {
			if (location == null || location.equals("") || location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)){
				if (fade > 0){
					HashMap<Long,Integer> fadePairs = Helper.obtainFadeFanOrVibration(on, curTime, intensityScale255, fade);
					for (Long key : fadePairs.keySet()) {
						VibrationMessage vibrationMessage = new VibrationMessage();
						vibrationMessage.setOccurrenceTime(key);
						vibrationMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE);
						vibrationMessage.setIntensity(fadePairs.get(key).intValue());
						vibrationMessages.addElement(vibrationMessage);
					}
				}
				else {
					VibrationMessage vibrationMessage = new VibrationMessage();
					vibrationMessage.setOccurrenceTime(curTime);
					vibrationMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE);
					vibrationMessage.setIntensity(intensityScale255);
					vibrationMessages.addElement(vibrationMessage);
				}
				if (on && duration > 0){
					long timeDeactivate = curTime + (duration * 1000);
					VibrationMessage vibrationMessage = new VibrationMessage();
					vibrationMessage.setOccurrenceTime(timeDeactivate);
					vibrationMessage.setLocation(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE);
					vibrationMessage.setIntensity(0);
					vibrationMessages.addElement(vibrationMessage);
				}
			}
			else {
				if (fade > 0){
					HashMap<Long,Integer> fadePairs = Helper.obtainFadeFanOrVibration(on, curTime, intensityScale255, fade);
					for (Long key : fadePairs.keySet()) {
						VibrationMessage vibrationMessage = new VibrationMessage();
						vibrationMessage.setOccurrenceTime(key);
						vibrationMessage.setLocation(location);
						vibrationMessage.setIntensity(fadePairs.get(key).intValue());
						vibrationMessages.addElement(vibrationMessage);
					}
				}
				else {
					VibrationMessage vibrationMessage = new VibrationMessage();
					vibrationMessage.setOccurrenceTime(curTime);
					vibrationMessage.setLocation(location);
					vibrationMessage.setIntensity(intensityScale255);
					vibrationMessages.addElement(vibrationMessage);
				}
				if (on && duration > 0){
					long timeDeactivate = curTime + (duration * 1000);
					VibrationMessage vibrationMessage = new VibrationMessage();
					vibrationMessage.setOccurrenceTime(timeDeactivate);
					vibrationMessage.setLocation(location);
					vibrationMessage.setIntensity(0);
					vibrationMessages.addElement(vibrationMessage);
				}
			}
		}
		else if (ebt instanceof ScentType) {
			ScentMessage scentMessage = new ScentMessage();
			scentMessage.setOccurrenceTime(curTime);
			scentMessage.setLocation(location);
			scentMessage.setIntensity(intensityScale255);
			scentMessage.setScent(scent);
			scentMessages.addElement(scentMessage);
			
			if (on && duration > 0){
				long timeDeactivate = curTime + (duration * 1000);
				ScentMessage scentMessage2 = new ScentMessage();
				scentMessage2.setOccurrenceTime(timeDeactivate);
				scentMessage2.setLocation(location);
				scentMessage2.setIntensity(0);
				scentMessage2.setScent(scent);
				scentMessages.addElement(scentMessage2);
			}
		}
	}
}