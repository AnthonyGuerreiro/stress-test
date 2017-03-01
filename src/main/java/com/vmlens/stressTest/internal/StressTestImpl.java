package com.vmlens.stressTest.internal;


import java.util.Calendar;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import com.vmlens.stressTest.setup.TestSetup;

public class StressTestImpl {
	
	
	
	
	private void runTest( Class<TestSetup> testSetupClass, StopStrategy stopStrategy ,  int workerThreadCount ,  int test_per_run   ) throws Exception
	{
		
		
		TestSetupCall[] testSetupCallArray = new TestSetupCall[workerThreadCount];
		
	
		
		
		for( int i = 0 ; i < workerThreadCount ; i++ )
		{
		
			TestSetup[] testSetupArray = new TestSetup[test_per_run];
			
			for( int x = 0 ; x < test_per_run; x++)
			{
				testSetupArray[x] = testSetupClass.newInstance();
			}
			
			
			testSetupCallArray[i] = new TestSetupCall(testSetupArray,test_per_run);
			
		}
		
		
		WorkerThread[] workerThreadArray = new WorkerThread[workerThreadCount];
		
		for( int i = 0 ; i < workerThreadCount ; i++ )
		{
			workerThreadArray[i] = new WorkerThread();
			workerThreadArray[i].start();
		}
		
		int errorCount = 0;
		int iterations = 0;
		long testCount = 0;
		long callCount = 0;
		
		System.out.format("Started at: %tT", Calendar.getInstance());
		System.out.println();
		long startTime = System.currentTimeMillis();
			
		try{
		
	
		
	
		while( ! stopStrategy.stop(errorCount,iterations))
		{
			
			for(int i = 0 ; i < workerThreadCount ; i++)
			{
				workerThreadArray[i].in.put(  testSetupCallArray[i]  );
			}
			
			TestSetupResult[] testSetupResultArray = new TestSetupResult[workerThreadCount];
			
			for(int i = 0 ; i < workerThreadCount; i++)
			{
				Result result = workerThreadArray[i].out.take();
				testSetupResultArray[i] = (TestSetupResult) result;
			}
			
			TestCall[] testCallArray = new TestCall[workerThreadCount];
			
			TestArray[] testArray = new TestArray[workerThreadCount];
			
			for(int i = 0 ; i < workerThreadCount ; i++)
			{
				testArray[i] = new TestArray(testSetupResultArray[i].getTests());	
			}
			
			
			for(int i = 0 ; i < workerThreadCount ; i++)
			{
				testCallArray[i] = new TestCall(testArray);
			}
			

			
			
			for(int i = 0 ; i < workerThreadCount ; i++)
			{
				workerThreadArray[i].in.put(  testCallArray[i]  );
			}
			
			for(int i = 0 ; i < workerThreadCount; i++)
			{
				Result result = workerThreadArray[i].out.take();
				TestResult testResult  = (TestResult) result;
				for( Throwable t : testResult.getExceptionList()  )
				{
					errorCount++;
					t.printStackTrace();
				}
				
				
			}
			
			
			iterations++;
			testCount +=  test_per_run * workerThreadCount;
			callCount +=  test_per_run * workerThreadCount * workerThreadCount;
		}
		
		}
		finally{
			for(int i = 0 ; i < workerThreadCount ; i++)
			{
				workerThreadArray[i].in.put(  new PoisenedCall()  );
			}
		}
		
		System.out.println();
		System.out.format("stopped after %.2f seconds, running for %d iterations, %d tests, %d calls with %d errors." ,((float) (System.currentTimeMillis() -  startTime) / 1000)  , iterations ,testCount ,callCount ,  errorCount );
		System.out.println();
		
		
		
	}
	
	
	private static final String ITERATION_OPTION = "i";
	private static final String ERROR_OPTION = "e";
	private static final String WORKER_THREAD_OPTION = "w";
	private static final String TEST_PER_ITERATION = "t";
	private static final String HELP_OPTION = "h";
//	private static final String TEST_SETUP_OPTION = "c";
	
	
	
	public  void runTest(String[] args) {
		
		Options options = new Options();

		
		options.addOption( Option.builder(ITERATION_OPTION).hasArg().argName("iteration count").desc("stop after n iterations").build() );
		options.addOption( Option.builder(ERROR_OPTION).hasArg().argName("error count").desc("stop after n errors").build() );
		options.addOption( Option.builder(WORKER_THREAD_OPTION).hasArg().argName("worker thread count").desc("number of worker threads").build() );
		options.addOption( Option.builder(TEST_PER_ITERATION).hasArg().argName("test per iteration").desc("number of tests per iteration").build() );
		options.addOption( Option.builder(HELP_OPTION).desc("displays this message").build() );
		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse( options, args);
			
		
		
			
			if(cmd.hasOption(HELP_OPTION) )
			{
				printHelp(options);
				return;
			}
			
			
			String[]  leftOverArgs =  cmd.getArgs();
			
			
			if( leftOverArgs.length != 1  )
			{
				throw new IllegalArgumentException("No test setup class specified.");
			}
			else
			{
				String setupClassName = leftOverArgs[0];
				StopStrategy stopStrategy = null;
				
				
			    if(  cmd.hasOption(ITERATION_OPTION)  )
			    {
			    	int maxIterations =  Integer.parseInt( cmd.getOptionValue(ITERATION_OPTION));
			    	stopStrategy = new StopAtIterationCount(maxIterations);
			    }
			    else if(  cmd.hasOption(ERROR_OPTION)  )
			    {
			    	int maxErrors =  Integer.parseInt( cmd.getOptionValue(ERROR_OPTION));
			    	stopStrategy = new StopAtErrorCount(maxErrors);
			    }
			   
			    int workerThreadCount = 4;
			
			    
			    if( cmd.hasOption(WORKER_THREAD_OPTION))
			    {
			    	workerThreadCount =  Integer.parseInt( cmd.getOptionValue(WORKER_THREAD_OPTION));
			    }
			
			    
			    int test_per_run = 1000;
			    
			    if( cmd.hasOption(TEST_PER_ITERATION))
			    {
			    	test_per_run =  Integer.parseInt( cmd.getOptionValue(TEST_PER_ITERATION));
			    }
			
			     
			     
			     
			    if(stopStrategy != null)
			    {
			    	
			    	
			    	
			    	
			    	Class<TestSetup> cl =  (Class<TestSetup>) this.getClass().getClassLoader().loadClass(setupClassName);
			    	runTest( cl , stopStrategy , workerThreadCount,test_per_run);
			    }
			    else
			    {
			    	throw new IllegalArgumentException("Neither iteration count nor error count specified.");
			    }
			    
			
			
			}
			
			
			
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
			printHelp(options);
		
			
		}
		
		
		
	}
	
	
	private void printHelp(Options options)
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(300, "com.vmlens.StressTest (-e <error count> | -i <iteration count>) <Test Setup Class>", "Test Setup Class must implement com.vmlens.stressTest.setup.TestSetup and have a default constructor. \nExample: java  -cp stress-test-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.vmlens.StressTest -e 5 com.vmlens.stressTest.examples.doubleBasedLocking.DoubleBasedLockingTestSetup" , 
				options , ""  );
	}
	
	
	
	

}
