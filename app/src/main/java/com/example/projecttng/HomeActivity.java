package com.example.projecttng;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rvFood;
    FoodAdapter foodAdapter;
    List<FoodItem> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Tìm RecyclerView trong layout
        rvFood = findViewById(R.id.rv_food);

        // Khởi tạo danh sách món ăn
        foodList = new ArrayList<>();

        // Thêm dữ liệu món ăn
        foodList.add(new FoodItem("Sushi Maki", "Sushi tươi ngon chuẩn vị Nhật", "100kcal", "40.000đ", "60min", R.drawable.sushi));
        foodList.add(new FoodItem("Bánh Mì", "Bánh mì giòn rụm thơm ngon", "200kcal", "25.000đ", "30min", R.drawable.banhmi));
        foodList.add(new FoodItem("Trà Sữa", "Trà sữa béo ngậy, vị thơm", "150kcal", "30.000đ", "10min", R.drawable.trasua));

        // Tạo adapter với dữ liệu món ăn
        foodAdapter = new FoodAdapter(this, foodList);

        // Thiết lập LinearLayoutManager dạng ngang cho RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFood.setLayoutManager(layoutManager);

        // Gán adapter cho RecyclerView
        rvFood.setAdapter(foodAdapter);
    }
}
