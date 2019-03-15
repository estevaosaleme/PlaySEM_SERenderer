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
 * Copyright 2008, 2009, 2010, 2011, 2012 Alpen-Adria-Universitaet Klagenfurt, Markus Waltl.
 */
package br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv;

/**
 * Interface which defines variables for the SEM parsing and creation
 * @author Markus Waltl
 *
 */

/**
 * Modified by
 * 
 * @author Estevao Bissoli Saleme 
 * @version 2.0
 */	

public interface SEMDefinitions {
	public final static String TIMESCALE = "timeScale";
	public final static String PTS = "pts";
	public final static String ANCHORELEMENT = "anchorElement";
	
	public final static String TRUE = "true";
	public final static String FALSE = "false";
	
	// needed for storing
	public final static String ACTIVATE = "activate";
	public final static String DURATION = "duration";
	public final static String FADE = "fade";
	public final static String ALT = "alt";
	public final static String PRIORITY = "priority";
	public final static String INTENSITYVALUE = "intensity-value";
	public final static String INTENSITYRANGE = "intensity-range";
	public final static String LOCATION = "location";
	public final static String AUTOEXTRACTION = "autoExtraction";
	
	public final static String COLOR = "color";
	
	public final static String SCENT = "scent";
}
