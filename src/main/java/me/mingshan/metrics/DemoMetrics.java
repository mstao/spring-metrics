package me.mingshan.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

/**
 * @author Walker Han
 * @date 2020/7/16 15:44
 */
@Component
public class DemoMetrics implements MeterBinder {
  // 计数器
  private Counter jobCounter;

  @Override
  public void bindTo(MeterRegistry meterRegistry) {
    this.jobCounter = Counter.builder("demo_counter").tags("name", "demo1")
        .description("zzz")
        .register(meterRegistry);
  }

  public Counter getJobCounter() {
    return jobCounter;
  }
}
