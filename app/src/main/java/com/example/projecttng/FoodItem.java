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
    private float rating; // Added rating field
    private int quantity = 1; // default quantity

    // Full constructor (9 parameters)
    public FoodItem(String name, String description, String calories, String price, String time, int imageResId, int soldCount, int likeCount, float rating) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.price = price;
        this.time = time;
        this.imageResId = imageResId;
        this.soldCount = soldCount;
        this.likeCount = likeCount;
        this.rating = rating;
    }

    // Constructor without rating (defaults to 0)
    public FoodItem(String name, String description, String calories, String price, String time, int imageResId, int soldCount, int likeCount) {
        this(name, description, calories, price, time, imageResId, soldCount, likeCount, 0f);
    }

    // Old constructor (6 parameters)
    public FoodItem(String name, String description, String calories, String price, String time, int imageResId) {
        this(name, description, calories, price, time, imageResId, 0, 0, 0f);
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
    public int getLikes() { return likeCount; }
    public float getRating() { return rating; } // Added getter

    // Setter for rating
    public void setRating(float rating) { this.rating = rating; }

    // Quantity
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Parse price string to int
    public int getParsedPrice() {
        try {
            return Integer.parseInt(price.replace(".", "").replace("đ", "").trim());
        } catch (Exception e) {
            return 0;
        }
    }

    // Temporary ID based on name
    public String getId() {
        return name;
    }
    public String getFormattedPrice() {
        return String.format("%,d đ", getParsedPrice()).replace(',', '.');
    }

}