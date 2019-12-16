package ru.krasilova.otus.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.configuration.Config;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final Config config;

    public MessageServiceImpl(MessageSource messageSource, Config config) {
        this.messageSource = messageSource;
        this.config = config;
    }

    ;

    @Override
    public String getMessage(String keyString) {
        String message = messageSource.getMessage(keyString, null, config.getLocale());
        return message;
    }

    @Override
    public String getMessageFormat(String keyString, Object[] objectsFormat) {

        String message = messageSource.getMessage(keyString, objectsFormat, config.getLocale());
        return message;
    }
}

