package com.vmlens.stressTest.examples.dataRace;

import java.lang.reflect.Type;

import org.junit.Assert;


/**
 * 
 * This test contains a simple data race. The method getDoubleBasedLocking():
 * 
 * <pre>
 *  {@code
 *  public Type[] getDoubleBasedLocking() {
 *		if (instance == null) {
 *			
 *
 *				if (instance == null) {
 * 
 *					Type[] ts = new Type[1];
 *
 *					ts[0] = Object.class;
 *
 *					instance = ts;
 *
 *				}
 *
 *		}
 *
 *		return instance;
 *   }
 *   }
 *	 </pre>	
 *
 *		has no synchronization. This leads to platform dependent errors. If its run on an ARM compatible plattform like 
 *		for an example an rasberry pi it will generate null pointer exceptions.
 *		
 * 
 * 
 * 
 * @author thomas
 *
 */


public class DataRaceTest implements Runnable {
	
	
	
	private Type[] instance;

	public Type[] getDoubleBasedLocking() {

		if (instance == null) {
			

				if (instance == null) {

					Type[] ts = new Type[1];

					ts[0] = Object.class;

					instance = ts;

				}

		}

		return instance;
		
		

	}
	





	@Override
	public void run() {

		Assert.assertEquals(Object.class , (Class<Object>) getDoubleBasedLocking()[0]  );
	
	}
	
	
	

}
