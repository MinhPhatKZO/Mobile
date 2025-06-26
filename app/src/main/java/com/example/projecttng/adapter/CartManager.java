package com.example.projecttng.adapter;

import com.example.projecttng.model.FoodItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartManager {
    private static final List<FoodItem> cartItems = new ArrayList<>();

    // Thêm món vào giỏ hàng
    public static synchronized void addItem(FoodItem item) {
        for (FoodItem i : cartItems) {
            if (i.getId().equals(item.getId())) {
                i.setQuantity(i.getQuantity() + 1);
                return;
            }
        }
        cartItems.add(item);
    }

    // Cập nhật số lượng món
    public static synchronized void updateItemQuantity(String id, int quantity) {
        for (FoodItem item : cartItems) {
            if (item.getId().equals(id)) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    // Xóa món khỏi giỏ hàng
    public static synchronized void removeItem(String id) {
        Iterator<FoodItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            FoodItem item = iterator.next();
            if (item.getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }

    // Kiểm tra món đã tồn tại chưa
    public static synchronized boolean containsItem(String id) {
        for (FoodItem item : cartItems) {
            if (item.getId().equals(id)) return true;
        }
        return false;
    }

    // Lấy danh sách giỏ hàng
    public static synchronized List<FoodItem> getCartItems() {
        return new ArrayList<>(cartItems); // trả về bản sao để tránh bị thay đổi từ bên ngoài
    }

    // Tổng số món trong giỏ
    public static synchronized int getTotalQuantity() {
        int total = 0;
        for (FoodItem item : cartItems) {
            total += item.getQuantity();
        }
        return total;
    }

    // Tổng tiền
    public static synchronized int getTotalPrice() {
        int total = 0;
        for (FoodItem item : cartItems) {
            total += item.getParsedPrice() * item.getQuantity();
        }
        return total;
    }

    // Xóa toàn bộ giỏ hàng
    public static synchronized void clearCart() {
        cartItems.clear();
    }

    // Singleton style nếu cần dùng getInstance()
    public static CartManager getInstance() {
        return new CartManager(); // bạn có thể chuyển sang Singleton thực sự nếu cần
    }
}
