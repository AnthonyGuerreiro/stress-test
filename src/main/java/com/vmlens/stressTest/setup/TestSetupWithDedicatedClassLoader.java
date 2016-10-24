package com.vmlens.stressTest.setup;

public interface TestSetupWithDedicatedClassLoader {

	Runnable createTest(int iteration,int blockNumber);
	
	
}
