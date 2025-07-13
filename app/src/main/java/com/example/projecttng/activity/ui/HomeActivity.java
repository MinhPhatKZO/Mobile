package com.example.projecttng.activity.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.activity.auth.SignInActivity;
import com.example.projecttng.activity.sp.ChatActivity;
import com.example.projecttng.adapter.FoodAdapter;
import com.example.projecttng.dao.FoodDao;
import com.example.projecttng.model.FoodItem;
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
    private FoodDao foodDao;

    private ImageView iconProfile, iconFoodList, iconFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Kiểm tra đăng nhập
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.rv_food);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // DAO và dữ liệu
        foodDao = new FoodDao(this);
        foodDao.insertSampleFoodsIfEmpty();
        allFoodItems = foodDao.getAllFoods();
        foodAdapter = new FoodAdapter(this, new ArrayList<>(allFoodItems));
        recyclerView.setAdapter(foodAdapter);

        // Mở drawer menu
        findViewById(R.id.btn_menu).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Các icon điều hướng
        iconProfile = findViewById(R.id.icon_profile);
        iconFoodList = findViewById(R.id.icon_foodlist);
        iconFavorite = findViewById(R.id.icon_favorite); // ← THÊM

        if (iconProfile != null) {
            iconProfile.setOnClickListener(v -> startActivityWithAnimation(UserProfileActivity.class));
        }

        if (iconFoodList != null) {
            iconFoodList.setOnClickListener(v -> startActivityWithAnimation(ChatActivity.class));
        }

        if (iconFavorite != null) {
            iconFavorite.setOnClickListener(v -> startActivityWithAnimation(FavoriteActivity.class));
        }

        // Navigation Drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
            } else if (id == R.id.nav_favorite) {
                startActivity(new Intent(this, FavoriteActivity.class));
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, UserProfileActivity.class));
            } else if (id == R.id.nav_category) {
                startActivity(new Intent(this, CategoryActivity.class));
            } else if (id == R.id.nav_product) {
                startActivity(new Intent(this, ProductActivity.class));
            } else if (id == R.id.nav_order) {
                startActivity(new Intent(this, OrderZaloPayActivity.class));
            } else if (id == R.id.nav_shopping_cart) {
                startActivity(new Intent(this, ShoppingCartActivity.class));
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Filter buttons
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

        highlightFilter(btnHome);
    }

    @Override
    protected void onResume() {
        super.onResume();
        allFoodItems = foodDao.getAllFoods();
        foodAdapter.setFoodList(new ArrayList<>(allFoodItems));
    }

    private void filterFoodByType(FoodItem.FoodType type) {
        List<FoodItem> filtered = new ArrayList<>();
        for (FoodItem item : allFoodItems) {
            if (item.getType() == type) {
                filtered.add(item);
            }
        }
        foodAdapter.setFoodList(filtered);
    }

    private void highlightFilter(Button selected) {
        btnHome.setBackgroundColor(Color.LTGRAY);
        btnFastFood.setBackgroundColor(Color.LTGRAY);
        btnDessert.setBackgroundColor(Color.LTGRAY);
        btnDrink.setBackgroundColor(Color.LTGRAY);
        selected.setBackgroundColor(Color.DKGRAY);
    }

    private void startActivityWithAnimation(Class<?> cls) {
        Intent intent = new Intent(this, cls);
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
