package com.vmlens.stressTest.internal;

public class StopAtErrorCount implements  StopStrategy{
	
	private final int errorCountToReach;

	public StopAtErrorCount(int errorCountToReach) {
		super();
		this.errorCountToReach = errorCountToReach;
	}

	@Override
	public boolean stop(int errorCount, int iterations) {
		
		return errorCount >= errorCountToReach;
	}
	
	
	
	

}
