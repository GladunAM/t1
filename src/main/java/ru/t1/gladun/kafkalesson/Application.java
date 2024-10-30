package ru.t1.gladun.kafkalesson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.t1.gladun.kafkalesson.config.KafkaConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(KafkaConfigProperties.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
