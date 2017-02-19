package com.vmlens.stressTest.examples.doubleBasedLocking;


import com.vmlens.stressTest.setup.*;


public class DoubleBasedLockingTestSetup implements TestSetup  {

	
	
	
	@Override
	public Runnable createTest() {
		return  new DoubleBasedLockingTest();
		
		
	}

	@Override
	public void initialize() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
