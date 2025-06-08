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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);

                if (id == R.id.nav_category) {
                    startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                    return true;
                } else if (id == R.id.nav_product) {
                    startActivity(new Intent(MainActivity.this, ProductActivity.class));
                    return true;
                } else if (id == R.id.nav_order) {
                    startActivity(new Intent(MainActivity.this, OrderActivity.class));
                    return true;
                } else if (id == R.id.nav_shopping_cart) {
                    startActivity(new Intent(MainActivity.this, ShoppingCartActivity.class));
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.btn_menu).setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        // Top-right profile button
        View btnProfile = findViewById(R.id.btn_profile);
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
            });
        }

        // Bottom navigation profile icon
        ImageView iconProfile = findViewById(R.id.icon_profile);
        iconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}