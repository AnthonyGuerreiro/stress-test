package com.vmlens.stressTest.examples.clone;

import com.vmlens.stressTest.setup.TestSetup;

/**
 * 
 * Creates a {@link com.vmlens.stressTest.examples.clone.CloneTest}.
 * 
 * 
 * @author thomas
 *
 */


public class CloneTestSetup implements TestSetup {

	@Override
	public Runnable createTest() throws Exception {
		
		return new CloneTest();
	}

}
