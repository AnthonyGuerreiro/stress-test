# stess-test

Stress test is a tool to reproduce java race conditions. It creates an array of <test per iteration> tests using the test setup class by n worker threads. It then executes the test by n worker threads till the given iteration count or error count is reached. 

usage: com.vmlens.StressTest (-e <error count> | -i <iteration count>) <Test Setup Class>

Test Setup Class must implement com.vmlens.stressTest.setup.TestSetup and have a default constructor.

Example: java  -cp stress-test-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.vmlens.StressTest -e 5 com.vmlens.stressTest.examples.doubleBasedLocking.DoubleBasedLockingTestSetup

Options:
<dl>
  <dt>-e <error count></dt>
  <dd> stop after n errors</dd>

  <dt>-i <iteration count>  </dt>
  <dd>stop after n iterations</dd>
 
 
   <dt> -t <test per iteration></dt>
  <dd> number of tests per iteration</dd>

  <dt> -w <worker thread count>  </dt>
  <dd>    
   number of worker threads</dd>
 
 
 
</dl>




#Examples
 * dataRace.DataRaceTest: This test contains a simple data race. If its run on an ARM compatible plattform like for example an rasberry pi it will generate null pointer exceptions. 
 * reflection.GenericsHandlingTest: A test to reproduce a race condition in java.lang.reflect.TypeVariable.getBounds()

#Api Doc

https://vmlens.github.io/stress-test/apidocs



#Latest release
* [jar](https://github.com/vmlens/stress-test/releases/download/0.0.1/stress-test-0.0.1-SNAPSHOT-jar-with-dependencies.jar)  
* [javadoc](https://github.com/vmlens/stress-test/releases/download/0.0.1/stress-test-0.0.1-SNAPSHOT-javadoc.jar) 





#License
concurrent-junit is released under the [Eclipse Public License 1.0](http://www.eclipse.org/legal/epl-v10.html)


