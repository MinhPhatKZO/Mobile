package com.example.projecttng.activity.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.adapter.CartAdapter;
import com.example.projecttng.dao.CartDao;
import com.example.projecttng.dao.OrderDao;
import com.example.projecttng.dao.OrderDetailDao;
import com.example.projecttng.model.FoodItem;
import com.example.projecttng.model.Order;
import com.example.projecttng.model.OrderDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShoppingCartActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvTitle, tvItemCount, tvTotalPrice;
    private Button btnAddMore, btnCheckout;
    private RadioGroup rgPayment;
    private RecyclerView rvCartItems;

    private CartDao cartDao;
    private CartAdapter cartAdapter;
    private List<FoodItem> cartItemList;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        setContentView(R.layout.activity_shoppingcart);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userId = prefs.getInt("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Không xác định được người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnBack = findViewById(R.id.btn_back);
        tvTitle = findViewById(R.id.tv_title);
        tvItemCount = findViewById(R.id.tv_item_count);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btnAddMore = findViewById(R.id.btn_add_more);
        btnCheckout = findViewById(R.id.btn_checkout);
        rgPayment = findViewById(R.id.rg_payment);
        rvCartItems = findViewById(R.id.rv_cart_items);

        cartDao = new CartDao(this);

        setupCart();

        btnBack.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        });

        btnAddMore.setOnClickListener(view -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        });

        btnCheckout.setOnClickListener(view -> handleCheckout());
    }

    private void setupCart() {
        cartItemList = cartDao.getAllCartItems(userId);
        cartAdapter = new CartAdapter(this, cartItemList, this::updateCartSummary, userId);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        rvCartItems.setAdapter(cartAdapter);
        updateCartSummary();
    }

    private void updateCartSummary() {
        int totalQuantity = 0;
        int totalPrice = 0;

        for (FoodItem item : cartItemList) {
            totalQuantity += item.getQuantity();
            totalPrice += item.getParsedPrice() * item.getQuantity();
        }

        tvItemCount.setText(totalQuantity + " Món đang chọn");
        tvTotalPrice.setText(formatCurrency(totalPrice));
    }

    private String formatCurrency(int amount) {
        return String.format("%,d đ", amount).replace(",", ".");
    }

    private void handleCheckout() {
        int selectedId = rgPayment.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cartItemList.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        String method;
        if (selectedId == R.id.rb_cod) {
            method = "COD";
        } else if (selectedId == R.id.rb_vnpay) {
            method = "VNPay";
        } else if (selectedId == R.id.rb_zalopay) {
            method = "ZaloPay";
        } else {
            Toast.makeText(this, "Phương thức thanh toán không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalPrice = 0;
        for (FoodItem item : cartItemList) {
            totalPrice += item.getParsedPrice() * item.getQuantity();
        }

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Order order = new Order(userId, totalPrice, method, date);

        OrderDao orderDao = new OrderDao(this);
        long orderId = orderDao.insertOrder(order);

        if (orderId == -1) {
            Toast.makeText(this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
            return;
        }

        List<OrderDetail> detailList = new ArrayList<>();
        for (FoodItem item : cartItemList) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderId((int) orderId);
            detail.setFoodId(item.getId());
            detail.setQuantity(item.getQuantity());
            detailList.add(detail);
        }

        OrderDetailDao detailDao = new OrderDetailDao(this);
        boolean success = detailDao.insertMultipleOrderDetails(detailList);

        if (!success) {
            Toast.makeText(this, "Lỗi khi lưu chi tiết đơn hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        cartDao.clearCart(userId);

        Toast.makeText(this, "Đặt hàng thành công bằng " + method, Toast.LENGTH_LONG).show();

        Intent intent;
        if (method.equals("ZaloPay")) {
            intent = new Intent(this, OrderZaloPayActivity.class);
            intent.putExtra("orderId", (int) orderId);
            intent.putExtra("amount", totalPrice);
        } else {
            intent = new Intent(this, OrderActivity.class);
            intent.putExtra("orderId", (int) orderId);
        }

        startActivity(intent);
        finish();
    }
}
