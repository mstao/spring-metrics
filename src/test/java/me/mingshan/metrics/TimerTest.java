package me.mingshan.metrics;

import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Timer（计时器）适用于记录耗时比较短的事件的执行时间，通过时间分布展示事件的序列和发生频率。
 *
 * 所有的Timer的实现至少记录了发生的事件的数量和这些事件的总耗时，从而生成一个时间序列。
 * Timer的基本单位基于服务端的指标而定，但是实际上我们不需要过于关注Timer的基本单位，
 * 因为Micrometer在存储生成的时间序列的时候会自动选择适当的基本单位。
 *
 * 主要用于：
 *
 * 1、记录指定方法的执行时间用于展示。
 * 2、记录一些任务的执行时间，从而确定某些数据来源的速率，例如消息队列消息的消费速率等。
 *
 * @author Walker Han
 * @date 2020/7/24 15:48
 */
public class TimerTest {

  @Test
  public void test1() {
    Metrics.addRegistry(new SimpleMeterRegistry());
    Order order1 = new Order();
    order1.setOrderId("ORDER_ID_1");
    order1.setAmount(100);
    order1.setChannel("CHANNEL_A");
    order1.setCreateTime(LocalDateTime.now());
    Timer timer = Metrics.timer("timer", "createOrder", "cost");
    timer.record(() -> createOrder(order1));
    System.out.println(timer.measure());
  }

  @Test
  public void test2() {
    Metrics.addRegistry(new SimpleMeterRegistry());

    Object holder = new Object();
    AtomicLong totalTimeNanos = new AtomicLong(0);
    AtomicLong totalCount = new AtomicLong(0);

    FunctionTimer functionTimer = FunctionTimer.builder("functionTimer", holder, p -> totalCount.get(),
        p -> totalTimeNanos.get(), TimeUnit.SECONDS)
        .register(Metrics.globalRegistry);

    totalTimeNanos.addAndGet(10000000);
    totalCount.incrementAndGet();

    System.out.println(functionTimer.measure());
  }

  private void createOrder(Order order1) {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
