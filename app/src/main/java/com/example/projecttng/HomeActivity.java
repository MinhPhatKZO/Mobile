package com.example.projecttng;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView btnMenu;

    private RecyclerView rvFood;
    private FoodAdapter foodAdapter;
    private List<FoodItem> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ánh xạ view
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btnMenu = findViewById(R.id.btn_menu);

        // Mở menu khi nhấn nút
        btnMenu.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        // Xử lý chọn mục trong Navigation Drawer
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            int id = menuItem.getItemId();

            if (id == R.id.nav_category) {
                startActivity(new Intent(HomeActivity.this, CategoryActivity.class));
            } else if (id == R.id.nav_product) {
                startActivity(new Intent(HomeActivity.this, ProductActivity.class));
            } else if (id == R.id.nav_order) {
                startActivity(new Intent(HomeActivity.this, OrderActivity.class));
            } else if (id == R.id.nav_shopping_cart) {
                startActivity(new Intent(HomeActivity.this, ShoppingCartActivity.class));
            }

            return true;
        });

        // Xử lý Bottom Navigation
        ImageView iconProfile = findViewById(R.id.icon_profile);
        iconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        // Khởi tạo RecyclerView danh sách món ăn
        rvFood = findViewById(R.id.rv_food);
        foodList = new ArrayList<>();
        loadFoodData();
        foodAdapter = new FoodAdapter(this, foodList);
        rvFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvFood.setAdapter(foodAdapter);
    }

    private void loadFoodData() {
        foodList.add(new FoodItem("Sushi Maki", "Sushi tươi ngon chuẩn vị Nhật", "100kcal", "40.000đ", "60min", R.drawable.sushi));
        foodList.add(new FoodItem("Bánh Mì", "Bánh mì giòn rụm thơm ngon", "200kcal", "25.000đ", "30min", R.drawable.banhmi));
        foodList.add(new FoodItem("Trà Sữa", "Trà sữa béo ngậy, vị thơm", "150kcal", "30.000đ", "10min", R.drawable.trasua));
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
