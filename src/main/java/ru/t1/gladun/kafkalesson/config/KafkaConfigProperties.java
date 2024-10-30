package ru.t1.gladun.kafkalesson.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfigProperties {
    private String groupId;
    private String servers;
    private String sessionTimeout;
    private String maxPartitionFetchBytes;
    private String maxPollRecords;
    private String maxPollIntervalsMs;
    private String clientTopic;
    private String clientRegistrationTopic;
}
