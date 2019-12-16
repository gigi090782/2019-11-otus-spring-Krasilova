
package ru.krasilova.otus.spring.service;

public interface MessageService {
    String getMessage(String keyString);

    String getMessageFormat(String keyString, Object[] objectsFormat);
}
