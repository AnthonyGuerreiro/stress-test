package com.vmlens.stressTest.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class TestCall implements Callable<Result> {

	private final Runnable[] firstTestArray;
	private final Runnable[] secondTestArray;
	
	
	
	
	public TestCall(Runnable[] firstTestArray, Runnable[] secondTestArray) {
		super();
		this.firstTestArray = firstTestArray;
		this.secondTestArray = secondTestArray;
	}




	@Override
	public Result call() throws Exception {
	
		List<Throwable> exceptionList = new LinkedList<Throwable>();
		
		for(Runnable t : firstTestArray)
		{
			try{
				t.run();
			}
			catch(Throwable throwable)
			{
				exceptionList.add(throwable);
			}
			
			
		}
		
		
		for(Runnable t : secondTestArray)
		{
			try{
				t.run();
			}
			catch(Throwable throwable)
			{
				exceptionList.add(throwable);
			}
		}
		
		
		return new TestResult(exceptionList);
		
	}

}
