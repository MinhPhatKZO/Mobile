package com.example.projecttng;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

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

        // Mở trang profile khi nhấn biểu tượng ở thanh dưới
        ImageView iconProfile = findViewById(R.id.icon_profile);
        if (iconProfile != null) {
            iconProfile.setOnClickListener(v -> {
                startActivityWithAnimation(UserProfileActivity.class);
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
    }

    // Thêm hiệu ứng khi mở activity mới
    private void startActivityWithAnimation(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    // Hàm xử lý đăng xuất (tuỳ chọn nếu có chức năng logout)
    private void logoutUser() {
        // TODO: Xoá session, shared preferences...
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Xử lý nút Back khi Drawer đang mở
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}