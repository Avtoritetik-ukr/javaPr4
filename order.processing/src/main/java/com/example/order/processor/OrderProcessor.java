package com.example.order.processor;

import com.example.order.exception.OrderProcessingException;
import com.example.order.model.Product;

public class OrderProcessor<T extends Product> {
    private final T product;

    public OrderProcessor(T product) {
        this.product = product;
    }

    public void processOrder() throws OrderProcessingException {
        if (product == null) {
            throw new OrderProcessingException("Помилка: Товар не ініціалізований (null).");
        }
        if (product.getPrice() <= 0) {
            throw new OrderProcessingException(
                    String.format("Помилка обробки товару '%s' (ID: %s): некоректна ціна %.2f грн",
                            product.getName(), product.getId(), product.getPrice())
            );
        }

        System.out.printf("[%s] УСПІХ: Замовлення опрацьовано -> %s%n",
                Thread.currentThread().getName(), product);
    }

    public T getProduct() {
        return product;
    }
}