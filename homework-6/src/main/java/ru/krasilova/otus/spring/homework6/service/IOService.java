package ru.krasilova.otus.spring.homework6.service;

public interface IOService {
    String readString();
    String readString(String prompt);
    void printString(String s);
}
