package com.example.projecttng;

public class FoodItem {
    private String name;
    private String description;
    private String calories;
    private String price;
    private String time;
    private int imageResId;

    public FoodItem(String name, String description, String calories, String price, String time, int imageResId) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.price = price;
        this.time = time;
        this.imageResId = imageResId;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCalories() { return calories; }
    public String getPrice() { return price; }
    public String getTime() { return time; }
    public int getImageResId() { return imageResId; }
}
