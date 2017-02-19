package com.vmlens.stressTest.internal;

public class TestSetupResult  implements Result {
	
	private final Runnable[] tests;

	public TestSetupResult(Runnable[] tests) {
		super();
		this.tests = tests;
	}

	public Runnable[] getTests() {
		return tests;
	}

	
	
	

}
