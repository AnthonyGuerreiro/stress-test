package com.vmlens.stressTest.internal;

public class StopAtIterationCount implements  StopStrategy {

	
	private final int iterationCountToReach;

	
	
	

	public StopAtIterationCount(int iterationCountToReach) {
		super();
		this.iterationCountToReach = iterationCountToReach;
	}





	@Override
	public boolean stop(int errorCount, int iterations) {
		
		return iterations >= iterationCountToReach;
	}
	
	
}
