package ru.krasilova.otus.spring.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Config {

    @Value("${config.local}")
    private String localeStr;

    @Value("${count.answers}")
    private int countCorrectAnswersToOk;


    @Value("${filename.pattern}")
    private String filenamePattern;



    public Locale getLocale() {

        return (localeStr == "") ? Locale.getDefault() : new Locale(localeStr);
    }


    public int getCountCorrectAnswersToOk() {
        return countCorrectAnswersToOk;
    }

    public String getFilenamePattern() {
        return filenamePattern;
    }
}

