package com.example.projecttng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.adapter.CartAdapter;
import com.example.projecttng.adapter.CartManager;
import com.example.projecttng.model.FoodItem;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvTitle, tvItemCount, tvTotalPrice;
    private Button btnAddMore, btnCheckout;
    private RadioGroup rgPayment;
    private RadioButton rbCod, rbMomo, rbVnpay;
    private RecyclerView rvCartItems;

    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);

        // Ánh xạ view
        btnBack = findViewById(R.id.btn_back);
        tvTitle = findViewById(R.id.tv_title);
        tvItemCount = findViewById(R.id.tv_item_count);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btnAddMore = findViewById(R.id.btn_add_more);
        btnCheckout = findViewById(R.id.btn_checkout);
        rgPayment = findViewById(R.id.rg_payment);
        rbCod = findViewById(R.id.rb_cod);
        rbMomo = findViewById(R.id.rb_momo);
        rbVnpay = findViewById(R.id.rb_vnpay);
        rvCartItems = findViewById(R.id.rv_cart_items);

        // Gán adapter và dữ liệu
        setupCart();

        // Nút quay lại
        btnBack.setOnClickListener(view -> finish());

        // Nút chọn thêm món → về CategoryActivity
        btnAddMore.setOnClickListener(view -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            startActivity(intent);
        });

        // Nút thanh toán
        btnCheckout.setOnClickListener(view -> {
            int selectedId = rgPayment.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            String method = "";
            if (selectedId == R.id.rb_cod) method = "COD";
            else if (selectedId == R.id.rb_momo) method = "Momo";
            else if (selectedId == R.id.rb_vnpay) method = "VNPay";

            Toast.makeText(this, "Bạn đã chọn thanh toán bằng " + method, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        });
    }

    private void setupCart() {
        List<FoodItem> items = CartManager.getInstance().getCartItems();

        cartAdapter = new CartAdapter(this, items, this::updateCartSummary);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        rvCartItems.setAdapter(cartAdapter);

        updateCartSummary(); // Gọi cập nhật số món và giá tổng ban đầu
    }

    private void updateCartSummary() {
        int totalQuantity = CartManager.getInstance().getTotalQuantity();
        int totalPrice = CartManager.getInstance().getTotalPrice();

        tvItemCount.setText(totalQuantity + " Món đang chọn");
        tvTotalPrice.setText(formatCurrency(totalPrice));
    }

    private String formatCurrency(int amount) {
        return String.format("%,d đ", amount).replace(",", ".");
    }
}
