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
package br.ufes.inf.lprm.sensoryeffect.renderer.parser;

import java.awt.Color;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.Comparator;
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
import org.iso.mpeg.mpegv._2010.sev.VibrationType;
import org.iso.mpeg.mpegv._2010.sev.WindType;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererDevice;
import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererService;
import br.ufes.inf.lprm.sensoryeffect.renderer.serial.SerialMessage;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.TimeLineAction;

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
 * @version 1.1
 */	

public class SEMParser implements Runnable {
	private double curTime = -1;
	private long timeScale = 1;
	private Vector<EffectElement> effectElements = null;
	private EffectElement tmpElement = null;

	private Vector<TimeLineAction> timeLineActions = null;
	private String sensoryEffectMetadata = "";
	private long duration = -1;
	
	public SEMParser(String newSensoryEffectMetadata, long newDuration) {
		timeLineActions = new Vector<TimeLineAction>();
		effectElements = new Vector<EffectElement>();
		sensoryEffectMetadata = newSensoryEffectMetadata;
		duration = newDuration;
	}
	
	public static JAXBContext jaxbContext;
	
	@Override
	public void run() {
		try {
			parse(sensoryEffectMetadata);
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
		Comparator<TimeLineAction> comp = new Comparator<TimeLineAction>() {
		      public int compare(TimeLineAction ta1, TimeLineAction ta2) {
		        return ta1.getOccursTime().compareTo(ta2.getOccursTime());
		      }
		    };
		Collections.sort(timeLineActions, comp);
		if (SERendererDevice.debugMode){
			for (int i = 0; i < timeLineActions.size(); i++)
				System.out.println(timeLineActions.get(i).getOccursTime() + ": " + SerialMessage.messageToString(timeLineActions.get(i).getSerialMessage()));
			System.out.println("Ready for start...");
			System.out.println("---");
		}
		SERendererDevice.timeLine.setActions(timeLineActions, duration);
		SERendererService.semPrepared = true;
		try {
			Thread.sleep(100);
			SERendererService.getPropertyChangeSupport().firePropertyChange("SemPrepared", "", SERendererService.semPrepared);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
				SERendererService.setLightAutoExraction(true);
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
									tmpElement.getFade(), searchPriorElementActivated.getLocation(), searchPriorElementActivated.getR(), searchPriorElementActivated.getG(), searchPriorElementActivated.getB());
						}
						else
							prepareTimeLineActions(false, ebt, (long)(curTime * 1000), 0, 0, 0f, "", 0, 0, 0);
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
					prepareTimeLineActions(true, ebt, (long)(curTime * 1000), intensityScale255, tmpElement.getDuration(), tmpElement.getFade(), tmpElement.getLocation(), tmpElement.getR(), tmpElement.getG(), tmpElement.getB());
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
									tmpElement.getFade(), searchPriorElementActivated.getLocation(), searchPriorElementActivated.getR(), searchPriorElementActivated.getG(), searchPriorElementActivated.getB());
						}
						else
							prepareTimeLineActions(false, ebt, (long)(curTime * 1000), 0, 0, 0f, "", 0, 0, 0);
					}
				}
			}
		}
		tmpElement = null;
	}
	
	private void prepareTimeLineActions(boolean on, EffectBaseType ebt, long curTime, int intensityScale255, long duration, double fade, 
			String location, int r, int g, int b){
		if (on == false)
			curTime = curTime -1;
		if (ebt instanceof LightType) {
			if (fade > 0){
				HashMap<Long,Color> fadePairs = HelperFunctions.obtainFadeLight(on, curTime, intensityScale255, fade, r, g, b);	
				for (Long key : fadePairs.keySet()) {
					Color color = fadePairs.get(key);
					byte[] message = SerialMessage.prepareMessageToLed(location, (char)color.getRed(), (char)color.getGreen(), (char)color.getBlue());
					timeLineActions.add(new TimeLineAction(key, message));
				}
			}
			else {
				int percentIntensity = 100; // not adjust luminance
				if (intensityScale255 > -1 && intensityScale255 <= 255)
					percentIntensity = (int)(Double.valueOf(String.valueOf(intensityScale255)) / 255 * 100);
				Color color = HelperFunctions.adjustColor(percentIntensity, r, g, b);
				byte[] message = SerialMessage.prepareMessageToLed(location, (char)((int)color.getRed()), (char)color.getGreen(), (char)color.getBlue());
				timeLineActions.add(new TimeLineAction(curTime, message));
			}
			if (on && duration > 0){
				long timeDeactivate = curTime + (duration * 1000);
				byte[] message = SerialMessage.prepareMessageToLed(location, (char)0, (char)0, (char)0);
				timeLineActions.add(new TimeLineAction(timeDeactivate, message));
			}
		}
		else if (ebt instanceof WindType) {
			if (location.equals("") || location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)){
				if (fade > 0){
					HashMap<Long,Integer> fadePairs = HelperFunctions.obtainFadeFanOrVibration(on, curTime, intensityScale255, fade);
					for (Long key : fadePairs.keySet()) {
						byte[] message1 = SerialMessage.prepareMessageToFan(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT, (char)fadePairs.get(key).intValue());
						timeLineActions.add(new TimeLineAction(key, message1));
						byte[] message2 = SerialMessage.prepareMessageToFan(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT, (char)fadePairs.get(key).intValue());
						timeLineActions.add(new TimeLineAction(key, message2));
					}
				}
				else {
					byte[] message1 = SerialMessage.prepareMessageToFan(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT, (char)intensityScale255);
					timeLineActions.add(new TimeLineAction(curTime, message1));
					byte[] message2 = SerialMessage.prepareMessageToFan(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT, (char)intensityScale255);
					timeLineActions.add(new TimeLineAction(curTime, message2));
				}
				if (on && duration > 0){
					long timeDeactivate = curTime + (duration * 1000);
					byte[] message1 = SerialMessage.prepareMessageToFan(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT, (char)0);
					timeLineActions.add(new TimeLineAction(timeDeactivate, message1));
					byte[] message2 = SerialMessage.prepareMessageToFan(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT, (char)0);
					timeLineActions.add(new TimeLineAction(timeDeactivate, message2));
				}
			}
			else {
				if (fade > 0){
					HashMap<Long,Integer> fadePairs = HelperFunctions.obtainFadeFanOrVibration(on, curTime, intensityScale255, fade);
					for (Long key : fadePairs.keySet()) {
						byte[] message = SerialMessage.prepareMessageToFan(location, (char)fadePairs.get(key).intValue());
						timeLineActions.add(new TimeLineAction(key, message));
					}
				}
				else {
					byte[] message = SerialMessage.prepareMessageToFan(location, (char)intensityScale255);
					timeLineActions.add(new TimeLineAction(curTime, message));
				}
				if (on && duration > 0){
					long timeDeactivate = curTime + (duration * 1000);
					byte[] message = SerialMessage.prepareMessageToFan(location, (char)0);
					timeLineActions.add(new TimeLineAction(timeDeactivate, message));
				}
			}
		}
		else if (ebt instanceof VibrationType) {
			if (location.equals("") || location.startsWith(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_EVERYWHERE)){
				if (fade > 0){
					HashMap<Long,Integer> fadePairs = HelperFunctions.obtainFadeFanOrVibration(on, curTime, intensityScale255, fade);
					for (Long key : fadePairs.keySet()) {
						byte[] message1 = SerialMessage.prepareMessageToVibration(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT, (char)fadePairs.get(key).intValue());
						timeLineActions.add(new TimeLineAction(key, message1));
						byte[] message2 = SerialMessage.prepareMessageToVibration(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT, (char)fadePairs.get(key).intValue());
						timeLineActions.add(new TimeLineAction(key, message2));
					}
				}
				else {
					byte[] message1 = SerialMessage.prepareMessageToVibration(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT, (char)intensityScale255);
					timeLineActions.add(new TimeLineAction(curTime, message1));
					byte[] message2 = SerialMessage.prepareMessageToVibration(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT, (char)intensityScale255);
					timeLineActions.add(new TimeLineAction(curTime, message2));
				}
				if (on && duration > 0){
					long timeDeactivate = curTime + (duration * 1000);
					byte[] message1 = SerialMessage.prepareMessageToVibration(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_LEFT, (char)0);
					timeLineActions.add(new TimeLineAction(timeDeactivate, message1));
					byte[] message2 = SerialMessage.prepareMessageToVibration(ClassificationScheme.LOCATIONURIBASE +ClassificationScheme.LOC_X_RIGHT, (char)0);
					timeLineActions.add(new TimeLineAction(timeDeactivate, message2));
				}
			}
			else {
				if (fade > 0){
					HashMap<Long,Integer> fadePairs = HelperFunctions.obtainFadeFanOrVibration(on, curTime, intensityScale255, fade);
					for (Long key : fadePairs.keySet()) {
						byte[] message = SerialMessage.prepareMessageToVibration(location, (char)fadePairs.get(key).intValue());
						timeLineActions.add(new TimeLineAction(key, message));
					}
				}
				else {
					byte[] message = SerialMessage.prepareMessageToVibration(location, (char)intensityScale255);
					timeLineActions.add(new TimeLineAction(curTime, message));
				}
				if (on && duration > 0){
					long timeDeactivate = curTime + (duration * 1000);
					byte[] message = SerialMessage.prepareMessageToVibration(location, (char)0);
					timeLineActions.add(new TimeLineAction(timeDeactivate, message));
				}
			}
		}
	}
}