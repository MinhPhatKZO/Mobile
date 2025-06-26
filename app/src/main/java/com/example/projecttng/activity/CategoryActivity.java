package com.example.projecttng.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.model.FoodItem;
import com.example.projecttng.R;
import com.example.projecttng.adapter.CategoryFoodAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView rvCategoryItems;
    private CategoryFoodAdapter foodAdapter;
    private List<FoodItem> allFoodList;
    private List<FoodItem> displayList;

    private ChipGroup chipGroupFilter;
    private Chip chipAll, chipFood, chipDrink;
    private TextView tvCategoryTitle, tvCategoryDescription, tvItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initViews();
        loadCategoryData();
        setupRecyclerView();
        setupFilterListeners();
    }

    private void initViews() {
        rvCategoryItems = findViewById(R.id.rv_category_items);
        chipGroupFilter = findViewById(R.id.chip_group_filter);
        chipAll = findViewById(R.id.chip_all);
        chipFood = findViewById(R.id.chip_food);
        chipDrink = findViewById(R.id.chip_drink);
        tvCategoryTitle = findViewById(R.id.tv_category_title);
        tvCategoryDescription = findViewById(R.id.tv_category_description);
        tvItemCount = findViewById(R.id.tv_item_count);

        // Nút ẩn/hiện bộ lọc
        findViewById(R.id.btn_filter_category).setOnClickListener(v -> {
            chipGroupFilter.setVisibility(
                    chipGroupFilter.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
            );
        });
    }

    private void loadCategoryData() {
        allFoodList = new ArrayList<>();
        displayList = new ArrayList<>();

        allFoodList.add(new FoodItem("Sushi Maki", "Sushi tươi ngon chuẩn vị Nhật", "100kcal", "45.000đ", "15min", R.drawable.sushi, 0, 0, 4.5f, FoodItem.FoodType.FOOD));
        allFoodList.add(new FoodItem("Bánh Mì", "Bánh mì giòn rụm thơm ngon", "200kcal", "25.000đ", "30min", R.drawable.banhmi, 0, 0, 4.8f, FoodItem.FoodType.FOOD));
        allFoodList.add(new FoodItem("Gà Rán", "Gà rán giòn tan", "1500kcal", "40.000đ", "10min", R.drawable.garan, 0, 0, 4.3f, FoodItem.FoodType.FOOD));
        allFoodList.add(new FoodItem("Bún Bò", "Sợi bún hòa huyện với nước lèo béo ngậy", "2000kcal", "40.000đ", "10min", R.drawable.bunbo, 0, 0, 4.3f, FoodItem.FoodType.FOOD));

        allFoodList.add(new FoodItem("Trà Sữa", "Trà sữa béo ngậy, vị thơm", "150kcal", "30.000đ", "5min", R.drawable.trasua, 0, 0, 4.7f, FoodItem.FoodType.DRINK));
        allFoodList.add(new FoodItem("Cafe Đá", "Cafe đá đậm vị", "5kcal", "18.000đ", "3min", R.drawable.trada, 0, 0, 4.3f, FoodItem.FoodType.DRINK));

        displayList.addAll(allFoodList); // Hiển thị mặc định
    }

    private void setupRecyclerView() {
        foodAdapter = new CategoryFoodAdapter(displayList);
        rvCategoryItems.setLayoutManager(new LinearLayoutManager(this));
        rvCategoryItems.setAdapter(foodAdapter);
    }

    private void setupFilterListeners() {
        chipGroupFilter.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chip_all) {
                filterAll();
            } else if (checkedId == R.id.chip_food) {
                filterFood();
            } else if (checkedId == R.id.chip_drink) {
                filterDrink();
            }
        });
    }

    private void filterAll() {
        displayList.clear();
        displayList.addAll(allFoodList);
        foodAdapter.notifyDataSetChanged();
        updateCategoryInfo("Tất cả", "Tất cả món ăn và đồ uống", displayList.size());
    }

    private void filterFood() {
        displayList.clear();
        for (FoodItem item : allFoodList) {
            if (item.getType() == FoodItem.FoodType.FOOD) {
                displayList.add(item);
            }
        }
        foodAdapter.notifyDataSetChanged();
        updateCategoryInfo("Đồ ăn", "Các món ăn nhanh phổ biến", displayList.size());
    }

    private void filterDrink() {
        displayList.clear();
        for (FoodItem item : allFoodList) {
            if (item.getType() == FoodItem.FoodType.DRINK) {
                displayList.add(item);
            }
        }
        foodAdapter.notifyDataSetChanged();
        updateCategoryInfo("Đồ uống", "Các loại đồ uống giải khát", displayList.size());
    }

    private void updateCategoryInfo(String title, String description, int itemCount) {
        tvCategoryTitle.setText("Danh mục: " + title);
        tvCategoryDescription.setText(description);
        tvItemCount.setText(itemCount + " món");
    }
}
