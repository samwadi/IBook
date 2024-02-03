package com.example.ibooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView orderIdTextView;
        private TextView bookIdTextView;
        private TextView BookNameTextView;
        private TextView PriceTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            bookIdTextView = itemView.findViewById(R.id.bookIdTextView);
            BookNameTextView = itemView.findViewById(R.id.NameTextView);
            PriceTextView = itemView.findViewById(R.id.priceTextView);
        }

        public void bind(Order order) {
            orderIdTextView.setText("Order ID: " + order.getOrderId());
            bookIdTextView.setText("Book ID: " + order.getBookId());
            BookNameTextView.setText("Owner ID: " + order.getOwnerId());
            PriceTextView.setText("Requester ID: " + order.getRequesterId());
        }
    }
}
