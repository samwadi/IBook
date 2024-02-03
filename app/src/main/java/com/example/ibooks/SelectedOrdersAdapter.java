package com.example.ibooks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SelectedOrdersAdapter extends RecyclerView.Adapter<SelectedOrdersAdapter.ViewHolder> {

    private List<ToDeliver> selectedOrdersList;

    public SelectedOrdersAdapter(List<ToDeliver> selectedOrdersList) {
        this.selectedOrdersList = selectedOrdersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDeliver toDeliver = selectedOrdersList.get(position);

        holder.bookIdTextView.setText("Book ID: " + toDeliver.getBookId());
        holder.requesterIdTextView.setText("Requester ID: " + toDeliver.getRequesterId());
        holder.ownerIdTextView.setText("Owner ID: " + toDeliver.getOwnerId());
        holder.cityTextView.setText("City: " + toDeliver.getCity());
        holder.areaTextView.setText("Area: " + toDeliver.getArea());
        holder.deliveryPersonIdTextView.setText("Delivery Person ID: " + toDeliver.getDeliveryPersonId());
    }

    @Override
    public int getItemCount() {
        return selectedOrdersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookIdTextView;
        TextView requesterIdTextView;
        TextView ownerIdTextView;
        TextView cityTextView;
        TextView areaTextView;
        TextView deliveryPersonIdTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookIdTextView = itemView.findViewById(R.id.bookIdTextView);
            requesterIdTextView = itemView.findViewById(R.id.requesterIdTextView);
            ownerIdTextView = itemView.findViewById(R.id.ownerIdTextView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            areaTextView = itemView.findViewById(R.id.areaTextView);
            deliveryPersonIdTextView = itemView.findViewById(R.id.deliveryPersonIdTextView);
        }
    }
}
