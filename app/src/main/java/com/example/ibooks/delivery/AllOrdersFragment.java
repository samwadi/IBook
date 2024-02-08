package com.example.ibooks.delivery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibooks.Order;
import com.example.ibooks.R;
import com.example.ibooks.User;
import com.example.ibooks.delivery.DeliveryOrderAdapter;
import com.example.ibooks.delivery.ToDeliver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class AllOrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Order> orderList;
    private DeliveryOrderAdapter orderAdapter;
    private FirebaseAuth mAuth;

    private DatabaseReference ordersReference;
    private DatabaseReference usersReference;
    private static final String TAG = "AllOrdersFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_orders, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAllOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        orderList = new ArrayList<>();
        orderAdapter = new DeliveryOrderAdapter(orderList, new DeliveryOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                if (order != null) {
                    String bookId = order.getBookId();
                    String requesterId = order.getRequesterId();
                    String ownerId = order.getOwnerId();
                    String city = order.getCity() != null ? order.getCity() + "Ramallah" : "Ramallah";
                    String area = order.getArea() != null ? order.getArea() + "Ramallah" : "Ramallah";

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    if (mAuth.getCurrentUser() != null) {
                        String deliveryPerson = mAuth.getCurrentUser().getUid();
                        if (deliveryPerson != null) {
                            DatabaseReference toDeliverRef = FirebaseDatabase.getInstance().getReference("toDeliver");
                            String toDeliveryId = toDeliverRef.push().getKey();
                            if (toDeliveryId != null) {
                                ToDeliver toDeliver = new ToDeliver(toDeliveryId, bookId, requesterId, ownerId, city, area, deliveryPerson);
                                toDeliverRef.child(toDeliveryId).setValue(toDeliver)
                                        .addOnSuccessListener(aVoid -> {
                                            // Operation successful, remove the order from the Orders
                                            DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
                                            ordersRef.child(order.getOrderId()).removeValue()
                                                    .addOnSuccessListener(aVoid1 -> {
                                                        // Successfully removed the order, update the UI
                                                        orderList.remove(order);
                                                        orderAdapter.notifyDataSetChanged();
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        // Failed to remove the order, handle the error
                                                        Log.e(TAG, "Failed to remove order: " + e.getMessage());
                                                        Toast.makeText(getContext(), "Failed to remove order", Toast.LENGTH_SHORT).show();

                                                    });
                                        })
                                        .addOnFailureListener(e -> {
                                            // Failed to set the value, handle the error
                                            Log.e(TAG, "Failed to set toDeliver value: " + e.getMessage());
                                            Toast.makeText(getContext(), "Failed to set toDeliver value", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Log.e(TAG, "toDeliveryId is null");
                                Toast.makeText(getContext(), "toDeliveryId is null", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Log.e(TAG, "deliveryPerson is null");
                            Toast.makeText(getContext(), "deliveryPerson is null", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Log.e(TAG, "CurrentUser is null");
                        Toast.makeText(getContext(), "CurrentUser is null", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.e(TAG, "Order is null");
                    Toast.makeText(getContext(), "Order is null", Toast.LENGTH_SHORT).show();
                }
            }

        });

        recyclerView.setAdapter(orderAdapter);

        ordersReference = FirebaseDatabase.getInstance().getReference("Orders");
        usersReference = FirebaseDatabase.getInstance().getReference("User");

        loadOrders();

        return view;
    }

    private void loadOrders() {
        orderList.clear();

        ordersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    Order order = orderSnapshot.getValue(Order.class);
                    if (order != null) {
                        loadUserDetails(order);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void loadUserDetails(Order order) {
        usersReference.child(order.getRequesterId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    order.setArea(user.getArea());
                    order.setCity(user.getCity());
                    orderList.add(order);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
