package com.example.projecttng;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    Context context;
    List<FoodItem> foodList;

    public FoodAdapter(Context context, List<FoodItem> foodList) {
        this.context = context;
        this.foodList = foodList;
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

        holder.tvName.setText(item.getName());
        holder.tvDescription.setText(item.getDescription());
        holder.tvCalories.setText(item.getCalories());
        holder.tvPrice.setText(item.getPrice());
        holder.tvTime.setText(item.getTime());
        holder.imgFood.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
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
