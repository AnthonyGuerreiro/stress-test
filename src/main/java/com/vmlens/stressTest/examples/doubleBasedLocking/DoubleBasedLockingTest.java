package com.vmlens.stressTest.examples.doubleBasedLocking;

import org.junit.Assert;


public class DoubleBasedLockingTest implements Runnable {
	
	private final DoubleBasedLocking doubleBasedLocking = new DoubleBasedLocking();
	
	
	





	@Override
	public void run() {

		Assert.assertEquals(Object.class , (Class<Object>) doubleBasedLocking.getDoubleBasedLocking()[0]  );
	
	}
	
	
	

}
