package com.vmlens.stressTest.internal;

import java.util.concurrent.Callable;

public class PoisenedCall implements Callable<Result> {

	@Override
	public Result call() throws Exception {
		
		throw new StopException();
		
	}

}
