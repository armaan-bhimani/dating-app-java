package com.example.date_app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.date_app.R;
import com.example.date_app.adapter.MessageAdapter;
import com.example.date_app.databinding.ActivityMessageBinding;
import com.example.date_app.model.MessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MessageActivity extends AppCompatActivity {

    ActivityMessageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getdata(getIntent().getStringExtra("chat_id"));

        binding.imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.yourMessage.getText().toString().isEmpty()) {
                    Toast.makeText(MessageActivity.this, "Please Enter Your Message", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendMessage(binding.yourMessage.getText().toString());
                }

            }
        });
    }

    private void getdata(String chatId) {
        FirebaseDatabase.getInstance().getReference("chats").child(chatId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MessageModel> list = new ArrayList<>();
                for(DataSnapshot show : snapshot.getChildren()){
                    list.add(show.getValue(MessageModel.class));
                }
//                binding.recyclerview2.adapter = MessageAdapter(this@MessageActivity,list);
                MessageAdapter adapter = new MessageAdapter(MessageActivity.this, list);
                binding.recyclerview2.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void sendMessage(String message) {

        String receiverId = getIntent().getStringExtra("userId");

        String senderId = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        String chatId = senderId + receiverId;

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());

        HashMap<String, String> map = new HashMap<>();
        map.put("message", message);
        map.put("senderId", senderId);
        map.put("currentTime", currentTime);
        map.put("currentDate", currentDate);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats").child(chatId);

        reference.child(reference.push().getKey()).setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            binding.yourMessage.setText(null);
                            Toast.makeText(MessageActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MessageActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}