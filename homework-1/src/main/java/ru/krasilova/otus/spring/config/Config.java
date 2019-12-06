package ru.krasilova.otus.spring.config;


import org.springframework.cglib.core.Local;

public interface Config {
    public String getLocale();

    public void setLocale();

    public String getMessageSource(String keyString);

    public int getCountCorrectAnswersToOk();

}


