package com.vmlens.stressTest.examples.doubleBasedLocking;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;


public class DoubleBasedLockingTest implements Runnable {
	
	DoubleBasedLocking doubleBasedLocking = new DoubleBasedLocking();
	


	@Override
	public void run() {
		assertThat (doubleBasedLocking.getDoubleBasedLocking().value , equalTo(6));
		
	}
	
	
	
	
	

}
