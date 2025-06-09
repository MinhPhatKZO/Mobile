package com.example.projecttng;

public class FoodItem {
    private String name;
    private String description;
    private String calories;
    private String price;
    private String time;
    private int imageResId;
    private int soldCount;
    private int likeCount;

    // Constructor đầy đủ 8 tham số
    public FoodItem(String name, String description, String calories, String price, String time, int imageResId, int soldCount, int likeCount) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.price = price;
        this.time = time;
        this.imageResId = imageResId;
        this.soldCount = soldCount;
        this.likeCount = likeCount;
    }

    // Constructor cũ 6 tham số (giữ lại để tránh lỗi ở các class cũ)
    public FoodItem(String name, String description, String calories, String price, String time, int imageResId) {
        this(name, description, calories, price, time, imageResId, 0, 0); // Mặc định soldCount và likeCount = 0
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCalories() { return calories; }
    public String getPrice() { return price; }
    public String getTime() { return time; }
    public int getImageResId() { return imageResId; }
    public int getSoldCount() { return soldCount; }
    public int getLikeCount() { return likeCount; }
}
