package com.example.ibooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerYourOrders;
    private RecyclerView recyclerOtherOrders;
    private OrderAdapter yourOrdersAdapter;
    private OrderAdapter otherOrdersAdapter;
    private List<Order> yourOrdersList;
    private List<Order> otherOrdersList;

    public OrderFragment(Book book) {
    }
    public OrderFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerYourOrders = view.findViewById(R.id.recycler_your_orders);
        recyclerOtherOrders = view.findViewById(R.id.recycler_other_orders);

        yourOrdersList = new ArrayList<>();
        otherOrdersList = new ArrayList<>();

        yourOrdersAdapter = new OrderAdapter(yourOrdersList,getActivity());
        otherOrdersAdapter = new OrderAdapter(otherOrdersList,getActivity());

        recyclerYourOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerOtherOrders.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerYourOrders.setAdapter(yourOrdersAdapter);
        recyclerOtherOrders.setAdapter(otherOrdersAdapter);

        // Load your orders and other orders
        loadYourOrders();
        loadOtherOrders();
    }

    private void loadYourOrders() {
        // TODO: Load your orders from Firebase or any other data source
        // Test Data
        Order order1 = new Order("1", "book1", "owner1", "requester1","97","test1");
        Order order2 = new Order("2", "book2", "owner2", "requester1","87","test2");
        Order order3 = new Order("3", "book3", "owner3", "requester1","54","test3");

//        yourOrdersList.clear();
        yourOrdersList.add(order1);
        yourOrdersList.add(order2);
        yourOrdersList.add(order3);
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //yourOrdersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
//                    Toast.makeText(getActivity(),snapshot.,Toast.LENGTH_LONG).show();
                    if (order != null && order.getRequesterId().equals("currentUserId")) {
                        yourOrdersList.add(order);
                    }
                }

                yourOrdersAdapter.notifyDataSetChanged();
                //return null;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(getActivity(), "Failed to load orders: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadOtherOrders() {
        // TODO: Load other orders from Firebase
        DatabaseReference otherOrdersRef = FirebaseDatabase.getInstance().getReference("Orders");

        otherOrdersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                otherOrdersList.clear();

                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    Order order = orderSnapshot.getValue(Order.class);
                    if (order != null) {
                        otherOrdersList.add(order);
                    }
                }

                otherOrdersAdapter.notifyDataSetChanged();
                //return null;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

}
