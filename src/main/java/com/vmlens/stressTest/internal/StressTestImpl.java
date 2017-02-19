package com.vmlens.stressTest.internal;


import java.util.Calendar;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.vmlens.stressTest.setup.TestSetup;

public class StressTestImpl {
	
	
	
	
	private void runTest( Class<TestSetup> testSetupClass, StopStrategy stopStrategy  ) throws Exception
	{
		
		
		TestSetupCall[] testSetupCallArray = new TestSetupCall[4];
		TestSetup testSetup = testSetupClass.newInstance();
		testSetup.initialize();
	
		
		
		for( int i = 0 ; i < 4 ; i++ )
		{
		
			TestSetup[] testSetupArray = new TestSetup[200];
			
			for( int x = 0 ; x < 200; x++)
			{
				testSetupArray[x] = testSetupClass.newInstance();
			}
			
			
			testSetupCallArray[i] = new TestSetupCall(testSetupArray);
			
		}
		
		
		WorkerThread[] workerThreadArray = new WorkerThread[4];
		
		for( int i = 0 ; i < 4 ; i++ )
		{
			workerThreadArray[i] = new WorkerThread();
			workerThreadArray[i].start();
		}
		
		int errorCount = 0;
		int iterations = 0;
		
		
		System.out.format("Started at: %tT", Calendar.getInstance());
		long startTime = System.currentTimeMillis();
			
		try{
		
	
		
	
		while( ! stopStrategy.stop(errorCount,iterations))
		{
			
			for(int i = 0 ; i < 4 ; i++)
			{
				workerThreadArray[i].in.put(  testSetupCallArray[i]  );
			}
			
			TestSetupResult[] testSetupResultArray = new TestSetupResult[4];
			
			for(int i = 0 ; i < 4; i++)
			{
				Result result = workerThreadArray[i].out.take();
				testSetupResultArray[i] = (TestSetupResult) result;
			}
			
			TestCall[] testCallArray = new TestCall[4];
			
			testCallArray[0] = new TestCall(testSetupResultArray[0].getTests(), testSetupResultArray[1].getTests());
			testCallArray[1] = new TestCall(testSetupResultArray[0].getTests(), testSetupResultArray[1].getTests());
			
			testCallArray[2] = new TestCall(testSetupResultArray[2].getTests(), testSetupResultArray[3].getTests());
			testCallArray[3] = new TestCall(testSetupResultArray[2].getTests(), testSetupResultArray[3].getTests());
			
			
			for(int i = 0 ; i < 4 ; i++)
			{
				workerThreadArray[i].in.put(  testCallArray[i]  );
			}
			
			for(int i = 0 ; i < 4; i++)
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
			
		}
		
		}
		finally{
			for(int i = 0 ; i < 4 ; i++)
			{
				workerThreadArray[i].in.put(  new PoisenedCall()  );
			}
		}
		
		System.out.println();
		System.out.format("stopped after %.2f seconds, running for %d iterations with %d errors." ,((float) (System.currentTimeMillis() -  startTime) / 1000)  , iterations , errorCount );
		System.out.println();
		
		
		
	}
	
	
	private static final String ITERATION_OPTION = "i";
	private static final String ERROR_OPTION = "e";
//	private static final String TEST_SETUP_OPTION = "c";
	
	
	
	public  void runTest(String[] args) {
		
		Options options = new Options();

		
		options.addOption( Option.builder(ITERATION_OPTION).hasArg().argName("iteration count").desc("stop after n iterations").build() );
		options.addOption( Option.builder(ERROR_OPTION).hasArg().argName("error count").desc("stop after n iterations").build() );
		
		

		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse( options, args);
			
		
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
			   
			
			    if(stopStrategy != null)
			    {
			    	Class<TestSetup> cl =  (Class<TestSetup>) this.getClass().getClassLoader().loadClass(setupClassName);
			    	runTest( cl , stopStrategy );
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
