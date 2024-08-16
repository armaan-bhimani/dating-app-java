package com.example.date_app.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.date_app.MainActivity;
import com.example.date_app.R;
import com.example.date_app.activity.utils.Config;
import com.example.date_app.databinding.ActivityLoginBinding;
import com.example.date_app.databinding.ActivityRegisterBinding;
import com.example.date_app.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {

    Uri imageUri = null;
    private ActivityResultLauncher<String> selectImage = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    imageUri = uri;
                    binding.userimage.setImageURI(imageUri);
                }
            });

    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage.launch("image/*");
            }
        });

        binding.savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        if (binding.username.getText().toString().isEmpty() ||
                binding.useremail.getText().toString().isEmpty() ||
                binding.usercity.getText().toString().isEmpty())
        {
            Toast.makeText(RegisterActivity.this,"Please Enter All Fields",Toast.LENGTH_SHORT).show();
        } else if (!binding.termsconditions.isChecked())
        {
            Toast.makeText(RegisterActivity.this,"Please Accept Terms And Conditions",Toast.LENGTH_SHORT).show();
        }
        else {
//            uploadImage(imageUri, binding.username.getText().toString(),
//                    binding.useremail.getText().toString(), binding.usercity.getText().toString());
//            storeData();
            uploadImage();
        }

    }

    private void uploadImage() {
        Config.showdialog(this);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference("profile")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile.jpg");

        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                storeData(uri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                hideDialog();
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        hideDialog();
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void storeData(Uri imageUri) {
        String name = binding.username.getText().toString();
        String email = binding.useremail.getText().toString();
        String city = binding.usercity.getText().toString();
        String image = imageUri.toString();
//        Intent i= new Intent(RegisterActivity.this,LoginActivity.class);
//        startActivity(i);
//        finish();

        UserModel data = new UserModel(name,image, email, city);

        String phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        FirebaseDatabase.getInstance().getReference("users").child(phoneNumber).setValue(data)
                .addOnCompleteListener(task -> {
                    hideDialog();
                    if (task.isSuccessful()) {
                        Intent i= new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    Toast.makeText(RegisterActivity.this, "User Register Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Stor Data Toss", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void hideDialog() {
        // Method to hide dialog
    }
}