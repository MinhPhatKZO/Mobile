package com.example.projecttng;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private TextView tvTotalPrice, tvItemCount;
    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private List<FoodItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopingcart);

        // Ánh xạ View
        ImageView btnBack = findViewById(R.id.btn_back);
        Button btnAddMore = findViewById(R.id.btn_add_more);
        Button btnCheckout = findViewById(R.id.btn_checkout);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        tvItemCount = findViewById(R.id.tv_item_count);
        rvCartItems = findViewById(R.id.rv_cart_items);

        // Lấy dữ liệu từ giỏ hàng
        cartItems = CartManager.getCartItems();

        // Cài đặt RecyclerView
        cartAdapter = new CartAdapter(this, cartItems, this::updateSummaryUI);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        rvCartItems.setAdapter(cartAdapter);

        // Gán sự kiện
        btnBack.setOnClickListener(v -> finish());

        btnAddMore.setOnClickListener(v ->
                startActivity(new Intent(this, CategoryActivity.class))
        );

        btnCheckout.setOnClickListener(v -> {
            int totalAmount = CartManager.getTotalPrice();
            Toast.makeText(this, "Tiến hành thanh toán " + formatCurrency(totalAmount), Toast.LENGTH_SHORT).show();
            // TODO: startActivity(new Intent(this, CheckoutActivity.class));
        });

        // Hiển thị thông tin tổng cộng ban đầu
        updateSummaryUI();
    }

    // Cập nhật tổng tiền & số lượng món
    private void updateSummaryUI() {
        int totalQuantity = CartManager.getTotalQuantity();
        int totalPrice = CartManager.getTotalPrice();

        tvItemCount.setText(totalQuantity + " món đang chọn");
        tvTotalPrice.setText(formatCurrency(totalPrice));
    }

    // Format tiền tệ
    private String formatCurrency(int amount) {
        return String.format("%,d đ", amount).replace(',', '.');
    }
}
