package com.vmlens.stressTest.examples.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.junit.Assert;

public class GenericsHandlingTest implements Runnable {

	private final TypeVariable typeVariable;
	
	
	
	
	public GenericsHandlingTest(TypeVariable typeVariable) {
		super();
		this.typeVariable = typeVariable;
	}




	@Override
	public void run() {
	
		
		
		
		
        for( Type b : typeVariable.getBounds() )
        {
    	
        	Assert.assertEquals( b.getTypeName()   ,  "java.lang.Object" );
        	
        }
	    
	  	
	 	
	 	
	   // Assert.assertEquals( t.getTypeName()  , "T"  );
		
	}

}
