package ru.krasilova.otus.spring.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("filepath")
public class ConfigFilePath {

    private String pattern;


    public String getPattern() {

        return pattern;
    }

    public void setPattern(String pattern) {

        this.pattern = pattern;
    }
}

