package com.example.projecttng.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.activity.sp.ChatActivity;
import com.example.projecttng.adapter.ChefAdapter;
import com.example.projecttng.adapter.FavoriteAdapter;
import com.example.projecttng.dao.FavoriteDao;
import com.example.projecttng.model.Chef;
import com.example.projecttng.model.FoodItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private FavoriteDao favoriteDao;
    private FavoriteAdapter favoriteAdapter;
    private ChefAdapter chefAdapter;
    private RecyclerView recyclerView;
    private TextView tvEmpty;
    private TabLayout tabLayout;

    private ImageView btnMenu, btnProfile;
    private ImageView iconHome, iconFoodList, iconFavorite, iconProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favoriteDao = new FavoriteDao(this);
        initViews();
        setupTabs();
        loadFavorites(); // Mặc định là tab Recipes
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tabLayout.getSelectedTabPosition() == 0) {
            loadFavorites();
        }
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_favorite);
        tvEmpty = findViewById(R.id.tv_empty);
        tabLayout = findViewById(R.id.tabLayout);

        btnMenu = findViewById(R.id.btn_menu);
        btnProfile = findViewById(R.id.btn_profile);

        iconHome = findViewById(R.id.icon_home);
        iconFoodList = findViewById(R.id.icon_foodlist);
        iconFavorite = findViewById(R.id.icon_favorite);
        iconProfile = findViewById(R.id.icon_profile);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoriteAdapter = new FavoriteAdapter(this, favoriteDao.getAllFavoriteFoods());
        recyclerView.setAdapter(favoriteAdapter);

        setupBottomNavigation();
    }

    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Recipes"));
        tabLayout.addTab(tabLayout.newTab().setText("Chefs"));
        tabLayout.selectTab(tabLayout.getTabAt(0));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    loadFavorites();
                } else {
                    loadFavoriteChefs();
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadFavorites() {
        List<FoodItem> list = favoriteDao.getAllFavoriteFoods();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoriteAdapter = new FavoriteAdapter(this, list);
        recyclerView.setAdapter(favoriteAdapter);

        tvEmpty.setText("Không có món yêu thích nào.");
        tvEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void loadFavoriteChefs() {
        List<Chef> chefs = getSampleChefs();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        chefAdapter = new ChefAdapter(this, chefs);
        recyclerView.setAdapter(chefAdapter);

        tvEmpty.setVisibility(chefs.isEmpty() ? View.VISIBLE : View.GONE);
        tvEmpty.setText("Không có đầu bếp yêu thích nào.");
    }

    private void setupBottomNavigation() {
        iconHome.setOnClickListener(v -> {
            startActivity(new Intent(FavoriteActivity.this, HomeActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        iconFoodList.setOnClickListener(v -> {
            startActivity(new Intent(FavoriteActivity.this, ChatActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        iconFavorite.setOnClickListener(v -> {
            // đang ở FavoriteActivity
        });

        iconProfile.setOnClickListener(v -> {
            startActivity(new Intent(FavoriteActivity.this, UserProfileActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }

    private List<Chef> getSampleChefs() {
        List<Chef> chefs = new ArrayList<>();
        chefs.add(new Chef(1, "Esther T.", R.drawable.chef1));
        chefs.add(new Chef(2, "Jenny M.", R.drawable.chef2));
        chefs.add(new Chef(3, "Jacob U.", R.drawable.chef3));
        chefs.add(new Chef(4, "Bessi K.", R.drawable.chef4));
        chefs.add(new Chef(5, "Arlene M.", R.drawable.chef5));
        chefs.add(new Chef(6, "Dianne R.", R.drawable.chef6));
        chefs.add(new Chef(7, "Cody F.", R.drawable.chef7));
        chefs.add(new Chef(8, "Leslie A.", R.drawable.chef8));
        chefs.add(new Chef(9, "Robert F.", R.drawable.chef9));
        chefs.add(new Chef(10, "Albert F.", R.drawable.chef10));
        chefs.add(new Chef(11, "Devon L.", R.drawable.chef11));
        chefs.add(new Chef(12, "Courtney H.", R.drawable.chef12));
        return chefs;
    }
}
