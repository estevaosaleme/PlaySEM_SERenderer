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

import java.util.Vector;

/**
 * Class which represents a container for an effect element
 * 
 * @author Markus Waltl
 * @version 1.0
 */

/**
 * Modified by
 * 
 * @author Estevao Bissoli Saleme 
 * @version 2.0
 */	

public class EffectElement {
	private String effectName_ = "";
	private double startTime_ = -1.0;
	private double endTime_ = -1.0;
	private Vector<String[]> attributes_ = null;
	
	private float intensity = -1, minIntensity = -1, maxIntensity = -1;
	private boolean autoExtraction = false;
	private int priority = -1;
	private long duration = -1;
	private double fade = -1;
	private String alt, location, scent = "";
	private int r = 0;
	private int g = 0;
	private int b = 0;
	private boolean activate = false;
	
	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public float getMinIntensity() {
		return minIntensity;
	}

	public void setMinIntensity(float minIntensity) {
		this.minIntensity = minIntensity;
	}

	public float getMaxIntensity() {
		return maxIntensity;
	}

	public void setMaxIntensity(float maxIntensity) {
		this.maxIntensity = maxIntensity;
	}

	public boolean isAutoExtraction() {
		return autoExtraction;
	}

	public void setAutoExtraction(boolean autoExtraction) {
		this.autoExtraction = autoExtraction;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public double getFade() {
		return fade;
	}

	public void setFade(double fade) {
		this.fade = fade;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	/**
	 * Constructor
	 */
	public EffectElement() {
		attributes_ = new Vector<String[]>();
	}
	
	/**
	 * Method for retrieving the effect name
	 * @return String with the name
	 */
	public String getEffectName() {
		return effectName_;
	}
	
	/**
	 * Method for setting the effect name
	 * @param name String with the name
	 */
	public void setEffectName(String name) {
		effectName_ = name;
	}
		
	/**
	 * Method for setting the start time
	 * @param seconds double with the time
	 */
	public void setStartTime(double seconds) {
		startTime_ = seconds;
	}
	
	/**
	 * Method for getting the start time
	 * @return double with time
	 */
	public double getStartTime() {
		return startTime_;
	}
	
	/**
	 * Method for setting the end time
	 * @param seconds double with time
	 */
	public void setEndTime(double seconds) {
		endTime_ = seconds;
	}
	
	/**
	 * Method for getting the end time
	 * @return double with time
	 */
	public double getEndTime() {
		return endTime_;
	}
	
	/**
	 * Method for retrieving the attributes of the effect
	 * @return Vector<String[]> with the attributes
	 */
	public Vector<String[]> getAttributes() {
		return attributes_;
	}
	
	/**
	 * Method for adding an attribute
	 * @param attribute String[] with attribute (0 = attribute name, 1 = attribute value)
	 * @return true if added successfully else false
	 */
	public boolean addAttribute(String[] attribute) {
		if (attribute == null || attribute[0] == null || attribute[0].equals(""))
			return false;
		
		attributes_.add(attribute);
		return true;
	}
	
	/**
	 * Method for getting a string representation of the effect element
	 * @param String with representation
	 */
	public String toString() {
		String s = "Name: " + effectName_ + "\n";
		s += "Start time: " + startTime_ + "\n";
		s += "End time: " + endTime_ + "\n";
		s += "Attributes:\n";
		
		for (int i = 0; i < attributes_.size(); i++) {
			String[] att = attributes_.elementAt(i);
			
			s += att[0] + ": " + att[1] + " \n";
		}
		s += "\n";
		return s;
	}

	public boolean isActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	public String getScent() {
		return scent;
	}
	public void setScent(String scent) {
		this.scent = scent;
	}
}
