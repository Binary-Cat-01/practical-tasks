package com.walking.lesson50_stream_practice;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Order {
    private final Long id;
    private final LocalDate orderDate;
    private final LocalDate deliveryDate;
    private final String status;
    private final Customer customer;
    Set<Product> products;

    public Order() {
        this.id = 1L;
        this.orderDate = LocalDate.now();
        this.deliveryDate = LocalDate.now();
        this.status = "status";
        this.customer = new Customer();
        this.products = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Set<Product> getProducts() {
        return products;
    }
}
