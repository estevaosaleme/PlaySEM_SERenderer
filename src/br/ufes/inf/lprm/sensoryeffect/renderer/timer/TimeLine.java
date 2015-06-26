package br.ufes.inf.lprm.sensoryeffect.renderer.timer;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererDevice;
import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererService;
import br.ufes.inf.lprm.sensoryeffect.renderer.serial.SerialMessage;

public class TimeLine extends java.util.TimerTask {

	public static Status status = Status.STOPPED;
	private static long currentTime = 0;
	private static long duration = 0;
	private final long DELAY_TO_FADE = 1000;
	private Vector<TimeLineAction> timeLineActions = null;
	private Vector<TimeLineAction> tempTimeLineActions = null;
	private Comparator<TimeLineAction> comp = new Comparator<TimeLineAction>() {
	      public int compare(TimeLineAction ta1, TimeLineAction ta2) {
	        return ta1.getOccursTime().compareTo(ta2.getOccursTime());
	      }
	    };
	
	@Override
	public void run() {
		if (status.getId() == Status.PLAYING.getId() && currentTime <= duration){
			currentTime += 1; 
			SERendererService.setLocalCurrentTime(currentTime);
			if (tempTimeLineActions != null){
				int indexOfAction = Collections.binarySearch(tempTimeLineActions, new TimeLineAction(currentTime,null), comp);
			    while (indexOfAction > -1){
			    	SerialMessage.sendMessage(tempTimeLineActions.get(indexOfAction).getSerialMessage());
			    	tempTimeLineActions.remove(indexOfAction);
					indexOfAction = Collections.binarySearch(tempTimeLineActions, new TimeLineAction(currentTime,null), comp);
			    }
			}
			if (SERendererDevice.debugMode)
				System.out.println(currentTime);
		}
	}
	
	public void stop(){
		status = Status.STOPPED;
		currentTime = 0;
		tempTimeLineActions.clear();
		tempTimeLineActions.addAll(timeLineActions);
		SerialMessage.resetDevices();
	}
	
	public void pause(long newCurrentTime){
		status = Status.PAUSED;
		if (SERendererDevice.debugMode)
			System.out.println(">>> SetPause on " + currentTime);
		currentTime = newCurrentTime;
		if (SERendererDevice.debugMode)
			System.out.println(">>> PAUSE on " + newCurrentTime);
	}
	
	public void play(long newCurrentTime){
		status = Status.PLAYING;
		if (SERendererDevice.debugMode)
			System.out.println(">>> SetPlay on " + currentTime);
		currentTime = newCurrentTime;
		if (SERendererDevice.debugMode)
			System.out.println(">>> PLAY on " + newCurrentTime);
	}
	
	public void setCurrentTime(long newCurrentTime){
		tempTimeLineActions.clear();
		tempTimeLineActions.addAll(timeLineActions);
		if (SERendererDevice.debugMode)
			System.out.println(">>> SetCurrentTime on " + currentTime);
		currentTime = newCurrentTime;
		if (SERendererDevice.debugMode)
			System.out.println(">>> CURRENT TIME " + newCurrentTime);
	}
	
	public static long getCurrentTime(){
		return currentTime;
	}
	
	public void setActions(Vector<TimeLineAction> timeLineActions, long duration){
		SerialMessage.resetDevices();
		this.timeLineActions = timeLineActions;
		tempTimeLineActions = new Vector<TimeLineAction>();
		tempTimeLineActions.clear();
		tempTimeLineActions.addAll(timeLineActions);
		TimeLine.duration = duration + DELAY_TO_FADE;
		if (SERendererDevice.debugMode){
			System.out.println("Duration (+DELAY): " + duration + " ("+DELAY_TO_FADE+")");
			System.out.println("---");
		}
	}
	
	public static long getDuration(){
		return duration;
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
