package me.mingshan.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterRegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MetricsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetricsApplication.class, args);
	}

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> configure(@Value("${spring.application.name}") String applicationName) {
    return (registry) -> registry.config().commonTags("application", applicationName);
  }
}
