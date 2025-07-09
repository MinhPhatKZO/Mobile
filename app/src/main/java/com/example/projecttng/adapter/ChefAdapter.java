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

import com.example.projecttng.R;
import com.example.projecttng.activity.ui.ChefFoodsActivity;
import com.example.projecttng.model.Chef;

import java.util.List;

public class ChefAdapter extends RecyclerView.Adapter<ChefAdapter.ChefViewHolder> {

    private final Context context;
    private final List<Chef> chefs;

    public ChefAdapter(Context context, List<Chef> chefs) {
        this.context = context;
        this.chefs = chefs;
    }

    @NonNull
    @Override
    public ChefViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chef, parent, false);
        return new ChefViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChefViewHolder holder, int position) {
        Chef chef = chefs.get(position);
        holder.tvName.setText(chef.getName());
        holder.imgChef.setImageResource(chef.getImageResId());

        // ✅ Bắt sự kiện click vào chef
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChefFoodsActivity.class);
            intent.putExtra("chefId", chef.getId());
            intent.putExtra("chefName", chef.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chefs.size();
    }

    public static class ChefViewHolder extends RecyclerView.ViewHolder {
        ImageView imgChef;
        TextView tvName;

        public ChefViewHolder(@NonNull View itemView) {
            super(itemView);
            imgChef = itemView.findViewById(R.id.img_chef);
            tvName = itemView.findViewById(R.id.tv_chef_name);
        }
    }
}
