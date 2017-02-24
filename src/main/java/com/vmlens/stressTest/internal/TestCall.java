package com.vmlens.stressTest.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class TestCall implements Callable<Result> {

	private final TestArray[] testArray;

	
	
	
	
	public TestCall(TestArray[] testArray) {
		super();
		
		this.testArray = testArray;
		
	}




	@Override
	public Result call() throws Exception {
	
		List<Throwable> exceptionList = new LinkedList<Throwable>();
		
		
		for(TestArray  array : testArray )
		{
			for(Runnable t : array.getTests())
			{
				try{
					t.run();
				}
				catch(Throwable throwable)
				{
					exceptionList.add(throwable);
				}
			}
		}
		
		
		
		
		return new TestResult(exceptionList);
		
	}

}
