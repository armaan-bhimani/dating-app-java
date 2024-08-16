package com.example.date_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.date_app.R;
import com.example.date_app.activity.EditProfileActivity;
import com.example.date_app.activity.utils.Config;
import com.example.date_app.auth.LoginActivity;
import com.example.date_app.databinding.FragmentProfileBinding;
import com.example.date_app.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Show loading dialog
        Config.showdialog(requireContext());

        // Load user data from Firebase
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        userRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserModel data = dataSnapshot.getValue(UserModel.class);
                    if (data != null) {
                        // Set data to EditText fields or TextViews
                        binding.name.setText(data.getName());
                        binding.city.setText(data.getCity());
                        binding.email.setText(data.getEmail());
                        binding.number.setText(data.getNumber());

                        // Load user image using Glide
                        Glide.with(requireContext())
                                .load(data.getImage())
                                .placeholder(R.drawable.boy)
                                .into(binding.userImage);
                    }
                }

                // Hide loading dialog after data is loaded
                Config.hideDialog();
            }
        });

        // Handle logout button click
        binding.Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(requireContext(), LoginActivity.class));
                requireActivity().finish();
            }
        });

        // Handle edit profile button click
        binding.EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), EditProfileActivity.class));
            }
        });
    }
}
