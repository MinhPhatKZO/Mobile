package com.example.projecttng.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.activity.ui.FoodDetailActivity;
import com.example.projecttng.dao.FavoriteDao;
import com.example.projecttng.model.FoodItem;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {

    private final Context context;
    private List<FoodItem> favoriteList;
    private final FavoriteDao favoriteDao;

    public FavoriteAdapter(Context context, List<FoodItem> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
        this.favoriteDao = new FavoriteDao(context);
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        FoodItem item = favoriteList.get(position);
        if (item == null) return;

        holder.tvName.setText(item.getName());
        holder.tvTime.setText(item.getTime() + " • Easy • by Devon Lane");
        holder.imgFood.setImageResource(item.getImageResId());

        holder.btnFavorite.setImageResource(R.drawable.ic_favoritefood);

        // Chuyển sang màn chi tiết khi click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodDetailActivity.class);
            intent.putExtra("id", item.getId());
            context.startActivity(intent);
        });

        // Xóa khỏi danh sách yêu thích
        holder.btnFavorite.setOnClickListener(v -> {
            favoriteDao.removeFavorite(item.getId());
            favoriteList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, favoriteList.size());
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList != null ? favoriteList.size() : 0;
    }

    public void setData(List<FoodItem> newList) {
        this.favoriteList = newList;
        notifyDataSetChanged();
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvName, tvTime;
        ImageButton btnFavorite;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            tvName = itemView.findViewById(R.id.tv_food_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
        }
    }
}
