package com.example.date_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.date_app.R;
import com.example.date_app.activity.MessageActivity;
import com.example.date_app.databinding.ItemUserLayoutBinding;
import com.example.date_app.model.UserModel;

import java.util.ArrayList;

public class DatingAdapter extends RecyclerView.Adapter<DatingAdapter.DatingViewHolder> {
    private Context context;
    private ArrayList<UserModel> list;

    public DatingAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserLayoutBinding binding = ItemUserLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new DatingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DatingViewHolder holder, @SuppressLint("RecyclerView") int position) {
        UserModel user = list.get(position);

        holder.binding.textView4.setText(user.getName());
        holder.binding.textView3.setText(user.getEmail());

        Glide.with(context)
                .load(user.getImage())
                .into(holder.binding.userImage);

        holder.binding.chatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userId", list.get(position).getNumber());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DatingViewHolder extends RecyclerView.ViewHolder {
        private ItemUserLayoutBinding binding;

        public DatingViewHolder(ItemUserLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
