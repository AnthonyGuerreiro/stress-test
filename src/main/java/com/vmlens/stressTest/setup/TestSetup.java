package com.vmlens.stressTest.setup;

public interface TestSetup {
	
	
	
	void initialize()      throws Exception;
	Runnable createTest()  throws Exception;
	
	
	

}
