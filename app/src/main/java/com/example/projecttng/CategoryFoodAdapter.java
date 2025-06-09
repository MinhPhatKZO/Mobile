package com.example.projecttng;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryFoodAdapter extends RecyclerView.Adapter<CategoryFoodAdapter.ViewHolder> {
    private List<FoodItem> foodList;

    public CategoryFoodAdapter(List<FoodItem> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false); // Use item_category.xml
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem item = foodList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvSold.setText(item.getSoldCount() + "+ đã bán");
        holder.tvLike.setText(item.getLikeCount() + " thích");
        holder.tvPrice.setText(item.getPrice());
        holder.ivFood.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFood;
        TextView tvName, tvSold, tvLike, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.img_food); // Đúng với layout
            tvName = itemView.findViewById(R.id.tv_food_name); // Đúng với layout
            tvSold = itemView.findViewById(R.id.tv_sold);
            tvLike = itemView.findViewById(R.id.tv_likes);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }


}
