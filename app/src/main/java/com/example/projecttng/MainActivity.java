package com.example.projecttng;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
        setContentView(R.layout.activity_home); // Đảm bảo layout đúng

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_category) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                    return true;
                } else if (id == R.id.nav_product) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(MainActivity.this, ProductActivity.class));
                    return true;
                } else if (id == R.id.nav_order) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(MainActivity.this, OrderActivity.class));
                    return true;
                } else if (id == R.id.nav_shopping_cart) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(MainActivity.this, ShoppingCartActivity.class));
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.btn_menu).setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        // Sự kiện click avatar user
        findViewById(R.id.icon_profile).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });
    }
}
