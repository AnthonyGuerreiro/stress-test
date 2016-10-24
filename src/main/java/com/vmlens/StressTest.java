package com.vmlens;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.mutable.MutableInt;

import com.vmlens.stressTest.examples.doubleBasedLocking.DoubleBasedLockingTest;
import com.vmlens.stressTest.internal.WorkerThread;
import com.vmlens.stressTest.internal.run.CallMethod;
import com.vmlens.stressTest.internal.run.SyncThreads;
import com.vmlens.stressTest.setup.StressTestClassLoader;

public class StressTest {

	
	
	private static int getOrDefault(CommandLine line , Option option , int defaultValue)
	{
		
		if( line.hasOption(option.getOpt()) )
		{
			 return Integer.parseInt( line.getOptionValue(option.getOpt())); 
		}
		else
		{
			return defaultValue;
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		
		
		Options options = new Options(
				);

		
		//  runs     iterations     threads
		
		Option threadCountOption   = Option.builder("t").hasArg().argName("threadCount").build();
		Option blockSizeOption   = Option.builder("b").hasArg().argName("iterations").build();
		Option timeInSecondsOption   = Option.builder("s").hasArg().argName("seconds").build();
		Option printResultEveryXsecondsOption   = Option.builder("p").hasArg().argName("seconds").build();
		Option reloadClassesOption =  Option.builder("r").build();
	    Option tillErrorCountOption = 	Option.builder("e").hasArg().argName("count").build();
	    Option testsPerIterationOption = 	Option.builder("ti").hasArg().argName("count").build();
	    
	    
		options.addOption( threadCountOption );
		options.addOption( blockSizeOption );
		options.addOption( timeInSecondsOption );
		options.addOption( printResultEveryXsecondsOption );
		options.addOption( reloadClassesOption );
		options.addOption( tillErrorCountOption );
		options.addOption( testsPerIterationOption );
		
	   CommandLineParser parser = new DefaultParser();
	   CommandLine line = parser.parse(options, args);
	
	   boolean reloadClasses = line.hasOption(reloadClassesOption.getOpt());
	   
	   
	    int threadCount  =   getOrDefault(line , threadCountOption , 16);
		int blockSize =   getOrDefault(line , blockSizeOption , 100);
		int timeInSeconds =  getOrDefault(line , timeInSecondsOption , 60);
		int printResultEveryXseconds = getOrDefault(line , printResultEveryXsecondsOption , 10);
		
		int tillErrorCount = getOrDefault(line , tillErrorCountOption , -1);
		int testsPerIteration = 
				getOrDefault(line , testsPerIterationOption , 1);
		
		 currentStartTime = System.currentTimeMillis();
		
	
		long completeStartTime =  System.currentTimeMillis();
		
		
		while(true)
		{
                 if( tillErrorCount < 0 &&  (System.currentTimeMillis() - completeStartTime) > ( timeInSeconds * 1000 ) )
                 {
                	 break;
                 } 
                 else
                 {
                	 if( tillErrorCount <= completeErrorCount )
                	 {
                		 break;
                	 }
                 }
			
                 
                 
			
			
	             runIteration(threadCount , blockSize , printResultEveryXseconds,reloadClasses,testsPerIteration);
		}
		
		
	
		for( Map.Entry<String, MutableInt>  t : errorSet.entrySet()  )
		{
			System.out.println(t.getKey());
			System.out.println("has count " + t.getValue());
			
			
		}
		
		
		long durationInSeconds = (System.currentTimeMillis() - completeStartTime) / 1000;
		
		int througput = (int)(compleIterationCount / durationInSeconds);	
	System.out.println( " running for " + ( (System.currentTimeMillis() - completeStartTime) / 1000  ) +" errors "  + completeErrorCount + " iterations " + compleIterationCount + " througput "  + througput);
		
	
	
	
	}

	
	
	private static int completeErrorCount = 0;
	private static Map<String,MutableInt> errorSet = new HashMap<String,MutableInt>();
	private static int compleIterationCount = 0;
	private static int currentIterationCount = 0;
	private static long currentStartTime;
	
	
	
	private static void runIteration(int threadCount,int blockSize , int printResultEveryXseconds, boolean reloadClasses , int testsPerIteration ) throws Exception
	{
		List<WorkerThread> workerThreadList = new LinkedList<WorkerThread>();
		
		for(int testRun = 0 ;  testRun < testsPerIteration ; testRun++ )
		{
			
		
		
		for(int t = 0 ; t < threadCount; t++)
		{
			workerThreadList.add(new WorkerThread(new LinkedList<Runnable>() , t > 0));
			
			
		}
			
		for(int i = 0 ; i < blockSize ; i++)
		{
			
			compleIterationCount++;
			currentIterationCount++;
			
			
			Runnable runnable;
			
			if( reloadClasses )
			{
				ClassLoader classLoader = StressTestClassLoader.create();
				Class<Runnable> cl = (Class<Runnable>) classLoader.loadClass("com.vmlens.stress.test.DoubleBasedLockingTest");
				
				 runnable = cl.newInstance();
			}
			else
			{
				runnable =  new DoubleBasedLockingTest();
			}
			
			
			
			
			//DoubleBasedLockingTest doubleBasedLockingTest = new DoubleBasedLockingTest();
			
			
			for( WorkerThread workerThread  :  workerThreadList)
			{
				workerThread.taskList.add(new CallMethod(runnable));
			}
			
			
			
		}
		}
		
		
		for(   WorkerThread workerThread  :  workerThreadList )
		{
			workerThread.start();
		}
		
		
		for(   WorkerThread workerThread  :  workerThreadList )
		{
			workerThread.join();
		}
		
		
		
	    
	     

			for(   WorkerThread workerThread  :  workerThreadList )
			{
				completeErrorCount += workerThread.errorCount;
				errorSet.putAll( workerThread.errorSet );
			}
			
		if(  (System.currentTimeMillis() - currentStartTime)  > ( printResultEveryXseconds * 1000 )   )	
		{
			
			for( Map.Entry<String, MutableInt>  t : errorSet.entrySet()  )
			{
				System.out.println(t.getKey());
				System.out.println("has count " + t.getValue());
				
				
			}
			
			
			
			long durationInSeconds = (System.currentTimeMillis() - currentStartTime) / 1000;
			
			int througput = (int)(currentIterationCount / durationInSeconds);
			
			System.out.println( "errors  " + completeErrorCount + " iterations " + compleIterationCount + " throughput " + througput );
			
			currentIterationCount = 0;
			currentStartTime = System.currentTimeMillis() ;
			
			
			
		}
		
	
	}
	
	
	
	
	
	
	
	
	
	
	
}
