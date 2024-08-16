package com.example.date_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.date_app.activity.MessageActivity;
import com.example.date_app.databinding.UserItemLayoutBinding;
import com.example.date_app.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageUserAdapter extends RecyclerView.Adapter<MessageUserAdapter.MessageUserViewHolder> {

    private Context context;
    private ArrayList<String> userList;

    private List<String> chatKey;

    public MessageUserAdapter(Context context, ArrayList<String> userList, List<String> chatKey) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MessageUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageUserViewHolder(UserItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageUserViewHolder holder, @SuppressLint("RecyclerView") int position) {

        FirebaseDatabase.getInstance().getReference("users").child(userList.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    UserModel data = snapshot.getValue(UserModel.class);
                    Glide.with(context)
                            .load(data.getImage())
                            .into(holder.binding.userImage);

                    holder.binding.userName.setText(data.getName());

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("chat_id", chatKey.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return  userList.size();
    }

    // ViewHolder class
    public static class MessageUserViewHolder extends RecyclerView.ViewHolder {

        private UserItemLayoutBinding binding;

        public MessageUserViewHolder(UserItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
