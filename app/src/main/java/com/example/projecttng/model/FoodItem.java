package com.example.projecttng.model;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private int id;
    private String name;
    private String description;
    private String calories;
    private String price;
    private String time;
    private int imageResId;
    private int soldCount;
    private int likeCount;
    private float rating;
    private FoodType type;
    private int quantity = 1;
    private int chefId;

    // Default constructor
    public FoodItem() {}

    // Constructor without chefId (legacy)
    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount, float rating, FoodType type) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.price = price;
        this.time = time;
        this.imageResId = imageResId;
        this.soldCount = soldCount;
        this.likeCount = likeCount;
        this.rating = rating;
        this.type = type;
        this.chefId = -1;
    }

    // Constructor with chefId
    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount, float rating, FoodType type, int chefId) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.price = price;
        this.time = time;
        this.imageResId = imageResId;
        this.soldCount = soldCount;
        this.likeCount = likeCount;
        this.rating = rating;
        this.type = type;
        this.chefId = chefId;
    }

    // Constructor with id but without chefId (for compatibility)
    public FoodItem(int id, String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount, float rating, FoodType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.price = price;
        this.time = time;
        this.imageResId = imageResId;
        this.soldCount = soldCount;
        this.likeCount = likeCount;
        this.rating = rating;
        this.type = type;
        this.chefId = -1;
    }

    // Constructor with id and chefId
    public FoodItem(int id, String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount, float rating, FoodType type, int chefId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.price = price;
        this.time = time;
        this.imageResId = imageResId;
        this.soldCount = soldCount;
        this.likeCount = likeCount;
        this.rating = rating;
        this.type = type;
        this.chefId = chefId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCalories() { return calories; }
    public String getPrice() { return price; }
    public String getTime() { return time; }
    public int getImageResId() { return imageResId; }
    public int getSoldCount() { return soldCount; }
    public int getLikeCount() { return likeCount; }
    public float getRating() { return rating; }
    public FoodType getType() { return type; }
    public int getQuantity() { return quantity; }
    public int getChefId() { return chefId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCalories(String calories) { this.calories = calories; }
    public void setPrice(String price) { this.price = price; }
    public void setTime(String time) { this.time = time; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }
    public void setSoldCount(int soldCount) { this.soldCount = soldCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setRating(float rating) { this.rating = rating; }
    public void setType(FoodType type) { this.type = type; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setChefId(int chefId) { this.chefId = chefId; }

    // Format price
    public String getFormattedPrice() {
        try {
            return String.format("%,d đ", Integer.parseInt(price)).replace(",", ".");
        } catch (Exception e) {
            return price;
        }
    }

    // Parse price to int
    public int getParsedPrice() {
        try {
            return Integer.parseInt(price.replace(".", "").replace("đ", "").trim());
        } catch (Exception e) {
            return 0;
        }
    }

    // FoodType enum
    public enum FoodType {
        FOOD("Đồ ăn"),
        DRINK("Đồ uống"),
        DESSERT("Tráng miệng");

        private final String displayName;

        FoodType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String toRawString() {
            return this.name();
        }

        public static FoodType valueOfSafe(String value) {
            try {
                return FoodType.valueOf(value.toUpperCase());
            } catch (Exception e) {
                return FOOD;
            }
        }
    }
}