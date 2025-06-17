package com.example.projecttng;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<FoodItem> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.rv_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        loadProducts();

        foodAdapter = new FoodAdapter(this, productList);
        recyclerView.setAdapter(foodAdapter);
    }

    private void loadProducts() {
        productList.add(new FoodItem("Pizza", "Pizza phô mai béo ngậy", "1200kcal", "50.000đ", "45min", R.drawable.garan, 100, 10, 4.5f, FoodItem.FoodType.FOOD));
        productList.add(new FoodItem("Hamburger", "Hamburger bò Mỹ đặc biệt", "950kcal", "45.000đ", "30min", R.drawable.bunbo, 120, 9, 4.4f, FoodItem.FoodType.FOOD));
        productList.add(new FoodItem("Kem dâu", "Kem tươi mát lạnh vị dâu", "300kcal", "20.000đ", "5min", R.drawable.banhmi, 60, 6, 4.6f, FoodItem.FoodType.FOOD));
        productList.add(new FoodItem("Coca Cola", "Nước giải khát có ga", "140kcal", "10.000đ", "2min", R.drawable.trada, 90, 8, 4.2f, FoodItem.FoodType.DRINK));
    }
}
