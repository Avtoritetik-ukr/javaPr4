package com.example.order.app;

import com.example.order.model.Clothing;
import com.example.order.model.Electronics;
import com.example.order.model.Product;
import com.example.order.processor.OrderProcessor;
import com.example.order.storage.OrderRepository;
import com.example.order.threading.OrderExecutionManager;
import net.datafaker.Faker;

import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();
        OrderRepository<Product> repository = new OrderRepository<>();

        System.out.println("=== 1. Генерація даних (JavaFaker + Lombok Builder) ===");

        for (int i = 0; i < 4; i++) {
            Electronics item = Electronics.builder()
                    .id(UUID.randomUUID().toString().substring(0, 8))
                    .name(faker.device().modelName())
                    .price(faker.number().randomDouble(2, 200, 1500))
                    .brand(faker.device().manufacturer())
                    .warrantyMonths(faker.number().numberBetween(6, 36))
                    .build();
            repository.addOrder(item);
        }

        for (int i = 0; i < 4; i++) {
            Clothing item = Clothing.builder()
                    .id(UUID.randomUUID().toString().substring(0, 8))
                    .name(faker.commerce().productName())
                    .price(faker.number().randomDouble(2, 20, 200))
                    .size(faker.options().option("S", "M", "L", "XL"))
                    .material(faker.commerce().material())
                    .build();
            repository.addOrder(item);
        }

        Electronics invalidItem = Electronics.builder()
                .id(UUID.randomUUID().toString().substring(0, 8))
                .name("Бракований гаджет")
                .price(-50.0)
                .brand("Unknown")
                .warrantyMonths(0)
                .build();
        repository.addOrder(invalidItem);

        System.out.println("\n=== 2. Фільтрація за допомогою Лямбда-виразів ===");
        System.out.println("--- Тільки Одяг з ціною > 50 грн: ---");
        List<Product> filteredClothing = repository.filterOrders(
                product -> product instanceof Clothing && product.getPrice() > 50.0
        );
        filteredClothing.forEach(System.out::println);

        System.out.println("\n=== 3. Багатопотокова обробка замовлень (Executor Threads) ===");
        OrderExecutionManager manager = new OrderExecutionManager(3);
        manager.processOrdersAsync(repository);
        manager.shutdown();

        System.out.println("\n=== 4. Посилання на методи (Method References) ===");
        List<Product> allProducts = repository.getAllOrders();

        allProducts.stream()
                .map(OrderProcessor::new)
                .filter(p -> p.getProduct().getPrice() > 0)
                .forEach(processor -> {
                    try {
                        processor.processOrder();
                    } catch (Exception e) {
                        System.err.println("Помилка: " + e.getMessage());
                    }
                });
    }
}