package com.example.projecttng.activity.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;
import com.example.projecttng.dao.CartDao;
import com.example.projecttng.dao.FavoriteDao;
import com.example.projecttng.dao.FoodDao;
import com.example.projecttng.model.FoodItem;

public class FoodDetailActivity extends AppCompatActivity {

    TextView tvName, tvDescription, tvPrice, tvType;
    ImageView ivFood;
    ImageButton btnBack;
    Button btnDetails, btnReviews, btnAddToCart;

    TextView tvItemCount, tvTotalPrice;

    final int quantity = 1;

    CartDao cartDao;
    FoodDao foodDao;

    FoodItem foodItem; // món ăn hiện tại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // Init DAO
        cartDao = new CartDao(this);
        foodDao = new FoodDao(this);

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

        // Nhận id từ Intent
        Intent intent = getIntent();
        int foodId = intent.getIntExtra("id", -1);

        if (foodId == -1) {
            Toast.makeText(this, "Không tìm thấy món ăn", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Lấy món ăn từ SQLite
        foodItem = foodDao.getFoodById(foodId); // lấy dữ liệu từ foodDao

        if (foodItem == null) {
            Toast.makeText(this, "Món ăn không tồn tại", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Hiển thị dữ liệu
        tvName.setText(foodItem.getName());
        tvDescription.setText(foodItem.getDescription());
        tvPrice.setText("Giá: " + foodItem.getPrice());
        tvType.setText("Loại: " + foodItem.getType().getDisplayName());
        ivFood.setImageResource(foodItem.getImageResId());

        // Nút quay lại
        btnBack.setOnClickListener(view -> finish());

        // Nút "Thêm vào giỏ"
        btnAddToCart.setOnClickListener(v -> {
            // Lấy userId từ SharedPreferences
            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            int userId = prefs.getInt("userId", -1);

            if (userId == -1) {
                Toast.makeText(this, "Không xác định được người dùng", Toast.LENGTH_SHORT).show();
                return;
            }

            foodItem.setQuantity(quantity);
            cartDao.addOrUpdateItem(userId, foodItem); // ✅ truyền userId

            Toast.makeText(this, foodItem.getName() + " đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

            // Chuyển sang giỏ hàng
            Intent cartIntent = new Intent(FoodDetailActivity.this, ShoppingCartActivity.class);
            startActivity(cartIntent);
        });

        // Tab "Chi tiết"
        btnDetails.setOnClickListener(v -> {
            btnDetails.setBackgroundTintList(getColorStateList(R.color.red_700));
            btnDetails.setTextColor(getColor(R.color.white));

            btnReviews.setBackgroundTintList(getColorStateList(R.color.gray_light));
            btnReviews.setTextColor(getColor(R.color.red_700));

            tvDescription.setVisibility(View.VISIBLE);
        });

        // Tab "Đánh giá"
        btnReviews.setOnClickListener(v -> {
            btnReviews.setBackgroundTintList(getColorStateList(R.color.red_700));
            btnReviews.setTextColor(getColor(R.color.white));

            btnDetails.setBackgroundTintList(getColorStateList(R.color.gray_light));
            btnDetails.setTextColor(getColor(R.color.red_700));

            tvDescription.setVisibility(View.GONE);
            Toast.makeText(this, "Hiển thị đánh giá (chưa có nội dung)", Toast.LENGTH_SHORT).show();
        });
        Button btnFavorite = findViewById(R.id.btn_add_to_favorite);
        btnFavorite.setOnClickListener(v -> {
            FavoriteDao favoriteDao = new FavoriteDao(this);
            favoriteDao.addFavorite(foodItem.getId());
            Toast.makeText(this, "Đã thêm vào Recipes", Toast.LENGTH_SHORT).show();
        });

    }
}
