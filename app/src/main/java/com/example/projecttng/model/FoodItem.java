package com.example.projecttng.model;

import androidx.annotation.NonNull;

public class FoodItem {

    private String name;
    private String description;
    private String calories;
    private String price;
    private String time;
    private int imageResId;
    private int soldCount;
    private int likeCount;
    private float rating;
    private int quantity = 1;
    private FoodType type;

    // Enum loại món
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
    }

    // Constructor đầy đủ
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
    }

    // Constructor không có type (mặc định là FOOD)
    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount, float rating) {
        this(name, description, calories, price, time, imageResId, soldCount, likeCount, rating, FoodType.FOOD);
    }

    // Constructor không có rating và type
    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount) {
        this(name, description, calories, price, time, imageResId, soldCount, likeCount, 0f, FoodType.FOOD);
    }

    // Constructor cơ bản
    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId) {
        this(name, description, calories, price, time, imageResId, 0, 0, 0f, FoodType.FOOD);
    }

    // --- Getter ---
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

    // --- Setter ---
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setRating(float rating) { this.rating = rating; }
    public void setType(FoodType type) { this.type = type; }

    // --- Logic bổ sung ---
    public int getParsedPrice() {
        try {
            return Integer.parseInt(price.replace(".", "").replace("đ", "").trim());
        } catch (Exception e) {
            return 0;
        }
    }

    @NonNull
    public String getFormattedPrice() {
        return String.format("%,d đ", getParsedPrice()).replace(',', '.');
    }

    public String getId() {
        return name; // Có thể dùng ID thực nếu backend có
    }
}
