package ru.krasilova.otus.spring.homework6.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {
    private final static Scanner scanner = new Scanner(System.in);

    @Override
    public String readString() {
        return (scanner.nextLine());
    }

    @Override
    public String readString(String prompt) {
        System.out.print(prompt);
        return (scanner.nextLine());
    }

    @Override
    public void printString(String s) {
        System.out.println(s);
    }
}