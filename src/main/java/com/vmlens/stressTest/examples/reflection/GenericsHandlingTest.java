package com.vmlens.stressTest.examples.reflection;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;


import org.junit.Assert;


/**
 * 
 * A test to reproduce a race condition in java.lang.reflect.TypeVariable.getBounds().
 * 
 * 
 * @author thomas
 *
 */


public class GenericsHandlingTest implements Runnable {

	private final TypeVariable typeVariable;
	
	
	
	
	public GenericsHandlingTest( TypeVariable typeVariable) {
		super();
		this.typeVariable = typeVariable;
	}




	@Override
	public void run() {
	
		
		Assert.assertTrue( typeVariable.getBounds()[0] instanceof  ParameterizedType );
		

		
	}

}
