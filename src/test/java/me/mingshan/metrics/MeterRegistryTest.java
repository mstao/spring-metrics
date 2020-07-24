package me.mingshan.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试 MeterRegistry
 *
 * @author Walker Han
 * @date 2020/7/24 14:13
 */
public class MeterRegistryTest {

  /**
   * SimpleMeterRegistry适合做调试的时候使用
   */
  @Test
  public void testSimpleMeterRegistry() {
    SimpleMeterRegistry meterRegistry = new SimpleMeterRegistry();
    Counter counter = meterRegistry.counter("counter");
    counter.increment();

    Assert.assertEquals(1, counter.count(), 0);
  }

  /**
   * CompositeMeterRegistry实例初始化的时候，内部持有的MeterRegistry列表是空的,
   * 需要调用 add 方法添加后使用
   */
  @Test
  public void testCompositeMeterRegistry() {
    CompositeMeterRegistry compositeMeterRegistry = new CompositeMeterRegistry();

    // 直接这样写计算无效
    Counter counter = compositeMeterRegistry.counter("counter");
    counter.increment();

    Assert.assertEquals(0, counter.count(), 0);

    // 需要向CompositeMeterRegistry实例中添加SimpleMeterRegistry实例
    SimpleMeterRegistry simpleMeterRegistry = new SimpleMeterRegistry();
    compositeMeterRegistry.add(simpleMeterRegistry);

    counter.increment();
    Assert.assertEquals(1, counter.count(), 0);
  }

  /**
   * 测试全局
   */
  @Test
  public void testMetrics() {
    Metrics.addRegistry(new SimpleMeterRegistry());

    Counter counter = Metrics.counter("counter");
    counter.increment();
    Assert.assertEquals(1, counter.count(), 0);
  }

}
