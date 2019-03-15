package br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv;

import java.awt.Color;
import java.util.HashMap;

/**
 * Class providing helper functions
 * 
 * @author Estevao Bissoli Saleme 
 * @version 1.0
 */	
public class Helper {
	
	public static HashMap<Long, Integer> obtainFadeFanOrVibration(boolean on, long curTime, double intensityScale255, double fade){
		HashMap<Long, Integer> ret = new HashMap<Long, Integer>();
		if (on){
			long fadeTimeFinal = (long)(curTime + (fade*1000));
			long diffTime = fadeTimeFinal - curTime;
			int steps = Math.round(diffTime/100);
			double stepIntensity = intensityScale255 / steps;
			long timeFadeIn = curTime;
			for (int i =0; i< steps;i++){
				int intensityFadeIn = (int)(stepIntensity * i);
				ret.put(timeFadeIn, intensityFadeIn);
				timeFadeIn += 100;
			}
			ret.put(timeFadeIn, (int)intensityScale255);
		} else {
			long fadeTimeInitial = (long)(curTime - (fade*1000));
			long diffTime = curTime - fadeTimeInitial;
			int steps = Math.round(diffTime/100);
			double stepIntensity = intensityScale255 / steps;
			double intensityFadeOut = intensityScale255;
			long timeFadeOut = fadeTimeInitial;
			for (int i =0; i< steps;i++){
				ret.put(timeFadeOut, (int)intensityFadeOut);
				timeFadeOut += 100;
				intensityFadeOut -= stepIntensity;
			}
			ret.put(timeFadeOut, 0);
		}
		return ret;
	}
	
	public static HashMap<Long, Color> obtainFadeLight(boolean on, long curTime, double intensityScale255, double fade, int r, int g, int b){
		HashMap<Long, Color> ret = new HashMap<Long, Color>();
		if (on){
			long fadeTimeFinal = (long)(curTime + (fade*1000));
			long diffTime = fadeTimeFinal - curTime;
			int steps = Math.round(diffTime/100);
			int stepIntensity = (int)(intensityScale255 / steps);
			long timeFadeIn = curTime;
			for (int i =0; i< steps;i++){
				double intensityFadeIn = stepIntensity * i;
				double percentIntensity = 100;
				if (intensityFadeIn > -1 && intensityFadeIn <= 255)
					percentIntensity = (intensityFadeIn / 255) * 100;
				Color color = Helper.adjustColor((int)percentIntensity, r, g, b);
				ret.put(timeFadeIn, color);
				timeFadeIn += 100;
			}
			double percentIntensity = 100;
			if (intensityScale255 > -1 && intensityScale255 <= 255)
				percentIntensity = (intensityScale255 / 255) * 100;
			Color color = Helper.adjustColor((int)percentIntensity, r, g, b);
			ret.put(timeFadeIn, color);
		} else {
			long fadeTimeInitial = (long)(curTime - (fade*1000));
			long diffTime = curTime - fadeTimeInitial;
			int steps = Math.round(diffTime/100);
			double stepIntensity = intensityScale255 / steps;
			double intensityFadeOut = intensityScale255;
			long timeFadeOut = fadeTimeInitial;
			for (int i =0; i< steps;i++){
				double percentIntensity = 100;
				if (intensityFadeOut > -1 && intensityFadeOut <= 255)
					percentIntensity = (intensityFadeOut / 255) * 100;
				Color color = Helper.adjustColor((int)percentIntensity, r, g, b);
				ret.put(timeFadeOut, color);
				timeFadeOut += 100;
				intensityFadeOut -= stepIntensity;
			}
			Color color = new Color(0,0,0);
			ret.put(timeFadeOut, color);
		}
		return ret;
	}
	
	public static int[] hexToRGB(String hex) {
		if (hex == null || hex.length() != 7)
			return new int[]{255, 255, 255};
		hex = hex.substring(1);
		int r = Integer.parseInt(hex.substring(0, 2), 16);
		int g = Integer.parseInt(hex.substring(2, 4), 16);
		int b = Integer.parseInt(hex.substring(4, 6), 16);
		return new int[]{r, g, b};
	}

	public static Color adjustColor(float percentIntensity, int r, int g, int b) {
		float percentRed = r/255.0f * percentIntensity;
		int redFinal = (int)((percentRed/100) * 255);
		float percentGreen = g/255.0f * percentIntensity;
		int greenFinal = (int)((percentGreen/100) * 255);
		float percentBlue = b/255.0f * percentIntensity;
		int blueFinal = (int)((percentBlue/100) * 255);
		return new Color(redFinal, greenFinal, blueFinal);
	}

	
}
