package com.example.projecttng.model;

import java.util.ArrayList;
import java.util.List;

public class CartItem {
    public int id;
    public int foodId;
    public int quantity;
    public FoodItem food;

    public CartItem(int id, int foodId, int quantity, FoodItem food) {
        this.id = id;
        this.foodId = foodId;
        this.quantity = quantity;
        this.food = food;
    }

    public static List<FoodItem> toFoodItemList(List<CartItem> cartItems) {
        List<FoodItem> result = new ArrayList<>();
        for (CartItem ci : cartItems) {
            FoodItem f = ci.food;
            f.setQuantity(ci.quantity);
            result.add(f);
        }
        return result;
    }
}
