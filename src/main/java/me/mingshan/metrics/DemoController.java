package me.mingshan.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Walker Han
 * @date 2020/7/16 16:05
 */
@RestController
@RequestMapping("/metrics")
public class DemoController {

  @Autowired
  private DemoMetrics demoMetrics;

  @GetMapping("/test")
  public String test() {
    demoMetrics.getJobCounter().increment();
    return "zzz";
  }

}
