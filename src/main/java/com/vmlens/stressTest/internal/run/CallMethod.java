package com.vmlens.stressTest.internal.run;

public class CallMethod implements Runnable {
	
	private final Runnable objectUnderTest;

	public CallMethod(Runnable objectUnderTest) {
		super();
		this.objectUnderTest = objectUnderTest;
	}

	public void run() {
		objectUnderTest.run();
		
	}
	
	
	

}
