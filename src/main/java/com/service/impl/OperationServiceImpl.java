package com.service.impl;

import com.entity.OrderBookRow;
import com.entity.type.Type;
import com.service.OperationService;

import java.util.Comparator;
import java.util.Map;

public class OperationServiceImpl implements OperationService {

    @Override
    public void processBuy(Map<Long, OrderBookRow> map, Integer size) {
        OrderBookRow minA = getMinA(map);
        process(map, minA, size);
    }

    @Override
    public void processSell(Map<Long, OrderBookRow> map, Integer size) {
        OrderBookRow maxB = getMaxB(map);
        process(map, maxB, size);
    }

    private void process(Map<Long, OrderBookRow> map, OrderBookRow row, Integer size) {
        if (row.getSize().equals(size)) {
            map.remove(row.getPrice());
        } else if (row.getSize() > size) {
            map.computeIfPresent(row.getPrice(), (aLong, orderBookRow) -> {
                orderBookRow.setSize(row.getSize() - size);
                return orderBookRow;
            });
        } else if ((row.getSize() < size)) {
            Integer newSize = size - row.getSize();
            map.remove(row.getPrice());
            if (row.getType().equals(Type.A)) {
                processBuy(map, newSize);
            } else {
                processSell(map, newSize);
            }
        }

    }


    private OrderBookRow getMinA(Map<Long, OrderBookRow> map) {
        return map.values().stream()
                .filter(orderBookRow -> orderBookRow.getType().equals(Type.A))
                .filter(orderBookRow -> orderBookRow.getSize() != 0L)
                .min(Comparator.comparing(OrderBookRow::getPrice))
                .orElseThrow(() -> new RuntimeException("Cannot get chippest"));
    }

    private OrderBookRow getMaxB(Map<Long, OrderBookRow> map) {
        return map.values().stream()
                .filter(orderBookRow -> orderBookRow.getType().equals(Type.B))
                .filter(orderBookRow -> orderBookRow.getSize() != 0L)
                .max(Comparator.comparing(OrderBookRow::getPrice))
                .orElseThrow(() -> new RuntimeException("Cannot get most expensive"));
    }


}
