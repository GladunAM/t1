package ru.t1.gladun.starter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "log")
public class LogProperties {
    private Boolean enabled;
    private String level;
}
