package ru.krasilova.otus.spring.service;

public interface IOService {
    String readString();
    String readString(String prompt);
    void printString(String s);
}
