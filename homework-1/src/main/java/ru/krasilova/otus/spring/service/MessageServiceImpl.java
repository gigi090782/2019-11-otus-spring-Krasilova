package ru.krasilova.otus.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.configuration.Config;

@Service
public class MessageServiceImpl implements MessageService  {

    private final MessageSource messageSource;
    private final Config config;

    private String message;


    public MessageServiceImpl(MessageSource messageSource, Config config) {
        this.messageSource = messageSource;
        this.config = config;
    };

    @Override
    public String getMessage(String keyString)
    {
        message = messageSource.getMessage(keyString,null, config.getLocale());
        return this.message;
    }

    @Override
    public String getMessageFormat(String keyString, Object[] objectsFormat)
    {
        message = messageSource.getMessage(keyString,objectsFormat, config.getLocale());
        return this.message;
    }
}

