package com.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OrderBook {

    private final Map<Long, OrderBookRow> orderBookRowMap = new HashMap<>();

    public void addRow(OrderBookRow orderBookRow) {
        orderBookRowMap.put(orderBookRow.getPrice(), orderBookRow);
    }

    public Collection<OrderBookRow> getOrderBookRowList() {
        return orderBookRowMap.values();
    }

    public Map<Long, OrderBookRow> getOrderBookRowMap() {
        return orderBookRowMap;
    }
}
