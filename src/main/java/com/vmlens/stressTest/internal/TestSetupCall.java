package com.vmlens.stressTest.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import com.vmlens.stressTest.setup.TestSetup;

public class TestSetupCall implements Callable<Result> {

	private final TestSetup[] testSetupArray;
	private final Runnable[] testArray;
	private final int test_per_run;
	
	
	
	public TestSetupCall(TestSetup[] testSetupArray, int test_per_run) {
		super();
		this.testSetupArray = testSetupArray;
		this.test_per_run = test_per_run;
		testArray = new Runnable[test_per_run];
	}





	@Override
	public Result call() throws Exception {
		
		
	
		
		for(int i = 0 ; i < test_per_run ; i++)
		{
			testArray[i] = testSetupArray[i].createTest();
		}
		
		
		
		return new TestSetupResult(testArray);
	}

}
