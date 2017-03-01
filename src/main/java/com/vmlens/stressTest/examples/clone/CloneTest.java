package com.vmlens.stressTest.examples.clone;

import java.lang.reflect.Type;

/**
 * 
 * This is the same test as {@link com.vmlens.stressTest.examples.dataRace.DataRaceTest}.
 * Only that it clones the returned array.
 * 
 * 
 * @author thomas
 *
 */


public class CloneTest implements Runnable {

	

	public Type[] instance;

	@Override
	public void run() {

		if (instance == null) {
			

				if (instance == null) {

					Type[] ts = new Type[1];

					ts[0] = Object.class;

					instance = ts;

				}

			

		}

		Type[] clonedInstance =  instance.clone();
		
		clonedInstance[0].getTypeName();
		

	}
	
	
	
	
	
	
}
