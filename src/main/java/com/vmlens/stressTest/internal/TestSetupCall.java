package com.vmlens.stressTest.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import com.vmlens.stressTest.setup.TestSetup;

public class TestSetupCall implements Callable<Result> {

	private final TestSetup[] testSetupArray;
	private final Runnable[] testArray;
	
	
	
	
	public TestSetupCall(TestSetup[] testSetupArray) {
		super();
		this.testSetupArray = testSetupArray;
		testArray = new Runnable[200];
	}





	@Override
	public Result call() throws Exception {
		
		
	
		
		for(int i = 0 ; i < 200 ; i++)
		{
			testArray[i] = testSetupArray[i].createTest();
		}
		
		
		
		return new TestSetupResult(testArray);
	}

}
