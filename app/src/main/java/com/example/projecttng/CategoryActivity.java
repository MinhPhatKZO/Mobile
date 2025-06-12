package com.example.projecttng;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // 1. Ánh xạ RecyclerView
        RecyclerView rvCategoryItems = findViewById(R.id.rv_category_items);

        // 2. Gán LayoutManager cho RecyclerView
        rvCategoryItems.setLayoutManager(new LinearLayoutManager(this));

        // 3. Tạo danh sách món ăn
        List<FoodItem> categoryFoodList = new ArrayList<>();
        categoryFoodList.add(new FoodItem("Sushi Maki", "Sushi tươi ngon chuẩn vị Nhật", "100kcal", "40.000đ", "60min", R.drawable.sushi, 700, 6));
        categoryFoodList.add(new FoodItem("Bánh Mì", "Bánh mì giòn rụm thơm ngon", "200kcal", "25.000đ", "30min", R.drawable.banhmi, 600, 7));
        categoryFoodList.add(new FoodItem("Trà Sữa", "Trà sữa béo ngậy, vị thơm", "150kcal", "30.000đ", "10min", R.drawable.trasua, 800, 10));

        // 4. Gắn Adapter cho RecyclerView
        rvCategoryItems.setAdapter(new CategoryFoodAdapter(categoryFoodList));
    }
}
