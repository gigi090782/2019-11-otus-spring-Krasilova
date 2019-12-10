
package ru.krasilova.otus.spring.service;

public interface MessageService  {
    public String getMessage(String keyString);
    public String getMessageFormat(String keyString, Object[] objectsFormat);
}
