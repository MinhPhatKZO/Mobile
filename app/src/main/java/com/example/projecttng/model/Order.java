package com.example.projecttng.model;

public class Order {
    private int id;
    private int userId;
    private int totalPrice;
    private String paymentMethod;
    private String orderDate;

    public Order() {}

    public Order(int userId, int totalPrice, String paymentMethod, String orderDate) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
}
