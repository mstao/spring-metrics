package me.mingshan.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Test;

/**
 * 测试TAG功能
 *
 * @author Walker Han
 * @date 2020/7/24 14:33
 */
public class TagTest {

  @Test
  public void test1() {
    Tags tags = Tags.of("key", "value");

    Metrics.addRegistry(new SimpleMeterRegistry());
    Counter counter = Metrics.counter("counter", tags);
    counter.increment();
  }

}
