package com.example.projecttng;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryFoodAdapter extends RecyclerView.Adapter<CategoryFoodAdapter.ViewHolder> {
    private List<FoodItem> foodItems;
    private Context context;

    public CategoryFoodAdapter(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvFoodName, tvPrice, tvSold, tvLikes;
        ImageButton btnAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            tvFoodName = itemView.findViewById(R.id.tv_food_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvSold = itemView.findViewById(R.id.tv_sold);
            tvLikes = itemView.findViewById(R.id.tv_likes);
            btnAdd = itemView.findViewById(R.id.btn_add);
        }
    }

    @NonNull
    @Override
    public CategoryFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryFoodAdapter.ViewHolder holder, int position) {
        FoodItem item = foodItems.get(position);

        holder.tvFoodName.setText(item.getName());
        holder.tvPrice.setText(item.getPrice());
        holder.imgFood.setImageResource(item.getImageResId());

        if (holder.tvSold != null) {
            holder.tvSold.setText(item.getSoldCount() + " đã bán");
        }

        if (holder.tvLikes != null) {
            holder.tvLikes.setText(item.getLikeCount() + " thích");
        }

        // Khi click vào nút "+"
        holder.btnAdd.setOnClickListener(v -> {
            FoodItem newItem = new FoodItem(
                    item.getName(),
                    item.getDescription(),
                    item.getCalories(),
                    item.getPrice(),
                    item.getTime(),
                    item.getImageResId(),
                    item.getSoldCount(),
                    item.getLikeCount(),
                    item.getRating()
            );
            newItem.setQuantity(1);

            CartManager.addItem(newItem);

            Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, ShoppingCartActivity.class);
            context.startActivity(intent);
        });

        // Khi click vào toàn bộ item
        holder.itemView.setOnClickListener(v -> {
            FoodItem newItem = new FoodItem(
                    item.getName(),
                    item.getDescription(),
                    item.getCalories(),
                    item.getPrice(),
                    item.getTime(),
                    item.getImageResId(),
                    item.getSoldCount(),
                    item.getLikeCount(),
                    item.getRating()
            );
            newItem.setQuantity(1);

            CartManager.addItem(newItem);

            Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, ShoppingCartActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }
}