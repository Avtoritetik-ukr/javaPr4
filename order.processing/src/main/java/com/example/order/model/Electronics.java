package com.example.order.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Electronics extends Product {
    private final int warrantyMonths;
    private final String brand;

    @Builder
    public Electronics(String id, String name, double price, int warrantyMonths, String brand) {
        super(id, name, price);
        this.warrantyMonths = warrantyMonths;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [Бренд=%s, Гарантія=%d міс.]", brand, warrantyMonths);
    }
}