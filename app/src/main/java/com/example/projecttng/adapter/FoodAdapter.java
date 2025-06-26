package com.example.projecttng.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.activity.FoodDetailActivity;
import com.example.projecttng.model.FoodItem;
import com.example.projecttng.R;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final Context context;
    private final List<FoodItem> foodList;

    public FoodAdapter(Context context, List<FoodItem> foodList) {
        this.context = context;
        this.foodList = new ArrayList<>(foodList != null ? foodList : new ArrayList<>());
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem item = foodList.get(position);
        if (item == null) return;

        holder.tvName.setText(item.getName());
        holder.tvDescription.setText(item.getDescription());
        holder.tvCalories.setText("Calories: " + item.getCalories());
        holder.tvPrice.setText(item.getFormattedPrice());
        holder.tvTime.setText("Thời gian: " + item.getTime());
        holder.imgFood.setImageResource(item.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodDetailActivity.class);
            intent.putExtra("name", item.getName());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("calories", item.getCalories());
            intent.putExtra("price", item.getFormattedPrice());
            intent.putExtra("time", item.getTime());
            intent.putExtra("imageResId", item.getImageResId());
            intent.putExtra("soldCount", item.getSoldCount());
            intent.putExtra("likeCount", item.getLikeCount());
            intent.putExtra("rating", item.getRating());
            intent.putExtra("type", item.getType().getDisplayName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    // Update the food list and notify UI
    public void setFoodList(List<FoodItem> newList) {
        foodList.clear();
        if (newList != null) {
            foodList.addAll(newList);
        }
        notifyDataSetChanged();
    }

    // Optional: get item at position
    public FoodItem getItem(int position) {
        return (position >= 0 && position < foodList.size()) ? foodList.get(position) : null;
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvName, tvDescription, tvCalories, tvPrice, tvTime;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.iv_food);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvCalories = itemView.findViewById(R.id.tv_calories);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}