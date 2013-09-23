package edu.uncc.mad.despicablemehunt.utils;

public class TimeHelper {

	private long startTime;
	private boolean timerStarted;
	private double elapsedTime;
	
	public TimeHelper() {
		resetCounters();
	}
	
	private void resetCounters(){
		startTime = 0;
		elapsedTime = 0.0d;
		timerStarted = false;
	}
	
	public void startTimer(boolean forceStart){
		
		if(!forceStart){
			if(timerStarted)
				return;
			
			startTime = System.currentTimeMillis();
			timerStarted = true;
			
			return;
			
		}
		
		startTime = System.currentTimeMillis();
		timerStarted = true;
		
	}
	
	public void startTimer(){
		startTimer(false);
	}
		
	public double stopTimer() throws Exception{
		if(!timerStarted)
			throw new Exception("time not started. ");
		
		elapsedTime = (System.currentTimeMillis() - startTime)/1000.0;
		double retVal = elapsedTime;
		resetCounters();
		return retVal;
	}
}