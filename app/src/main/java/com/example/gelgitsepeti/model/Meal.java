package com.example.gelgitsepeti.model;

public class Meal {
    private final String name;
    private final String price;
    private final int imageRes;

    public Meal(String name, String price, int imageRes) {
        this.name = name;
        this.price = price;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageRes() {
        return imageRes;
    }
}
