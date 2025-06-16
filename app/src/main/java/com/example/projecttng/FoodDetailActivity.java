package com.example.projecttng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailActivity extends AppCompatActivity {

    TextView tvName, tvDescription, tvPrice, tvType;
    ImageView ivFood;
    ImageButton btnBack;
    Button btnDetails, btnReviews, btnAddToCart;

    // Nếu layout có hỗ trợ hiển thị tổng, khai báo 2 TextView này
    TextView tvItemCount, tvTotalPrice;

    final int quantity = 1; // Mặc định là 1 món

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // Ánh xạ view
        tvName = findViewById(R.id.tv_food_name);
        tvDescription = findViewById(R.id.tv_food_description);
        tvPrice = findViewById(R.id.tv_food_price);
        tvType = findViewById(R.id.tv_food_type);
        ivFood = findViewById(R.id.img_food_detail);

        btnBack = findViewById(R.id.btn_back);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnDetails = findViewById(R.id.btn_details);
        btnReviews = findViewById(R.id.btn_reviews);

        // Optional: nếu có hỗ trợ hiển thị tổng món/tổng tiền
        // tvItemCount = findViewById(R.id.tv_item_count);
        // tvTotalPrice = findViewById(R.id.tv_total_price);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String calories = intent.getStringExtra("calories");
        String time = intent.getStringExtra("time");
        String typeStr = intent.getStringExtra("type");
        int imageResId = intent.getIntExtra("imageResId", R.drawable.ic_launcher_foreground);

        // Hiển thị dữ liệu
        tvName.setText(name);
        tvDescription.setText(description);
        tvPrice.setText("Giá: " + price);
        tvType.setText("Loại: " + typeStr);
        ivFood.setImageResource(imageResId);

        // Nút quay lại
        btnBack.setOnClickListener(view -> finish());

        // Nút Thêm vào giỏ hàng
        btnAddToCart.setOnClickListener(v -> {
            // Chuyển từ string sang enum
            FoodItem.FoodType foodType = FoodItem.FoodType.FOOD;
            if ("Đồ uống".equals(typeStr)) {
                foodType = FoodItem.FoodType.DRINK;
            }

            // Tạo món ăn
            FoodItem foodItem = new FoodItem(name, description, calories, price, time, imageResId);
            foodItem.setType(foodType);
            foodItem.setQuantity(quantity); // Luôn là 1

            // Thêm vào giỏ
            CartManager.addItem(foodItem);

            // Cập nhật UI nếu có
            updateSummaryUI();

            // Thông báo
            Toast.makeText(this, name + " đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

            // Chuyển sang giỏ hàng
            Intent cartIntent = new Intent(FoodDetailActivity.this, ShoppingCartActivity.class);
            startActivity(cartIntent);
        });

        // Tab "Details"
        btnDetails.setOnClickListener(v -> {
            btnDetails.setBackgroundTintList(getColorStateList(R.color.red_700));
            btnDetails.setTextColor(getColor(R.color.white));

            btnReviews.setBackgroundTintList(getColorStateList(R.color.gray_light));
            btnReviews.setTextColor(getColor(R.color.red_700));

            tvDescription.setVisibility(View.VISIBLE);
        });

        // Tab "Reviews"
        btnReviews.setOnClickListener(v -> {
            btnReviews.setBackgroundTintList(getColorStateList(R.color.red_700));
            btnReviews.setTextColor(getColor(R.color.white));

            btnDetails.setBackgroundTintList(getColorStateList(R.color.gray_light));
            btnDetails.setTextColor(getColor(R.color.red_700));

            tvDescription.setVisibility(View.GONE);
            Toast.makeText(this, "Hiển thị đánh giá (chưa có nội dung)", Toast.LENGTH_SHORT).show();
        });
    }

    // Cập nhật tổng số món và giá
    private void updateSummaryUI() {
        int totalQuantity = CartManager.getTotalQuantity();
        int totalPrice = CartManager.getTotalPrice();

        // Nếu layout có các TextView này thì gỡ comment
        // tvItemCount.setText(totalQuantity + " món đang chọn");
        // tvTotalPrice.setText(formatCurrency(totalPrice));
    }

    // Định dạng tiền VNĐ
    private String formatCurrency(int amount) {
        return String.format("%,dđ", amount);
    }
}
