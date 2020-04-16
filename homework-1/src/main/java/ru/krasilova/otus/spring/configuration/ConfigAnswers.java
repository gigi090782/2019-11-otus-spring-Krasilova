package ru.krasilova.otus.spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties("answers")
public class ConfigAnswers {


    private int countToOk;

    public void setCountToOk(int countToOk) {

        this.countToOk = countToOk;
    }

    public int getCountToOk() {

        return this.countToOk;
    }

}

