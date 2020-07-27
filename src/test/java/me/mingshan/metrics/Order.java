package me.mingshan.metrics;

import java.time.LocalDateTime;

/**
 * @author Walker Han
 * @date 2020/7/24 15:55
 */
public class Order {
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