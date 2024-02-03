package com.example.ibooks;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SelectedOrdersFragment extends Fragment {

    private RecyclerView selectedOrdersRecyclerView;
    private SelectedOrdersAdapter selectedOrdersAdapter;
    private List<ToDeliver> selectedOrdersList;

    private DatabaseReference toDeliverRef;
    private FirebaseAuth mAuth;

    public SelectedOrdersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selected_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedOrdersRecyclerView = view.findViewById(R.id.selectedOrdersRecyclerView);
        selectedOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        selectedOrdersList = new ArrayList<>();
        selectedOrdersAdapter = new SelectedOrdersAdapter(selectedOrdersList);
        selectedOrdersRecyclerView.setAdapter(selectedOrdersAdapter);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String deliveryPersonId = currentUser.getUid();
            toDeliverRef = FirebaseDatabase.getInstance().getReference("toDeliver");

            toDeliverRef.orderByChild("deliveryPersonId").equalTo(deliveryPersonId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            selectedOrdersList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                ToDeliver toDeliver = snapshot.getValue(ToDeliver.class);
                                if (toDeliver != null) {
                                    selectedOrdersList.add(toDeliver);
                                }
                            }

                            selectedOrdersAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle error
                        }
                    });
        }
    }
}

