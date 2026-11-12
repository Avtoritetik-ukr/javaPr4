package com.example.order.model;

public abstract class Product {
    private final String id;
    private final String name;
    private final double price;

    protected Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("%s [ID=%s, Назва='%s', Ціна=%.2f грн]",
                getClass().getSimpleName(), id, name, price);
    }
}