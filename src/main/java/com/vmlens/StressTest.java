package com.vmlens;

import com.vmlens.stressTest.internal.StressTestImpl;


/**
 * 
 * Runs tests multiple times by multiple threads 
 * Run with -h option to see a help message.
 * 
 * @author thomas
 *
 */



public class StressTest {

	
	
	
	/**
	 * 
	 * Runs the test. Run with -h option to see a help message.
	 * 
	 * 
	 * @param args
	 * @throws Exception
	 */
	
	
	public static void main(String[] args) throws Exception {
		
		StressTestImpl stressTestImpl = new StressTestImpl();
		stressTestImpl.runTest(args);
	
	}

	
	
	
	
	
	
	
	
}
