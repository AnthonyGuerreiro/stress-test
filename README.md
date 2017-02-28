# stess-test

Stress test is a tool to reproduce java race conditions. It creates an array of <test per iteration> tests using the test setup class by n worker threads. It than executes it test by n worker threads till the given iteration count or error count is reached. 

usage: com.vmlens.StressTest (-e <error count> | -i <iteration count>) <Test Setup Class>

Test Setup Class must implement com.vmlens.stressTest.setup.TestSetup and have a default constructor.

Example: java  -cp stress-test-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.vmlens.StressTest -e 5 com.vmlens.stressTest.examples.doubleBasedLocking.DoubleBasedLockingTestSetup

Options:
 -e <error count>           stop after n errors
 -i <iteration count>       stop after n iterations
 -t <test per iteration>    number of tests per iteration
 -w <worker thread count>   number of worker threads

#Examples





#Latest release
* [jar](http://search.maven.org/remotecontent?filepath=com/vmlens/concurrent-junit/1.0.0/concurrent-junit-1.0.0.jar) 
* [sources](http://search.maven.org/remotecontent?filepath=com/vmlens/concurrent-junit/1.0.0/concurrent-junit-1.0.0-sources.jar) 
* [javadoc](http://search.maven.org/remotecontent?filepath=com/vmlens/concurrent-junit/1.0.0/concurrent-junit-1.0.0-javadoc.jar) 


## Maven

```xml
<dependency>
<groupId>com.vmlens</groupId>
<artifactId>concurrent-junit</artifactId>
<version>1.0.0</version>
</dependency>
```


#License
concurrent-junit is released under the [Eclipse Public License 1.0](http://www.eclipse.org/legal/epl-v10.html)


