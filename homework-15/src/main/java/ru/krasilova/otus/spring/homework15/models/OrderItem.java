package ru.krasilova.otus.spring.homework15.models;

public class OrderItem {

    private final String itemName;

    public OrderItem(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}
