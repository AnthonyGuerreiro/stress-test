package com.vmlens.stressTest.examples.doubleBasedLocking;

import java.lang.reflect.Type;

public class DoubleBasedLocking {

	public Type[] instance;

	public Type[] getDoubleBasedLocking() {

		if (instance == null) {
			//synchronized (this) {

				if (instance == null) {

					Type[] ts = new Type[1];

					ts[0] = Object.class;

					instance = ts;

				}

			//}

		}

		//return instance;
		
		return instance.clone();

	}

}
