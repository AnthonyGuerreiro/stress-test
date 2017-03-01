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
 *		has no synchronization. This leads to platform dependent errors. If its run on an ARM compatible platform like 
 *		for example an Raspberry Pi it will generate null pointer exceptions.
 *		
 * 
 * 
 * 
 * @author thomas
 *
 */


public class DataRaceTest implements Runnable {
	
	
	
	private Type[] instance;

	
	





	@Override
	public void run() {

		if (instance == null) {
			

			if (instance == null) {

				Type[] ts = new Type[1];

				ts[0] = Object.class;

				instance = ts;

			}

     	}
		

		instance[0].getTypeName();
	
	}
	
	
	

}
