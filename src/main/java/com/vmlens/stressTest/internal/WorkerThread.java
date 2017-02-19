package com.vmlens.stressTest.internal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class WorkerThread extends Thread {

	
	 final ArrayBlockingQueue<Callable<Result>> in  = new ArrayBlockingQueue<Callable<Result>>(1);
	 final ArrayBlockingQueue<Result>           out = new ArrayBlockingQueue<Result>(1);
	
	
	
	@Override
	public void run() {

		try {
		
		while( true )
		{
			Callable<Result> task = in.take();
			Result result = task.call();
			out.put(result);
			
			
		}
		
		} catch (StopException e) {
			
			
		}
       catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
	}
	
	
	
	
}
