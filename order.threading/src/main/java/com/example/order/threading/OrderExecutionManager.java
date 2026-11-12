package com.example.order.threading;

import com.example.order.exception.OrderProcessingException;
import com.example.order.model.Product;
import com.example.order.processor.OrderProcessor;
import com.example.order.storage.OrderRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OrderExecutionManager {
    private final ExecutorService executorService;

    public OrderExecutionManager(int poolSize) {
        this.executorService = Executors.newFixedThreadPool(poolSize);
    }

    public <T extends Product> void processOrdersAsync(OrderRepository<T> repository) {
        List<T> orders = repository.getAllOrders();

        for (T product : orders) {
            executorService.submit(() -> {
                OrderProcessor<T> processor = new OrderProcessor<>(product);
                try {
                    processor.processOrder();
                } catch (OrderProcessingException e) {
                    System.err.printf("[%s] ПОМИЛКА: %s%n", Thread.currentThread().getName(), e.getMessage());
                }
            });
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}