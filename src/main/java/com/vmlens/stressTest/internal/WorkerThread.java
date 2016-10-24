package com.vmlens.stressTest.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.mutable.MutableInt;

public class WorkerThread extends Thread {
	
	
	public final List<Runnable> taskList;
    private final boolean yieldAtBeginning;
	
    public int errorCount = 0;
    public Map<String,MutableInt> errorSet = new HashMap<String,MutableInt>();
   // public int yieldCount = 0;
	
	public WorkerThread(List<Runnable> taskList, boolean yieldAtBeginning) {
		super();
		this.taskList = taskList;
		this.yieldAtBeginning = yieldAtBeginning;
		this.setPriority(Thread.MAX_PRIORITY);
	}

	
	public static boolean callYield()
	{
		WorkerThread workerThread = (WorkerThread)	Thread.currentThread();
		
		return  workerThread.yieldAtBeginning;  
			
		
		
		
		
	}
	
	
    
	
	
	
	
	
	
	@Override
	public void run() {
		
		
		for(Runnable t : taskList)
		{
			
			try{
				
		    //yieldCount = 0;		
		    
				
			t.run();
			}
			catch(Throwable e)
			{
				//e.printStackTrace();
				
				
				StringWriter stringWriter =  new StringWriter();
				
				e.printStackTrace(new PrintWriter(stringWriter));
				
				String errorMessage = stringWriter.toString();
				
				MutableInt mutable = errorSet.get(errorMessage);
				
				
				if( mutable == null )
				{
					errorSet.put(errorMessage, new MutableInt(1));
				}
				else
				{
					mutable.increment();
				}
				
				
				//System.out.println(yieldCount);
				
				errorCount++;
			}
		}
		
		
	}
	
	
	
	

}
