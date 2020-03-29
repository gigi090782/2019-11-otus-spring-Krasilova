package ru.krasilova.otus.spring.homework15;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.krasilova.otus.spring.homework15.models.OrderItem;
import ru.krasilova.otus.spring.homework15.models.Product;

import java.util.Collection;

@MessagingGateway
public interface Workshop {

    @Gateway(requestChannel = "itemsChannel", replyChannel = "productChannel")
    Collection<Product> process(Collection<OrderItem> orderItem);
}

