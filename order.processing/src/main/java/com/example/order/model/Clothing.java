package com.example.order.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Clothing extends Product {
    private final String size;
    private final String material;

    @Builder
    public Clothing(String id, String name, double price, String size, String material) {
        super(id, name, price);
        this.size = size;
        this.material = material;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [Розмір=%s, Матеріал=%s]", size, material);
    }
}