package com.example.projecttng.model;

import androidx.annotation.NonNull;

public class FoodItem {

    private int id; // ID trong database
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

        // ✅ Convert từ string raw trong DB (VD: "FOOD") thành Enum
        public static FoodType valueOfSafe(String value) {
            try {
                return FoodType.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return FOOD; // Mặc định nếu sai
            }
        }

        // ✅ Convert Enum thành string raw để lưu DB
        public String toRawString() {
            return this.name();
        }
    }

    // --- Constructor đầy đủ (DAO dùng) ---
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
    }

    // --- Constructor UI dùng (không có ID) ---
    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount, float rating, FoodType type) {
        this(-1, name, description, calories, price, time, imageResId, soldCount, likeCount, rating, type);
    }

    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount, float rating) {
        this(name, description, calories, price, time, imageResId, soldCount, likeCount, rating, FoodType.FOOD);
    }

    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount) {
        this(name, description, calories, price, time, imageResId, soldCount, likeCount, 0f);
    }

    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId) {
        this(name, description, calories, price, time, imageResId, 0, 0);
    }

    // --- Getter ---
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

    // --- Setter ---
    public void setId(int id) { this.id = id; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setRating(float rating) { this.rating = rating; }
    public void setType(FoodType type) { this.type = type; }

    // --- Logic ---
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
}
