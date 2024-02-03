package com.example.ibooks;

import java.util.List;

public class SelectedOrdersAdapter {
    private List<Order> orderList;
    private DeliveryOrderAdapter.OnItemClickListener listener;

    public SelectedOrdersAdapter(List<Order> orderList, DeliveryOrderAdapter.OnItemClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }


}
