package com.example.projecttng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projecttng.R;
import com.example.projecttng.model.User;

import java.util.List;

public class ManageUsersAdapter extends RecyclerView.Adapter<ManageUsersAdapter.ViewHolder> {

    private final List<User> userList;
    private final Context context;

    public ManageUsersAdapter(Context ctx, List<User> list) {
        this.context = ctx;
        this.userList = list;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        User u = userList.get(pos);
        holder.ivUserIcon.setImageResource(u.getAvatarResId()); // avatar từ drawable
        holder.tvUserName.setText(u.getName());
        holder.tvUserEmail.setText(u.getEmail());
    }

    @Override public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserIcon;
        TextView tvUserName, tvUserEmail;
        ViewHolder(@NonNull View v) {
            super(v);
            ivUserIcon = v.findViewById(R.id.ivUserIcon);
            tvUserName = v.findViewById(R.id.tvUserName);
            tvUserEmail = v.findViewById(R.id.tvUserEmail);
        }
    }
}
