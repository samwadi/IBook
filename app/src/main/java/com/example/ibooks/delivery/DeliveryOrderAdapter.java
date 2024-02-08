package com.example.ibooks.delivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibooks.Order;
import com.example.ibooks.R;

import java.util.List;

public class DeliveryOrderAdapter extends RecyclerView.Adapter<DeliveryOrderAdapter.DeliveryOrderViewHolder> {

    private List<Order> orderList;
    private OnItemClickListener listener;

    public DeliveryOrderAdapter(List<Order> orderList, OnItemClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeliveryOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allorder_item_order, parent, false);
        return new DeliveryOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryOrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.bind(order, listener);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class DeliveryOrderViewHolder extends RecyclerView.ViewHolder {

        private TextView bookNameTextView;
        private TextView requesterInfoTextView;
        private Button acceptButton;

        public DeliveryOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            bookNameTextView = itemView.findViewById(R.id.AllOrder_bookNameTextView);
            requesterInfoTextView = itemView.findViewById(R.id.AllOrder_requesterInfoTextView);
            acceptButton = itemView.findViewById(R.id.AllOrder_acceptButton);
        }

        public void bind(Order order, OnItemClickListener listener) {
            bookNameTextView.setText(order.getName()+"");

            String requesterInfo = "Requested by: " + order.getRequesterId() + "\nCity: Ramallah " + order.getCity() + "\nArea: Ramallah" + order.getArea();
            requesterInfoTextView.setText(requesterInfo);

            acceptButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(order);
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Order order);
    }

}
