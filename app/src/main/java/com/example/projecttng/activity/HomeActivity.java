package com.example.projecttng.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.rv_food);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        foodDao = new FoodDao(this);
        foodDao.insertSampleFoodsIfEmpty(); // ✅ chỉ chèn dữ liệu nếu rỗng
        allFoodItems = foodDao.getAllFoods();

        foodAdapter = new FoodAdapter(this, new ArrayList<>(allFoodItems));
        recyclerView.setAdapter(foodAdapter);

        findViewById(R.id.btn_menu).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        View btnProfile = findViewById(R.id.btn_profile);
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> startActivityWithAnimation(UserProfileActivity.class));
        }

        ImageView iconProfile = findViewById(R.id.icon_profile);
        if (iconProfile != null) {
            iconProfile.setOnClickListener(v -> startActivityWithAnimation(UserProfileActivity.class));
        }

        // ✅ Thêm xử lý khi nhấn vào icon_foodlist để mở ChatActivity
        ImageView iconFoodList = findViewById(R.id.icon_foodlist);
        if (iconFoodList != null) {
            iconFoodList.setOnClickListener(v -> startActivityWithAnimation(ChatActivity.class));
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
            } else if (id == R.id.nav_favorite) {
                startActivity(new Intent(this, FavoriteActivity.class));
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(this, SearchActivity.class));
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, UserProfileActivity.class));
            } else if (id == R.id.nav_category) {
                startActivity(new Intent(this, CategoryActivity.class));
            } else if (id == R.id.nav_product) {
                startActivity(new Intent(this, ProductActivity.class));
            } else if (id == R.id.nav_order) {
                startActivity(new Intent(this, OrderActivity.class));
            } else if (id == R.id.nav_shopping_cart) {
                startActivity(new Intent(this, ShoppingCartActivity.class));
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

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

        highlightFilter(btnHome); // Mặc định chọn btnHome
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
