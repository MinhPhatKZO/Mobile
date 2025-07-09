package com.example.projecttng.activity;

import android.content.Intent;
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

import com.example.projecttng.activity.ui.CategoryActivity;
import com.example.projecttng.activity.ui.FavoriteActivity;
import com.example.projecttng.activity.ui.OrderActivity;
import com.example.projecttng.activity.ui.ShoppingCartActivity;
import com.example.projecttng.activity.ui.UserProfileActivity;
import com.example.projecttng.model.FoodItem;
import com.example.projecttng.R;
import com.example.projecttng.adapter.FoodAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<FoodItem> allFoodItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.rv_food);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo dữ liệu mẫu
        loadSampleFoodItems();

        // Khởi tạo adapter với tất cả món ăn
        foodAdapter = new FoodAdapter(this, new ArrayList<>(allFoodItems));
        recyclerView.setAdapter(foodAdapter);

        // Mở Navigation Drawer khi nhấn nút menu
        findViewById(R.id.btn_menu).setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        // Mở trang profile khi nhấn nút góc trên
        View btnProfile = findViewById(R.id.btn_profile);
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                startActivityWithAnimation(UserProfileActivity.class);
            });
        }

        // Lọc theo loại DRINK
        Button btnDrink = findViewById(R.id.btn_drink);
        btnDrink.setOnClickListener(v -> filterFoodByType(FoodItem.FoodType.DRINK));

        // Mở trang profile khi nhấn biểu tượng ở thanh dưới
        ImageView iconProfile = findViewById(R.id.icon_profile);
        if (iconProfile != null) {
            iconProfile.setOnClickListener(v -> {
                startActivityWithAnimation(UserProfileActivity.class);
            });
        }

        // ✅ Mở trang món yêu thích khi nhấn biểu tượng ở thanh dưới
        ImageView iconFavorite = findViewById(R.id.icon_favorite);
        if (iconFavorite != null) {
            iconFavorite.setOnClickListener(v -> {
                startActivityWithAnimation(FavoriteActivity.class);
            });
        }

        // Xử lý chọn item trong Navigation Drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);

                if (id == R.id.nav_category) {
                    startActivityWithAnimation(CategoryActivity.class);
                    return true;
                } else if (id == R.id.nav_order) {
                    startActivityWithAnimation(OrderActivity.class);
                    return true;
                } else if (id == R.id.nav_shopping_cart) {
                    startActivityWithAnimation(ShoppingCartActivity.class);
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Xử lý lọc theo loại món ăn
        Button btnHome = findViewById(R.id.btn_home);
        Button btnFastFood = findViewById(R.id.btn_fastfood);
        Button btnDessert = findViewById(R.id.btn_dessert);

        btnHome.setOnClickListener(v -> foodAdapter.setFoodList(new ArrayList<>(allFoodItems)));
        btnFastFood.setOnClickListener(v -> filterFoodByType(FoodItem.FoodType.FOOD));
        btnDessert.setOnClickListener(v -> filterFoodByType(FoodItem.FoodType.DESSERT));
    }

    // Lọc món ăn theo loại
    private void filterFoodByType(FoodItem.FoodType type) {
        List<FoodItem> filtered = new ArrayList<>();
        for (FoodItem item : allFoodItems) {
            if (item.getType() == type) {
                filtered.add(item);
            }
        }
        foodAdapter.setFoodList(filtered);
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

    private void startActivityWithAnimation(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
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
