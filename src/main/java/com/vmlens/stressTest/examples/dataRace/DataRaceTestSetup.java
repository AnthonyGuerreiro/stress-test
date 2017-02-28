package com.vmlens.stressTest.examples.dataRace;


import com.vmlens.stressTest.setup.*;


/**
 * 
 * Creates a {@link com.vmlens.stressTest.examples.dataRace.DataRaceTest}.
 * 
 * 
 * @author thomas
 *
 */



public class DataRaceTestSetup implements TestSetup  {

	
	
	
	@Override
	public Runnable createTest() {
		return  new DataRaceTest();
		
		
	}


	
	
	
	

}
