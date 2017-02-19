package com.vmlens.stressTest.examples.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

import com.vmlens.stressTest.setup.TestSetup;

public class GenericsHandlingTestSetup implements TestSetup  {

	
	private TypeVariable typeVariable;
	private Field bounds;
	
	public GenericsHandlingTestSetup() {
	Class cl = GenericInterface.class;
		
		Object[] array = new Object[0];
		
		
		try {
			Method method = cl.getMethod("genericMethod");
		    typeVariable = 	(TypeVariable) method.getGenericReturnType();
			
			bounds = typeVariable.getClass().getDeclaredField("bounds");
			bounds.setAccessible(true);
			
			
		} catch (NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	
	}

	@Override
	public Runnable createTest() throws  IllegalArgumentException, IllegalAccessException {
		
		bounds.set(typeVariable, null);
		
		return new GenericsHandlingTest(typeVariable);
	}

	@Override
	public void initialize() throws Exception {
		Class clazz = GenericInterface.class.getClass();
		
		Field field = clazz.getDeclaredField("useCaches");
		
		field.setAccessible(true);
		
		field.setBoolean( clazz , false);
	}

}
