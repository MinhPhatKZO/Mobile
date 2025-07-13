package com.example.projecttng.activity.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.adapter.OrderDetailAdapter;
import com.example.projecttng.dao.OrderDao;
import com.example.projecttng.dao.OrderDetailDao;
import com.example.projecttng.model.FoodItem;
import com.example.projecttng.model.Order;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private TextView tvOrderId, tvPaymentMethod, tvTotalPrice, tvOrderDate;
    private RecyclerView rvOrderDetails;

    private int orderId;
    private OrderDao orderDao;
    private OrderDetailDao detailDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        setContentView(R.layout.activity_order);

        initViews();
        initData();
    }

    private void initViews() {
        tvOrderId = findViewById(R.id.tv_order_id);
        tvPaymentMethod = findViewById(R.id.tv_payment_method);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        tvOrderDate = findViewById(R.id.tv_order_date);
        rvOrderDetails = findViewById(R.id.rv_order_details);
    }

    private void initData() {
        orderId = getIntent().getIntExtra("orderId", -1);
        if (orderId == -1) {
            Toast.makeText(this, "Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        orderDao = new OrderDao(this);
        detailDao = new OrderDetailDao(this);

        loadOrderInfo();
    }

    private void loadOrderInfo() {
        Order order = orderDao.getOrderById(orderId);
        if (order == null) {
            Toast.makeText(this, "Không tìm thấy thông tin đơn hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set thông tin đơn hàng
        tvOrderId.setText("Mã đơn: #" + orderId);
        tvPaymentMethod.setText("Thanh toán: " + order.getPaymentMethod());
        tvTotalPrice.setText("Tổng tiền: " + formatCurrency(order.getTotalPrice()));
        tvOrderDate.setText("Ngày đặt: " + order.getOrderDate());

        // Lấy danh sách món ăn trong đơn hàng
        List<FoodItem> foodItems = detailDao.getFoodItemsByOrderId(orderId);

        // Setup RecyclerView
        OrderDetailAdapter adapter = new OrderDetailAdapter(this, foodItems);
        rvOrderDetails.setLayoutManager(new LinearLayoutManager(this));
        rvOrderDetails.setAdapter(adapter);
    }

    private String formatCurrency(int amount) {
        return String.format("%,d đ", amount).replace(",", ".");
    }
}
