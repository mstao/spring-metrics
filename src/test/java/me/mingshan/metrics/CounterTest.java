package me.mingshan.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToDoubleFunction;

/**
 * 计数器
 *
 * 使用场景：
 *
 * Counter的作用是记录XXX的总量或者计数值，适用于一些增长类型的统计，
 * 例如下单、支付次数、HTTP请求总量记录等等，通过Tag可以区分不同的场景，对于下单，可以使用不同的Tag标记不同的业务来源或者是按日期划分，
 * 对于HTTP请求总量记录，可以使用Tag区分不同的URL。
 *
 * @author Walker Han
 * @date 2020/7/24 15:10
 */
public class CounterTest {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Test
  public void test1() {
    SimpleMeterRegistry meterRegistry = new SimpleMeterRegistry();
    Counter counter = meterRegistry.counter("http.request", "createOrder", "/order/create");
    counter.increment();
    System.out.println(counter.measure()); // [Measurement{statistic='COUNT', value=1.0}]
    Assert.assertEquals(1, counter.count(), 0);
  }

  @Test
  public void test2() {
    Metrics.addRegistry(new SimpleMeterRegistry());

    Order order1 = new Order();
    order1.setOrderId("ORDER_ID_1");
    order1.setAmount(100);
    order1.setChannel("CHANNEL_A");
    order1.setCreateTime(LocalDateTime.now());
    createOrder(order1);
    Order order2 = new Order();
    order2.setOrderId("ORDER_ID_2");
    order2.setAmount(200);
    order2.setChannel("CHANNEL_B");
    order2.setCreateTime(LocalDateTime.now().minusDays(1));
    createOrder(order2);

    Search.in(Metrics.globalRegistry).meters().forEach(each -> {
      StringBuilder builder = new StringBuilder();
      builder.append("name:")
          .append(each.getId().getName())
          .append(",tags:")
          .append(each.getId().getTags())
          .append(",type:").append(each.getId().getType())
          .append(",value:").append(each.measure());
      System.out.println(builder.toString());
    });
  }

  /**
   * 计数器数值增加的动作抽象成接口类型ToDoubleFunction，用户可以自定义计数器实现
   */
  @Test
  public void test3() {
    final SimpleMeterRegistry simpleMeterRegistry = new SimpleMeterRegistry();
    final AtomicInteger atomicInteger = new AtomicInteger(1);

    FunctionCounter functionCounter = FunctionCounter
        .builder("functionCounter", atomicInteger, func -> func.get())
        .register(simpleMeterRegistry);

    atomicInteger.getAndIncrement();
    atomicInteger.getAndIncrement();
    atomicInteger.getAndIncrement();
    System.out.println(functionCounter.count());
    System.out.println(functionCounter.measure());
  }

  private static void createOrder(Order order) {
    //忽略订单入库等操作
    Metrics.counter("order.create",
        "channel", order.getChannel(),
        "createTime", FORMATTER.format(order.getCreateTime())).increment();

    // 或者
    Tags tags = Tags.of("channel", order.getChannel())
        .and("createTime", FORMATTER.format(order.getCreateTime()));
    Counter counter = Counter.builder("order.create2")
        .description("订单新建")
        .tags(tags)
        .register(Metrics.globalRegistry);
    counter.increment();
  }

  //实体
  static class Order {
    private String orderId;
    private Integer amount;
    private String channel;
    private LocalDateTime createTime;

    public String getOrderId() {
      return orderId;
    }

    public void setOrderId(String orderId) {
      this.orderId = orderId;
    }

    public Integer getAmount() {
      return amount;
    }

    public void setAmount(Integer amount) {
      this.amount = amount;
    }

    public String getChannel() {
      return channel;
    }

    public void setChannel(String channel) {
      this.channel = channel;
    }

    public LocalDateTime getCreateTime() {
      return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
      this.createTime = createTime;
    }
  }

}
