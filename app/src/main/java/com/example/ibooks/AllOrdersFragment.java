package com.example.ibooks;

import android.os.Bundle;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_orders, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAllOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        orderList = new ArrayList<>();
        orderAdapter = new DeliveryOrderAdapter(orderList, new DeliveryOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                // Handle item click if needed
                String bookId = order.getBookId();
                String requesterId = order.getRequesterId();
                String ownerId = order.getOwnerId();
                String city = order.getCity();
                String area = order.getArea();

                DatabaseReference toDeliverRef = FirebaseDatabase.getInstance().getReference("toDeliver");
                mAuth = FirebaseAuth.getInstance();
                String toDeliveryId = toDeliverRef.push().getKey();
                String deliveryPerson = mAuth.getCurrentUser().getUid();
                ToDeliver toDeliver = new ToDeliver(toDeliveryId, bookId, requesterId, ownerId, city, area,deliveryPerson);

                toDeliverRef.child(toDeliveryId).setValue(toDeliver);

                // remove the order from the Orders
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
                ordersRef.child(order.getOrderId()).removeValue();
                orderList.remove(order);
                orderAdapter.notifyDataSetChanged();
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
