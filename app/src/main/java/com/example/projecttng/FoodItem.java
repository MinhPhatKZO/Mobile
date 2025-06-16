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
    private float rating;
    private int quantity = 1;
    private FoodType type; // Thêm loại món

    // Enum cho loại món
    public enum FoodType {
        FOOD("Đồ ăn"),
        DRINK("Đồ uống");

        private String displayName;

        FoodType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Full constructor với type (10 parameters)
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

    // Constructor without type (defaults to FOOD) - 9 parameters
    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount, float rating) {
        this(name, description, calories, price, time, imageResId, soldCount, likeCount, rating, FoodType.FOOD);
    }

    // Constructor without rating and type (8 parameters)
    public FoodItem(String name, String description, String calories, String price, String time,
                    int imageResId, int soldCount, int likeCount) {
        this(name, description, calories, price, time, imageResId, soldCount, likeCount, 0f, FoodType.FOOD);
    }

    // Old constructor (6 parameters)
    public FoodItem(String name, String description, String calories, String price, String time, int imageResId) {
        this(name, description, calories, price, time, imageResId, 0, 0, 0f, FoodType.FOOD);
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
    public float getRating() { return rating; }
    public FoodType getType() { return type; }

    // Setters
    public void setRating(float rating) { this.rating = rating; }
    public void setType(FoodType type) { this.type = type; }

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
