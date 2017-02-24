package com.vmlens.stressTest.examples.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

import com.vmlens.stressTest.setup.TestSetup;
import com.vmlens.stressTest.util.StressTestClassLoader;

public class GenericsHandlingTestSetup implements TestSetup  {

	
	private TypeVariable typeVariable;
	private Field bounds;
	
	public GenericsHandlingTestSetup() {
	
		
		
		  
		
		
		
		
		try {
			
			Class cl =  (new StressTestClassLoader( GenericsHandlingTestSetup.class.getClassLoader() )).loadClass("com.vmlens.stressTest.examples.reflection.GenericInterface");
			
			
			
			
			typeVariable  = cl.getTypeParameters()[0];
			
			bounds = typeVariable.getClass().getDeclaredField("bounds");
			bounds.setAccessible(true);
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	
	}

	@Override
	public Runnable createTest() throws  IllegalArgumentException, IllegalAccessException {
		
		bounds.set(typeVariable, null);
		
		return new GenericsHandlingTest(typeVariable);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	

}
