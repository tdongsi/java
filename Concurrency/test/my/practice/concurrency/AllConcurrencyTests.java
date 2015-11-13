package my.practice.concurrency;

import my.practice.concurrency.counter.AtomicCounterTest;
import my.practice.concurrency.counter.MonitorCounterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SimpleCounterTest.class, AtomicCounterTest.class, MonitorCounterTest.class })
public class AllConcurrencyTests {

}
