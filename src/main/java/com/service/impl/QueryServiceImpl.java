package com.service.impl;

import com.entity.OrderBookRow;
import com.entity.type.Type;
import com.service.QueryService;

import java.util.*;

public class QueryServiceImpl implements QueryService {
    @Override
    public OrderBookRow countMaxBuy(Collection<OrderBookRow> rowList) {
        return rowList.stream()
                .filter(row -> Type.B.equals(row.getType()))
                .filter(row -> row.getSize() != 0)
                .max(Comparator.comparing(OrderBookRow::getPrice))
                .orElse(new OrderBookRow(0L, 0, null));
    }

    @Override
    public OrderBookRow countMaxAsk(Collection<OrderBookRow> rowList) {
        return rowList.stream()
                .filter(row -> Type.A.equals(row.getType()))
                .filter(row -> row.getSize() != 0)
                .min(Comparator.comparing(OrderBookRow::getPrice))
                .orElse(new OrderBookRow(0L, 0, null));
    }

    @Override
    public Integer countAmount(Map<Long, OrderBookRow> rowMap, Long price) {
        return Optional.ofNullable(rowMap.get(price))
                .map(OrderBookRow::getSize)
                .orElse(0);
    }

}
