package com.example.gelgitsepeti.model;

public class Coupon {
    private final String title;
    private final String code;
    private final int imageResId;

    public Coupon(String title, String code, int imageResId) {
        this.title = title;
        this.code = code;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getCode()  { return code; }
    public int getImageResId() { return imageResId; }
}

