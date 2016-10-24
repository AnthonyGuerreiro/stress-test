package com.vmlens.stressTest.examples.doubleBasedLocking;

public class DoubleBasedLocking {

	
	private  Result instance;

	
	
	
	
	public  Result getDoubleBasedLocking()
	{
//		while(  WorkerThread.callYield() && instance == null )
//		{
//			//((WorkerThread)Thread.currentThread()).yieldCount++;
//			
//			Thread.yield();
//		}
		
//		for(int i = 0 ; i < 80 ; i++)
//		{
//			Thread.yield();
//		}
		
		
//		if( WorkerThread.callYield()  )
//		{
//			for(int i = 0 ; i < 10 ; i++)
//				{
//					Thread.yield();
//				}
//		}
		
		
		if( instance == null )
		{
			synchronized(this)
			{
				
				if( instance == null )
				{
					
					Result temp = new Result();
					
					temp.value = 1;

			//		Thread.yield();
					
					temp.value = 23;
					
//					for(int i = 0 ; i < 20 ; i++)
//					{
//						Thread.yield();
//					}
					
				//	Thread.yield();
					
					
					temp.value = 6;
					
					instance = temp;
					
					
					
				}
				
				
				
			}
			
			
			
			
			
		}
		
		
		return instance;
		
	}
	
	
	
	
}
