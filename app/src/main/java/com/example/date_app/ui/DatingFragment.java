package com.example.date_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.example.date_app.adapter.DatingAdapter;
import com.example.date_app.databinding.FragmentDatingBinding;
import com.example.date_app.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;
import java.util.Collections;

public class DatingFragment extends Fragment {

    FragmentDatingBinding binding;
    CardStackLayoutManager manager;

    public static ArrayList<UserModel> list = new ArrayList<>();

    // Static block to initialize list if needed
    static {
        // Example initialization
        list = new ArrayList<>();
    }

    // Static method to access the list
    public static ArrayList<UserModel> getList() {
        return list;
    }


    public DatingFragment() {
    }

    public static DatingFragment newInstance() {
        return new DatingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDatingBinding.inflate(inflater, container, false);
        getData();
        Log.d("DATING FRAGEMENT", "i love u");
        Toast.makeText(requireContext(), "i love u", Toast.LENGTH_SHORT).show();
        return binding.getRoot();
    }

    private void init() {
        CardStackLayoutManager layoutManager = new CardStackLayoutManager(requireContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                // Implement your logic for card dragging
            }

            @Override
            public void onCardSwiped(Direction direction) {
                if (manager.getTopPosition() == list.size()) {
                    Toast.makeText(requireContext(), "This is the last card", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCardRewound() {
                // Implement your logic for card rewinding
            }

            @Override
            public void onCardCanceled() {
                // Implement your logic for card canceling
            }

            @Override
            public void onCardAppeared(View view, int position) {
                // Implement your logic when a card appears
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                // Implement your logic when a card disappears
            }
        });

        layoutManager.setVisibleCount(3);
        layoutManager.setTranslationInterval(0.6f);
        layoutManager.setScaleInterval(0.8f);
        layoutManager.setMaxDegree(20.0f);
        layoutManager.setDirections(Direction.HORIZONTAL);

        this.manager = layoutManager;
    }

    private void getData() {
        FirebaseDatabase.getInstance().getReference("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            list.clear();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                UserModel model = data.getValue(UserModel.class);
                                if (model != null) {
                                    list.add(model);
                                }
                            }
                            Collections.shuffle(list);
                            init();
                            binding.cardStackView.setLayoutManager(manager);
                            binding.cardStackView.setItemAnimator(new DefaultItemAnimator());
                            binding.cardStackView.setAdapter(new DatingAdapter(requireContext(), list));
                        } else {
                            Toast.makeText(requireContext(), "No users found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
