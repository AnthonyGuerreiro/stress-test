package com.vmlens.stressTest.internal.run;

import java.util.concurrent.CountDownLatch;

public class SyncThreads implements Runnable{

	
	private final CountDownLatch latch;
	
	
	
	
	public SyncThreads(CountDownLatch latch) {
		super();
		this.latch = latch;
	}




	@Override
	public void run() {
		
		latch.countDown();
		try{
			latch.await();	
		} catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		
	}

}
