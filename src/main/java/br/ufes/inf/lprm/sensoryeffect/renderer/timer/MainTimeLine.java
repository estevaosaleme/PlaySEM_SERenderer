package br.ufes.inf.lprm.sensoryeffect.renderer.timer;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererBroker;
import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.message.TimeLineDeviceCommand;

public class MainTimeLine extends java.util.TimerTask {

	private static Status status = Status.STOPPED;
	private static long currentTime = 0;
	private static long duration = 0;
	private final long DELAY_TO_FADE = 1000;
	
	private Comparator<TimeLineDeviceCommand> comp = new Comparator<TimeLineDeviceCommand>() {
	      public int compare(TimeLineDeviceCommand ta1, TimeLineDeviceCommand ta2) {
	        return ta1.getOccurrenceTime().compareTo(ta2.getOccurrenceTime());
	      }
	    };
	
	@Override
	public void run() {
		if (status.getId() == Status.PLAYING.getId() && currentTime <= duration){
			currentTime += 1;
			SERendererBroker.service.setLocalCurrentTime(currentTime);
	
			for (HashMap.Entry<String,SensoryEffectDeviceBase> entry : SERendererBroker.sensoryEffectDevices.entrySet()) {
				if (entry.getValue().getTempTimeLineDeviceCommands() != null){
					int indexOfAction = Collections.binarySearch(entry.getValue().getTempTimeLineDeviceCommands(), new TimeLineDeviceCommand(currentTime,null), comp);
					while (indexOfAction > -1){
				    	entry.getValue().getDeviceConnectivity().sendMessage(entry.getValue().getTempTimeLineDeviceCommands().get(indexOfAction).getMessage());
				    	entry.getValue().getTempTimeLineDeviceCommands().remove(indexOfAction);
				    	indexOfAction = Collections.binarySearch(entry.getValue().getTempTimeLineDeviceCommands(), new TimeLineDeviceCommand(currentTime,null), comp);
				    }
				}
			}
			
			if (SERendererBroker.debugMode)
				System.out.println(currentTime);
		}
	}
	
	public void stop(){
		status = Status.STOPPED;
		currentTime = 0;
		for (HashMap.Entry<String, SensoryEffectDeviceBase> entry : SERendererBroker.sensoryEffectDevices.entrySet()) {
			SensoryEffectDeviceBase sensoryEffectDeviceBase = entry.getValue();
			sensoryEffectDeviceBase.setTempTimeLineDeviceCommands((Vector<TimeLineDeviceCommand>) sensoryEffectDeviceBase.getTimeLineDeviceCommands().clone());
			sensoryEffectDeviceBase.resetDevice();
	    }
	}
	
	public void pause(long newCurrentTime){
		status = Status.PAUSED;
		if (SERendererBroker.debugMode)
			System.out.println(">>> SetPause at " + currentTime);
		currentTime = newCurrentTime;
		if (SERendererBroker.debugMode)
			System.out.println(">>> PAUSE at " + newCurrentTime);
	}
	
	public void play(long newCurrentTime){
		status = Status.PLAYING;
		if (SERendererBroker.debugMode)
			System.out.println(">>> SetPlay at " + currentTime);
		currentTime = newCurrentTime;
		if (SERendererBroker.debugMode)
			System.out.println(">>> PLAY at " + newCurrentTime);
	}
	
	public void setCurrentTime(long newCurrentTime){
		for (HashMap.Entry<String, SensoryEffectDeviceBase> entry : SERendererBroker.sensoryEffectDevices.entrySet()) {
			SensoryEffectDeviceBase sensoryEffectDeviceBase = entry.getValue();
			sensoryEffectDeviceBase.setTempTimeLineDeviceCommands((Vector<TimeLineDeviceCommand>) sensoryEffectDeviceBase.getTimeLineDeviceCommands().clone());
	    }
		if (SERendererBroker.debugMode)
			System.out.println(">>> SetCurrentTime at " + currentTime);
		currentTime = newCurrentTime;
		if (SERendererBroker.debugMode)
			System.out.println(">>> CURRENT TIME " + newCurrentTime);
	}
	
	public static long getCurrentTime(){
		return currentTime;
	}
	
	public void getReady(long duration){
		MainTimeLine.duration = duration + DELAY_TO_FADE;
		if (SERendererBroker.debugMode){
			System.out.println("Duration (+DELAY): " + duration + " ("+DELAY_TO_FADE+")");
			System.out.println("---");
		}
	}
	
	public static long getDuration(){
		return duration;
	}
	
	public static Status getStatus() {
		return status;
	}

	public static void setStatus(Status status) {
		MainTimeLine.status = status;
	}
	
	public enum Status{
		PAUSED(2),
		STOPPED(0),
		PLAYING(1);
		Status(int id) {
			this.id = id;
		}
		private int id;
		public int getId() {
			return id;
		}
	}
}
