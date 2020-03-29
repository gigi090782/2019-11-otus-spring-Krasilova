package ru.krasilova.otus.spring.homework15.service;

import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.homework15.models.OrderItem;
import ru.krasilova.otus.spring.homework15.models.Product;

@Service
public class BlacksmithWorkshopService {

    public Product made(OrderItem orderItem) throws Exception {
        System.out.println("Doing " + orderItem.getItemName());
        Thread.sleep(3000);
        System.out.println("Doing " + orderItem.getItemName() + " done");
        return new Product(orderItem.getItemName());
    }
}
