package com.vmlens.stressTest.internal;

public interface StopStrategy {

	
	boolean stop(int errorCount, int iterations);
	
	
}
