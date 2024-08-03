package com.walking.lesson50_stream_practice;

import java.util.HashSet;
import java.util.Set;

public class Product {
    private final Long id;
    private final String name;
    private final String category;
    private Double price;
    private final Set<Order> orders;

    public Product() {
        this.id = 1L;
        this.name = "name";
        this.category = "category";
        this.price = 1.00;
        this.orders = new HashSet<>();
    }

    public Product withPrice(double newPrice) {
        Product newProduct = new Product();
        newProduct.setPrice(newPrice);
        return newProduct;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
