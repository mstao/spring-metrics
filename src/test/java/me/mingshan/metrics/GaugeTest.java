package me.mingshan.metrics;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class GaugeTest {

  @Test
  public void test1() {
    Metrics.addRegistry(new SimpleMeterRegistry());

    AtomicInteger number = Metrics.globalRegistry.gauge("number", new AtomicInteger(1));
    number.getAndIncrement();
    number.getAndIncrement();
    number.getAndIncrement();
    number.getAndIncrement();
  }


}
