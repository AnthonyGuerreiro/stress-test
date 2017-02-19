package com.vmlens.stressTest.internal;

import java.util.List;

public class TestResult implements Result  {

	private final List<Throwable> exceptionList;

	public TestResult(List<Throwable> exceptionList) {
		super();
		this.exceptionList = exceptionList;
	}

	public List<Throwable> getExceptionList() {
		return exceptionList;
	} 
	
	
	
	
}
