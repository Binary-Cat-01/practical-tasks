package com.walking.lesson50_stream_practice;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Customer> customers = List.of(new Customer(), new Customer(), new Customer());
        List<Order> orders = List.of(new Order(), new Order(), new Order());
        List<Product> products = List.of(new Product(), new Product(), new Product());
    }

    private static List<Product> getHighPriceBooks(List<Product> products) {
        return products.stream()
                       .filter(p -> p.getCategory()
                                     .equalsIgnoreCase("Books"))
                       .filter(p -> p.getPrice() > 100)
                       .collect(Collectors.toList());
    }

    private static List<Order> getOrdersWithBabyProduct(List<Order> orders) {
        return orders.stream()
                     .filter(o -> o.getProducts()
                                   .stream()
                                   .anyMatch(p -> p.getCategory()
                                                   .equalsIgnoreCase("Baby")))
                     .collect(Collectors.toList());
    }

    private static List<Product> getToysProductWithDiscount(List<Product> products) {
        return products.stream()
                       .filter(p -> p.getCategory()
                                     .equalsIgnoreCase("Toys"))
                       .map(p -> p.withPrice(0.9 * p.getPrice()))
                       .collect(Collectors.toList());
    }

    private static List<Product> getProductsOrderedTier2CustomerBetween(List<Order> orders) {
        return orders.stream()
                     .filter(o -> o.getCustomer()
                                   .getTier() == 2)
                     .filter(o -> o.getOrderDate()
                                   .isAfter(LocalDate.of(2021, Month.FEBRUARY, 1)))
                     .filter(o -> o.getOrderDate()
                                   .isBefore(LocalDate.of(2021, Month.MARCH, 1)))
                     .flatMap(o -> o.getProducts()
                                    .stream())
                     .distinct()
                     .collect(Collectors.toList());
    }

    private static Product getCheapestBook(List<Product> products) {
        return products.stream()
                       .filter(p -> p.getCategory()
                                     .equalsIgnoreCase("Books"))
                       .min(Comparator.comparingDouble(Product::getPrice))
                       .orElse(null);
    }

    private static List<Order> getRecentPlacedOrders(List<Order> orders) {
        return orders.stream()
                     .sorted(Comparator.comparing(Order::getOrderDate)
                                       .reversed())
                     .limit(3)
                     .collect(Collectors.toList());
    }

    private static List<Product> getProductsOrderedOnDate(List<Order> orders) {
        return orders.stream()
                     .filter(o -> o.getOrderDate()
                                   .isEqual(LocalDate.of(2021, Month.MARCH, 15)))
                     .peek(System.out::println)
                     .flatMap(o -> o.getProducts()
                                    .stream())
                     .distinct()
                     .collect(Collectors.toList());
    }

    private static double getSumOrdersByMonth(List<Order> orders) {
        return orders.stream()
                     .filter(o -> o.getOrderDate()
                                   .isAfter(LocalDate.of(2021, Month.JANUARY, 31)))
                     .filter(o -> o.getOrderDate()
                                   .isBefore(LocalDate.of(2021, Month.MARCH, 1)))
                     .flatMap(o -> o.getProducts()
                                    .stream())
                     .mapToDouble(Product::getPrice)
                     .sum();
    }

    private static double getOrderAveragePaymentOnDate(List<Order> orders) {
        return orders.stream()
                     .filter(o -> o.getOrderDate()
                                   .isEqual(LocalDate.of(2021, Month.MARCH, 14)))
                     .mapToDouble(o -> o.getProducts()
                                        .stream()
                                        .mapToDouble(Product::getPrice)
                                        .sum())
                     .average()
                     .orElse(0);
    }

    private static double getOrderAveragePaymentOnDate1(List<Order> orders) {
        return orders.stream()
                     .filter(o -> o.getOrderDate()
                                   .isEqual(LocalDate.of(2021, Month.MARCH, 14)))
                     .flatMap(o -> o.getProducts()
                                    .stream())
                     .mapToDouble(Product::getPrice)
                     .average()
                     .orElse(0);
    }

    private static DoubleSummaryStatistics getSummaryForBooks(List<Product> products) {
        return products.stream()
                       .filter(p -> p.getCategory()
                                     .equalsIgnoreCase("Books"))
                       .mapToDouble(Product::getPrice)
                       .summaryStatistics();
    }

    private static Map<Long, Long> getProductsCountById(List<Order> orders) {
        return orders.stream()
                     .collect(Collectors.groupingBy(Order::getId, Collectors.summingLong(
                             order -> (long) order.getProducts()
                                                  .size())));
    }

    private static Map<Long, Integer> getProductsCountById1(List<Order> orders) {
        return orders.stream()
                     .collect(Collectors.toMap(Order::getId, o -> o.getProducts()
                                                                   .size()));
    }

    private static Map<Customer, List<Order>> getOrdersByCustomer(List<Order> orders) {
        return orders.stream()
                     .collect(Collectors.groupingBy(Order::getCustomer));
    }

    private static Map<Order, Double> getProductsSumInOrder(List<Order> orders) {
        return orders.stream()
                     .collect(Collectors.toMap(Function.identity(), o -> o.getProducts()
                                                                          .stream()
                                                                          .mapToDouble(Product::getPrice)
                                                                          .sum()));
    }

    private static Map<String, List<String>> getProductsByCategory(List<Product> products) {
        return products.stream()
                       .collect(Collectors.groupingBy(Product::getCategory,
                               Collectors.mapping(Product::getName, Collectors.toList())));
    }

    private static Map<String, Optional<Product>> getMostExpensiveProductByCategory(List<Product> products) {
        return products.stream()
                       .collect(Collectors.groupingBy(Product::getCategory,
                               Collectors.maxBy(Comparator.comparingDouble(Product::getPrice))));
    }
}
