package com.example.order.storage;

import com.example.order.model.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrderRepository<T extends Product> {
    private final List<T> orders = Collections.synchronizedList(new ArrayList<>());

    public void addOrder(T product) {
        orders.add(product);
    }

    public List<T> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public List<T> filterOrders(Predicate<T> predicate) {
        return orders.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}