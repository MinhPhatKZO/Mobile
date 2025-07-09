    package com.example.projecttng.activity.admin;

    import android.os.Bundle;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import com.example.projecttng.R;
    import com.example.projecttng.dao.FoodDao;
    import com.example.projecttng.model.FoodItem;

    public class EditFoodActivity extends AppCompatActivity {

        private EditText etName, etDescription, etPrice, etCalories, etTime;
        private Button btnSave;
        private FoodDao foodDao;
        private FoodItem foodItem;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_food);

            etName = findViewById(R.id.et_name);
            etDescription = findViewById(R.id.et_description);
            etPrice = findViewById(R.id.et_price);
            etCalories = findViewById(R.id.et_calories);
            etTime = findViewById(R.id.et_time);
            btnSave = findViewById(R.id.btn_save);

            foodDao = new FoodDao(this);

            foodItem = (FoodItem) getIntent().getSerializableExtra("food");
            if (foodItem != null) {
                etName.setText(foodItem.getName());
                etDescription.setText(foodItem.getDescription());
                etPrice.setText(foodItem.getPrice());
                etCalories.setText(foodItem.getCalories());
                etTime.setText(foodItem.getTime());
            } else {
                Toast.makeText(this, "Không tìm thấy món ăn", Toast.LENGTH_SHORT).show();
                finish();
            }

            btnSave.setOnClickListener(v -> {
                if (foodItem != null) {
                    foodItem.setName(etName.getText().toString().trim());
                    foodItem.setDescription(etDescription.getText().toString().trim());
                    foodItem.setPrice(etPrice.getText().toString().trim());
                    foodItem.setCalories(etCalories.getText().toString().trim());
                    foodItem.setTime(etTime.getText().toString().trim());

                    foodDao.updateFood(foodItem);
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
