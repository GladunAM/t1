package ru.t1.gladun.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1.gladun.starter.aspect.MainAspect;
import ru.t1.gladun.starter.exception.WorkingProcessException;

@Configuration
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoConfiguration {

    private final LogProperties logProperties;

    public LogAutoConfiguration(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    @Bean
    @ConditionalOnProperty(value = "log.enabled",
            havingValue = "true",
            matchIfMissing = true)
    public MainAspect mainAspect(LogProperties logProperties) {
        return new MainAspect(logProperties.getLevel());
    }

    @Bean
    public WorkingProcessException workingProcessException() {
        String message = "";
        return new WorkingProcessException(message);
    }

}
