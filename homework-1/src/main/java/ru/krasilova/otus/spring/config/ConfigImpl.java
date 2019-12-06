package ru.krasilova.otus.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Configuration
@Component
public class ConfigImpl implements Config {


    private final MessageSource messageSource;
    private Locale locale;
    private String message;

    @Value("${config.local}")
    private String localeStr;

    @Value("${count.answers}")
    private int countCorrectAnswersToOk;

    @Autowired
    public ConfigImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    };

    public void setLocale()
    {   String localConfig = this.getLocale();
        this.locale = (localConfig=="")? Locale.getDefault():new Locale(localConfig);
    }

    @Override
    public String getLocale() {
        return this.localeStr;
    }

    @Override
    public String getMessageSource(String keyString)
    {
        message = messageSource.getMessage(keyString,null, this.locale);
        return this.message;
    }

    @Override
    public int getCountCorrectAnswersToOk() {
        return countCorrectAnswersToOk;
    }
}

