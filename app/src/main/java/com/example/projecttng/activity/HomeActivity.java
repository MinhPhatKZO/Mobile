package com.example.projecttng.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.model.FoodItem;
import com.example.projecttng.R;
import com.example.projecttng.adapter.FoodAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<FoodItem> allFoodItems = new ArrayList<>();

    private Button btnHome, btnFastFood, btnDessert, btnDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Khởi tạo View
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.rv_food);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Load dữ liệu mẫu
        loadSampleFoodItems();

        // Adapter
        foodAdapter = new FoodAdapter(this, new ArrayList<>(allFoodItems));
        recyclerView.setAdapter(foodAdapter);

        // Mở menu
        findViewById(R.id.btn_menu).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Xử lý click profile
        View btnProfile = findViewById(R.id.btn_profile);
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> startActivityWithAnimation(UserProfileActivity.class));
        }

        ImageView iconProfile = findViewById(R.id.icon_profile);
        if (iconProfile != null) {
            iconProfile.setOnClickListener(v -> startActivityWithAnimation(UserProfileActivity.class));
        }

        // Xử lý các mục trong NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class)); // Quay lại màn hình chính
                } else if (id == R.id.nav_favorite) {
                    startActivity(new Intent(HomeActivity.this, FavoriteActivity.class)); // Chuyển đến danh mục
                } else if (id == R.id.nav_search) {
                    startActivity(new Intent(HomeActivity.this, SearchActivity.class)); // Chuyển đến sản phẩm
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(HomeActivity.this, UserProfileActivity.class)); // Hồ sơ người dùng
                }
                else if (id == R.id.nav_category) {
                    startActivity(new Intent(HomeActivity.this, CategoryActivity.class));
                } else if (id == R.id.nav_product) {
                    startActivity(new Intent(HomeActivity.this, ProductActivity.class));
                } else if (id == R.id.nav_order) {
                    startActivity(new Intent(HomeActivity.this, OrderActivity.class));
                } else if (id == R.id.nav_shopping_cart) {
                    startActivity(new Intent(HomeActivity.this, ShoppingCartActivity.class));
                }


                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // Gán nút lọc
        btnHome = findViewById(R.id.btn_home);
        btnFastFood = findViewById(R.id.btn_fastfood);
        btnDessert = findViewById(R.id.btn_dessert);
        btnDrink = findViewById(R.id.btn_drink);

        btnHome.setOnClickListener(v -> {
            foodAdapter.setFoodList(new ArrayList<>(allFoodItems));
            highlightFilter(btnHome);
        });

        btnFastFood.setOnClickListener(v -> {
            filterFoodByType(FoodItem.FoodType.FOOD);
            highlightFilter(btnFastFood);
        });

        btnDessert.setOnClickListener(v -> {
            filterFoodByType(FoodItem.FoodType.DESSERT);
            highlightFilter(btnDessert);
        });

        btnDrink.setOnClickListener(v -> {
            filterFoodByType(FoodItem.FoodType.DRINK);
            highlightFilter(btnDrink);
        });
    }

    // Lọc món theo loại
    private void filterFoodByType(FoodItem.FoodType type) {
        List<FoodItem> filtered = new ArrayList<>();
        for (FoodItem item : allFoodItems) {
            if (item.getType() == type) {
                filtered.add(item);
            }
        }
        foodAdapter.setFoodList(filtered);
    }

    // Đổi màu nút được chọn
    private void highlightFilter(Button selected) {
        btnHome.setBackgroundColor(Color.LTGRAY);
        btnFastFood.setBackgroundColor(Color.LTGRAY);
        btnDessert.setBackgroundColor(Color.LTGRAY);
        btnDrink.setBackgroundColor(Color.LTGRAY);

        selected.setBackgroundColor(Color.DKGRAY);
    }

    // Dữ liệu mẫu
    private void loadSampleFoodItems() {
        allFoodItems.add(new FoodItem("Sushi Maki", "Sushi tươi ngon chuẩn vị Nhật", "100kcal", "40.000đ", "60min", R.drawable.sushi, 100, 10, 4.8f, FoodItem.FoodType.FOOD));
        allFoodItems.add(new FoodItem("Bánh Mì", "Bánh mì giòn rụm thơm ngon", "200kcal", "25.000đ", "30min", R.drawable.banhmi, 100, 10, 4.5f, FoodItem.FoodType.FOOD));
        allFoodItems.add(new FoodItem("Gà Rán", "Gà rán giòn tan thơm ngon", "1500kcal", "40.000đ", "60min", R.drawable.garan, 80, 8, 4.6f, FoodItem.FoodType.FOOD));
        allFoodItems.add(new FoodItem("Bún Bò", "Sợi bún hòa huyện với nước lèo béo ngậy", "2000kcal", "35.000đ", "30min", R.drawable.bunbo, 90, 9, 4.7f, FoodItem.FoodType.FOOD));
        allFoodItems.add(new FoodItem("Trà Đá", "Trà đá mát lạnh", "10kcal", "5.000đ", "2min", R.drawable.trada, 50, 5, 4.0f, FoodItem.FoodType.DRINK));
        allFoodItems.add(new FoodItem("Trà Sữa", "Trà sữa béo ngậy, vị thơm", "150kcal", "30.000đ", "10min", R.drawable.trasua, 60, 6, 4.2f, FoodItem.FoodType.DRINK));
    }

    // Mở Activity có hiệu ứng mượt
    private void startActivityWithAnimation(Class<?> cls) {
        Intent intent = new Intent(HomeActivity.this, cls);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
